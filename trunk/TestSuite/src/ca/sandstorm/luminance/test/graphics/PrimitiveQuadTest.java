package ca.sandstorm.luminance.test.graphics;

import javax.vecmath.Vector3f;

import ca.sandstorm.luminance.graphics.*;
import android.test.AndroidTestCase;

/**
 * Testing of the PrimitiveQuad class
 * @author Chet
 *
 */
public class PrimitiveQuadTest extends AndroidTestCase {
	
	private PrimitiveQuad quad;

	protected void setUp() throws Exception {
		super.setUp();
		quad = new PrimitiveQuad(new Vector3f(0.0f,0.0f,0.0f), new Vector3f(0.0f,0.0f,0.0f));
	}
	
	/*
	 * Testing of the constructor
	 */
	public void testPrimitiveBox() throws Exception {
		assert(quad != null);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}