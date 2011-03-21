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
    
    private Camera _cam;
    private Toolbelt _toolbelt;
    private GUIManager _guiManager;
    private Grid _grid;
 // input handling properties
    private float _initialX = 0.0f;
    private float _initialY = 0.0f;
    // the second finger/pointer coordinates
    private float _initialSecondX = 0.0f;
    private float _initialSecondY = 0.0f;
    private float _pinchDist = 0.0f;
    //private static final int SCROLL = 1;
    private static final int ZOOM = 2;
    private static final float TOUCH_CAMERA_SPEED = 0.5f;
    private static final float TOUCH_SENSITIVITY = 3.0f;
    private static final int NONE = -1;
    private static final int ON_SCROLL = 0;
    private static final int ON_FLING = 1;
    private static final int ON_SINGLE_TAP_CONFIRMED = 2;
    private static final int ON_DOWN = 3;
    private static final int ON_PRESS = 4;
    private int _touchMode = NONE;


    private static final float DISTANCE_TIME_FACTOR = 0.4f;
    private float _acceleration = 0.0f;
    private float _flingMoveX = 0.0f;
    private float _flingMoveY = 0.0f;
    private float _flingDistanceX = 0.0f;
    private float _flingDistanceY = 0.0f;
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
     */
    public void process(Camera cam, Toolbelt toolbelt, Grid grid, GUIManager guiManager)
    {
	_cam = cam;
	_toolbelt = toolbelt;
	_grid = grid;
	_guiManager = guiManager;
	
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
	if (keys[KeyEvent.KEYCODE_Z].getPressed()) {
	    _toolbelt.adjustRotation(-1);
	}
	if (keys[KeyEvent.KEYCODE_X].getPressed()) {
	    _toolbelt.adjustRotation(1);
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
	scrollGesture();
	flingGesture();
	
	if (Engine.getInstance().getInputSystem().getTouchScreen()
		.getPressed(1)) {
	    // use to identify the zoom gesture
	    _touchMode = ZOOM;
	    _initialSecondX = Engine.getInstance().getInputSystem()
		    .getTouchScreen().getTouchEvent().getX(1);
	    _initialSecondY = Engine.getInstance().getInputSystem()
		    .getTouchScreen().getTouchEvent().getY(1);
	} else {
	    _touchMode = NONE;
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
	    
	    if (Engine.getInstance().getInputSystem()
		.getTouchScreen().getTouchMode() == ON_SINGLE_TAP_CONFIRMED){
		    mouseClick(touchEvent.getX(), 
		               touchEvent.getY() 
		               - Engine.getInstance().getMenuBarHeight() 
		               - Engine.getInstance().getTitleBarHeight());
		    
		    Engine.getInstance().getInputSystem()
			.getTouchScreen().setTouchMode(NONE);
	    }
	    
	    if (Engine.getInstance().getInputSystem()
			.getTouchScreen().getTouchMode() == ON_PRESS) {
		Button touchButton = _guiManager.touchOccured(touchEvent.getX(), 
		                                              touchEvent.getY() 
		                                              - Engine.getInstance().getMenuBarHeight() 
		                                              - Engine.getInstance().getTitleBarHeight());
	    
		if (touchButton != null){
		    touchButton.setIsTapped(true);
		}
	    }
	    
	    switch (touchEvent.getAction()) {		    
		case MotionEvent.ACTION_UP:
		    //_guiManager.letGoOfButton();
		    break;
		case MotionEvent.ACTION_MOVE:
		    zoomGesture(touchEvent);
		    break;
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
	// Check if pause was pressed
	/*Button touchedButton = _guiManager.touchOccured(x, y);
	if(touchedButton != null) {
	    if (touchedButton.getTitle().equalsIgnoreCase("pause")) {
		logger.debug("pause has been tapped");
		// TODO: disable pausing for now (it doesn't work correctly atm)
		//Engine.getInstance().pause();
	    }
	}*/
	
	Ray r = _cam.getWorldCoord(new Vector2f(x, y));
	if (r == null)
	    return;

	Vector3f colPoint = Colliders.collide(r, _grid.getPlane());
	logger.debug("CollisionPoint: " + colPoint);

	Point2i gridPoint = _grid.getGridPosition(colPoint.x, colPoint.y, colPoint.z);
	logger.debug("Grid Point: " + gridPoint);
	
	_toolbelt.processClick(x, y, gridPoint);
    }

    
    private void scrollGesture()
    {
	// handle the scroll/drag gesture
	if(Engine.getInstance().getInputSystem()
		.getTouchScreen().getTouchMode() == ON_SCROLL &&
		_touchMode != ZOOM){
	    
	    _cam.moveLeft(Engine.getInstance().getInputSystem()
	                  .getTouchScreen().getDistanceX()
	                  * TOUCH_CAMERA_SPEED * _cam.getEye().getY());
	    _cam.moveUp(-Engine.getInstance().getInputSystem()
	                  .getTouchScreen().getDistanceY()
	                  * TOUCH_CAMERA_SPEED * _cam.getEye().getY());
	    
	    // set the touch mode back to none
	    Engine.getInstance().getInputSystem()
		.getTouchScreen().setTouchMode(NONE);
	}
    }
    
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
	    }
    }
    
    private void flingGesture()
    {
	// handle fling gesture
	if(Engine.getInstance().getInputSystem()
		.getTouchScreen().getTouchMode() == ON_FLING && 
		_touchMode != ZOOM){
    
            _flingDistanceX = (DISTANCE_TIME_FACTOR * Engine.getInstance()
                    					.getInputSystem()
                    					.getTouchScreen()
                    					.getVelocityX()/2);
            _flingDistanceY = (DISTANCE_TIME_FACTOR * Engine.getInstance()
                    					.getInputSystem()
                    					.getTouchScreen()
                    					.getVelocityY()/2);
	    
            _acceleration = 2.0f;
            _flingEffect = true;

            // set touch mode to none
	    Engine.getInstance().getInputSystem()
		.getTouchScreen().setTouchMode(NONE);
	}
	
	// method to move camera based on fling gesture
	onFlingMove();
    }
   
    /**
     * Calculate the x and y after the fling gesture
     */
    private void calculateFlingMove()
    {
	_flingMoveX = _cam.getEye().getY() * TOUCH_CAMERA_SPEED * _acceleration;
	_flingMoveY = _cam.getEye().getY() * TOUCH_CAMERA_SPEED * _acceleration;
	
	if (_acceleration <= 0){
	    _flingEffect = false;   
	    
	}else {
	    if (_flingDistanceX > 0){
		// fling left to right
		_flingDistanceX = _flingDistanceX - _flingMoveX;
		if (_flingDistanceX < 0){
		    _flingMoveX = 0.0f;    
		}
	    } else if (_flingDistanceX < 0) {
		// fling right to left
		_flingMoveX = -_flingMoveX;
		_flingDistanceX = _flingDistanceX - _flingMoveX;
		if (_flingDistanceX > 0){
		    _flingMoveX = 0.0f;
		}    	
	    }
	
	    if (_flingDistanceY > 0) {
		// fling up to down
		_flingDistanceY = _flingDistanceY - _flingMoveY;
		if (_flingDistanceY < 0){
		    _flingMoveY = 0.0f;
		}
		
	    } else if (_flingDistanceY < 0) {
		// fling down to up
		_flingMoveY = -_flingMoveY;
		_flingDistanceY = _flingDistanceY - _flingMoveY;
		if (_flingDistanceY > 0){
		    _flingMoveY = 0.0f;    
		}  
	    }
	
	    if (_flingMoveX == 0.0f && _flingMoveY == 0.0f){
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
	    _cam.moveLeft(-_flingMoveX);
	    _cam.moveUp(_flingMoveY);
	}
    }
 
}
