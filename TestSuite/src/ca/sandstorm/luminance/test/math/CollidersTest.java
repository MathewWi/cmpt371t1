package ca.sandstorm.luminance.test.math;

import javax.vecmath.Vector3f;

import ca.sandstorm.luminance.math.Colliders;
import android.test.AndroidTestCase;

/**
 * Testing of the Colliders class
 * @author Chet W Collins
 *
 */
public class CollidersTest extends AndroidTestCase {

	/*
	 * Setting up instances to use for testing
	 * (non-Javadoc)
	 * @see android.test.AndroidTestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/*
	 * Testing of the dotProduct() method
	 */
	public static void testDotProduct() throws Exception {
		// Test coordinates
		float x1 = (float) 1.5;
		float y1 = (float) 3.5;
		float z1 = (float) 4.5;
		float x2 = (float) 2.5;
		float y2 = (float) 6.5;
		float z2 = (float) 11.5;
		// Test vectors
		Vector3f v1 = new Vector3f();
		Vector3f v2 = new Vector3f();
		v1.set(x1, y1, z1);
		v2.set(x2, y2, z2);
		// Make sure the result of dotProduct() == x1*x2 + y1*y2 + z1*z2
		try{
			Double result = Colliders.dotProduct(v1, v2);
			Double actual = (double) (x1*x2 + y1*y2 + z1*z2);
			assertTrue(result.equals(actual));
		}
		catch(Exception ex){}
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
