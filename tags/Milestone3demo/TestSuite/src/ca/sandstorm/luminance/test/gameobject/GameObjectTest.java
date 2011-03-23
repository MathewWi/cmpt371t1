package ca.sandstorm.luminance.test.gameobject;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import android.test.AndroidTestCase;

import ca.sandstorm.luminance.gameobject.Box;
import ca.sandstorm.luminance.gameobject.GameObject;

public class GameObjectTest extends AndroidTestCase{
	GameObject myObject;
	
	/**
	 * Create an instance of Box to test.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		myObject = new Box(new Vector3f(0,0,0), new Vector3f(0,0,0));
	}

	/**
	 * Test the update method.
	 * 
	 * @throws Exception
	 */
	public void testUpdate() throws Exception {
		// assertTrue(myObject.Update());
		// TODO: write proper tests
	}

	/**
	 * Test the destroy method.
	 * 
	 * @throws Exception
	 */
	public void testDestroy() throws Exception {
		// assertTrue(myObject.destroy());
		// TODO: write proper tests
	}

	/**
	 * Test the getPosition method.
	 * 
	 * @throws Exception
	 */
	public void testGetPosition() throws Exception {
		Vector3f dummy = myObject.getPosition();
		assertTrue(dummy.x == 0);
		assertTrue(dummy.y == 0);
		assertTrue(dummy.z == 0);
	}
	
	/**
	 * Test the setPosition method.
	 * 
	 * @throws Exception
	 */
	public void testSetPosition() throws Exception {
		myObject.setPosition(new Vector3f(1.0f,1.0f,1.0f));
		Vector3f dummy = myObject.getPosition();
		assertTrue(dummy.x == 1.0f);
		assertTrue(dummy.y == 1.0f);
		assertTrue(dummy.z == 1.0);
	}

	/**
	 * Test the getRotation method.
	 * 
	 * @throws Exception
	 */
	public void testGetRotation() throws Exception {
		Vector4f dummy = myObject.getRotation();
		assertTrue(dummy.w == 0.0f);
		assertTrue(dummy.x == 0.0f);
		assertTrue(dummy.y == 0.0f);
		assertTrue(dummy.z == 0.0f);
	}

	/**
	 * Test the getScale method.
	 * 
	 * @throws Exception
	 */
	public void testGetScale() throws Exception {
		Vector3f dummy = myObject.getScale();
		assertTrue(dummy.x == 0.0f);
		assertTrue(dummy.y == 0.0f);
		assertTrue(dummy.z == 0.0f);
	}

	/**
	 * Dispose of the created resource.
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
