package ca.sandstorm.luminance.level;

/**
 * Class for level objects: bricks, goals.
 * @author Steven Indzeoski
 * 
 */
public class XmlLevelObject 
{
	private String _type;
	private float _positionX;
	private float _positionY;
	private float _rotation;
	
	/**
	 * Constructor for XmlLevelObject.
	 * @param type The type of the object.
	 * @throws IllegalArgumentException
	 * @precond type is brick or goal.
	 * @postcond XmlLevelObject is created with given type.
	 */
	public XmlLevelObject(String type) throws IllegalArgumentException 
	{
		if (isValidType(type))
		{
			_type = type;
		}
		else
		{
			throw new IllegalArgumentException("The type given is invalid.");
		}
	}
	
	/**
	 * Checks to see if a type given is valid.
	 * @param type The object type.
	 * @return True if the type is valid, false otherwise.
	 * @precond type != null
	 */
	public boolean isValidType(String type) 
	{
		if (type.equals("brick")) return true;
		else if (type.equals("goal")) return true;
		else return false;
	}
	
	/**
	 * Getter method for type.
	 * @return The type of the object.
	 */
	public String getType()
	{
		return _type;
	}
	
	/**
	 * Setter method for the object's position.
	 * @param x The x position of the object.
	 * @param y The y position of the object.
	 * @precond x > 0, y > 0
	 * @postcond _positionX == x, _positionY == y
	 */
	public void setPosition(float x, float y)
	{
		_positionX = x;
		_positionY = y;
	}
	
	/**
	 * The getter method for the object's x position.
	 * @return The object's x position.
	 */
	public float getPositionX()
	{
		return _positionX;
	}
	
	/**
	 * The getter method for the object's y position.
	 * @return The object's y position
	 */
	public float getPositionY()
	{
		return _positionY;
	}
	
	/**
	 * The setter method for the object's rotation.
	 * @param rotation The rotation of the object.
	 * @precond rotation > 0
	 * @postcond _rotation == rotation
	 */
	public void setRotation(float rotation)
	{
		_rotation = rotation;
	}
	
	/**
	 * The getter method for the object's rotation.
	 * @return The object's rotation.
	 */
	public float getRotation()
	{
		return _rotation;
	}
	
	/**
	 * Returns a string representation of the XmlLevelObject.
	 */
	public String toString()
	{
		return ("\nType: " + getType() + 
				"\nPosition: " + getPositionX() + " x " + getPositionY() + 
				"\nRotation: " + getRotation());
	}
}
