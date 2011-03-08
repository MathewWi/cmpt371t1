package ca.sandstorm.luminance.test.gameobject;

import javax.vecmath.Point2i;
import javax.vecmath.Vector3f;

import android.test.AndroidTestCase;
import ca.sandstorm.luminance.gameobject.Grid;
import ca.sandstorm.luminance.math.Plane;

/**
 * Testing of the Grid class of the gameobject package
 * 
 * @author Martina Nagy
 * 
 */
public class GridTest extends AndroidTestCase {
	Grid myGrid;

	/**
	 * Create an instance of Box to test.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		myGrid = new Grid(10, 5, 1.0f, 1.0f);
	}

	/**
	 * Test the getColumnCount method.
	 * 
	 * @throws Exception
	 */
	public void testGetColumnCount() throws Exception {
		assertTrue(myGrid.getColumnCount() == 5);
	}

	/**
	 * Test the getRowCount method.
	 * 
	 * @throws Exception
	 */
	public void testGetRowCount() throws Exception {
		assertTrue(myGrid.getRowCount() == 10);
	}

	/**
	 * Test the getCellWidth method.
	 * 
	 * @throws Exception
	 */
	public void testGetCellWidth() throws Exception {
		assertTrue(myGrid.getCellWidth() == 1.0f);
	}

	/**
	 * Test the getCellHeight method.
	 * 
	 * @throws Exception
	 */
	public void testGetCellHeight() throws Exception {
		assertTrue(myGrid.getCellWidth() == 1.0f);
	}

	/**
	 * Test the getTotalWidth method.
	 * 
	 * @throws Exception
	 */
	public void testGetTotalWidth() throws Exception {
		assertTrue(myGrid.getTotalWidth() == 5.0f);
	}

	/**
	 * Test the getTotalHeight method.
	 * 
	 * @throws Exception
	 */
	public void testGetTotalHeight() throws Exception {
		assertTrue(myGrid.getTotalHeight() == 10.0f);
	}

	/**
	 * Test the getPlane() method.
	 * 
	 * @throws Exception
	 */
	public void testGetPlane() throws Exception {
		Plane myPlane = myGrid.getPlane();
		assertTrue(myPlane != null);

		Vector3f pos = myPlane.getPosition();
		Vector3f norm = myPlane.getNormal();

		assertTrue(pos.x == 0);
		assertTrue(pos.y == 0);
		assertTrue(pos.z == 0);
		assertTrue(norm.x == 0);
		assertTrue(norm.y == 1);
		assertTrue(norm.z == 0);
	}

	/**
	 * Test the getCellCenter() method.
	 * 
	 * @throws Exception
	 */
	public void testGetCellCenter() throws Exception {
		Vector3f dummy = myGrid.getCellCenter(3, 2);
		assertTrue(dummy.x == 2.5);
		assertTrue(dummy.y == 0);
		assertTrue(dummy.z == 3.5);
	}

	/**
	 * Test the getGridPosition() method.
	 * 
	 * @throws Exception
	 */
	/**public void testGetGridPosition() throws Exception {
		Point2i dummy = myGrid.getGridPosition(3.0f, 2.0f, 1.0f);
		assertTrue(dummy.x == 3.0f);
		assertTrue(dummy.y == 1.0f);
	}**/

	/**
	 * Dispose of the created resource.
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
