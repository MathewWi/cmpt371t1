package ca.sandstorm.luminance.gametools;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.vecmath.Point2i;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.gamelogic.GameState;
import ca.sandstorm.luminance.gameobject.IGameObject;
import ca.sandstorm.luminance.gameobject.Mirror;
import ca.sandstorm.luminance.gameobject.Prism;

/**
 * Holds the level's tools and provides functionality for placing/removing them.
 * @author zenja
 */
public class Toolbelt
{
    private static final Logger logger = LoggerFactory.getLogger(Toolbelt.class);

    private final float _GRIDPOINT_ERROR = 0.05f;
    private ToolType _selectedTool = ToolType.Mirror;
    
    // Reference to the game state the toolbelt is in so it can be manipulated
    private GameState _gameState;
    
    // Collections of placed tools
    private LinkedList<IGameObject> _tools;

    // Stock available of each tool type
    private HashMap<ToolType, Integer> _stock;
    
    public Toolbelt(GameState gameState)
    {
	_gameState = gameState;
	_tools = new LinkedList<IGameObject>();
	
	_stock = new HashMap<ToolType, Integer>();
	_stock.put(ToolType.Mirror, 0);
	_stock.put(ToolType.Prism, 0);
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
	if(_selectedTool == ToolType.Eraser) {
	    eraseTool(x, y);
	} else {
	    placeTool(_selectedTool, x, y);
	}
    }
    
    /**
     * Place a tool into the world.
     * @param toolType Type of tool to place
     * @param x Grid X coordinate
     * @param y Grid Y coordinate
     * @return Null if unable to place (out of stock, collision, etc) or
     *         the new object if successful.
     */
    public IGameObject placeTool(ToolType toolType, int x, int y)
    {
	assert _stock.containsKey(toolType);
	
	// Check if stock is available
	if(_stock.get(toolType) <= 0) {
	    logger.debug("Out of stock: " + toolType);
	    return null;
	}
	
	// Check if an object is already at this point
	if(_gameState.isCellOccupied(x, y)) {
	    logger.debug("Cell is occupied: " + x + "," + y);
	    return null;
	}
	
	// Create a mirror and place it
	Vector3f position = _gameState.gridToWorldCoords(x, y);
	IGameObject tool = null;
	if(toolType == ToolType.Mirror) { 
	    tool = new Mirror(position, 45f);
	} else if(toolType == ToolType.Prism) {
	    tool = new Prism(position, 0f);
	}
	
	addToolStock(toolType, -1);
	_gameState.addObject(tool);
	_tools.add(tool);
	logger.debug("Placed tool: " + toolType);
	return tool;
    }
    
    /**
     * Erase a previously-placed tool.
     * @param x Grid X coordinate
     * @param y Grid Y coordinate
     */
    public void eraseTool(int x, int y)
    {
	for(IGameObject tool : _tools) {
	    
	    Point2i gridCoords = _gameState.worldToGridCoords(tool.getPosition());
	    if(gridCoords.x == x && gridCoords.y == y) {
		logger.debug("Found tool to erase at " + x + "," + y + ": " + tool);
		if(tool instanceof Mirror) {
		    addToolStock(ToolType.Mirror, 1);
		} else if(tool instanceof Prism) {
		    addToolStock(ToolType.Prism, 1);
		} else {
		    assert false;
		}
		
		_tools.remove(tool);
		_gameState.removeObject(tool);
		break;
	    }
	}
    }
    
    /**
     * Add stock for a particular type of tool.
     * @param toolType Tool type
     * @param quantity Quantity of tool to add
     */
    public void addToolStock(ToolType toolType, int quantity)
    {
	assert _stock.containsKey(toolType);
	int stockQty = _stock.get(toolType);
	stockQty += quantity;
	_stock.put(toolType, stockQty);
    }
    
    /**
     * Set which tool should be active.
     * @param toolType Tool to set as selected
     */
    public void selectTool(ToolType toolType)
    {
	assert _stock.containsKey(toolType);
	_selectedTool = toolType;
	logger.debug("Selected tool: " + toolType);
    }
}
