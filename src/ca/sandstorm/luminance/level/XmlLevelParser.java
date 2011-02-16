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
    		for (int i = 0; i < objectNodeList.getLength(); i++) 
    		{
    			Node node = objectNodeList.item(i);
    			
    			if (node.getNodeType() == Node.ELEMENT_NODE)
    			{
    				Element element = (Element)node;
    				
    				// Get the type
    				NodeList typeNodeList = element.getElementsByTagName("type");
    				Element typeElement = (Element)typeNodeList.item(0);
    				NodeList type = typeElement.getChildNodes();
    				String typeString = ((Node)type.item(0)).getNodeValue();
    				System.out.println("Type: " + typeString);
    				
    				// Get the colour if object is a goal
    				String colourString = null;
    				if (typeString.equals("goal"))
    				{
    					NodeList colourNodeList = element.getElementsByTagName("colour");
    					Element colourElement = (Element)colourNodeList.item(0);
    					NodeList colour = colourElement.getChildNodes();
    					colourString = ((Node)colour.item(0)).getNodeValue();
    					System.out.println("Colour: " + colourString);
    				}
    				
    				// Get the position
    				NodeList positionNodeList = element.getElementsByTagName("position");
    				Element positionElement = (Element)positionNodeList.item(0);
    				
    				// Get the x position
    				NodeList objectXNodeList = positionElement.getElementsByTagName("x");
    				Element objectXElement = (Element)objectXNodeList.item(0);
    				NodeList objectX = objectXElement.getChildNodes();
    				String objectXString = ((Node)objectX.item(0)).getNodeValue();
    				System.out.println("Object x: " + objectXString);
    				float objectXFloat = 0;
    				try 
    				{
    					objectXFloat = Float.valueOf(objectXString.trim()).floatValue();
    				}
    				catch (NumberFormatException e)
    				{
    					e.printStackTrace();
    				}
    				
    				// Get the y position
    				NodeList objectYNodeList = positionElement.getElementsByTagName("y");
    				Element objectYElement = (Element)objectYNodeList.item(0);
    				NodeList objectY = objectYElement.getChildNodes();
    				String objectYString = ((Node)objectY.item(0)).getNodeValue();
    				System.out.println("Object y: " + objectYString);
    				float objectYFloat = 0;
    				try 
    				{
    					objectYFloat = Float.valueOf(objectYString.trim()).floatValue();
    				}
    				catch (NumberFormatException e)
    				{
    					e.printStackTrace();
    				}
    				
    				// Get the rotation
    				NodeList rotationNodeList = element.getElementsByTagName("rotation");
    				Element rotationElement = (Element)rotationNodeList.item(0);
    				NodeList rotation = rotationElement.getChildNodes();
    				String rotationString = ((Node)rotation.item(0)).getNodeValue();
    				System.out.println("Object rotation: " + rotationString);
    				float rotationFloat = 0;
    				try
    				{
    					rotationFloat = Float.valueOf(rotationString.trim()).floatValue();
    				}
    				catch (NumberFormatException e)
    				{
    					e.printStackTrace();
    				}
    				
    				// Create the object for the level
    				XmlLevelObject xmlLevelObject = new XmlLevelObject(typeString);
    				if (xmlLevelObject.getType().equals("goal"))
    				{
    					xmlLevelObject.setColour(colourString);
    				}
    				xmlLevelObject.setPosition(objectXFloat, objectYFloat);
    				xmlLevelObject.setRotation(rotationFloat);
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
