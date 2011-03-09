package ca.sandstorm.luminance.level;

import ca.sandstorm.luminance.level.XmlLevelGoal.GoalColour;

/**
 * Class for the light emitter object in levels
 * @author Steven Indzeoski
 *
 */
public class XmlLevelEmitter extends XmlLevelObject
{
    public enum EmitterColour { WHITE, RED, GREEN, BLUE };
    
    private EmitterColour _colour;
    
    /**
     * Constructor for XmlLevelEmitter.
     * @postcond XmlLevelEmitter is created.
     */
    public XmlLevelEmitter(String colour)
    {
	super(XmlLevelEmitter.getId());
	
	colour = colour.toUpperCase();
	
	if (!isValidColour(colour))
	{
	    throw new IllegalArgumentException("The colour " + colour + " is invalid.");
	}
	
	if (colour.equals(EmitterColour.WHITE.toString()))
	{
	    _colour = EmitterColour.WHITE;
	}
	else if (colour.equals(EmitterColour.RED.toString()))
	{
	    _colour = EmitterColour.RED;
	}
	else if (colour.equals(EmitterColour.GREEN.toString()))
	{
	    _colour = EmitterColour.GREEN;
	}
	else if (colour.equals(EmitterColour.BLUE.toString()))
	{
	    _colour = EmitterColour.BLUE;
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
     * Method for getting the colour of an XmlLevelEmitter.
     * @return The colour of the XmlLevelEmitter.
     */
    public String getColour()
    {
	return _colour.toString();
    }
    
    /**
     * getId method for XmlLevelEmitter.
     * @return The object type.
     */
    public static String getId()
    {
	return "emitter";
    }
    
    
}
