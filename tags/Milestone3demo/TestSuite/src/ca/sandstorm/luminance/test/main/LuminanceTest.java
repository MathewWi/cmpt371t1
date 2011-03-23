/**
 * 
 */
package ca.sandstorm.luminance.test.main;

import android.test.AndroidTestCase;
import android.view.KeyEvent;
import android.view.MotionEvent;
import ca.sandstorm.luminance.Luminance;


/**
 * Testing Luminance android main class
 * @author Amara Daal
 *
 */
public class LuminanceTest extends AndroidTestCase
{
	Luminance luminance;

    /**
     * Constructor
     */
    public LuminanceTest()
    {
	super();

    }


    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
	super.setUp();
	luminance = new Luminance();
    }


    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception
    {
	super.tearDown();
    }

    /**
     * Test method for {@link ca.sandstorm.luminance.Luminance#onTouchEvent(android.view.MotionEvent)}.
     */
    public void testOnTouchEventMotionEvent()
    {
    	//assertTrue(luminance.onTouchEvent(MotionEvent.obtain(1, 2, MotionEvent.ACTION_DOWN, 4, 5, 2)));
    }
    /**
     * Test method for {@link ca.sandstorm.luminance.Luminance#onKeyDown(int, android.view.KeyEvent)}.
     */
    public void testOnKeyDownIntKeyEvent()
    {
	int keyCode = 4;
	KeyEvent keyEvent = new KeyEvent(keyCode, keyCode);
	assertNotNull(luminance.onKeyDown(KeyEvent.ACTION_DOWN, keyEvent));
    
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Luminance#onKeyUp(int, android.view.KeyEvent)}.
     */
    public void testOnKeyUpIntKeyEvent()
    {
	int keyCode = 4;
	KeyEvent keyEvent = new KeyEvent(keyCode, keyCode);
	//TODO: this function always returns false tell devs to fix, currently returning null
	//assertNotNull(luminance.onKeyUp(KeyEvent.ACTION_DOWN, keyEvent));
	
    }




}
