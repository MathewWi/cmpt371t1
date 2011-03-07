package ca.sandstorm.luminance.audio;

import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.resources.SoundResource;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;


/**
 * Android sound player manager.
 * 
 * @author Zenja
 */
public class AndroidSoundPlayer implements IAudioDriver
{
    // TODO: Remove sound streams from map when they're done
    private static final int MAX_STREAMS = 8;

    private MediaPlayer _mediaPlayer;
    private SoundPool _soundPool;
    private HashMap<Integer, Integer> _streamMap;
    private static final Logger _logger = LoggerFactory
	    .getLogger("Luminance.AndroidSoundPlayer");


    /**
     * Initialize the Android audio player manager.
     */
    public AndroidSoundPlayer()
    {
	_logger.debug("AndroidSoundPlayer()");
	
	_mediaPlayer = new MediaPlayer();
	if(_mediaPlayer == null) {
	    throw new RuntimeException("Audio: failed to create MediaPlayer");
	}
	
	_soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
	if (_soundPool == null) {
	    throw new RuntimeException("Audio: failed to create SoundPool");
	}
	
	_streamMap = new HashMap<Integer, Integer>();
    }
    
    /**
     * Get the main audio pool.
     * @return The audio pool
     */
    public SoundPool getPool()
    {
	return _soundPool;
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
	_logger.debug("play(" + sound + ", " + volume + ")");
	
	assert sound != null;
	int soundId = sound.getSound();
	int streamId = _soundPool.play(soundId, volume, volume, 1, 0, 1.0f);
	_streamMap.put(soundId, streamId);

	return streamId;
    }

    /**
     * Play a song by streaming it.
     * @param file Path to song
     * @throws IOException
     */
    public void playMusic(String file) throws IOException
    {
	//_mediaPlayer.setDataSource(Engine.getInstance().getResourceManager().loadResource(file).getAssetFd());
	_mediaPlayer.setDataSource(file);
	_mediaPlayer.setLooping(true);
	_mediaPlayer.prepare();
	_mediaPlayer.start();
    }


    /**
     * Stop a playing stream.
     * 
     * @param stream
     *            ID of stream to stop.
     */
    public void stop(int stream)
    {
	_logger.debug("stop(" + stream + ")");
	
	_soundPool.stop(stream);
	_streamMap.remove(stream);
    }


    /**
     * Pause all sounds that are currently playing.
     */
    public void pauseAll()
    {
	_logger.debug("pauseAll()");
	
	// Stop all sound effects
	for (int stream : _streamMap.values()) {
	    _soundPool.pause(stream);
	}
	
	// Stop music
	_mediaPlayer.start();
    }


    /**
     * Resume all paused sounds.
     */
    public void resumeAll()
    {
	_logger.debug("resumeAll()");
	
	// Resume all sound effects
	for (int stream : _streamMap.values()) {
	    _soundPool.resume(stream);
	}
	
	// Resume music
	_mediaPlayer.pause();
    }


    /**
     * Release (free) all loaded sounds.
     */
    public void unloadAll()
    {
	_logger.debug("unloadAll()");
	
	_streamMap.clear();
	_soundPool.release();
	_soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
    }
}
