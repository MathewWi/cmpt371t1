package ca.sandstorm.luminance.level;

import java.io.File;
import java.util.LinkedList;

import javax.xml.parsers.*;

import org.w3c.dom.*;

public class XmlLevelParser
{
    private String _filename;
    
    /**
     * Constructor for the XmlParser class.
     * @param filename The name of a file in the same directory.
     */
    public XmlLevelParser(String filename)
    {
	_filename = filename;
    }
    
    /**
     * Accessor method for the filename of the file being parsed.
     * @return The filename of the file being parsed.
     */
    public String getFilename()
    {
	return _filename;
    }
    
    public void parse()
    {
    	try
    	{
    		File file = new File(getFilename());
    		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    		DocumentBuilder db = dbf.newDocumentBuilder();
    		Document doc = db.parse(file);
    		doc.getDocumentElement().normalize();
    		
    		// Check that root element is "level"
    		String rootElement = doc.getDocumentElement().getNodeName();
    		if (!rootElement.equals("level"))
    		{
    			throw new Exception("The XML file is not a level.");
    		}
    		
    		// Get the level name.
    		Element nameElement = (Element)doc.getElementsByTagName("name").item(0);
    		String name = ((Node)nameElement.getChildNodes().item(0)).getNodeValue();
    		System.out.println("Name: " + name);
    		
    		// Get the difficulty.
    		Element difficultyElement = (Element)doc.getElementsByTagName("difficulty").item(0);
    		String difficulty = ((Node)difficultyElement.getChildNodes().item(0)).getNodeValue();
    		System.out.println("Difficulty: " + difficulty);
    		
    		// Get the dimensions of the level.
    		Element gridElement = (Element)doc.getElementsByTagName("grid_size").item(0);
    		Element xElement = (Element)gridElement.getElementsByTagName("x").item(0);
    		String x = ((Node)xElement.getChildNodes().item(0)).getNodeValue();
    		System.out.println("x: " + x);
    		Element yElement = (Element)gridElement.getElementsByTagName("y").item(0);
    		String y = ((Node)yElement.getChildNodes().item(0)).getNodeValue();
    		System.out.println("y: " + y);
    		
    		// Get level objects.
    		NodeList objectNodeList = doc.getElementsByTagName("object");
    		LinkedList<XmlLevelObject> objectList = new LinkedList<XmlLevelObject>();
    		XmlLevelObject levelObject;
    		Node objectNode;
    		for (int i = 0; i < objectNodeList.getLength(); i++)
    		{
    			objectNode = objectNodeList.item(i);
    			if (objectNode.getNodeType() == Node.ELEMENT_NODE)
    			{
    				Element objectElement = (Element)objectNode;
    				Element typeElement = (Element)doc.getElementsByTagName("type").item(0);
    				String type = ((Node)typeElement.getChildNodes().item(0)).getNodeValue();
    				System.out.println("type: " + type);
    			}
    		}
    	}
    	catch (Exception e) 
    	{
    		e.printStackTrace();
    	}
    }
    
    public static void main(String args[])
    {
    	XmlLevelParser parser = new XmlLevelParser("template.xml");
    	parser.parse();
    }
}
