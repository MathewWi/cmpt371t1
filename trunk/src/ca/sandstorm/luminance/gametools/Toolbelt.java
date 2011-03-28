package ca.sandstorm.luminance.gametools;

import java.util.HashMap;

import javax.vecmath.Point2i;
import javax.vecmath.Vector3f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.gamelogic.GameState;
import ca.sandstorm.luminance.gameobject.IGameObject;
import ca.sandstorm.luminance.gameobject.Mirror;
import ca.sandstorm.luminance.gameobject.Prism;
import ca.sandstorm.luminance.gui.Button;
import ca.sandstorm.luminance.gui.NumericLabel;
import ca.sandstorm.luminance.resources.TextureResource;
import ca.sandstorm.luminance.resources.SoundResource;

/**
 * Holds the level's tools and provides functionality for placing/removing them.
 * @author zenja
 */
public class Toolbelt
{
    private static final Logger logger = LoggerFactory.getLogger(Toolbelt.class);

    private ToolType _selectedTool = ToolType.Mirror;
        
    // Reference to the game state the toolbelt is in so it can be manipulated
    private GameState _gameState;
        
    // Collection of placed tools
    private HashMap<Point2i, IGameObject> _tools;

    // Stock available of each tool type
    private HashMap<ToolType, Integer> _stock;
    private HashMap<ToolType, NumericLabel> _stockLabel;
    
    private boolean _mirrorIconAdded = false;
    private boolean _prismIconAdded = false;
    private Point2i _tempPoint;
    
    private float _toolIconSizeWidth;
    private float _toolIconSizeHeight;
    private float _toolIconYPos;
    
    private Button _prevTouchedButton = null;
    
    public Toolbelt(GameState gameState)
    {
	_gameState = gameState;
	_tempPoint = new Point2i();
	_tools = new HashMap<Point2i, IGameObject>();
	
	_stock = new HashMap<ToolType, Integer>();
	_stock.put(ToolType.Mirror, 0);
	_stock.put(ToolType.Prism, 0);
	
	_stockLabel = new HashMap<ToolType, NumericLabel>();
	
	addGui();
    }
    
    /**
     * Process a mouse/touchpad click.
     * @param x Screen X coordinate
     * @param y Screen Y coordinate
     * @param gridCoords Grid coordinates
     */
    public void processClick(float x, float y, Point2i gridCoords)
    {
	// Check if click was in the toolbelt area
	// TODO: GUIManager gives button callback functionality, so perhaps change it to
	//       that instead of checking button titles like this
	Button touchedButton = _gameState.getGui().touchOccured(x, y);
	if (touchedButton != null) {
	    Engine.getInstance().getAudio().play((SoundResource)Engine.getInstance().getResourceManager().getResource("sounds/iconClick.ogg"), 0.9f);
	    if (touchedButton.getTitle().equalsIgnoreCase("mirror")) {
		logger.debug("mirror has been tapped");
		//touchedButton.setIsTapped(true);
		selectTool(ToolType.Mirror);
	    } else if (touchedButton.getTitle().equalsIgnoreCase("prism")) {
		logger.debug("prism has been tapped");
		//touchedButton.setIsTapped(true);
		selectTool(ToolType.Prism);
	    } else if (touchedButton.getTitle().equalsIgnoreCase("eraser")) {
		logger.debug("eraser has been tapped");
		//touchedButton.setIsTapped(true);
		selectTool(ToolType.Eraser);
	    }
	    touchedButton.setIsSelected(true);

	    if (!touchedButton.equals(_prevTouchedButton)){
		if(_prevTouchedButton != null){
		    _prevTouchedButton.setIsSelected(false);
		}
		_prevTouchedButton = touchedButton;  
	    }
	    
	} else if (gridCoords != null) {
	    // It's a click on the grid
	    // Add a small amount to the coordinates so that casting to int doesn't possibly round down due to float error
	    _gridClick(gridCoords.x, gridCoords.y);
	}
    }
    
    
    public void processDoubleClick(float x, float y, Point2i gridCoords)
    {
	if (gridCoords != null)
	{
	    _gameState.rotateObjectAtGridCoords(gridCoords.x, gridCoords.y);
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
	    Engine.getInstance().getAudio().play((SoundResource)Engine.getInstance().getResourceManager().getResource("sounds/noPlace.ogg"), 0.9f);
	    return null;
	}
	
	// Check if an object is already at this point
	if(_gameState.isCellOccupied(x, y)) {
	    logger.debug("Cell is occupied: " + x + "," + y);
	    Engine.getInstance().getAudio().play((SoundResource)Engine.getInstance().getResourceManager().getResource("sounds/noPlace.ogg"), 0.9f);
	    return null;
	}
	
	// Create an object and place it
	Vector3f position = _gameState.gridToWorldCoords(x, y);
	position.y += 0.5f;  // line bottom up with the grid
	IGameObject tool = null;
	if(toolType == ToolType.Mirror) { 
	    tool = new Mirror(position);
	} else if(toolType == ToolType.Prism) {
	    tool = new Prism(position, new Vector3f(0, 0, 0));
	}
	
	addToolStock(toolType, -1);
	_gameState.addObject(tool);
	_tools.put(new Point2i(x, y), tool);
	logger.debug("Placed tool: " + toolType);
	
	// Play sound effect
	Engine.getInstance().getAudio().play((SoundResource)Engine.getInstance().getResourceManager().getResource("sounds/place.ogg"), 0.9f);
	
	return tool;
    }
    
    /**
     * Erase a previously-placed tool.
     * @param x Grid X coordinate
     * @param y Grid Y coordinate
     */
    public void eraseTool(int x, int y)
    {
	_tempPoint.x = x;
	_tempPoint.y = y;
	IGameObject tool = _tools.get(_tempPoint);
	
	if(tool != null) {
	    logger.debug("Found tool to erase at " + x + "," + y + ": " + tool);
	    if(tool instanceof Mirror) {
		addToolStock(ToolType.Mirror, 1);
	    } else if(tool instanceof Prism) {
		addToolStock(ToolType.Prism, 1);
	    } else {
		assert false;
	    }

	    // Remove the object both from gamestate and our collection of tools
	    _tools.remove(_tempPoint);
	    _gameState.removeObject(tool);
	    
	    Engine.getInstance().getAudio().play((SoundResource)Engine.getInstance().getResourceManager().getResource("sounds/eraser.ogg"), 0.9f);
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
	
	// Update stock
	int stockQty = _stock.get(toolType);
	stockQty += quantity;
	_stock.put(toolType, stockQty);

	// Draw the widget if needed
	if(toolType == ToolType.Mirror && !_mirrorIconAdded) {
	    _addMirrorIcon();
	} else if(toolType == ToolType.Prism && !_prismIconAdded) {
	    _addPrismIcon();
	}
	
	// Update stock on icon
	NumericLabel label = _stockLabel.get(toolType);
	label.setNumber(_stock.get(toolType));
	_stockLabel.put(toolType, label);
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
    
    /**
     * Add all the toolbelt GUI icons. Typically called after an orientation flip.
     */
    public void addGui()
    {
	_toolIconSizeWidth = Engine.getInstance().getViewWidth() * 0.20f;
	_toolIconSizeHeight = Engine.getInstance().getViewHeight() * 0.18f;
	_toolIconYPos = Engine.getInstance().getViewHeight() * 0.80f;

	Button eraserButton = new Button(_toolIconSizeWidth*2, 
	                                 _toolIconYPos, 
	                                 _toolIconSizeWidth, 
	                                 _toolIconSizeHeight, "Eraser");
	eraserButton.setTextureResourceLocation("textures/eraser.png");
	eraserButton.setTappedTextureLocation("textures/eraserClicked.png");
	_gameState.getGui().addButton(eraserButton);

	if(_stock.get(ToolType.Mirror) > 0)
	    _addMirrorIcon();
	if(_stock.get(ToolType.Prism) > 0)
	    _addPrismIcon();
    }
    
    /**
     * Add mirror icon and quantity label.
     */
    private void _addMirrorIcon()
    {
	// Add the mirror icon
	Button button = new Button(0, 
	                           _toolIconYPos, 
	                           _toolIconSizeWidth, 
	                           _toolIconSizeHeight, "Mirror");
	button.setTextureResourceLocation("textures/mirror.png");
	button.setTappedTextureLocation("textures/mirrorClicked.png");
	TextureResource mirrorTexture = (TextureResource)Engine.getInstance().getResourceManager().getResource("textures/mirror.png");
	button.setTexture(mirrorTexture);
	TextureResource mirrorClickedTexture = (TextureResource)Engine.getInstance().getResourceManager().getResource("textures/mirrorClicked.png");
	button.setTappedTexture(mirrorClickedTexture);
	_mirrorIconAdded = true;
	_gameState.getGui().addButton(button);

	// Add the label indicating stock
	NumericLabel label = new NumericLabel(0, _toolIconYPos, _toolIconSizeWidth / 2.5f, _toolIconSizeHeight / 2.5f, _stock.get(ToolType.Mirror));
//	label.setTexture((TextureResource)Engine.getInstance().getResourceManager().getResource("textures/numbers.png"));
	_stockLabel.put(ToolType.Mirror, label);
	_gameState.getGui().addButton(label);
    }
    
    /**
     * Add prism icon and quantity label.
     */
    private void _addPrismIcon()
    {
	// Add the prism icon
	Button button = new Button(_toolIconSizeWidth, 
	                    _toolIconYPos, 
	                    _toolIconSizeWidth, 
	                    _toolIconSizeHeight, "Prism");
	button.setTextureResourceLocation("textures/prism.png");
	button.setTappedTextureLocation("textures/prismClicked.png");
	TextureResource prismTexture = (TextureResource)Engine.getInstance().getResourceManager().getResource("textures/prism.png");
	button.setTexture(prismTexture);
	TextureResource prismClickedTexture = (TextureResource)Engine.getInstance().getResourceManager().getResource("textures/prismClicked.png");
	button.setTappedTexture(prismClickedTexture);
	_prismIconAdded = true;
	_gameState.getGui().addButton(button);

	// Add the label indicating stock
	NumericLabel label = new NumericLabel(_toolIconSizeWidth, _toolIconYPos, _toolIconSizeWidth / 2.5f, _toolIconSizeHeight / 2.5f, _stock.get(ToolType.Prism));
//	label.setTexture((TextureResource)Engine.getInstance().getResourceManager().getResource("textures/numbers.png"));
	_stockLabel.put(ToolType.Prism, label);
	_gameState.getGui().addButton(label);
    }
}
