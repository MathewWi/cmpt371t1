package ca.sandstorm.luminance.resources;

import android.graphics.Bitmap;


/**
 * Represents an image resource.
 * @author zenja
 */
public class ImageResource implements IResource
{
    private String _name;
    private Bitmap _bitmap;


    /**
     * Constructor.
     * 
     * @param name
     *            Resource name
     * @param bitmap
     *            Bitmap to be contained
     */
    public ImageResource(String name, Bitmap bitmap)
    {
	_name = name;
	_bitmap = bitmap;
    }


    /**
     * Get the resource name.
     * @return The resource's name, which is normally its path
     */
    @Override
    public String getName()
    {
	return _name;
    }


    /**
     * Get the memory footprint of this resource.
     * @return Memory footprint in bytes
     */
    @Override
    public int getMemorySize()
    {
	// TODO
	return 0;
    }


    /**
     * Dispose of the resource and clean up behind it.
     */
    @Override
    public void dispose()
    {
	_bitmap.recycle();
	_bitmap = null;
    }
}
