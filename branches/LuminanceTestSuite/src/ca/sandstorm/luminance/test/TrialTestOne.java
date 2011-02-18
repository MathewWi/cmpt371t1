package ca.sandstorm.luminance.test;

import android.test.suitebuilder.TestSuiteBuilder;
import junit.framework.Test;
import junit.framework.TestSuite;

public class TrialTestOne extends TestSuite{
	public static Test Suite(){
		return new TestSuiteBuilder(TrialTestOne.class).includeAllPackagesUnderHere().build();
	}
	
}
