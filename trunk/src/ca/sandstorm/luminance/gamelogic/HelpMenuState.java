package ca.sandstorm.luminance.gamelogic;

import java.io.IOException;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Bundle;
import android.view.MotionEvent;
import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.graphics.PrimitiveQuad;
import ca.sandstorm.luminance.gui.Button;
import ca.sandstorm.luminance.gui.GUIManager;
import ca.sandstorm.luminance.gui.IWidget;
import ca.sandstorm.luminance.gui.Label;
import ca.sandstorm.luminance.input.InputTouchScreen;
import ca.sandstorm.luminance.resources.TextureResource;
import ca.sandstorm.luminance.state.IState;

/**
 * State for handling ingame help menu instruction.
 * @author jonny
 *
 */
public class HelpMenuState implements IState
{
    private static final Logger _logger = LoggerFactory.getLogger(LevelMenuState.class);
    private boolean _initialized = false;

    private TextureResource _background;
    private PrimitiveQuad _quad;
    
    private ArrayList<TextureResource> _instructionTexture;
    private GUIManager _guiManager;
    private boolean _tapped;
    private Label _instructionLabel;

    public HelpMenuState()
    {
	_instructionTexture = new ArrayList<TextureResource>();
	_guiManager = new GUIManager (false);
	_tapped = false;
    }
    
    /**
     * init()
     * Engine has informed this state can init any openGL 
     * required resources.
     * @param gl OpenGL context
     */
    public void init(GL10 gl)
    {
	// Recreate GUI on orientation flip
	if (Engine.getInstance().isInitialized()) {
	    _guiManager = new GUIManager(false);
	}
	
	_guiManager.initialize(gl);
	
	float width = Engine.getInstance().getViewWidth();
	float height = Engine.getInstance().getViewHeight();
	_quad = new PrimitiveQuad(
	        new Vector3f(0, 0, 0),
		new Vector3f(width, height, 0)
	);
	

	
	_instructionLabel = new Label(0.10f * width,
	                              0.05f * height,
	                              0.80f * width,
	                              0.50f * height,
	                              "instructionLabel");
	_instructionLabel.setTextureResourceLocation("textures/goal.png");
	
	Button prevButton = new Button(0.10f * width,
	                               0.65f * height,
	                               0.35f * width,
	                               0.15f * height,
	                               "prev");
	prevButton.setCalleeAndMethod(this, "goPrevInstruction");
	prevButton.setTextureResourceLocation("textures/prev.png");
	prevButton.setTappedTextureLocation("textures/prevClicked.png");
	
	Button nextButton = new Button(0.55f * width,
	                               0.65f * height,
	                               0.35f * width,
	                               0.15f * height,
	                               "next");
	nextButton.setCalleeAndMethod(this, "goNextInstruction");
	nextButton.setTextureResourceLocation("textures/next.png");
	nextButton.setTappedTextureLocation("textures/nextClicked.png");

	
	Button mainMenuButton = new Button(0.30f * width,
	                               0.85f * height,
	                               0.40f * width,
	                               0.15f * height,
	                               "mainMenu");
	mainMenuButton.setCalleeAndMethod(this, "goMainMenu");
	mainMenuButton.setTextureResourceLocation("textures/mainMenu.png");
	mainMenuButton.setTappedTextureLocation("textures/mainMenuClicked.png");
	
	_guiManager.addButton(_instructionLabel);
	_guiManager.addButton(prevButton);
	_guiManager.addButton(nextButton);
	_guiManager.addButton(mainMenuButton);
	
	loadTextures(gl);
	
    }
    
    public void goPrevInstruction()
    {
	int index = _instructionTexture.indexOf(_instructionLabel.getTexture());
	if (index > 0){
	    _instructionLabel.setTexture(_instructionTexture.get(index-1));
	}
	
    }
    
    public void goNextInstruction()
    {
	int index = _instructionTexture.indexOf(_instructionLabel.getTexture());
	if(index < _instructionTexture.size()){
	    _instructionLabel.setTexture(_instructionTexture.get(index+1));
	}
	
    }
    
    public void goMainMenu()
    {
	Engine.getInstance().popState();
	Engine.getInstance().pushState( new MenuState() );
    }
    
    private void loadTextures(GL10 gl)
    {
	try {
	    _background = Engine.getInstance().getResourceManager().loadTexture(gl, "textures/menuBackground.png");
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	loadInstructionTexture(gl, "textures/goal.png");
	loadInstructionTexture(gl, "textures/scroll.png");
	loadInstructionTexture(gl, "textures/pinch.png");
	loadInstructionTexture(gl, "textures/placingMirror.png");
	loadInstructionTexture(gl, "textures/doubleClickMirror.png");
	loadInstructionTexture(gl, "textures/placingPrism.png");
	loadInstructionTexture(gl, "textures/placingEraser.png");
	loadInstructionTexture(gl, "textures/doubleClickEmitter.png");
	
	loadButtonTexture(gl);
	
    }
    
    private void loadInstructionTexture(GL10 gl, String texture)
    {
	try {
	    _instructionTexture.add(Engine.getInstance().getResourceManager().loadTexture(gl, texture));	    
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    private void loadButtonTexture(GL10 gl)
    {
	try {
	    for (IWidget widget : _guiManager.getWidgets()) {
		if (widget != null) {
		    String textureResourceLocation = widget
			    .getTextureResourceLocation();
		    TextureResource texture = Engine.getInstance()
			    .getResourceManager()
			    .loadTexture(gl, textureResourceLocation);
		    widget.setTexture(texture);

		    if (widget instanceof Button &&
			((Button) widget).getTappedTextureLocation() != null) {
			Button button = (Button)widget;
			String tappedTextureLocation = button.getTappedTextureLocation();
			TextureResource tappedTexture = Engine.getInstance().getResourceManager().loadTexture(gl, tappedTextureLocation);
			button.setTappedTexture(tappedTexture);
			
			// If the button is selected initially, set it to tapped texture
			if (button.getIsSelected()) {
			    // This causes the right things to happen internally
			    button.setIsSelected(button.getIsSelected());
			}
		    }
		}
	    }
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
	_logger.debug("deviceChanged(" + gl + ", " + w + ", " + h + ")");
	
	gl.glShadeModel(GL10.GL_SMOOTH); // Enable Smooth Shading
	gl.glClearColor(0.182f, 0.182f, 1, 1); // Error blue
	gl.glClearDepthf(1.0f); // Depth Buffer Setup
	gl.glEnable(GL10.GL_DEPTH_TEST); // Enables Depth Testing
	gl.glDepthFunc(GL10.GL_LEQUAL); // The Type Of Depth Testing To Do

	if (h == 0) {
	    h = 1;
	}
    }

    /**
     * pause() Engine has requested this state be paused. This is different from
     * ingame pausing.
     */
    @Override
    public void pause()
    {
	// TODO Auto-generated method stub
    }

    /**
     * resume() Engine has requested this state to resume.
     */
    @Override
    public void resume()
    {
	// TODO Auto-generated method stub

    }

    /**
     * Engine has requested this state update itself.
     * 
     * @param OpenGL
     *            context
     */
    @Override
    public void update(GL10 gl)
    {
	if (Engine.getInstance().getInputSystem().getTouchScreen()
		.getTouchEvent() != null) {
	    MotionEvent touchEvent = Engine.getInstance().getInputSystem()
		    .getTouchScreen().getTouchEvent();

	    if (Engine.getInstance().getInputSystem().getTouchScreen()
		    .getTouchMode() == InputTouchScreen.ON_DOWN) {

		Button eventWidget = _guiManager.touchOccured(touchEvent);
		if (eventWidget != null) {
		    _logger.debug("button has been tapped");
		    eventWidget.setIsTapped(true);
		    
		    _tapped = true;
		}

		Engine.getInstance().getInputSystem().getTouchScreen()
			.setTouchMode(InputTouchScreen.NONE);
	    }

	    if (touchEvent.getAction() == MotionEvent.ACTION_UP && _tapped) {
		_logger.debug("Menu has been touched");
		if (_guiManager.buttonIsTapped()) {
		    _logger.debug(_guiManager.getTappedButton().getIdentifier());
		}
		_guiManager.letGoOfButton();
		_tapped = false;
	    }

	}

    }


    @Override
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

    @Override
    public void saveInstance(Bundle savedInstanceState)
    {
	// TODO Auto-generated method stub

    }

}
