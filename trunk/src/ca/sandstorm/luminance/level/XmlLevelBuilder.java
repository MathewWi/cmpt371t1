package ca.sandstorm.luminance.level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

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
	// TODO
	return null;
    } 
    
    private Element createLevel(XmlLevel level)
    {
	// TODO
	return null;
    }
    
    private Element createBrick(XmlLevelBrick brick)
    {
	// TODO
	return null;
    }
    
    private Element createEmitter(XmlLevelEmitter emitter)
    {
	// TODO
	return null;
    }
    
    private Element createGoal(XmlLevelGoal goal)
    {
	// TODO
	return null;
    }
    
    private Element createMirror(XmlLevelMirror mirror)
    {
	// TODO
	return null;
    }
    
    private Element createObject(XmlLevelObject object)
    {
	Element e = _doc.createElement("object");
	// TODO
	return null;
    }
    
    private Element createPrism(XmlLevelPrism prism)
    {
	// TODO
	return null;
    }
    
    private Element createTool(XmlLevelTool tool)
    {
	// TODO
	return null;
    }

    private Element createTextElement(String name, String text)
    {
	Text t = _doc.createTextNode(text);
	Element e = _doc.createElement(name);
	e.appendChild(t);
	return e;
    }
}
