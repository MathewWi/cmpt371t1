package ca.sandstorm.luminance.gamelogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;
import android.widget.Button;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.gui.GUIManager;
import ca.sandstorm.luminance.state.IState;


public class MenuState implements IState
{
    private static final Logger logger = LoggerFactory.getLogger(MenuState.class);
    private GUIManager _guiManager;

    public MenuState()
    {
	_guiManager = new GUIManager();
    }
    
    public GUIManager getGUIManager()
    {
	return _guiManager;
    }
    
    public boolean addButton(ca.sandstorm.luminance.gui.Button button)
    {
	return _guiManager.addButton(button);
    }
    
    @Override
    public void deviceChanged(GL10 gl, int w, int h)
    {
	// TODO Auto-generated method stub

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
    }


    @Override
    public void draw(GL10 gl)
    {
	// TODO Auto-generated method stub
	logger.debug("draw(GL10)");
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
