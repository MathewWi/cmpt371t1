package ca.sandstorm.luminance.test.gui;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.gui.Button;
import ca.sandstorm.luminance.gui.GUIManager;

/**
 * @author Amara Daal
 * 
 */
public class GUIManagerTest extends AndroidTestCase {

	GUIManager guiManager;
	int MAX_BUTTON_COUNT = 10;
	Button[] button = new Button[MAX_BUTTON_COUNT];

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		guiManager = new GUIManager();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.gui.GUIManager#GUIManager()}.
	 */
	public void testGUIManager() {
		guiManager = new GUIManager();
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.gui.GUIManager#getNumberOfButtons()}.
	 */
	public void testGetNumberOfButtons() {
		// assertEquals(MAX_BUTTON_COUNT, guiManager.getNumberOfButtons());
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.gui.GUIManager#addButton(ca.sandstorm.luminance.gui.Button)}
	 * .
	 */
	public void testAddButton() {
		int x = 1;
		int y = 1;
		int width = 1;
		int height = 1;

		// Add the maximum amount of buttons
		for (int i = 0; i < MAX_BUTTON_COUNT; i++) {

			button[i] = new Button(x, y, width, height, Integer.toString(i));

			assertTrue(guiManager.addButton(button[i]));

			// position buttons in diagonal
			x++;
			y++;
			/**
			 * x x x
			 */
		}
		// Try to add an extra button
		assertFalse(guiManager.addButton(new Button(1, 1, 10, 10, "test2")));

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.gui.GUIManager#touchOccured(android.view.MotionEvent)}
	 * .
	 */
	public void testTouchOccuredMotionEvent() {
		// Can't test MotionEvent constructor is private
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.gui.GUIManager#touchOccured(float, float)}.
	 * NOTE: Talk to Kumaryan crazy if statement
	 */
	public void testTouchOccuredFloatFloat() {
		int x = 1;
		int y = 1;
		// float compensatedY = 50; must add 50
		Button testButtonHolder;

		// Add the maximum amount of buttons
		for (int i = 0; i < MAX_BUTTON_COUNT; i++) {

			testButtonHolder = guiManager.touchOccured(x, y);

			assertNotNull(testButtonHolder);
			System.out.println("1");

			assertTrue(testButtonHolder.getTitle().equals(button[i].getTitle()));

			// position buttons in diagonal
			x++;
			y++;
			/**
			 * x x x
			 */
		}

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.gui.GUIManager#update(javax.microedition.khronos.opengles.GL10)}
	 * .
	 */
	public void testUpdate() {
		// OpenGL
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.gui.GUIManager#draw(javax.microedition.khronos.opengles.GL10)}
	 * .
	 */
	public void testDraw() {
		// OpenGL
	}

}
