package ca.sandstorm.luminance.test.gamelogic;

import android.test.AndroidTestCase;

public class GameRendererTest extends AndroidTestCase {
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Add a new object to be drawn on every frame render.
	 * 
	 * @param object
	 *            Renderable object to be added.
	 */
	public void testAdd() throws Exception {
	}

	/**
	 * Remove an object from the automatic draw list.
	 * 
	 * @param object
	 *            Renderable object to remove.
	 */
	public void testRemove() throws Exception {

	}

	/**
	 * Get a box primitive.
	 * 
	 * @return Box primitive
	 */
	public void testGetBox() throws Exception {
	}

	/**
	 * Get a sphere primitive.
	 * 
	 * @return Sphere primitive
	 */
	public void testGetSphere() throws Exception {
	}

	/**
	 * Get a prism primitive.
	 * 
	 * @return Prism primitive
	 */
	public void testGetPrism() throws Exception {
	}

	/**
	 * Draw the normal objects which require no special rendering.
	 * 
	 * @param gl
	 *            OpenGL context
	 */
	public void testDrawNormalObjects() throws Exception {

	}

	/**
	 * Draw the alpha objects which require sorted and special rendering.
	 * 
	 * @param gl
	 *            OpenGL context
	 */
	public void testDrawAlphaObjects() throws Exception {

	}

	/**
	 * Draw the reflection objects which require special rendering.
	 * 
	 * @param gl
	 *            OpenGL context
	 */
	public void testDrawReflectionObjects() throws Exception {

	}

	/**
	 * Render all objects that are being tracked by the renderer.
	 * 
	 * @param gl
	 *            OpenGL context to render with
	 */
	public void testDraw() throws Exception {
	}
}
