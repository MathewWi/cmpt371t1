package ca.sandstorm.luminance;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.gamelogic.GameState;
import ca.sandstorm.luminance.gamelogic.LevelMenuState;
import ca.sandstorm.luminance.gamelogic.MenuState;
import ca.sandstorm.luminance.state.IState;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.Window;


/**
 * ANDROID MAIN CLASS - The activity for our App (essentially the entry point) -
 * Everything is passed into the Engine. This is essentially a bridge between
 * Android and our Engine.
 * 
 * @author halsafar
 * 
 */
public class Luminance extends Activity
{
    private static final Logger _logger = LoggerFactory.getLogger("Luminance");

    private static Luminance _instance = null;

    // Used for framerate analysis while stress testing.
    // private float _logFpsCounter = 0f;

    // openGL view
    private GLSurfaceView mGLView;

    private GestureDetector _gesture;

    private String _subTitle = "";

    private ProgressDialog _myDialog;

    // a handler for updating the ui thread from the android thread
    private final Handler handler = new Handler()
    {
	public void handleMessage(Message msg)
	{
	    float fps = msg.getData().getFloat("fps");
	    String newSubtitle = msg.getData().getString("subtitle");
	    if (newSubtitle != null) {
		_subTitle = newSubtitle;
	    }

	    if (Engine.DEBUG) {
		Luminance.this.setTitle("Luminance - " + _subTitle + "(" + 1f /
					fps + " fps)");
	    } else {
		Luminance.this.setTitle("Luminance - " + _subTitle);
	    }

	    if (msg.getData().getBoolean("startLoadGame")) {
		_myDialog = ProgressDialog.show(Luminance.getInstance(), "LUMINANCE",
						" Loading. Please wait ... ",
						true);
	    } else if (msg.getData().getBoolean("finishLoadGame")) {
		_myDialog.dismiss();
	    }

	    // Uncomment below code for logging framerate while doing
	    // performance
	    // or stress tests. Do NOT commit with this uncommented. This way
	    // avoids using an "if" here which would always slow the project
	    // down.
	    // logFpsCounter += fps;
	    // if(logFpsCounter > 1f) {
	    // _logger.debug("Frame length: " + fps);
	    // logFpsCounter -= 1f;
	    // }
	}
    };


    public static Luminance getInstance()
    {
	return _instance;
    }


    public Handler getHandler()
    {
	return handler;
    }


    /**
     * Android Activity OnCreate()
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	_logger.debug("onCreate()");
	_instance = this;
	
	//Engine savedEngine = (Engine)savedInstanceState.getSerializable("Engine");

	// Assign the engine's application context
	Engine.getInstance().setContext(getApplicationContext());

	_gesture = new GestureDetector(this, Engine.getInstance()
		.getInputSystem().getTouchScreen());

	// Push a new menu state unless engine is initialized, meaning one
	// already exists
	if (!Engine.getInstance().isInitialized()) {
	    // init the engine and add our states
	    Engine.getInstance().pushState(new MenuState());
	}

	// init gl surface view for android
	super.onCreate(savedInstanceState);
	mGLView = new GLSurfaceView(this);
	mGLView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
	mGLView.getHolder().setFormat(PixelFormat.RGBA_8888);
	mGLView.setRenderer(new ClearRenderer(this));
	mGLView.setGLWrapper(new GLSurfaceView.GLWrapper()
	{
	    @Override
	    public GL wrap(GL gl)
	    {
		return new MatrixTrackingGL(gl);
	    }
	});

	setContentView(mGLView);
    }


    public boolean onPrepareOptionsMenu(Menu menu)
    {
	// showOrDismissPauseMenu() only exists in GameState, so wen need to
	// check if currentState is an instance of GameState.
	IState currentState = Engine.getInstance().getCurrentState();
	if (currentState instanceof GameState) {
	    ((GameState) currentState).showOrDismissPauseMenu();
	}
	return false;
    }


    /**
     * Set the application subtitle
     * 
     * @param subtitle
     */
    public void setSubTitle(String subtitle)
    {
	Message msg = new Message();
	Bundle b = new Bundle();
	b.putString("subtitle", subtitle);
	msg.setData(b);
	handler.sendMessage(msg);
    }


    public void showLoadingBar()
    {
	Message msg = new Message();
	Bundle b = new Bundle();
	b.putBoolean("startLoadGame", true);
	msg.setData(b);
	Luminance.getInstance().getHandler().sendMessage(msg);
    }


    public void dismissLoadingBar()
    {
	Message msg = new Message();
	Bundle b = new Bundle();
	b.putBoolean("finishLoadGame", true);
	msg.setData(b);
	Luminance.getInstance().getHandler().sendMessage(msg);
    }


    @Override
    public void onBackPressed()
    {
	System.out.println("onBackPressed()");
	IState currentState = Engine.getInstance().getCurrentState();
	if ((currentState instanceof GameState) &
	    ((GameState) currentState).getShowMenu()) {
	    ((GameState) currentState).showOrDismissPauseMenu();
	}
    }


    @Override
    protected void onStop()
    {
	super.onStop();
    }
    
    /**
     * Called when the activity needs to save its state so it can be restored later.
     */
    //@Override
    //protected void onSaveInstanceState(Bundle savedInstanceState)
    //{
	//_logger.debug("onSaveInstanceState()");
	//savedInstanceState.putSerializable("Engine", Engine.getInstance());
	//super.onSaveInstanceState(savedInstanceState);
    //}
    
    /**
     * Called when the activity needs to restore from a saved bundle.
     */
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState)
//    {
//	_logger.debug("onRestoreInstanceState()");
//	super.onRestoreInstanceState(savedInstanceState);
//    }


    /**
     * Android Activity has been paused.
     */
    @Override
    protected void onPause()
    {
	_logger.debug("onPause()");

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
	_logger.debug("onResume()");

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
	// not logging anymore, already slow enough
	// _logger.debug("onTouchEvent(" + event + ")");

	Engine.getInstance().getTouchFilter().updateTouch(event);
	_gesture.onTouchEvent(event);

	// try {Thread.sleep(33);} catch (Exception e) {
	// _logger.error("Could not sleep event thread"); }

	return true;
    }


    /**
     * Android received a keydown event.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
	_logger.debug("onKeyDown(" + keyCode + ", " + event + ")");

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
	_logger.debug("onKeyUp(" + keyCode + ", " + event + ")");

	boolean result = false;

	Engine.getInstance().getInputSystem().keyUp(keyCode);

	// Let's check to see if the back button has been pressed
	IState currentState = Engine.getInstance().getCurrentState();
	if (keyCode == 4) {
	    if (currentState instanceof GameState &&
		((GameState) currentState).getShowMenu()) {
		// We are currently in the game and the in-game menu is up
		// So let's dismiss the in-game menu
		((GameState) currentState).showOrDismissPauseMenu();
	    } else if (currentState instanceof LevelMenuState) {
		// We are currently in the level menu
		// So let's go back to the main menu (MenuState)
		Engine.getInstance().popState();
		Engine.getInstance().pushState(new MenuState());
	    }
	}

	return result;
    }


    /**
     * Inline class to handle opengl rendering. Passes off all work to the
     * Engine singleton.
     * 
     * @author halsafar
     * 
     */
    class ClearRenderer implements GLSurfaceView.Renderer
    {
	private Activity _activity = null;


	public ClearRenderer(Activity act)
	{
	    _logger.debug("ClearRenderer(" + act + ")");
	    _activity = act;
	}


	/**
	 * OpenGL surface first created.
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
	    _logger.debug("onSurfaceCreated(" + gl.toString() + ", " +
			  config.toString() + ")");

	    Rect rect = new Rect();
	    Window window = _activity.getWindow();
	    window.getDecorView().getWindowVisibleDisplayFrame(rect);
	    int statusBarHeight = rect.top;
	    int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT)
		    .getTop();
	    int titleBarHeight = contentViewTop - statusBarHeight;

	    _logger.debug("StatusBarHeight: " + statusBarHeight);
	    _logger.debug("TitleBarHeight: " + titleBarHeight);

	    Engine.getInstance().setMenuBarHeight(statusBarHeight);
	    Engine.getInstance().setTitleBarHeight(titleBarHeight);
	    Engine.getInstance().setViewWidth(window.getDecorView().getWidth());
	    // offseting the screen with status and title bar
	    Engine.getInstance().setViewHeight(window.getDecorView()
						       .getHeight() -
						       statusBarHeight -
						       titleBarHeight);

	    Engine.getInstance().init(gl);
	}


	/**
	 * OpenGL surface has changed.
	 */
	public void onSurfaceChanged(GL10 gl, int w, int h)
	{
	    _logger.debug("onSurfaceChanged(" + gl.toString() + ", " + w +
			  ", " + h + ")");

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
	    _logger.debug("width:" + defaultWidth + "\theight: " +
			  defaultHeight);
	}


	/**
	 * OpenGL Surface has finished swapping. Ready for a new frame.
	 */
	public void onDrawFrame(GL10 gl)
	{
	    // do not log...
	    if (Engine.DEBUG) { 
		Message msg = new Message();
		Bundle b = new Bundle();
		b.putFloat("fps", Engine.getInstance().getTimer().getFrameDelta());
		msg.setData(b);
		handler.sendMessage(msg);
	    }

	    // update/draw engine
	    Engine.getInstance().update(gl);
	    Engine.getInstance().draw(gl);
	}
    }
}
