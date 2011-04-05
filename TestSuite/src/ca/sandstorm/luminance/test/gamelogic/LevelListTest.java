package ca.sandstorm.luminance.test.gamelogic;
import org.junit.Before;
import ca.sandstorm.luminance.gamelogic.LevelList;
import android.test.AndroidTestCase;

/**
 * @author Chet W Collins
 * Testing the LevelList class
 */
public class LevelListTest extends AndroidTestCase{
	
	private LevelList list;

	/**
	 * Setting up required instances
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		list = new LevelList("BasicPack.lst");
	}
	
	/**
	 * Testing the constructor
	 * @throws Exception
	 */
	public void testLevelList() throws Exception {
		assert(list != null);
	}
	
	/**
	 * Testing the getCurrentLevel method
	 * @throws Exception
	 */
	public void testGetCurrentLevel() throws Exception {
		assert(list.getCurrentLevel().equals("level1.xml"));
	}
	
	/**
	 * Testing the getCurrentLevelIndex method
	 * @throws Exception
	 */
	public void testGetCurrentLevelIndex() throws Exception {
		assert(list.getCurrentLevelIndex() == 0);
	}

	/**
	 * Testing the setCurrentLevel method
	 * @throws Exception
	 */
	public void testSetCurrentLevel() throws Exception {
		list.setCurrentLevel(3);
		assert(list.getCurrentLevelIndex() == 3);
	}
	
	/**
	 * Testing the iterateNextLevel method
	 * @throws Exception
	 */
	public void testIterateNextLevel() throws Exception {
		list.iterateNextLevel();
		assert(list.getCurrentLevelIndex() == 4);
		list.iterateNextLevel();
		assert(list.getCurrentLevelIndex() == 5);
	}
	
	/**
	 * Testing the isPackFinished method
	 * @throws Exception
	 */
	public void testIsPackFinished() throws Exception {
		list.setCurrentLevel(0);
		assert(!list.isPackFinished());
		list.setCurrentLevel(500);
		assert(list.isPackFinished());
	}
}
