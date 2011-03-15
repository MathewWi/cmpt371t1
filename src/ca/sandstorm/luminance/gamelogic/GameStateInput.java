package ca.sandstorm.luminance.gamelogic;

import java.io.IOException;

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
    private Grid _grid;
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
    
    public GameStateInput()
    {
	logger.debug("GameStateInput()");
    }
    
    public void process(Camera cam, Toolbelt toolbelt, Grid grid)
    {
	_cam = cam;
	_toolbelt = toolbelt;
	_grid = grid;
	InputButton[] keys = Engine.getInstance().getInputSystem()
				.getKeyboard().getKeys();
	
	
/*	
	moveCamUp(keys);
	moveCamDown(keys);
	moveCamLeft(keys);
	moveCamRight(keys);
	zoomIn(keys);
	zoomOut(keys);
	rotateUp(keys);
	rotateDown(keys);
	rotateLeft(keys);
	rotateRight(keys);

	quitGame(keys);
*/	
	processKeyboardInput();
	processTouchInput();
    }
    /*
    public void moveCamUp(InputButton[] keys)
    {
	if (keys[KeyEvent.KEYCODE_W].getPressed()) {
	    _cam.moveUp(1.0f);
	}
    }
    
    public void moveCamDown(InputButton[] keys)
    {
	if (keys[KeyEvent.KEYCODE_S].getPressed()) {
	    _cam.moveUp(-1.0f);
	}
    }
    
    public void moveCamLeft(InputButton[] keys)
    {
	if (keys[KeyEvent.KEYCODE_A].getPressed()) {
	    // _cam.rotateCamera(-0.01f, 0, 1, 0);
	    _cam.moveLeft(-1.0f);
	}
    }
    
    public void moveCamRight(InputButton[] keys)
    {
	if (keys[KeyEvent.KEYCODE_D].getPressed()) {
	    // _cam.rotateCamera(0.01f, 0, 1, 0);
	    _cam.moveLeft(1.0f);
	}
    }
    
    public void zoomIn(InputButton[] keys)
    {
	if (keys[KeyEvent.KEYCODE_E].getPressed()) {
	    _cam.moveForward(-1.0f);
	}
    }
    
    public void zoomOut(InputButton[] keys)
    {
	if (keys[KeyEvent.KEYCODE_Q].getPressed()) {
	    _cam.moveForward(-1.0f);
	}
    }
    
    public void rotateUp(InputButton[] keys)
    {
	//TODO
    }
    
    public void rotateDown(InputButton[] keys)
    {
	//TODO
    }
    
    public void rotateLeft(InputButton[] keys)
    {
	//TODO
    }
    
    public void rotateRight(InputButton[] keys)
    {
	//TODO
    }
    
    public void quitGame(InputButton[] keys)
    {
	if (keys[KeyEvent.KEYCODE_1].getPressed()) {
	    System.exit(-1);
	}
    }
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
	if (keys[KeyEvent.KEYCODE_5].getPressed()) {
	    try {
		Engine.getInstance().getAudio().playMusic("sounds/music1.mp3");
	    } catch (IOException e) {
		logger.error("IOException: " + e.getMessage());
		e.printStackTrace();
	    }
	}
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
	
    
    public void processTouchInput()
    {
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
		    // TODO: Make mouseClick() only trigger on a full click, and not when trying to zoom/drag
		    // TODO: Should the menu bar height be compensated for elsewhere, higher up?
		    _mouseClick(touchEvent.getX(), touchEvent.getY() - Engine.getInstance().getMenuBarHeight() - Engine.getInstance().getTitleBarHeight());
		    
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
     * Handle a mouse/touchpad click.
     * @param x Click X coordinate
     * @param y Click Y coordinate
     */
    private void _mouseClick(float x, float y)
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
}
