package ca.sandstorm.luminance.test.input;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.input.InputTouchScreen;

public class InputTouchScreenTest extends AndroidTestCase{
	InputTouchScreen _its;
	
	protected void setUp() throws Exception {
		_its = new InputTouchScreen();
		super.setUp();
	}
	
	public void testInputTouchScreen() throws Exception {
		assertNotNull(_its);
	}
	
}
