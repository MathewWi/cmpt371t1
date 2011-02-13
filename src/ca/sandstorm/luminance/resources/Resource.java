package ca.sandstorm.luminance.resources;

/**
 * Project resource.
 * @author zenja
 */
public class Resource implements IResource
{
    protected byte[] data;
    protected String name;
    
    /**
     * Constructor
     * @param name Resource name
     * @param data Resource data
     */
    public Resource(String name, byte[] data)
    {
	this.name = name;
	this.data = data;
    }
    
    /**
     * Get the resource name.
     * @return Name of the resource (normally its file path relative to assets directory)
     */
    @Override
    public String getName()
    {
	return name;
    }

    /**
     * Get the memory footprint of this resource.
     * @return Memory footprint in bytes
     */
    @Override
    public int getMemorySize()
    {
	return data.length;
    }
    
    /**
     * Get the resource raw data.
     * @return Data in byte-array form
     */
    @Override
    public byte[] getData()
    {
	return data;
    }
    
    /**
     * Dispose of the resource and clean up behind it.
     */
    @Override
    public void dispose()
    {
        data = null;
        name = null;
    }
}
