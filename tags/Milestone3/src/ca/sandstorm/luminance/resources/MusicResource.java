package ca.sandstorm.luminance.resources;

import java.io.FileDescriptor;
import java.io.IOException;

import android.content.res.AssetFileDescriptor;

/**
 * Represents a music resource.
 * @author zenja
 */
public class MusicResource implements IResource
{

    private String _name;
    private AssetFileDescriptor _fileDescriptor;


    /**
     * Constructor.
     * 
     * @param name
     *            Resource name
     * @param fileDescriptor
     *            Asset file descriptor
     */
    public MusicResource(String name, AssetFileDescriptor fileDescriptor)
    {
	_name = name;
	_fileDescriptor = fileDescriptor;
    }


    /**
     * Get the file's file descriptor.
     * 
     * @return File descriptor
     */
    public FileDescriptor getMusicFd()
    {
	return _fileDescriptor.getFileDescriptor();
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
	// This is resource is streamed, not loaded into memory
	return 0;
    }


    /**
     * Dispose of the resource and clean up behind it.
     */
    @Override
    public void dispose()
    {
	try {
	    _fileDescriptor.close();
	} catch (IOException e) {
	    throw new RuntimeException("Failed to close resource file: " + _name);
	}
    }
}
