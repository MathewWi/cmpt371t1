package ca.sandstorm.luminance.test.resource;

import java.nio.ByteBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.resources.TextureResource;

/**
 * Testing of the TextureResource class
 * 
 * @author Chet W Collins
 * 
 */
public class TextureResourceTest extends AndroidTestCase {
	private TextureResource texResource;

	/**
	 * Set up an instance to test with
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		GL10 gl = null;

		texResource = new TextureResource("texture1", 1, gl);
	}

	/**
	 * Test the getName() method
	 * 
	 * @throws Exception
	 */
	public void testGetName() throws Exception {

		assertTrue(texResource.getName().equals("texture1"));
		assertFalse(texResource.getName().equals(""));
		assertFalse(texResource.getName() == null);
	}

	/**
	 * Test the getTexture() method
	 * 
	 * @throws Exception
	 */
	public void testGetSound() throws Exception {

		assertTrue(texResource.getTexture() == 1);
	}

	/**
	 * Dispose of the created resource
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		texResource.dispose();
	}
}
