package ca.sandstorm.luminance.gamelogic;

import java.io.IOException;
import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.opengl.GLU;
import android.view.KeyEvent;
import android.view.MotionEvent;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.camera.Camera;
import ca.sandstorm.luminance.gameobject.Box;
import ca.sandstorm.luminance.gameobject.Grid;
import ca.sandstorm.luminance.gameobject.IGameObject;
import ca.sandstorm.luminance.gameobject.Receptor;
import ca.sandstorm.luminance.gameobject.Skybox;
import ca.sandstorm.luminance.input.InputButton;
import ca.sandstorm.luminance.state.IState;


public class GameState implements IState
{
    private static final Logger logger = LoggerFactory
	    .getLogger(GameState.class);

    private Camera _cam;

    private Box _testBox;
    private Receptor _testReceptor;

    private Grid _grid;
    private Skybox _sky;

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

    // Container of game objects -zenja
    // TODO: Implement functions for manipulating this
    private LinkedList<IGameObject> objects;


    public GameState()
    {
	logger.debug("GameState()");

	objects = new LinkedList<IGameObject>();

	_grid = new Grid(10, 10, 1.0f, 1.0f);

	// Temporary box and receptor for testing
	Vector3f center = _grid.getCellCenter(5, 5);
	_testBox = new Box(center, new Vector3f(1f, 1f, 1f));
	_testReceptor = new Receptor(new Vector3f(0f, 0f, 0f), new Vector3f(1f, 1f, 1f));

	objects.add(_testBox);
	objects.add(_testReceptor);
	Engine.getInstance().getRenderer().addRenderable(_testBox);
	Engine.getInstance().getRenderer().addRenderable(_testReceptor);

	float maxSize = Math.max(_grid.getTotalWidth(), _grid.getTotalHeight());
	float camY = 1.15f * (maxSize / (float) (Math.tan((Math.PI / 6.0))));

	_cam = new Camera();
	_cam.setEye(_grid.getTotalWidth() / 2.0f, camY,
		    _grid.getTotalHeight() / 2.0f);
	_cam.setTarget(_grid.getTotalWidth() / 2.0f, 0,
		       _grid.getTotalHeight() / 2.0f);
	_cam.rotateCamera(0.01f, 1, 0, 0);

	_sky = new Skybox();
	objects.add(_grid);
	objects.add(_sky);
    }


    @Override
    public void pause()
    {
	logger.debug("pause()");

    }


    @Override
    public void resume()
    {
	logger.debug("resume()");

	Engine.getInstance().getTimer().reset();
    }


    @Override
    public void init(GL10 gl)
    {
	logger.debug("init()");
	// Load textures
	try {
	    Engine.getInstance().getResourceManager()
		    .loadTexture(gl, "textures/wallBrick.jpg");
	} catch (IOException e) {
	    // TODO: improve this
	    throw new RuntimeException("Unable to load a required texture!");
	}

	// Initialize objects
	for (IGameObject object : objects) {
	    object.initialize();
	}

	try {
	    _sky.init(gl);
	} catch (IOException e) {

	}
    }


    @Override
    public void update(GL10 gl)
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
	
	if (Engine.getInstance().getInputSystem()
		.getTouchScreen().getPressed(1)) {
	    // use to identify the zoom gesture
	    _touchMode = ZOOM;
	    _initialSecondX = Engine.getInstance().getInputSystem()
	    			.getTouchScreen().getTouchEvent().getX(1);
	    _initialSecondY = Engine.getInstance().getInputSystem()
				.getTouchScreen().getTouchEvent().getY(1);
	} else {
	    _touchMode = DRAG;
	}

	if (!Engine.getInstance().getInputSystem()
		.getTouchScreen().getPressed(0)) {
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
		    }else if (_touchMode == ZOOM) {
			// pinch gesture for zooming
			float newPinchDist = Engine.getInstance().getInputSystem()
	  					.getTouchScreen().getPinchDistance();

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
		
			    if (newPinchDist > _pinchDist){
				_cam.moveForward(1.0f);
				logger.debug("pinch out: " +
				             Float.toString(_pinchDist) +
				             ", " + Float.toString(newPinchDist));
			    } else {
				_cam.moveForward(-1.0f);
				logger.debug("pinch in: " +
				             Float.toString(_pinchDist) +
				             ", " + Float.toString(newPinchDist));
			    }
			    
			}
			_pinchDist = newPinchDist;
		    break;
		    }
	    }
	}
	
	// Update game objects -zenja
	for (IGameObject object : objects) {
	    object.update();
	}
    }


    @Override
    public void draw(GL10 gl)
    {
	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

	_cam.updateViewMatrix(gl);

	gl.glPushMatrix();
	gl.glLoadIdentity();
	GLU.gluLookAt(gl, 0, 0, 0, _cam.getTarget().x, _cam.getTarget().y,
		      _cam.getTarget().z, 0, 1, 0);
	gl.glDisable(GL10.GL_DEPTH_TEST);
	gl.glColor4f(0.2f, 0.2f, 0.2f, 1.0f);
	_sky.draw(gl);
	gl.glEnable(GL10.GL_DEPTH_TEST);
	gl.glPopMatrix();

	// Get renderer to draw everything on its renderable list -zenja
	Engine.getInstance().getRenderer().drawObjects(gl);

	gl.glPushMatrix();
	gl.glTranslatef(0.0f, 0, 0f);
	_grid.draw(gl);
	gl.glPopMatrix();
    }


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