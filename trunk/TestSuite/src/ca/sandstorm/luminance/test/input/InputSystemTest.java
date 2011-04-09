package ca.sandstorm.luminance.test.input;

import android.test.AndroidTestCase;
import android.view.KeyEvent;
import ca.sandstorm.luminance.input.InputButton;
import ca.sandstorm.luminance.input.InputSystem;

/**
 * Testing InputSystem functionality
 * 
 * @author Amara Daal
 * 
 */
public class InputSystemTest extends AndroidTestCase {

	InputSystem _inputSystem;

	/**
	 * @throws java.lang.Exception
	 */

	public void setUp() throws Exception {
		super.setUp();
		_inputSystem = new InputSystem();
		assertNotNull(_inputSystem);
	}

	/**
	 * @throws java.lang.Exception
	 */

	public void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputSystem#InputSystem()}.
	 */

	public void testInputSystem() {
		InputSystem newInputSystem = new InputSystem();
		assertNotNull(newInputSystem);

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputSystem#reset()}.
	 */

	public void testReset() {
		_inputSystem.getTrackball().press(0, 2, 2);
		_inputSystem.getKeyboard().press(0, 2);
		_inputSystem.getTouchScreen().press(0, 0, 2, 2);
		_inputSystem.getOrientationSensor().press(0, 2, 2);

		_inputSystem.reset();

		assertFalse(_inputSystem.getTrackball().getPressed());
		assertFalse(_inputSystem.getKeyboard().getKeys()[2].getPressed());
		assertFalse(_inputSystem.getTouchScreen().getPressed(0));
		assertFalse(_inputSystem.getOrientationSensor().getPressed());

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputSystem#roll(float, float)}.
	 * 
	 */

	public void testRoll() {

		float oldX;
		float oldY;
		float newX;
		float newY;

		for (int i = 0; i < 10; i++) {

			oldX = _inputSystem.getTrackball().getX();
			oldY = _inputSystem.getTrackball().getY();

			_inputSystem.roll(i, i);

			newX = _inputSystem.getTrackball().getX();
			newY = _inputSystem.getTrackball().getY();

			// Assert we have reached where we wanted
			assertEquals(newX, oldX + i, .001f);
			assertEquals(newY, oldY + i, .001f);

			// rollback
			if (i > 1) {
				_inputSystem.roll(-(i / 2), -(i / 3));

				assertEquals(newX + -(i / 2),
						_inputSystem.getTrackball().getX(), 0.001f);
				assertEquals(newY + -(i / 3),
						_inputSystem.getTrackball().getY(), 0.001f);
			}

		}

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputSystem#touchDown(int, float, float)}
	 * .
	 * 
	 */

	/*
	 * public void testTouchDown() {
	 * 
	 * //Assert indices are not touched, touch them for(int i= 0; i < 10; i++){
	 * assertFalse(_inputSystem.getTouchScreen().getPressed(i));
	 * 
	 * _inputSystem.touchDown(i, i, i);
	 * assertTrue(_inputSystem.getTouchScreen().getPressed(i));
	 * 
	 * //Assert x and y of event equals input //assertEquals(i,
	 * _inputSystem.getTouchScreen().getTouchEvent().getRawX(), 0.001f);
	 * //assertEquals(i, _inputSystem.getTouchScreen().getTouchEvent().getRawY(),
	 * 0.001f);
	 * 
	 * }
	 * 
	 * }
	 */

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputSystem#touchUp(int, float, float)}
	 * .
	 * 
	 */
	/*
	 * public void testTouchUp() { _inputSystem.reset(); //Touch down for(int i=
	 * 0; i < 10; i++){ _inputSystem.touchDown(i, i, i);
	 * assertTrue(_inputSystem.getTouchScreen().getPressed(i)); } //Release all
	 * buttons for(int i= 0; i < 10; i++){ _inputSystem.touchUp(i, i, i);
	 * assertFalse(_inputSystem.getTouchScreen().getPressed(i)); }
	 * 
	 * }
	 */

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputSystem#setOrientation(float, float, float)}
	 * . TODO: contract unclear need to clarify
	 */

	public void testSetOrientation() {

		// Sets the orientation, the actual calculations are
		// done by NVIDIA function TODO: (testing can be done on known values)
		for (int i = 0; i < 10; i++) {
			_inputSystem.setOrientation(i, i, i);

			assertTrue(_inputSystem.getOrientationSensor().getPressed());

		}
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputSystem#keyDown(int)}.
	 */

	public void testKeyDown() {
		_inputSystem.getKeyboard().resetAll();

		// Press select keys
		for (int i = KeyEvent.getMaxKeyCode() - 10; i < KeyEvent
				.getMaxKeyCode(); i++) {

			_inputSystem.keyDown(i);

		}

		InputButton[] keysAr = _inputSystem.getKeyboard().getKeys();

		// Assert all chosen keys were pressed
		for (int i = KeyEvent.getMaxKeyCode() - 10; i < KeyEvent
				.getMaxKeyCode(); i++) {

			assertTrue(keysAr[i].getPressed());

		}
		// Assert no other keys were pressed
		for (int i = 0; i < KeyEvent.getMaxKeyCode() - 10; i++) {

			assertFalse(keysAr[i].getPressed());

		}
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputSystem#keyUp(int)}.
	 */

	public void testKeyUp() {
		_inputSystem.getKeyboard().resetAll();

		// Press all keys
		for (int i = 0; i < KeyEvent.getMaxKeyCode(); i++) {

			_inputSystem.keyDown(i);

		}
		InputButton[] keysAr = _inputSystem.getKeyboard().getKeys();
		// TODO: clarify 10 offset
		// Release select keys
		for (int i = KeyEvent.getMaxKeyCode() - 10; i < KeyEvent
				.getMaxKeyCode(); i++) {

			_inputSystem.keyUp(i);

			assertFalse(keysAr[i].getPressed());

		}
		// Assert all other keys are still pressed
		for (int i = 0; i < KeyEvent.getMaxKeyCode() - 10; i++) {

			assertTrue(keysAr[i].getPressed());

		}

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputSystem#releaseAllKeys()}.
	 */

	public void testReleaseAllKeys() {
		_inputSystem.reset();

		// Activate trackball
		_inputSystem.roll(1, 1);
		assertTrue(_inputSystem.getTrackball().getPressed());

		// Activate all keys
		for (int i = 0; i < KeyEvent.getMaxKeyCode(); i++) {
			_inputSystem.keyDown(i);
		}
		// Activate touch screen
		for (int i = 0; i < 10; i++) {
			_inputSystem.touchDown(i, i, i);
		}

		// Activate orientation
		_inputSystem.setOrientation(1f, 1f, 1f);
		assertTrue(_inputSystem.getOrientationSensor().getPressed());

		_inputSystem.releaseAllKeys();

		// Verify trackballs
		assertFalse(_inputSystem.getTrackball().getPressed());

		// Verify Keyboard
		InputButton[] keysAr = _inputSystem.getKeyboard().getKeys();
		for (int i = 0; i < KeyEvent.getMaxKeyCode(); i++) {

			assertFalse(keysAr[i].getPressed());
		}

		// Verify touch screen
		for (int i = 0; i < 10; i++) {

			assertFalse(_inputSystem.getTouchScreen().getPressed(i));
		}

		// Verify orienation sensor
		assertFalse(_inputSystem.getOrientationSensor().getPressed());

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputSystem#getTouchScreen()}.
	 */

	public void testGetTouchScreen() {
		assertNotNull(_inputSystem.getTouchScreen());

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputSystem#getOrientationSensor()}
	 * .
	 */

	public void testGetOrientationSensor() {
		assertNotNull(_inputSystem.getOrientationSensor());
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputSystem#getTrackball()}.
	 */

	public void testGetTrackball() {
		assertNotNull(_inputSystem.getTrackball());
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputSystem#getKeyboard()}.
	 */

	public void testGetKeyboard() {
		assertNotNull(_inputSystem.getKeyboard());
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputSystem#setScreenRotation(int)}
	 * .
	 */

	public void testSetScreenRotation() {
		/**
		 * TODO: Need to test canonical orientation first
		 */
		assert (true);
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputSystem#canonicalOrientationToScreenOrientation(int, float[], float[])}
	 * .
	 */

	public void testCanonicalOrientationToScreenOrientation() {
		/**
		 * TODO: Needs to be tested
		 */
	}

}
