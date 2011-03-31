package ca.sandstorm.luminance.level;

import java.util.Vector;

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
    private Vector<Float> _position;
//    private float _rotationX;
//    private float _rotationY;
//    private float _rotationZ;
    private Vector<Float> _rotation;

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
	if (isValidType(type)) {
	    _type = type;
	}
	else {
	    throw new IllegalArgumentException("The type given is invalid.");
	}
	_position = new Vector<Float>(2);
	_rotation = new Vector<Float>(3);
    }

    /**
     * Checks to see if a type given is valid.
     * @param type The object type.
     * @return True if the type is valid, false otherwise.
     * @precond type != null
     */
    public boolean isValidType(String type) 
    {
	if (type.equals(XmlLevelBrick.getId())) {
	    return true;
	}
	else if (type.equals(XmlLevelGoal.getId())) {
	    return true;
	}
	else if (type.equals(XmlLevelEmitter.getId())) {
	    return true;
	}
	else {
	    return false;
	}
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
	_position.add(x);
	_position.add(y);
    }

    /**
     * The getter method for the object's x position.
     * @return The object's x position.
     */
    public float getPositionX()
    {
	return _position.get(0);
    }

    /**
     * The getter method for the object's y position.
     * @return The object's y position
     */
    public float getPositionY()
    {
	return _position.get(1);
    }
    
    /**
     * Getter method for the object's position vector.
     * @return The object's position vector.
     */
    public Vector<Float> getPosition()
    {
	return _position;
    }

    /**
     * The setter method for the object's rotation.
     * @param rotation The rotation of the object.
     * @precond rotation > 0
     * @postcond _rotation == rotation
     */
    public void setRotation(float rotationX, float rotationY, float rotationZ)
    {
	_rotation.add(rotationX);
	_rotation.add(rotationY);
	_rotation.add(rotationZ);
    }

    /**
     * Getter method for the object's X rotation.
     * @return The object's X rotation.
     */
    public float getRotationX()
    {
	return _rotation.get(0);
    }

    /**
     * Getter method for the object's Y rotation.
     * @return The object's Y rotation.
     */
    public float getRotationY()
    {
	return _rotation.get(1);
    }

    /**
     * Getter method for the object's Z rotation.
     * @return The object's Z rotation.
     */
    public float getRotationZ()
    {
	return _rotation.get(2);
    }
    
    /**
     * Getter method for the object's rotation Vector.
     * @return The object's rotation Vector.
     */
    public Vector<Float> getRotation()
    {
	return _rotation;
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
	return ("\nType: " + getType() + 
		"\nPosition: " + getPositionX() + " x " + getPositionY() + 
		"\nRotation: (" + getRotationX() + ", " + getRotationY() + ", " + getRotationZ() + ")");
    }
}
