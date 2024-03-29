package ca.sandstorm.luminance.test.graphics;

import ca.sandstorm.luminance.graphics.PrimitiveBox;
import android.test.AndroidTestCase;

/**
 * Testing of the PrimitiveBox class
 * @author Chet
 *
 */
public class PrimitiveBoxTest extends AndroidTestCase {
	
	private PrimitiveBox _box;

	protected void setUp() throws Exception {
		super.setUp();
		_box = new PrimitiveBox();
	}
	
	/*
	 * Testing of the constructor
	 */
	public void testPrimitiveBox() throws Exception {
		assert(_box != null);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
