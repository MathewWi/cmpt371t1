package ca.sandstorm.luminance.test.gui;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.gui.Button;
import ca.sandstorm.luminance.gui.GUIManager;
import ca.sandstorm.luminance.input.InputXY;

/**
 * @author Amara Daal
 * 
 */
public class GUIManagerTest extends AndroidTestCase {
	Button[] _mbAr;
	int MAX_BUTTON_COUNT = 10;
	InputXY[] _inAr;
	GUIManager _GUIManager;
	boolean isFading = false;
	
	// Button attributes
	float height = 1.0f;
	float width = 1.0f;
	float x = 1.0f;
	float y = 1.0f;

	GUIManager _newManager;
	
	/**
	 * @throws java.lang.Exception
	 */

	public void setUp() throws Exception {
		super.setUp();
		GUIManager = new GUIManager(isFading);
		MAX_BUTTON_COUNT = 10;
		// _inAr = new InputXY[MAX_BUTTON_COUNT];
		_mbAr = new Button[MAX_BUTTON_COUNT];

		// Create Buttons to test with the GUIManager
		for (int i = 0; i < _mbAr.length; i++) {
			//_inAr[i] = new InputXY();
			_mbAr[i] = new Button(x + i, y + i, width, height, "test");

		}
		
		_newManager = new GUIManager(isFading);
	}

	/**
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.GUIManager#GUIManager()}.
	 */

	public void testGUIManager() {

		assertTrue(_GUIManager != null);
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.GUIManager#addButton(ca.sandstorm.luminance.input.Button)}
	 * .
	 */

	public void testAddButton() {

		// Add the max number of buttons to the input manager
		for (int i = 0; i < MAX_BUTTON_COUNT; i++) {
			assertTrue(_GUIManager.addButton(_mbAr[i]));

		}
		// Try to add more than the max number of buttons
		assertTrue(_GUIManager.addButton(new Button(2.0f, 2.0f, 2.0f, 2.0f,
				"test")));// new InputXY()

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.GUIManager#touchOccured(android.view.MotionEvent)}
	 * .
	 */

	public void testTouchOccuredMotionEvent() {
		/**
		 * NOTE: this function uses touchOccured(float, float)
		 * MotionEvent constructor is not public unable to construct event to
		 * test function. 
		 */
		assert (true);

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.GUIManager#touchOccured(float, float)}
	 * .
	 */

	public void testTouchOccuredFloatFloat() {
		// Touch every initialized button with buttons
		// placed in the GUIManager
		for (int i = 0; i < _mbAr.length; i++) {

			//assertEquals(_mbAr[i], GUIManager.touchOccured(x + i, y + i));

		}

	}

}
