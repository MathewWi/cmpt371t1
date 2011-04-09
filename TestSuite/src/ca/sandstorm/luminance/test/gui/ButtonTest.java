package ca.sandstorm.luminance.test.gui;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.gui.Button;

/**
 * Testing _button functionality
 * 
 * @author Amara Daal
 * 
 */
public class ButtonTest extends AndroidTestCase {
	Button _button;
	float _x, _y, width, height;
	String title;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		_x = 10;
		_y = 10;
		width = 1000000;
		height = 1000000;
		title = "testButton";
		_button = new Button(_x, _y, width, height, title);
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

		assertNotNull(_button);
	}


	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Button#setX(float)}.
	 */
	public void testGetSetX() {
		_x = 1;
		_button.setX(_x);

		assertTrue(_x == _button.getX());
		//assertEquals(x, _button.getX(), .001);

	}


	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Button#setY(float)}.
	 */
	public void testGetSetY() {
		_y = 1;
		_button.setY(_y);

		assertTrue(_y == _button.getY());
		//assertEquals(y, _button.getY(), .001);
	}


	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Button#setWidth(float)}
	 * .
	 */
	public void testGetSetWidth() {
		width = 1;
		_button.setWidth(width);

		assertTrue(width == _button.getWidth());
		//assertEquals(width, _button.getWidth(), .001);
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.gui.Button#setHeight(float)}.
	 */
	public void testGetSetHeight() {
		height = 1;
		_button.setHeight(height);

		assertTrue(height == _button.getHeight());
		//assertEquals(height, _button.getHeight(), .001);
	}

}
