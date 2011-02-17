package ca.sandstorm.luminance.gui;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;
import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.input.InputTouchScreen;
import ca.sandstorm.luminance.input.InputXY;


public class GUIManager
{

    private int MAX_BUTTON_COUNT = 10;

    private int numberOfButtons;
    private InputTouchScreen _touchScreen;
    private Button buttons[];


    public GUIManager()
    {
	_touchScreen = Engine.getInstance().getInputSystem().getTouchScreen();
	buttons = new Button[MAX_BUTTON_COUNT];
	numberOfButtons = 0;
    }


    /*
     * Add a button to be managed by the InputManager returns true if
     * successful, false otherwise
     */
    public boolean addButton(Button button)
    {
	if (numberOfButtons == MAX_BUTTON_COUNT) {
	    return false;
	} else {
	    buttons[numberOfButtons++] = button;
	    return true;
	}
    }


    /*
     * Convenience method. Pass in a MotionEvent and it will figure out IF IT
     * STARTED inside a button. Recommended for taps. For other kinds of
     * touches, use touchOccured(float, float).
     */
    public Button touchOccured(MotionEvent event)
    {
	float xPosition = event.getX();
	float yPosition = event.getY();

	return this.touchOccured(xPosition, yPosition);
    }


    /*
     * Will figure out if a touch, given x and y, falls into button-space.
     */
    public Button touchOccured(float x, float y)
    {
	/*
	 * Searches through the array of buttons and compares each one
	 */
	for (int i = 0; i < numberOfButtons; i++) {
	    Button button = buttons[i];
	    if (x > button.getX() && x < button.getX() + button.getWidth() &&
		y > button.getY() && y < button.getY() + button.getHeight()) {
		return button;
	    }
	}

	return null;
    }
    
    
    public void update(GL10 gl)
    {
	
    }
    
    
    public void draw(GL10 gl)
    {
	for (int i = 0; i < numberOfButtons; i++) {
	    buttons[i].draw(gl);
	}	
    }
    
}
