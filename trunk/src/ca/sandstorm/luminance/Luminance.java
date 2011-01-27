package ca.sandstorm.luminance;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.gamelogic.GameState;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;


public class Luminance extends Activity
{
    private static final Logger logger = LoggerFactory
	    .getLogger(Luminance.class);

    private GLSurfaceView mGLView;
    
    private final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            float fps = msg.getData().getFloat("fps");
            
            Luminance.this.setTitle("Luminance - " + fps);
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	logger.debug("onCreate()");
	
	// init gl surface view for android
	super.onCreate(savedInstanceState);
	mGLView = new GLSurffaceView(this);
	mGLView.setRenderer(new ClearRenderer());
	setContentView(mGLView);

	// init the engine and add our states
	Engine.getInstance().pushState(new GameState());
    }


    @Override
    protected void onPause()
    {
	logger.debug("onPause()");

	super.onPause();
	mGLView.onPause();
    }


    @Override
    protected void onResume()
    {
	logger.debug("onResume()");

	super.onResume();
	mGLView.onResume();
    }


    class ClearRenderer implements GLSurfaceView.Renderer
    {
	//private static final Logger logger = LoggerFactory
	//	.getLogger(ClearRenderer.class);


	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
	    logger.debug("onSurfaceCreated(" + gl.toString() + ", " +
			 config.toString() + ")");

	    // Engine.getInstance().init();
	}


	public void onSurfaceChanged(GL10 gl, int w, int h)
	{
	    logger.debug("onSurfaceChanged(" + gl.toString() + ", " + w + ", " +
			 h + ")");

	    Engine.getInstance().deviceChanged(gl, w, h);
	}


	public void onDrawFrame(GL10 gl)
        {
	    // do not log...
	    Message msg = new Message();
	    Bundle b = new Bundle();
	    b.putFloat("fps", Luminance.this.getWindowManager().getDefaultDisplay().getRefreshRate());
	    msg.setData(b);
	    handler.sendMessage(msg);
	                

            // update/draw engine
            Engine.getInstance().update(gl);
            Engine.getInstance().draw(gl);
        }
    }
}
