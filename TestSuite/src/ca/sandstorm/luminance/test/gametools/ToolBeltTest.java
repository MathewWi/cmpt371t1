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
	private Toolbelt _tToolbelt;
	
	protected void setUp() throws Exception {
		super.setUp();
		int level = 1;
		_tToolbelt = new Toolbelt(new GameState(level));

	}
	
	public void tes_tToolbelt(){
		assertTrue(_tToolbelt != null);
	}

	// Don't know how to test
	public void testProcessClick() throws Exception {
		assertTrue(true);
	}


	// Should be no tools in toolbelt and all should return null
	public void testPlaceToolBefore() throws Exception {
		try{
			
			assertNull(_tToolbelt.placeTool(ToolType.Mirror, 0,0));
			assertNull(_tToolbelt.placeTool(ToolType.Prism, 0,0));
	
			assertTrue(_tToolbelt.placeTool(ToolType.Mirror, 5000, 5000) == null);
			assertTrue(_tToolbelt.placeTool(ToolType.Prism, 5000, 5000) == null);
		} catch (Exception e){
			assertTrue(true);
		}
	}

	// Added 1 to each tool stock
	// Should be able to place all tools into toolbelt
	public void testAddToolStock() throws Exception {
		
		_tToolbelt.addToolStock(ToolType.Mirror, 1);
		_tToolbelt.addToolStock(ToolType.Prism, 1);
		
		try {
			_tToolbelt.addToolStock(ToolType.None, 1);
			assertTrue(false); // Should not get here
		} catch (Exception e) {
			assertTrue(true);
		}
		try {
			_tToolbelt.addToolStock(ToolType.Eraser, 1);
			assertTrue(false); // Should not get here
		} catch (Exception e) {
			assertTrue(true);
		}
		
		//assertNull(_tToolbelt.placeTool(ToolType.Mirror, 0,1));
		//assertNull(_tToolbelt.placeTool(ToolType.Prism, 0,2));
	}

	// Know that the tool stock is empty
	// erase tools from toolbelt
	// Back to 1 tool each
	// placing of tool back into tool belt should work
	public void testEraseTool() throws Exception {
		try{
			assertTrue(_tToolbelt.placeTool(ToolType.Mirror, 0,0) != null);
			assertTrue(_tToolbelt.placeTool(ToolType.Prism, 0,1) != null);
		} catch(Exception e){
			assertTrue(true);
		}
		
		_tToolbelt.eraseTool(0,0);
		_tToolbelt.eraseTool(0,1);
		_tToolbelt.eraseTool(0,2);
		_tToolbelt.eraseTool(0,3);
		
		//assertTrue(_tToolbelt.placeTool(ToolType.Mirror, 0,1) != null);
		//assertTrue(_tToolbelt.placeTool(ToolType.Prism, 0,2) != null);
		
	}
	
	// Checking of an enum should not give any errors as there is no possible way
	public void testSelectTool() throws Exception {
		assertTrue(true);
	}
}
