package ca.sandstorm.luminance.gamelogic;

import javax.microedition.khronos.opengles.GL10;

import android.widget.Button;

import ca.sandstorm.luminance.gui.GUIManager;
import ca.sandstorm.luminance.state.IState;


public class MenuState implements IState
{
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
	// TODO Auto-generated method stub

    }


    @Override
    public void draw(GL10 gl)
    {
	// TODO Auto-generated method stub

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
