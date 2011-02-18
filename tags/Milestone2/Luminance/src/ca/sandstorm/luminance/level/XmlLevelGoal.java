package ca.sandstorm.luminance.level;

/**
 * Class for goal objects in levels.
 * @author Steven Indzeoski
 *
 */
public class XmlLevelGoal extends XmlLevelObject {
	
	private String _colour;

	/**
	 * Constructor for XmlLevelGoal class.
	 * @throws IllegalArgumentException
	 * @precond colour != null
	 * @postcond XmlLevelGoal is created.
	 */
	public XmlLevelGoal(String colour) throws IllegalArgumentException {
		super("goal");
		if (!isValidColour(colour))
		{
			throw new IllegalArgumentException("The colour " + colour + " is invalid.");
		}
		_colour = colour;
	}
	
	/**
	 * Checks to see if a colour given is valid.
	 * @param colour The given colour, should be white, red, blue, or green.
	 * @return True if the colour is valid, false otherwise.
	 * @precond colour != null
	 */
	public boolean isValidColour(String colour) 
	{
		if (colour.equals("white"))
		{
		    return true;
		}
		else if (colour.equals("red")) 
		{
		    return true;
		}
		else if (colour.equals("blue")) 
		{
		    return true;
		}
		else if (colour.equals("green"))
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
		if (!getType().equals("goal")) 
		{
			throw new IllegalStateException("Trying to get colour of non-goal object.");
		}
		return _colour;
	}
	
	/**
	 * Returns a string representation of the XmlLevelObject.
	 */
	public String toString()
	{
		return ("\nType: " + getType() + 
				"\nColour: " + getColour() +
				"\nPosition: " + getPositionX() + " x " + getPositionY() + 
				"\nRotation: " + getRotation());
	}
}
