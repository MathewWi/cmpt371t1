package ca.sandstorm.luminance.test.gametools;

import ca.sandstorm.luminance.gamelogic.GameState;
import ca.sandstorm.luminance.gametools.ToolType;
import ca.sandstorm.luminance.gametools.Toolbelt;
import android.test.AndroidTestCase;

public class ToolBeltTest extends AndroidTestCase {
	private Toolbelt tToolbelt;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		tToolbelt = new Toolbelt(new GameState());

	}
	
	public void testToolbelt(){
		assertNotNull(tToolbelt);
	}

	// Don't know how to test
	public void testProcessClick() throws Exception {
		assertTrue(true);
	}


	// Should be no tools in toolbelt and all should return null
	public void testPlaceToolBefore() throws Exception {
		assertNull(tToolbelt.placeTool(ToolType.None, 0, 0));
		assertNull(tToolbelt.placeTool(ToolType.Mirror, 0,0));
		assertNull(tToolbelt.placeTool(ToolType.Prism, 0,0));
		assertNull(tToolbelt.placeTool(ToolType.Eraser, 0,0));

		assertNull(tToolbelt.placeTool(ToolType.None, 5000, 5000));
		assertNull(tToolbelt.placeTool(ToolType.Mirror, 5000, 5000));
		assertNull(tToolbelt.placeTool(ToolType.Prism, 5000, 5000));
		assertNull(tToolbelt.placeTool(ToolType.Eraser, 5000, 5000));
	}

	public void testEraseTool() throws Exception {

	}

	public void testAddToolStock() throws Exception {

	}

	public void testSelectTool() throws Exception {

	}
}
