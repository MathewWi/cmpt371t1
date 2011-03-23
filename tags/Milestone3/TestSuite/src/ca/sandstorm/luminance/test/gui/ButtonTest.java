package ca.sandstorm.luminance.test.gui;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.gui.Button;

/**
 * Testing button functionality
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
		assertEquals(x, button.getX(), .001);
		assertEquals(y, button.getY(), .001);
		assertEquals(width, button.getWidth(), .001);
		assertEquals(height, button.getHeight(), .001);
		assertTrue(title.equals(button.getTitle()));

		// TODO: Consider testing for null values
		// or change preconditions for functions

	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Button#getX()}.
	 */
	public void testGetX() {
		assertEquals(x, button.getX(), .001);
	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Button#setX(float)}.
	 */
	public void testSetX() {
		x = 1;
		button.setX(x);

		assertEquals(x, button.getX(), .001);

	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Button#getY()}.
	 */
	public void testGetY() {

		assertEquals(y, button.getY(), .001);
	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Button#setY(float)}.
	 */
	public void testSetY() {
		y = 1;
		button.setY(y);

		assertEquals(y, button.getY(), .001);
	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Button#getWidth()}.
	 */
	public void testGetWidth() {

		assertEquals(width, button.getWidth(), .001);
	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Button#setWidth(float)}
	 * .
	 */
	public void testSetWidth() {
		width = 1;
		button.setWidth(width);

		assertEquals(width, button.getWidth(), .001);
	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Button#getHeight()}.
	 */
	public void testGetHeight() {
		assertEquals(height, button.getHeight(), .001);
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.gui.Button#setHeight(float)}.
	 */
	public void testSetHeight() {
		height = 1;
		button.setHeight(height);

		assertEquals(height, button.getHeight(), .001);
	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Button#getTitle()}.
	 */
	public void testGetTitle() {
		assertTrue(title.equals(button.getTitle()));
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.gui.Button#setTitle(java.lang.String)}.
	 */
	public void testSetTitle() {
		title = "test2";
		button.setTitle(title);

		assertTrue(title.equals(button.getTitle()));

	}

}
