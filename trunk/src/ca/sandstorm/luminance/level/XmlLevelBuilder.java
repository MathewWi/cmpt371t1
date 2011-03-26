package ca.sandstorm.luminance.level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import android.graphics.Color;

public class XmlLevelBuilder
{
    private DocumentBuilder _builder;
    private Document _doc;
    
    public XmlLevelBuilder() throws ParserConfigurationException
    {
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	_builder = factory.newDocumentBuilder();
    }
    
    public Document build(XmlLevel level)
    {
	_doc = _builder.newDocument();
	_doc.appendChild(createLevel(level));
	return _doc;
    } 
    
    public void saveToFile(String filename)
    {
	// TODO
    }
    
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
    
    private Element createGridSize(int sizeX, int sizeY, float width, float height)
    {
	Element e = _doc.createElement("grid_size");
	e.appendChild(createTextElement("x", String.valueOf(sizeX)));
	e.appendChild(createTextElement("y", String.valueOf(sizeY)));
	e.appendChild(createTextElement("width", String.valueOf(width)));
	e.appendChild(createTextElement("height", String.valueOf(height)));
	return e;
    }
    
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
    
    private Element createObjectPosition(float positionX, float positionY)
    {
	Element e = _doc.createElement("position");
	e.appendChild(createTextElement("x", String.valueOf(positionX)));
	e.appendChild(createTextElement("y", String.valueOf(positionY)));
	return e;
    }
    
    private Element createObjectRotation(float rotationX, float rotationY, float rotationZ)
    {
	Element e = _doc.createElement("rotation");
	e.appendChild(createTextElement("x", String.valueOf(rotationX)));
	e.appendChild(createTextElement("y", String.valueOf(rotationY)));
	e.appendChild(createTextElement("z", String.valueOf(rotationZ)));
	return e;
    }
    
    private Element createTool(XmlLevelTool tool)
    {
	Element e = _doc.createElement("tool");
	e.appendChild(createTextElement("type", tool.getType()));
	e.appendChild(createTextElement("count", String.valueOf(tool.getCount())));
	return e;
    }

    private Element createTextElement(String name, String text)
    {
	Text t = _doc.createTextNode(text);
	Element e = _doc.createElement(name);
	e.appendChild(t);
	return e;
    }
}
