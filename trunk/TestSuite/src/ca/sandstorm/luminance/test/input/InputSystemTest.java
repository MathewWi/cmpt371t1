package ca.sandstorm.luminance.test.input;
<<<<<<< .mine
import ca.sandstorm.luminance.input.InputSystem;
import ca.sandstorm.luminance.input.*;
import android.test.AndroidTestCase;
import android.view.KeyEvent;


=======
>>>>>>> .r676
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.test.AndroidTestCase;
import android.view.KeyEvent;
import ca.sandstorm.luminance.input.InputButton;
import ca.sandstorm.luminance.input.InputSystem;

/**
 * @author Amara Daal
 *
 */
public class InputSystemTest extends AndroidTestCase
{

    InputSystem inputSystem;
    
    /**
     * @throws java.lang.Exception
     */
    
    public void setUp() throws Exception
    {
	super.setUp();
	inputSystem = new InputSystem();
	
    }


    /**
     * @throws java.lang.Exception
     */
    
    public void tearDown() throws Exception
    {
	super.tearDown();
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputSystem#InputSystem()}.
     */
    
    public void testInputSystem()
    {
	InputSystem newInputSystem = new InputSystem();
	assertNotNull(newInputSystem);
	
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputSystem#reset()}.
     */
    
    public void testReset()
    {
    	inputSystem.getTrackball().press(0, 2, 2);
    	inputSystem.getKeyboard().press(0, 2);
    	inputSystem.getTouchScreen().press(0, 0, 2, 2);
    	inputSystem.getOrientationSensor().press(0, 2, 2);
    	
    	inputSystem.reset();
    	
    	assertFalse(inputSystem.getTrackball().getPressed());
    	assertFalse(inputSystem.getKeyboard().getKeys()[2].getPressed());
    	assertFalse(inputSystem.getTouchScreen().getPressed(0));
    	assertFalse(inputSystem.getOrientationSensor().getPressed());
    	
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputSystem#roll(float, float)}.
     * 
     */
    
    public void testRoll()
    {

	float oldX;
	float oldY;
	float newX;
	float newY;
	
	for(int i= 0; i < 10; i++){
	    
	    oldX = inputSystem.getTrackball().getX();
	    oldY = inputSystem.getTrackball().getY();
	    
	    inputSystem.roll(i, i);
	    
	    newX = inputSystem.getTrackball().getX();
	    newY = inputSystem.getTrackball().getY();
	    
	    //Assert we have reached where we wanted
	    assertEquals(newX, oldX+i, .001f);
	    assertEquals(newY, oldY+i, .001f);
	    
	    //rollback
	    if(i> 1){
		inputSystem.roll(-(i/2), -(i/3));
		
		assertEquals(newX+-(i/2), inputSystem.getTrackball().getX(), 0.001f);
		assertEquals(newY+-(i/3), inputSystem.getTrackball().getY(), 0.001f);
	    }
	    
	}
	
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputSystem#touchDown(int, float, float)}.
     * 
     */
    
  /*  public void testTouchDown()
    {

	//Assert indices are not touched, touch them
	for(int i= 0; i < 10; i++){
	    assertFalse(inputSystem.getTouchScreen().getPressed(i));
	    
	    inputSystem.touchDown(i, i, i);
	    assertTrue(inputSystem.getTouchScreen().getPressed(i));
	    
	    //Assert x and y of event equals input
	    //assertEquals(i, inputSystem.getTouchScreen().getTouchEvent().getRawX(), 0.001f);
	    //assertEquals(i, inputSystem.getTouchScreen().getTouchEvent().getRawY(), 0.001f);

	}

    }
*/

    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputSystem#touchUp(int, float, float)}.
     * 
     */
    /*
    public void testTouchUp()
    {
	inputSystem.reset();
	//Touch down
	for(int i= 0; i < 10; i++){
	    inputSystem.touchDown(i, i, i);
	    assertTrue(inputSystem.getTouchScreen().getPressed(i));
	}
	//Release all buttons
	for(int i= 0; i < 10; i++){
	    inputSystem.touchUp(i, i, i);
	    assertFalse(inputSystem.getTouchScreen().getPressed(i));
	}

    }
*/

    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputSystem#setOrientation(float, float, float)}.
     * TODO: contract unclear need to clarify
     */
    
    public void testSetOrientation()
    {

	//Sets the orientation, the actual calculations are
	//done by NVIDIA function TODO: (testing can be done on known values)
	for(int i= 0; i < 10; i++){
	    inputSystem.setOrientation(i, i, i);

	    assertTrue(inputSystem.getOrientationSensor().getPressed());

	}
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputSystem#keyDown(int)}.
     */
    
    public void testKeyDown()
    {
	inputSystem.getKeyboard().resetAll();
	
	//Press select keys
	for(int i= KeyEvent.getMaxKeyCode()- 10; i < KeyEvent.getMaxKeyCode(); i++){
	    
	    inputSystem.keyDown(i);
	    

	}
	
	InputButton[] keysAr = inputSystem.getKeyboard().getKeys();
	
	//Assert all chosen keys were pressed
	for(int i= KeyEvent.getMaxKeyCode()- 10; i < KeyEvent.getMaxKeyCode(); i++){
	    
	    assertTrue(keysAr[i].getPressed());
	    

	}
	//Assert no other keys were pressed
	for(int i= 0; i < KeyEvent.getMaxKeyCode()-10; i++){
	    
	    assertFalse(keysAr[i].getPressed());

	}
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputSystem#keyUp(int)}.
     */
    
    public void testKeyUp()
    {
	inputSystem.getKeyboard().resetAll();
	
	//Press all keys
	for(int i= 0; i < KeyEvent.getMaxKeyCode(); i++){
	    
	    inputSystem.keyDown(i);
	    

	}
	InputButton[] keysAr = inputSystem.getKeyboard().getKeys();
	
	//Release select keys
	for(int i= KeyEvent.getMaxKeyCode()- 10; i < KeyEvent.getMaxKeyCode(); i++){
	    
	    inputSystem.keyUp(i);
	    
	    assertFalse(keysAr[i].getPressed());
	    

	}
	//Assert all other keys are still pressed
	for(int i= 0; i < KeyEvent.getMaxKeyCode()-10; i++){
	    
	    assertTrue(keysAr[i].getPressed());

	}

    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputSystem#releaseAllKeys()}.
     */
    
    public void testReleaseAllKeys()
    {
	inputSystem.reset();
	
	//Activate trackball
	inputSystem.roll(1, 1);
	assertTrue(inputSystem.getTrackball().getPressed());
	
	//Activate all keys
	for(int i= 0; i < KeyEvent.getMaxKeyCode(); i++){
	    inputSystem.keyDown(i);
	}
	//Activate touch screen
	for(int i= 0; i < 10; i++){
	    inputSystem.touchDown(i, i, i);
	}
	
	//Activate orientation
	inputSystem.setOrientation(1f, 1f, 1f);
	assertTrue(inputSystem.getOrientationSensor().getPressed());
	
	inputSystem.releaseAllKeys();
	
	//Verify trackballs
	assertFalse(inputSystem.getTrackball().getPressed());
	
	//Verify Keyboard
	InputButton[] keysAr = inputSystem.getKeyboard().getKeys();
	for(int i= 0; i < KeyEvent.getMaxKeyCode(); i++){
	    
	    assertFalse(keysAr[i].getPressed());
	}
	
	//Verify touch screen
	for(int i=0; i < 10; i++){
	    
	    assertFalse(inputSystem.getTouchScreen().getPressed(i));
	}
	
	//Verify orienation sensor
	assertFalse(inputSystem.getOrientationSensor().getPressed());

    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputSystem#getTouchScreen()}.
     */
    
    public void testGetTouchScreen()
    {
	assertNotNull(inputSystem.getTouchScreen());

    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputSystem#getOrientationSensor()}.
     */
    
    public void testGetOrientationSensor()
    {
	assertNotNull(inputSystem.getOrientationSensor());
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputSystem#getTrackball()}.
     */
    
    public void testGetTrackball()
    {
	assertNotNull(inputSystem.getTrackball());
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputSystem#getKeyboard()}.
     */
    
    public void testGetKeyboard()
    {
	assertNotNull(inputSystem.getKeyboard());
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputSystem#setScreenRotation(int)}.
     */
    
    public void testSetScreenRotation()
    {
	/**
	 * TODO: Need to test canonical orientation first
	 */
	assert(true);
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputSystem#canonicalOrientationToScreenOrientation(int, float[], float[])}.
     */
    
    public void testCanonicalOrientationToScreenOrientation()
    {
	/**
	 * TODO: Needs to be tested
	 */
    }

}
