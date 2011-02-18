package ca.sandstorm.luminance.level;

/**
 * Class for tools in XmlLevels.
 * @author Steven Indzeoski
 *
 */
public class XmlLevelTool {
	
	private String _type;
	private int _count;
	
	/**
	 * Constructor for XmlLevelTool object.
	 * @param type The type of tool (mirror, prism)
	 * @param count The number of this tool you get for this level.
	 */
	public XmlLevelTool(String type, int count)
	{
		if (!isValidType(type)) 
		{
			throw new IllegalArgumentException("Type given (\"" + type + "\") is invalid.");
		}
		else if (count < 0)
		{
			throw new IllegalArgumentException("Number of " + type + "s given (" + count + ") is invalid.");
		}
		_type = type;
		_count = count;
	}
	
	/**
	 * Getter method for the type of the tool.
	 * @return The type of the tool.
	 */
	public String getType()
	{
		return _type;
	}
	
	/**
	 * Getter method for the count of the tool.
	 * @return The count of the tool.
	 */
	public int getCount()
	{
		return _count;
	}
	
	/**
	 * Checks if a type is valid (mirror, prism)
	 * @param type The type to check the validity of.
	 * @return True is the type is valid, false otherwise.
	 */
	public boolean isValidType(String type)
	{
		if (type.equals("mirror"))
		{
			return true;
		}
		else if (type.equals("prism"))
		{
			return true;
		}
		else {
			return false;
		}
	}
	
	public String toString()
	{
		return ("\nType: " + getType() + 
				"\nCount: " + getCount());
	}
}
