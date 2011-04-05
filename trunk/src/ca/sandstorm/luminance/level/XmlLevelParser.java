package ca.sandstorm.luminance.level;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
     * @postcond An XmlLevel is successfully created.
     * @return An XmlLevel object that contains all information about the level.
     * @throws IOException
     */
    public XmlLevel parse() throws IOException
    {	
	try {
	    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(_file);
		document.getDocumentElement().normalize();

		String levelName = getLevelName(document);
		String levelDifficulty = getLevelDifficulty(document);
		int levelXSize = getLevelXSize(document);
		int levelYSize = getLevelYSize(document);
		float levelWidth = getLevelWidth(document);
		float levelHeight = getLevelHeight(document);
		LinkedList<XmlLevelObject> levelObjects = getLevelObjects(document);
		LinkedList<XmlLevelTool> levelTools = getLevelTools(document);
		return new XmlLevel(levelName, levelDifficulty, levelXSize, levelYSize, 
		                    levelWidth, levelHeight, levelObjects, levelTools);
	} catch (Exception e) {
	    throw new IOException(e.getMessage());
	}
    }


    /**
     * Helper method for parsing the level tools.
     * @param document A Document object of the level.
     * @return A LinkedList of the XmlLevelTools in the level.
     */
    private LinkedList<XmlLevelTool> getLevelTools(Document document)
    {
	LinkedList<XmlLevelTool> toolList = new LinkedList<XmlLevelTool>();
	NodeList toolNodeList = document.getElementsByTagName("tool");
	for (int i = 0; i < toolNodeList.getLength(); i++) {
	    Node toolNode = toolNodeList.item(i);
	    XmlLevelTool xmlLevelTool = getLevelTool(toolNode);
	    toolList.add(xmlLevelTool);
	}
	return toolList;
    }


    /**
     * Helper method for parsing a level tool.
     * @param toolNode A Node object containing the XML of a tool.
     * @return An XmlLevelTool containing the type and the count.
     */
    private XmlLevelTool getLevelTool(Node toolNode)
    {
	if (toolNode.getNodeType() == Node.ELEMENT_NODE) {
	    Element element = (Element)toolNode;
	    String toolType = getToolType(element);
	    int toolCount = getToolCount(element);	

	    // Create the XmlLevelTool and add to list.
	    XmlLevelTool xmlLevelTool = null;
	    if (toolType.equals(XmlLevelMirror.getId())) {
		xmlLevelTool = new XmlLevelMirror(toolCount);
	    }
	    else if (toolType.equals(XmlLevelPrism.getId())) {
		xmlLevelTool = new XmlLevelPrism(toolCount);
	    }
	    else {
		throw new RuntimeException("Level file parsing failed. Invalid tool type \"" + toolType + "\"");
	    }
	    return xmlLevelTool;
	}
	else {
	    throw new RuntimeException("Something was wrong with the XML file.");
	}
    }


    /**
     * Helper method for parsing the count for a tool.
     * @param element An Element object of the tool.
     * @return How many of the tool the user gets for the level.
     */
    private int getToolCount(Element element)
    {
	NodeList countNodeList = element.getElementsByTagName("count");
	Element countElement = (Element)countNodeList.item(0);
	NodeList count = countElement.getChildNodes();
	String countString = ((Node)count.item(0)).getNodeValue();
	return Integer.parseInt(countString);
    }


    /**
     * Helper method for parsing the type of a tool.
     * @param element An Element object of the tool.
     * @return The type of the tool.
     */
    private String getToolType(Element element)
    {
	NodeList typeNodeList = element.getElementsByTagName("type");
	Element typeElement = (Element)typeNodeList.item(0);
	NodeList type = typeElement.getChildNodes();
	return ((Node)type.item(0)).getNodeValue();
    }


    /**
     * Helper method for parsing the objects in a level.
     * @param document A Document object of the level.
     * @return A LinkedList of the XmlLevelObjects for the level.
     */
    private LinkedList<XmlLevelObject> getLevelObjects(Document document)
    {
	LinkedList<XmlLevelObject> objectList = new LinkedList<XmlLevelObject>();
	NodeList objectNodeList = document.getElementsByTagName("object");
	for (int i = 0; i < objectNodeList.getLength(); i++)  {
	    Node objectNode = objectNodeList.item(i);
	    XmlLevelObject xmlLevelObject = getLevelObject(objectNode);
	    objectList.add(xmlLevelObject);
	}
	return objectList;
    }

    /**
     * Helper method for parsing an object for a level.
     * @param objectNode A Node object containing the XML for an object.
     * @return An XmlLevelObject that was parsed from the XML.
     */
    private XmlLevelObject getLevelObject(Node objectNode)
    {
	if (objectNode.getNodeType() == Node.ELEMENT_NODE) {
	    Element objectElement = (Element)objectNode;
	    String objectType = getObjectType(objectElement);
	    String objectColour = getObjectColour(objectType, objectElement);
	    Vector<Float> objectPosition = getObjectPosition(objectElement);
	    Vector<Float> objectRotation = getObjectRotation(objectElement);

	    // Create the object for the level
	    XmlLevelObject xmlLevelObject = null;
	    if (objectType.equals(XmlLevelBrick.getId())) {
		xmlLevelObject = new XmlLevelBrick(objectPosition, objectRotation);
	    }
	    else if (objectType.equals(XmlLevelGoal.getId())) {
		xmlLevelObject = new XmlLevelGoal(objectColour, objectPosition, objectRotation);
	    }
	    else if (objectType.equals(XmlLevelEmitter.getId())) {
		xmlLevelObject = new XmlLevelEmitter(objectColour, objectPosition, objectRotation);
	    }
	    else {
		throw new RuntimeException("Level file parsing failed. Invalid type: \"" + objectType + "\"");
	    }
	    return xmlLevelObject;
	}
	else {
	    throw new RuntimeException("Something was wrong with the XML file.");
	}
    }


    /**
     * Helper method for parsing the rotation of an object.
     * @param element An Element object containing the object.
     * @return A Vector containing the rotation of the object.
     */
    private Vector<Float> getObjectRotation(Element element)
    {
	Element rotationElement = getRotationElement(element);
	Vector<Float> rotation = new Vector<Float>(3);
	rotation.add(getObjectXRotation(rotationElement));
	rotation.add(getObjectYRotation(rotationElement));
	rotation.add(getObjectZRotation(rotationElement));
	return rotation;
    }


    /**
     * Helper method for parsing the Z rotation of an object.
     * @param rotationElement An Element object containing the rotation of an object.
     * @return The Z rotation of the object.
     */
    private Float getObjectZRotation(Element rotationElement)
    {
	NodeList objectZRotationNodeList = rotationElement.getElementsByTagName("z");
	Element objectZRotationElement = (Element)objectZRotationNodeList.item(0);
	NodeList objectZRotation = objectZRotationElement.getChildNodes();
	String objectZRotationString = ((Node)objectZRotation.item(0)).getNodeValue();
	return Float.parseFloat(objectZRotationString);
    }


    /**
     * Helper method for parsing the Y rotation of an object.
     * @param rotationElement An Element object containing the rotation of an object.
     * @return The Y rotation of the object.
     */
    private Float getObjectYRotation(Element rotationElement)
    {
	NodeList objectYRotationNodeList = rotationElement.getElementsByTagName("y");
	Element objectYRotationElement = (Element)objectYRotationNodeList.item(0);
	NodeList objectYRotation = objectYRotationElement.getChildNodes();
	String objectYRotationString = ((Node)objectYRotation.item(0)).getNodeValue();
	return Float.parseFloat(objectYRotationString);
    }


    /**
     * Helper method for parsing the X rotation of an object.
     * @param rotationElement An Element object containing the rotation of an object.
     * @return The X rotation of the object.
     */
    private Float getObjectXRotation(Element rotationElement)
    {
	NodeList objectXRotationNodeList = rotationElement.getElementsByTagName("x");
	Element objectXRotationElement = (Element)objectXRotationNodeList.item(0);
	NodeList objectXRotation = objectXRotationElement.getChildNodes();
	String objectXRotationString = ((Node)objectXRotation.item(0)).getNodeValue();
	return Float.parseFloat(objectXRotationString);
    }


    /**
     * Helper method for getting the rotation element for the object element.
     * @param element An Element object of the level object.
     * @return An element object for the object's rotation.
     */
    private Element getRotationElement(Element element)
    {
	NodeList rotationNodeList = element.getElementsByTagName("rotation");
	return (Element)rotationNodeList.item(0);
    }


    /**
     * Helper method for parsing the position of an object.
     * @param element An Element object of the level object.
     * @return A Vector containing the position of the object.
     */
    private Vector<Float> getObjectPosition(Element element)
    {
	Element positionElement = getPositionElement(element);
	Vector<Float> position = new Vector<Float>(2);
	position.add(getObjectXPosition(positionElement));
	position.add(getObjectYPosition(positionElement));
	return position;
    }


    /**
     * Helper method for parsing the object's Y position.
     * @param positionElement An Element object containing the object's position.
     * @return The Y position of the object.
     */
    private float getObjectYPosition(Element positionElement)
    {
	NodeList objectYNodeList = positionElement.getElementsByTagName("y");
	Element objectYElement = (Element)objectYNodeList.item(0);
	NodeList objectY = objectYElement.getChildNodes();
	String objectYString = ((Node)objectY.item(0)).getNodeValue();
	return Float.parseFloat(objectYString);
    }


    /**
     * Helper method for parsing the object's X position.
     * @param positionElement An Element object containing the object's position.
     * @return The X position of the object.
     */
    private float getObjectXPosition(Element positionElement)
    {
	NodeList objectXNodeList = positionElement.getElementsByTagName("x");
	Element objectXElement = (Element)objectXNodeList.item(0);
	NodeList objectX = objectXElement.getChildNodes();
	String objectXString = ((Node)objectX.item(0)).getNodeValue();
	return Float.parseFloat(objectXString);
    }


    /**
     * Helper method for getting the position element from an object element.
     * @param element An Element object for the object.
     * @return An Element object for the position of an object.
     */
    private Element getPositionElement(Element element)
    {
	NodeList positionNodeList = element.getElementsByTagName("position");
	return (Element)positionNodeList.item(0);
    }


    /**
     * Helper method for parsing the colour of an object.
     * @param objectType The type of the object.
     * @param element An Element object for the object.
     * @return The colour of the object (white if it is a brick).
     */
    private String getObjectColour(String objectType, Element element)
    {
	if (objectType.equals(XmlLevelBrick.getId())) {
	    return "white";
	}
	else {
	    NodeList colourNodeList = element.getElementsByTagName("colour");
	    Element colourElement = (Element)colourNodeList.item(0);
	    NodeList colour = colourElement.getChildNodes();
	    return ((Node)colour.item(0)).getNodeValue();
	}
    }


    /**
     * Helper method for parsing the type of the object.
     * @param element An Element object for the object.
     * @return The type of the object.
     */
    private String getObjectType(Element element)
    {
	NodeList typeNodeList = element.getElementsByTagName("type");
	Element typeElement = (Element)typeNodeList.item(0);
	NodeList type = typeElement.getChildNodes();
	return ((Node)type.item(0)).getNodeValue();
    }


    /**
     * Helper method for parsing the height of the level.
     * @param document A Document object of the level.
     * @return The height of the level.
     */
    private float getLevelHeight(Document document)
    {
	Element gridSizeElement = getGridSizeElement(document);
	Element hElement = (Element)gridSizeElement.getElementsByTagName("height").item(0);
	String h = ((Node)hElement.getChildNodes().item(0)).getNodeValue();
	return Float.parseFloat(h);
    }


    /**
     * Helper method for parsing the width of the level.
     * @param document A Document object of the level.
     * @return The width of the level.
     */
    private float getLevelWidth(Document document)
    {
	Element gridSizeElement = getGridSizeElement(document);
	Element wElement = (Element)gridSizeElement.getElementsByTagName("width").item(0);
	String w = ((Node)wElement.getChildNodes().item(0)).getNodeValue();
	return Float.parseFloat(w);
    }


    /**
     * Helper method for parsing the y size of the level.
     * @param document A Document object of the level.
     * @return The y size of the level.
     */
    private int getLevelYSize(Document document)
    {
	Element gridSizeElement = getGridSizeElement(document);
	Element yElement = (Element)gridSizeElement.getElementsByTagName("y").item(0);
	String y = ((Node)yElement.getChildNodes().item(0)).getNodeValue();
	return Integer.parseInt(y);
    }


    /**
     * Helper method for parsing the x size of the level.
     * @param document A Document object of the level.
     * @return The x size of the level.
     */
    private int getLevelXSize(Document document)
    {
	Element gridSizeElement = getGridSizeElement(document);
	Element xElement = (Element)gridSizeElement.getElementsByTagName("x").item(0);
	String x = ((Node)xElement.getChildNodes().item(0)).getNodeValue();
	return Integer.parseInt(x);
    }


    /**
     * Helper method for getting the grid_size element from the level.
     * @param document A Document object of the level.
     * @return An Element object of the grid_size of the level.
     */
    private Element getGridSizeElement(Document document)
    {
	return (Element)document.getElementsByTagName("grid_size").item(0);
    }


    /**
     * Helper method for parsing the difficulty of a level.
     * @param document A Document object of the level.
     * @return The difficulty of the level.
     */
    private String getLevelDifficulty(Document document)
    {
	Element difficultyElement = (Element)document.getElementsByTagName("difficulty").item(0);
	String difficulty = ((Node)difficultyElement.getChildNodes().item(0)).getNodeValue();
	return difficulty;
    }


    /**
     * Helper method for parsing the name of a level.
     * @param document A Document object of the level.
     * @return The name of the level.
     */
    private String getLevelName(Document document)
    {
	Element nameElement = (Element)document.getElementsByTagName("name").item(0);
	String name = ((Node)nameElement.getChildNodes().item(0)).getNodeValue();
	return name;
    }
}
