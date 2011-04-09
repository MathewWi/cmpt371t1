package ca.sandstorm.luminance.test.gamelogic;

import ca.sandstorm.luminance.gamelogic.LevelMenuState;
import android.test.AndroidTestCase;

/*
 * Can't be tested because it uses Engine
 * It's also an IState meaning it'll be tested
 * Using play testing and GUI.
 */
public class LevelMenuStateTest extends AndroidTestCase{

	LevelMenuState _lms;
	
	protected void setUp() throws Exception{
		_lms = new LevelMenuState();
		super.setUp();
	}
	
	public void testLevelMenuState() throws Exception{
		assertNotNull(_lms);
	}
	
}
