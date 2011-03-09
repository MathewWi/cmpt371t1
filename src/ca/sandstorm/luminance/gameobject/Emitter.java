package ca.sandstorm.luminance.gameobject;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.graphics.IRenderable;
import ca.sandstorm.luminance.math.Sphere;
import ca.sandstorm.luminance.resources.TextureResource;

public class Emitter extends GameObject implements IRenderableObject
{
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger("Luminance.Emitter");

    private IRenderable _model;
    private int _texture;
    
    private RenderType _renderType = RenderType.Normal;
    
    private Sphere _colSphere;
    
    public Emitter(Vector3f position, Vector3f rotation)
    {
	_position = new Vector3f(position);
	_rotation = new Vector4f(rotation);
	_scale = new Vector3f(0.15f, 0.15f, 0.5f);
	_model = Engine.getInstance().getRenderer().getBox();
	
	_colSphere = new Sphere(_position.x, _position.y, _position.z, 0.5f);	
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
	TextureResource tex = (TextureResource)Engine.getInstance().getResourceManager().getResource("textures/missing.jpg");
	if(tex == null) {
	    throw new RuntimeException("Unable to get emitter texture. It hasn't been loaded yet!");
	}
	_texture = tex.getTexture();
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
     * Get the OpenGL texture to use for rendering this object.
     * @return Texture ID
     */
    @Override
    public int getTexture()
    {
	return _texture;
    }

    /**
     * Get the type of rendering to use on this object.
     * @param Render type
     */
    @Override
    public RenderType getRenderType()
    {
	return _renderType;
    }

    /**
     * Return the collision detection sphere.
     * @return Collision sphere
     */
    @Override
    public Sphere getCollisionSphere()
    {
	// TODO Auto-generated method stub
	return _colSphere;
    }

    /**
     * Defines how the object interacts with a lightbeam.
     * @param beam The light beam
     * @param lightIndexToInteract Index of the light beam element to interact with
     */
    @Override
    public void beamInteract(LightBeam beam, int lightIndexToInteract)
    {
	// TODO Auto-generated method stub
	
    }

}
