package ca.sandstorm.luminance.test.graphics;

import ca.sandstorm.luminance.graphics.PrimitiveBox;
import android.test.AndroidTestCase;

/**
 * Testing of the PrimitiveBox class
 * @author Chet
 *
 */
public class PrimitiveBoxTest extends AndroidTestCase {
	
	private PrimitiveBox box;

	protected void setUp() throws Exception {
		super.setUp();
		box = new PrimitiveBox();
	}
	
	/*
	 * Testing of the constructor
	 */
	public void testPrimitiveBox() throws Exception {
		assert(box != null);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
