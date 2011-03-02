package ca.sandstorm.luminance.gamelogic;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.microedition.khronos.opengles.GL10;

import android.view.KeyEvent;
import android.view.MotionEvent;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.gui.Button;
import ca.sandstorm.luminance.gui.GUIManager;
import ca.sandstorm.luminance.gui.IWidget;
import ca.sandstorm.luminance.input.InputButton;
import ca.sandstorm.luminance.resources.TextureResource;
import ca.sandstorm.luminance.state.IState;


public class MenuState implements IState
{
    private static final Logger logger = LoggerFactory.getLogger(MenuState.class);
    private GUIManager _guiManager;
    private boolean _tapped;

    public MenuState()
    {
	_tapped = false;
	_guiManager = new GUIManager();
	
	Button startButton = new Button(20, 50, 280, 30, "Start");
	startButton.setTextureResourceLocation("textures/startImage.png");
	Button optionsButton = new Button(20, 110, 280, 30, "Options");
	optionsButton.setTextureResourceLocation("textures/optionsImage.png");
	Button aboutButton = new Button(20, 170, 280, 30, "About");
	aboutButton.setTextureResourceLocation("textures/aboutImage.png");
	_guiManager.addButton(startButton);
	_guiManager.addButton(optionsButton);
	_guiManager.addButton(aboutButton);
    }
    
    /**
     * Get the GUIManager being used by this MenuState.
     * @return the GUIManager being by the MenuState.
     */
    public GUIManager getGUIManager()
    {
	return _guiManager;
    }
    
    
    /**
     * Engine has informed the state the device has changed.
     * @param gl OpenGL context
     * @param w The new width value
     * @param h The new height value
     * @precond We do not control the values here.  w > 0 && h > 0 would be optimal.
     */    
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
    
    
    /**
     * init()
     * Engine has informed this state can init any openGL 
     * required resources.
     * @param gl OpenGL context
     */
    @Override
    public void init(GL10 gl)
    {
	// TODO Auto-generated method stub
	logger.debug("MenuState init has been called");
	try {
	    for (IWidget widget : _guiManager.getButtons()) {
		if (widget != null) {
		    String textureResourceLocation = widget.getTextureResourceLocation();
		    TextureResource texture = Engine.getInstance().getResourceManager().loadTexture(gl, textureResourceLocation);
		    widget.setTexture(texture);
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
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
	// TODO Auto-generated method stub

    }


    /**
     * resume()
     * Engine has requested this state to resume.
     */    
    @Override
    public void resume()
    {
	// TODO Auto-generated method stub

    }


    /**
     * Engine has requested this state update itself.
     * @param OpenGL context
     */
    @Override
    public void update(GL10 gl)
    {
	// TODO Auto-generated method stub
	if (Engine.getInstance().getInputSystem().getTouchScreen().getTouchEvent() != null) {
	    MotionEvent touchEvent = Engine.getInstance().getInputSystem()
	    				.getTouchScreen().getTouchEvent();
	    
	    if (touchEvent.getAction() == MotionEvent.ACTION_DOWN) {
		_tapped = true;
	    } if (touchEvent.getAction() == MotionEvent.ACTION_UP && _tapped) {
		logger.debug("Menu has been touched");
		if (_guiManager.touchOccured(touchEvent) != null) {
		    logger.debug("button has been tapped");
		}
		_tapped = false;
	    }
	}
	
	InputButton[] keys = Engine.getInstance().getInputSystem().getKeyboard().getKeys();
	if (keys[KeyEvent.KEYCODE_1].getPressed()) {
	    Engine.getInstance().popState();
	}
    }


    /**
     * Engine has requested this state draw itself.
     * @param OpenGL context
     */
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


}
