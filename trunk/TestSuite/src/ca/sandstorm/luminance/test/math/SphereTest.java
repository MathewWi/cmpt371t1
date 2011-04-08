package ca.sandstorm.luminance.test.math;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.math.Sphere;

public class SphereTest extends AndroidTestCase{
	Sphere s;
	
	protected void setUp() throws Exception {
		s = new Sphere(0,0,0,0);
		super.setUp();
	}
	
	public void testSphere() throws Exception {
		assertNotNull(s);
	}
}
