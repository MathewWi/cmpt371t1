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
    private Vector<Float> _rotation;

    /**
     * Constructor for XmlLevelObject.
     * @param type The type of the object.
     * @param position A vector containing position of the object (x, y).
     * @param rotation A vector containing rotation of the object (x, y, z).
     * @throws IllegalArgumentException
     * @precond type is brick or goal.
     * @precond position has 2 elements.
     * @precond rotation has 3 elements.
     * @precond x and y of position are positive.
     * @postcond XmlLevelObject is created with given type.
     */
    public XmlLevelObject(String type, Vector<Float> position, Vector<Float> rotation) throws IllegalArgumentException 
    {
	_logger.debug("XmlLevelObject(" + type + ", " + position.toString() + ", " + rotation.toString() + ")");
	if (!isValidType(type)) {
	    throw new IllegalArgumentException("The type given is invalid. Type given was " + type);
	}
	if (position.size() != 2) {
	    throw new IllegalArgumentException("Position must be a vector with 2 elements. Position given had size " + position.size());
	}
	if (rotation.size() != 3) {
	    throw new IllegalArgumentException("Rotation must be a vector with 3 elements. Rotation given had size " + rotation.size());
	}
	if (position.get(0) < 0) {
	    throw new IllegalArgumentException("X position can't be negative. It is " + position.get(0));
	}
	if (position.get(1) < 0) {
	    throw new IllegalArgumentException("Y position can't be negative. It is " + position.get(1));
	}
	_type = type;
	_position = position;
	_rotation = rotation;
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
        
    /**
     * Returns the colour of the object as an integer colour code.
     * @return The colour of the object as an integer colour code
     */
    public abstract int getColour();
    
    /**
     * Returns the colour of the object as a string.
     * @return The colour of the object as a string.
     */
    public abstract String getColourAsString();
    
    /**
     * Return a deep copy of the XmlLevelObject.
     * @return A deep copy of the XmlLevelObject.
     */
    public abstract XmlLevelObject deepCopy();

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
