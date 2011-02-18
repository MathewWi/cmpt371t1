package ca.sandstorm.luminance.level;

/**
 * Class for the mirror tool in XmlLevels.
 * @author Steven Indzeoski
 *
 */
public class XmlLevelMirror extends XmlLevelTool 
{
	/**
	 * Constructor for the XmlLevelMirror class.
	 * @param count
	 * @precond count > 0
	 * @postcond XmlLevelMirror is created
	 */
	public XmlLevelMirror(int count) 
	{
		super("mirror", count);
	}
	
}
