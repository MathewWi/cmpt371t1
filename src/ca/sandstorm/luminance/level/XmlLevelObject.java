package ca.sandstorm.luminance.level;

public class XmlLevelObject 
{
	private String _type;
	private String _colour;
	private float _positionX;
	private float _positionY;
	private float _rotation;
	
	/**
	 * Constructor for XmlLevelObject.
	 * @param type The type of the object.
	 * @throws Exception
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
	 */
	public boolean isValidType(String type) 
	{
		if (type.equals("brick")) return true;
		else if (type.equals("goal")) return true;
		else return false;
	}
	
	/**
	 * Checks to see if a colour given is valid.
	 * @param colour The given colour, should be white, red, blue, or green.
	 * @return True if the colour is valid, false otherwise.
	 */
	public boolean isValidColour(String colour) 
	{
		if (colour.equals("white")) return true;
		else if (colour.equals("red")) return true;
		else if (colour.equals("blue")) return true;
		else if (colour.equals("green")) return true;
		return false;
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
	 * Setter method for colour when object is a goal.
	 * @param colour The colour of the goal.
	 */
	public void setColour(String colour)
	{
		if (!getType().equals("goal")) 
		{
			throw new IllegalStateException("Trying to set colour of non-goal object.");
		}
		else if (isValidColour(colour))
		{
			_colour = colour;
		}
		else
		{
			throw new IllegalArgumentException("The colour given is invalid.");
		}
	}
	
	/**
	 * Getter method for colour when object is a goal.
	 * @return The colour of the goal.
	 */
	public String getColour()
	{
		if (!getType().equals("goal")) 
		{
			throw new IllegalStateException("Trying to get colour of non-goal object.");
		}
		return _colour;
	}
	
	/**
	 * Setter method for the object's position.
	 * @param x The x position of the object.
	 * @param y The y position of the object.
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
}
