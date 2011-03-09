package ca.sandstorm.luminance.level;

import android.graphics.Color;

/**
 * Class for the light emitter object in levels
 * @author Steven Indzeoski
 *
 */
public class XmlLevelEmitter extends XmlLevelObject
{
    private String _color;
    
    /**
     * Constructor for XmlLevelEmitter.
     * @postcond XmlLevelEmitter is created.
     */
    public XmlLevelEmitter(String color)
    {
	super(XmlLevelEmitter.getId());
	
	_color = color;
    }
    
    public String getColour()
    {
	return _color; 
    }
    
    /**
     * getId method for XmlLevelEmitter.
     * @return The object type.
     */
    public static String getId()
    {
	return "emitter";
    }
    
    
}
