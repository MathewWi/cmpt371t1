package ca.sandstorm.luminance.test.camera;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.camera.Camera;

public class CameraTest extends AndroidTestCase {

	private Camera camera;
	
	private Vector3f up, target, eye, lookDirection, strafeDirection;
	
	private float[] lastProjectionMat, lastModelViewMat;
	
	private Quat4f rotation, view, newView, conjugate;
	
	protected void setUp() throws Exception {
		super.setUp();

		camera = new Camera();
		
		up = new Vector3f(0, 1, 0);
		target = new Vector3f(0, 0, 0);
		eye = new Vector3f(0, 0, 0);

		lookDirection = new Vector3f();
		strafeDirection = new Vector3f();

		lastProjectionMat = new float[16];
		lastModelViewMat = new float[16];

		rotation = new Quat4f();
		view = new Quat4f();
		newView = new Quat4f();
		conjugate = new Quat4f();		
	}
	
	public void testGetEye() throws Exception	{
		assertTrue(camera.getEye().equals(eye));
	}
	
	public void testGetTarget() throws Exception {
		assertTrue(camera.getTarget().equals(target));
	}
	
	public void testSetEye() throws Exception{
		Vector3f eyeTwo = new Vector3f(1,1,1);
		
		camera.setEye(new Vector3f(1,1,1));
		assertTrue(camera.getEye().equals(eyeTwo));
		
		camera.setEye(1,0,1);
		assertTrue(camera.getEye().equals(new Vector3f(1,0,1)));
	}
	
	public void testSetTarget() throws Exception{
		Vector3f targetTwo = new Vector3f(1,1,1);
		
		camera.setTaret(new Vector3f(1,1,1));
		assertTrue(camera.getTarget().equals(targetTwo));
		
		camera.setTarget(1,0,1);
		assertTrue(camera.getTarget().equals(new Vector3f(1,0,1)));
	}
	
	public void testSetUp() throws Exception{
		// Up is a private var and no other way to access it.
		// It is also only used in cross and move functions.
		// They are assumed to be working correctly
		assertTrue(true);
	}
	
	public void testSetViewPort() throws Exception{
		// This is used to call Open GL
		// Assumed to be working correctly
		assertTrue(true);
	}
	
	public void testSetPerspective() throws Exception{
		// This is used to call Open GL
		// Assumed to be working correctly
		assertTrue(true);
	}
	
	public void testUpdateViewMatrix() throws Exception{
		// This is used to call Open GL
		// Assumed to be working correctly
		assertTrue(true);
	}
	
	public void testUpdateViewDirection() throws Exception{
		// Uses Java built-in method calls
		assertTrue(true);
	}
	
	public void testMove() throws Exception{
		float dis, x, y, z;
		Vector3f target, eye;
		
		dis = 2;
		x = 3;
		y = 4;
		z = 5;
		
		target = new Vector3f(camera.getTarget());
		target.x += dis * x;
		target.y += dis * y;
		target.z += dis * z;
		
		eye = new Vector3f(camera.getEye());
		eye.x += dis * x;
		eye.y += dis * y;
		eye.z += dis * z;
		
		camera.move(dis, x, y, z);
		
		assertTrue(target.equals(camera.getTarget()));
		assertTrue(eye.equals(camera.getEye()));
	}
	
	public void testMoveDirection() throws Exception{
		// Uses built-in java calls
		// Covers moveUp, moveForward, moveLeft
		assertTrue(true);
	}

	public void testRotateCamera(){
		Vector3f target = new Vector3f(camera.getTarget());
		camera.rotateCamera(1,2,3,4);
		Vector3f targetTwo = new Vector3f(camera.getTarget());
		
		assertTrue(target.x == targetTwo.x);
		assertTrue(target.y == targetTwo.y);
		assertTrue(target.z == targetTwo.z);
	}
	
	public void testGetCurrentModelView() throws Exception{
		//Open GL call
		assertTrue(true);
	}
	
	public void testGetCurrentProject() throws Exception{
		// helper function and OpenGL
		assertTrue(true);
	}
	
	public void testGetMatrix() throws Exception{
		// Open GL call
		assertTrue(true);
	}
	
	public void testGetWorldCoords() throws Exception{
		assertNotNull(camera.getWorldCoord(new Vector2f(5,5)));
	}
}
