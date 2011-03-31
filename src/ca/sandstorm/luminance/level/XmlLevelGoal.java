package ca.sandstorm.luminance.level;

import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class for goal objects in levels.
 * @author Steven Indzeoski
 *
 */
public class XmlLevelGoal extends XmlLevelObject
{	
    private static final Logger _logger = LoggerFactory.getLogger(XmlLevelGoal.class);
    
    public enum GoalColour { WHITE, RED, GREEN, BLUE };

    private int _colour;

    /**
     * Constructor for XmlLevelGoal class.
     * @param colour The colour of the goal.
     * @param position A vector containing the position of the goal (x, y).
     * @param rotation A vector containing the rotation of the goal (x, y, z).
     * @throws IllegalArgumentException
     * @precond colour is white, red, green, or blue.
     * @postcond XmlLevelGoal is created.
     */
    public XmlLevelGoal(String colour, Vector<Float> position, Vector<Float> rotation) throws IllegalArgumentException 
    {
	super(XmlLevelGoal.getId(), position, rotation);
	_logger.debug("XmlLevelGoal(" + colour + ")");
	
	if (!isValidColour(colour)) {
	    throw new IllegalArgumentException("The colour " + colour + " is invalid.");
	}
	else {
	    _colour = Color.parseColor(colour);
	}
    }

    /**
     * Checks to see if a colour given is valid.
     * @param colour The given colour, should be white, red, blue, or green.
     * @return True if the colour is valid, false otherwise.
     * @precond colour != null
     */
    public boolean isValidColour(String colour) 
    {
	colour = colour.toUpperCase();
	if (colour.equals(GoalColour.WHITE.toString())) {
	    return true;
	}
	else if (colour.equals(GoalColour.RED.toString()))  {
	    return true;
	}
	else if (colour.equals(GoalColour.BLUE.toString()))  {
	    return true;
	}
	else if (colour.equals(GoalColour.GREEN.toString())) {
	    return true;
	}
	return false;
    }

    /**
     * Getter method for colour when object is a goal.
     * @return The colour of the goal.
     */
    public int getColour()
    {
	return _colour;
    }

    /**
     * Returns a string representation of the XmlLevelObject.
     */
    public String toString()
    {
	return (super.toString() + "\nColour: " + getColour());
    }
    
    /**
     * Returns the goals colour as a String.
     * @return The goals colour as a String.
     */
    public String getColourAsString()
    {
	switch (_colour) {
	    case Color.WHITE:
		return "white";
	    case Color.RED:
		return "red";
	    case Color.GREEN:
		return "green";
	    case Color.BLUE:
		return "blue";
	    default:
		return null;    
	}
    }

    /**
     * getId method for XmlLevelGoal
     * @return The type of the object.
     */
    public static String getId()
    {
	return "goal";
    }
}
