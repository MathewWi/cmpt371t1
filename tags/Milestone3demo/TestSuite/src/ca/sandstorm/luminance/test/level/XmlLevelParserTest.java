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

	private XmlLevelParser tParser;
	private String xmlFileName = "TestLevels/TestLevel.xml";
	private XmlLevel tLevel;

	private LinkedList<XmlLevelObject> tListObject;
	private LinkedList<XmlLevelTool> tListTool;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		InputStream tis = this.getContext().getAssets().open(xmlFileName);
		//FileInputStream fis;
		tParser = new XmlLevelParser(tis);
	}

	public void testXmlLevelParser() throws Exception {
		assertTrue(tParser != null);
	}

	public void testParser() throws Exception {
		assertTrue(tParser != null);

		tLevel = tParser.parse();

		assertTrue(tLevel != null);
		assertTrue(tLevel.getHeight() == 1.0f);
		assertTrue(tLevel.getWidth() == 1.0f);
		assertTrue(tLevel.getDifficulty().equals("easy"));
		assertTrue(tLevel.getName().equals("Level 1"));
		assertTrue(tLevel.getXSize() == 10);
		assertTrue(tLevel.getYSize() == 10);

		tListObject = tLevel.getObjects();
		tListTool = tLevel.getTools();

		assertNotNull(tListObject);
		assertNotNull(tListTool);

		XmlLevelObject tObject = tListObject.removeFirst();

		assertNotNull(tObject);
		assertTrue(tObject.getType().equals("brick"));
		assertTrue(tObject.getPositionX() == 1);
		assertTrue(tObject.getPositionY() == 1);
		assertTrue(tObject.getRotationX() == 0);
		assertTrue(tObject.getRotationY() == 0);
		assertTrue(tObject.getRotationZ() == 0);

		tObject = tListObject.removeFirst();
		
		assertNotNull(tObject);
		assertTrue(tObject.getType().equals("brick"));
		assertTrue(tObject.getPositionX() == 2);
		assertTrue(tObject.getPositionY() == 2);
		assertTrue(tObject.getRotationX() == 0);
		assertTrue(tObject.getRotationY() == 0);
		assertTrue(tObject.getRotationZ() == 0);

		tObject = tListObject.removeFirst();
		
		assertNotNull(tObject);
		assertTrue(tObject.getType().equals("brick"));
		assertTrue(tObject.getPositionX() == 3);
		assertTrue(tObject.getPositionY() == 3);
		assertTrue(tObject.getRotationX() == 0);
		assertTrue(tObject.getRotationY() == 0);
		assertTrue(tObject.getRotationZ() == 0);

		tObject = tListObject.removeFirst();
		
		assertNotNull(tObject);
		assertTrue(tObject.getType().equals("brick"));
		assertTrue(tObject.getPositionX() == 4);
		assertTrue(tObject.getPositionY() == 4);
		assertTrue(tObject.getRotationX() == 0);
		assertTrue(tObject.getRotationY() == 0);
		assertTrue(tObject.getRotationZ() == 0);
		
		tObject = tListObject.removeFirst();

		assertNotNull(tObject);
		assertTrue(tObject.getType().equals("brick"));
		assertTrue(tObject.getPositionX() == 0);
		assertTrue(tObject.getPositionY() == 6);
		assertTrue(tObject.getRotationX() == 0);
		assertTrue(tObject.getRotationY() == 0);
		assertTrue(tObject.getRotationZ() == 0);

		tObject = tListObject.removeFirst();
		
		assertNotNull(tObject);
		assertTrue(tObject.getType().equals("brick"));
		assertTrue(tObject.getPositionX() == 6);
		assertTrue(tObject.getPositionY() == 0);
		assertTrue(tObject.getRotationX() == 0);
		assertTrue(tObject.getRotationY() == 0);
		assertTrue(tObject.getRotationZ() == 0);

		tObject = tListObject.removeFirst();
		
		assertNotNull(tObject);
		assertTrue(tObject.getType().equals("goal"));
		assertTrue(tObject.getPositionX() == 50);
		assertTrue(tObject.getPositionY() == 15);
		assertTrue(tObject.getRotationX() == 0);
		assertTrue(tObject.getRotationY() == 0);
		assertTrue(tObject.getRotationZ() == 0);

		XmlLevelTool tTools = tListTool.removeFirst();

		assertNotNull(tTools);
		assertTrue(tTools.getType().equals("mirror"));
		assertTrue(tTools.getCount() == 5);

		tTools = tListTool.removeFirst();

		assertNotNull(tTools);
		assertTrue(tTools.getType().equals("prism"));
		assertTrue(tTools.getCount() == 1);

	}

}
