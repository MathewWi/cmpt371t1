package ca.sandstorm.luminance.test.gameobject;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.gameobject.Skybox;

/**
 * Testing of the Skybox class of the gameobject package
 * 
 * @author Martina Nagy
 * 
 */
public class SkyboxTest extends AndroidTestCase {

	Skybox _mySkybox;

	/**
	 * Create an instance of Box to test.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		_mySkybox = new Skybox();
	}

	/**
	 * Test the getPosition method.
	 * 
	 * @throws Exception
	 */
	public void testGetPosition() throws Exception {
		// Vector3f dummy = _mySkybox.getPosition();
		/*
		 * assertTrue(dummy.x == 0); assertTrue(dummy.y == 0);
		 * assertTrue(dummy.z == 0);
		 */
		// TODO: write proper tests;
	}

	/**
	 * Test the getRotation method.
	 * 
	 * @throws Exception
	 */
	public void testGetRotation() throws Exception {
		// Vector4f dummy = _mySkybox.getRotation();
		/*
		 * assertTrue(dummy.w == 0.0f); assertTrue(dummy.x == 1.0f);
		 * assertTrue(dummy.y == 0.0f); assertTrue(dummy.z == 0.5f);
		 */
		// TODO: write proper tests;
	}

	/**
	 * Test the getScale method.
	 * 
	 * @throws Exception
	 */
	public void testGetScale() throws Exception {
		// Vector3f dummy = _mySkybox.getScale();
		/*
		 * assertTrue(dummy.x == 0.0f); assertTrue(dummy.y == 0.0f);
		 * assertTrue(dummy.z == 0.0f);
		 */
		// TODO: write proper tests;
	}

	/**
	 * Dispose of the created resource.
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
