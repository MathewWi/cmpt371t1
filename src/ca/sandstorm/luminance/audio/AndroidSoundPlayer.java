package ca.sandstorm.luminance.audio;

import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.media.AudioManager;
import android.media.SoundPool;


/**
 * Android sound player manager.
 * 
 * @author Zenja
 */
public class AndroidSoundPlayer implements IAudioDriver
{
    // TODO: Remove sound streams from map when they're done
    private SoundPool mSoundPool;
    private HashMap<String, Integer> mSoundMap;
    private HashMap<Integer, Integer> mStreamMap;
    private static final Logger logger = LoggerFactory
	    .getLogger("Luminance.AndroidSoundPlayer");


    /**
     * Initialize the Android audio player manager.
     */
    public AndroidSoundPlayer()
    {
	mSoundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
	if (mSoundPool == null) {
	    throw new RuntimeException("Audio: failed to create SoundPool");
	}

	mSoundMap = new HashMap<String, Integer>();
	mStreamMap = new HashMap<Integer, Integer>();
    }


    /**
     * Load a sound file for future playback.
     * 
     * @param fileName
     *            Name of the file, relative to the audio resources directory
     * @throws IOException
     */
    public void load(String fileName)
    {
	// Load the sound
	int soundDescriptor = mSoundPool.load(fileName, 1);

	// Put the sound ID in the sound map
	mSoundMap.put(fileName, soundDescriptor);
    }


    /**
     * Play a sound from a file. Ideally, the sound should be already loaded
     * with a previous load to call ahead of time, but it will be loaded now if
     * it wasn't before.
     * 
     * @param fileName
     *            Name of the file, relative to the audio resources directory
     * @volume Volume (between 0 and 1) to play sound at.
     * @return An identifier for the playing stream.
     */
    public int play(String fileName, float volume)
    {
	// Load sound if it isn't already loaded
	if (!mSoundMap.containsKey(fileName)) {
	    logger.debug("Playing sound " + fileName +
			 " that hasn't been preloaded");
	    load(fileName);
	}

	int soundId = mSoundMap.get(fileName);
	int streamId = mSoundPool.play(soundId, volume, volume, 1, 0, 1.0f);

	if (streamId == 0) {
	    // Playback failed
	    throw new RuntimeException("Audio: failed to play sound " +
				       fileName);
	}
	mStreamMap.put(soundId, streamId);

	return streamId;
    }


    /**
     * Stop a playing stream.
     * 
     * @param stream
     *            ID of stream to stop.
     */
    public void stop(int stream)
    {
	mSoundPool.stop(stream);
    }


    /**
     * Pause all sounds that are currently playing.
     */
    public void pauseAll()
    {
	for (int stream : mStreamMap.values()) {
	    mSoundPool.pause(stream);
	}
    }


    /**
     * Resume all paused sounds.
     */
    public void resumeAll()
    {
	for (int stream : mStreamMap.values()) {
	    mSoundPool.resume(stream);
	}
    }


    /**
     * Release (free) all loaded sounds.
     */
    public void unloadAll()
    {
	for (int sound : mSoundMap.values()) {
	    mSoundPool.unload(sound);
	}
    }
}
