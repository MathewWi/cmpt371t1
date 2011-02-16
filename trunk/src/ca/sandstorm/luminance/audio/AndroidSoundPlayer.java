package ca.sandstorm.luminance.audio;

import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.resources.SoundResource;

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
    private static final int MAX_STREAMS = 0;

    private SoundPool mSoundPool;
    private HashMap<Integer, Integer> mStreamMap;
    private static final Logger logger = LoggerFactory
	    .getLogger("Luminance.AndroidSoundPlayer");


    /**
     * Initialize the Android audio player manager.
     */
    public AndroidSoundPlayer()
    {
	mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
	if (mSoundPool == null) {
	    throw new RuntimeException("Audio: failed to create SoundPool");
	}

	mStreamMap = new HashMap<Integer, Integer>();
    }
    
    /**
     * Get the main audio pool.
     * @return The audio pool
     */
    public SoundPool getPool()
    {
	return mSoundPool;
    }

    /**
     * Play a sound described by the sound resource.
     * 
     * @param sound Sound resource to play
     * @volume Volume (between 0 and 1) to play sound at.
     * @return An identifier for the playing stream.
     */
    public int play(SoundResource sound, float volume)
    {
	assert sound != null;
	int soundId = sound.getSound();
	int streamId = mSoundPool.play(soundId, volume, volume, 1, 0, 1.0f);
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
	mStreamMap.remove(stream);
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
	mStreamMap.clear();
	mSoundPool.release();
	mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
    }
}
