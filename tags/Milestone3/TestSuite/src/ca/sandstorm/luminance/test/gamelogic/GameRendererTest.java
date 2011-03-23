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
	
	private GameRenderer gr;
	private Box tBox;
	private Mirror tMirror;
	private Prism tPrism;

	protected void setUp() throws Exception {
		super.setUp();
		gr = new GameRenderer();
		
		tBox = new Box(new Vector3f(3,2,1), new Vector3f(4,3,2));
		tMirror = new Mirror(new Vector3f(1,4,4));
		
		Vector3f position = new Vector3f(5,5,5);
		Vector3f rotation = new Vector3f(5, 5, 5);
		tPrism = new Prism(position, rotation);
	}

	// Need to use coverage testing to see if it gets all cases
	public void testAdd() throws Exception {
		gr.add(new Box(new Vector3f(1,2,2), new Vector3f(2,2,2)));
		gr.add(new Mirror(new Vector3f(3,3,3)));
		gr.add(new Prism(new Vector3f(4,4,4), new Vector3f(4, 4, 4)));
		
		gr.add(tBox);
		gr.add(tMirror);
		
		assertTrue(gr != null);
	}

	// Same as add. Use coverage testing.
	public void testRemove() throws Exception {
		gr.remove(new Box(new Vector3f(1,2,2), new Vector3f(2,2,2)));
		gr.remove(new Mirror(new Vector3f(3,3,3)));
		gr.remove(new Prism(new Vector3f(4,4,4),  new Vector3f(4, 4, 4)));
		
		gr.remove(tBox);
		gr.remove(tMirror);
		gr.remove(tPrism);
	}

	public void testGetBox() throws Exception {
		assertTrue(gr.getBox() != null);
	}

	public void testGetSphere() throws Exception {
		assertTrue(gr.getSphere() != null);
	}

	public void testGetPrism() throws Exception {
		assertTrue(gr.getPrism() != null);
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
