package ca.sandstorm.luminance.test.resource;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.resources.TextResource;

/**
 * Testing of the TextResource class
 * 
 * @author Chet W Collins
 * 
 */
public class TextResourceTest extends AndroidTestCase {
	TextResource textRes;

	protected void setUp() throws Exception {
		super.setUp();
		String test = "Hello, World!";
		textRes = new TextResource("text1", test.getBytes());
	}

	/**
	 * Test the getText() method
	 * 
	 * @throws Exception
	 */
	public void testText() throws Exception {

		String hello = "Hello, World!";
		assertTrue(textRes.getText().equals(hello));
		assertFalse(textRes.getText().equals(""));
	}

	/**
	 * Dispose of the created resource
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		textRes.dispose();
	}
}
