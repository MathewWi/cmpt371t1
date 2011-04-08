package ca.sandstorm.luminance.level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for the mirror tool in XmlLevels.
 * @author Steven Indzeoski
 *
 */
public class XmlLevelMirror extends XmlLevelTool 
{
    private static final Logger _logger = LoggerFactory.getLogger(XmlLevelMirror.class);
    
    /**
     * Constructor for the XmlLevelMirror class.
     * @param count
     * @precond count > 0
     * @postcond XmlLevelMirror is created
     */
    public XmlLevelMirror(int count) 
    {
	super(XmlLevelMirror.getId(), count);
	_logger.debug("XmlLevelMirror(" + count + ")");
    }

    /**
     * getId method for XmlLevelMirror.
     * @return The tool type.
     */
    public static String getId()
    {
	return "mirror";
    }

    /**
     * Method for creating a deep copy of the XmlLevelMirror.
     * @return A deep copy of the XmlLevelMirror.
     */
    @Override
    public XmlLevelMirror deepCopy()
    {
	return new XmlLevelMirror(this.getCount());
    }
}
