package ca.sandstorm.luminance.level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for XmlLevelPrism objects in XmlLevels.
 * @author Steven Indzeoski
 *
 */
public class XmlLevelPrism extends XmlLevelTool
{
    private static final Logger _logger = LoggerFactory.getLogger(XmlLevelPrism.class);
    /**
     * Constructor for XmlLevelPrism class.
     * @param count The number of prisms for the player to use in the current level.
     * @precond count > 0
     * @postcond An XmlLevelPrism is created.
     */
    public XmlLevelPrism(int count) 
    {
	super(XmlLevelPrism.getId(), count);
	_logger.debug("XmlLevelPrism(" + count + ")");
    }

    /**
     * getId method for XmlLevelPrism.
     * @return The tool type.
     */
    public static String getId()
    {
	_logger.debug("getId()");
	return "prism";
    }

}
