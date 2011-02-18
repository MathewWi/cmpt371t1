package ca.sandstorm.luminance.resources;

/**
 * Represents a sound effect resource.
 * 
 * @author zenja
 */
public class SoundResource implements IResource
{
    private String _name;
    private int _sound;


    /**
     * Constructor.
     * 
     * @param name
     *            Resource name
     * @param texture
     *            OpenGL texture identifier
     */
    public SoundResource(String name, int sound)
    {
	_name = name;
	_sound = sound;
    }


    /**
     * Get the OpenGL texture identifier for this texture.
     * 
     * @return Texture pointer/identifier
     */
    public int getSound()
    {
	return _sound;
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
