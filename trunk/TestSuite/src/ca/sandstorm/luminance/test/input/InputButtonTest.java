package ca.sandstorm.luminance.test.input;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.input.InputButton;

/**
 * Testing InputButton functionality
 * @author Amara Daal
 * 
 */
public class InputButtonTest extends AndroidTestCase {

	InputButton _inputButton;
	float _currentTime = 2.0f;
	float _magnitude = 1.0f;

	/**
	 * @throws java.lang.Exception
	 */

	public void setUp() throws Exception {
		super.setUp();
		_inputButton = new InputButton();
		assertNotNull(_inputButton);

	}

	/**
	 * @throws java.lang.Exception
	 */

	public void tearDown() throws Exception {
		super.tearDown();

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputButton#press(float, float)}
	 * .
	 */

	public void testPress() {
		_inputButton.press(_currentTime, _magnitude);
		assertTrue(_inputButton.getPressed());
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputButton#release()}.
	 */

	public void testRelease() {
		_inputButton.release();
		assertFalse(_inputButton.getPressed());
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputButton#getPressed()}.
	 */

	public void testGetPressed() {
		_inputButton.press(_currentTime, _magnitude);
		assertTrue(_inputButton.getPressed());
		_inputButton.release();
		assertFalse(_inputButton.getPressed());

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputButton#getPressedDuration(float)}
	 * .
	 */

	public void testGetPressedDuration() {
		float newTime = _currentTime;

		_inputButton.press(_currentTime, _magnitude);
		assert (_inputButton.getPressedDuration(_currentTime) < 0.001f);

		newTime += 3;
		assertEquals(_inputButton.getPressedDuration(newTime), newTime
				- _currentTime, .001f);

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputButton#getLastPressedTime()}
	 * .
	 */

	public void testGetLastPressedTime() {
		_inputButton.press(_currentTime, _magnitude);
		assertEquals(_currentTime, _inputButton.getLastPressedTime(), .001f);

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputButton#getMagnitude()}.
	 */

	public void testGetMagnitude() {
		_inputButton.press(_currentTime, _magnitude);
		float mg = _inputButton.getMagnitude();
		assertEquals(_magnitude, mg, .001f);

		_inputButton.release();
		mg = _inputButton.getMagnitude();
		assertEquals(0.0f, mg, 0.001f);

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputButton#setMagnitude(float)}
	 * .
	 */

	public void testSetMagnitude() {
		_inputButton.reset();
		float newMg = 0.01f;
		_inputButton.press(0, 2.0f);
		_inputButton.setMagnitude(newMg);
		float mg = _inputButton.getMagnitude();
		assertEquals(newMg, mg, 0.01f);

		newMg = -0.01f;
		_inputButton.setMagnitude(newMg);
		mg = _inputButton.getMagnitude();
		assertEquals(newMg, mg, 0.001f);

		newMg = 99.00f;
		_inputButton.setMagnitude(newMg);
		mg = _inputButton.getMagnitude();
		assertEquals(newMg, mg, 0.001f);

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputButton#reset()}.
	 */

	public void testReset() {
		_inputButton.reset();
		float mg = _inputButton.getMagnitude();
		assert (mg < 0.001f);

	}

}
