package ca.sandstorm.luminance;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.gamelogic.GameState;
import ca.sandstorm.luminance.gamelogic.MenuState;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;


/**
 * ANDROID MAIN CLASS
 * - The activity for our App (essentially the entry point)
 * - Everything is passed into the Engine.  This is essentially
 * a bridge between Android and our Engine.
 * @author halsafar
 *
 */
public class Luminance extends Activity
{
    private static final Logger logger = LoggerFactory.getLogger("Luminance");

    // openGL view
    private GLSurfaceView mGLView;

    // a handler for updating the ui thread from the android thread
    private final Handler handler = new Handler()
    {
	public void handleMessage(Message msg)
	{
	    float fps = msg.getData().getFloat("fps");

	    Luminance.this.setTitle("Luminance - " + fps);
	}
    };


    /**
     * Android Activity OnCreate()
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	logger.debug("onCreate()");
	
	// Assign the engine's application context
	Engine.getInstance().setContext(getApplicationContext());
	
	// init the engine and add our states
	Engine.getInstance().pushState(new GameState());
	
	// UNCOMMENT TO START IN MENU STATE
	//Engine.getInstance().pushState(new MenuState());
		
	// init gl surface view for android
	super.onCreate(savedInstanceState);
	mGLView = new GLSurfaceView(this);
	mGLView.setRenderer(new ClearRenderer());
	mGLView.setGLWrapper(new GLSurfaceView.GLWrapper()
        {
            @Override
            public GL wrap(GL gl) { return new MatrixTrackingGL(gl); }
        });
	
	setContentView(mGLView);
    }


    /**
     * Android Activity has been paused.
     */
    @Override
    protected void onPause()
    {
	logger.debug("onPause()");

	super.onPause();
	mGLView.onPause();

	Engine.getInstance().pause();
    }


    /**
     * Android activity has been resumed.
     */
    @Override
    protected void onResume()
    {
	logger.debug("onResume()");

	super.onResume();
	mGLView.onResume();

	Engine.getInstance().resume();
    }


    /**
     * Android received a touch even.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
	logger.debug("onTouchEvent(" + event + ")");

	Engine.getInstance().getTouchFilter().updateTouch(event);
	
	try {Thread.sleep(16);} catch (Exception e) { logger.error("Could not sleep event thread"); }

	return true;
    }


    /**
     * Android received a keydown event.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
	logger.debug("onKeyDown(" + keyCode + ", " + event + ")");

	boolean result = false;

	Engine.getInstance().getInputSystem().keyDown(keyCode);

	return result;
    }


    /**
     * Android received a keyup event.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
	logger.debug("onKeyUp(" + keyCode + ", " + event + ")");

	boolean result = false;

	Engine.getInstance().getInputSystem().keyUp(keyCode);

	return result;
    }


    /**
     * Inline class to handle opengl rendering.
     * Passes off all work to the Engine singleton.
     * @author halsafar
     *
     */
    class ClearRenderer implements GLSurfaceView.Renderer
    {
	
	/**
	 * OpenGL surface first created.
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
	    logger.debug("onSurfaceCreated(" + gl.toString() + ", " +
			 config.toString() + ")");

	    
	    Engine.getInstance().init(gl);
	}


	/**
	 * OpenGL surface has changed.
	 */
	public void onSurfaceChanged(GL10 gl, int w, int h)
	{
	    logger.debug("onSurfaceChanged(" + gl.toString() + ", " + w + ", " +
			 h + ")");

	    DisplayMetrics dm = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(dm);

	    int defaultWidth = 480;
	    int defaultHeight = 320;
	    if (dm.widthPixels != defaultWidth) {
		float ratio = ((float) dm.widthPixels) / dm.heightPixels;
		defaultWidth = (int) (defaultHeight * ratio);
	    }
	    
	    Engine.getInstance().deviceChanged(gl, w, h, dm.widthPixels,
	                                       dm.heightPixels);
	    logger.debug("width:" + defaultWidth + "\theight: " + defaultHeight);
	}


	/**
	 * OpenGL Surface has finished swapping.
	 * Ready for a new frame.
	 */
	public void onDrawFrame(GL10 gl)
	{
	    // do not log...
	    Message msg = new Message();
	    Bundle b = new Bundle();
	    b.putFloat("fps", Engine.getInstance().getTimer().getFrameDelta());
	    msg.setData(b);
	    handler.sendMessage(msg);

	    // update/draw engine
	    Engine.getInstance().update(gl);
	    Engine.getInstance().draw(gl);
	}
    }
}
