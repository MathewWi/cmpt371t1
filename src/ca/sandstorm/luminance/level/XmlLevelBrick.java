package ca.sandstorm.luminance.level;

/**
 * Class for brick objects in levels.
 * @author Steven Indzeoski
 *
 */
public class XmlLevelBrick extends XmlLevelObject {

	/**
	 * Constructor for XmlLevelBrick.
	 * @throws IllegalArgumentException
	 * @postcond XmlLevelBrick is created
	 */
	public XmlLevelBrick() throws IllegalArgumentException {
		super(XmlLevelBrick.getId());
	}
	
	/**
	 * getId method for XmlLevelBrick.
	 * @return The object type.
	 */
	public static String getId()
	{
	    return "brick";
	}

}
