package ca.sandstorm.luminance.test.level;

import ca.sandstorm.luminance.level.XmlLevelPrism;
import android.test.AndroidTestCase;

public class XmlLevelPrismTest extends AndroidTestCase {
	
	private XmlLevelPrism tPrism;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		tPrism = new XmlLevelPrism(5);
	}
	
	public void testXmlLevelPrism(){
		assertNotNull(tPrism);
	}
	
	public void testGetId(){
		assert(XmlLevelPrism.getId().equals("prism"));
	}
}
