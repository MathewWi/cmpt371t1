package ca.sandstorm.luminance.test.recyclingbin;

import javax.vecmath.Vector3f;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.gameobject.Mirror;


/**
 * Testing of the MirrorTool Class
 * @author lianghuang
 *
 */
public class MirrorToolTest extends AndroidTestCase {
	
	private Mirror tMirror;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		tMirror = new Mirror( new Vector3f(3, 3, 3));

	}
	
	public void testMirrorTool(){
		assertTrue(tMirror != null);
	}
}
