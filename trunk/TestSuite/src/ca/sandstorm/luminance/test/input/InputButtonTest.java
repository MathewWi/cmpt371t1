/**
 * 
 */
package ca.sandstorm.luminance.test.input;

import ca.sandstorm.luminance.input.InputButton;

import android.test.AndroidTestCase;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Amara Daal
 * 
 */
public class InputButtonTest extends AndroidTestCase
{

    InputButton inputButton;
    float currentTime = 2.0f;
    float magnitude = 1.0f;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
	super.setUp();
	inputButton = new InputButton();
	
    }


    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
	super.tearDown();

    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputButton#press(float, float)}.
     */
    @Test
    public void testPress()
    {
	inputButton.press(currentTime, magnitude);
	assertTrue(inputButton.getPressed());
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputButton#release()}.
     */
    @Test
    public void testRelease()
    {
	inputButton.release();
	assertFalse(inputButton.getPressed());
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputButton#getPressed()}.
     */
    @Test
    public void testGetPressed()
    {
	inputButton.press(currentTime, magnitude);
	assertTrue(inputButton.getPressed());
	inputButton.release();
	assertFalse(inputButton.getPressed());

    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputButton#getTriggered(float)}.
     */
    @Test
    public void testGetTriggered()
    {
	fail("Not yet implemented"); // TODO
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputButton#getPressedDuration(float)}.
     */
    @Test
    public void testGetPressedDuration()
    {
	float newTime = currentTime;

	inputButton.press(currentTime, magnitude);
	assert(inputButton.getPressedDuration(currentTime) < 0.001f);
	
	newTime +=3;
	assertEquals(inputButton.getPressedDuration(newTime), newTime-currentTime, .001f);
	
	


    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputButton#getLastPressedTime()}.
     */
    @Test
    public void testGetLastPressedTime()
    {
	
	assertEquals(currentTime, inputButton.getLastPressedTime(), .001f);


    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputButton#getMagnitude()}.
     */
    @Test
    public void testGetMagnitude()
    {
	inputButton.press(currentTime, magnitude);
	float mg = inputButton.getMagnitude();
	assertEquals(magnitude, mg, .001f);
	
	inputButton.release();
	mg = inputButton.getMagnitude();
	assertEquals(0.0f, mg, 0.001f);

    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputButton#setMagnitude(float)}.
     */
    @Test
    public void testSetMagnitude()
    {
	float newMg = 0.01f;
	inputButton.setMagnitude(newMg);
	float mg = inputButton.getMagnitude();
	assertEquals(newMg, mg, 0.001f);
	
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
     * Test method for {@link ca.sandstorm.luminance.test.input.InputButton#reset()}.
     */
    @Test
    public void testReset()
    {
	inputButton.reset();
	float mg = inputButton.getMagnitude();
	assert(mg < 0.001f);
	
    }

}
