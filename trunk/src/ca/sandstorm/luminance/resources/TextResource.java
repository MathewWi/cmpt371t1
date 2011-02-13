package ca.sandstorm.luminance.resources;

/**
 * Text file resource.
 * @author zenja.
 */
public class TextResource extends Resource implements IResource
{
    /**
     * Constructor.
     * @param name Resource name
     * @param data Resource raw data
     */
    public TextResource(String name, byte[] data)
    {
	super(name, data);
    }

    /**
     * Get the text resource's data in string form.
     * @return String contained in the text resource
     */
    public String getText()
    {
	return new String(data);
    }
}
