package ca.sandstorm.luminance.test.input;

import android.test.AndroidTestCase;
import android.view.KeyEvent;
import ca.sandstorm.luminance.input.InputButton;
import ca.sandstorm.luminance.input.InputKeyboard;

/**
 * @author Amara Daal
 * 
 */
public class InputKeyboardTest extends AndroidTestCase {

	InputKeyboard _inputKeyboard;
	float _currentTime = 2.0f;
	InputButton[] _mKeys;

	/**
	 * @throws java.lang.Exception
	 */

	public void setUp() throws Exception {
		super.setUp();
		_inputKeyboard = new InputKeyboard();
		assertNotNull(_inputKeyboard);
	}

	/**
	 * @throws java.lang.Exception
	 */

	public void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputKeyboard#press(float, int)}
	 * .
	 */

	public void testPress() {

		_mKeys = _inputKeyboard.getKeys();

		// Assert all keys are intialized to not pressed
		for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
			assertFalse(_mKeys[x].getPressed());
		}
		// Press all keys
		for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
			_inputKeyboard.press(_currentTime + x, x);

		}

		// Assert all keys were pressed
		for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
			assertTrue(_mKeys[x].getPressed());
			assertEquals(_currentTime + x, _mKeys[x].getLastPressedTime());
		}
		// Release all keys
		_inputKeyboard.releaseAll();

		// Press individual keys, observe effect on others
		_inputKeyboard.press(_currentTime, 0);
		_inputKeyboard.press(_currentTime, 5);
		_inputKeyboard.press(_currentTime, 4);
		_inputKeyboard.press(_currentTime, 2);
		_inputKeyboard.press(_currentTime, 6);
		_inputKeyboard.press(_currentTime, 3);
		_inputKeyboard.press(_currentTime, 1);
		_inputKeyboard.press(_currentTime, KeyEvent.getMaxKeyCode() - 1);

		// Assert all selected keys were pressed
		for (int x = 0; x < 7; x++) {
			assertTrue(_mKeys[x].getPressed());
		}
		assertTrue(_mKeys[KeyEvent.getMaxKeyCode() - 1].getPressed());

		// Assert all other keys were not pressed
		for (int x = 7; x < KeyEvent.getMaxKeyCode() - 1; x++) {
			assertFalse(_mKeys[x].getPressed());
		}

		_inputKeyboard.resetAll();

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputKeyboard#release(int)}.
	 */

	public void testRelease() {

		_inputKeyboard.resetAll();

		// Press all keys
		for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
			_inputKeyboard.press(_currentTime, x);

		}

		// Release all keys
		for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
			_inputKeyboard.release(x);

		}
		_mKeys = _inputKeyboard.getKeys();
		// Assert all other keys are released
		for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
			assertFalse(_mKeys[x].getPressed());
		}

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputKeyboard#releaseAll()}.
	 */

	public void testReleaseAll() {

		// Press all keys
		for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
			_inputKeyboard.press(_currentTime, x);

		}
		_inputKeyboard.releaseAll();

		_mKeys = _inputKeyboard.getKeys();
		// Assert all keys were released
		for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
			assertFalse(_mKeys[x].getPressed());
		}

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputKeyboard#getKeys()}.
	 */

	public void testGetKeys() {
		_mKeys = null;
		_mKeys = _inputKeyboard.getKeys();
		assertNotNull(_mKeys);

		// Press individual keys, observe effect on others
		_inputKeyboard.press(_currentTime, 0);
		_inputKeyboard.press(_currentTime, 5);
		_inputKeyboard.press(_currentTime, 4);
		_inputKeyboard.press(_currentTime, 2);
		_inputKeyboard.press(_currentTime, 6);
		_inputKeyboard.press(_currentTime, 3);
		_inputKeyboard.press(_currentTime, 1);
		_inputKeyboard.press(_currentTime, KeyEvent.getMaxKeyCode() - 1);

		// Assert all buttons not null
		for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
			assertNotNull(_mKeys[x]);
		}

		// Assert all selected keys were pressed
		for (int x = 0; x < 7; x++) {
			assertTrue(_mKeys[x].getPressed());
		}
		assertTrue(_mKeys[KeyEvent.getMaxKeyCode() - 1].getPressed());

		_inputKeyboard.resetAll();

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputKeyboard#resetAll()}.
	 */

	public void testResetAll() {
		_mKeys = _inputKeyboard.getKeys();

		// Press all keys
		for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
			_inputKeyboard.press(_currentTime + x, x);

		}

		_inputKeyboard.releaseAll();

		// Assert all keys are released
		for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
			assertFalse(_mKeys[x].getPressed());
		}
	}

}
