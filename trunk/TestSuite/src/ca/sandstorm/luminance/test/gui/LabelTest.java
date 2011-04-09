package ca.sandstorm.luminance.test.gui;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.gui.Label;

/**
 * Testing Label functionality
 * 
 * @author Amara Daal
 * 
 */
public class LabelTest extends AndroidTestCase {

	Label _label;
	String _text;
	float _x;
	float _y;
	float _width;
	float _height;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		_text = "test";
		_x = 1.0f;
		_y = 1.0f;
		_width = 1.0f;
		_height = 1.0f;
		
		_label = new Label(_x, _y, _width, _height, _text);

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
	 * {@link ca.sandstorm.luminance.gui.Label#Label(java.lang.String)}.
	 */
	public void testLabel() {

		assertNotNull(_label);

	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Label#getX()}.
	 */
	public void testGetX() {

		_label.setX(_x);
		assertTrue(_x == _label.getX());
		//assertEquals(x, _label.getX(), .001);

	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Label#setX(float)}.
	 */
	public void testSetX() {
		_x = 2.0f;
		_label.setX(_x);
		assertTrue(_x == _label.getX());
		//assertEquals(x, _label.getX(), .001);

	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Label#getY()}.
	 */
	public void testGetY() {

		_label.setY(_y);
		assertTrue(_y == _label.getY());
		//assertEquals(y, _label.getY(), .001);

	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Label#setY(float)}.
	 */
	public void testSetY() {
		_y = 2.0f;
		_label.setY(_y);
		assertTrue(_y == _label.getY());
		//assertEquals(y, _label.getY(), .001);

	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Label#getWidth()}.
	 */
	public void testGetWidth() {
		_label.setWidth(_width);
		assertTrue(_width == _label.getWidth());
		//assertEquals(_width, _label.getWidth(), .001);

	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Label#setWidth(float)}.
	 */
	public void testSetWidth() {
		_width = 2.0f;
		_label.setWidth(_width);
		assertTrue(_width == _label.getWidth());
		//assertEquals(_width, _label.getWidth(), .001);

	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Label#getHeight()}.
	 */
	public void testGetHeight() {
		_label.setHeight(_height);
		
		assertTrue(_height == _label.getHeight());
		//assertEquals(_height, _label.getHeight(), .001);
	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Label#setHeight(float)}
	 * .
	 */
	public void testSetHeight() {
		_height = 2.0f;
		_label.setHeight(_height);
		assertTrue(_height == _label.getHeight());
		//assertEquals(_height, _label.getHeight(), .001);

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.gui.Label#draw(javax.microedition.khronos.opengles.GL10)}
	 * .
	 */
	public void testDraw() {
		// OpenGL
	}

}
