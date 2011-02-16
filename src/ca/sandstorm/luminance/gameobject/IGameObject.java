package ca.sandstorm.luminance.gameobject;

import javax.vecmath.Vector3f;

public interface IGameObject
{
    void initialize();
    void update();
    void destroy();
    Vector3f getPosition();
}
