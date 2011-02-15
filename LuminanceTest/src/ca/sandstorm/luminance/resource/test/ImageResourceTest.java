package ca.sandstorm.luminance.resource.test;

import ca.sandstorm.luminance.resources.ImageResource;
import android.test.AndroidTestCase;

/**
 * Testing of the ImageResource class
 * @author Chet W Collins
 *
 */
public class ImageResourceTest extends AndroidTestCase
{
    private ImageResource imResource;

    /**
     * Set up an instance to test with
     */
    protected void setUp() throws Exception {
	super.setUp();
	imResource = new ImageResource("imageRes", (new Bitmap()));
    }
    
    /**
     * Test the getName() method
     * @throws Exception
     */
    public void testGetName() throws Exception {
	
	assertTrue(imResource.getName().equals("imageRes"));
	assertFalse(imResource.getName().equals(""));
	assertFalse(imResource.getName() == null);
     }
    
    /**
     * Test the getMemorySize() method
     * @throws Exception
     */
    public void testGetMemorySize() throws Exception {
	
	assertTrue(imResource.getMemorySize() == 0);
     }
    
    /**
     * Dispose of the created resource
     */
    protected void tearDown() throws Exception
    {
	super.tearDown();
	imResource.dispose();
    }
}
