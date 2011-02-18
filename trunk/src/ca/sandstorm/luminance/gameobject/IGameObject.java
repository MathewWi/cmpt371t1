package ca.sandstorm.luminance.gameobject;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public interface IGameObject
{
    /**
     * Initialize the object.
     * Associate with the object's texture. The texture needs to be loaded
     * before calling this function.
     */
    void initialize();
    
    /**
     * Update the object state.
     */
    void update();
    
    /**
     * Destroy the object.
     */
    void destroy();
    
    /**
     * Get the object's position.
     * @return Object position
     */
    Vector3f getPosition();
    
    /**
     * Get the object's rotation.
     * @return Object rotation
     */
    Vector4f getRotation();
    
    /**
     * Get the object's scale.
     * @return Object scale
     */
    Vector3f getScale();
}
