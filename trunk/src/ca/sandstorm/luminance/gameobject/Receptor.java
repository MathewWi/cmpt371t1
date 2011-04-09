package ca.sandstorm.luminance.gameobject;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.graphics.IRenderable;
import ca.sandstorm.luminance.level.Color;
import ca.sandstorm.luminance.math.Colliders;
import ca.sandstorm.luminance.math.Sphere;
import ca.sandstorm.luminance.resources.TextureResource;

/**
 * Light receptor game object.
 */
public class Receptor extends GameObject implements IRenderableObject
{
    private static final Logger _logger = LoggerFactory.getLogger(Receptor.class);

    private IRenderable _model;
    private int _texture;    
    private RenderType _renderType = RenderType.Normal;
    
    private Sphere _colSphere;
    
    private int _deactivatedColor;
    private int _activatedColor;
    private boolean _activated;
    
    private float _scaleCounter;
    
    private Vector3f _originalScale;
    
    /**
     * Constructor.
     * Sets up the receptor position and scale.
     * @param position Position to put receptor at initially
     * @param scale receptor initial scale
     */
    public Receptor(Vector3f position, Vector3f scale)
    {
	_logger.debug("Receptor(" + position + ", " + scale + ")");
	
	_position = new Vector3f(position);
	_rotation = new Vector4f(1.0f, 1.0f, 1.0f, 0f);
	_scale = new Vector3f(scale);
	_originalScale = new Vector3f(scale);
	
	_model = Engine.getInstance().getRenderer().getSphere();
	
	_colSphere = new Sphere(_position.x, _position.y, _position.z, 0.5f);
	
	_activated = false;
    }
    
    
    /**
     * Destroy the object.
     */
    @Override
    public void destroy()
    {
	
    }
    
    
    /**
     * Set the receptor color.
     * @param color Android color
     */
    public void setColor(int color)
    {
	_activatedColor = color;
	int red = (int)(Color.red(color) / 1.5);
	int green = (int)(Color.green(color) / 1.5);
	int blue = (int)(Color.blue(color) / 1.5);
	_deactivatedColor = Color.argb(185, red, green, blue);
    }
    
    
    /**
     * Get the receptor color.
     * @return Android color
     */
    public int getColor()
    {
	if (_activated)
	{
	    return _activatedColor;
	}
	else
	{
	   return _deactivatedColor;
	}
    }
    
    
    public void setActivated(boolean b)
    {
	_activated = b;
    }
    
    
    /**
     * Check if this receptor is activated by the right light beam.
     * @return True if activate, false if not
     */
    public boolean getActivated()
    {
	return _activated;
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
	TextureResource tex = (TextureResource)Engine.getInstance().getResourceManager().getResource("textures/inGameMirror.png");
	if(tex == null) {
	    throw new RuntimeException("Unable to get receptor texture. It hasn't been loaded yet!");
	}
	_texture = tex.getTexture();
    }

    /**
     * Update the object state.
     */
    @Override
    public void update()
    {
	// For fun
	/*if (_activated)
	{
	    _scaleCounter += 0.05f;
	    float delim = (float)((Math.sin(_scaleCounter) * 0.10));
	    _scale.x = _originalScale.x + delim;
	    _scale.y = _originalScale.y + delim;
	    _scale.z = _originalScale.z + delim;
	}
	else
	{
	    _scale.x = _originalScale.x;
	    _scale.y = _originalScale.y;
	    _scale.z = _originalScale.z;
	}*/
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
	Light l = beam.get(lightIndex);
	if (_activatedColor == l.getColor())
	{
	    setActivated(true);	    
	}
	
	l.setDistance( (float)Colliders.distance(l.getStartPoint(), this.getPosition()) );
	l.setEndTouchedObject(this);
    }


    @Override
    public float getNextYRotation()
    {
	// TODO Auto-generated method stub
	return 0;
    }


    @Override
    public float getPrevYRotation()
    {
	// TODO Auto-generated method stub
	return 0;
    }


    @Override
    public float getCurrentYRotation()
    {
	return 0;
    }


    @Override
    public int getYRotationCount()
    {
	// TODO Auto-generated method stub
	return 0;
    }
}
