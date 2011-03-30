package ca.sandstorm.luminance.gamelogic;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Matrix3f;
import javax.vecmath.Point2i;
import javax.vecmath.Vector3f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.Luminance;
import ca.sandstorm.luminance.camera.Camera;
import ca.sandstorm.luminance.gameobject.Box;
import ca.sandstorm.luminance.gameobject.Emitter;
import ca.sandstorm.luminance.gameobject.GameObject;
import ca.sandstorm.luminance.gameobject.Grid;
import ca.sandstorm.luminance.gameobject.IGameObject;
import ca.sandstorm.luminance.gameobject.IRenderableObject;
import ca.sandstorm.luminance.gameobject.Light;
import ca.sandstorm.luminance.gameobject.LightBeamCollection;
import ca.sandstorm.luminance.gameobject.LightBeam;
import ca.sandstorm.luminance.gameobject.Receptor;
import ca.sandstorm.luminance.gameobject.Skybox;
import ca.sandstorm.luminance.gametools.ToolType;
import ca.sandstorm.luminance.gametools.Toolbelt;
import ca.sandstorm.luminance.gui.Button;
import ca.sandstorm.luminance.gui.GUIManager;
import ca.sandstorm.luminance.gui.IWidget;
import ca.sandstorm.luminance.level.XmlLevel;
import ca.sandstorm.luminance.level.XmlLevelEmitter;
import ca.sandstorm.luminance.level.XmlLevelGoal;
import ca.sandstorm.luminance.level.XmlLevelObject;
import ca.sandstorm.luminance.level.XmlLevelParser;
import ca.sandstorm.luminance.level.XmlLevelTool;
import ca.sandstorm.luminance.math.Colliders;
import ca.sandstorm.luminance.resources.MusicResource;
import ca.sandstorm.luminance.resources.SoundResource;
import ca.sandstorm.luminance.resources.TextureResource;
import ca.sandstorm.luminance.state.IState;


/**
 * GameState
 * Application logic class.
 * Controls the game.
 * 
 * @author halsafar
 *
 */
public class GameState implements IState
{
    private static final Logger logger = LoggerFactory
	    .getLogger(GameState.class);
    
    public static final float DEFAULT_CAMERA_Z_NEAR = 0.2f;
    public static final float DEFAULT_CAMERA_Z_FAR = 40.0f;
    public static final float DEFAULT_CAMERA_FOV = 45.0f;
    public static final float DEFAULT_CAMERA_SKY_FOV = 90.0f;

    // has the game state had its core components initialized
    private boolean _initialized = false;
    
    // Current level
    private boolean _complete = false;
    
    // camera for the view matrix
    private Camera _cam;

    // game grid
    private Grid _grid;
    
    // game skybox
    private Skybox _sky;
    
    // light beam
    private LightPath _lightPath;

    private GUIManager _guiManager;
    
    // in-game menu
    private GUIManager _menuGuiManager;
    private boolean _showMenu;

    // Temporary Point to avoid creating new ones
    private Point2i tempPoint;
    
    // Toolbelt
    private Toolbelt _toolbelt;

    // Container of game objects
    private HashMap<Point2i, IGameObject> _objects;
    
    // Container for the goals for quick verification
    private Vector<Receptor> _goalObjects = null;
    
    // Container for the emitters, used to restart lights
    private Vector<Emitter> _emitterObjects = null;

    // Controller class for gamestate
    private GameStateInput _input;
    
    // Level pack list
    private LevelList _level;
    private String _levelName = "";
    
    /**
     * Constructor()
     * 
     * Sets up a basic test world.
     */
    public GameState(int level)
    {
	logger.debug("GameState()");
	
	//_objects = new LinkedList<IGameObject>();
	_objects = new HashMap<Point2i, IGameObject>();
	_goalObjects = new Vector<Receptor>();
	_emitterObjects = new Vector<Emitter>();
	tempPoint = new Point2i();
	_input = new GameStateInput();
	
	// TODO - should crash if pack file does not exist
	_level = new LevelList("BasicPack.lst");
	_level.setCurrentLevel(level);	
	
	// Create GUI manager and add initial widgets
	_guiManager = new GUIManager(false);
	_menuGuiManager = new GUIManager(true);
	_menuGuiManager.setIsEnabled(false);
	_showMenu = false;
    }
    
    public boolean getShowMenu()
    {
	return _showMenu;
    }
    
    public void setShowMenu(boolean showMenu)
    {
	_showMenu = showMenu;
    }
        
    /**
     * Add a game object to the game.
     * NOTE: Also calls object's initialize(), at least for now.
     * @param obj Object to add.
     */
    public void addObject(IGameObject obj)
    {
	// Initialize object
	obj.initialize();
	
	// Add to updatable objects list
	Point2i gridCoord = worldToGridCoords(obj.getPosition());
	_objects.put(gridCoord, obj);
	
	// Add to renderer if applicable
	if(obj instanceof IRenderableObject) {
	    Engine.getInstance().getRenderer().add((IRenderableObject)obj);
	}
    }
    
    /**
     * Remove an object from the world.
     * @param obj Object to remove
     */
    public void removeObject(IGameObject obj)
    {
	assert _objects.containsValue(obj);
	
	// Remove from objects list
	logger.debug("Removing object: " + obj);
	
	// Hashmap removes by key, so we need to calculate the key (cheap operation).
	// Alternately, we can keep an object's grid coords inside the object.
	Point2i key = worldToGridCoords(obj.getPosition());
	_objects.remove(key);
	
	// Remove from renderer
	if(obj instanceof IRenderableObject) {
	    Engine.getInstance().getRenderer().remove((IRenderableObject)obj);
	}
	
	resetEmitters();
    }
    
    /**
     * Clear objects currently in the level.
     */
    private void _clearLevel()
    {
	// clear arrays
	_objects.clear();
	_emitterObjects.clear();
	_goalObjects.clear();
	_lightPath.getLightPaths().clear();
	Engine.getInstance().getRenderer().removeAll();
	
	// reinit gui manager
	_guiManager = new GUIManager(false);

	float width = Engine.getInstance().getViewWidth();
	float height = Engine.getInstance().getViewHeight();	
	
	Button pauseButton = new Button(width*0.86f,
	                                height*0.86f,
	                                width*0.14f,
	                                height*0.12f,
	                                "Pause");
	pauseButton.setTextureResourceLocation("textures/pause.png");
	pauseButton.setTappedTextureLocation("textures/pauseClicked.png");
	pauseButton.setCalleeAndMethod(this, "showOrDismissPauseMenu");

	_guiManager.addButton(pauseButton);	
	
	// init toolbelt
	_toolbelt = new Toolbelt(this);
	
	for (IWidget widget : _guiManager.getWidgets()) {
	    if (widget != null) {
		String textureResourceLocation = widget.getTextureResourceLocation();
		TextureResource texture = (TextureResource)Engine.getInstance().getResourceManager().getResource(textureResourceLocation);

		widget.setTexture(texture);

		if (widget.getClass() == Button.class && 
			((Button)widget).getTappedTextureLocation() != null) {
		    String tappedTextureLocation = ((Button)widget).getTappedTextureLocation();
		    TextureResource tappedTexture = (TextureResource)Engine.getInstance().getResourceManager().getResource(tappedTextureLocation);
		    ((Button)widget).setTappedTexture(tappedTexture);
		}
	    }
	}	
	
	// level is not complete
	_complete = false;
    }
    
    /**
     * Level is completed. Show a message, and more in the future.
     */
    private void _levelComplete()
    {
	// TODO: maybe move these to be class members updated automatically
	float width = Engine.getInstance().getViewWidth();
	float height = Engine.getInstance().getViewHeight();
	
	Button button = new Button(0.0f * width,
	                           0.0f * height,
	                           width,
	                           height,
	                           "LevelComplete");
	button.setTexture((TextureResource)Engine.getInstance().getResourceManager().getResource("textures/levelComplete.png"));
	button.setCalleeAndMethod(this, "nextLevel");
	_guiManager.addButton(button);
	
	Engine.getInstance().getAudio().play((SoundResource)Engine.getInstance().getResourceManager().getResource("sounds/sample.ogg"), 0.9f);
    }
    
    /**
     * Go to the next level.
     * Clear the current state and load the next one.
     */
    public void nextLevel()
    {
	_clearLevel();
	_level.iterateNextLevel();
	_parseLevel();
	resetCamera();
	resetEmitters();
	
	//Engine.getInstance().popState();
	//Engine.getInstance().pushState(new GameState(_level + 1));
    }
    
    
    /**
     * Parses the level info. 
     */
    private void _parseLevel()
    {
	// clear old objects
	_clearLevel();
	
	// try to load a level
	try
	{
	    // parse the level
	    InputStream levelFile = Engine.getInstance().getContext().getAssets().open("levels/" + _level.getCurrentLevel());
	    XmlLevelParser levelParser = new XmlLevelParser(levelFile);
	    XmlLevel level = levelParser.parse();
	    _levelName = level.getName();
	    Luminance.getInstance().setSubTitle(_levelName);
	    level.toString();
	    
	    // parse the grid
	    _grid = new Grid(level.getXSize(), level.getYSize(), 1.0f, 1.0f);
	    
	    // parse all the objects into game objects
	    //for (int i = 0; i < level.getObjects().size(); i++)
	    for (XmlLevelObject obj : level.getObjects())
	    {
		//XmlLevelObject obj = level.getObjects().get(i);
		
		Vector3f gridPos = _grid.getCellCenter((int)obj.getPositionY(), (int)obj.getPositionX());
		Vector3f vPos = new Vector3f(gridPos.x, gridPos.y, gridPos.z);
		vPos.y += 0.5f;  // lift the box so the bottom is inline with the grid		
		Vector3f vRot = new Vector3f(obj.getRotationX(), obj.getRotationY(), obj.getRotationZ());
		Vector3f vScale = new Vector3f(0.5f, 0.5f, 0.5f);
				
		// parse bricks into world
		if (obj.getType().equals("brick"))
		{		   
		    Box box = new Box(vPos, vScale);
		    addObject(box);
		}		
		// parse goals into world
		else if (obj.getType().equals("goal"))
		{
		    Receptor goal = new Receptor(vPos, vScale);
		    goal.setColor(((XmlLevelGoal)obj).getColour());
		    addObject(goal);
		    _goalObjects.add(goal);
		}
		else if (obj.getType().equals("emitter"))
		{
		    // calculate goal color
		    int color = ((XmlLevelEmitter)obj).getColour();
		 	    
		    Emitter emitter = new Emitter(vPos, vRot, color);
		    addObject(emitter);
		    _emitterObjects.add(emitter);		    
		}
	    }
	    
	    // Parse tools -zenja
	    for(XmlLevelTool tool : level.getTools()) {
		if(tool.getType().equals("mirror")) {
		    _toolbelt.addToolStock(ToolType.Mirror, tool.getCount());
		    logger.debug("Level parser: added " + tool.getCount() + " mirror stock.");
		}
		if(tool.getType().equals("prism")) {
		    _toolbelt.addToolStock(ToolType.Prism, tool.getCount());
		    logger.debug("Level parser: added " + tool.getCount() + " prism stock.");
		}
	    }
	}
	catch (IOException e)
	{
	    logger.error("Could not open level file!");
	    System.exit(0);
	}	
    }
    
    
    /**
     * Simply resets the camera to default position for each level.
     */
    public void resetCamera()
    {
	float maxSize = Math.max(_grid.getTotalWidth(), _grid.getTotalHeight());
	float camY = 1.15f * (maxSize / (float) (Math.tan((Math.PI / 6.0))));

	_cam = new Camera();
	
	_cam.setViewPort(0, 0, Engine.getInstance().getViewWidth(), Engine.getInstance().getViewHeight());
	_cam.setPerspective(DEFAULT_CAMERA_FOV, (float) Engine.getInstance().getViewWidth() / (float) Engine.getInstance().getViewHeight(), DEFAULT_CAMERA_Z_NEAR, DEFAULT_CAMERA_Z_FAR);	
	
	_cam.setEye(_grid.getTotalWidth() / 2.0f, camY,
		    _grid.getTotalHeight() / 2.0f);
	_cam.setTarget(_grid.getTotalWidth() / 2.0f, 0,
		       _grid.getTotalHeight() / 2.0f);
	_cam.rotateCamera(0.01f, 1, 0, 0);	
    }
    
    
    /**
     * 
     */
    public void resetEmitters()
    {
	// delete all the light paths
	_lightPath.getLightPaths().clear();
	
	// reset all goals
	for (Receptor r : _goalObjects)
	{
	    r.setActivated(false);
	}
	
	// restart all emitters
	for (Emitter e : _emitterObjects)
	{
	    LightBeam beam = new LightBeam();
	    
	    // calc light direction
	    Vector3f norm = new Vector3f(1,0,0);
	    Vector3f result = new Vector3f();
	    Matrix3f mat = Colliders.getMatrixRotationY((float)Math.toRadians(-e.getRotation().y));
	    Colliders.Transform(norm, mat, result);	    
	    
	    // generate a light beam for this emitter
	    Light l = new Light(e.getPosition().x, e.getPosition().y, e.getPosition().z, 
	                        result.x, result.y, result.z, 
	                        Light.LIGHT_INFINITY, e.getColor() );
	    l.setStartTouchedObject(e);
	    beam.add(l);
	    _lightPath.getLightPaths().add(beam);	    
	}
    }


    /**
     * pause()
     * Engine has requested this state be paused.
     * This is different from ingame pausing.
     */    
    @Override
    public void pause()
    {
	logger.debug("pause()");
    }


    /**
     * resume()
     * Engine has requested this state to resume.
     */
    @Override
    public void resume()
    {
	logger.debug("resume()");

	Engine.getInstance().getTimer().reset();
    }


    /**
     * init()
     * Engine has informed this state can init any openGL 
     * required resources.
     * @param gl OpenGL context
     */
    @Override
    public void init(GL10 gl)
    {
	logger.debug("init()");
	float width = Engine.getInstance().getViewWidth();
	float height = Engine.getInstance().getViewHeight();
	
	// Clear out the GUI elements and recreate them for new GL instance
	if (_initialized) {
	    _guiManager = new GUIManager(false);
	    _menuGuiManager = new GUIManager(true);
	}
		
	// Load textures
	try {
	    Engine.getInstance().getResourceManager().loadTexture(gl, "textures/wallBrick.jpg");
	    Engine.getInstance().getResourceManager().loadTexture(gl, "textures/inGameMirror.png");
	    Engine.getInstance().getResourceManager().loadTexture(gl, "textures/inGamePrism.png");
	    Engine.getInstance().getResourceManager().loadTexture(gl, "textures/missing.jpg");
	    Engine.getInstance().getResourceManager().loadTexture(gl, "textures/levelComplete.png");
	    Engine.getInstance().getResourceManager().loadTexture(gl, "textures/emitter.jpg");
	    
	    Engine.getInstance().getResourceManager().loadTexture(gl, "textures/prism.png");
	    Engine.getInstance().getResourceManager().loadTexture(gl, "textures/prismClicked.png");
	    Engine.getInstance().getResourceManager().loadTexture(gl, "textures/mirror.png");
	    Engine.getInstance().getResourceManager().loadTexture(gl, "textures/mirrorClicked.png");
	    Engine.getInstance().getResourceManager().loadTexture(gl, "textures/numbers.png");
	} catch (IOException e) {
	    logger.error("Unable to load a required texture: " + e.getMessage());
	    e.printStackTrace();
	}
	
	// Load sound effects and music
	try {
	    Engine.getInstance().getResourceManager().loadSound(Engine.getInstance().getAudio().getPool(), "sounds/sample.ogg");
	    Engine.getInstance().getResourceManager().loadSound(Engine.getInstance().getAudio().getPool(), "sounds/place.ogg");
	    Engine.getInstance().getResourceManager().loadSound(Engine.getInstance().getAudio().getPool(), "sounds/rotate.ogg");
	    Engine.getInstance().getResourceManager().loadSound(Engine.getInstance().getAudio().getPool(), "sounds/noPlace.ogg");
	    Engine.getInstance().getResourceManager().loadSound(Engine.getInstance().getAudio().getPool(), "sounds/iconClick.ogg");
	    Engine.getInstance().getResourceManager().loadSound(Engine.getInstance().getAudio().getPool(), "sounds/eraser.ogg");
	} catch (IOException e) {
	    logger.error("Unable to load a required sound: " + e.getMessage());
	    e.printStackTrace();
	}
	
	Button pauseButton = new Button(width*0.86f,
	                                height*0.86f,
	                                width*0.14f,
	                                height*0.12f,
	                                "Pause");
	pauseButton.setTextureResourceLocation("textures/pause.png");
	pauseButton.setTappedTextureLocation("textures/pauseClicked.png");
	pauseButton.setCalleeAndMethod(this, "showOrDismissPauseMenu");
	
	Button resumeButton = new Button(0.175f*width, 
	                                 0.200f*height, 
	                                 0.550f*width, 
	                                 0.1250f*height,
					 "Resume");
	resumeButton.setTextureResourceLocation("textures/resume.png");
	resumeButton.setCalleeAndMethod(this, "showOrDismissPauseMenu");
	
	Button restartLevelButton = new Button(0.175f*width, 
	                                       0.200f*height + 0.180f*height, 
	                                       0.550f*width, 
	                                       0.1250f*height,
						"Restart Level");
	restartLevelButton.setTextureResourceLocation("textures/restart.png");
	restartLevelButton.setCalleeAndMethod(this, "restartLevel");

	Button exitButton = new Button(0.175f*width, 
	                               0.200f*height + 2*0.180f*height, 
	                               0.550f*width, 
	                               0.1250f*height,
					"Exit");
	exitButton.setTextureResourceLocation("textures/mainMenu.png");
	exitButton.setCalleeAndMethod(this, "exitLevel");

	_guiManager.addButton(pauseButton);
	_menuGuiManager.addButton(resumeButton);
	_menuGuiManager.addButton(restartLevelButton);
	_menuGuiManager.addButton(exitButton);
	
	// Add a skybox
	_sky = new Skybox();
	try {
	    _sky.init(gl);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	if (!_initialized) {
	    // init the lightpath
	    // add a test light
	    _lightPath = new LightPath();

	    // Create the toolbelt
	    _toolbelt = new Toolbelt(this);

	    // Load level
	    _parseLevel();

	    // get the light going
	    resetEmitters();
	} else {
	    // Recreate toolbelt GUI
	    _toolbelt.addGui();
	    
	    // Re-initialize all gameobjects so they have updated texture id
	    for(IGameObject obj : _objects.values()) {
		obj.initialize();
	    }
	}
	
	// default camera
	resetCamera();
	
	_loadGuiTextures(gl, _guiManager);
	_loadGuiTextures(gl, _menuGuiManager);
		
	// SUCCESS
	_initialized = true;
    }
    
    /**
     * Load textures for a given GUI manager.
     */
    private void _loadGuiTextures(GL10 gl, GUIManager gui)
    {
	try {
	    for (IWidget widget : gui.getWidgets()) {
		if (widget != null) {
		    String textureResourceLocation = widget.getTextureResourceLocation();
		    TextureResource texture = Engine.getInstance().getResourceManager().loadTexture(gl, textureResourceLocation);

		    widget.setTexture(texture);

		    if (widget.getClass() == Button.class && 
			    ((Button)widget).getTappedTextureLocation() != null) {
			String tappedTextureLocation = ((Button)widget).getTappedTextureLocation();
			TextureResource tappedTexture = Engine.getInstance().getResourceManager().loadTexture(gl, tappedTextureLocation);
			((Button)widget).setTappedTexture(tappedTexture);
		    }
		}
	    }
	} catch (IOException e) {
	    // TODO: improve this
	    throw new RuntimeException("Unable to load a required texture!");
	}
    }

    /**
     * Show the pause menu if it is not currently being shown and dismiss it if
     * it is being shown. This enables or disables the primary gui manager and
     * the menu gui manager depending on the current state.
     */
    public void showOrDismissPauseMenu()
    {
	logger.debug("showOrDismissPauseMenu()");
	Engine.getInstance().getAudio().play((SoundResource)Engine.getInstance().getResourceManager().getResource("sounds/iconClick.ogg"), 0.9f);

	_showMenu = !_showMenu;
	_guiManager.setIsEnabled(!_showMenu);
	_menuGuiManager.setIsEnabled(_showMenu);
    }
    
    public void exitLevel()
    {
	logger.debug("exitLevel()");
	
	Engine.getInstance().popState();
	Engine.getInstance().pushState( new MenuState() );
    }
    
    public void restartLevel()
    {
	logger.debug("restartLevel()");
	
	logger.debug(Integer.toString(_level.getCurrentLevelIndex()));
	_level.setCurrentLevel(_level.getCurrentLevelIndex() - 1);
	nextLevel();
	showOrDismissPauseMenu();
    }

    /**
     * Convert a grid cell coordinate to a world coordinate.
     * @param x Grid X coordinate
     * @param y Grid Y coordinate
     * @return World coordinates
     */
    public Vector3f gridToWorldCoords(int x, int y)
    {
	return _grid.getCellCenter(y, x);
    }
    
    /**
     * Convert a world coordinate to a grid cell coordinate.
     * @param position World coordinate
     * @return Grid coordinate
     */
    public Point2i worldToGridCoords(Vector3f position)
    {
	return _grid.getGridPosition(position.x, position.y, position.z);
    }
    
    /**
     * Get an object at specified grid coordinates.
     * @param x Grid X coordinate
     * @param y Grid Y coordinate
     * @return The object at the position, or null if none
     */
    public IGameObject getObjectAtGridCoords(int x, int y)
    {
	tempPoint.x = x;
	tempPoint.y = y;
	return _objects.get(tempPoint);
    }
    
    
    /**
     * 
     * @param x
     * @param y
     */
    public void rotateObjectAtGridCoords(int x, int y)
    {
	// if cell is occupied
	if (isCellOccupied(x, y))
	{
	    Engine.getInstance().getAudio().play((SoundResource)Engine.getInstance().getResourceManager().getResource("sounds/rotate.ogg"), 0.9f);
	    
	    // just hack rotation for now
	    IGameObject obj = getObjectAtGridCoords(x, y);
	    obj.setRotation(0, obj.getNextYRotation(), 0);
	    
	    resetEmitters();
	}
    }
    
    
    /**
     * Check if a cell is occupied.
     * @param x Grid X coordinate
     * @param y Grid Y coordinate
     * @return True if occupied, false if not
     */
    public boolean isCellOccupied(int x, int y)
    {
	tempPoint.x = x;
	tempPoint.y = y;
	return _objects.containsKey(tempPoint);
    }

    /**
     * update()
     * Engine has requested this state update itself.
     * @param gl OpenGL context
     */
    @Override
    public void update(GL10 gl)
    {
	// Handle input
	if (_showMenu) {
	    _input.process(_cam, _toolbelt, _grid, _menuGuiManager, false);
	} else {
	    _input.process(_cam, _toolbelt, _grid, _guiManager, true);
	}
	
	// Update game objects
	for (IGameObject object : _objects.values()) {
	    object.update();
	}
	
	// collision detection
	LightBeamCollection beams = _lightPath.getLightPaths();
	for (int i = 0; i < beams.size(); i++) 
	{
	    LightBeam lightBeam = beams.get(i);
	    int savedBeamSize = lightBeam.size();

	    for (int j = 0; j < lightBeam.size(); j++)	
	    {
		IGameObject minObj = null;
		float minDist = Light.LIGHT_INFINITY;
		float curDist = 0.0f;
		
		Light l = lightBeam.get(j);
		
		// if light has an end point adjust it for max distance
		if (l.getEndTouchedObject() != null)
		{
		    minDist = (float)Colliders.distance(l.getEndTouchedObject().getPosition(), l.getPosition());
		}
		
		// loop through all objects to find min dist collision
		for (IGameObject o : _objects.values())
		{
		    // ignore collisions with objects we know light
		    // such a HACK
		    if (o == l.getStartTouchedObject())
		    {
			continue;
		    }
		    
		    if (o == l.getEndTouchedObject())
		    {
			//minObj = null;
			continue;
		    }
		    
		    // get collision point, compare to find min
		    Vector3f colPoint = Colliders.collide(o.getCollisionSphere(), l.getRay());
		    if (colPoint != null)
		    {
			curDist = (float)Colliders.distance(colPoint, l.getPosition());
			if (curDist <= minDist)
			{
			    minDist = curDist;
			    minObj = o;			    
			}		
		    }
		}
		
		// we found a min collision
		if (minObj != null)
		{
		    minObj.beamInteract(_lightPath.getLightPaths(), i, j);
		}
		
		// check if the beam was modified and we are done
		if (lightBeam.size() < savedBeamSize)
		{
		    break;
		}
		
	    }
	    
	}
	
	// level end check
	if (!_complete) {
	    boolean bLevelComplete = true;
	    for (Receptor r : _goalObjects)
	    {
		// all goals must be activated
		if (!r.getActivated())
		{
		    bLevelComplete = false;
		    break;
		}
	    }

	    if (bLevelComplete)
	    {
		logger.info("/--LEVEL COMPLETED--\\");
		_complete = true;
		_levelComplete();
	    }
	}
    }


    /**
     * Engine has requested this state draw itself.
     * @param OpenGL context
     */
    @Override
    public void draw(GL10 gl)
    {	
	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

	// render skybox at 90 fov to keep the perspective realistic
	_cam.setFov(DEFAULT_CAMERA_SKY_FOV);
	_cam.updateViewMatrix(gl);
	gl.glPushMatrix();
	gl.glTranslatef(_cam.getEye().x, _cam.getEye().y, _cam.getEye().z);
	gl.glDisable(GL10.GL_DEPTH_TEST);
	_sky.draw(gl);
	gl.glEnable(GL10.GL_DEPTH_TEST);
	gl.glPopMatrix();	
	
	// render game at 45 fov to keep the 3D effect realistic
	_cam.setFov(DEFAULT_CAMERA_FOV);
	_cam.updateViewMatrix(gl);

	gl.glEnable(GL10.GL_CULL_FACE);
	gl.glCullFace(GL10.GL_BACK);	

	// Get renderer to draw everything on its renderable list
	Engine.getInstance().getRenderer().draw(gl);

	gl.glPushMatrix();
	gl.glTranslatef(0.0f, 0, 0f);
	_grid.draw(gl);
	gl.glPopMatrix();
	
	gl.glPushMatrix();
	_lightPath.draw(gl);
	gl.glPopMatrix();
	
	// render 2D stuff, reset view to identity now
	gl.glLoadIdentity();
	gl.glMatrixMode(GL10.GL_PROJECTION);
	gl.glLoadIdentity();
	gl.glOrthof(0, Engine.getInstance().getViewWidth(), Engine.getInstance().getViewHeight(), 0, -1.0f, 1.0f);    			
    	_guiManager.draw(gl);
    	if (_showMenu) {
    	    _menuGuiManager.draw(gl);
    	}
    	
    	// disable any states
	gl.glDisable(GL10.GL_CULL_FACE);
    }

    public GUIManager getGui()
    {
	return _guiManager;
    }
    
    /**
     * Engine has informed the state the device has changed.
     * @param gl OpenGL context
     * @param w The new width value
     * @param h The new height value
     */
    @Override
    public void deviceChanged(GL10 gl, int w, int h)
    {
	logger.debug("deviceChanged(" + gl + ", " + w + ", " + h + ")");

	gl.glShadeModel(GL10.GL_SMOOTH); // Enable Smooth Shading
	gl.glClearColor(0.182f, 0.182f, 1, 1); // Error blue
	gl.glClearDepthf(1.0f); // Depth Buffer Setup
	gl.glEnable(GL10.GL_DEPTH_TEST); // Enables Depth Testing
	gl.glDepthFunc(GL10.GL_LEQUAL); // The Type Of Depth Testing To Do

	// Really Nice Perspective Calculations 
	/*gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
	gl.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_FASTEST);
	gl.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_FASTEST);
	gl.glHint(GL10.GL_POINT_SMOOTH_HINT, GL10.GL_FASTEST);
	gl.glHint(GL10.GL_POLYGON_SMOOTH_HINT, GL10.GL_FASTEST);*/

	// prevent divide by zero.
	// @HACK - Forgiven since h == 0 means the game window is probably
	// hidden.
	if (h == 0) {
	    h = 1;
	}

	// if camera is active, update it
	if (_cam != null)
	{
	    _cam.setViewPort(0, 0, w, h);
	    _cam.setPerspective(DEFAULT_CAMERA_FOV, (float) Engine.getInstance().getViewWidth() / (float) Engine.getInstance().getViewHeight(), DEFAULT_CAMERA_Z_NEAR, DEFAULT_CAMERA_Z_FAR);
	}
	
	Luminance.getInstance().setSubTitle(_levelName);
    }


    @Override
    public void messageRecieved()
    {
	// TODO Auto-generated method stub

    }


    @Override
    public boolean isActive()
    {
	return true;
    }


    @Override
    public boolean isVisible()
    {
	// TODO Auto-generated method stub
	return true;
    }

    
    @Override
    public boolean isInitialized()
    {
	return _initialized;
    }

}