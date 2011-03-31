package ca.sandstorm.luminance.level;

/**
 * Class for brick objects in levels.
 * @author Steven Indzeoski
 *
 */
public class XmlLevelBrick extends XmlLevelObject 
{
    /**
     * Constructor for XmlLevelBrick.
     * @throws IllegalArgumentException
     * @postcond XmlLevelBrick is created
     */
    public XmlLevelBrick() throws IllegalArgumentException 
    {
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

    /**
     * Getter method for the colour of the brick. Always returns white.
     * @return Color.WHITE
     */
    public int getColour()
    {
	return Color.WHITE;
    }

    /**
     * Getter method for the colour of the brick. Always returns white.
     * @return white
     */
    public String getColourAsString()
    {
	return "white";
    }

}
