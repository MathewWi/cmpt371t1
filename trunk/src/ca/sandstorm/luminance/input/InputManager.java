package ca.sandstorm.luminance.input;

import android.view.MotionEvent;
import ca.sandstorm.luminance.Engine;

public class InputManager
{

    private InputTouchScreen touchScreen;
    private MenuButton buttons[];
    
    public InputManager()
    {
	touchScreen = Engine.getInstance().getInputSystem().getTouchScreen();
    }
    
    /* 
     * Convenience method. Pass in a MotionEvent and it will figure
     * out IF IT STARTED inside a button. Recommended for taps. For
     * other kinds of touches, use touchOccured(float, float).
     */
    public void touchOccured(MotionEvent event)
    {
	float xPosition = event.getX();
	float yPosition = event.getY();
	
	this.touchOccured(xPosition, yPosition);
    }
    
    /*
     * Will figure out if a touch, given x and y, falls into button-space.
     */
    public MenuButton touchOccured(float x, float y)
    {
	
	return null;
    }
}
