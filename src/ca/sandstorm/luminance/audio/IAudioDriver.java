package ca.sandstorm.luminance.audio;

public interface IAudioDriver
{
    void load(String fileName);
    int play(String fileName, float volume);
    void stop(int stream);
    void pauseAll();
    void resumeAll();
    void unloadAll();
}
