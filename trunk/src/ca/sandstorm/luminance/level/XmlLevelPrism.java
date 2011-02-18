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
	 */
	public XmlLevelPrism(int count) 
	{
		super("prism", count);
	}

}
