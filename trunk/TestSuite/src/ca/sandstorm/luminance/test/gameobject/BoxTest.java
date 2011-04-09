package ca.sandstorm.luminance.test.gameobject;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.gameobject.Box;
import ca.sandstorm.luminance.gameobject.Light;
import ca.sandstorm.luminance.gameobject.LightBeam;
import ca.sandstorm.luminance.gameobject.LightBeamCollection;
import ca.sandstorm.luminance.gameobject.RenderType;

/**
 * Testing of the Box class of the gameobject package
 * 
 * @author Martina Nagy
 * 
 */
public class BoxTest extends AndroidTestCase {
	Box _myBox;

	/**
	 * Create an instance of Box to test.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		_myBox = new Box(new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0.5f,
				0.5f, 0.5f));
	}

	/**
	 * Test the getRenderable method.
	 * 
	 * @throws Exception
	 */
	public void testGetRenderable() throws Exception {
		assertTrue(_myBox.getRenderable() != null);
	}

	/**
	 * Test the getTexture() method.
	 * 
	 * @throws Exception
	 */
	public void testGetTexture() throws Exception {
		assertNotNull(_myBox.getTexture());
	}

	/**
	 * Test the getPosition() method.
	 * 
	 * @throws Exception
	 */
	public void testGetPosition() throws Exception {
		Vector3f dummy = _myBox.getPosition();
		assertTrue(dummy.x == 0.5f);
		assertTrue(dummy.y == 0.5f);
		assertTrue(dummy.z == 0.5f);
	}

	/**
	 * Test the getRotation() method.
	 * 
	 * @throws Exception
	 */
	public void testGetRotation() throws Exception {
		Vector4f dummy = _myBox.getRotation();
		assertTrue(dummy.w == 0.0f);
		assertTrue(dummy.x == 0.0f);
		assertTrue(dummy.y == 0.0f);
		assertTrue(dummy.z == 0.0f);
	}

	/**
	 * Test the getScale() method.
	 * 
	 * @throws Exception
	 */
	public void testGetScale() throws Exception {
		Vector3f dummy = _myBox.getScale();
		assertTrue(dummy.x == 0.5f);
		assertTrue(dummy.y == 0.5f);
		assertTrue(dummy.z == 0.5f);
	}

	/**
	 * Test the getRenderType() method.
	 * 
	 * @throws Exception
	 */
	public void testGetRenderType() throws Exception {
		RenderType dummy = _myBox.getRenderType();
		assertTrue(dummy == RenderType.Normal);
	}
	
	/**
	 * Test the getCollisionSphere() method
	 * 
	 * @throws Exception
	 */
	public void testGetCollisionSphere() throws Exception
	{
		assertNotNull(_myBox.getCollisionSphere());	
	}
	
	/**
	 * Test the beamInteract method
	 */
	public void testBeamInteract()
	{
		LightBeamCollection lbc = new LightBeamCollection();
		LightBeam lb = new LightBeam();
		Light l = new Light(0, 0, 0, 0, 0, 0, 5, 0);
		lb.add(0, l);
		lbc.add(0, lb);
		
		_myBox.beamInteract(lbc, 0, 0);
		
		assertTrue(l.getEndTouchedObject().equals(_myBox));
	}

	/**
	 * Dispose of the created resource.
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
