package ca.sandstorm.luminance.gameobject;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.graphics.IRenderable;
import ca.sandstorm.luminance.resources.TextureResource;

/**
 * Represents a game wall object.
 * @author zenja
 */
public class Box implements IRenderableObject
{
    private static final Logger logger = LoggerFactory.getLogger("Luminance.Box");

    private IRenderable _model;
    private Vector3f _position;
    private Vector4f _rotation;
    private Vector3f _scale;
    private int _texture;
    
    private RenderType _renderType = RenderType.Normal;
    
    /**
     * Constructor.
     * Sets up the box position and scale.
     * @param position Position to put box at initially
     * @param scale Box initial scale
     */
    public Box(Vector3f position, Vector3f scale)
    {
	_position = new Vector3f(position);
	_rotation = new Vector4f(1.0f, 1.0f, 1.0f, 0f);
	_scale = new Vector3f(scale);
	_model = Engine.getInstance().getRenderer().getBox();
    }
    
    /**
     * Get the model associated with this object.
     * @return Associated model
     */
    @Override
    public IRenderable getRenderable()
    {
	return _model;
    }

    /**
     * Get the texture associated with this object.
     * @return Associated texture
     */
    @Override
    public int getTexture()
    {
	return _texture;
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

    /**
     * Initialize the object.
     * Associate with the object's texture. The texture needs to be loaded
     * before calling this function.
     */
    @Override
    public void initialize()
    {
	// Texture needs to be loaded ahead of time because an instance of GL
	// is needed to load it, which is unavailable here.
	TextureResource tex = (TextureResource)Engine.getInstance().getResourceManager().getResource("textures/wallBrick.jpg");
	if(tex == null) {
	    throw new RuntimeException("Unable to get wallBrick texture. It hasn't been loaded yet!");
	}
	_texture = tex.getTexture();
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

    @Override
    public RenderType getRenderType()
    {
	return _renderType;
    }
}
