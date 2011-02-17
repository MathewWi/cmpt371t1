package ca.sandstorm.luminance.gamelogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.microedition.khronos.opengles.GL10;

import android.view.KeyEvent;
import android.view.MotionEvent;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.gui.Button;
import ca.sandstorm.luminance.gui.GUIManager;
import ca.sandstorm.luminance.input.InputButton;
import ca.sandstorm.luminance.state.IState;


public class MenuState implements IState
{
    private static final Logger logger = LoggerFactory.getLogger(MenuState.class);
    private GUIManager _guiManager;

    public MenuState()
    {
	_guiManager = new GUIManager();
	
	Button b = new Button(10, 10, 100, 100, null);
	_guiManager.addButton(b);
    }
    
    public GUIManager getGUIManager()
    {
	return _guiManager;
    }
    

    
    @Override
    public void deviceChanged(GL10 gl, int w, int h)
    {
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

	// setup 2D ortho mode with 0,0 top left
	gl.glViewport(0,0,w,h);
	gl.glMatrixMode(GL10.GL_PROJECTION);
	gl.glLoadIdentity();
	gl.glOrthof(0, w, h, 0, -1.0f, 1.0f);
	gl.glMatrixMode(GL10.GL_MODELVIEW);
	//gl.glLoadIdentity();
    }


    @Override
    public void pause()
    {
	// TODO Auto-generated method stub

    }


    @Override
    public void resume()
    {
	// TODO Auto-generated method stub

    }


    @Override
    public void update(GL10 gl)
    {
	System.out.println("update(GL10)");
	// TODO Auto-generated method stub
	if (Engine.getInstance().getInputSystem().getTouchScreen().getTouchEvent() != null) {
	    MotionEvent touchEvent = Engine.getInstance().getInputSystem()
	    				.getTouchScreen().getTouchEvent();
	    
	    if (touchEvent.getAction() == MotionEvent.ACTION_UP) {
		logger.debug("Menu has been touched");
		if (_guiManager.touchOccured(touchEvent) != null) {
		    logger.debug("button has been tapped");
		}
	    }
	}
	
	InputButton[] keys = Engine.getInstance().getInputSystem().getKeyboard().getKeys();
	if (keys[KeyEvent.KEYCODE_1].getPressed()) {
	    Engine.getInstance().popState();
	}
    }


    @Override
    public void draw(GL10 gl)
    {
	gl.glViewport(0,0,Engine.getInstance().getViewWidth(), Engine.getInstance().getViewHeight());
	gl.glMatrixMode(GL10.GL_PROJECTION);
	gl.glLoadIdentity();
	gl.glOrthof(0, Engine.getInstance().getViewWidth(), Engine.getInstance().getViewHeight(), 0, -1.0f, 1.0f);
	gl.glMatrixMode(GL10.GL_MODELVIEW);	
	gl.glLoadIdentity();
	
	gl.glClearColor(0, 0, 0, 1);
	gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
	_guiManager.draw(gl);
    }


    @Override
    public void messageRecieved()
    {
	// TODO Auto-generated method stub

    }


    @Override
    public boolean isActive()
    {
	// TODO Auto-generated method stub
	return true;
    }


    @Override
    public boolean isVisible()
    {
	// TODO Auto-generated method stub
	return true;
    }


    @Override
    public void init(GL10 gl)
    {
	// TODO Auto-generated method stub
	
    }

}
