package ca.sandstorm.luminance.resources;

/**
 * Text file resource.
 * 
 * @author zenja.
 */
public class TextResource extends Resource
{
    /**
     * Constructor.
     * 
     * @param name
     *            Resource name
     * @param data
     *            Resource raw data
     */
    public TextResource(String name, byte[] data)
    {
	super(name, data);
    }


    public TextResource(Resource res)
    {
	super(res.name, res.data);
    }


    /**
     * Get the text resource's data in string form.
     * 
     * @return String contained in the text resource
     */
    public String getText()
    {
	return new String(data);
    }
}
