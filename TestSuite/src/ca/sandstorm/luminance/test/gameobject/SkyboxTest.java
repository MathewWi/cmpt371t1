package ca.sandstorm.luminance.test.gameobject;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import ca.sandstorm.luminance.gameobject.Receptor;
import ca.sandstorm.luminance.gameobject.RenderType;
import ca.sandstorm.luminance.gameobject.Skybox;
import android.test.AndroidTestCase;

/**
 * Testing of the Skybox class of the gameobject package
 * @author Martina Nagy
 *
 */
public class SkyboxTest extends AndroidTestCase {

	Skybox mySkybox;
	
	/**
	 * Create an instance of Box to test. 
	 */
	protected void setUp() throws Exception {
	super.setUp();
	mySkybox = new Skybox();
	 }
	
	/**
	 * Test the getPosition method.
	 * @throws Exception
	 */
	public void testGetPosition() throws Exception {
	Vector3f dummy = mySkybox.getPosition();
	/*assertTrue(dummy.x == 0);
	assertTrue(dummy.y == 0);
	assertTrue(dummy.z == 0);*/
	//TODO: write proper tests;
	 }
	
	/**
	 * Test the getRotation method.
	 * @throws Exception
	 */
	public void testGetRotation() throws Exception {
	Vector4f dummy = mySkybox.getRotation();
	/*assertTrue(dummy.w == 0.0f);
	assertTrue(dummy.x == 1.0f);
	assertTrue(dummy.y == 0.0f);
	assertTrue(dummy.z == 0.5f);*/
	//TODO: write proper tests;
	 }
	
	/**
	 * Test the getScale method.
	 * @throws Exception
	 */
	public void testGetScale() throws Exception {
	Vector3f dummy = mySkybox.getScale();
	/*assertTrue(dummy.x == 0.0f);
	assertTrue(dummy.y == 0.0f);
	assertTrue(dummy.z == 0.0f);*/
	//TODO: write proper tests;
	 }
	
	/**
	 * Dispose of the created resource.
	 */
	protected void tearDown() throws Exception {
	super.tearDown();
	 }
}
