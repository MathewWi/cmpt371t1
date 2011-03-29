package ca.sandstorm.luminance.level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for level objects: bricks, goals.
 * @author Steven Indzeoski
 * 
 */
public abstract class XmlLevelObject 
{
    private static final Logger _logger = LoggerFactory.getLogger(XmlLevelObject.class);
    
    private String _type;
    private float _positionX;
    private float _positionY;
    private float _rotationX;
    private float _rotationY;
    private float _rotationZ;

    /**
     * Constructor for XmlLevelObject.
     * @param type The type of the object.
     * @throws IllegalArgumentException
     * @precond type is brick or goal.
     * @postcond XmlLevelObject is created with given type.
     */
    public XmlLevelObject(String type) throws IllegalArgumentException 
    {
	_logger.debug("XmlLevelObject(" + type + ")");
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
	_logger.debug("isValidType(" + type + ")");
	if (type.equals(XmlLevelBrick.getId())) 
	{
	    return true;
	}
	else if (type.equals(XmlLevelGoal.getId())) 
	{
	    return true;
	}
	else if (type.equals(XmlLevelEmitter.getId()))
	{
	    return true;
	}
	else return false;
    }

    /**
     * Getter method for type.
     * @return The type of the object.
     */
    public String getType()
    {
	_logger.debug("getType()");
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
	_logger.debug("setPosition(" + x + ", " + y + ")");
	_positionX = x;
	_positionY = y;
    }

    /**
     * The getter method for the object's x position.
     * @return The object's x position.
     */
    public float getPositionX()
    {
	_logger.debug("getPositionX()");
	return _positionX;
    }

    /**
     * The getter method for the object's y position.
     * @return The object's y position
     */
    public float getPositionY()
    {
	_logger.debug("getPositionY()");
	return _positionY;
    }

    /**
     * The setter method for the object's rotation.
     * @param rotation The rotation of the object.
     * @precond rotation > 0
     * @postcond _rotation == rotation
     */
    public void setRotation(float rotationX, float rotationY, float rotationZ)
    {
	_logger.debug("setRotation(" + rotationX + ", " + rotationY + ", " + rotationZ + ")");
	_rotationX = rotationX;
	_rotationY = rotationY;
	_rotationZ = rotationZ;
    }

    /**
     * Getter method for the object's X rotation.
     * @return The object's X rotation.
     */
    public float getRotationX()
    {
	_logger.debug("getRotationX()");
	return _rotationX;
    }

    /**
     * Getter method for the object's Y rotation.
     * @return The object's Y rotation.
     */
    public float getRotationY()
    {
	_logger.debug("getRotationY()");
	return _rotationY;
    }

    /**
     * Getter method for the object's Z rotation.
     * @return The object's Z rotation.
     */
    public float getRotationZ()
    {
	_logger.debug("getRotationZ()");
	return _rotationZ;
    }
    
    /**
     * Checks if an object is a coloured object.
     * @param object The XmlLevelObject to check.
     * @return True if the object is a coloured object, false otherwise.
     */
    public boolean isColouredObject()
    {
	if (_type.equals(XmlLevelGoal.getId())) {
	    return true;
	}
	if (_type.equals(XmlLevelEmitter.getId())) {
	    return true;
	}
	return false;
    }
    
    public abstract int getColour();
    
    public abstract String getColourAsString();

    /**
     * Returns a string representation of the XmlLevelObject.
     */
    public String toString()
    {
	_logger.debug("toString()");
	return ("\nType: " + getType() + 
		"\nPosition: " + getPositionX() + " x " + getPositionY() + 
		"\nRotation: (" + getRotationX() + ", " + getRotationY() + ", " + getRotationZ() + ")");
    }
}
