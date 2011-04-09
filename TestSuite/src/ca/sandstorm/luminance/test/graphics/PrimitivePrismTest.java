package ca.sandstorm.luminance.test.graphics;

import ca.sandstorm.luminance.graphics.PrimitivePrism;
import android.test.AndroidTestCase;

/**
 * Testing of the PrimitivePrism class
 * @author Chet
 *
 */
public class PrimitivePrismTest extends AndroidTestCase {
	
	private PrimitivePrism _prism;

	protected void setUp() throws Exception {
		super.setUp();
		_prism = new PrimitivePrism();
	}
	
	/*
	 * Testing of the constructor
	 */
	public void testPrimitivePrism() throws Exception {
		assert(_prism != null);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
