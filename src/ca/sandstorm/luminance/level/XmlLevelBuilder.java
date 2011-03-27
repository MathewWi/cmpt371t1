package ca.sandstorm.luminance.level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;


/**
 * Class for building an XML file from an XmlLevel.
 * @author Steven Indzeoski
 *
 */
public class XmlLevelBuilder
{
    private DocumentBuilder _builder;
    private Document _doc;
    
    /**
     * Constructor method for XmlLevelBuilder.
     * @throws ParserConfigurationException
     */
    public XmlLevelBuilder() throws ParserConfigurationException
    {
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	_builder = factory.newDocumentBuilder();
    }
    
    /**
     * Creates the Document object from an XmlLevel.
     * @param level The XmlLevel to convert.
     * @return A DOM Document object.
     */
    public Document build(XmlLevel level)
    {
	_doc = _builder.newDocument();
	_doc.appendChild(createLevel(level));
	return _doc;
    } 
    
    /**
     * Saves the converted level to a file.
     * @param filename The file to save to.
     */
    public void saveToFile(String filename)
    {
	// TODO
    }
    
    /**
     * Helper method for writing the level into the Document.
     * @param level The XmlLevel to be written.
     * @return An Element object for <level>
     */
    private Element createLevel(XmlLevel level)
    {
	Element e = _doc.createElement("level");
	e.appendChild(createTextElement("name", level.getName()));
	e.appendChild(createTextElement("difficulty", level.getDifficulty()));
	e.appendChild(createGridSize(level.getXSize(), level.getYSize(), level.getWidth(), level.getHeight()));
	for (XmlLevelObject levelObject : level.getObjects())
	{
	    e.appendChild(createObject(levelObject));
	}
	
	for (XmlLevelTool levelTool : level.getTools())
	{
	    e.appendChild(createTool(levelTool));
	}
	return e;
    }
    
    /**
     * Helper method for writing the grid size into the Document.
     * @param sizeX The size of the x dimension of the grid.
     * @param sizeY The size of the y dimension of the grid.
     * @param width The width of the grid.
     * @param height The height of the grid.
     * @return An Element object for <grid_size>.
     */
    private Element createGridSize(int sizeX, int sizeY, float width, float height)
    {
	Element e = _doc.createElement("grid_size");
	e.appendChild(createTextElement("x", String.valueOf(sizeX)));
	e.appendChild(createTextElement("y", String.valueOf(sizeY)));
	e.appendChild(createTextElement("width", String.valueOf(width)));
	e.appendChild(createTextElement("height", String.valueOf(height)));
	return e;
    }
    
    /**
     * Helper method for writing each object into the Document.
     * @param object The XmlLevelObject to be written.
     * @return An Element object for an <object>.
     */
    private Element createObject(XmlLevelObject object)
    {
	Element e = _doc.createElement("object");
	e.appendChild(createTextElement("type", object.getType()));
	if (object.getType().equals(XmlLevelGoal.getId()))
	{
	    switch (((XmlLevelGoal)object).getColour())
	    {
		case Color.WHITE:
		    e.appendChild(createTextElement("colour", "white"));
		    break;
		case Color.RED:
		    e.appendChild(createTextElement("colour", "red"));
		    break;
		case Color.GREEN:
		    e.appendChild(createTextElement("colour", "green"));
		    break;
		case Color.BLUE:
		    e.appendChild(createTextElement("colour", "blue"));
		    break;
		default:
		    throw new IllegalArgumentException("Goal has an invalid colour.");
	    }
	}
	else if (object.getType().equals(XmlLevelEmitter.getId()))
	{
	    switch (((XmlLevelEmitter)object).getColour())
	    {
		case Color.WHITE:
		    e.appendChild(createTextElement("colour", "white"));
		    break;
		case Color.RED:
		    e.appendChild(createTextElement("colour", "red"));
		    break;
		case Color.GREEN:
		    e.appendChild(createTextElement("colour", "green"));
		    break;
		case Color.BLUE:
		    e.appendChild(createTextElement("colour", "blue"));
		    break;
		default:
		    throw new IllegalArgumentException("Emitter has an invalid colour.");
	    }
	}
	e.appendChild(createObjectPosition(object.getPositionX(), object.getPositionY()));
	e.appendChild(createObjectRotation(object.getRotationX(), object.getRotationY(), object.getRotationZ()));
	return e;
    }
    
    /**
     * Helper method for writing the object position into the Document.
     * @param positionX The x grid position of the object.
     * @param positionY The y grid position of the object.
     * @return An Element object for <position> of an object.
     */
    private Element createObjectPosition(float positionX, float positionY)
    {
	Element e = _doc.createElement("position");
	e.appendChild(createTextElement("x", String.valueOf(positionX)));
	e.appendChild(createTextElement("y", String.valueOf(positionY)));
	return e;
    }
    
    /**
     * Helper method for writing the object rotation into the Document.
     * @param rotationX The x rotation of the object.
     * @param rotationY The y rotation of the object.
     * @param rotationZ The z rotation of the object.
     * @return An Element object for <rotation> of an object.
     */
    private Element createObjectRotation(float rotationX, float rotationY, float rotationZ)
    {
	Element e = _doc.createElement("rotation");
	e.appendChild(createTextElement("x", String.valueOf(rotationX)));
	e.appendChild(createTextElement("y", String.valueOf(rotationY)));
	e.appendChild(createTextElement("z", String.valueOf(rotationZ)));
	return e;
    }
    
    /**
     * Helper method for writing the XmlLevelTools into the Document.
     * @param tool The XmlLevelTool to be written.
     * @return An Element object for the <tool>.
     */
    private Element createTool(XmlLevelTool tool)
    {
	Element e = _doc.createElement("tool");
	e.appendChild(createTextElement("type", tool.getType()));
	e.appendChild(createTextElement("count", String.valueOf(tool.getCount())));
	return e;
    }

    /**
     * Helper method for writing a just text Element.
     * @param name The name of the element to be written.
     * @param text The body of the element to be written.
     * @return An Element object.
     */
    private Element createTextElement(String name, String text)
    {
	Text t = _doc.createTextNode(text);
	Element e = _doc.createElement(name);
	e.appendChild(t);
	return e;
    }
}
