package ca.sandstorm.luminance.test.math;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.math.Ray;

/**
 * Testing of the Ray class
 * 
 * @author Chet
 * 
 */
public class RayTest extends AndroidTestCase {
	private Ray _tester;
	private float _testX;
	private float _testY;
	private float _testZ;
	private float _testXDir;
	private float _testYDir;
	private float _testZDir;

	protected void setUp() throws Exception {
		super.setUp();
		_tester = new Ray(_testX, _testY, _testZ, _testXDir, _testYDir, _testZDir);
	}

	/*
	 * Testing the setPosition() method
	 */
	public void testSetPosition() throws Exception {
		_testX = (float) 1.7;
		_testY = (float) 2.9;
		_testZ = (float) 5.4;
		_tester.setPosition(_testX, _testY, _testZ);
	}

	/*
	 * Testing the getPosition() method
	 *
	public void testGetPosition() throws Exception {
		Vector3f pos = tester.getPosition();
		assert (pos.getX() == testX);
		assert (pos.getY() == testY);
		assert (pos.getZ() == testZ);
	}

	/*
	 * Testing the setDirection() method
	 *
	public void testSetDirection() throws Exception {
		testXDir = (float) 5.7;
		testYDir = (float) 7.9;
		testZDir = (float) 1.4;
		tester.setDirection(testXDir, testYDir, testZDir);
	}

	/*
	 * Testing the getDirection() method
	 *
	public void testGetDirection() throws Exception {
		Vector3f norm = tester.getDirection();
		assert (norm.getX() == testXDir);
		assert (norm.getY() == testYDir);
		assert (norm.getZ() == testZDir);
	}

	/*
	 * 
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
