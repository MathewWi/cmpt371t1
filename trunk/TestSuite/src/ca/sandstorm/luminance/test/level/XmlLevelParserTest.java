package ca.sandstorm.luminance.test.level;

import java.io.InputStream;
import java.util.LinkedList;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.level.XmlLevel;
import ca.sandstorm.luminance.level.XmlLevelObject;
import ca.sandstorm.luminance.level.XmlLevelParser;
import ca.sandstorm.luminance.level.XmlLevelTool;
import ca.sandstorm.luminance.resources.ResourceManager;
import ca.sandstorm.luminance.resources.TextResource;

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
	private String xmlFileName = "levels/TestLevel.xml";
	private XmlLevel tLevel;

	private LinkedList<XmlLevelObject> tListObject;
	private LinkedList<XmlLevelTool> tListTool;
	
	ResourceManager tMan;

	protected void setUp() throws Exception {
		super.setUp();
		
		tMan = new ResourceManager();
		
		tMan.setAssets(this.getContext().getAssets());
		
		TextResource tRes = tMan.loadText(xmlFileName);
		//FileInputStream fis;
		tParser = new XmlLevelParser(tRes.getAssetFd().createInputStream());
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
		assertTrue(tLevel.getName().equals("Level Test"));
		assertTrue(tLevel.getXSize() == 15);
		assertTrue(tLevel.getYSize() == 30);

		tListObject = tLevel.getObjects();
		tListTool = tLevel.getTools();

		assertNotNull(tListObject);
		assertNotNull(tListTool);

		XmlLevelObject tObject = tListObject.getFirst();

		assertNotNull(tObject);
		assertTrue(tObject.getType().equals("brick"));
		assertTrue(tObject.getPositionX() == 10);
		assertTrue(tObject.getPositionY() == 10);
		assertTrue(tObject.getRotationX() == 0);
		assertTrue(tObject.getRotationY() == 0);
		assertTrue(tObject.getRotationZ() == 0);

		tObject = tListObject.getFirst();

		assertNotNull(tObject);
		assertTrue(tObject.getType().equals("brick"));
		assertTrue(tObject.getPositionX() == 9);
		assertTrue(tObject.getPositionY() == 9);
		assertTrue(tObject.getRotationX() == 0);
		assertTrue(tObject.getRotationY() == 0);
		assertTrue(tObject.getRotationZ() == 0);

		tObject = tListObject.getFirst();

		assertNotNull(tObject);
		assertTrue(tObject.getType().equals("goal"));
		assertTrue(tObject.getPositionX() == 1);
		assertTrue(tObject.getPositionY() == 1);
		assertTrue(tObject.getRotationX() == 0);
		assertTrue(tObject.getRotationY() == 0);
		assertTrue(tObject.getRotationZ() == 0);

		XmlLevelTool tTools = tListTool.getFirst();

		assertNotNull(tTools);
		assertTrue(tTools.getType().equals("mirror"));
		assertTrue(tTools.getCount() == 5);

		tTools = tListTool.getFirst();

		assertNotNull(tTools);
		assertTrue(tTools.getType().equals("prism"));
		assertTrue(tTools.getCount() == 1);

	}

}
