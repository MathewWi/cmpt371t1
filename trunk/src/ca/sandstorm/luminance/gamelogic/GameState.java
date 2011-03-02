package ca.sandstorm.luminance.gamelogic;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.view.KeyEvent;
import android.view.MotionEvent;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.camera.Camera;
import ca.sandstorm.luminance.gameobject.Box;
import ca.sandstorm.luminance.gameobject.Grid;
import ca.sandstorm.luminance.gameobject.IGameObject;
import ca.sandstorm.luminance.gameobject.IRenderableObject;
import ca.sandstorm.luminance.gameobject.Mirror;
import ca.sandstorm.luminance.gameobject.Skybox;
import ca.sandstorm.luminance.input.InputButton;
import ca.sandstorm.luminance.level.XmlLevel;
import ca.sandstorm.luminance.level.XmlLevelObject;
import ca.sandstorm.luminance.level.XmlLevelParser;
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

    // camera for the view matrix
    private Camera _cam;

    // game grid
    private Grid _grid;
    
    // game skybox
    private Skybox _sky;

    // input handling properties
    private float _initialX = 0.0f;
    private float _initialY = 0.0f;
    // the second finger/pointer coordinates
    private float _initialSecondX = 0.0f;
    private float _initialSecondY = 0.0f;
    private float _pinchDist = 0.0f;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private static final float TOUCH_CAMERA_SPEED = 45.0f;
    private static final float TOUCH_SENSITIVITY = 3.0f;
    private int _touchMode;

    // Container of game objects -zenja (and fixed properly by shinhalsafar)
    private LinkedList<IGameObject> _objects;


    /**
     * Constructor()
     * 
     * Sets up a basic test world.
     */
    public GameState()
    {
	logger.debug("GameState()");
	
	_objects = new LinkedList<IGameObject>();
    }
    
    /**
     * Add a game object to the game.
     * @param obj Object to add.
     */
    private void _addObject(IGameObject obj)
    {
	// Initialize object
	obj.initialize();
	
	// Add to updatable objects list
	_objects.add(obj);
	
	// Add to renderer if applicable
	if(obj instanceof IRenderableObject) {
	    Engine.getInstance().getRenderer().add((IRenderableObject)obj);
	}
    }
    
    /**
     * Clear objects currently in the level.
     */
    private void _clearLevel()
    {
	// TODO: clear the renderer list too
	_objects.clear();
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
	    _addObject(_grid);
	    
	    // parse all the objects into game objects
	    for (int i = 0; i < level.getObjects().size(); i++)
	    {
		XmlLevelObject obj = level.getObjects().get(i);
		
		Vector3f gridPos = _grid.getCellCenter((int)obj.getPositionX(), (int)obj.getPositionY());
		Vector3f vPos = new Vector3f(gridPos.x, gridPos.y, gridPos.z);
		Vector3f vRot = new Vector3f(0, 0, 0);
		Vector3f vScale = new Vector3f(0.5f, 0.5f, 0.5f);
		
		if (obj.getType().equals("brick"))
		{		   
		    Box box = new Box(vPos, vScale);
		    _addObject(box);
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
	// Load textures
	try {
	    Engine.getInstance().getResourceManager()
		    .loadTexture(gl, "textures/wallBrick.jpg");
	    Engine.getInstance().getResourceManager().loadTexture(gl, "textures/missing.jpg");
	} catch (IOException e) {
	    // TODO: improve this
	    throw new RuntimeException("Unable to load a required texture!");
	}
	
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
	_addObject(_sky);
	
	resetCamera();
	
	// Temporary developer test area
	IGameObject mirror = new Mirror(new Vector3f(0,0,0), 45f);
	_addObject(mirror);
    }

    /**
     * Read and process user input.
     */
    public void processInput()
    {
	InputButton[] keys = Engine.getInstance().getInputSystem()
		.getKeyboard().getKeys();
	if (keys[KeyEvent.KEYCODE_1].getPressed()) {
	    System.exit(-1);
	}

	if (keys[KeyEvent.KEYCODE_W].getPressed()) {
	    _cam.moveForward(1.0f);
	}

	if (keys[KeyEvent.KEYCODE_S].getPressed()) {
	    _cam.moveForward(-1.0f);
	}

	if (keys[KeyEvent.KEYCODE_A].getPressed()) {
	    // _cam.rotateCamera(-0.01f, 0, 1, 0);
	    _cam.moveLeft(-1.0f);
	}

	if (keys[KeyEvent.KEYCODE_D].getPressed()) {
	    // _cam.rotateCamera(0.01f, 0, 1, 0);
	    _cam.moveLeft(1.0f);
	}

	if (keys[KeyEvent.KEYCODE_Q].getPressed()) {
	    _cam.rotateCamera(0.01f, 1, 0, 0);
	    // _cam.moveUp(1.0f);
	}

	if (keys[KeyEvent.KEYCODE_E].getPressed()) {
	    _cam.rotateCamera(-0.01f, 1, 0, 0);
	    // _cam.moveUp(-1.0f);
	}

	if (Engine.getInstance().getInputSystem().getTouchScreen()
		.getPressed(1)) {
	    // use to identify the zoom gesture
	    _touchMode = ZOOM;
	    _initialSecondX = Engine.getInstance().getInputSystem()
		    .getTouchScreen().getTouchEvent().getX(1);
	    _initialSecondY = Engine.getInstance().getInputSystem()
		    .getTouchScreen().getTouchEvent().getY(1);
	} else {
	    _touchMode = DRAG;
	}

	if (!Engine.getInstance().getInputSystem().getTouchScreen()
		.getPressed(0)) {
	    // set touchMode to NONE if screen is not pressed
	    _touchMode = NONE;
	}

	// use touch screen to move the camera
	//
	// not using replica island coordinates
	if (Engine.getInstance().getInputSystem().getTouchScreen()
		.getTouchEvent() != null) {

	    MotionEvent touchEvent = Engine.getInstance().getInputSystem()
		    .getTouchScreen().getTouchEvent();

	    switch (touchEvent.getAction()) {
		case MotionEvent.ACTION_DOWN:
		    // unproject test
		    _cam.getWorldCoord(new Vector2f(touchEvent.getX(),
			    touchEvent.getY()));

		    _initialX = touchEvent.getX();
		    _initialY = touchEvent.getY();

		    _touchMode = DRAG;
		case MotionEvent.ACTION_MOVE:
		    if (_touchMode == DRAG) {
			float newX = touchEvent.getX();
			float newY = touchEvent.getY();

			float moveX = newX - _initialX;
			float moveY = newY - _initialY;
			_initialX = newX;
			_initialY = newY;

			if (Math.abs(moveX) > TOUCH_SENSITIVITY ||
			    Math.abs(moveY) > TOUCH_SENSITIVITY) {
			    if (Math.abs(moveX) > Math.abs(moveY)) {
				if (moveX > 0) {
				    // Left to right
				    _cam.moveLeft(-TOUCH_CAMERA_SPEED);
				    logger.debug("Left to right: " +
						 Float.toString(-moveX));

				} else if (moveX < 0) {
				    // Right to left
				    _cam.moveLeft(TOUCH_CAMERA_SPEED);
				    logger.debug("Right to left: " +
						 Float.toString(-moveX));
				}

			    } else {
				if (moveY > 0) {
				    // up to down
				    _cam.moveUp(TOUCH_CAMERA_SPEED);
				    logger.debug("Up to down: " +
						 Float.toString(moveY));

				} else if (moveY < 0) {
				    // Down to up
				    _cam.moveUp(-TOUCH_CAMERA_SPEED);
				    logger.debug("Down to up: " +
						 Float.toString(moveY));
				}
			    }
			}
		    } else if (_touchMode == ZOOM) {
			// pinch gesture for zooming
			float newPinchDist = Engine.getInstance()
				.getInputSystem().getTouchScreen()
				.getPinchDistance();

			float newX = touchEvent.getX();
			float newY = touchEvent.getY();
			float newSecondX = touchEvent.getX(1);
			float newSecondY = touchEvent.getY(1);

			float moveX = newX - _initialX;
			float moveY = newY - _initialY;
			float moveSecondX = newSecondX - _initialSecondX;
			float moveSecondY = newSecondY - _initialSecondY;

			_initialX = newX;
			_initialY = newY;
			_initialSecondX = newSecondX;
			_initialSecondY = newSecondY;
			if (Math.abs(moveX) > TOUCH_SENSITIVITY ||
			    Math.abs(moveY) > TOUCH_SENSITIVITY ||
			    Math.abs(moveSecondX) > TOUCH_SENSITIVITY ||
			    Math.abs(moveSecondY) > TOUCH_SENSITIVITY) {

			    logger.debug("ZOOMING");

			    if (newPinchDist > _pinchDist) {
				_cam.moveForward(1.0f);
				logger.debug("pinch out: " +
					     Float.toString(_pinchDist) + ", " +
					     Float.toString(newPinchDist));
			    } else {
				_cam.moveForward(-1.0f);
				logger.debug("pinch in: " +
					     Float.toString(_pinchDist) + ", " +
					     Float.toString(newPinchDist));
			    }

			}
			_pinchDist = newPinchDist;
			break;
		    }
	    }
	}

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
	processInput();

	// Update game objects
	for (IGameObject object : _objects) {
	    object.update();
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

	// Get renderer to draw everything on its renderable list -zenja
	Engine.getInstance().getRenderer().draw(gl);

	gl.glPushMatrix();
	gl.glTranslatef(0.0f, 0, 0f);
	_grid.draw(gl);
	gl.glPopMatrix();
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
	gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

	// prevent divide by zero.
	// @HACK - Forgiven since h == 0 means the game window is probably
	// hidden.
	if (h == 0) {
	    h = 1;
	}

	_cam.setViewPort(gl, 0, 0, w, h);
	_cam.setPerspective(gl, 45.0f, (float) w / (float) h, 0.1f, 100.0f);
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

}