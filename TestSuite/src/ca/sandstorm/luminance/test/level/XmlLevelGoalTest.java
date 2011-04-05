package ca.sandstorm.luminance.test.level;

import java.util.Vector;

import ca.sandstorm.luminance.level.Color;
import ca.sandstorm.luminance.level.XmlLevelGoal;
import android.test.AndroidTestCase;
/**
 * Testing Xml Level goal functionality
 * @author Jeff
 *
 */
/**
 * Testing of the XmlLevelGoal Class
 * @author lianghuang
 *
 */
public class XmlLevelGoalTest extends AndroidTestCase {
	
	private XmlLevelGoal tGoalWhite, tGoalRed, tGoalGreen, tGoalBlue, tGoalOut;
	
	protected void setUp() throws Exception {
		super.setUp();
		Vector<Float> pos = new Vector<Float>();
		pos.add(0.0f);
		pos.add(0.0f);
		Vector<Float> dir = new Vector<Float>();
		dir.add(0.0f);
		dir.add(0.0f);
		dir.add(0.0f);
		tGoalWhite = new XmlLevelGoal("WhitE",pos,dir);
		tGoalRed = new XmlLevelGoal("rEd",pos,dir);
		tGoalGreen = new XmlLevelGoal("Green",pos,dir);
		tGoalBlue = new XmlLevelGoal("BLUE", pos,dir);
		
		try {
			tGoalOut = new XmlLevelGoal("HerpaDerp",pos,dir);
		} catch (Exception e) {
			// Should still stay null
		}
	}
	
	public void testXmlLevelGoal(){
		assertNotNull(tGoalWhite);
		assertNotNull(tGoalRed);
		assertNotNull(tGoalGreen);
		assertNotNull(tGoalBlue);
		assertNull(tGoalOut);
	}

	public void testIsValidColour() throws Exception {
		assertTrue(tGoalWhite.isValidColour("WHITE"));
		assertTrue(tGoalWhite.isValidColour("RED"));
		assertTrue(tGoalWhite.isValidColour("GREEN"));
		assertTrue(tGoalWhite.isValidColour("BLUE"));
		assertTrue(tGoalWhite.isValidColour("WHITE"));

	}

	public void testGetColour() throws Exception {
		assertEquals(tGoalWhite.getColour(), Color.WHITE);
		assertEquals(tGoalRed.getColour(), Color.RED);
		assertEquals(tGoalGreen.getColour(), Color.GREEN);
		assertEquals(tGoalBlue.getColour(), Color.BLUE);
	}

}
