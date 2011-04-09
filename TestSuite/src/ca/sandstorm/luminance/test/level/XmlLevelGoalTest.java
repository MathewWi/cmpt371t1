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
	
	private XmlLevelGoal _tGoalWhite, _tGoalRed, _tGoalGreen, _tGoalBlue, _tGoalOut;
	
	protected void setUp() throws Exception {
		super.setUp();
		Vector<Float> pos = new Vector<Float>();
		pos.add(0.0f);
		pos.add(0.0f);
		Vector<Float> dir = new Vector<Float>();
		dir.add(0.0f);
		dir.add(0.0f);
		dir.add(0.0f);
		_tGoalWhite = new XmlLevelGoal("WhitE",pos,dir);
		_tGoalRed = new XmlLevelGoal("rEd",pos,dir);
		_tGoalGreen = new XmlLevelGoal("Green",pos,dir);
		_tGoalBlue = new XmlLevelGoal("BLUE", pos,dir);
		
		try {
			_tGoalOut = new XmlLevelGoal("HerpaDerp",pos,dir);
		} catch (Exception e) {
			// Should still stay null
		}
	}
	
	public void testXmlLevelGoal(){
		assertNotNull(_tGoalWhite);
		assertNotNull(_tGoalRed);
		assertNotNull(_tGoalGreen);
		assertNotNull(_tGoalBlue);
		assertNull(_tGoalOut);
	}

	public void testIsValidColour() throws Exception {
		assertTrue(_tGoalWhite.isValidColour("WHITE"));
		assertTrue(_tGoalWhite.isValidColour("RED"));
		assertTrue(_tGoalWhite.isValidColour("GREEN"));
		assertTrue(_tGoalWhite.isValidColour("BLUE"));
		assertTrue(_tGoalWhite.isValidColour("WHITE"));

	}

	public void testGetColour() throws Exception {
		assertEquals(_tGoalWhite.getColour(), Color.WHITE);
		assertEquals(_tGoalRed.getColour(), Color.RED);
		assertEquals(_tGoalGreen.getColour(), Color.GREEN);
		assertEquals(_tGoalBlue.getColour(), Color.BLUE);
	}

}
