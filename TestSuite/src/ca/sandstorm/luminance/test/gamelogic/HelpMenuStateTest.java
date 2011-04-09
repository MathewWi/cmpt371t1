package ca.sandstorm.luminance.test.gamelogic;

import ca.sandstorm.luminance.gamelogic.HelpMenuState;
import android.test.AndroidTestCase;

/*
 * Can't be tested on reasons of using Engine.
 * And is also an iState.
 */
public class HelpMenuStateTest extends AndroidTestCase {
	HelpMenuState _hms;
	
	protected void setUp() throws Exception{
		_hms = new HelpMenuState();
		super.setUp();
	}
	
	public void testHelpMenuState() throws Exception{
		assertNotNull(_hms);
	}
}
