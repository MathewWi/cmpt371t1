package ca.sandstorm.luminance.level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for brick objects in levels.
 * @author Steven Indzeoski
 *
 */
public class XmlLevelBrick extends XmlLevelObject 
{
    private static final Logger _logger = LoggerFactory
    .getLogger(XmlLevelBrick.class);

    /**
     * Constructor for XmlLevelBrick.
     * @throws IllegalArgumentException
     * @postcond XmlLevelBrick is created
     */
    public XmlLevelBrick() throws IllegalArgumentException 
    {
	super(XmlLevelBrick.getId());
	_logger.debug("XmlLevelBrick()");
    }

    /**
     * getId method for XmlLevelBrick.
     * @return The object type.
     */
    public static String getId()
    {
	_logger.debug("getId()");
	return "brick";
    }

    @Override
    public int getColour()
    {
	return Color.WHITE;
    }

    @Override
    public String getColourAsString()
    {
	return "white";
    }

}
