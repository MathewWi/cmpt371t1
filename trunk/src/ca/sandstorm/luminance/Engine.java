package ca.sandstorm.luminance;

import java.util.Stack;

import javax.microedition.khronos.opengles.GL10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.SystemClock;

import ca.sandstorm.luminance.gamelogic.GameState;
import ca.sandstorm.luminance.input.InputSystem;
import ca.sandstorm.luminance.state.IState;
import ca.sandstorm.luminance.time.TimeSystem;


public class Engine
{
    private static final Logger logger = LoggerFactory.getLogger(Engine.class);

    private static Engine _instance = null;

    private TimeSystem _timer;
    private long _lastTime;
    private Stack<IState> _stateStack;

    private int _width;
    private int _height;
    private float _scaleX;
    private float _scaleY;
    
    private InputSystem _inputSystem;

    private Engine()
    {
	logger.debug("Engine()");

	_stateStack = new Stack<IState>();
	_timer = new TimeSystem();
    }


    public static Engine getInstance()
    {
	if (_instance == null) {
	    _instance = new Engine();
	}

	return _instance;
    }


    public TimeSystem getTimer()
    {
	return _timer;
    }
    
    
    public InputSystem getInputSystem()
    {
	return _inputSystem;
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
    }


    public void pause()
    {
	logger.debug("pause()");

	for (IState s : _stateStack) {
	    s.pause();
	}
    }


    public void deviceChanged(GL10 gl, int w, int h)
    {
	logger.debug("deviceChanged(" + gl.toString() + ", " + w + ", " + h +
		     ")");

	_width = w;
	_height = h;
	
	//_scaleX = (float)viewWidth / gameWidth;
        //_scaleY = (float)viewHeight / gameHeight;	
	
	for (IState s : _stateStack) {
	    s.deviceChanged(gl, _width, _height);
	}
    }


    public void update(GL10 gl)
    {
	long time = SystemClock.uptimeMillis();
	long timeDelta = time - _lastTime;

	_timer.update(timeDelta);

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
}
