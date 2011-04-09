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
	private ImageResource _imResource;
	private Bitmap _bMap;

	/**
	 * Set up an instance to test with
	 */
	protected void setUp() throws Exception {
		super.setUp();
		_bMap = BitmapFactory.decodeFile("skyBack.jpg");
		_imResource = new ImageResource("imageRes", _bMap);
	}

	/**
	 * Test the getName() method
	 * 
	 * @throws Exception
	 */
	public void testGetName() throws Exception {
		
		assertTrue(_imResource.getName().equals("imageRes"));
		assertFalse(_imResource.getName().equals(""));
		assertFalse(_imResource.getName() == null);
	}

	/**
	 * Test the getMemorySize() method
	 * 
	 * @throws Exception
	 */
	public void testGetMemorySize() throws Exception {

		assertTrue(_imResource.getMemorySize() == 0);
	}
}
