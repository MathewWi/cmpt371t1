/**
 * 
 */
package ca.sandstorm.luminance.test.resource;


import org.junit.Before;

import ca.sandstorm.luminance.resources.MusicResource;

import android.content.res.AssetFileDescriptor;

/**
 * @author Chet W Collins
 * Testing of the MusicResource class
 *
 */
public class MusicResourceTest {
	
	private AssetFileDescriptor fd;
	MusicResource musicRes;

	/**
	 * @throws java.lang.Exception
	 * Initializing variables
	 */
	@Before
	public void setUp() throws Exception {
		fd = new AssetFileDescriptor(null, 0, 0);
		musicRes = new MusicResource("awesomeSong",fd);
	}
	
	/**
	 * Testing the getMusicFd() method
	 * @throws Exception
	 */
	public void testGetMusicFd() throws Exception {
		assert(musicRes.getMusicFd() != null);
	}
	
	/**
	 * Testing the getName() method
	 * @throws Exception
	 */
	public void testGetName() throws Exception {
		assert(musicRes.getName().equals("awesomeSong"));
	}
}
