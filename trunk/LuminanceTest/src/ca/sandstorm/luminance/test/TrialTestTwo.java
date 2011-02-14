package ca.sandstorm.luminance.test;

import ca.sandstorm.luminance.Luminance;
import android.test.ActivityUnitTestCase;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class ca.sandstorm.luminance.LuminanceTest \
 * ca.sandstorm.luminance.tests/android.test.InstrumentationTestRunner
 */
public class TrialTestTwo extends ActivityUnitTestCase<Luminance> {

    private Luminance myAct;

    public TrialTestTwo() {
        super(Luminance.class);
    }

    protected void setUp() throws Exception{
    	super.setUp();
    	myAct = this.getActivity();
    }

    public void testOne(){
    	assertTrue("THIS FAILS", false);
    }

    public void testTwo(){
    	assertNotNull(myAct);
    }
}
