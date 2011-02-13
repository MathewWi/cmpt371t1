/******************************************************************************
-- Quaternion Camera Class --
	A 6DOF Quaternion Camera

This code is free to use and modify just so long any modifications are clearly
documented.  Any use of this class in a commercial product is fine just so long
the original author is clearly documented for having created the class.

Copyright (C) 2010 
	Stephen Damm

 */

package ca.sandstorm.luminance.camera;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.Engine;

import android.opengl.GLU;


public class Camera
{
    private static final Logger logger = LoggerFactory.getLogger(Engine.class);

    private Vector3f _up;
    private Vector3f _target;
    private Vector3f _eye;

    private Vector3f _lookDirection;
    private Vector3f _strafeDirection;

    float _matView[];

    private Quat4f _qRotation;
    private Quat4f _qView;
    private Quat4f _qNewView;
    private Quat4f _qConjugate;


    public Camera()
    {
	_up = new Vector3f(0, 1, 0);
	_target = new Vector3f(0, 0, 0);
	_eye = new Vector3f(0, 0, 0);

	_lookDirection = new Vector3f();
	_strafeDirection = new Vector3f();

	_matView = new float[16];

	_qRotation = new Quat4f();
	_qView = new Quat4f();
	_qNewView = new Quat4f();
	_qConjugate = new Quat4f();
    }


    public void setEye(Vector3f v)
    {
	_eye = v;
    }


    public void setEye(float x, float y, float z)
    {
	_eye.x = x;
	_eye.y = y;
	_eye.z = z;
    }


    public void setTaret(Vector3f v)
    {
	_target = v;
    }


    public void setTaret(float x, float y, float z)
    {
	_target.x = x;
	_target.y = y;
	_target.z = z;
    }


    public void setUp(Vector3f v)
    {
	_target = v;
    }


    public void setUp(float x, float y, float z)
    {
	_up.x = x;
	_up.y = y;
	_up.z = z;
    }


    public void setViewPort(GL10 gl, int x, int y, int w, int h)
    {
	logger.debug("setViewPort(" + x + ", " + y + ", " + w + ", " + h + ")");

	gl.glViewport(x, y, w, h);
    }


    public void setPerspective(GL10 gl, float fov, float aspect, float zNear,
	    float zFar)
    {
	logger.debug("setPerspective(" + fov + ", " + aspect + ", " + zNear +
		     ", " + zFar + ")");

	gl.glMatrixMode(GL10.GL_PROJECTION);
	gl.glLoadIdentity();

	GLU.gluPerspective(gl, fov, aspect, zNear, zFar);

	gl.glMatrixMode(GL10.GL_MODELVIEW);
	gl.glLoadIdentity();
    }


    public void updateViewMatrix(GL10 gl)
    {
	//This was being spammed on every frame so I commented it out -Zenja
	//logger.debug("updateViewMatrix()");

	// calculate the mdoel view matrix

	/*
	 * Matrix.setLookAtM (_matView, 0, _eye.x, _eye.y, _eye.z, _target.x,
	 * _target.y, _target.z, _up.x, _up.y, _up.z);
	 */

	gl.glMatrixMode(GL10.GL_MODELVIEW);
	gl.glLoadIdentity();
	GLU.gluLookAt(gl, _eye.x, _eye.y, _eye.z, _target.x, _target.y,
		      _target.z, _up.x, _up.y, _up.z);

	// push the new openGL matrix
	// gl.glLoadMatrixf(_matView, 0);

	// gl.glPushMatrix();
    }


    // --------------------
    // Updates the Camera
    // Last modified: 06.02.05 15:04 (Halsafar)
    // --------------------
    public void move(float distance, float xDir, float yDir, float zDir)
    {
	// Move the camera on the X and Z axis. Don't do y to keep camera on the
	// ground.
	_eye.x += xDir * distance;
	_eye.y += yDir * distance;
	_eye.z += zDir * distance;

	// Move the view along with the position
	_target.x += xDir * distance;
	_target.y += yDir * distance;
	_target.z += zDir * distance;
    }


    public void moveForward(float distance)
    {
	// The look direction is the view (where we are looking) minus the
	// position (where we are).
	_lookDirection.set(_target);
	_lookDirection.sub(_eye);

	// Normalize the direction.
	_lookDirection.normalize();

	// Call UpdateCamera to move our camera in the direction we want.
	move(distance, _lookDirection.x, _lookDirection.y, _lookDirection.z);
    }


    public void moveLeft(float distance)
    {
	// The look direction is the view (where we are looking) minus the
	// position (where we are).
	_lookDirection.set(_target);
	_lookDirection.sub(_eye);

	// Normalize the direction.
	_lookDirection.normalize();

	// Get the cross product of the direction we are looking and the up
	// direction.
	_strafeDirection.cross(_lookDirection, _up);

	move(distance, _strafeDirection.x, _strafeDirection.y,
	     _strafeDirection.z);
    }


    public void moveUp(float distance)
    {
	// The look direction is the view (where we are looking) minus the
	// position (where we are).
	_lookDirection.set(_target);
	_lookDirection.sub(_eye);

	// Normalize the direction.
	_lookDirection.normalize();

	// Get the cross product of the direction we are looking and the up
	// direction.
	_strafeDirection.cross(_lookDirection, _up);

	// Calculate the up vector
	_strafeDirection.cross(_strafeDirection, _lookDirection);

	move(distance, _strafeDirection.x, _strafeDirection.y,
	     _strafeDirection.z);
    }


    // --------------------
    // Rotate Camera along a angle
    // Credit: Allen Sherrod
    // --------------------
    public void rotateCamera(float AngleDir, float xSpeed, float ySpeed,
	    float zSpeed)
    {
	// Create the rotation quaternion based on the axis we are rotating on.
	_qRotation.set(new AxisAngle4f(xSpeed, ySpeed, zSpeed, AngleDir));

	// Create the view quaternion. This will be the direction of the view
	// and position.
	_qView.x = _target.x - _eye.x;
	_qView.y = _target.y - _eye.y;
	_qView.z = _target.z - _eye.z;
	_qView.w = 0;

	// Create the resulting quaternion by multiplying the rotation quat by
	// the view quat
	// then multiplying that by the conjugate of the rotation quat.
	// TODO: might have broken this during port
	_qConjugate.conjugate(_qRotation);

	_qRotation.mul(_qView);
	_qRotation.mul(_qConjugate);

	_qNewView = _qRotation;
	// qNewView = ((qRotation * qView) * qRotation.conjugate());

	// Update the view information by adding the position to the resulting
	// quaternion.
	_target.x = _eye.x + _qNewView.x;
	_target.y = _eye.y + _qNewView.y;
	_target.z = _eye.z + _qNewView.z;
    }
}
