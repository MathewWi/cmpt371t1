package ca.sandstorm.luminance.input;

import ca.sandstorm.luminance.Engine;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.MotionEvent;

/**
 * Code from Replica Island (Android open source game project)
 * 
 * Class to filter out the multi single touch and store it in the input touch screen class
 */
public class MultiTouchFilter extends SingleTouchFilter
{
    private boolean mCheckedForMultitouch = false;
    private boolean mSupportsMultitouch = false;


    @Override
    public void updateTouch(MotionEvent event)
    {
	final int pointerCount = event.getPointerCount();
	for (int x = 0; x < pointerCount; x++) {
	    final int action = event.getAction();
	    final int actualEvent = action & MotionEvent.ACTION_MASK;
	    final int id = event.getPointerId(x);
	    if (actualEvent == MotionEvent.ACTION_POINTER_UP ||
		actualEvent == MotionEvent.ACTION_UP ||
		actualEvent == MotionEvent.ACTION_CANCEL) {
		Engine.getInstance()
			.getInputSystem()
			.touchUp(id,
				 event.getX(x) *
					 (1.0f / Engine.getInstance()
						 .getViewScaleX()),
				 event.getY(x) *
					 (1.0f / Engine.getInstance()
						 .getViewScaleY()));
	    } else {
		Engine.getInstance()
			.getInputSystem()
			.touchDown(id,
				   event.getX(x) *
					   (1.0f / Engine.getInstance()
						   .getViewScaleX()),
				   event.getY(x) *
					   (1.0f / Engine.getInstance()
						   .getViewScaleY()));
	    }
	}
	Engine.getInstance().getInputSystem().getTouchScreen()
		.setTouchEvent(event);
    }


    @Override
    public boolean supportsMultitouch(Context context)
    {
	if (!mCheckedForMultitouch) {
	    PackageManager packageManager = context.getPackageManager();
	    mSupportsMultitouch = packageManager
		    .hasSystemFeature("android.hardware.touchscreen.multitouch");
	    mCheckedForMultitouch = true;
	}

	return mSupportsMultitouch;
    }
}
