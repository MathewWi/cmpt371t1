package ca.sandstorm.luminance.gamelogic;

import javax.microedition.khronos.opengles.GL10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.view.KeyEvent;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.camera.Camera;
import ca.sandstorm.luminance.gameobject.Box;
import ca.sandstorm.luminance.input.InputButton;
import ca.sandstorm.luminance.state.IState;


public class GameState implements IState
{
    private static final Logger logger = LoggerFactory
	    .getLogger(GameState.class);

    private Camera _cam;

    private Box _box;


    public GameState()
    {
	logger.debug("GameState()");

	_cam = new Camera();
	_cam.setEye(0, 0, 5);
	
	_box = new Box();
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

    }


    @Override
    public void update(GL10 gl)
    {
	gl.glClearColor(0.182f, 0.182f, 1, 1);
	
	InputButton[] keys = Engine.getInstance().getInputSystem().getKeyboard().getKeys();
	if (keys[KeyEvent.KEYCODE_1].getPressed())
	{
	    System.exit(-1);
	}
	
	if (keys[KeyEvent.KEYCODE_W].getPressed())
	{
	   _cam.moveForward(1.0f);    
	}
	
	if (keys[KeyEvent.KEYCODE_S].getPressed())
	{
	    _cam.moveForward(-1.0f);       
	}	
	
	if (keys[KeyEvent.KEYCODE_A].getPressed())
	{
	    //_cam.rotateCamera(-0.01f, 0, 1, 0);
	    _cam.moveLeft(-1.0f);
	}
	
	if (keys[KeyEvent.KEYCODE_D].getPressed())
	{
	    //_cam.rotateCamera(0.01f, 0, 1, 0);
	    _cam.moveLeft(1.0f);
	}	
	
	if (keys[KeyEvent.KEYCODE_Q].getPressed())
	{
	    //_cam.rotateCamera(0.01f, 0, 1, 0);
	    _cam.moveUp(1.0f);
	}
	
	if (keys[KeyEvent.KEYCODE_E].getPressed())
	{
	    //_cam.rotateCamera(0.01f, 0, 1, 0);
	    _cam.moveUp(-1.0f);
	}	

    }


    private float rquad = 0.0f;


    @Override
    public void draw(GL10 gl)
    {
	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

	_cam.updateViewMatrix(gl);
	
	gl.glPushMatrix();
	//gl.glLoadIdentity();

	gl.glTranslatef(0.0f, 0, -14.0f);
	gl.glRotatef(rquad, 1.0f, 1.0f, 1.0f);
	_box.draw(gl);
	rquad -= 0.45f;
	
	gl.glPopMatrix();
    }


    @Override
    public void deviceChanged(GL10 gl, int w, int h)
    {
	logger.debug("deviceChanged(" + gl + ", " + w + ", " + h + ")");

	gl.glShadeModel(GL10.GL_SMOOTH); // Enable Smooth Shading
	gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // Black Background
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
