package ca.sandstorm.luminance.test.gametools;

import ca.sandstorm.luminance.gametools.PrismTool;
import android.test.AndroidTestCase;

/**
 * Testing of the PrismTool Class
 * @author lianghuang
 *
 */
public class PrismToolTest extends AndroidTestCase {
	
	private PrismTool tPrism;
	protected void setUp() throws Exception {
		super.setUp();

		tPrism = new PrismTool();
	}
	
	public void testPrismTool(){
		assertTrue(tPrism != null);
	}
}
