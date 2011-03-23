package ca.sandstorm.luminance.test.resource;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.AndroidTestCase;
import ca.sandstorm.luminance.resources.ImageResource;

/**
 * Testing of the ImageResource class
 * 
 * @author Chet W Collins
 * 
 */
public class ImageResourceTest extends AndroidTestCase {
	private ImageResource imResource;
	private Bitmap bMap;

	/**
	 * Set up an instance to test with
	 */
	protected void setUp() throws Exception {
		super.setUp();
		bMap = BitmapFactory.decodeFile("skyBack.jpg");
		imResource = new ImageResource("imageRes", bMap);
	}

	/**
	 * Test the getName() method
	 * 
	 * @throws Exception
	 */
	public void testGetName() throws Exception {
		
		assertTrue(imResource.getName().equals("imageRes"));
		assertFalse(imResource.getName().equals(""));
		assertFalse(imResource.getName() == null);
	}

	/**
	 * Test the getMemorySize() method
	 * 
	 * @throws Exception
	 */
	public void testGetMemorySize() throws Exception {

		assertTrue(imResource.getMemorySize() == 0);
	}
}
