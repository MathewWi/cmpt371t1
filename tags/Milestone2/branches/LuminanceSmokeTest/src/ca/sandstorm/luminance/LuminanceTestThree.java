package ca.sandstorm.luminance;

import android.test.ActivityInstrumentationTestCase2;

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
public class LuminanceTestThree extends ActivityInstrumentationTestCase2<Luminance> {

    public LuminanceTestThree() {
        super("ca.sandstorm.luminance", Luminance.class);
    }

    public void testThreeOne(){
	assertTrue("SHITZ TRUE SON", true);
    }

    public void testThreeTwo(){
	assertTrue("SHITZ NOT TRUE", true);
    }

}
