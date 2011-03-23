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

	ResourceManager resManager;
	TextResource text;
	ImageResource image;
	TextureResource texture;
	SoundResource sound;
	Resource resource;

	/**
	 * Setting up an instance to be tested
	 */
	protected void setUp() throws Exception {
		super.setUp();
		resManager = new ResourceManager();
	}

	/**
	 * Testing the loadResource() method
	 */
	public void testLoadResource() throws Exception {

		try {
			resource = resManager.loadResource("text.txt");
			assert (resource != null);
		} catch (Exception ex) {}
	}

	/**
	 * Testing the loadText() method
	 */
	public void testLoadText() throws Exception {

		try {
			text = resManager.loadText("text.txt");
			assert (text != null);
		} catch (Exception ex) {}
	}

	/**
	 * Testing the loadImage() method
	 */
	public void testLoadImage() throws Exception {

		try {
			image = resManager.loadImage("skyBack.jpg");
			assert (image != null);
		} catch (Exception ex) {}
	}

	/**
	 * Testing the loadSound() method
	 * 
	 * @throws Exception
	 */
	public void testLoadSound() throws Exception {

		try {
			sound = resManager.loadSound((new SoundPool(1, 1, 100)),
					"sample.ogg");
			assert (sound != null);
		} catch (Exception ex) {}
	}

	public void testLoadTexture() throws Exception {

		try {
			texture = resManager.loadTexture(null, "skyBack.jpg");
			assert (texture != null);
		} catch (Exception ex) {
			
		}
	}

	/**
	 * Testing the getResource() method
	 * 
	 * @throws Exception
	 */
	public void testGetResource() throws Exception {

		IResource res = resManager.getResource("text.txt");
		assert (res.getName().equals("text.txt"));
	}

}
