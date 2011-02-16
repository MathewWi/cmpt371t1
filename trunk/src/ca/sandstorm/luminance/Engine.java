package ca.sandstorm.luminance;

import java.io.IOException;
import java.util.Stack;

import javax.microedition.khronos.opengles.GL10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;
import android.os.SystemClock;

import ca.sandstorm.luminance.audio.AndroidSoundPlayer;
import ca.sandstorm.luminance.graphics.GLRenderer;
import ca.sandstorm.luminance.input.InputSystem;
import ca.sandstorm.luminance.input.MultiTouchFilter;
import ca.sandstorm.luminance.input.SingleTouchFilter;
import ca.sandstorm.luminance.input.TouchFilter;
import ca.sandstorm.luminance.resources.ResourceManager;
import ca.sandstorm.luminance.resources.TextResource;
import ca.sandstorm.luminance.state.IState;
import ca.sandstorm.luminance.time.TimeSystem;


public class Engine
{
    private static final Logger logger = LoggerFactory
	    .getLogger("Luminance.Engine");

    private static Engine _instance = null;
    private Context _context = null;

    private Stack<IState> _stateStack;

    private int _width;
    private int _height;
    private float _scaleX;
    private float _scaleY;

    private TimeSystem _timer;
    private long _lastTime;
    private InputSystem _inputSystem;
    private AndroidSoundPlayer _audioSystem;
    private ResourceManager _resourceManager;
    private GLRenderer _renderer;

    private TouchFilter _touchFilter;


    private Engine()
    {
	logger.debug("Engine()");
	_renderer = new GLRenderer();

	_stateStack = new Stack<IState>();
	_timer = new TimeSystem();
	_resourceManager = new ResourceManager();
	_audioSystem = new AndroidSoundPlayer();
	_inputSystem = new InputSystem();
	_touchFilter = new MultiTouchFilter();

	_lastTime = SystemClock.uptimeMillis();

	// TEMP: play a sound to test sound system
	// _audioSystem.load("test.mp3");
	// _audioSystem.play("test.mp3", 0.9f);
    }


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

	listDirectoryFiles("");

	// TEMP: test reading from text file
	printFileContents("text/text.txt");
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

    
    public GLRenderer getRenderer()
    {
	return _renderer;
    }
    

    public TimeSystem getTimer()
    {
	return _timer;
    }


    public InputSystem getInputSystem()
    {
	return _inputSystem;
    }


    public ResourceManager getResourceManager()
    {
	return _resourceManager;
    }


    public int getViewWidth()
    {
	return _width;
    }


    public int getViewHeight()
    {
	return _height;
    }


    public float getViewScaleX()
    {
	return _scaleX;
    }


    public float getViewScaleY()
    {
	return _scaleY;
    }


    public void setMultiTouchFilter(boolean b)
    {
	if (b) {
	    _touchFilter = new MultiTouchFilter();
	} else {
	    _touchFilter = new SingleTouchFilter();
	}
    }


    public TouchFilter getTouchFilter()
    {
	return _touchFilter;
    }


    public void pushState(IState state)
    {
	logger.debug("pushState(" + state + ")");

	_stateStack.push(state);
    }


    public void popState()
    {
	logger.debug("popState()");

	_stateStack.pop();
    }


    public void resume()
    {
	logger.debug("resume()");

	_lastTime = SystemClock.uptimeMillis();

	for (IState s : _stateStack) {
	    s.resume();
	}

	// Resume all sounds
	_audioSystem.resumeAll();
    }


    public void pause()
    {
	logger.debug("pause()");

	for (IState s : _stateStack) {
	    s.pause();
	}

	// Pause all sounds
	_audioSystem.pauseAll();
    }


    public void deviceChanged(GL10 gl, int w, int h, int viewWidth, int viewHeight)
    {
	logger.debug("deviceChanged(" + gl.toString() + ", " + w + ", " + h +
		     ")");

	_width = w;
	_height = h;

	 _scaleX = (float)viewWidth / _width;
	 _scaleY = (float)viewHeight / _height;

	for (IState s : _stateStack) {
	    s.deviceChanged(gl, _width, _height);
	}
    }


    public void update(GL10 gl)
    {
	long time = SystemClock.uptimeMillis();
	long timeDelta = time - _lastTime;
	float secondsDelta = (time - _lastTime) * 0.001f;
	_timer.update(secondsDelta);
	_lastTime = time;

	for (IState s : _stateStack) {
	    if (s.isActive()) {
		s.update(gl);
	    }
	}
    }


    public void draw(GL10 gl)
    {
	for (IState s : _stateStack) {
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
    private void printFileContents(String path)
    {
	try {
	    logger.info("Contents of '" + path + "'");
	    TextResource res = _resourceManager.loadTextResource(path);
	    if (res != null)
		logger.info(res.getText());
	} catch (IOException e) {
	    logger.error("Failed to load file '" + path + "' for printing!");
	    e.printStackTrace();
	}
    }
}
