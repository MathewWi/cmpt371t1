package ca.sandstorm.luminance.gametools;

import java.util.LinkedList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import ca.sandstorm.luminance.gamelogic.GameState;
import ca.sandstorm.luminance.gameobject.Mirror;
import ca.sandstorm.luminance.gameobject.Prism;

public class Toolbelt
{
    private final float _GRIDPOINT_ERROR = 0.05f;
    private ToolType _selectedTool = ToolType.Mirror;
    
    private GameState _gameState;
    
    // Collections of available tools to place
    private LinkedList<MirrorTool> _mirrors;
    private LinkedList<PrismTool> _prisms;
    
    public Toolbelt(GameState gameState)
    {
	_gameState = gameState;
	_mirrors = new LinkedList<MirrorTool>();
	_prisms = new LinkedList<PrismTool>();
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
	    // Select tool based on click coordinates
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
	    placePrism(x, y);
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
     * @return True if placed, false if can't place (out of stock, etc)
     */
    public boolean placeMirror(int x, int y)
    {
	// Check if stock is available
	if(_mirrors.isEmpty()) {
	    return false;
	}
	
	// Check if an object is already at this point
	// TODO
	
	// Create a mirror and place it
	Mirror m = (Mirror)_mirrors.removeFirst().getGameObject();
	Vector2f position = _gameState.gridToScreenCoords(x, y);
	m.setPosition(new Vector3f(position.x, 0, position.y));
	_gameState.addObject(m);
	return true;
    }
    
    /**
     * Place a prism into the world.
     * @param x Grid X coordinate
     * @param y Grid Y coordinate
     * @return True if placed, false if can't place (out of stock, etc)
     */
    public boolean placePrism(int x, int y)
    {
	// Check if stock is available
	if(_prisms.isEmpty()) {
	    return false;
	}
	
	// Check if an object is already at this point
	// TODO
	
	// Create a prism and place it
	Prism p = (Prism)_prisms.removeFirst().getGameObject();
	Vector2f position = _gameState.gridToScreenCoords(x, y);
	p.setPosition(new Vector3f(position.x, 0, position.y));
	_gameState.addObject(p);
	return true;
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
		_mirrors.add(new MirrorTool());
	    }
	    break;
	case Prism:
	    for(int i = 0; i < quantity; i++) {
		_prisms.add(new PrismTool());
	    }
	    break;
	case Eraser:
	    break;
	default:
	    break;
	}
    }
}
