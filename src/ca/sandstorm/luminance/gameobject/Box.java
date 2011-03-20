package ca.sandstorm.luminance.gameobject;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.graphics.IRenderable;
import ca.sandstorm.luminance.math.Colliders;
import ca.sandstorm.luminance.math.Sphere;
import ca.sandstorm.luminance.resources.TextureResource;

/**
 * Represents a game wall object.
 * @author zenja
 */
public class Box extends GameObject implements IRenderableObject
{
    private static final Logger _logger = LoggerFactory.getLogger("Luminance.Box");

    protected IRenderable _model;
    protected int _texture;
    
    private RenderType _renderType = RenderType.Normal;
    
    private Sphere _colSphere;
    
    
    /**
     * Constructor.
     * Sets up the box position and scale.
     * @param position Position to put box at initially
     * @param scale Box initial scale
     */
    public Box(Vector3f position, Vector3f scale)
    {
	_logger.debug("Box(" + position + ", " + scale + ")");
	
	_position = new Vector3f(position);
	_rotation = new Vector4f(0, 0, 0, 0f);
	_scale = new Vector3f(scale);
	_model = Engine.getInstance().getRenderer().getBox();
	
	_colSphere = new Sphere(_position.x, _position.y, _position.z, scale.z);	
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
     * Destroy the object.
     */
    @Override
    public void destroy()
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
     * Get the type of rendering to use on this object.
     * @param Render type
     */
    @Override
    public RenderType getRenderType()
    {
	return _renderType;
    }

    /**
     * Defines how the object interacts with a lightbeam.
     * @param beamIndex The light beam
     * @param lightIndex Index of the light beam element to interact with
     */
    @Override
    public void beamInteract(LightBeamCollection beamCollection, int beamIndex, int lightIndex)
    {
	//_logger.debug("beamInteract(" + beam + ", " + lightIndexToInteract + ")");
	
	LightBeam beam = beamCollection.get(beamIndex);
	
	// this is not the end light
	while (lightIndex < beam.size()-1)
	{
	    // this is a brick, remove all fragments
	    beam.removeLast();
	}
	
	// adjust the last piece (the one hitting the brick)
	Light l = beam.get(lightIndex);
	float newDistance = (float)Colliders.distance(this.getPosition(), l.getPosition());
	l.setDistance(newDistance);
	
	// set the object this light is touching
	l.setEndTouchedObject(this);
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
}
