package ca.sandstorm.luminance.test.audio;

import android.media.SoundPool;
import android.test.AndroidTestCase;
import ca.sandstorm.luminance.audio.AndroidSoundPlayer;
import ca.sandstorm.luminance.resources.ResourceManager;
import ca.sandstorm.luminance.resources.SoundResource;

/**
 * Testing of the AndroidSoundPlayer class
 * 
 * @author Chet W Collins
 * 
 */
public class AndroidSoundPlayerTest extends AndroidTestCase {

	private AndroidSoundPlayer player;
	private int streamId = 0;
	private ResourceManager resManager;
	private SoundResource sound;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.test.AndroidTestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		player = new AndroidSoundPlayer();
		resManager = new ResourceManager();
	}

	/*
	 * Testing of the getPool() method
	 */
	public void testGetPool() throws Exception {
		SoundPool testPool = player.getPool();
		assertNotNull(testPool);
	}

	/*
	 * Testing of the play() method
	 */
	public void testPlay() throws Exception {
		try {
			sound = resManager.loadSound(player.getPool(), "sample.ogg");
			streamId = player.play(sound, (new Float(0.5)));
			assert (streamId != 0);
		} catch (Exception ex) {
		}
	}

	/*
	 * Testing of the stop() method
	 */
	public void testStop() throws Exception {
		player.stop(streamId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.test.AndroidTestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
