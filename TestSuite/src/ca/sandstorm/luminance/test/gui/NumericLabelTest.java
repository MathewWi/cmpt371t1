package ca.sandstorm.luminance.test.gui;

import ca.sandstorm.luminance.gui.NumericLabel;
import android.test.AndroidTestCase;

public class NumericLabelTest extends AndroidTestCase{
	
	NumericLabel nl;
	
	protected void setUp() throws Exception{
		nl = new NumericLabel(0,0,0,0,0);
		super.setUp();
	}
	
	public void testNumericLabel() throws Exception {
		assertNotNull(nl);
	}
}
