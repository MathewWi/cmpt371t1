package ca.sandstorm.luminance.test.level;

import ca.sandstorm.luminance.level.Color;
import android.test.AndroidTestCase;

public class ColorTest extends AndroidTestCase{
	Color _c;
	
	protected void setUp() throws Exception {
		_c = new Color();
		super.setUp();
	}
	
	public void testColor() throws Exception {
		assertNotNull(_c);
	}
}
