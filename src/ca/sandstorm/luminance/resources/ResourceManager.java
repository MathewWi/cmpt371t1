package ca.sandstorm.luminance.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.SoundPool;
import android.opengl.GLUtils;


/**
 * Luminance resource manager. Loads and stores resources such as sounds,
 * images, and text files.
 * 
 * @author zenja
 */
public class ResourceManager
{
    private static final Logger _logger = LoggerFactory
	    .getLogger(ResourceManager.class);

    private AssetManager _assets;
    private HashMap<String, IResource> _resources;


    /**
     * Constructor.
     */
    public ResourceManager()
    {
	_logger.debug("ResourceManager()");
	
	_resources = new HashMap<String, IResource>();
	_assets = null;	
    }


    /**
     * Retrieve a stored resource by name.
     * 
     * @param name
     *            Name of the resource (normally its file path in assets
     *            directory)
     * @return The resource
     */
    public IResource getResource(String name)
    {
	return _resources.get(name);
    }


    /**
     * Assign the asset manager corresponding with the current application.
     * 
     * @param assets
     *            The asset manager
     */
    public void setAssets(AssetManager assets)
    {
	_logger.debug("setAssets(" + assets + ")");
	
	if (assets == null)
	    throw new RuntimeException(
		    "Attempting to assign null application context to resource manager!");
	this._assets = assets;
	_logger.debug("Assets have been assigned to ResourceManager.");
    }


    /**
     * Load a generic resource.
     * 
     * @param filename
     *            Path to resource relative to assets directory
     * @return The newly loaded resource, or a previously loaded one
     * @throws IOException
     */
    public Resource loadResource(String filename) throws IOException
    {
	_logger.debug("loadResource(" + filename + ")");
	
	// Check if the resource is already loaded
	if (_resources.containsKey(filename))
	    return (Resource) _resources.get(filename);

	// Read file
	byte[] data = readFile(filename);
	Resource res = new Resource(filename, data);
	_resources.put(filename, res);

	return res;
    }
    
    /**
     * Remove a loaded resource.
     * @param name Name of resource to unload
     */
    public void unloadResource(String name)
    {
	_logger.debug("unloadResource(" + name + ")");
	
	if (!_resources.containsKey(name))
	    return;
	
	_resources.get(name).dispose();
	_resources.remove(name);
    }


    /**
     * Load a text file resource.
     * 
     * @param filename
     *            Path to resource relative to assets directory
     * @return The newly loaded resource, or a previously loaded one
     * @throws IOException
     */
    public TextResource loadText(String filename) throws IOException
    {
	_logger.debug("loadText(" + filename + ")");
	
	// Check if the resource is already loaded
	if (_resources.containsKey(filename))
	    return (TextResource) _resources.get(filename);

	// Read file
	byte[] data = readFile(filename);
	TextResource res = new TextResource(filename, data);

	_resources.put(filename, res);
	return res;
    }


    /**
     * Load an image resource.
     * 
     * @param filename
     *            Path to resource relative to assets directory
     * @return The newly loaded resource, or an existing already loaded one
     * @throws IOException
     */
    public ImageResource loadImage(String filename) throws IOException
    {
	_logger.debug("loadImage(" + filename + ")");
	
	InputStream stream = _assets.open(filename);
	Bitmap bitmap = BitmapFactory.decodeStream(stream);
	ImageResource res = new ImageResource(filename, bitmap);

	_resources.put(filename, res);
	return res;
    }


    /**
     * Load a texture resource
     * @param gl
     *            OpenGL context to use for texture creation
     * @param filename
     *            Path to resource relative to assets directory
     * 
     * @return The newly loaded resource, or an existing already loaded one
     * @throws IOException
     */
    public TextureResource loadTexture(GL10 gl, String filename)
	    throws IOException
    {
	_logger.debug("loadTexture(" + gl + " , " + filename + ")");
	
	// Load the image
	InputStream stream = _assets.open(filename);
	Bitmap bitmap = BitmapFactory.decodeStream(stream);

	_logger.debug("loadTexture() Width: " + bitmap.getWidth() + " Height: " + bitmap.getHeight());
	
	// Create a texture out of the image
	int[] textures = new int[1];
	gl.glGenTextures(1, textures, 0); // one texture
	gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

	// Texture parameters
	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
			   GL10.GL_NEAREST);
	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
			   GL10.GL_LINEAR);
	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
			   GL10.GL_REPEAT);
	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
			   GL10.GL_REPEAT);

	// Use the Android GLUtils to specify a two-dimensional texture image
	// from our bitmap
	GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
	TextureResource res = new TextureResource(filename, textures[0]);

	_resources.put(filename, res);
	_logger.debug("Generated texture: " + filename + " = " + Integer.toString(textures[0]));
	
	return res;
    }


    /**
     * Load a sound effect resource.
     * 
     * @param pool Android SoundPool to use
     * @param filename
     *            Path to resource relative to assets directory
     * @return The newly loaded resource, or an existing already loaded one
     * @throws IOException 
     */
    public SoundResource loadSound(SoundPool pool, String filename) throws IOException
    {
	_logger.debug("loadSound(" + pool + ", " + filename + ")");
	
	//int soundId = pool.load(filename, 1);
	AssetFileDescriptor afd = _assets.openFd(filename);
	int soundId = pool.load(afd, 1);
	SoundResource res = new SoundResource(filename, soundId);

	_resources.put(filename, res);
	return res;
    }


    /**
     * Read byte array from a raw file.
     * 
     * @param filename
     *            Path to file in assets directory
     * @return Raw data in byte array
     * @throws IOException
     */
    public byte[] readFile(String filename) throws IOException
    {
	_logger.debug("readFile(" + filename + ")");
	
	// An official Android sample states that available() guarantees
	// returning the whole file size
	InputStream stream = _assets.open(filename);
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
     * 
     * @param res
     *            The resource to dispose of
     */
    public void unloadResource(IResource res)
    {
	_logger.debug("unloadResource(" + res + ")");
	
	if (_resources.containsKey(res.getName())) {
	    res.dispose();
	    _resources.remove(res.getName());
	}
    }


    /**
     * Get a list of files in the resources directory.
     * 
     * @return Array of strings representing the file list
     * @throws IOException
     */
    public String[] getFileListing(String path) throws IOException
    {
	_logger.debug("unloadResource(" + path + ")");
	
	if (_assets == null)
	    throw new RuntimeException(
		    "Trying to access null asset manager in getFileListing()!");
	
	return _assets.list(path);
    }
}
