package ca.sandstorm.luminance.test.level;

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
		
		tGoalWhite = new XmlLevelGoal("WhitE");
		tGoalRed = new XmlLevelGoal("rEd");
		tGoalGreen = new XmlLevelGoal("Green");
		tGoalBlue = new XmlLevelGoal("BLUE");
		
		try {
			tGoalOut = new XmlLevelGoal("HerpaDerp");
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
		
		assertFalse(tGoalWhite.isValidColour("white"));
		assertFalse(tGoalWhite.isValidColour("red"));
		assertFalse(tGoalWhite.isValidColour("green"));
		assertFalse(tGoalWhite.isValidColour("blue"));
		assertFalse(tGoalWhite.isValidColour(""));
		//assertFalse(tGoalWhite.isValidColour(null));
	}

	public void testGetColour() throws Exception {
		assert(tGoalWhite.getColour().equals("WHITE"));
		assert(tGoalRed.getColour().equals("RED"));
		assert(tGoalGreen.getColour().equals("GREEN"));
		assert(tGoalBlue.getColour().equals("BLUE"));
	}

}
