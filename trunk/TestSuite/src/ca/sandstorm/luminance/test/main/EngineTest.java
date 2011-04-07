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

    Engine engine;

    int w = 60; //arbitrary value
    int h = 60;
    int viewHeight = 100;
    int viewWidth = 100;
    
    int heightMenuBar;
    
    IGameObject light;
    GameState gameState;
    /**
     * @param name
     */
    public EngineTest()
    {
    	super();

    }
	

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
    	engine = Engine.getInstance();
		light = new Light(1, 1, 1, 1, 1, 1, 1, 1);
		gameState = new GameState(1);
    	super.setUp();
    }


    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception
    {
	super.tearDown();
    }
    
    public void testEngine(){
		assertTrue(light != null);
		assertTrue(gameState != null);    	
    }

	public void testAddObject() {
		//added light into gameState
		//gameState.addObject(light);
	}

    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#getInstance()}.
     */
    public void testGetInstance()
    {
    	assertNotNull(engine);
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#setContext(android.content.Context)}.
     */
    public void testSetContext()
    {
    	//Cant create context private
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#getContext()}.
     */
    public void testGetContext()
    {

    	//see set context

    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#getRenderer()}.
     */
    public void testGetRenderer()
    {
    	assertNotNull(engine.getRenderer());
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#getTimer()}.
     */
    public void testGetTimer()
    {
    	assertNotNull(engine.getTimer());
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#getInputSystem()}.
     */
    public void testGetInputSystem()
    {
    	assertNotNull(engine.getInputSystem());
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#getResourceManager()}.
     */
    public void testGetResourceManager()
    {
    	assertNotNull(engine.getResourceManager());
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#setMenuBarHeight(int)}.
     */
    public void testSetGetMenuBarHeight()
    {
		heightMenuBar = 50;
		engine.setMenuBarHeight(50);
		assertEquals(engine.getMenuBarHeight(), heightMenuBar);
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#setTitleBarHeight(int)}.
     */
    public void testSetGetTitleBarHeight()
    {
		int titleBarHeight = 60;
		engine.setTitleBarHeight(titleBarHeight);
		assertEquals(engine.getTitleBarHeight(), titleBarHeight);
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
		assertEquals(0, engine.getViewWidth());
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#getViewHeight()}.
     */
    public void testGetViewHeight()
    {
		//Might not work since deviceChanged needs to be 
		//called first
    	assertEquals(0, engine.getViewHeight());
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#getViewScaleX()}.
     */
    public void testGetViewScaleX()
    {
		//Might not work since deviceChanged needs to be 
		//called first
		assertEquals(0, engine.getViewScaleX(), .001);
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#getViewScaleY()}.
     */
    public void testGetViewScaleY()
    {
		//Might not work since deviceChanged needs to be 
		//called first
		//float viewScaleY = viewHeight / h;
    	assertEquals(0, engine.getViewScaleY(), .001);
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#setMultiTouchFilter(boolean)}.
     */
    public void testSetGetMultiTouchFilter()
    {
		//MultiTouchFilter
		engine.setMultiTouchFilter(true);
		assertNotNull(engine.getTouchFilter());
		
		//SingleTouchFilter
		engine.setMultiTouchFilter(false);
		assertNotNull(engine.getTouchFilter());
	
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


	//engine.resume();

    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#pause()}.
     */
    public void testPause()
    {
	//engine.pause();
    }





    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#init(javax.microedition.khronos.opengles.GL10)}.
     */
    public void testInit()
    {
    	//engine.init(mockGl);
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#update(javax.microedition.khronos.opengles.GL10)}.
     */
    public void testUpdate()
    {
	//engine.update(mockGl);
    }


    /**
     * Test method for {@link ca.sandstorm.luminance.Engine#draw(javax.microedition.khronos.opengles.GL10)}.
     */
    public void testDraw()
    {
	//engine.draw(mockGl);
    }

}
