package ca.sandstorm.luminance.level;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;


/**
 * Class for building an XML file from an XmlLevel.
 * @author Steven Indzeoski
 *
 */
public class XmlLevelBuilder
{
    private XmlSerializer _serializer;
    private StringWriter _writer;
    
    /**
     * Constructor method for XmlLevelBuilder.
     */
    public XmlLevelBuilder()
    {
	_serializer = Xml.newSerializer();
	_writer = new StringWriter();
    }
    
    /**
     * Method for building an XmlLevel into a format suitable for outputting to a file.
     * @param level The XmlLevel to convert.
     * @throws RuntimeException
     * @throws FileNotFoundException 
     */
    public void build(XmlLevel level, String filename) throws RuntimeException, FileNotFoundException
    {
	try {
	    _serializer.setOutput(_writer);
	    _serializer.startDocument("UTF-8", true);
	    createLevel(level);
	    _serializer.endDocument();
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
	
	PrintWriter out = new PrintWriter(filename);
	out.println(_writer.toString());
	out.close();
    } 
    
    /**
     * Helper method for writing the level.
     * @param level The XmlLevel to be written.
     * @throws Exception
     */
    private void createLevel(XmlLevel level) throws Exception
    {
	// Start the level tag.
	_serializer.startTag("", "level");
	
	// Write the name.
	_serializer.startTag("", "name");
	_serializer.text(level.getName());
	_serializer.endTag("", "name");
	
	// Write the difficulty.
	_serializer.startTag("", "difficulty");
	_serializer.text(level.getDifficulty());
	_serializer.endTag("", "difficulty");
	
	// Write the grid size.
	createGridSize(level.getXSize(), level.getYSize(), level.getWidth(), level.getHeight());
	
	// Write the level objects.
	for (XmlLevelObject levelObject : level.getObjects()) {
	    createObject(levelObject);
	}
	
	// Write the level tools.
	for (XmlLevelTool levelTool : level.getTools()) {
	    createTool(levelTool);
	}
	
	// End the level tag.
	_serializer.endTag("", "level");
    }
    
    /**
     * Helper method for writing the grid size.
     * @param sizeX The x size of the grid.
     * @param sizeY The y size of the grid.
     * @param width The width of the grid.
     * @param height The height of the grid.
     * @throws Exception
     */
    private void createGridSize(int sizeX, int sizeY, float width, float height) throws Exception
    {
	// Start grid_size tag.
	_serializer.startTag("", "grid_size");
	
	// Write the x.
	_serializer.startTag("", "x");
	_serializer.text(String.valueOf(sizeX));
	_serializer.endTag("", "x");
	
	// Write the y.
	_serializer.startTag("", "y");
	_serializer.text(String.valueOf(sizeY));
	_serializer.endTag("", "y");
	
	// Write the width.
	_serializer.startTag("", "width");
	_serializer.text(String.valueOf(width));
	_serializer.endTag("", "width");
	
	// Write the height.
	_serializer.startTag("", "height");
	_serializer.text(String.valueOf(height));
	_serializer.endTag("", "height");
	
	// End grid_size tag.
	_serializer.endTag("", "grid_size");
    }
    
    /**
     * Helper method for writing an object.
     * @param object The XmlLevelObject to be written.
     * @throws Exception
     */
    private void createObject(XmlLevelObject object) throws Exception
    {
	// Start object tag.
	_serializer.startTag("", "object");
	
	// Write type tag.
	_serializer.startTag("", "type");
	_serializer.text(object.getType());
	_serializer.endTag("", "type");
	
	if (object.isColouredObject()) {
	    // Write colour tag.
	    _serializer.startTag("", "colour");
	    _serializer.text(object.getColourAsString());
	    _serializer.endTag("", "colour");
	}
	
	// Write object position.
	createObjectPosition(object.getPositionX(), object.getPositionY());
	
	// Write object rotation.
	createObjectRotation(object.getRotationX(), object.getRotationY(), object.getRotationZ());
	
	// End object tag.
	_serializer.endTag("", "object");
    }
    
    
    
    /**
     * Helper method for writing object position
     * @param positionX The x position of the object.
     * @param positionY The y position of the object.
     * @throws Exception
     */
    private void createObjectPosition(float positionX, float positionY) throws Exception
    {
	// Start position tag.
	_serializer.startTag("", "position");
	
	// Write x tag.
	_serializer.startTag("", "x");
	_serializer.text(String.valueOf(positionX));
	_serializer.endTag("", "x");
	
	// Write y tag.
	_serializer.startTag("", "y");
	_serializer.text(String.valueOf(positionY));
	_serializer.endTag("", "y");
	
	// End position tag.
	_serializer.endTag("", "position");
    }
    
    /**
     * Helper method for writing the object rotation.
     * @param rotationX The x rotation of the object.
     * @param rotationY The y rotation of the object.
     * @param rotationZ The z rotation of the object.
     * @throws Exception
     */
    private void createObjectRotation(float rotationX, float rotationY, float rotationZ) throws Exception
    {
	// Start rotation tag.
	_serializer.startTag("", "rotation");
	
	// Write x tag.
	_serializer.startTag("", "x");
	_serializer.text(String.valueOf(rotationX));
	_serializer.endTag("", "x");
	
	// Write y tag.
	_serializer.startTag("", "y");
	_serializer.text(String.valueOf(rotationY));
	_serializer.endTag("", "y");
	
	// Write z tag.
	_serializer.startTag("", "z");
	_serializer.text(String.valueOf(rotationZ));
	_serializer.endTag("", "z");
	
	// End rotation tag.
	_serializer.endTag("", "rotation");
    }
    
    /**
     * Helper method for writing the tools.
     * @param tool The XmlLevelTool to be written.
     * @throws Exception
     */
    private void createTool(XmlLevelTool tool) throws Exception
    {
	// Start tool tag.
	_serializer.startTag("", "tool");
	
	// Write type element.
	_serializer.startTag("", "type");
	_serializer.text(tool.getType());
	_serializer.endTag("", "type");
	
	// Write count element.
	_serializer.startTag("", "count");
	_serializer.text(String.valueOf(tool.getCount()));
	_serializer.endTag("", "count");
	
	// End tool tag.
	_serializer.endTag("", "tool");
    }
}
