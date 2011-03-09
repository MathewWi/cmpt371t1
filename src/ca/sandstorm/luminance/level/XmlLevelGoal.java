package ca.sandstorm.luminance.level;

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

    private GoalColour _colour;

    /**
     * Constructor for XmlLevelGoal class.
     * @throws IllegalArgumentException
     * @precond colour != null
     * @postcond XmlLevelGoal is created.
     */
    public XmlLevelGoal(String colour) throws IllegalArgumentException 
    {
	super(XmlLevelGoal.getId());
	_logger.debug("XmlLevelGoal(" + colour + ")");
	
	colour = colour.toUpperCase();
	
	if (!isValidColour(colour))
	{
	    throw new IllegalArgumentException("The colour " + colour + " is invalid.");
	}
	
	if (colour.equals(GoalColour.WHITE.toString()))
	{
	    _colour = GoalColour.WHITE;
	}
	else if (colour.equals(GoalColour.RED.toString()))
	{
	    _colour = GoalColour.RED;
	}
	else if (colour.equals(GoalColour.BLUE.toString()))
	{
	    _colour = GoalColour.BLUE;
	}
	else if (colour.equals(GoalColour.GREEN.toString()))
	{
	    _colour = GoalColour.GREEN;
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
	_logger.debug("isValidColour(" + colour + ")");
	if (colour.equals(GoalColour.WHITE.toString()))
	{
	    return true;
	}
	else if (colour.equals(GoalColour.RED.toString())) 
	{
	    return true;
	}
	else if (colour.equals(GoalColour.BLUE.toString())) 
	{
	    return true;
	}
	else if (colour.equals(GoalColour.GREEN.toString()))
	{
	    return true;
	}
	return false;
    }

    /**
     * Getter method for colour when object is a goal.
     * @return The colour of the goal.
     */
    public String getColour()
    {
	_logger.debug("getColour()");
	if (!getType().equals(XmlLevelGoal.getId())) 
	{
	    throw new IllegalStateException("Trying to get colour of non-coloured object.");
	}
	return _colour.toString();
    }

    /**
     * Returns a string representation of the XmlLevelObject.
     */
    public String toString()
    {
	_logger.debug("toString()");
	return (super.toString() + "\nColour: " + getColour());
    }

    /**
     * getId method for XmlLevelGoal
     * @return The type of the object.
     */
    public static String getId()
    {
	_logger.debug("getId()");
	return "goal";
    }
}
