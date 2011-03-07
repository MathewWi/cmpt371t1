package ca.sandstorm.luminance.test.gametools;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.gametools.MirrorTool;

/**
 * Testing of the MirrorTool Class
 * @author lianghuang
 *
 */
public class MirrorToolTest extends AndroidTestCase {
	
	private MirrorTool tMirror;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		tMirror = new MirrorTool();

	}
	
	public void testMirrorTool(){
		assertNotNull(tMirror);
	}
}
