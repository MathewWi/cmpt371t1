package ca.sandstorm.luminance.input;

import javax.vecmath.Vector2f;

import android.util.FloatMath;
import android.view.MotionEvent;


public class InputTouchScreen
{

    private int MAX_TOUCH_POINTS = 5;
    private InputXY mTouchPoints[];

    private MotionEvent touchEvent;
    
    public InputTouchScreen()
    {
	mTouchPoints = new InputXY[MAX_TOUCH_POINTS];
	for (int x = 0; x < MAX_TOUCH_POINTS; x++) {
	    mTouchPoints[x] = new InputXY();
	}
    }


    public void reset()
    {
	for (int x = 0; x < MAX_TOUCH_POINTS; x++) {
	    mTouchPoints[x].reset();
	}
    }


    public final void press(int index, float currentTime, float x, float y)
    {
	assert (index >= 0 && index < MAX_TOUCH_POINTS);
	if (index < MAX_TOUCH_POINTS) {
	    mTouchPoints[index].press(currentTime, x, y);
	}
    }


    public final void release(int index)
    {
	if (index < MAX_TOUCH_POINTS) {
	    mTouchPoints[index].release();
	}
    }


    public void resetAll()
    {
	for (int x = 0; x < MAX_TOUCH_POINTS; x++) {
	    mTouchPoints[x].reset();
	}
    }


    public boolean getTriggered(int index, float time)
    {
	boolean triggered = false;
	if (index < MAX_TOUCH_POINTS) {
	    triggered = mTouchPoints[index].getTriggered(time);
	}
	return triggered;
    }


    public boolean getPressed(int index)
    {
	boolean pressed = false;
	if (index < MAX_TOUCH_POINTS) {
	    pressed = mTouchPoints[index].getPressed();
	}
	return pressed;
    }


    public final void setVector(int index, Vector2f vector)
    {
	if (index < MAX_TOUCH_POINTS) {
	    mTouchPoints[index].setVector(vector);
	}
    }


    public final float getX(int index)
    {
	float magnitude = 0.0f;
	if (index < MAX_TOUCH_POINTS) {
	    magnitude = mTouchPoints[index].getX();
	}
	return magnitude;
    }


    public final float getY(int index)
    {
	float magnitude = 0.0f;
	if (index < MAX_TOUCH_POINTS) {
	    magnitude = mTouchPoints[index].getY();
	}
	return magnitude;
    }


    public final float getLastPressedTime(int index)
    {
	float time = 0.0f;
	if (index < MAX_TOUCH_POINTS) {
	    time = mTouchPoints[index].getLastPressedTime();
	}
	return time;
    }


    public InputXY findPointerInRegion(float regionX, float regionY,
	    float regionWidth, float regionHeight)
    {
	InputXY touch = null;
	for (int x = 0; x < MAX_TOUCH_POINTS; x++) {
	    final InputXY pointer = mTouchPoints[x];
	    if (pointer.getPressed() &&
		getTouchedWithinRegion(pointer.getX(), pointer.getY(), regionX,
				       regionY, regionWidth, regionHeight)) {
		touch = pointer;
		break;
	    }
	}
	return touch;
    }


    private final boolean getTouchedWithinRegion(float x, float y,
	    float regionX, float regionY, float regionWidth, float regionHeight)
    {
	return (x >= regionX && y >= regionY && x <= regionX + regionWidth && y <= regionY +
										   regionHeight);
    }


    public boolean getTriggered(float gameTime)
    {
	boolean triggered = false;
	for (int x = 0; x < MAX_TOUCH_POINTS && !triggered; x++) {
	    triggered = mTouchPoints[x].getTriggered(gameTime);
	}
	return triggered;
    }

    /**
     * Set the current movement event's object
     * @param touchEvent store the current object of movement event
     */
    public void setTouchEvent(MotionEvent touchEvent){
	this.touchEvent = touchEvent;
    }
    
    /**
     * Get the current movement event's object
     * @return Object used to report movement event
     */
    public MotionEvent getTouchEvent(){
	return touchEvent;
    }
    
    /* for later use in pinch gesture
    public float getPinchDistance(){
	float x = touchEvent.getX(0) - touchEvent.getX(1);
	float y = touchEvent.getY(0) - touchEvent.getY(1);
	return FloatMath.sqrt(x * x + y * y);
    }
   */ 
}
