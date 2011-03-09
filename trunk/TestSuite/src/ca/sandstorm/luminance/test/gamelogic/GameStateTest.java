package ca.sandstorm.luminance.test.gamelogic;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;

import javax.vecmath.Vector3f;

import ca.sandstorm.luminance.gamelogic.GameState;
import ca.sandstorm.luminance.gameobject.IGameObject;
import android.test.AndroidTestCase;

/**
 * Testing of the GameState Class
 * Its not filled out at the moment because GameState is... horribly cluttered
 * @author lianghuang
 *
 */
public class GameStateTest extends AndroidTestCase {
	protected void setUp() throws Exception {
		super.setUp();

	}

	public void testAddToolBelt() throws Exception {

	}
	/**
	 * @Mock Test
	 */
	public void testAddObject() {
		//setup
		IGameObject mockLight = mock(IGameObject.class);
		GameState gameState = new GameState();
		stub(mockLight.getPosition()).toReturn(new Vector3f(3f, 3f, 3f));
		//added mock light into gameState
		gameState.addObject(mockLight);
		//execute
		assertEquals(mockLight.getPosition().x, gameState.getObjectAtGridCoords(3, 3).getPosition().x, .001);
		//verify
		verify(mockLight).getPosition();
		
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
