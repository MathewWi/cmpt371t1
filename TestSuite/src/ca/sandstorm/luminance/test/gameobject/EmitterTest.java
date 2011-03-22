package ca.sandstorm.luminance.test.gameobject;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.gameobject.Light;
import ca.sandstorm.luminance.gameobject.LightBeam;
import ca.sandstorm.luminance.gameobject.LightBeamCollection;
import ca.sandstorm.luminance.gameobject.Emitter;
import ca.sandstorm.luminance.gameobject.RenderType;

/**
 * Testing of the Emitter class of the gameobject package
 * 
 * @author Martina Nagy
 * 
 */
public class EmitterTest extends AndroidTestCase {
	Emitter myEmitter;

	/**
	 * Create an instance of Box to test.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		myEmitter = new Emitter(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0);
	}
	
	/**
	 * Test the getRenderable method.
	 * 
	 * @throws Exception
	 */
	public void testGetRenderable() throws Exception {
		assertTrue(myEmitter.getRenderable() != null);
	}
	
	/**
	 * Test the getTexture() method.
	 * 
	 * @throws Exception
	 */
	public void testGetTexture() throws Exception {
		assertNotNull(myEmitter.getTexture());
	}
	
	/**
	 * Test the getRenderType() method.
	 * 
	 * @throws Exception
	 */
	public void testGetRenderType() throws Exception {
		RenderType dummy = myEmitter.getRenderType();
		assertTrue(dummy == RenderType.Normal);
	}
	
	/**
	 * Test the getCollisionSphere() method
	 * 
	 * @throws Exception
	 */
	public void testGetCollisionSphere()
	{
		assertNotNull(myEmitter.getCollisionSphere());
	}
	
	/**
	 * Test the beamInteract() method
	 * 
	 * @throws Exception
	 */
	public void testBeamInteract()
	{
		
	}

	/**
	 * Test the update method.
	 * 
	 * @throws Exception
	 */
	public void testUpdate() throws Exception {
		// assertTrue(myEmitter.Update());
		// TODO: write proper tests
	}

	/**
	 * Test the destroy method.
	 * 
	 * @throws Exception
	 */
	public void testDestroy() throws Exception {
		// assertTrue(myEmitter.destroy());
		// TODO: write proper tests
	}

	/**
	 * Test the getPosition method.
	 * 
	 * @throws Exception
	 */
	public void testGetPosition() throws Exception {
		Vector3f dummy = myEmitter.getPosition();
		assertTrue(dummy.x == 0);
		assertTrue(dummy.y == 0);
		assertTrue(dummy.z == 0);
	}

	/**
	 * Test the getRotation method.
	 * 
	 * @throws Exception
	 */
	public void testGetRotation() throws Exception {
		Vector4f dummy = myEmitter.getRotation();
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
		Vector3f dummy = myEmitter.getScale();
		assertTrue(dummy.x == 0.15f);
		assertTrue(dummy.y == 0.15f);
		assertTrue(dummy.z == 0.5f);
	}

	/**
	 * Dispose of the created resource.
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
