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

	Label label;
	String text;
	float x;
	float y;
	float width;
	float height;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		text = "test";
		x = 1.0f;
		y = 1.0f;
		width = 1.0f;
		height = 1.0f;
		
		label = new Label(x, y, width, height, text);

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

		assertNotNull(label);

	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Label#getX()}.
	 */
	public void testGetX() {

		label.setX(x);
		assertTrue(x == label.getX());
		//assertEquals(x, label.getX(), .001);

	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Label#setX(float)}.
	 */
	public void testSetX() {
		x = 2.0f;
		label.setX(x);
		assertTrue(x == label.getX());
		//assertEquals(x, label.getX(), .001);

	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Label#getY()}.
	 */
	public void testGetY() {

		label.setY(y);
		assertTrue(y == label.getY());
		//assertEquals(y, label.getY(), .001);

	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Label#setY(float)}.
	 */
	public void testSetY() {
		y = 2.0f;
		label.setY(y);
		assertTrue(y == label.getY());
		//assertEquals(y, label.getY(), .001);

	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Label#getWidth()}.
	 */
	public void testGetWidth() {
		label.setWidth(width);
		assertTrue(width == label.getWidth());
		//assertEquals(width, label.getWidth(), .001);

	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Label#setWidth(float)}.
	 */
	public void testSetWidth() {
		width = 2.0f;
		label.setWidth(width);
		assertTrue(width == label.getWidth());
		//assertEquals(width, label.getWidth(), .001);

	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Label#getHeight()}.
	 */
	public void testGetHeight() {
		label.setHeight(height);
		
		assertTrue(height == label.getHeight());
		//assertEquals(height, label.getHeight(), .001);
	}

	/**
	 * Test method for {@link ca.sandstorm.luminance.gui.Label#setHeight(float)}
	 * .
	 */
	public void testSetHeight() {
		height = 2.0f;
		label.setHeight(height);
		assertTrue(height == label.getHeight());
		//assertEquals(height, label.getHeight(), .001);

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
