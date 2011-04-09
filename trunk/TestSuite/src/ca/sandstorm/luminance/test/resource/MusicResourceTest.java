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
	
	private AssetFileDescriptor _fd;
	MusicResource _musicRes;

	/**
	 * @throws java.lang.Exception
	 * Initializing variables
	 */
	@Before
	public void setUp() throws Exception {
		_fd = new AssetFileDescriptor(null, 0, 0);
		_musicRes = new MusicResource("awesomeSong",_fd);
	}
	
	/**
	 * Testing the getMusicFd() method
	 * @throws Exception
	 */
	public void testGetMusicFd() throws Exception {
		assert(_musicRes.getMusicFd() != null);
	}
	
	/**
	 * Testing the getName() method
	 * @throws Exception
	 */
	public void testGetName() throws Exception {
		assert(_musicRes.getName().equals("awesomeSong"));
	}
}
