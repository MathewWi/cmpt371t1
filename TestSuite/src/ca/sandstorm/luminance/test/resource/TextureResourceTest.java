package ca.sandstorm.luminance.test.resource;

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
	private TextureResource _texResource;

	/**
	 * Set up an instance to test with
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		GL10 gl = null;

		_texResource = new TextureResource("texture1", 1, gl);
	}

	/**
	 * Test the getName() method
	 * 
	 * @throws Exception
	 */
	public void testGetName() throws Exception {

		assertTrue(_texResource.getName().equals("texture1"));
		assertFalse(_texResource.getName().equals(""));
		assertFalse(_texResource.getName() == null);
	}

	/**
	 * Test the getTexture() method
	 * 
	 * @throws Exception
	 */
	public void testGetSound() throws Exception {

		assertTrue(_texResource.getTexture() == 1);
	}

	/**
	 * Dispose of the created resource
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		_texResource.dispose();
	}
}
