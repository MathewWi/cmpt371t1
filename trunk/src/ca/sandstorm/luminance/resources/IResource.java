package ca.sandstorm.luminance.resources;

public interface IResource
{
    String getName();
    int getMemorySize();
    byte[] getData();
    void dispose();
}
