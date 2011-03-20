package ca.sandstorm.luminance.resources;

import javax.microedition.khronos.opengles.GL10;

/**
 * Represents an OpenGL texture resource.
 * 
 * @author zenja
 */
public class TextureResource implements IResource
{
    private String _name;
    private int _glTexturePtr;
    protected GL10 _gl;
    

    /**
     * Constructor.
     * 
     * @param name
     *            Resource name
     * @param texture
     *            OpenGL texture identifier
     */
    public TextureResource(String name, int texture, GL10 gl)
    {
	_name = name;
	_glTexturePtr = texture;
	_gl = gl;
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
