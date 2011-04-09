package ca.sandstorm.luminance.test.gamelogic;
import org.junit.Before;
import ca.sandstorm.luminance.gamelogic.*;
import ca.sandstorm.luminance.gameobject.LightBeamCollection;
import android.test.AndroidTestCase;

/**
 * @author Chet W Collins
 * Testing the LightPath class
 */
public class LightPathTest extends AndroidTestCase {
	
	private LightPath _path;
	private LightBeamCollection _collection;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		_path = new LightPath();
	}
	
	/**
	 * Testing the getLightPaths() method
	 * @throws Exception
	 */
	public void testGetLightPaths() throws Exception {
		_collection = _path.getLightPaths();
		assert(_collection != null);
	}
}
