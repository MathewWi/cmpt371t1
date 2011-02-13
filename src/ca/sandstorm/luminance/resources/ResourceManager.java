package ca.sandstorm.luminance.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.res.AssetManager;

/**
 * Luminance resource manager. Loads and stores resources such as sounds, images, and text files.
 * @author zenja
 */
public class ResourceManager
{
    private static final Logger logger = LoggerFactory.getLogger("Luminance.ResourceManager");

    private AssetManager assets;
    private HashMap<String, IResource> resources;
    
    /**
     * Constructor.
     */
    public ResourceManager() {
	resources = new HashMap<String, IResource>();
	assets = null;
    }
    
    /**
     * Assign the asset manager corresponding with the current application.
     * @param assets The asset manager
     */
    public void setAssets(AssetManager assets)
    {
	if (assets == null)
	    throw new RuntimeException("Attempting to assign null application context to resource manager!");
	this.assets = assets;
    }
    
    /**
     * Load a generic resource.
     * @param filename Path to resource relative to assets directory
     * @return The newly loaded resource, or a previously loaded one
     * @throws IOException
     */
    public Resource loadResource(String filename) throws IOException
    {
	// Check if the resource is already loaded
	if (resources.containsKey(filename))
	    return (Resource)resources.get(filename);
	
	// Read file
	byte[] data = readFile(filename);    	
	Resource res = new Resource(filename, data);
	resources.put(filename, res);
	
	return res;
    }
    
    /**
     * Load a text file resource.
     * @param filename Path to resource relative to assets directory
     * @return The newly loaded resource, or a previously loaded one
     * @throws IOException 
     */
    public TextResource loadTextResource(String filename) throws IOException
    {
	// Check if the resource is already loaded
	if (resources.containsKey(filename))
	    return (TextResource)resources.get(filename);
		
	// Read file
	byte[] data = readFile(filename);    	
	TextResource res = new TextResource(filename, data);
	resources.put(filename, res);
	
	return res;
    }
    
    /**
     * Read byte array from a raw file.
     * @param filename Path to file in assets directory
     * @return Raw data in byte array
     * @throws IOException
     */
    public byte[] readFile(String filename) throws IOException
    {
	InputStream stream = assets.open(filename);
	int size = stream.available();  // an official Android sample states that available() guarantees returning the whole file size
	if (size == -1)
	    return null;
	
	byte[] buffer = new byte[size];
	stream.read(buffer);
	stream.close();
	    
	return buffer;
    }
    
    /**
     * Dispose of a loaded resource.
     * @param res The resource to dispose of
     */
    public void unloadResource(IResource res)
    {
	if(resources.containsKey(res.getName())) {
	    res.dispose();
	    resources.remove(res.getName());
	}
    }
    
    /**
     * Get a list of files in the resources directory.
     * @return Array of strings representing the file list
     * @throws IOException 
     */
    public String[] getFileListing(String path) throws IOException
    {
	if (assets == null)
	    throw new RuntimeException("Trying to access null asset manager in getFileListing()!");
	return assets.list(path);
    }
}
