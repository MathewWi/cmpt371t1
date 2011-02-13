package ca.sandstorm.luminance.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.Engine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

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
	logger.debug("ResourceManager created.");
    }
    
    /**
     * Retrieve a stored resource by name.
     * @param name Name of the resource (normally its file path in assets directory)
     * @return The resource
     */
    public IResource getResource(String name)
    {
	return resources.get(name);
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
	logger.debug("Assets have been assigned to ResourceManager.");
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
     * Load an image resource.
     * @param filename Path to resource relative to assets directory
     * @return The newly loaded resource, or an existing already loaded one
     * @throws IOException
     */
    public ImageResource loadImageResource(String filename) throws IOException
    {
	InputStream stream = assets.open(filename);
	Bitmap bitmap = BitmapFactory.decodeStream(stream);
	ImageResource res = new ImageResource(filename, bitmap);
	
	resources.put(filename, res);
	return res;
    }
    
    /**
     * Load a texture resource
     * @param filename Path to resource relative to assets directory
     * @param gl OpenGL context to use for texture creation
     * @return The newly loaded resource, or an existing already loaded one
     * @throws IOException
     */
    public TextureResource loadTextureResource(String filename, GL10 gl) throws IOException
    {
	// Load the image
	InputStream stream = assets.open(filename);
	Bitmap bitmap = BitmapFactory.decodeStream(stream);
	
	// Create a texture out of the image
	int[] textures = new int[1];
	gl.glGenTextures(1, textures, 0);  // one texture
	gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
	
	// Texture parameters
	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
	
	// Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
	GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
	TextureResource res = new TextureResource(filename, textures[0]);
	
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
	// An official Android sample states that available() guarantees returning the whole file size
	InputStream stream = assets.open(filename);
	int size = stream.available();
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
