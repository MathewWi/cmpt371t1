package ca.sandstorm.luminance.test.level;

import ca.sandstorm.luminance.level.XmlLevelBuilder;
import android.test.AndroidTestCase;

public class XmlLevelBuilderTest extends AndroidTestCase{
	XmlLevelBuilder xlb;
	
	protected void setUp() throws Exception {
		xlb = new XmlLevelBuilder();
		super.setUp();
	}
	
	public void testXmlLevelBuilder() throws Exception {
		assertNotNull(xlb);
	}
}
