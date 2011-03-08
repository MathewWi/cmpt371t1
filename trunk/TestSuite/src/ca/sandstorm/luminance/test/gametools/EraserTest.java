package ca.sandstorm.luminance.test.gametools;

import ca.sandstorm.luminance.gametools.Eraser;
import android.test.AndroidTestCase;

/**
 * Testing of the Eraser Class
 * @author lianghuang
 *
 */
public class EraserTest extends AndroidTestCase {
	private Eraser tEraser;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		tEraser = new Eraser();

	}
	
	public void testEraser(){
		assertTrue(tEraser != null);
	}
}
