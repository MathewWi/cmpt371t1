package ca.sandstorm.luminance.gamelogic;

import java.io.IOException;

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
import ca.sandstorm.luminance.gui.NumericLabel;
import ca.sandstorm.luminance.input.InputTouchScreen;
import ca.sandstorm.luminance.resources.TextureResource;
import ca.sandstorm.luminance.state.IState;

public class LevelMenuState implements IState
{
    private static final Logger logger = LoggerFactory.getLogger(LevelMenuState.class);
    private boolean _initialized = false;
    private GUIManager _guiManager;
    private boolean _screenIsTapped;
    
    private float screenWidth;
    private float screenHeight;
    
    private TextureResource _background;
    private PrimitiveQuad _quad;
    
    private LevelList _levels;
    
    private MotionEvent _lastTouchEvent;
    
    public LevelMenuState()
    {
	_guiManager = new GUIManager(false);
	_levels = new LevelList("BasicPack.lst");
	_screenIsTapped = false;
    }

    public void init(GL10 gl)
    {
	screenWidth = Engine.getInstance().getViewWidth();
	screenHeight = Engine.getInstance().getViewHeight();
	_quad = new PrimitiveQuad(
	        new Vector3f(0, 0, 0),
		new Vector3f(screenWidth, screenHeight, 0)
	);
	
	_guiManager.initialize(gl);

	try {
	    _background = Engine.getInstance().getResourceManager().loadTexture(gl,
	                                                                        "textures/menuBackground.png");
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	float xPosition = screenWidth/3;
	float yPosition = 0.075f*screenHeight;
	
	logger.debug(String.valueOf(_levels.getNumberOfLevels()));
	
	for (int i = 0; i < _levels.getNumberOfLevels(); i++) {
	    _setupLevelButton(xPosition*(i % 3), yPosition, i);
	    
	    if (i % 3 == 2) {
		yPosition += screenHeight/5;
	    }
	}
		
	_loadTextures(gl);
    }
    
    /**
     * Load the textures into the widgets in the gui manager.
     * 
     * @param gl The GL10 instance.
     */
    private void _loadTextures(GL10 gl)
    {
	for (IWidget widget : _guiManager.getWidgets()) {
	    if (widget != null) {
		_loadTexturesForWidget(gl, widget);
	    }
	}
    }
    
    /**
     * Load the textures for a given widget.
     * 
     * @param gl The GL10 instance.
     * @param widget The widget whose textures we need to load.
     */
    private void _loadTexturesForWidget(GL10 gl, IWidget widget)
    {
	try {
	    if (widget instanceof Label) {
		String textureLocation = widget.getTextureResourceLocation();
		TextureResource texture = Engine.getInstance().getResourceManager().loadTexture(gl, 
		                                                                                textureLocation);
		widget.setTexture(texture);
	    }
	    
	    if (widget.getClass() == Button.class && ((Button)widget).getTappedTextureLocation() != null) {
		String tappedTextureLocation = ((Button)widget).getTappedTextureLocation();
		TextureResource tappedTexture = Engine.getInstance().getResourceManager().loadTexture(gl,
		                                                                                      tappedTextureLocation);
		((Button)widget).setTappedTexture(tappedTexture);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * Create and set up a label and a button for each level in the game.
     * Position them on the screen in a grid with 3 buttons per row.
     * 
     * @param xPosition The x coordinate for the button.
     * @param yPosition The y coordinate for the button.
     * @param buttonNumber The number representing the level number that the
     * button should display.
     */
    private void _setupLevelButton(float xPosition, float yPosition, int buttonNumber)
    {
	    String levelNumberString = String.valueOf(buttonNumber);
	    
	    Button button = new Button(xPosition,
	                               yPosition,
	                               screenWidth*0.14f,
	                               screenHeight*0.12f,
	                               levelNumberString);
	    button.setTextureResourceLocation("textures/levelBox.png");
	    button.setTappedTextureLocation("textures/levelBoxClicked.png");
	    button.setCalleeAndMethodWithParameter(this, "goToLevel");
		
	    NumericLabel label = new NumericLabel(xPosition,
	                                          yPosition,
	                                          screenWidth*0.14f,
		                                  screenHeight*0.12f,
		                                  buttonNumber);
	    
	    _guiManager.addButton(button);
	    _guiManager.addButton(label);
    }
    
    /**
     * Move all the buttons and labels on the screen up or down by a given
     * value (xDelta).
     * 
     * @param xPosition The change in the y axis.
     */
    private void _moveAllButtonsAndLabelsVertically(float yDelta)
    {
	int numberOfWidgets = _guiManager.getNumberOfWidgets();
	IWidget widgets[] = _guiManager.getWidgets();
	for (int i = 0; i < numberOfWidgets; i++) {
	    IWidget widget = widgets[i];
	    if (widget == null) {
		logger.debug("THIS WIDGET IS NULL!");
	    }
    	    widget.setY(widget.getY() + yDelta);
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
    
    /**
     * Go to a specific level, as specified by button's identifier attribute.
     * 
     * @param button The button whose identifier will determine the level we
     * load.
     */
    public void goToLevel(Button button)
    {
	logger.debug("goToLevel(" + button.getIdentifier() + ")");
	int level = new Integer(button.getIdentifier());
	Engine.getInstance().popState();
	Engine.getInstance().pushState(new GameState(level));
    }

    public void update(GL10 gl)
    {
	MotionEvent touchEvent = Engine.getInstance().getInputSystem().getTouchScreen().getTouchEvent();
	if (touchEvent != null) {
	    int touchMode = Engine.getInstance().getInputSystem().getTouchScreen().getTouchMode();
	    // When the user taps on the screen
	    if (touchMode == InputTouchScreen.ON_DOWN) {
		Button eventWidget = _guiManager.touchOccured(touchEvent);
		if (eventWidget != null) {
		    eventWidget.setIsTapped(true);
		    _screenIsTapped = true;
		}
		
		Engine.getInstance().getInputSystem().getTouchScreen().setTouchMode(InputTouchScreen.NONE);
		_lastTouchEvent = touchEvent;
	    } 
	    
//	    if (touchMode == InputTouchScreen.ON_DOWN) {
//		logger.debug("moved");
//		float yDelta = touchEvent.getY() - _lastTouchEvent.getY();
//		_moveAllButtonsAndLabelsVertically(yDelta);
//		
//		Engine.getInstance().getInputSystem().getTouchScreen().setTouchMode(InputTouchScreen.NONE);
//	    }
	    
	    // When the user lets go of the screen
	    if (touchEvent.getAction() == MotionEvent.ACTION_UP && _screenIsTapped) {
		logger.debug("Button was let go");
		_guiManager.letGoOfButton();
		_screenIsTapped = false;
	    }
	}
	_lastTouchEvent = touchEvent;
    }

    public void draw(GL10 gl)
    {
	// clear to back and clear the depth buffer!
	// gl.glClearColor(0, 0, 0, 1);
	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

	gl.glViewport(0, 0, Engine.getInstance().getViewWidth(), Engine
		.getInstance().getViewHeight());

	// render 3D stuff - if any
	// ... matrix push pops etc

	// render 2D stuff in a complex matrix saving manner
	gl.glMatrixMode(GL10.GL_MODELVIEW);
	gl.glPushMatrix();
	gl.glLoadIdentity();

	gl.glMatrixMode(GL10.GL_PROJECTION);
	gl.glPushMatrix();
	gl.glLoadIdentity();
	gl.glOrthof(0, Engine.getInstance().getViewWidth(), Engine
		.getInstance().getViewHeight(), 0, -1.0f, 1.0f);

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
