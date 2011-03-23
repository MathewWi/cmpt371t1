package ca.sandstorm.luminance.input;

import ca.sandstorm.luminance.Engine;
import android.view.MotionEvent;

/**
 * Code from Replica Island (Android open source game project)
 * 
 * Class to filter out the single touch and store it in the input touch screen class
 */
public class SingleTouchFilter extends TouchFilter
{

    public void updateTouch(MotionEvent event)
    {
	if (event.getAction() == MotionEvent.ACTION_UP) {
	    Engine.getInstance()
		    .getInputSystem()
		    .touchUp(0,
			     event.getRawX() *
				     (1.0f / Engine.getInstance()
					     .getViewScaleX()),
			     event.getRawY() *
				     (1.0f / Engine.getInstance()
					     .getViewScaleY()));
	} else {
	    Engine.getInstance()
		    .getInputSystem()
		    .touchDown(0,
			       event.getRawX() *
				       (1.0f / Engine.getInstance()
					       .getViewScaleX()),
			       event.getRawY() *
				       (1.0f / Engine.getInstance()
					       .getViewScaleY()));
	}

	Engine.getInstance().getInputSystem().getTouchScreen()
		.setTouchEvent(event);
    }


    @Override
    public void reset()
    {
    }

}
