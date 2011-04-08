package ca.sandstorm.luminance.test.gui;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.gui.Button;

/**
 * Testing button functionality
 * 
 * @author Amara Daal
 * 
 */
public class ButtonTest extends AndroidTestCase {
	Button button;
	float x, y, width, height;
	String title;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		x = 10;
		y = 10;
		width = 1000000;
		height = 1000000;
		title = "testButton";
		button = new Button(x, y, width, height, title);
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
	 * {@link ca.sandstorm.luminance.gui.Button#Button(float, float, float, float, java.lang.String)}
	 * .
	 */
	public void testButton() {

		assertNotNull(button);
	}


	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Button#setX(float)}.
	 */
	public void testGetSetX() {
		x = 1;
		button.setX(x);

		assertTrue(x == button.getX());
		//assertEquals(x, button.getX(), .001);

	}


	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Button#setY(float)}.
	 */
	public void testGetSetY() {
		y = 1;
		button.setY(y);

		assertTrue(y == button.getY());
		//assertEquals(y, button.getY(), .001);
	}


	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Button#setWidth(float)}
	 * .
	 */
	public void testGetSetWidth() {
		width = 1;
		button.setWidth(width);

		assertTrue(width == button.getWidth());
		//assertEquals(width, button.getWidth(), .001);
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.gui.Button#setHeight(float)}.
	 */
	public void testGetSetHeight() {
		height = 1;
		button.setHeight(height);

		assertTrue(height == button.getHeight());
		//assertEquals(height, button.getHeight(), .001);
	}

}
