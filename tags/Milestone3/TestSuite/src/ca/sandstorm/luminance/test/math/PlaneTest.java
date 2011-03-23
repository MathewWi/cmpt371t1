package ca.sandstorm.luminance.test.math;

import javax.vecmath.Vector3f;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.math.Plane;

/**
 * Testing of the Plane class
 * 
 * @author Chet
 * 
 */
public class PlaneTest extends AndroidTestCase {

	private Plane tester;
	private float testX = 1.0f;
	private float testY = 1.0f;
	private float testZ = 1.0f;
	private float testXNormal = 1.0f;
	private float testYNormal = 1.0f;
	private float testZNormal = 1.0f;

	protected void setUp() throws Exception {
		super.setUp();
		tester = new Plane(testX, testY, testZ, testXNormal, testYNormal,
				testZNormal);
	}

	/*
	 * Testing the setPosition() method
	 */
	public void testSetPosition() throws Exception {
		testX = (float) 1.7;
		testY = (float) 2.9;
		testZ = (float) 5.4;
		tester.setPosition(testX, testY, testZ);
	}

	/*
	 * Testing the getPosition() method
	 */
	public void testGetPosition() throws Exception {
		Vector3f pos = tester.getPosition();
		assert (pos.getX() == testX);
		assert (pos.getY() == testY);
		assert (pos.getZ() == testZ);
	}

	/*
	 * Testing the setNormal() method
	 */
	public void testSetNormal() throws Exception {
		testXNormal = (float) 5.7;
		testYNormal = (float) 7.9;
		testZNormal = (float) 1.4;
		tester.setNormal(testXNormal, testYNormal, testZNormal);
	}

	/*
	 * Testing the getNormal() method
	 */
	public void testGetNormal() throws Exception {
		Vector3f norm = tester.getNormal();
		assert (norm.getX() == testXNormal);
		assert (norm.getY() == testYNormal);
		assert (norm.getZ() == testZNormal);
	}
	
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
