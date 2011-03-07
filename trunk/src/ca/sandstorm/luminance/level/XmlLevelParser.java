package ca.sandstorm.luminance.level;

import java.io.InputStream;
import java.util.LinkedList;

import javax.xml.parsers.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;


/**
 * Class for parsing an XML level file.
 * @author Steven Indzeoski
 * 
 */
public class XmlLevelParser
{
	private InputStream _file;
	
	private static final Logger _logger = LoggerFactory
	    .getLogger(XmlLevelParser.class);	

	/**
	 * Constructor for the XmlParser class.
	 * @param filename The name of a file in the same directory.
	 * @precond filename != null, filename is a file in the project directory.
	 * @postcond XmlLevelParser has been created
	 */
	public XmlLevelParser(InputStream file)
	{
	    _logger.debug("XmlLevelParser(" + file + ")");
	    
	    _file = file;
	}


	
	/**
	 * Parse the level file.
	 * @precond XmlLevelParser was successfully created
	 * @postcond An XmlLevel is created from information parsed from the XML file
	 * @return An XmlLevel object that contains all information about the level.
	 */
	public XmlLevel parse()
	{
	    _logger.debug("parse()");
	    
		try
		{
			//File file = new File(getFilename());
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(_file);
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

			// Get the difficulty.
			Element difficultyElement = (Element)doc.getElementsByTagName("difficulty").item(0);
			String difficulty = ((Node)difficultyElement.getChildNodes().item(0)).getNodeValue();

			// Get the dimensions of the level.
			Element gridElement = (Element)doc.getElementsByTagName("grid_size").item(0);
			Element xElement = (Element)gridElement.getElementsByTagName("x").item(0);
			String x = ((Node)xElement.getChildNodes().item(0)).getNodeValue();
			int xString = Integer.parseInt(x);
			Element yElement = (Element)gridElement.getElementsByTagName("y").item(0);
			String y = ((Node)yElement.getChildNodes().item(0)).getNodeValue();
			int yString = Integer.parseInt(y);
			
			Element wElement = (Element)gridElement.getElementsByTagName("width").item(0);
			String w = ((Node)wElement.getChildNodes().item(0)).getNodeValue();
			int iW = Integer.parseInt(w);
			
			Element hElement = (Element)gridElement.getElementsByTagName("height").item(0);
			String h = ((Node)hElement.getChildNodes().item(0)).getNodeValue();
			int iH = Integer.parseInt(h);		
			

			// Get level objects.
			LinkedList<XmlLevelObject> objectList = new LinkedList<XmlLevelObject>();
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

					// Get the colour if object is a goal
					String colourString = null;
					if (typeString.equals(XmlLevelGoal.getId()))
					{
						NodeList colourNodeList = element.getElementsByTagName("colour");
						Element colourElement = (Element)colourNodeList.item(0);
						NodeList colour = colourElement.getChildNodes();
						colourString = ((Node)colour.item(0)).getNodeValue();
						//System.out.println("Colour: " + colourString);
					}

					// Get the position
					NodeList positionNodeList = element.getElementsByTagName("position");
					Element positionElement = (Element)positionNodeList.item(0);

					// Get the x position
					NodeList objectXNodeList = positionElement.getElementsByTagName("x");
					Element objectXElement = (Element)objectXNodeList.item(0);
					NodeList objectX = objectXElement.getChildNodes();
					String objectXString = ((Node)objectX.item(0)).getNodeValue();
					//System.out.println("Object x: " + objectXString);
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
					//System.out.println("Object y: " + objectYString);
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
					
					// Get the x rotation
					NodeList objectXRotationNodeList = rotationElement.getElementsByTagName("x");
					Element objectXRotationElement = (Element)objectXRotationNodeList.item(0);
					NodeList objectXRotation = objectXRotationElement.getChildNodes();
					String objectXRotationString = ((Node)objectXRotation.item(0)).getNodeValue();
					float objectXRotationFloat = Float.parseFloat(objectXRotationString);
					
					// Get the y rotation
					NodeList objectYRotationNodeList = rotationElement.getElementsByTagName("y");
					Element objectYRotationElement = (Element)objectYRotationNodeList.item(0);
					NodeList objectYRotation = objectYRotationElement.getChildNodes();
					String objectYRotationString = ((Node)objectYRotation.item(0)).getNodeValue();
					float objectYRotationFloat = Float.parseFloat(objectYRotationString);
					
					// Get the z rotation
					NodeList objectZRotationNodeList = rotationElement.getElementsByTagName("z");
					Element objectZRotationElement = (Element)objectZRotationNodeList.item(0);
					NodeList objectZRotation = objectZRotationElement.getChildNodes();
					String objectZRotationString = ((Node)objectZRotation.item(0)).getNodeValue();
					float objectZRotationFloat = Float.parseFloat(objectZRotationString);

					// Create the object for the level
					XmlLevelObject xmlLevelObject = null;
					if (typeString.equals(XmlLevelBrick.getId()))
					{
						xmlLevelObject = new XmlLevelBrick();
					}
					else if (typeString.equals(XmlLevelGoal.getId()))
					{
						xmlLevelObject = new XmlLevelGoal(colourString);
					}
					xmlLevelObject.setPosition(objectXFloat, objectYFloat);
					xmlLevelObject.setRotation(objectXRotationFloat, objectYRotationFloat, objectZRotationFloat);

					// Add the object to a linked list of objects
					objectList.add(xmlLevelObject);
				}
			} // end of objects for loop
			
			// Get tools
			NodeList toolNodeList = doc.getElementsByTagName("tool");
			LinkedList<XmlLevelTool> toolList = new LinkedList<XmlLevelTool>();
			for (int i = 0; i < toolNodeList.getLength(); i++)
			{
				Node node = toolNodeList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element element = (Element)node;
					
					// Get the type
					NodeList typeNodeList = element.getElementsByTagName("type");
					Element typeElement = (Element)typeNodeList.item(0);
					NodeList type = typeElement.getChildNodes();
					String typeString = ((Node)type.item(0)).getNodeValue();
					
					// Get the count
					NodeList countNodeList = element.getElementsByTagName("count");
					Element countElement = (Element)countNodeList.item(0);
					NodeList count = countElement.getChildNodes();
					String countString = ((Node)count.item(0)).getNodeValue();
					int countInt = Integer.parseInt(countString);
					
					// Create the XmlLevelTool and add to list.
					XmlLevelTool xmlLevelTool = null;
					if (typeString.equals(XmlLevelMirror.getId()))
					{
						xmlLevelTool = new XmlLevelMirror(countInt);
					}
					else if (typeString.equals(XmlLevelPrism.getId()))
					{
						xmlLevelTool = new XmlLevelPrism(countInt);
					}
					toolList.add(xmlLevelTool);
				}
			}
			
			// Create and return level object.
			XmlLevel xmlLevel = new XmlLevel(name, difficulty, xString, yString, iW, iH, objectList, toolList);	
			return xmlLevel;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}
