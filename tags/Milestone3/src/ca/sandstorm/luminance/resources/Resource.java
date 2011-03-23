package ca.sandstorm.luminance.resources;

import android.content.res.AssetFileDescriptor;

/**
 * Project resource.
 * 
 * @author zenja
 */
public class Resource implements IResource
{
    protected byte[] _data;
    protected String _name;

    /**
     * Constructor.
     * 
     * @param name
     *            Resource name
     * @param data
     *            Resource data
     */
    public Resource(String name, byte[] data)
    {
	_name = name;
	_data = data;
    }


    /**
     * Get the resource name.
     * 
     * @return Name of the resource (normally its file path relative to assets
     *         directory)
     */
    @Override
    public String getName()
    {
	return _name;
    }


    /**
     * Get the memory footprint of this resource.
     * 
     * @return Memory footprint in bytes
     */
    @Override
    public int getMemorySize()
    {
	return _data.length;
    }


    /**
     * Get the resource raw data.
     * 
     * @return Data in byte-array form
     */
    public byte[] getData()
    {
	return _data;
    }
    

    /**
     * Dispose of the resource and clean up behind it.
     */
    @Override
    public void dispose()
    {
	_data = null;
	_name = null;
    }
}
