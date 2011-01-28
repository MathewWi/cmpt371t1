package ca.sandstorm.luminance;

import java.util.Stack;

import javax.microedition.khronos.opengles.GL10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.gamelogic.GameState;
import ca.sandstorm.luminance.state.IState;


public class Engine
{
    private static final Logger logger = LoggerFactory.getLogger(Engine.class);
    
    private static Engine _instance = null;

    private Stack<IState> _stateStack;


    private Engine()
    {
	logger.debug("Engine()");
	logger.debug("Zenja was here (testing)");
	
	_stateStack = new Stack<IState>();
    }


    public static Engine getInstance()
    {
	if (_instance == null) {
	    _instance = new Engine();
	}

	return _instance;
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
	logger.debug("deviceChanged(" + gl.toString() + ", " + w + ", " + h + ")");
	
	for (IState s : _stateStack) {
	    s.deviceChanged(gl, w, h);
	}
    }


    public void update(GL10 gl)
    {
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
