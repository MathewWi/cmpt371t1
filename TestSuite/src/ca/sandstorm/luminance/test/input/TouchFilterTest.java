package ca.sandstorm.luminance.test.input;

import ca.sandstorm.luminance.mock.mockTouchFilter;
import android.test.AndroidTestCase;

public class TouchFilterTest extends AndroidTestCase{
	mockTouchFilter tf;
	
	protected void setUp() throws Exception {
		tf = new mockTouchFilter();
		super.setUp();
	}
	
	public void testMultiTouchFilter() throws Exception {
		assertNotNull(tf);
	}
}
