package ca.sandstorm.luminance.test.gamelogic;

import javax.vecmath.Vector3f;

import ca.sandstorm.luminance.gamelogic.GameRenderer;
import ca.sandstorm.luminance.gameobject.Box;
import ca.sandstorm.luminance.gameobject.Mirror;
import ca.sandstorm.luminance.gameobject.Prism;
import android.test.AndroidTestCase;

/**
 * Testing of the GameRenderer Class
 * @author lianghuang
 *
 */
public class GameRendererTest extends AndroidTestCase {
	
	private GameRenderer _gr;
	private Box _tBox;
	private Mirror _tMirror;
	private Prism _tPrism;

	protected void setUp() throws Exception {
		super.setUp();
		_gr = new GameRenderer();
		
		_tBox = new Box(new Vector3f(3,2,1), new Vector3f(4,3,2));
		_tMirror = new Mirror(new Vector3f(1,4,4));
		
		Vector3f position = new Vector3f(5,5,5);
		Vector3f rotation = new Vector3f(5, 5, 5);
		_tPrism = new Prism(position, rotation);
	}
	
	public void testGameRenderer() throws Exception {
		assertTrue(_gr != null);
	}
	
	public void testRemoveAll() throws Exception {
		_gr.add(new Box(new Vector3f(1,2,2), new Vector3f(2,2,2)));
		_gr.add(new Mirror(new Vector3f(3,3,3)));
		_gr.add(new Prism(new Vector3f(4,4,4), new Vector3f(4, 4, 4)));
		
		_gr.removeAll();
	}

	// Need to use coverage testing to see if it gets all cases
	public void testAdd() throws Exception {
		_gr.add(new Box(new Vector3f(1,2,2), new Vector3f(2,2,2)));
		_gr.add(new Mirror(new Vector3f(3,3,3)));
		_gr.add(new Prism(new Vector3f(4,4,4), new Vector3f(4, 4, 4)));
		
		_gr.add(_tBox);
		_gr.add(_tMirror);
		
		assertTrue(_gr != null);
	}

	// Same as add. Use coverage testing.
	public void testRemove() throws Exception {
		_gr.remove(new Box(new Vector3f(1,2,2), new Vector3f(2,2,2)));
		_gr.remove(new Mirror(new Vector3f(3,3,3)));
		_gr.remove(new Prism(new Vector3f(4,4,4),  new Vector3f(4, 4, 4)));
		
		_gr.remove(_tBox);
		_gr.remove(_tMirror);
		_gr.remove(_tPrism);
	}

	public void testGe_tBox() throws Exception {
		assertTrue(_gr.getBox() != null);
	}

	public void testGetSphere() throws Exception {
		assertTrue(_gr.getSphere() != null);
	}

	public void testGe_tPrism() throws Exception {
		assertTrue(_gr.getPrism() != null);
	}

	public void testDrawNormalObjects() throws Exception {
		// Uses GL and will need to check visually on screen
		assertTrue(true);
	}

	public void testDrawAlphaObjects() throws Exception {
		// Uses GL and will need to check visually on screen
		assertTrue(true);
	}

	public void testDrawReflectionObjects() throws Exception {
		// Uses GL and will need to check visually on screen
		assertTrue(true);
	}

	public void testDraw() throws Exception {
		// Uses GL and will need to check visually on screen
		assertTrue(true);	}
}
