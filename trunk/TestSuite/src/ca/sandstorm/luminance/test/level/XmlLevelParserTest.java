package ca.sandstorm.luminance.test.level;

import java.io.InputStream;
import java.util.LinkedList;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.level.XmlLevel;
import ca.sandstorm.luminance.level.XmlLevelObject;
import ca.sandstorm.luminance.level.XmlLevelParser;
import ca.sandstorm.luminance.level.XmlLevelTool;

/**
 * Testing of XmlLevelParser Class
 * 
 * @author lianghuang
 * 
 */
public class XmlLevelParserTest extends AndroidTestCase {

	/*
	 * This is not finished. Couldn't figure out how to make it read a file from
	 * assets folder!!!
	 */

	private XmlLevelParser _tParser;
	private String _xmlFileName = "Tes_tLevels/Tes_tLevel.xml";
	private XmlLevel _tLevel;

	private LinkedList<XmlLevelObject> _tListObject;
	private LinkedList<XmlLevelTool> _tListTool;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		InputStream tis = this.getContext().getAssets().open(_xmlFileName);
		//FileInputStream fis;
		_tParser = new XmlLevelParser(tis);
	}

	public void testXmlLevelParser() throws Exception {
		assertTrue(_tParser != null);
	}

	public void tes_tParser() throws Exception {
		assertTrue(_tParser != null);

		_tLevel = _tParser.parse();

		assertTrue(_tLevel != null);
		assertTrue(_tLevel.getHeight() == 1.0f);
		assertTrue(_tLevel.getWidth() == 1.0f);
		assertTrue(_tLevel.getDifficulty().equals("easy"));
		assertTrue(_tLevel.getName().equals("Level 1"));
		assertTrue(_tLevel.getXSize() == 10);
		assertTrue(_tLevel.getYSize() == 10);

		_tListObject = _tLevel.getObjects();
		_tListTool = _tLevel.getTools();

		assertNotNull(_tListObject);
		assertNotNull(_tListTool);

		XmlLevelObject tObject = _tListObject.removeFirst();

		assertNotNull(tObject);
		assertTrue(tObject.getType().equals("brick"));
		assertTrue(tObject.getPositionX() == 1);
		assertTrue(tObject.getPositionY() == 1);
		assertTrue(tObject.getRotationX() == 0);
		assertTrue(tObject.getRotationY() == 0);
		assertTrue(tObject.getRotationZ() == 0);

		tObject = _tListObject.removeFirst();
		
		assertNotNull(tObject);
		assertTrue(tObject.getType().equals("brick"));
		assertTrue(tObject.getPositionX() == 2);
		assertTrue(tObject.getPositionY() == 2);
		assertTrue(tObject.getRotationX() == 0);
		assertTrue(tObject.getRotationY() == 0);
		assertTrue(tObject.getRotationZ() == 0);

		tObject = _tListObject.removeFirst();
		
		assertNotNull(tObject);
		assertTrue(tObject.getType().equals("brick"));
		assertTrue(tObject.getPositionX() == 3);
		assertTrue(tObject.getPositionY() == 3);
		assertTrue(tObject.getRotationX() == 0);
		assertTrue(tObject.getRotationY() == 0);
		assertTrue(tObject.getRotationZ() == 0);

		tObject = _tListObject.removeFirst();
		
		assertNotNull(tObject);
		assertTrue(tObject.getType().equals("brick"));
		assertTrue(tObject.getPositionX() == 4);
		assertTrue(tObject.getPositionY() == 4);
		assertTrue(tObject.getRotationX() == 0);
		assertTrue(tObject.getRotationY() == 0);
		assertTrue(tObject.getRotationZ() == 0);
		
		tObject = _tListObject.removeFirst();

		assertNotNull(tObject);
		assertTrue(tObject.getType().equals("brick"));
		assertTrue(tObject.getPositionX() == 0);
		assertTrue(tObject.getPositionY() == 6);
		assertTrue(tObject.getRotationX() == 0);
		assertTrue(tObject.getRotationY() == 0);
		assertTrue(tObject.getRotationZ() == 0);

		tObject = _tListObject.removeFirst();
		
		assertNotNull(tObject);
		assertTrue(tObject.getType().equals("brick"));
		assertTrue(tObject.getPositionX() == 6);
		assertTrue(tObject.getPositionY() == 0);
		assertTrue(tObject.getRotationX() == 0);
		assertTrue(tObject.getRotationY() == 0);
		assertTrue(tObject.getRotationZ() == 0);

		tObject = _tListObject.removeFirst();
		
		assertNotNull(tObject);
		assertTrue(tObject.getType().equals("goal"));
		assertTrue(tObject.getPositionX() == 50);
		assertTrue(tObject.getPositionY() == 15);
		assertTrue(tObject.getRotationX() == 0);
		assertTrue(tObject.getRotationY() == 0);
		assertTrue(tObject.getRotationZ() == 0);

		XmlLevelTool tTools = _tListTool.removeFirst();

		assertNotNull(tTools);
		assertTrue(tTools.getType().equals("mirror"));
		assertTrue(tTools.getCount() == 5);

		tTools = _tListTool.removeFirst();

		assertNotNull(tTools);
		assertTrue(tTools.getType().equals("prism"));
		assertTrue(tTools.getCount() == 1);

	}

}
