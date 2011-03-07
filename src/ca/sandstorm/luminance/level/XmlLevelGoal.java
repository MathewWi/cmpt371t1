package ca.sandstorm.luminance.level;

/**
 * Class for goal objects in levels.
 * @author Steven Indzeoski
 *
 */
public class XmlLevelGoal extends XmlLevelObject
{	
    public enum GoalColour { WHITE, RED, GREEN, BLUE };

    private GoalColour _colour;

    /**
     * Constructor for XmlLevelGoal class.
     * @throws IllegalArgumentException
     * @precond colour != null
     * @postcond XmlLevelGoal is created.
     */
    public XmlLevelGoal(String colour) throws IllegalArgumentException {
	super(XmlLevelGoal.getId());
	if (!isValidColour(colour))
	{
	    throw new IllegalArgumentException("The colour " + colour + " is invalid.");
	}
	colour = colour.toUpperCase();
	if (colour.equals(GoalColour.WHITE.name()))
	{
	    _colour = GoalColour.WHITE;
	}
	else if (colour.equals(GoalColour.RED.name()))
	{
	    _colour = GoalColour.RED;
	}
	else if (colour.equals(GoalColour.BLUE.name()))
	{
	    _colour = GoalColour.BLUE;
	}
	else if (colour.equals(GoalColour.GREEN.name()))
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
	if (colour.equals(GoalColour.WHITE.name()))
	{
	    return true;
	}
	else if (colour.equals(GoalColour.RED.name())) 
	{
	    return true;
	}
	else if (colour.equals(GoalColour.BLUE.name())) 
	{
	    return true;
	}
	else if (colour.equals(GoalColour.GREEN.name()))
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
	if (!getType().equals(XmlLevelGoal.getId())) 
	{
	    throw new IllegalStateException("Trying to get colour of non-goal object.");
	}
	return _colour.name();
    }

    /**
     * Returns a string representation of the XmlLevelObject.
     */
    public String toString()
    {
	return (super.toString() + "\nColour: " + getColour());
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
