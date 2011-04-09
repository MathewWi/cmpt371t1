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

	private Plane _tester;
	private float _testX = 1.0f;
	private float _testY = 1.0f;
	private float _testZ = 1.0f;
	private float _testXNormal = 1.0f;
	private float _testYNormal = 1.0f;
	private float _testZNormal = 1.0f;

	protected void setUp() throws Exception {
		super.setUp();
		_tester = new Plane(_testX, _testY, _testZ, _testXNormal, _testYNormal,
				_testZNormal);
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
	 */
	public void testGetPosition() throws Exception {
		Vector3f pos = _tester.getPosition();
		assert (pos.x == _testX);
		assert (pos.y == _testY);
		assert (pos.z == _testZ);
	}

	/*
	 * Testing the setNormal() method
	 */
	public void testSetNormal() throws Exception {
		_testXNormal = (float) 5.7;
		_testYNormal = (float) 7.9;
		_testZNormal = (float) 1.4;
		_tester.setNormal(_testXNormal, _testYNormal, _testZNormal);
	}

	/*
	 * Testing the getNormal() method
	 */
	public void testGetNormal() throws Exception {
		Vector3f norm = _tester.getNormal();
		assert (norm.x == _testXNormal);
		assert (norm.y == _testYNormal);
		assert (norm.z == _testZNormal);
	}
	
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
