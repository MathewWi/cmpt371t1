package ca.sandstorm.luminance.resources;

/**
 * Represents an OpenGL texture resource.
 * @author zenja
 */
public class TextureResource implements IResource
{
    private String name;
    private int glTexturePtr;
    
    /**
     * Constructor.
     * @param name Resource name
     * @param texture OpenGL texture identifier
     */
    public TextureResource(String name, int texture)
    {
	this.name = name;
	this.glTexturePtr = texture;
    }
    
    /**
     * Get the OpenGL texture identifier for this texture.
     * @return Texture pointer/identifier
     */
    public int getTexture()
    {
	return glTexturePtr;
    }
    
    /**
     * Get the resource name.
     * @return The resource's name, which is normally its path
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
	//TODO
	return 0;
    }

    /**
     * Dispose of the resource and clean up behind it.
     */
    @Override
    public void dispose()
    {
	//TODO
    }
}
