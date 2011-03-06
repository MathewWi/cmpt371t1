package ca.sandstorm.luminance.gameobject;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import ca.sandstorm.luminance.gametools.LightPath;

public interface IGameObject
{
    /**
     * Initialize the object.
     * Associate with the object's texture. The texture needs to be loaded
     * before calling this function.
     */
    void initialize(Grid grid);
    
    /**
     * Update the object state.
     */
    void update();
    
    /**
     * Destroy the object.
     */
    void destroy();
    
    /**
     * Influence and interact with the light beam.
     * @param beam The light beam
     */
    void beamInteract(LightPath beam);
    
    /**
     * Get the object's position.
     * @return Object position
     */
    Vector3f getPosition();
    
    int getGridPositionX();
    
    int getGridPositionY();
    
    /**
     * Set the object's position.
     * @param position New position
     */
    void setPosition(Vector3f position);
    
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
