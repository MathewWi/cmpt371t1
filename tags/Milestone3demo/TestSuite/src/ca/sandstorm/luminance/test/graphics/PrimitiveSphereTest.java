package ca.sandstorm.luminance.test.graphics;

import ca.sandstorm.luminance.graphics.PrimitiveSphere;
import android.test.AndroidTestCase;

/**
 * Testing of the PrimitiveSphere class
 * @author Chet
 *
 */
public class PrimitiveSphereTest extends AndroidTestCase {
	
	private PrimitiveSphere sphere;

	protected void setUp() throws Exception {
		super.setUp();
		sphere = new PrimitiveSphere();
	}
	
	/*
	 * Testing of the constructor
	 */
	public void testPrimitiveSphere() throws Exception {
		assert(sphere != null);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
