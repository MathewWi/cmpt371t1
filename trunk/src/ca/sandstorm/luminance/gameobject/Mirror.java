package ca.sandstorm.luminance.gameobject;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.graphics.IRenderable;
import ca.sandstorm.luminance.math.Colliders;
import ca.sandstorm.luminance.math.Plane;
import ca.sandstorm.luminance.math.Sphere;
import ca.sandstorm.luminance.resources.TextureResource;

public class Mirror extends GameObject implements IRenderableObject
{
    private static final Logger _logger = LoggerFactory.getLogger(Mirror.class);

    private IRenderable _model;
    private int _texture;
    
    private RenderType _renderType = RenderType.Normal;
    
    private Sphere _colSphere;
    private Plane _colPlane;
    
    private Light _lightLastTouched = null;
    
    public Mirror(Vector3f position, Vector3f rotation)
    {
	_logger.debug("Mirror(" + position + ", " + rotation + ")");
		
	// TODO: Improve orientation
	_position = new Vector3f(position);
	_rotation = new Vector4f(rotation);
	_scale = new Vector3f(0.1f, 0.5f, 0.5f);
	_model = Engine.getInstance().getRenderer().getBox();
	
	_colSphere = new Sphere(_position.x, _position.y, _position.z, 0.5f);
	
	Vector3f norm = new Vector3f(1,0,0);
	Vector3f result = new Vector3f();
	Matrix3f mat = new Matrix3f();
	mat.setIdentity();
	mat.rotY((float)Math.toRadians(-rotation.y));
	Colliders.Transform(norm, mat, result);
	
	_colPlane = new Plane(_position.x, _position.y, _position.z, result.x, result.y, result.z);
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
	TextureResource tex = (TextureResource)Engine.getInstance().getResourceManager().getResource("textures/inGameMirror.png");
	if(tex == null) {
	    throw new RuntimeException("Unable to get mirror texture. It hasn't been loaded yet!");
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
	//_logger.debug("beamInteract(" + beam + ", " + lightIndexToInteract + ")");
	
	LightBeam beam = beamCollection.get(beamIndex);
	
	// get the old light
	Light l = beam.get(lightIndex);
	
	// skip if this is same light
	if (_lightLastTouched != null)
	{
	    //return;
	}
	
	// if this mirror is breaking a light beam
	// this is not the end light
	while (lightIndex < beam.size()-1)
	{
	    // this is a brick, remove all fragments
	    beam.removeLast();
	}	
	
	float newDistance = (float)Colliders.distance(this.getPosition(), l.getPosition());
	l.setDistance(newDistance);
	
	// tell the old light it is touching this
	l.setEndTouchedObject(this);
	
	// calc new direction of light -- OLD
	//Vector3f dir = Colliders.crossProduct(l.getRay().getDirection(), Colliders.UP);
	
	// Reflect the light ray
	// r = i - (2 * n * dot(i, n))
	Vector3f dir = new Vector3f();
	Vector3f tmpNorm = new Vector3f(_colPlane.getNormal());
	float dot = 2.0f * (float)Colliders.dotProduct(l.getRay().getDirection(), _colPlane.getNormal());
	tmpNorm.scale(dot);
	dir.set(l.getRay().getDirection());
	dir.sub(tmpNorm);
		
	// if a beam collides with a mirror we need to create a new fragment
	Light newL = new Light(_position.x, _position.y, _position.z, 
	                       dir.x, dir.y, dir.z, 
	                       Light.LIGHT_INFINITY,
	                       l.getColor());
	newL.setStartTouchedObject(this);
	_lightLastTouched = l;
	
	// add new beam
	beam.add(newL);
    }

}
