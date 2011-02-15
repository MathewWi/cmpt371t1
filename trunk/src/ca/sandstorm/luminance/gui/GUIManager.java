package ca.sandstorm.luminance.gui;

import android.view.MotionEvent;
import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.input.InputTouchScreen;
import ca.sandstorm.luminance.input.InputXY;


public class GUIManager
{

    private int MAX_BUTTON_COUNT = 10;

    private int numberOfButtons;
    private InputTouchScreen _touchScreen;
    private MenuButton buttons[];


    public GUIManager()
    {
	_touchScreen = Engine.getInstance().getInputSystem().getTouchScreen();
	buttons = new MenuButton[MAX_BUTTON_COUNT];
	numberOfButtons = 0;
    }


    /*
     * Add a button to be managed by the InputManager returns true if
     * successful, false otherwise
     */
    public boolean addButton(MenuButton button)
    {
	if (numberOfButtons == MAX_BUTTON_COUNT) {
	    return false;
	} else {
	    buttons[numberOfButtons] = button;
	    return true;
	}
    }


    /*
     * Convenience method. Pass in a MotionEvent and it will figure out IF IT
     * STARTED inside a button. Recommended for taps. For other kinds of
     * touches, use touchOccured(float, float).
     */
    public MenuButton touchOccured(MotionEvent event)
    {
	float xPosition = event.getX();
	float yPosition = event.getY();

	return this.touchOccured(xPosition, yPosition);
    }


    /*
     * Will figure out if a touch, given x and y, falls into button-space.
     */
    public MenuButton touchOccured(float x, float y)
    {
	MenuButton tappedButton = null;

	/*
	 * Searches through the array of buttons and compares each one
	 */

	for (int i = 0; i < numberOfButtons; i++) {
	    MenuButton button = buttons[i];
	    InputXY input = _touchScreen
		    .findPointerInRegion(button.getX(), button.getY(),
					 button.getWidth(), button.getHeight());
	    if (button.getInput() == input) {
		tappedButton = button;
		break;
	    }
	}

	return tappedButton;
    }
}
