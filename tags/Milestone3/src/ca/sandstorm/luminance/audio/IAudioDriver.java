package ca.sandstorm.luminance.audio;

import ca.sandstorm.luminance.resources.SoundResource;

public interface IAudioDriver
{
    int play(SoundResource sound, float volume);


    void stop(int stream);


    void pauseAll();


    void resumeAll();


    void unloadAll();
}
