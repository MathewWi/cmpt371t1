package ca.sandstorm.luminance.test;

import ca.sandstorm.luminance.Luminance;
import android.test.ActivityUnitTestCase;

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
