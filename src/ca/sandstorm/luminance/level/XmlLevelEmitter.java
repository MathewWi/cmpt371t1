package ca.sandstorm.luminance.level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.level.XmlLevelGoal.GoalColour;

/**
 * Class for the light emitter object in levels
 * @author Steven Indzeoski
 *
 */
public class XmlLevelEmitter extends XmlLevelObject
{
    private static final Logger _logger = LoggerFactory.getLogger(XmlLevelEmitter.class);
    
    public enum EmitterColour { WHITE, RED, GREEN, BLUE };
    
    private int _colour;
    
    /**
     * Constructor for XmlLevelEmitter.
     * @postcond XmlLevelEmitter is created.
     */
    public XmlLevelEmitter(String colour)
    {
	super(XmlLevelEmitter.getId());
	_logger.debug("XmlLevelEmitter(" + colour + ")");
	
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
     * Method for getting the colour of an XmlLevelEmitter.
     * @return The colour of the XmlLevelEmitter.
     */
    public int getColour()
    {
	return _colour;
    }
    
    /**
     * Returns the emitters colour as a String.
     * @return The emitters colour as a String.
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
     * getId method for XmlLevelEmitter.
     * @return The object type.
     */
    public static String getId()
    {
	return "emitter";
    }    
}
