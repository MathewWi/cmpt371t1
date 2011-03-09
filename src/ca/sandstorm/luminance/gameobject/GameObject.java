package ca.sandstorm.luminance.gameobject;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;


public abstract class GameObject implements IGameObject
{
    protected Vector3f _position;
    protected Vector4f _rotation;
    protected Vector3f _scale;
    
    public GameObject()
    {
	_position = new Vector3f(0.0f, 0.0f, 0.0f);
	_rotation = new Vector4f(1.0f, 1.0f, 1.0f, 0f);
	_scale = new Vector3f(1.0f, 1.0f, 1.0f);
    }
    
    /**
     * Initialize the object.
     * Associate with the object's texture. The texture needs to be loaded
     * before calling this function.
     */
    @Override
    public void initialize()
    {
    }


    /**
     * Update the object state.
     */
    @Override
    public void update()
    {

    }


    /**
     * Destroy the object.
     */
    @Override
    public void destroy()
    {
	
    }


    /**
     * Get the object's position.
     * @return Object position
     */
    @Override
    public Vector3f getPosition()
    {
	return _position;
    }
        
    /**
     * Set the object's position.
     * @param position New position
     */
    @Override
    public void setPosition(Vector3f position)
    {
        _position = new Vector3f(position);
    }


    /**
     * Get the object's rotation.
     * @return Object rotation
     */
    @Override
    public Vector4f getRotation()
    {
        return _rotation;
    }


    /**
     * Get the object's scale.
     * @return Object scale
     */
    @Override
    public Vector3f getScale()
    {
	return _scale;
    }

}
