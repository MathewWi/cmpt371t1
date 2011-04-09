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

	private AndroidSoundPlayer _player;
	private int _streamId = 0;
	private ResourceManager _resManager;
	private SoundResource _sound;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.test.AndroidTestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		_player = new AndroidSoundPlayer();
		_resManager = new ResourceManager();
	}

	/*
	 * Testing of the getPool() method
	 */
	public void testGetPool() throws Exception {
		SoundPool testPool = _player.getPool();
		assertTrue(testPool != null);
	}

	/*
	 * Testing of the play() method
	 */
	public void testPlay() throws Exception {
		try {
			_sound = _resManager.loadSound(_player.getPool(), "sample.ogg");
			_streamId = _player.play(_sound, (new Float(0.5)));
			assertTrue (_streamId != 0);
		} catch (Exception ex) {
		}
	}

	/*
	 * Testing of the stop() method
	 */
	public void testStop() throws Exception {
		_player.stop(_streamId);
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
