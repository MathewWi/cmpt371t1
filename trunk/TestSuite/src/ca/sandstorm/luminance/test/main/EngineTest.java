/**
 * 
 */
package ca.sandstorm.luminance.test.main;


import android.test.AndroidTestCase;
import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.gamelogic.GameState;
import ca.sandstorm.luminance.gameobject.IGameObject;
import ca.sandstorm.luminance.gameobject.Light;


/**
 * Testing Engine functionality
 * @author Amara Daal
 *
 */
public class EngineTest extends AndroidTestCase
{

    Engine _engine;

    int _w = 60; //arbitrary value
    int _h = 60;
    int _viewHeight = 100;
    int _viewWidth = 100;
    
    int _heightMenuBar;
    
    IGameObject _light;
    GameState _gameState;	

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
    	_engine = Engine.getInstance();
		_light = new Light(1, 1, 1, 1, 1, 1, 1, 1);
		_gameState = new GameState(1);
		//mContext = new Context();
    	super.setUp();
    }
    
    /**
     * @param name
     */
    public EngineTest()
    {
    	super();

    }


    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception
    {
    	super.tearDown();
    }
    
    public void testEngine(){
		assertTrue(_light != null);
		assertTrue(_gameState != null);    	
    }

	public void testAddObject() {
		//added _light into _gameState
		//_gameState.addObject(_light);
	}

    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#getInstance()}.
     */
    public void testGetInstance()
    {
    	assertNotNull(getContext());
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#setContext(android.content.Context)}.
     */
    public void testSetContext()
    {
    	assertTrue(getContext().getApplicationContext() != null);
    	_engine.setContext(getContext().getApplicationContext());
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#getContext()}.
     */
    public void testGetContext()
    {
    	// Can't test it.
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#getRenderer()}.
     */
    public void testGetRenderer()
    {
    	assertTrue(_engine.getRenderer() != null);
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#getTimer()}.
     */
    public void testGetTimer()
    {
    	assertTrue(_engine.getTimer() != null);
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#getInputSystem()}.
     */
    public void testGetInputSystem()
    {
    	assertTrue(_engine.getInputSystem() != null);
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#getResourceManager()}.
     */
    public void testGetResourceManager()
    {
    	assertNotNull(_engine.getResourceManager());
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#setMenuBarHeight(int)}.
     */
    public void testSetGetMenuBarHeight()
    {
		_heightMenuBar = 50;
		_engine.setMenuBarHeight(50);
		assertEquals(_engine.getMenuBarHeight(), _heightMenuBar);
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#setTitleBarHeight(int)}.
     */
    public void testSetGetTitleBarHeight()
    {
		int titleBarHeight = 60;
		_engine.setTitleBarHeight(titleBarHeight);
		assertEquals(_engine.getTitleBarHeight(), titleBarHeight);
    }
    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#deviceChanged(javax.microedition.khronos.opengles.GL10, int, int, int, int)}.
     */
    public void testDeviceChanged()
    {

	//OpenGL

    }
    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#getViewWidth()}.
     */
    public void testGetViewWidth()
    {
		//Might not work since deviceChanged needs to be 
		//called first
		assertEquals(0, _engine.getViewWidth());
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#getViewHeight()}.
     */
    public void testGetViewHeight()
    {
		//Might not work since deviceChanged needs to be 
		//called first
    	assertEquals(0, _engine.getViewHeight());
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#getViewScaleX()}.
     */
    public void testGetViewScaleX()
    {
		//Might not work since deviceChanged needs to be 
		//called first
		assertEquals(0, _engine.getViewScaleX(), .001);
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#getViewScaleY()}.
     */
    public void testGetViewScaleY()
    {
		//Might not work since deviceChanged needs to be 
		//called first
		//float viewScaleY = _viewHeight / _h;
    	assertEquals(0, _engine.getViewScaleY(), .001);
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#setMultiTouchFilter(boolean)}.
     */
    public void testSetGetMultiTouchFilter()
    {
		//MultiTouchFilter
		_engine.setMultiTouchFilter(true);
		assertNotNull(_engine.getTouchFilter());
		
		//SingleTouchFilter
		_engine.setMultiTouchFilter(false);
		assertNotNull(_engine.getTouchFilter());
	
    }



    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#pushState(ca.sandstorm.luminance.state.IState)}.
     */
    public void testPushPopState()
    {

	
    }



    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#resume()}.
     */
    public void testResume()
    {


	//_engine.resume();

    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#pause()}.
     */
    public void testPause()
    {
	//_engine.pause();
    }





    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#init(javax.microedition.khronos.opengles.GL10)}.
     */
    public void testInit()
    {
    	//_engine.init(mockGl);
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#update(javax.microedition.khronos.opengles.GL10)}.
     */
    public void testUpdate()
    {
	//_engine.update(mockGl);
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#draw(javax.microedition.khronos.opengles.GL10)}.
     */
    public void testDraw()
    {
	//_engine.draw(mockGl);
    }

}
