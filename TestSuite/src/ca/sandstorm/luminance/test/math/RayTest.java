package ca.sandstorm.luminance.test.math;

import javax.vecmath.Vector3f;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.math.Ray;

/**
 * Testing of the Ray class
 * 
 * @author Chet
 * 
 */
public class RayTest extends AndroidTestCase {
	private Ray tester;
	private float testX;
	private float testY;
	private float testZ;
	private float testXDir;
	private float testYDir;
	private float testZDir;

	protected void setUp() throws Exception {
		super.setUp();
		tester = new Ray(testX, testY, testZ, testXDir, testYDir, testZDir);
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
	 * Testing the setDirection() method
	 */
	public void testSetDirection() throws Exception {
		testXDir = (float) 5.7;
		testYDir = (float) 7.9;
		testZDir = (float) 1.4;
		tester.setDirection(testXDir, testYDir, testZDir);
	}

	/*
	 * Testing the getDirection() method
	 */
	public void testGetDirection() throws Exception {
		Vector3f norm = tester.getDirection();
		assert (norm.getX() == testXDir);
		assert (norm.getY() == testYDir);
		assert (norm.getZ() == testZDir);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
