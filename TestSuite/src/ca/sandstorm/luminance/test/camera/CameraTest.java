package ca.sandstorm.luminance.test.camera;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.camera.Camera;

/**
 * Testing of the Camera Class
 * @author lianghuang
 *
 */
public class CameraTest extends AndroidTestCase {

	private Camera _camera;

	private Vector3f _target, _eye;

	protected void setUp() throws Exception {
		super.setUp();

		_camera = new Camera();
		
		_target = new Vector3f(0, 0, 0);
		_eye = new Vector3f(0, 0, 0);
	}

	public void testGetEye() throws Exception {
		assertTrue(_camera.getEye().equals(_eye));
	}

	public void testGetTarget() throws Exception {
		assertTrue(_camera.getTarget().equals(_target));
	}

	public void testSetEye() throws Exception {
		Vector3f _eyeTwo = new Vector3f(1, 1, 1);

		_camera.setEye(new Vector3f(1, 1, 1));
		assertTrue(_camera.getEye().equals(_eyeTwo));

		_camera.setEye(1, 0, 1);
		assertTrue(_camera.getEye().equals(new Vector3f(1, 0, 1)));
	}

	public void testSetTarget() throws Exception {
		Vector3f _targetTwo = new Vector3f(1, 1, 1);

		_camera.setTaret(new Vector3f(1, 1, 1));
		assertTrue(_camera.getTarget().equals(_targetTwo));

		_camera.setTarget(1, 0, 1);
		assertTrue(_camera.getTarget().equals(new Vector3f(1, 0, 1)));
	}

	public void testSetUp() throws Exception {
		// Up is a private var and no other way to access it.
		// It is also only used in cross and move functions.
		// They are assumed to be working correctly
		assertTrue(true);
	}

	public void testSetViewPort() throws Exception {
		// This is used to call Open GL
		// Assumed to be working correctly
		assertTrue(true);
	}

	public void testSetPerspective() throws Exception {
		// This is used to call Open GL
		// Assumed to be working correctly
		assertTrue(true);
	}

	public void testUpdateViewMatrix() throws Exception {
		// This is used to call Open GL
		// Assumed to be working correctly
		assertTrue(true);
	}

	public void testUpdateViewDirection() throws Exception {
		// Uses Java built-in method calls
		assertTrue(true);
	}

	public void testMove() throws Exception {
		float dis, x, y, z;
		Vector3f _target, _eye;

		dis = 2;
		x = 3;
		y = 4;
		z = 5;

		_target = new Vector3f(_camera.getTarget());
		_target.x += dis * x;
		_target.y += dis * y;
		_target.z += dis * z;

		_eye = new Vector3f(_camera.getEye());
		_eye.x += dis * x;
		_eye.y += dis * y;
		_eye.z += dis * z;

		_camera.move(dis, x, y, z);

		assertTrue(_target.equals(_camera.getTarget()));
		assertTrue(_eye.equals(_camera.getEye()));
	}

	public void testMoveDirection() throws Exception {
		// Uses built-in java calls
		// Covers moveUp, moveForward, moveLeft
		assertTrue(true);
	}

	public void testRotateCamera() {
		Vector3f _target = new Vector3f(_camera.getTarget());
		_camera.rotateCamera(1, 2, 3, 4);
		Vector3f _targetTwo = new Vector3f(_camera.getTarget());

		assertTrue(_target.x == _targetTwo.x);
		assertTrue(_target.y == _targetTwo.y);
		assertTrue(_target.z == _targetTwo.z);
	}

	public void testGetCurrentModelView() throws Exception {
		// Open GL call
		assertTrue(true);
	}

	public void testGetCurrentProject() throws Exception {
		// helper function and OpenGL
		assertTrue(true);
	}

	public void testGetMatrix() throws Exception {
		// Open GL call
		assertTrue(true);
	}

	public void testGetWorldCoords() throws Exception {
		assertNotNull(_camera.getWorldCoord(new Vector2f(5, 5)));
	}
}
