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

public class Emitter extends GameObject implements IRenderableObject
{
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger("Luminance.Emitter");

    private IRenderable _model;
    private int _texture;
    
    private RenderType _renderType = RenderType.Normal;
    
    private Sphere _colSphere;
    private int _color;
    
    private static float[] _rotationArray = new float[] { 0, 90, 180, 270 };
    private int _currentRotation = 0;
    
    
    /**
     * 
     * @param position
     * @param rotation
     * @param color
     */
    public Emitter(Vector3f position, Vector3f rotation, int color)
    {
	_position = new Vector3f(position);
	_rotation = new Vector4f(rotation);
	_scale = new Vector3f(0.15f, 0.15f, 0.5f);
	_model = Engine.getInstance().getRenderer().getBox();
	_color = color;
	
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
	TextureResource tex = (TextureResource)Engine.getInstance().getResourceManager().getResource("textures/emitter.jpg");
	if(tex == null) {
	    throw new RuntimeException("Unable to get emitter texture. It hasn't been loaded yet!");
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
    
    
    public int getColor()
    {
	return _color;
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
     * @param beamIndex The light beam
     * @param lightIndex Index of the light beam element to interact with
     */
    @Override
    public void beamInteract(LightBeamCollection beamCollection, int beamIndex, int lightIndex)
    {	
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

    
    @Override
    public float getNextYRotation()
    {
	_currentRotation++;
	if (_currentRotation >= _rotationArray.length) 
	{
	    _currentRotation = 0;
	}
	return _rotationArray[_currentRotation];
    }


    @Override
    public float getPrevYRotation()
    {
	_currentRotation--;
	if (_currentRotation < 0)
	{
	    _currentRotation = _rotationArray.length - 1;
	}
	return _rotationArray[_currentRotation];
    }

    @Override
    public float getCurrentYRotation()
    {
	return _rotationArray[_currentRotation];
    }

    @Override
    public int getYRotationCount()
    {
	return _rotationArray.length;
    }    
}
