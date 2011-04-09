package ca.sandstorm.luminance.test.input;

import ca.sandstorm.luminance.mock.mockTouchFilter;
import android.test.AndroidTestCase;

public class TouchFilterTest extends AndroidTestCase{
	mockTouchFilter _tf;
	
	protected void setUp() throws Exception {
		_tf = new mockTouchFilter();
		super.setUp();
	}
	
	public void testMultiTouchFilter() throws Exception {
		assertNotNull(_tf);
	}
}
