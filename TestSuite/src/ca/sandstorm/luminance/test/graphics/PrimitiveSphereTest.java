package ca.sandstorm.luminance.test.graphics;

import ca.sandstorm.luminance.graphics.PrimitiveSphere;
import android.test.AndroidTestCase;

/**
 * Testing of the PrimitiveSphere class
 * @author Chet
 *
 */
public class PrimitiveSphereTest extends AndroidTestCase {
	
	private PrimitiveSphere _sphere;

	protected void setUp() throws Exception {
		super.setUp();
		_sphere = new PrimitiveSphere();
	}
	
	/*
	 * Testing of the constructor
	 */
	public void testPrimitiveSphere() throws Exception {
		assert(_sphere != null);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
