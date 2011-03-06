package ca.sandstorm.luminance.gametools;

import java.util.LinkedList;

import javax.vecmath.Vector2f;

public class Toolbelt
{
    private final float _GRIDPOINT_ERROR = 0.05f;
    private ToolType _selectedTool = ToolType.Mirror;
    
    // Collections of available tools to place
    private LinkedList<Mirror> _mirrors;
    private LinkedList<Prism> _prisms;
    
    public Toolbelt()
    {
	_mirrors = new LinkedList<Mirror>();
	_prisms = new LinkedList<Prism>();
    }
    
    /**
     * Process a mouse/touchpad click.
     * @param x Screen X coordinate
     * @param y Screen Y coordinate
     * @param gridCoords Grid coordinates
     */
    public void processClick(float x, float y, Vector2f gridCoords)
    {
	// Check if click was in the toolbelt area
	// TODO
	if(false) {
	
	} else {
	    // It's a click on the grid
	    // Add a small amount to the coordinates so that casting to int doesn't possibly round down due to float error
	    _gridClick((int)(gridCoords.x + _GRIDPOINT_ERROR), (int)(gridCoords.y + _GRIDPOINT_ERROR));
	}
    }
    
    /**
     * Handle a click that landed on the grid and not on the toolbelt. 
     * @param x Grid X coordinate
     * @param y Grid Y coordinate
     */
    private void _gridClick(int x, int y)
    {
	switch(_selectedTool) {
	case None:
	    break;
	case Mirror:
	    placeMirror(x, y);
	    break;
	case Prism:
	    break;
	case Eraser:
	    break;
	default:
	    break;
	}
    }
    
    /**
     * Place a mirror into the world.
     * @param x Grid X coordinate
     * @param y Grid Y coordinate
     */
    public void placeMirror(int x, int y)
    {
	
    }
    
    /**
     * Add stock for a particular type of tool.
     * @param toolType Tool type
     * @param quantity Quantity of tool to add
     */
    public void addToolStock(ToolType toolType, int quantity)
    {
	switch(toolType) {
	case None:
	    break;
	case Mirror:
	    for(int i = 0; i < quantity; i++) {
		_mirrors.add(new Mirror());
	    }
	    break;
	case Prism:
	    for(int i = 0; i < quantity; i++) {
		_prisms.add(new Prism());
	    }
	    break;
	case Eraser:
	    break;
	default:
	    break;
	}
    }
}
