package ca.sandstorm.luminance.level;

import java.util.Vector;

/**
 * Class for brick objects in levels.
 * @author Steven Indzeoski
 *
 */
public class XmlLevelBrick extends XmlLevelObject 
{
    /**
     * Constructor for XmlLevelBrick.
     * @param position A vector containing the position of the brick (x, y).
     * @param rotation A vector containing the rotation of the brick (x, y, z).
     * @throws IllegalArgumentException
     * @postcond XmlLevelBrick is created
     */
    public XmlLevelBrick(Vector<Float> position, Vector<Float> rotation) throws IllegalArgumentException 
    {
	super(XmlLevelBrick.getId(), position, rotation);
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
    @Override
    public int getColour()
    {
	return Color.WHITE;
    }

    /**
     * Getter method for the colour of the brick. Always returns white.
     * @return white
     */
    @Override
    public String getColourAsString()
    {
	return "white";
    }
    
    /**
     * Creates a deep copy of an XmlLevelBrick.
     * @return A deep copy of an XmlLevelBrick.
     */
    @Override
    public XmlLevelBrick deepCopy() {
	Vector<Float> position = new Vector<Float>(2);
	for (int i = 0; i < this.getPosition().size(); i++) {
	    position.add(this.getPosition().get(i));
	}
	Vector<Float> rotation = new Vector<Float>(3);
	for (int i = 0; i < this.getRotation().size(); i++) {
	    rotation.add(this.getRotation().get(i));
	}
	return new XmlLevelBrick(position, rotation);
    }
}
