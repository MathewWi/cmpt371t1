package ca.sandstorm.luminance.test.input;

import ca.sandstorm.luminance.input.MultiTouchFilter;
import android.test.AndroidTestCase;

public class MultiTouchFilterTest extends AndroidTestCase{
	MultiTouchFilter _mtf;
	
	protected void setUp() throws Exception {
		_mtf = new MultiTouchFilter();
		super.setUp();
	}
	
	public void testMultiTouchFilter() throws Exception {
		assertNotNull(_mtf);
	}
}
