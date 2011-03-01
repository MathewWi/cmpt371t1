package ca.sandstorm.luminance.test.usecase;
import org.junit.After;
import org.junit.Before;

import android.test.ActivityInstrumentationTestCase2;
import ca.sandstorm.luminance.Luminance;

import com.jayway.android.robotium.solo.Solo;


public class LuminanceTest extends ActivityInstrumentationTestCase2<Luminance> {
	private Solo solo;
	
	public LuminanceTest(){
		super("ca.sandstorm.luminance", Luminance.class);
	}
	@Before
	public void setUp() throws Exception {
		super.setUp();
		this.solo = new Solo(getInstrumentation(), getActivity());
	}

	@After
	public void tearDown() throws Exception {
		try {
			this.solo.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		getActivity().finish();
		super.tearDown();
	}
	public void testDisplayBlackBox() {
		
	}
	public void testDisplayWhiteBox() {

	}

}
