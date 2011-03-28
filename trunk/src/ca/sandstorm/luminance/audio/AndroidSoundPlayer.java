package ca.sandstorm.luminance.audio;

import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.resources.MusicResource;
import ca.sandstorm.luminance.resources.SoundResource;

import android.content.Context;
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
    private static final int MAX_STREAMS = 16;
    private boolean _musicPlaying = false;

    private MediaPlayer _mediaPlayer;
    private SoundPool _soundPool;
    private HashMap<Integer, Integer> _streamMap;
    private MusicResource _currentMusic;
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
	_logger.debug("Playing sound effect: " + sound);
	assert sound != null;
	
	// Play based on the device's current volume.
	// There's conflicting info on whether this is necessary.
	AudioManager mgr = (AudioManager)Engine.getInstance().getContext().getSystemService(Context.AUDIO_SERVICE);
	float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
	float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);    
	float playVolume = volume * streamVolumeCurrent / streamVolumeMax;

	int soundId = sound.getSound();
	int streamId = _soundPool.play(soundId, playVolume, playVolume, 1, 0, 1.0f);
	_streamMap.put(soundId, streamId);

	return streamId;
    }

    /**
     * Play a song by streaming it.
     * @param file Path to song
     * @throws IOException
     */
    public void playMusic(MusicResource res) throws IOException
    {
	if (_mediaPlayer.isPlaying()) {
	    if (res == _currentMusic) {
		return;
	    } else {
		// TODO: switch songs
	    }
	}
	
	_logger.debug("Playing music: " + res.getName());
	_mediaPlayer.setDataSource(res.getMusicFd());
	_mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	_mediaPlayer.setLooping(true);
	_mediaPlayer.prepare();
	_mediaPlayer.start();
	_currentMusic = res;
	_musicPlaying = true;
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
	if (_mediaPlayer.isPlaying()) {
	    _mediaPlayer.pause();
	}
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
	if (_musicPlaying && !_mediaPlayer.isPlaying()) {
	    _mediaPlayer.start();
	}
    }


    /**
     * Release (free) all loaded sounds.
     */
    public void unloadAll()
    {
	_logger.debug("unloadAll()");
	
	_mediaPlayer.reset();
	_streamMap.clear();
	_soundPool.release();
	_soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
	_musicPlaying = false;
    }
}
