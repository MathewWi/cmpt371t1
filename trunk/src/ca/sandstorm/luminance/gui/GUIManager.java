package ca.sandstorm.luminance.gui;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;
import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.input.InputTouchScreen;
import ca.sandstorm.luminance.input.InputXY;


public class GUIManager
{

    private int MAX_BUTTON_COUNT = 10;

    private int _numberOfButtons;
    private InputTouchScreen _touchScreen;
    private Button _buttons[];


    public GUIManager()
    {
	_touchScreen = Engine.getInstance().getInputSystem().getTouchScreen();
	_buttons = new Button[MAX_BUTTON_COUNT];
	_numberOfButtons = 0;
    }

    /**
     * Add a button to be managed by this GUIManager. If the GUIManager has
     * already hit its maximum number of buttons it can hold, then it won't add
     * the button and return false.
     * @param button The button to be added to, and managed by, this GUIManager
     * @return false if the button wasn't added to the GUIManager, true otherwise
     * @precond button != null
     * @postcond _buttons.length <= 5
     */
    public boolean addButton(Button button)
    {
	if (_numberOfButtons == MAX_BUTTON_COUNT) {
	    return false;
	} else {
	    _buttons[_numberOfButtons++] = button;
	    return true;
	}
    }


    /**
     * Convenience method. Does the same thing as touchOccured(float, float)
     * except it uses the MotionEvent instance passed in. Checks the X and Y
     * coordinates of event to see if they fall in to any of the button's
     * space.
     * @param event The MotionEvent that represents the touch
     * @return the Button that was tapped.
     * @precond event != null
     * @postcond n/a
     */
    public Button touchOccured(MotionEvent event)
    {
	float xPosition = event.getX();
	float yPosition = event.getY();

	return this.touchOccured(xPosition, yPosition);
    }


    /**
     * @param x The X coordinate of the touch
     * @param y The Y coorindate of the touch
     * @return the Button that was tapped.
     * @precond x and y can't be negative
     * @postcond n/a
     */
    public Button touchOccured(float x, float y)
    {
	/*
	 * Searches through the array of buttons and compares each one
	 */
	for (int i = 0; i < _numberOfButtons; i++) {
	    Button button = _buttons[i];
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
	for (int i = 0; i < _numberOfButtons; i++) {
	    _buttons[i].draw(gl);
	}	
    }
    
}
