package ca.sandstorm.luminance.test.gamelogic;

import ca.sandstorm.luminance.gamelogic.GameStateInput;
import android.test.AndroidTestCase;

/*
 * Input can't be tested. It uses the Engine instance which
 * in turn needs every other major object so even mocking that is 
 * out of the question...
 */
public class GameStateInputTest extends AndroidTestCase {

	GameStateInput gsi;
	
	protected void setUp() throws Exception{
		gsi = new GameStateInput();
		super.setUp();
	}
	
	public void testGameStateInput() throws Exception {
		assertNotNull(gsi);
	}
}
