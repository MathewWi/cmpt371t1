
package ca.sandstorm.luminance.test.input;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.test.AndroidTestCase;
import android.view.KeyEvent;
import ca.sandstorm.luminance.input.InputButton;
import ca.sandstorm.luminance.input.InputKeyboard;

/**
 * @author Amara Daal
 *
 */
public class InputKeyboardTest extends AndroidTestCase
{

    InputKeyboard inputKeyboard;
    float currentTime = 2.0f;
    InputButton[] mKeys;
	
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
	super.setUp();
	inputKeyboard = new InputKeyboard();
	
	
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
     * Test method for {@link ca.sandstorm.luminance.test.input.InputKeyboard#press(float, int)}.
     */
    @Test
    public void testPress()
    {

	mKeys = inputKeyboard.getKeys();
	
	//Assert all keys are intialized to not pressed
	for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
	    assertFalse(mKeys[x].getPressed());
	}
	//Press all keys
	for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
	    inputKeyboard.press(currentTime+x, x);
	    
	}

	//Assert all keys were pressed
	for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
	    assertTrue(mKeys[x].getPressed());
	    assertEquals(currentTime+x, mKeys[x].getLastPressedTime());
	}
	//Release all keys
	inputKeyboard.releaseAll();
	
	//Press individual keys, observe effect on others
	inputKeyboard.press(currentTime, 0);
	inputKeyboard.press(currentTime, 5);
	inputKeyboard.press(currentTime, 4);
	inputKeyboard.press(currentTime, 2);
	inputKeyboard.press(currentTime, 6);
	inputKeyboard.press(currentTime, 3);
	inputKeyboard.press(currentTime, 1);
	inputKeyboard.press(currentTime, KeyEvent.getMaxKeyCode()-1);
	
	//Assert all selected keys were pressed
	for (int x = 0; x < 7; x++) {
	    assertTrue(mKeys[x].getPressed());
	}
	assertTrue(mKeys[KeyEvent.getMaxKeyCode()-1].getPressed());
	
	//Assert all other keys were not pressed
	for (int x = 7; x < KeyEvent.getMaxKeyCode()-1; x++) {
	    assertFalse(mKeys[x].getPressed());
	}
	
	inputKeyboard.resetAll();
	
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputKeyboard#release(int)}.
     */
    @Test
    public void testRelease()
    {

	inputKeyboard.resetAll();
	
	//Press all keys
	for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
	    inputKeyboard.press(currentTime, x);
	    
	}
	
	//Release all keys
	for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
	    inputKeyboard.release(x);
	    
	}
	
	//Assert all other keys are released
	for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
	    assertFalse(mKeys[x].getPressed());
	}
	

    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputKeyboard#releaseAll()}.
     */
    @Test
    public void testReleaseAll()
    {
	
	//Press all keys
	for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
	    inputKeyboard.press(currentTime, x);
	    
	}
	inputKeyboard.releaseAll();
	
	//Assert all keys were released
	for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
	    assertFalse(mKeys[x].getPressed());
	}
	
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputKeyboard#getKeys()}.
     */
    @Test
    public void testGetKeys()
    {
	mKeys = null;
	mKeys = inputKeyboard.getKeys();
	assertNotNull(mKeys);
	
	//Press individual keys, observe effect on others
	inputKeyboard.press(currentTime, 0);
	inputKeyboard.press(currentTime, 5);
	inputKeyboard.press(currentTime, 4);
	inputKeyboard.press(currentTime, 2);
	inputKeyboard.press(currentTime, 6);
	inputKeyboard.press(currentTime, 3);
	inputKeyboard.press(currentTime, 1);
	inputKeyboard.press(currentTime, KeyEvent.getMaxKeyCode()-1);
	
	//Assert all buttons not null
	for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
	    assertNotNull(mKeys[x]);
	}
	
	//Assert all selected keys were pressed
	for (int x = 0; x < 7; x++) {
	    assertTrue(mKeys[x].getPressed());
	}
	assertTrue(mKeys[KeyEvent.getMaxKeyCode()-1].getPressed());
	
	inputKeyboard.resetAll();

    }


    /**
     * Test method for {@link ca.sandstorm.luminance.test.input.InputKeyboard#resetAll()}.
     */
    @Test
    public void testResetAll()
    {
	mKeys = inputKeyboard.getKeys();
	
	//Press all keys
	for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
	    inputKeyboard.press(currentTime+x, x);
	    
	}
	
	inputKeyboard.releaseAll();
	
	//Assert all keys are released
	for (int x = 0; x < KeyEvent.getMaxKeyCode(); x++) {
	    assertFalse(mKeys[x].getPressed());
	}
    }

}
