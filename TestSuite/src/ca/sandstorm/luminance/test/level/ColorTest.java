package ca.sandstorm.luminance.test.level;

import ca.sandstorm.luminance.level.Color;
import android.test.AndroidTestCase;

public class ColorTest extends AndroidTestCase{
	Color c;
	
	protected void setUp() throws Exception {
		c = new Color();
		super.setUp();
	}
	
	public void testColor() throws Exception {
		assertNotNull(c);
	}
}
