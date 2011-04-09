package ca.sandstorm.luminance.test.gamelogic;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.gamelogic.GameState;

/**
 * Testing of the GameState Class
 * Its not filled out at the moment because GameState is... horribly cluttered
 * The Methods within GameState are mostly all private. Mocking of objects
 * does no good as many other classes need to be mocked or methods used
 * are of private state and need to be mocked and call other methods within
 * those and those objects need to be mocked...
 * No Testing will be done on the larger sections of methods.
 * @author lianghuang
 *
 */
public class GameStateTest extends AndroidTestCase {
		
	GameState _gs;
	
	protected void setUp() throws Exception {
		_gs = new GameState(1);
		super.setUp();

	}

	public void testGameState() throws Exception {
		assertNotNull(_gs);
	}
	
	public void testGetShowMenu() throws Exception {
		assertTrue(_gs.getShowMenu() == false);
	}
	
	public void testSetShowMenu() throws Exception {
		_gs.setShowMenu(true);
		assertTrue(_gs.getShowMenu());
	}
	
	public void testIsCurrentLevelComplete() throws Exception {
		assertTrue(_gs.isCurrentLevelComplete() == false);
	}
	
	/**
	 * @Mock Test
	 * Disappointingly, even with mock. We'd have to mock every known object/class of the whole game.
	 */
	public void testAddObject() {
		
		/*
		//setup
		mockIGameObject mockLight = new mockIGameObject();
		//added mock light into gameState
		mockLight.v = new Vector3f(4f,4f,4f);
		assertTrue(mockLight.v != null);
		
		_gs.addObject(mockLight);
		//execute
		assertEquals(mockLight.getPosition().x, _gs.getObjectAtGridCoords(3, 3).getPosition().x, .001);
		*/
		
	}

	public void testRemoveObject() throws Exception {

	}

	public void testClearLevel() throws Exception {

	}

	public void testParseLevel() throws Exception {

	}

	public void testResetCamera() throws Exception {

	}

	public void testPause() throws Exception {

	}

	public void testResume() throws Exception {

	}

	public void testInit() throws Exception {

	}

	public void testProcessInput() throws Exception {

	}

	public void testProcessToolBeltInput() throws Exception {

	}

	public void testMouseClick() throws Exception {

	}

	public void testGridToWorldCoords() throws Exception {

	}

	public void testWorldToGridCoords() throws Exception {

	}

	public void testGetObjectAtGridCoords() throws Exception {

	}

	public void testIsCellOccupied() throws Exception {

	}

	public void testUpdate() throws Exception {

	}

	public void testDraw() throws Exception {

	}

	public void testDeviceChanged() throws Exception {

	}

	public void testMessageReceived() throws Exception {

	}

	public void testIsActive() throws Exception {

	}

	public void testIsVisible() throws Exception {

	}
}
