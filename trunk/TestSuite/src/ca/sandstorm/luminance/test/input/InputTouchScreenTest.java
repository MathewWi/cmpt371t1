package ca.sandstorm.luminance.test.input;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.input.InputTouchScreen;

public class InputTouchScreenTest extends AndroidTestCase{
	InputTouchScreen its;
	
	protected void setUp() throws Exception {
		its = new InputTouchScreen();
		super.setUp();
	}
	
	public void testInputTouchScreen() throws Exception {
		assertNotNull(its);
	}
	
}
