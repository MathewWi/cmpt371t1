package ca.sandstorm.luminance.test.time;
import android.test.AndroidTestCase;

import ca.sandstorm.luminance.time.TimeSystem;

/**
 * Testing of the TimeSystem class of the gameobject package
 * @author Martina Nagy
 *
 */
public class TimeSystemTest extends AndroidTestCase {
	TimeSystem myTimeSystem;

	/**
	 * Create an instance of Box to test. 
	 */
	protected void setUp() throws Exception {
	super.setUp();
	myTimeSystem = new TimeSystem();
	 }

	/**
	 * Test the reset method.
	 * @throws Exception
	 */
	public void testReset() throws Exception {
	myTimeSystem.reset();
	assertTrue(myTimeSystem.getGameTime() == 0.0f);
	assertTrue(myTimeSystem.getRealTime() == 0.0f);
	assertTrue(myTimeSystem.getFrameDelta() == 0.0f);
	assertTrue(myTimeSystem.getRealTimeFrameDelta() == 0.0f);
	 }
	
	/**
	 * Test the freeze method.
	 * @throws Exception
	 */
	public void testFreeze() throws Exception {
	myTimeSystem.freeze(0.5f);
	//assertTrue(myTimeSystem.freeze(0.5f));
	//TODO: write proper tests
	 }
		
	/**
	 * Test the appyScale method.
	 * @throws Exception
	 */
	public void testAppyScale() throws Exception {
	myTimeSystem.appyScale(0.2f, 0.5f, true);
	//TODO: write asserts for fields this modifies (needs getters first)
	 }
	
	/**
	 * Test the update method.
	 * @throws Exception
	 */
	public void testUpdate() throws Exception {
	myTimeSystem.update(2.0f);
	myTimeSystem.freeze(5.0f);
	assertTrue(myTimeSystem.getRealTime() == 2.0f);
	assertTrue(myTimeSystem.getRealTimeFrameDelta() == 2.0f);
	//assertTrue(myTimeSystem.getFrameDelta() == 0.0f);
	 }
	
	/**
	 * Dispose of the created resource. 
	 */
	protected void tearDown() throws Exception {
	super.tearDown();
	 }
}
