package ca.sandstorm.luminance.test.input;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.input.InputButton;

/**
 * 
 * @author Amara Daal
 * 
 */
public class InputButtonTest extends AndroidTestCase {

	InputButton inputButton;
	float currentTime = 2.0f;
	float magnitude = 1.0f;

	/**
	 * @throws java.lang.Exception
	 */

	public void setUp() throws Exception {
		super.setUp();
		inputButton = new InputButton();

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
		inputButton.press(currentTime, magnitude);
		assertTrue(inputButton.getPressed());
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputButton#release()}.
	 */

	public void testRelease() {
		inputButton.release();
		assertFalse(inputButton.getPressed());
	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputButton#getPressed()}.
	 */

	public void testGetPressed() {
		inputButton.press(currentTime, magnitude);
		assertTrue(inputButton.getPressed());
		inputButton.release();
		assertFalse(inputButton.getPressed());

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputButton#getPressedDuration(float)}
	 * .
	 */

	public void testGetPressedDuration() {
		float newTime = currentTime;

		inputButton.press(currentTime, magnitude);
		assert (inputButton.getPressedDuration(currentTime) < 0.001f);

		newTime += 3;
		assertEquals(inputButton.getPressedDuration(newTime), newTime
				- currentTime, .001f);

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputButton#getLastPressedTime()}
	 * .
	 */

	public void testGetLastPressedTime() {
		inputButton.press(currentTime, magnitude);
		assertEquals(currentTime, inputButton.getLastPressedTime(), .001f);

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputButton#getMagnitude()}.
	 */

	public void testGetMagnitude() {
		inputButton.press(currentTime, magnitude);
		float mg = inputButton.getMagnitude();
		assertEquals(magnitude, mg, .001f);

		inputButton.release();
		mg = inputButton.getMagnitude();
		assertEquals(0.0f, mg, 0.001f);

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputButton#setMagnitude(float)}
	 * .
	 */

	public void testSetMagnitude() {
		inputButton.reset();
		float newMg = 0.01f;
		inputButton.press(0, 2.0f);
		inputButton.setMagnitude(newMg);
		float mg = inputButton.getMagnitude();
		assertEquals(newMg, mg, 0.01f);

		newMg = -0.01f;
		inputButton.setMagnitude(newMg);
		mg = inputButton.getMagnitude();
		assertEquals(newMg, mg, 0.001f);

		newMg = 99.00f;
		inputButton.setMagnitude(newMg);
		mg = inputButton.getMagnitude();
		assertEquals(newMg, mg, 0.001f);

	}

	/**
	 * Test method for
	 * {@link ca.sandstorm.luminance.test.input.InputButton#reset()}.
	 */

	public void testReset() {
		inputButton.reset();
		float mg = inputButton.getMagnitude();
		assert (mg < 0.001f);

	}

}
