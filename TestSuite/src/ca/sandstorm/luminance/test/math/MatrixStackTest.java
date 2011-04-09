package ca.sandstorm.luminance.test.math;

import ca.sandstorm.luminance.math.MatrixStack;
import android.test.AndroidTestCase;

public class MatrixStackTest extends AndroidTestCase{
	MatrixStack _ms;
	
	protected void setUp() throws Exception {
		_ms = new MatrixStack(1);
		super.setUp();
	}
	
	public void testMatrixStack() throws Exception {
		assertNotNull(_ms);
	}

}
