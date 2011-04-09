package ca.sandstorm.luminance.test.resource;

import java.util.Arrays;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.resources.Resource;

/**
 * Testing of the Resource class
 * 
 * @author Chet W Collins
 * 
 */
public class ResourceTest extends AndroidTestCase {
	private Resource _res;
	private Resource _res2;

	/**
	 * Set up an instance to test with
	 */
	protected void setUp() throws Exception {
		super.setUp();
		String test = "Hello, World!";
		String blank = "";
		_res = new Resource("res1", test.getBytes());
		_res2 = new Resource("res2", blank.getBytes());
	}

	/**
	 * Test the getName() method
	 * 
	 * @throws Exception
	 */
	public void testGetName() throws Exception {

		assertTrue(_res.getName().equals("res1"));
		assertFalse(_res.getName().equals(""));
		assertFalse(_res.getName() == null);
	}

	/**
	 * Test the getMemorySize() method
	 * 
	 * @throws Exception
	 */
	public void testGetMemorySize() throws Exception {

		assertTrue(_res.getMemorySize() != 0);
		assertTrue(_res2.getMemorySize() == 0);
	}

	/**
	 * Test the getData() method
	 * 
	 * @throws Exception
	 */
	public void testGetData() throws Exception {
		String foo = "Hello, World!";
		assertTrue(Arrays.equals(foo.getBytes(), _res.getData()));
	}

	/**
	 * Dispose of the created resource
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		_res.dispose();
		_res2.dispose();
	}
}
