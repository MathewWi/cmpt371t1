package ca.sandstorm.luminance.test.gameobject;
import android.test.AndroidTestCase;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import ca.sandstorm.luminance.gameobject.Mirror;
import ca.sandstorm.luminance.gameobject.RenderType;

/**
 * Testing of the Mirror class of the gameobject package
 * @author Martina Nagy
 *
 */
public class MirrorTest extends AndroidTestCase {
	Mirror myMirror;

	/**
	 * Create an instance of Box to test. 
	 */
	protected void setUp() throws Exception {
	super.setUp();
	myMirror = new Mirror(new Vector3f(0,0,0), 0.5f);
	 }

	/**
	 * Test the update method.
	 * @throws Exception
	 */
	public void testUpdate() throws Exception {
	//assertTrue(myMirror.Update());
	//TODO: write proper tests
	 }
	
	/**
	 * Test the destroy method.
	 * @throws Exception
	 */
	public void testDestroy() throws Exception {
	//assertTrue(myMirror.destroy());
	//TODO: write proper tests
	 }
	
	/**
	 * Test the getPosition method.
	 * @throws Exception
	 */
	public void testGetPosition() throws Exception {
	Vector3f dummy = myMirror.getPosition();
	assertTrue(dummy.x == 0);
	assertTrue(dummy.y == 0);
	assertTrue(dummy.z == 0);
	 }
	
	/**
	 * Test the getRotation method.
	 * @throws Exception
	 */
	public void testGetRotation() throws Exception {
	Vector4f dummy = myMirror.getRotation();
	assertTrue(dummy.w == 0.5f);
	assertTrue(dummy.x == 0.0f);
	assertTrue(dummy.y == 1.0f);
	assertTrue(dummy.z == 0.0f);
	 }
	
	/**
	 * Test the getScale method.
	 * @throws Exception
	 */
	public void testGetScale() throws Exception {
	Vector3f dummy = myMirror.getScale();
	assertTrue(dummy.x == 0.1f);
	assertTrue(dummy.y == 0.5f);
	assertTrue(dummy.z == 0.5f);
	 }
	
	/**
	 * Test the getRenderable method.
	 * @throws Exception
	 */
	public void testGetRenderable() throws Exception {
	assertNotNull(myMirror.getRenderable());
	 }
	
	/**
	 * Test the getTexture() method.
	 * @throws Exception
	 */
	public void testGetTexture() throws Exception {
	assertNotNull(myMirror.getTexture());
 	 }
	
	/**
	 * Test the getRenderType() method.
	 * @throws Exception
	 */
	public void testGetRenderType() throws Exception {
	RenderType dummy = myMirror.getRenderType();
	assertTrue(dummy == RenderType.Normal);
	 }
	
	/**
	 * Dispose of the created resource.
	 */
	protected void tearDown() throws Exception {
	super.tearDown();
	 }
}
