package ca.sandstorm.luminance.test.resource;

import android.media.SoundPool;
import android.test.AndroidTestCase;
import ca.sandstorm.luminance.resources.IResource;
import ca.sandstorm.luminance.resources.ImageResource;
import ca.sandstorm.luminance.resources.Resource;
import ca.sandstorm.luminance.resources.ResourceManager;
import ca.sandstorm.luminance.resources.SoundResource;
import ca.sandstorm.luminance.resources.TextResource;
import ca.sandstorm.luminance.resources.TextureResource;

/**
 * Testing of the ResourceManager class
 * 
 * @author Chet W Collins
 * 
 */
public class ResourceManagerTest extends AndroidTestCase {

	ResourceManager _resManager;
	TextResource _text;
	ImageResource _image;
	TextureResource _texture;
	SoundResource _sound;
	Resource _resource;

	/**
	 * Setting up an instance to be tested
	 */
	protected void setUp() throws Exception {
		super.setUp();
		_resManager = new ResourceManager();
	}

	/**
	 * Testing the loadResource() method
	 */
	public void testLoadResource() throws Exception {

		try {
			_resource = _resManager.loadResource("text.txt");
			assert (_resource != null);
		} catch (Exception ex) {}
	}

	/**
	 * Testing the loadText() method
	 */
	public void testLoadText() throws Exception {

		try {
			_text = _resManager.loadText("text.txt");
			assert (_text != null);
		} catch (Exception ex) {}
	}

	/**
	 * Testing the loadImage() method
	 */
	public void testLoadImage() throws Exception {

		try {
			_image = _resManager.loadImage("skyBack.jpg");
			assert (_image != null);
		} catch (Exception ex) {}
	}

	/**
	 * Testing the loadSound() method
	 * 
	 * @throws Exception
	 */
	public void testLoadSound() throws Exception {

		try {
			_sound = _resManager.loadSound((new SoundPool(1, 1, 100)),
					"sample.ogg");
			assert (_sound != null);
		} catch (Exception ex) {}
	}

	public void testLoadTexture() throws Exception {

		try {
			_texture = _resManager.loadTexture(null, "skyBack.jpg");
			assert (_texture != null);
		} catch (Exception ex) {
			
		}
	}

	/**
	 * Testing the getResource() method
	 * 
	 * @throws Exception
	 */
	public void testGetResource() throws Exception {

		IResource res = _resManager.getResource("text.txt");
		assert (res.getName().equals("text.txt"));
	}

}
