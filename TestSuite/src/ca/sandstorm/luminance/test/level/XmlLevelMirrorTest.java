package ca.sandstorm.luminance.test.level;

import ca.sandstorm.luminance.level.XmlLevelMirror;
import android.test.AndroidTestCase;

/**
 * Testing of the XmlLevelMirror Class
 * @author lianghuang
 *
 */
public class XmlLevelMirrorTest extends AndroidTestCase {

	private XmlLevelMirror _tMirror;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		_tMirror = new XmlLevelMirror(5);
	}
	
	public void testXmlLevelMirror(){
		assertNotNull(_tMirror);
	}
	
	public void testGetId(){
		assert(XmlLevelMirror.getId().equals("mirror"));
	}
}
