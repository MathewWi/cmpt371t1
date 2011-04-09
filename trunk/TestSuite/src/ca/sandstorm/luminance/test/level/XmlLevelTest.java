package ca.sandstorm.luminance.test.level;

import ca.sandstorm.luminance.level.XmlLevel;
import android.test.AndroidTestCase;

/**
 * Testing of XmlLevel Class
 * @author lianghuang
 *
 */
public class XmlLevelTest extends AndroidTestCase {
	
	private XmlLevel _tLevel;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		_tLevel = new XmlLevel("Hello", "medium", 5, 10, 5.0f, 10.0f, null, null );

	}

	public void testGetName() throws Exception {
		assert(_tLevel.getName().equals("Hello"));
	}

	public void testGetDifficulty() throws Exception {
		assert(_tLevel.getDifficulty().equals("medium"));
	}

	public void testGetYSize() throws Exception {
		assert(_tLevel.getYSize() == 10);
	}

	public void testGetXSize() throws Exception {
		assert(_tLevel.getXSize() == 5);
	}
	
	public void testGetHeight() throws Exception {
		assert(_tLevel.getHeight() == 10.0f);
	}
	
	public void testGetWidth() throws Exception {
		assert(_tLevel.getWidth() == 5.0f);
	}

	public void testGetObjects() throws Exception {
		assertNull(_tLevel.getObjects());
	}

	public void testGetTools() throws Exception {
		assertNull(_tLevel.getTools());
	}
}
