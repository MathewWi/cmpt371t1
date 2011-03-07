package ca.sandstorm.luminance.test.level;

import ca.sandstorm.luminance.level.XmlLevel;
import android.test.AndroidTestCase;

public class XmlLevelTest extends AndroidTestCase {
	
	private XmlLevel tLevel;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		tLevel = new XmlLevel("Hello", "medium", 5, 10, 5.0f, 10.0f, null, null );

	}

	public void testGetName() throws Exception {
		assert(tLevel.getName().equals("Hello"));
	}

	public void testGetDifficulty() throws Exception {
		assert(tLevel.getDifficulty().equals("medium"));
	}

	public void testGetYSize() throws Exception {
		assert(tLevel.getYSize() == 10);
	}

	public void testGetXSize() throws Exception {
		assert(tLevel.getXSize() == 5);
	}
	
	public void testGetHeight() throws Exception {
		assert(tLevel.getHeight() == 10.0f);
	}
	
	public void testGetWidth() throws Exception {
		assert(tLevel.getWidth() == 5.0f);
	}

	public void testGetObjects() throws Exception {
		assertNull(tLevel.getObjects());
	}

	public void testGetTools() throws Exception {
		assertNull(tLevel.getTools());
	}
}
