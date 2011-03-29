package ca.sandstorm.luminance.gamelogic;

import java.io.IOException;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.graphics.PrimitiveQuad;
import ca.sandstorm.luminance.gui.GUIManager;
import ca.sandstorm.luminance.resources.TextureResource;
import ca.sandstorm.luminance.state.IState;

public class LevelMenuState implements IState
{
    private static final Logger logger = LoggerFactory.getLogger(LevelMenuState.class);
    private boolean _initialized = false;
    private GUIManager _guiManager;
    
    private TextureResource _background;
    private PrimitiveQuad _quad;
    
    public LevelMenuState()
    {
	_guiManager = new GUIManager(false);
    }

    public void init(GL10 gl)
    {
	float width = Engine.getInstance().getViewWidth();
	float height = Engine.getInstance().getViewHeight();
	_quad = new PrimitiveQuad(
	        new Vector3f(0, 0, 0),
		new Vector3f(width, height, 0)
	);

	try {
	    _background = Engine.getInstance().getResourceManager().loadTexture(gl, "textures/menuBackground.png");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    public boolean isInitialized()
    {
	return _initialized;
    }

    public boolean isVisible()
    {
	return true;
    }

    public boolean isActive()
    {
	return true;
    }

    public void messageRecieved()
    {
	// TODO Auto-generated method stub

    }

    public void deviceChanged(GL10 gl, int w, int h)
    {
	logger.debug("deviceChanged(" + gl + ", " + w + ", " + h + ")");
	
	gl.glShadeModel(GL10.GL_SMOOTH); // Enable Smooth Shading
	gl.glClearColor(0.182f, 0.182f, 1, 1); // Error blue
	gl.glClearDepthf(1.0f); // Depth Buffer Setup
	gl.glEnable(GL10.GL_DEPTH_TEST); // Enables Depth Testing
	gl.glDepthFunc(GL10.GL_LEQUAL); // The Type Of Depth Testing To Do

	if (h == 0) {
	    h = 1;
	}
    }

    public void pause()
    {
	// TODO Auto-generated method stub

    }

    public void resume()
    {
	// TODO Auto-generated method stub

    }

    public void update(GL10 gl)
    {
	// TODO Auto-generated method stub

    }

    public void draw(GL10 gl)
    {
	// clear to back and clear the depth buffer!
	//gl.glClearColor(0, 0, 0, 1);
	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);	

	gl.glViewport(0,0,Engine.getInstance().getViewWidth(), Engine.getInstance().getViewHeight());
	
	// render 3D stuff - if any
	// ... matrix push pops etc
	
	// render 2D stuff in a complex matrix saving manner
	gl.glMatrixMode(GL10.GL_MODELVIEW);
	gl.glPushMatrix();	
        	gl.glLoadIdentity();
        	
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glPushMatrix();
			gl.glLoadIdentity();
			gl.glOrthof(0, Engine.getInstance().getViewWidth(), Engine.getInstance().getViewHeight(), 0, -1.0f, 1.0f);
			
			gl.glMatrixMode(GL10.GL_MODELVIEW);
	        	
	        	gl.glTranslatef(0, 0, 0);
	        	gl.glEnable(GL10.GL_TEXTURE_2D);
	        	gl.glBindTexture(GL10.GL_TEXTURE_2D, _background.getTexture());
	        	_quad.draw(gl);

	        	_guiManager.draw(gl);

	        	gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glPopMatrix();
		
	gl.glMatrixMode(GL10.GL_MODELVIEW);
	gl.glPopMatrix();		
    }
}
