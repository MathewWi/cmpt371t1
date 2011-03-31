package ca.sandstorm.luminance.level;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for storing all information about a level, loaded from XML.
 * @author Steven Indzeoski
 *
 */
public class XmlLevel 
{
    private static final Logger _logger = LoggerFactory
    .getLogger(XmlLevel.class);	

    private String _name;
    private String _difficulty;
    private int _xSize;
    private int _ySize;
    private float _width;
    private float _height;
    private LinkedList<XmlLevelObject> _objects;
    private LinkedList<XmlLevelTool> _tools;

    /**
     * Constructor for the XmlLevel object.
     * @param name The name of the level.
     * @param difficulty The difficulty of the level (easy, medium, hard, etc.)
     * @param xSize The horizontal size of the grid.
     * @param ySize The vertical size of the grid.
     * @param objects A LinkedList of XmlLevelObjects in the level.
     * @param tools A LinkedList of XmlLevelTools in the level.
     * @precond name != null, difficulty != null, xSize > 0, ySize > 0, objects != null, tools != null
     * @postcond An XmlLevel is created
     */
    public XmlLevel(String name, String difficulty, int xSize, int ySize, float width, float height, LinkedList<XmlLevelObject> objects, LinkedList<XmlLevelTool> tools)
    {
	_logger.debug("XmlLevel(" + name + ", " + difficulty + ", " + xSize + ", " + ySize + ", " + objects + ", " + tools + ")");

	_name = name;
	_difficulty = difficulty;
	_xSize = xSize;
	_ySize = ySize;
	_width = width;
	_height = height;
	_objects = objects;
	_tools = tools;
    }

    /**
     * Getter method for the name of the level.
     * @return The name of the level.
     */
    public String getName()
    {
	return _name;
    }

    /**
     * Getter method for the difficulty of the level.
     * @return The difficulty of the level.
     */
    public String getDifficulty()
    {
	return _difficulty;
    }

    /**
     * Getter method for the horizontal size of the level's grid.
     * @return The horizontal size of the level's grid.
     */
    public int getXSize()
    {
	return _xSize;
    }

    /**
     * Getter method for the vertical size of the level's grid.
     * @return The vertical size of the level's grid.
     */
    public int getYSize()
    {
	return _ySize;
    }

    /**
     * Getter method for the width of the level.
     * @return The width of the level.
     */
    public float getWidth()
    {
	return _width;
    }

    /**
     * Getter method for the height of the level.
     * @return
     */
    public float getHeight()
    {
	return _height;
    }

    /**
     * Getter method for the LinkedList of level objects.
     * @return A LinkedList of the level's objects.
     */
    public LinkedList<XmlLevelObject> getObjects()
    {
	return _objects;
    }

    /**
     * Getter method for the LinkedList of level tools.
     * @return A LinkedList of the level's tools.
     */
    public LinkedList<XmlLevelTool> getTools()
    {
	return _tools;
    }

    /**
     * Returns a string representation of the XmlLevel.
     */
    public String toString()
    {
	return("Name: " + getName() + 
		"\nDifficulty: " + getDifficulty() + 
		"\nGrid Size: " + getXSize() + " x " + getYSize() + 
		"\nObjects: " + getObjects().toString() + 
		"\nTools: " + getTools().toString());
    }
}
