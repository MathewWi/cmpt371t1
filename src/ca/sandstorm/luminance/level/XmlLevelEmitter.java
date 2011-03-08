package ca.sandstorm.luminance.level;

/**
 * Class for the light emitter object in levels
 * @author Steven Indzeoski
 *
 */
public class XmlLevelEmitter extends XmlLevelObject
{
    /**
     * Constructor for XmlLevelEmitter.
     * @postcond XmlLevelEmitter is created.
     */
    public XmlLevelEmitter()
    {
	super(XmlLevelEmitter.getId());
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
