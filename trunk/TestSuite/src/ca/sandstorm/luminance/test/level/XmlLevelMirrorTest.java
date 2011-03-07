package ca.sandstorm.luminance.test.level;

import ca.sandstorm.luminance.level.XmlLevelMirror;
import android.test.AndroidTestCase;

public class XmlLevelMirrorTest extends AndroidTestCase {

	private XmlLevelMirror tMirror;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		tMirror = new XmlLevelMirror(5);
	}
	
	public void testXmlLevelMirror(){
		assertNotNull(tMirror);
	}
	
	public void testGetId(){
		assert(XmlLevelMirror.getId().equals("mirror"));
	}
}
