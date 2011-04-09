package ca.sandstorm.luminance.test.level;

import ca.sandstorm.luminance.level.XmlLevelPrism;
import android.test.AndroidTestCase;

/**
 * Testing of XmlLevelPrism Class
 * @author lianghuang
 *
 */
public class XmlLevelPrismTest extends AndroidTestCase {
	
	private XmlLevelPrism _tPrism;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		_tPrism = new XmlLevelPrism(5);
	}
	
	public void testXmlLevelPrism(){
		assertNotNull(_tPrism);
	}
	
	public void testGetId(){
		assert(XmlLevelPrism.getId().equals("prism"));
	}
}
