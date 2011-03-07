package ca.sandstorm.luminance.level;

/**
 * Class for XmlLevelPrism objects in XmlLevels.
 * @author Steven Indzeoski
 *
 */
public class XmlLevelPrism extends XmlLevelTool
{
	/**
	 * Constructor for XmlLevelPrism class.
	 * @param count The number of prisms for the player to use in the current level.
	 * @precond count > 0
	 * @postcond An XmlLevelPrism is created.
	 */
	public XmlLevelPrism(int count) 
	{
		super(XmlLevelPrism.getId(), count);
	}
	
	/**
	 * getId method for XmlLevelPrism.
	 * @return The tool type.
	 */
	public static String getId()
	{
	    return "prism";
	}

}
