package ca.sandstorm.luminance.test.level;

import java.util.Vector;

import ca.sandstorm.luminance.level.XmlLevelBrick;
import android.test.AndroidTestCase;

/**
 * Testing of the XmlLevelBrick Class
 * @author lianghuang
 *
 */
public class XmlLevelBrickTest extends AndroidTestCase {
	
	private XmlLevelBrick _tBrick;
	
	protected void setUp() throws Exception {
		super.setUp();
		Vector<Float> pos = new Vector<Float>();
		pos.add(0.0f);
		pos.add(0.0f);
		Vector<Float> dir = new Vector<Float>();
		dir.add(0.0f);
		dir.add(0.0f);
		dir.add(0.0f);
		_tBrick = new XmlLevelBrick(pos, dir);
	}
	
	public void testXmlLevelBrick(){
		assertNotNull(_tBrick);
	}
	
	public void testGetId(){
		assertTrue(XmlLevelBrick.getId().equals("brick"));
	}
}
