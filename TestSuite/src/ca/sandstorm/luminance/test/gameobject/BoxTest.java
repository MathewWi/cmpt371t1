package ca.sandstorm.luminance.test.gameobject;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.gameobject.Box;
import ca.sandstorm.luminance.gameobject.RenderType;

/**
 * Testing of the Box class of the gameobject package
 * 
 * @author Martina Nagy
 * 
 */
public class BoxTest extends AndroidTestCase {
	Box myBox;

	/**
	 * Create an instance of Box to test.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		myBox = new Box(new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0.5f,
				0.5f, 0.5f));
	}

	/**
	 * Test the getRenderable method.
	 * 
	 * @throws Exception
	 */
	public void testGetRenderable() throws Exception {
		assertNotNull(myBox.getRenderable());
	}

	/**
	 * Test the getTexture() method.
	 * 
	 * @throws Exception
	 */
	public void testGetTexture() throws Exception {
		assertNotNull(myBox.getTexture());
	}

	/**
	 * Test the getPosition() method.
	 * 
	 * @throws Exception
	 */
	public void testGetPosition() throws Exception {
		Vector3f dummy = myBox.getPosition();
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
		Vector4f dummy = myBox.getRotation();
		assertTrue(dummy.w == 0.0f);
		assertTrue(dummy.x == 1.0f);
		assertTrue(dummy.y == 1.0f);
		assertTrue(dummy.z == 1.0f);
	}

	/**
	 * Test the getScale() method.
	 * 
	 * @throws Exception
	 */
	public void testGetScale() throws Exception {
		Vector3f dummy = myBox.getScale();
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
		RenderType dummy = myBox.getRenderType();
		assertTrue(dummy == RenderType.Normal);
	}

	/**
	 * Dispose of the created resource.
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
