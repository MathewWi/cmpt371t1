package ca.sandstorm.luminance.test.level;

import ca.sandstorm.luminance.level.XmlLevelBrick;
import android.test.AndroidTestCase;

/**
 * Testing of the XmlLevelBrick Class
 * @author lianghuang
 *
 */
public class XmlLevelBrickTest extends AndroidTestCase {
	
	private XmlLevelBrick tBrick;
	
	protected void setUp() throws Exception {
		super.setUp();

//		tBrick = new XmlLevelBrick();
	}
	
	public void testXmlLevelBrick(){
		assertNotNull(tBrick);
	}
	
	public void testGetId(){
		assertTrue(XmlLevelBrick.getId().equals("brick"));
	}
}
