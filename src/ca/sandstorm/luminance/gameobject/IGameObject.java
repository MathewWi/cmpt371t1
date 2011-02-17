package ca.sandstorm.luminance.gameobject;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public interface IGameObject
{
    void initialize();
    void update();
    void destroy();
    Vector3f getPosition();
    Vector4f getRotation();
	Vector3f getScale();
}
