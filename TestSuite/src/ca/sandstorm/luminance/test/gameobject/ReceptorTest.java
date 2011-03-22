package ca.sandstorm.luminance.test.gameobject;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.gameobject.Light;
import ca.sandstorm.luminance.gameobject.LightBeam;
import ca.sandstorm.luminance.gameobject.LightBeamCollection;
import ca.sandstorm.luminance.gameobject.Receptor;
import ca.sandstorm.luminance.gameobject.RenderType;
import ca.sandstorm.luminance.math.Sphere;

/**
 * Testing of the Receptor class of the gameobject package
 * 
 * @author Martina Nagy
 * 
 */
public class ReceptorTest extends AndroidTestCase {
	Receptor myReceptor;

	/**
	 * Create an instance of Box to test.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		myReceptor = new Receptor(new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(
				0.0f, 0.0f, 0.0f));
	}

	/**
	 * Test the setColor method.
	 * 
	 * @throws Exception
	 */
	public void testSetColor() throws Exception {
		myReceptor.setColor(5);
		assertTrue(myReceptor.getColor() == 5);
	}

	/**
	 * Test the getActivated method.
	 * 
	 * @throws Exception
	 */
	public void testGetActiviated()
	{
		assertFalse(myReceptor.getActivated());
	}
	
	/**
	 * Test the getRenderable method.
	 * 
	 * @throws Exception
	 */
	public void testGetRenderable() throws Exception {
		assertTrue(myReceptor.getRenderable() != null);
	}
	
	/**
	 * Test the getTexture() method.
	 * 
	 * @throws Exception
	 */
	public void testGetTexture() throws Exception {
		assertNotNull(myReceptor.getTexture());
	}
	
	/**
	 * Test the getRenderType() method.
	 * 
	 * @throws Exception
	 */
	public void testGetRenderType() throws Exception {
		RenderType dummy = myReceptor.getRenderType();
		assertTrue(dummy == RenderType.Normal);
	}

	/**
	 * Test the getCollisionSphere() method.
	 * 
	 * @throws Exception
	 */
	public void testGetCollisionSphere() throws Exception {
		Sphere dummy = myReceptor.getCollisionSphere();
		assertNotNull(dummy);
	}
	
	/**
	 * Test the beamInteract() method
	 * 
	 * @throws Exception
	 */
	public void testBeamInteract()
	{
		LightBeamCollection lbc = new LightBeamCollection();
		LightBeam lb = new LightBeam();
		Light l = new Light(0, 0, 0, 0, 0, 0, 5, 0);
		lb.add(0, l);
		lbc.add(0, lb);
		myReceptor.setColor(0);
		
		myReceptor.beamInteract(lbc, 0, 0);
		
		//assertTrue(l.getEndTouchedObject().equals(myReceptor));
		assertTrue(myReceptor.getActivated());
	}
	
	/**
	 * Test the update method.
	 * 
	 * @throws Exception
	 */
	public void testUpdate() throws Exception {
		// assertTrue(myReceptor.Update());
		// TODO: write proper tests
	}

	/**
	 * Test the destroy method.
	 * 
	 * @throws Exception
	 */
	public void testDestroy() throws Exception {
		// assertTrue(myReceptor.destroy());
		// TODO: write proper tests
	}

	/**
	 * Test the getPosition method.
	 * 
	 * @throws Exception
	 */
	public void testGetPosition() throws Exception {
		Vector3f dummy = myReceptor.getPosition();
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
		Vector4f dummy = myReceptor.getRotation();
		assertTrue(dummy.w == 0.0f);
		assertTrue(dummy.x == 1.0f);
		assertTrue(dummy.y == 1.0f);
		assertTrue(dummy.z == 1.0f);
	}

	/**
	 * Test the getScale method.
	 * 
	 * @throws Exception
	 */
	public void testGetScale() throws Exception {
		Vector3f dummy = myReceptor.getScale();
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
