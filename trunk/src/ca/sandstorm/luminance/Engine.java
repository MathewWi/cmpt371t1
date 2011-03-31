package ca.sandstorm.luminance;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.microedition.khronos.opengles.GL10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;

import ca.sandstorm.luminance.audio.AndroidSoundPlayer;
import ca.sandstorm.luminance.gamelogic.GameRenderer;
import ca.sandstorm.luminance.input.InputSystem;
import ca.sandstorm.luminance.input.MultiTouchFilter;
import ca.sandstorm.luminance.input.SingleTouchFilter;
import ca.sandstorm.luminance.input.TouchFilter;
import ca.sandstorm.luminance.resources.MusicResource;
import ca.sandstorm.luminance.resources.ResourceManager;
import ca.sandstorm.luminance.resources.TextResource;
import ca.sandstorm.luminance.state.IState;
import ca.sandstorm.luminance.time.TimeSystem;


/**
 * Application runtime controller class
 * 
 * @author halsafar - shinhalsafar@gmail.com
 * 
 */
public class Engine
{
    private static final Logger logger = LoggerFactory.getLogger(Engine.class);

    // constants for debugging
    public static final boolean DEBUG = false;

    // singleton instance
    private static Engine _instance = null;
    private boolean _initialized = false;

    // android context
    private Context _context = null;

    // state stack for updating/rendering
    private Stack<IState> _stateStack;
    private Queue<IState> _stateQueue;

    // stored screen vars
    private int _width;
    private int _height;
    private float _scaleX;
    private float _scaleY;

    // subsystem instances
    private TimeSystem _timer;
    private InputSystem _inputSystem;
    private AndroidSoundPlayer _audioSystem;
    private ResourceManager _resourceManager;
    private GameRenderer _renderer;
    private TouchFilter _touchFilter;
    
    // is audio enabled (user choice here)
    private boolean _audioEnabled;

    // last update step, used to calculate frame time delta
    private long _lastTime;

    // store menu bar height for offsetting input coordinates
    private int _menuBarHeight;
    private int _titleBarHeight;


    /**
     * Constructor.
     * 
     * Initializes all engine content and sub systems.
     */
    private Engine()
    {
	logger.debug("Engine()");
	_renderer = new GameRenderer();

	_stateStack = new Stack<IState>();
	_stateQueue = new LinkedList<IState>();
	_timer = new TimeSystem();
	_resourceManager = new ResourceManager();
	_audioSystem = new AndroidSoundPlayer();
	_inputSystem = new InputSystem();
	_touchFilter = new MultiTouchFilter();

	_lastTime = SystemClock.uptimeMillis();

	_menuBarHeight = 0;
	_titleBarHeight = 0;
	
	_audioEnabled = true;
    }


    /**
     * Singleton method.
     * 
     * @return instance to the Engine
     */
    public static Engine getInstance()
    {
	if (_instance == null) {
	    _instance = new Engine();
	}

	return _instance;
    }


    /**
     * Sets the engine's Android application context.
     * 
     * @param context
     *            Application context
     * @author zenja
     */
    public void setContext(Context context)
    {
	_context = context;
	_resourceManager.setAssets(_context.getAssets());

	// listDirectoryFiles("/");
    }


    /**
     * Get application context.
     * 
     * @return Application context
     * @author zenja
     */
    public Context getContext()
    {
	if (_context == null)
	    throw new RuntimeException(
		    "Attempting to access Engine's application context but it hasn't been assigned!");
	return _context;
    }


    /**
     * Returns the renderer used for this game.
     * 
     * @return
     */
    public GameRenderer getRenderer()
    {
	return _renderer;
    }


    /**
     * Returns the audio playback subsystem.
     * 
     * @return Audio system
     */
    public AndroidSoundPlayer getAudio()
    {
	return _audioSystem;
    }


    /**
     * Returns the timer system.
     * 
     * @return
     */
    public TimeSystem getTimer()
    {
	return _timer;
    }


    /**
     * Returns the input system.
     * 
     * @return
     */
    public InputSystem getInputSystem()
    {
	return _inputSystem;
    }


    /**
     * Returns the resource manager.
     * 
     * @return
     */
    public ResourceManager getResourceManager()
    {
	return _resourceManager;
    }


    /**
     * Set the menu bar height the engine will use to offset input coordinates.
     * 
     * @param h
     *            The height value in pixels of the menu bar
     */
    public void setMenuBarHeight(int h)
    {
	_menuBarHeight = h;
    }


    /**
     * Set the title bar height the engine will use to offset input coordinates.
     * 
     * @param h
     *            The height value in pixels of the menu bar
     */
    public void setTitleBarHeight(int h)
    {
	_titleBarHeight = h;
    }


    /**
     * Get the menu bar height.
     * 
     * @return the height in pixels.
     */
    public int getMenuBarHeight()
    {
	return _menuBarHeight;
    }


    /**
     * Get the title bar height.
     * 
     * @return the height in pixels.
     */
    public int getTitleBarHeight()
    {
	return _titleBarHeight;
    }


    /**
     * Set the view width of the screen view, where gui will use to calculate
     * the position.
     * 
     * @param w
     *            The w value in pixels of the screen
     */
    public void setViewWidth(int w)
    {
	_width = w;
    }


    /**
     * Set the view height of the screen view, where gui will use to calculate
     * the position.
     * 
     * @param h
     *            The h value in pixels of the screen
     */
    public void setViewHeight(int h)
    {
	_height = h;
    }


    /**
     * Get the view port width in pixels.
     * 
     * @return
     */
    public int getViewWidth()
    {
	return _width;
    }


    /**
     * Get the view port height in pixels.
     * 
     * @return
     */
    public int getViewHeight()
    {
	return _height;
    }


    /**
     * Get the view port X scaling. This is the x:y ratio.
     * 
     * @return
     */
    public float getViewScaleX()
    {
	return _scaleX;
    }


    /**
     * Get the view port Y scaling. This is the x:y ratio.
     * 
     * @return
     */
    public float getViewScaleY()
    {
	return _scaleY;
    }


    /**
     * Sets the filter to MultiTouch. The default is to use SingleTouch.
     * 
     * @param b
     *            true to set multitouch filter
     */
    public void setMultiTouchFilter(boolean b)
    {
	if (b) {
	    _touchFilter = new MultiTouchFilter();
	} else {
	    _touchFilter = new SingleTouchFilter();
	}
    }


    /**
     * Returns the current touch filter instance.
     * 
     * @return
     */
    public TouchFilter getTouchFilter()
    {
	return _touchFilter;
    }
    
    
    public boolean getAudioEnabled()
    {
	return _audioEnabled;	
    }
    
    
    public void setAudioEnabled(boolean b)
    {
	_audioEnabled = b;
	
	if (_audioEnabled)
	{

	}
	else
	{

	}
    }


    /**
     * Push a state onto the engine state stack.
     * 
     * @param state
     *            - An instance of a state for the engine to update.
     */
    public void pushState(IState state)
    {
	logger.debug("pushState(" + state + ")");

	// queue up state for adding on next update cycle
	_stateQueue.add(state);

	// _stateStack.push(state);
    }


    /**
     * Pops a state off the state stack.
     * 
     * @return the state popped off
     */
    public IState popState()
    {
	logger.debug("popState()");

	return _stateStack.pop();
    }


    public IState getCurrentState()
    {
	return _stateStack.peek();
    }


    /**
     * Informs all states that the rendering has resumed.
     */
    public void resume()
    {
	logger.debug("resume()");

	_lastTime = SystemClock.uptimeMillis();

	for (IState s : _stateStack) {
	    s.resume();
	}

	// Resume all sounds
	if (_audioSystem != null) {
	    _audioSystem.resumeAll();
	}
    }


    /**
     * Inform all states that the rendering has paused.
     */
    public void pause()
    {
	logger.debug("pause()");

	for (IState s : _stateStack) {
	    s.pause();
	}

	// Pause all sounds
	_audioSystem.pauseAll();
    }


    /**
     * The device information has changed. This function is called by the
     * graphics API. In most cases the Android events let us know or the JOGL
     * API.
     * 
     * @param gl
     *            Instance of GL10, not valid after the function ends.
     * @param w
     *            New width of the device
     * @param h
     *            New height of the device
     * @param viewWidth
     * @param viewHeight
     */
    public void deviceChanged(GL10 gl, int w, int h, int viewWidth,
	    int viewHeight)
    {
	logger.debug("deviceChanged(" + gl.toString() + ", " + w + ", " + h +
		     ")");

	_width = w;
	_height = h;

	_scaleX = (float) viewWidth / _width;
	_scaleY = (float) viewHeight / _height;

	// we should check both states no stack and queue
	for (IState s : _stateStack) {
	    s.deviceChanged(gl, _width, _height);
	}

	for (IState s : _stateQueue) {
	    s.deviceChanged(gl, _width, _height);
	}
    }


    /**
     * Init code path. Since the GL10 instance is only valid at certains times
     * this code path is used to allow states, objects, etc to init anything
     * they require an OpenGL context for.
     * 
     * @param gl
     *            OpenGL context, local scope.
     */
    public void init(GL10 gl)
    {
	Luminance.getInstance().showLoadingBar();
	
	// Start music playback
	try {
	    MusicResource music = _resourceManager
		    .loadMusic("sounds/music1.mp3");
	    _audioSystem.playMusic(music);
	} catch (IOException e) {
	    logger.error("Failed to load music file: " + e.getMessage());
	}
	
	for (IState s : _stateStack) {
	    if (s.isActive()) {
		s.init(gl);
	    }
	}
	
	Luminance.getInstance().dismissLoadingBar();
	_initialized = true;
    }


    /**
     * Check if the engine has been initialized.
     */
    public boolean isInitialized()
    {
	return _initialized;
    }


    /**
     * Update code path. Used to process the update step of the game. This would
     * include physics for example.
     * 
     * @param gl
     *            OpenGL context, local scope.
     */
    public void update(GL10 gl)
    {
	// flush the state queue
	// init any states on the queue and add them for update/drawing
	while (!_stateQueue.isEmpty()) {
	    IState state = _stateQueue.remove();
	    logger.debug("adding state: " + state);

	    Luminance.getInstance().showLoadingBar();

	    state.init(gl);

	    Luminance.getInstance().dismissLoadingBar();
	    
	    _stateStack.add(state);
	}

	// calculate time step
	long time = SystemClock.uptimeMillis();
	// long timeDelta = time - _lastTime;
	float secondsDelta = (time - _lastTime) * 0.001f;

	// update timer, store time step
	_timer.update(secondsDelta);
	_lastTime = time;

	// update all the active states
	for (int i = 0; i < _stateStack.size(); i++) {
	    IState s = _stateStack.get(i);
	    if (s.isActive()) {
		s.update(gl);
	    }
	}
    }


    /**
     * Drawing code path. Used to draw all visible on screen objects.
     * 
     * @param gl
     *            OpenGL context, local scope.
     */
    public void draw(GL10 gl)
    {
	// NOTE: do not flush the state queue here... only update() does that

	for (int i = 0; i < _stateStack.size(); i++) {
	    IState s = _stateStack.get(i);
	    if (s.isVisible()) {
		s.draw(gl);
	    }
	}
    }


    /**
     * List all the files in a given directory.
     * 
     * @param path
     *            Directory path
     * @author zenja
     */
    @SuppressWarnings("unused")
    private void listDirectoryFiles(String path)
    {
	logger.info("File listing for '" + path + "':");
	try {
	    String[] files;
	    files = _resourceManager.getFileListing(path);
	    for (String s : files)
		logger.info("- " + s);
	} catch (IOException e1) {
	    logger.error("Failed to retrieve file listing!");
	    e1.printStackTrace();
	}
    }


    /**
     * Print contents of a text file through the debugger
     * 
     * @param path
     *            Path to text file in assets
     * @author zenja
     */
    @SuppressWarnings("unused")
    private void printFileContents(String path)
    {
	try {
	    logger.info("Contents of '" + path + "'");
	    TextResource res = _resourceManager.loadText(path);
	    if (res != null)
		logger.info(res.getText());
	} catch (IOException e) {
	    logger.error("Failed to load file '" + path + "' for printing!");
	    e.printStackTrace();
	}
    }
}
