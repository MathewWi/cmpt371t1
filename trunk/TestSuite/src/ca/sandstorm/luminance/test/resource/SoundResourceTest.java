package ca.sandstorm.luminance.test.resource;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.resources.SoundResource;

/**
 * Testing of the SoundResource class
 * 
 * @author Chet W Collins
 * 
 */
public class SoundResourceTest extends AndroidTestCase {

	private SoundResource soundResource;

	/**
	 * Set up an instance to test with
	 */
	protected void setUp() throws Exception {
		super.setUp();
		soundResource = new SoundResource("soundRes", 1);
	}

	/**
	 * Test the getName() method
	 * 
	 * @throws Exception
	 */
	public void testGetName() throws Exception {

		assertTrue(soundResource.getName().equals("soundRes"));
		assertFalse(soundResource.getName().equals(""));
		assertFalse(soundResource.getName() == null);
	}

	/**
	 * Test the getSound() method
	 * 
	 * @throws Exception
	 */
	public void testGetSound() throws Exception {

		assertTrue(soundResource.getSound() == 1);
	}

	/**
	 * Dispose of the created resource
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		soundResource.dispose();
	}
}
