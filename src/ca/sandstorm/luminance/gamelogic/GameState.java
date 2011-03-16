package ca.sandstorm.luminance.gamelogic;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Point2i;
import javax.vecmath.Vector3f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.camera.Camera;
import ca.sandstorm.luminance.gameobject.Box;
import ca.sandstorm.luminance.gameobject.Emitter;
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

    private boolean _initialized = false;
    
    // camera for the view matrix
    private Camera _cam;

    // game grid
    private Grid _grid;
    
    // game skybox
    private Skybox _sky;
    
    // light beam
    private LightPath _lightPath;

    private GUIManager _guiManager;
    
    // Temporary Point to avoid creating new ones
    private Point2i tempPoint;
    
    // Toolbelt
    private Toolbelt _toolbelt;

    // Container of game objects
    //private LinkedList<IGameObject> _objects;
    private HashMap<Point2i, IGameObject> _objects;
    
    // Container for the goals for quick verification
    private Vector<Receptor> _goalObjects = null;

    // Controller class for gamestate
    private GameStateInput _input;
    
    
    /**
     * Constructor()
     * 
     * Sets up a basic test world.
     */
    public GameState()
    {
	logger.debug("GameState()");
	
	//_objects = new LinkedList<IGameObject>();
	_objects = new HashMap<Point2i, IGameObject>();
	_goalObjects = new Vector<Receptor>();
	tempPoint = new Point2i();
	_input = new GameStateInput();
	
	// Create GUI manager and add initial widgets
	_guiManager = new GUIManager();
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
	//_objects.add(obj);
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
    }
    
    /**
     * Clear objects currently in the level.
     */
    private void _clearLevel()
    {
	_objects.clear();
	_goalObjects.clear();
	Engine.getInstance().getRenderer().removeAll();
    }
    
    /**
     * Level is completed. Show a message, and more in the future.
     */
    private void _levelComplete()
    {
	// TODO: maybe move these to be class members updated automatically
	float width = Engine.getInstance().getViewWidth();
	float height = Engine.getInstance().getViewHeight();
	
	Button button = new Button(width*0.1f,
	                           height*0.1f,
	                           width*0.8f,
	                           height*0.8f,
	                           "LevelComplete");
	button.setTexture((TextureResource)Engine.getInstance().getResourceManager().getResource("textures/levelComplete.png"));
	button.setCalleeAndMethod(this, "nextLevel");
	_guiManager.addButton(button);
    }
    
    /**
     * Go to the next level.
     * Clear the current state and load the next one.
     */
    public void nextLevel()
    {
	//_parseLevel();
	_clearLevel();
    }
    
    
    /**
     * Parses the level info. 
     * @TODO: should come from a level list
     */
    private void _parseLevel()
    {
	// clear old objects
	_clearLevel();
	
	// try to load a level
	try
	{
	    // parse the level
	    InputStream levelFile = Engine.getInstance().getContext().getAssets().open("levels/TestLevel.xml");
	    XmlLevelParser levelParser = new XmlLevelParser(levelFile);
	    XmlLevel level = levelParser.parse();
	    level.toString();
	    
	    // parse the grid
	    _grid = new Grid(level.getXSize(), level.getYSize(), 1.0f, 1.0f);
	    
	    // parse all the objects into game objects
	    for (int i = 0; i < level.getObjects().size(); i++)
	    {
		XmlLevelObject obj = level.getObjects().get(i);
		
		Vector3f gridPos = _grid.getCellCenter((int)obj.getPositionX(), (int)obj.getPositionY());
		Vector3f vPos = new Vector3f(gridPos.x, gridPos.y, gridPos.z);
		vPos.y += 0.5f;  // lift the box so the bottom is inline with the grid		
		Vector3f vRot = new Vector3f(obj.getRotationX(), obj.getRotationY(), obj.getRotationZ());
		Vector3f vScale = new Vector3f(0.5f, 0.5f, 0.5f);
		
		// xml reads in degrees, convert to radians, no dont, opengl is degrees
		//vRot.x = (float)Math.toRadians(vRot.x);
		//vRot.y = (float)Math.toRadians(vRot.y);
		//vRot.z = (float)Math.toRadians(vRot.z);
		
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
		 
		    // @HACK
		    //color = Color.WHITE;
		    
		    Emitter emitter = new Emitter(vPos, vRot);
		    addObject(emitter);
		    
		    // generate a light beam for this emitter
		    LightBeam beam = new LightBeam();
		    Light l = new Light(vPos.x, vPos.y, vPos.z,
		                        1, 0, 0,
		                        Light.LIGHT_INFINITY,
		                        color);
		    l.setStartTouchedObject(emitter);
		    beam.add(l);
		    _lightPath.getLightPaths().add(beam);
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
	_cam.setPerspective(45.0f, (float) Engine.getInstance().getViewWidth() / (float) Engine.getInstance().getViewHeight(), 0.1f, 100.0f);	
	
	_cam.setEye(_grid.getTotalWidth() / 2.0f, camY,
		    _grid.getTotalHeight() / 2.0f);
	_cam.setTarget(_grid.getTotalWidth() / 2.0f, 0,
		       _grid.getTotalHeight() / 2.0f);
	_cam.rotateCamera(0.01f, 1, 0, 0);	
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
	
	// @TODO - write a proper uninit function and call it here.
	if (_initialized)
	{
	    return;
	}
	
	// Load textures
	try {
	    Engine.getInstance().getResourceManager().loadTexture(gl, "textures/wallBrick.jpg");
	    Engine.getInstance().getResourceManager().loadTexture(gl, "textures/inGameMirror.png");
	    Engine.getInstance().getResourceManager().loadTexture(gl, "textures/inGamePrism.png");
	    Engine.getInstance().getResourceManager().loadTexture(gl, "textures/missing.jpg");
	    Engine.getInstance().getResourceManager().loadTexture(gl, "textures/levelComplete.png");
	    Engine.getInstance().getResourceManager().loadTexture(gl, "textures/emitter.jpg");
	} catch (IOException e) {
	    logger.error("Unable to load a required texture: " + e.getMessage());
	    e.printStackTrace();
	}
	
	// Load sound effects and music
	try {
	    Engine.getInstance().getResourceManager().loadSound(Engine.getInstance().getAudio().getPool(), "sounds/sample.ogg");
	    Engine.getInstance().getResourceManager().loadMusic("sounds/music1.mp3");
	} catch (IOException e) {
	    logger.error("Unable to load a required sound: " + e.getMessage());
	    e.printStackTrace();
	}
	
	// init the lightpath
	// add a test light
	_lightPath = new LightPath();
	
	
	float width = Engine.getInstance().getViewWidth();
	float height = Engine.getInstance().getViewHeight();	
	
	Button pauseButton = new Button(width*0.86f,
	                                height*0.86f,
	                                width*0.14f,
	                                height*0.12f,
	                                "Pause");
	pauseButton.setTextureResourceLocation("textures/pause.png");
	_guiManager.addButton(pauseButton);
		
	// Create the toolbelt
	_toolbelt = new Toolbelt(this);
	
	// Load level
	_parseLevel();

	// Add a skybox
	_sky = new Skybox();
	try {
	    _sky.init(gl);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	resetCamera();
	
	try {
	    for (IWidget widget : _guiManager.getWidgets()) {
		if (widget != null) {
		    String textureResourceLocation = widget.getTextureResourceLocation();
		    TextureResource texture = Engine.getInstance().getResourceManager().loadTexture(gl, textureResourceLocation);
		   /* 
		    Engine.getInstance().getResourceManager().
		    			loadTexture(gl, textureResourceLocation.substring(0, 
		    			            		          textureResourceLocation.length()-4)
		    			                 + "Clicked.png");
		    */
		    widget.setTexture(texture);
		}
	    }	  
	} catch (IOException e) {
	    // TODO: improve this
	    throw new RuntimeException("Unable to load a required texture!");
	}
	
	// Start music playback
	try {
	    Engine.getInstance().getAudio().playMusic((MusicResource)Engine.getInstance().getResourceManager().getResource("sounds/music1.mp3"));
	} catch (IOException e) {
	    logger.error("Unable to play music: " + e.getMessage());
	    e.printStackTrace();
	}
	
	// SUCCESS
	_initialized = true;
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
	_input.process(_cam, _toolbelt, _grid);
	
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
	    
	    for (int j = 0; j < savedBeamSize; j++)	
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
		    
		    if (o == l.getEndTouchedObject())// && !(o instanceof Box))
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
		    if (l.getEndTouchedObject() != null)
		    {
			
		    }
		    minObj.beamInteract(lightBeam, j);
		}
		
		// check if the beam was modified and we are done
		if (lightBeam.size() != savedBeamSize)
		{
		    break;
		}
	    }
	}
	
	// level end check
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
	    _levelComplete();
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

	_cam.updateViewMatrix(gl);

	gl.glPushMatrix();
	gl.glTranslatef(_cam.getEye().x, _cam.getEye().y, _cam.getEye().z);
	gl.glDisable(GL10.GL_DEPTH_TEST);
	_sky.draw(gl);
	gl.glEnable(GL10.GL_DEPTH_TEST);
	gl.glPopMatrix();

	// Get renderer to draw everything on its renderable list
	Engine.getInstance().getRenderer().draw(gl);

	gl.glPushMatrix();
	gl.glTranslatef(0.0f, 0, 0f);
	_grid.draw(gl);
	gl.glPopMatrix();
	
	gl.glPushMatrix();
	_lightPath.draw(gl);
	gl.glPopMatrix();
	
	// render 2D stuff in a complex matrix saving manner
	gl.glMatrixMode(GL10.GL_MODELVIEW);
	gl.glPushMatrix();	
		gl.glLoadIdentity();
		
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glPushMatrix();
			gl.glLoadIdentity();
			gl.glOrthof(0, Engine.getInstance().getViewWidth(), Engine.getInstance().getViewHeight(), 0, -1.0f, 1.0f);
			
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			_guiManager.draw(gl);
			gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glPopMatrix();
		
	gl.glMatrixMode(GL10.GL_MODELVIEW);
	gl.glPopMatrix();
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
	gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

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
	    _cam.setPerspective(45.0f, (float) w / (float) h, 0.1f, 100.0f);
	}
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