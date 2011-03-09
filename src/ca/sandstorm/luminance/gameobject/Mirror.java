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

public class Mirror extends GameObject implements IRenderableObject
{
    private static final Logger _logger = LoggerFactory.getLogger(Mirror.class);

    private IRenderable _model;
    private int _texture;
    
    private RenderType _renderType = RenderType.Normal;
    
    private Sphere _colSphere;
    
    public Mirror(Vector3f position, Vector3f rotation)
    {
	_logger.debug("Mirror(" + position + ", " + rotation + ")");
	
	// TODO: Improve orientation
	_position = new Vector3f(position);
	_rotation = new Vector4f(rotation);
	_scale = new Vector3f(0.1f, 0.5f, 0.5f);
	_model = Engine.getInstance().getRenderer().getBox();
	
	_colSphere = new Sphere(_position.x, _position.y, _position.z, 0.5f);	
    }

    @Override
    public void initialize()
    {
	// Texture needs to be loaded ahead of time because an instance of GL
	// is needed to load it, which is unavailable here.
	TextureResource tex = (TextureResource)Engine.getInstance().getResourceManager().getResource("textures/missing.jpg");
	if(tex == null) {
	    throw new RuntimeException("Unable to get mirror texture. It hasn't been loaded yet!");
	}
	_texture = tex.getTexture();
    }


    @Override
    public IRenderable getRenderable()
    {
	return _model;
    }


    @Override
    public int getTexture()
    {
	return _texture;
    }


    @Override
    public RenderType getRenderType()
    {
	return _renderType;
    }

    @Override
    public Sphere getCollisionSphere()
    {
	// TODO Auto-generated method stub
	return _colSphere;
    }

    @Override
    public void beamInteract(LightBeam beam, int lightIndexToInteract)
    {
	//_logger.debug("beamInteract(" + beam + ", " + lightIndexToInteract + ")");
	
	// if this mirror is breaking a light beam
	// this is not the end light
	while (lightIndexToInteract < beam.size()-1)
	{
	    // this is a brick, remove all fragments
	    beam.removeLast();
	}	
	
	// get the old light
	Light l = beam.get(lightIndexToInteract);
	float newDistance = (float)Colliders.distance(this.getPosition(), l.getPosition());
	l.setDistance(newDistance);
	
	// if a beam collides with a mirror we need to create a new fragment
	Light newL = new Light(_position.x, _position.y, _position.z, 
	                       0, 0, 1, 
	                       Light.LIGHT_INFINITY,
	                       l.getColor());
	
	beam.add(newL);
    }

}
