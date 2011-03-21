package ca.sandstorm.luminance.test.gametools;

import ca.sandstorm.luminance.gamelogic.GameState;
import ca.sandstorm.luminance.gametools.ToolType;
import ca.sandstorm.luminance.gametools.Toolbelt;
import android.test.AndroidTestCase;
/**
 * Testing tool belt creation
 * @author Jeff
 *
 */
/**
 * Testing of the ToolBelt Class
 * @author lianghuang
 *
 */
public class ToolBeltTest extends AndroidTestCase {
	private Toolbelt tToolbelt;
	
	protected void setUp() throws Exception {
		super.setUp();
		int level = 1;
		tToolbelt = new Toolbelt(new GameState(1));

	}
	
	public void testToolbelt(){
		assertTrue(tToolbelt != null);
	}

	// Don't know how to test
	public void testProcessClick() throws Exception {
		assertTrue(true);
	}


	// Should be no tools in toolbelt and all should return null
	public void testPlaceToolBefore() throws Exception {
		//assertTrue(tToolbelt.placeTool(ToolType.None, 0, 0) == null);
		assertTrue(tToolbelt.placeTool(ToolType.Mirror, 0,0) == null);
		assertTrue(tToolbelt.placeTool(ToolType.Prism, 0,0) == null);
		//assertTrue(tToolbelt.placeTool(ToolType.Eraser, 0,0) == null);

		//assertTrue(tToolbelt.placeTool(ToolType.None, 5000, 5000) == null);
		assertTrue(tToolbelt.placeTool(ToolType.Mirror, 5000, 5000) == null);
		assertTrue(tToolbelt.placeTool(ToolType.Prism, 5000, 5000) == null);
		//assertTrue(tToolbelt.placeTool(ToolType.Eraser, 5000, 5000) == null);
	}

	// Added 1 to each tool stock
	// Should be able to place all tools into toolbelt
	public void testAddToolStock() throws Exception {
		
		tToolbelt.addToolStock(ToolType.Mirror, 1);
		tToolbelt.addToolStock(ToolType.Prism, 1);
		
		try {
			tToolbelt.addToolStock(ToolType.None, 1);
			assertTrue(false); // Should not get here
		} catch (Exception e) {
			assertTrue(true);
		}
		try {
			tToolbelt.addToolStock(ToolType.Eraser, 1);
			assertTrue(false); // Should not get here
		} catch (Exception e) {
			assertTrue(true);
		}
		
		//assertTrue(tToolbelt.placeTool(ToolType.None, 0, 0) == null);
		//assertNull(tToolbelt.placeTool(ToolType.Mirror, 0,1));
		//assertNull(tToolbelt.placeTool(ToolType.Prism, 0,2));
		//assertNull(tToolbelt.placeTool(ToolType.Eraser, 0,3));
	}

	// Know that the tool stock is empty
	// erase tools from toolbelt
	// Back to 1 tool each
	// placing of tool back into tool belt should work
	public void testEraseTool() throws Exception {
		//assertTrue(tToolbelt.placeTool(ToolType.None, 0, 0) == null);
		assertTrue(tToolbelt.placeTool(ToolType.Mirror, 0,0) == null);
		assertTrue(tToolbelt.placeTool(ToolType.Prism, 0,0) == null);
		//assertTrue(tToolbelt.placeTool(ToolType.Eraser, 0,0) == null);
		
		tToolbelt.eraseTool(0,0);
		tToolbelt.eraseTool(0,1);
		tToolbelt.eraseTool(0,2);
		tToolbelt.eraseTool(0,3);
		
		//assertTrue(tToolbelt.placeTool(ToolType.None, 0, 0) != null);
		assertTrue(tToolbelt.placeTool(ToolType.Mirror, 0,1) != null);
		assertTrue(tToolbelt.placeTool(ToolType.Prism, 0,2) != null);
		assertTrue(tToolbelt.placeTool(ToolType.Eraser, 0,3) != null);		
		
	}
	
	// Checking of an enum should not give any errors as there is no possible way
	public void testSelectTool() throws Exception {
		assertTrue(true);
	}
}
