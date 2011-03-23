package ca.sandstorm.luminance.test.level;

import ca.sandstorm.luminance.level.XmlLevelTool;
import android.test.AndroidTestCase;

/**
 * Testing of XmlLevelTool Class
 * @author lianghuang
 *
 */
public class XmlLevelToolTest extends AndroidTestCase {
	
	private XmlLevelTool tToolMirror, tToolPrism, tToolOut;
	
	protected void setUp() throws Exception {
		super.setUp();

		tToolMirror = new XmlLevelTool("mirror", 4);
		tToolPrism = new XmlLevelTool("prism", 4);
		
		try {
			tToolOut = new XmlLevelTool("Herp", -3);
		} catch (Exception e) {
			//Should remain null;
		}
	}
	
	public void testXmlLevelTool(){
		assertNotNull(tToolMirror);
		assertNotNull(tToolPrism);
		assertNull(tToolOut);
	}

	public void testGetType() throws Exception {
		assert(tToolMirror.getType().equals("mirror"));
		assert(tToolPrism.getType().equals("prism"));
	}

	public void testGetCount() throws Exception {
		assert(tToolMirror.getCount() == 4);
		assert(tToolPrism.getCount() == 4);
	}

	public void testIsValidType() throws Exception {
		assertTrue(tToolMirror.isValidType("mirror"));
		assertTrue(tToolMirror.isValidType("prism"));
		assertFalse(tToolMirror.isValidType(""));
		assertFalse(tToolMirror.isValidType("Herp"));
	}

}
