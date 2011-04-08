package ca.sandstorm.luminance.gamelogic;

import javax.vecmath.Point2i;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.view.KeyEvent;
import android.view.MotionEvent;
import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.camera.Camera;
import ca.sandstorm.luminance.gameobject.Grid;
import ca.sandstorm.luminance.gametools.ToolType;
import ca.sandstorm.luminance.gametools.Toolbelt;
import ca.sandstorm.luminance.gui.Button;
import ca.sandstorm.luminance.gui.GUIManager;
import ca.sandstorm.luminance.input.InputButton;
import ca.sandstorm.luminance.input.InputTouchScreen;
import ca.sandstorm.luminance.math.Colliders;
import ca.sandstorm.luminance.math.Ray;
import ca.sandstorm.luminance.resources.SoundResource;

/**
 * Class to read and process user input in the game state.
 * 
 * @author Jonny
 */
public class GameStateInput
{
    private static final Logger logger = LoggerFactory
    					.getLogger(GameStateInput.class);
    
    private static final float CAMERA_MIN_HEIGHT = 6.0f;
    private static final float CAMERA_MAX_HEIGHT = 25.0f;
    private static final float CAMERA_SCROLL_MAX_WIDTH_SCALE = 0.65f;
    private static final float CAMERA_SCROLL_MAX_LENGTH_SCALE = 0.90f;
    
    private Camera _cam;
    private Toolbelt _toolbelt;
    private GUIManager _guiManager;
    private Grid _grid;
    private boolean _isGridEnabled;
    
 // input handling properties
    private Vector2f _initialCoor = new Vector2f(0.0f, 0.0f);
    
    // the second finger/pointer coordinates
    private Vector2f _initialSecCoor = new Vector2f(0.0f, 0.0f);
    private float _pinchDist = 0.0f;
    
    private static final int ZOOM = 2;
    private static final float TOUCH_CAMERA_SPEED = 0.5f;
    private static final float TOUCH_SENSITIVITY = 3.0f;

    private int _touchMode = InputTouchScreen.NONE;

    private static final float DISTANCE_TIME_FACTOR = 0.4f;
    private float _acceleration = 0.0f;
    private Vector2f _flingMove = new Vector2f(0.0f, 0.0f);
    private Vector2f _flingDistance = new Vector2f(0.0f, 0.0f);

    private boolean _flingEffect = false;
    
    public GameStateInput()
    {
	logger.debug("GameStateInput()");
    }
    
    /**
     *  Process the input of the game state
     * @param cam 
     * @param toolbelt
     * @param grid
     * @param guiManager
     * @param isGridEnabled
     */
    public void process(Camera cam, Toolbelt toolbelt, Grid grid, GUIManager guiManager, boolean isGridEnabled)
    {
	_cam = cam;
	_toolbelt = toolbelt;
	_grid = grid;
	_guiManager = guiManager;
	_isGridEnabled = isGridEnabled;
	
	processKeyboardInput();
	processTouchInput();
    }

    /**
     * Process keyboard input of the game state
     */
    public void processKeyboardInput()
    {
	InputButton[] keys = Engine.getInstance().getInputSystem()
				.getKeyboard().getKeys();

	// Select tool based on keyboard input -zenja
	if (keys[KeyEvent.KEYCODE_M].getPressed()) {
	    _toolbelt.selectTool(ToolType.Mirror);
	}
	if (keys[KeyEvent.KEYCODE_P].getPressed()) {
	    _toolbelt.selectTool(ToolType.Prism);
	}
	if (keys[KeyEvent.KEYCODE_K].getPressed()) {
	    _toolbelt.selectTool(ToolType.Eraser);
	}

	// Testing audio
	if (keys[KeyEvent.KEYCODE_6].getPressed()) {
	    Engine.getInstance().getAudio().play((SoundResource)Engine.getInstance().getResourceManager().getResource("sounds/sample.ogg"), 0.9f);
	}

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

    }
	
    
    /**
     * Process touch input of the game state
     */
    public void processTouchInput()
    {
	if (_isGridEnabled) {
	    scrollGesture();
	    flingGesture();
	}
	
	if (Engine.getInstance().getInputSystem().getTouchScreen()
		.getPressed(1) && _isGridEnabled) {
	    // use to identify the zoom gesture
	    _touchMode = ZOOM;
	    _initialSecCoor.setX(Engine.getInstance().getInputSystem()
	     		    .getTouchScreen().getTouchEvent().getX(1)); 
	    _initialSecCoor.setY(Engine.getInstance().getInputSystem()
	     		    .getTouchScreen().getTouchEvent().getY(1));
	} else {
	    _touchMode = InputTouchScreen.NONE;
	}

	if (!Engine.getInstance().getInputSystem().getTouchScreen()
		.getPressed(0)) {
	    // set touchMode to NONE if screen is not pressed
	    _touchMode = InputTouchScreen.NONE;
	}
	
	// use touch screen to move the camera
	//
	// not using replica island coordinates
	if (Engine.getInstance().getInputSystem().getTouchScreen()
		.getTouchEvent() != null) {

	    MotionEvent touchEvent = Engine.getInstance().getInputSystem()
		    .getTouchScreen().getTouchEvent();
	    
	    if (Engine.getInstance().getInputSystem()
		.getTouchScreen().getTouchMode() == InputTouchScreen.ON_SINGLE_TAP_CONFIRMED){
		logger.debug("Single click: " + touchEvent.getX() + "," + touchEvent.getY());
		if (_isGridEnabled) {
		    mouseClick(touchEvent.getX(), 
		               touchEvent.getY() 
		               - Engine.getInstance().getMenuBarHeight() 
		               - Engine.getInstance().getTitleBarHeight());
		}
		

		_guiManager.touchOccured(touchEvent.getX(), touchEvent.getY() 
		                                           - Engine.getInstance().getMenuBarHeight() 
		                                           - Engine.getInstance().getTitleBarHeight());
		
		_guiManager.letGoOfButton();
		        
		Engine.getInstance().getInputSystem()
			.getTouchScreen().setTouchMode(InputTouchScreen.NONE);
	    }
	    
	    
	    if (Engine.getInstance().getInputSystem().getTouchScreen().getTouchMode() == InputTouchScreen.ON_DOUBLE_TAP_CONFIRMED 
		    && _isGridEnabled) {
		logger.debug("Double click: " + touchEvent.getX() + "," + touchEvent.getY());
		mouseDoubleClick(touchEvent.getX(), 
			               touchEvent.getY() 
			               - Engine.getInstance().getMenuBarHeight() 
			               - Engine.getInstance().getTitleBarHeight());		
		
		Engine.getInstance().getInputSystem().getTouchScreen().setTouchMode(InputTouchScreen.NONE);		
	    }
	    
	    // handle switching texture for pause button and inGame menu
	    if (Engine.getInstance().getInputSystem().getTouchScreen()
		    .getTouchMode() == InputTouchScreen.ON_DOWN) {
		
		Button touchButton = _guiManager.touchOccured(touchEvent.getX(), 
		                                              touchEvent.getY() 
		                                              - Engine.getInstance().getMenuBarHeight() 
		                                              - Engine.getInstance().getTitleBarHeight());
		if (touchButton != null) {
		    if (!touchButton.isToggle()) {
			touchButton.setIsTapped(true);
		    }
		}

		Engine.getInstance().getInputSystem().getTouchScreen().setTouchMode(InputTouchScreen.NONE);
	    }

	    // handle zoom input
	    if (touchEvent.getAction() == MotionEvent.ACTION_MOVE) {
		zoomGesture(touchEvent);
	    }
	}
    }
    
    /**
     * Handle a mouse/touchpad click.
     * @param x Click X coordinate
     * @param y Click Y coordinate
     */
    private void mouseClick(float x, float y)
    {
	Ray r = _cam.getWorldCoord(new Vector2f(x, y));
	if (r == null)
	    return;

	Vector3f colPoint = Colliders.collide(r, _grid.getPlane());
	logger.debug("CollisionPoint: " + colPoint);

	Point2i gridPoint = _grid.getGridPosition(colPoint.x, colPoint.y, colPoint.z);
	logger.debug("Grid Point: " + gridPoint);
	
	_toolbelt.processClick(x, y, gridPoint);
    }
    
    /**
     * Handle double click input
     * @param x
     * @param y
     */
    private void mouseDoubleClick(float x, float y)
    {
	logger.debug("mouseDoubleClick(" + x + ", " + y + ")");
	
	Ray r = _cam.getWorldCoord(new Vector2f(x, y));
	if (r == null)
	    return;
	
	Vector3f colPoint = Colliders.collide(r, _grid.getPlane());
	logger.debug("CollisionPoint: " + colPoint);

	Point2i gridPoint = _grid.getGridPosition(colPoint.x, colPoint.y, colPoint.z);
	logger.debug("Grid Point: " + gridPoint);	
	
	_toolbelt.processDoubleClick(x, y, gridPoint);
    }

    /**
     * Handle scroll gesture
     */
    private void scrollGesture()
    {
	// handle the scroll/drag gesture
	if(Engine.getInstance().getInputSystem()
		.getTouchScreen().getTouchMode() == InputTouchScreen.ON_SCROLL &&
		_touchMode != ZOOM){
	    
	    _cam.moveLeft(Engine.getInstance().getInputSystem()
	                  .getTouchScreen().getDistanceX()
	                  * TOUCH_CAMERA_SPEED * _cam.getEye().y);
	    double distance = _cam.getEye().x - _grid.getGridCenter().x;
	    distance = Math.sqrt(distance * distance);
	    if (distance > _grid.getTotalWidth() * CAMERA_SCROLL_MAX_WIDTH_SCALE)
	    {
		    _cam.moveLeft(-Engine.getInstance().getInputSystem()
		                  .getTouchScreen().getDistanceX()
		                  * TOUCH_CAMERA_SPEED * _cam.getEye().y);
	    }
	    
	    
	    _cam.moveUp(-Engine.getInstance().getInputSystem()
	                  .getTouchScreen().getDistanceY()
	                  * TOUCH_CAMERA_SPEED * _cam.getEye().y);
	    distance = _cam.getEye().z - _grid.getGridCenter().z;
	    distance = Math.sqrt(distance * distance);
	    if (distance > _grid.getTotalHeight() * CAMERA_SCROLL_MAX_LENGTH_SCALE)
	    {
		    _cam.moveUp(Engine.getInstance().getInputSystem()
		                  .getTouchScreen().getDistanceY()
		                  * TOUCH_CAMERA_SPEED * _cam.getEye().y);
	    }	    
	    
	    // set the touch mode back to none
	    Engine.getInstance().getInputSystem()
		.getTouchScreen().setTouchMode(InputTouchScreen.NONE);
	}
    }
    
    /**
     * Handle zoom gesture
     * @param touchEvent current touch event.
     */
    private void zoomGesture(MotionEvent touchEvent)
    {
	if (_touchMode == ZOOM) {
		// pinch gesture for zooming
		float newPinchDist = Engine.getInstance()
			.getInputSystem().getTouchScreen()
			.getPinchDistance();

		float newX = touchEvent.getX();
		float newY = touchEvent.getY();
		float newSecondX = touchEvent.getX(1);
		float newSecondY = touchEvent.getY(1);

		float moveX = newX - _initialCoor.getX();
		float moveY = newY - _initialCoor.getY();
		float moveSecondX = newSecondX - _initialSecCoor.getX();
		float moveSecondY = newSecondY - _initialSecCoor.getY();

		_initialCoor.setX(newX);
		_initialCoor.setY(newY);
		_initialSecCoor.setX(newSecondX);
		_initialSecCoor.setY(newSecondY);
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
			double distance = _cam.getEye().y - _grid.getGridCenter().y;
			logger.debug("distance: " + distance);			
			    if (distance < CAMERA_MIN_HEIGHT)
			    {
				_cam.moveForward(-1.0f);
			    }	  			
		    } else {
			_cam.moveForward(-1.0f);
			logger.debug("pinch in: " +
				     Float.toString(_pinchDist) + ", " +
				     Float.toString(newPinchDist));
			double distance = _cam.getEye().y - _grid.getGridCenter().y;
			logger.debug("distance: " + distance);
			    if (distance > CAMERA_MAX_HEIGHT)
			    {
				_cam.moveForward(1.0f);
			    }			
		    }

		}
		_pinchDist = newPinchDist;
	    }
    }
    
    /**
     * Method to handle fling gesture
     */
    private void flingGesture()
    {
	// handle fling gesture
	if(Engine.getInstance().getInputSystem()
		.getTouchScreen().getTouchMode() == InputTouchScreen.ON_FLING && 
		_touchMode != ZOOM){
    
            _flingDistance.setX((DISTANCE_TIME_FACTOR * Engine.getInstance()
                    					.getInputSystem()
                    					.getTouchScreen()
                    					.getVelocityX()/2));
            _flingDistance.setY((DISTANCE_TIME_FACTOR * Engine.getInstance()
                    					.getInputSystem()
                    					.getTouchScreen()
                    					.getVelocityY()/2));
	    
            _acceleration = 2.0f;
            _flingEffect = true;

            // set touch mode to none
	    Engine.getInstance().getInputSystem()
		.getTouchScreen().setTouchMode(InputTouchScreen.NONE);
	}
	
	// method to move camera based on fling gesture
	onFlingMove();
    }
   
    /**
     * Calculate the x and y after the fling gesture
     */
    private void calculateFlingMove()
    {
	_flingMove.setX(_cam.getEye().y * TOUCH_CAMERA_SPEED * _acceleration);
	_flingMove.setY(_cam.getEye().y * TOUCH_CAMERA_SPEED * _acceleration);
	
	if (_acceleration <= 0){
	    _flingEffect = false;   
	    
	}else {
	    if (_flingDistance.x > 0){
		// fling left to right
		_flingDistance.x = _flingDistance.x - _flingMove.x;
		if (_flingDistance.x < 0){
		    _flingMove.x = 0.0f;    
		}
	    } else if (_flingDistance.x < 0) {
		// fling right to left
		_flingMove.x = -_flingMove.x;
		_flingDistance.x = _flingDistance.x - _flingMove.x;
		if (_flingDistance.x > 0){
		    _flingMove.x = 0.0f;
		}    	
	    }
	
	    if (_flingDistance.y > 0) {
		// fling up to down
		_flingDistance.y = _flingDistance.y - _flingMove.y;
		if (_flingDistance.y < 0){
		    _flingMove.y = 0.0f;
		}
		
	    } else if (_flingDistance.y < 0) {
		// fling down to up
		_flingMove.y = -_flingMove.y;
		_flingDistance.y = _flingDistance.y - _flingMove.y;
		if (_flingDistance.y > 0){
		    _flingMove.y = 0.0f;    
		}  
	    }
	
	    if (_flingMove.y == 0.0f && _flingMove.y == 0.0f){
		_flingEffect = false;
		
	    } else{
		_flingEffect = true;
	    }
	    
	    _acceleration = _acceleration - 0.05f;
	}

    }
    
    /**
     * Method to handle fling gesture
     */
    private void onFlingMove()
    {
	calculateFlingMove();
	if(_flingEffect){
	    // move the camera when fling effect is true
	    _cam.moveLeft(-_flingMove.x);
	    double distance = _cam.getEye().x - _grid.getGridCenter().x;
	    distance = Math.sqrt(distance * distance);
	    if (distance > _grid.getTotalWidth() * CAMERA_SCROLL_MAX_WIDTH_SCALE)
	    {
		_cam.moveLeft(_flingMove.x);
	    }	    
	    
	    _cam.moveUp(_flingMove.x);
	    distance = _cam.getEye().z - _grid.getGridCenter().z;
	    distance = Math.sqrt(distance * distance);
	    if (distance > _grid.getTotalHeight() * CAMERA_SCROLL_MAX_LENGTH_SCALE)
	    {
		_cam.moveUp(-_flingMove.y);
	    }	    
	}
    }
 
}
