package ca.sandstorm.luminance.resources;

/**
 * Represents an OpenGL texture resource.
 * 
 * @author zenja
 */
public class TextureResource implements IResource
{
    private String _name;
    private int _glTexturePtr;


    /**
     * Constructor.
     * 
     * @param name
     *            Resource name
     * @param texture
     *            OpenGL texture identifier
     */
    public TextureResource(String name, int texture)
    {
	_name = name;
	_glTexturePtr = texture;
    }


    /**
     * Get the OpenGL texture identifier for this texture.
     * 
     * @return Texture pointer/identifier
     */
    public int getTexture()
    {
	return _glTexturePtr;
    }


    /**
     * Get the resource name.
     * 
     * @return The resource's name, which is normally its path
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
	// TODO
	return 0;
    }


    /**
     * Dispose of the resource and clean up behind it.
     */
    @Override
    public void dispose()
    {
	// TODO
    }
}
