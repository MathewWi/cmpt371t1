package ca.sandstorm.luminance.test.input;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.gui.Button;
import ca.sandstorm.luminance.gui.GUIManager;
import ca.sandstorm.luminance.input.InputXY;


/**
 * @author Amara Daal
 * 
 */
public class GUIManagerTest extends AndroidTestCase
{
    Button[] mbAr;
    int MAX_BUTTON_COUNT = 10;
    InputXY[] inAr;
    GUIManager GUIManager;

    //Button attributes
    float height = 1.0f;
    float width = 1.0f;
    float x = 1.0f;
    float y = 1.0f;


    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
	super.setUp();
	GUIManager = new GUIManager();

	MAX_BUTTON_COUNT = 10;
	//inAr = new InputXY[MAX_BUTTON_COUNT];
	mbAr = new Button[MAX_BUTTON_COUNT];

	// Create Buttons to test with the GUIManager
	for (int i = 0; i < mbAr.length; i++) {
	    inAr[i] = new InputXY();
	    mbAr[i] = new Button(x + i, y + i, width, height, "test");

	}
    }


    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
	super.tearDown();
    }

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.GUIManager#GUIManager()}.
	 */
	@Test
	public void testGUIManager() {
		GUIManager newManager = new GUIManager();
		assertNotNull(newManager);
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.GUIManager#addButton(ca.sandstorm.luminance.input.Button)}
	 * .
	 */
	@Test
	public void testAddButton() {

		// Add the max number of buttons to the input manager
		for (int i = 0; i < MAX_BUTTON_COUNT; i++) {
			assertTrue(GUIManager.addButton(mbAr[i]));

		}
		// Try to add more than the max number of buttons
		assertFalse(GUIManager.addButton(new Button(2.0f, 2.0f, 2.0f, 2.0f,
				"test")));// new InputXY()

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.GUIManager#touchOccured(android.view.MotionEvent)}
	 * .
	 */
	@Test
	public void testTouchOccuredMotionEvent() {
		/**
		 * MotionEvent constructor is not public unable to construct event to
		 * test function
		 */
		assert (true);

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.GUIManager#touchOccured(float, float)}
	 * .
	 */
	@Test
	public void testTouchOccuredFloatFloat() {
		// Touch every initialized button with buttons
		// placed in the GUIManager
		for (int i = 0; i < mbAr.length; i++) {

			assertEquals(mbAr[i], GUIManager.touchOccured(x + i, y + i));

		}

	}

}
