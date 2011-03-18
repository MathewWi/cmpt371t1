package ca.sandstorm.luminance.gameobject;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.graphics.Color;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.graphics.IRenderable;
import ca.sandstorm.luminance.math.Colliders;
import ca.sandstorm.luminance.math.Sphere;
import ca.sandstorm.luminance.resources.TextureResource;

public class Prism extends GameObject implements IRenderableObject
{
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger("Luminance.Box");

    private IRenderable _model;
    private int _texture;
    
    private RenderType _renderType = RenderType.Normal;
    
    private Sphere _colSphere;
    
    public Prism(Vector3f position)
    {
	// TODO: Improve orientation
	_position = new Vector3f(position);
	_rotation = new Vector4f(1.0f, 0.0f, 0.0f, 0);
	_scale = new Vector3f(0.5f, 0.5f, 0.5f);
	_model = Engine.getInstance().getRenderer().getPrism();
	
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
	TextureResource tex = (TextureResource)Engine.getInstance().getResourceManager().getResource("textures/inGamePrism.png");
	if(tex == null) {
	    throw new RuntimeException("Unable to get prism texture. It hasn't been loaded yet!");
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
     * @param beamIndex The light beam
     * @param lightIndex Index of the light beam element to interact with
     */
    @Override
    public void beamInteract(LightBeamCollection beamCollection, int beamIndex, int lightIndex)
    {
	LightBeam beam = beamCollection.get(beamIndex);
	Light l = beam.get(lightIndex);
	l.setEndTouchedObject(this);
	
	// prism spawns 3 lights	
	// light 1, straight through, light dir
	Vector3f dir = l.getDirection();
	LightBeam newBeam = new LightBeam();
	Light newLight = new Light(_position.x, _position.y, _position.z, dir.x, dir.y, dir.z, Light.LIGHT_INFINITY, Color.RED);
	newLight.setStartTouchedObject(this);
	newBeam.add(newLight);
	beamCollection.add(newBeam);
	
	// light 2, side 1, light dir x up
	dir = Colliders.crossProduct(l.getRay().getDirection(), Colliders.UP);
	newBeam = new LightBeam();
	newLight = new Light(_position.x, _position.y, _position.z, dir.x, dir.y, dir.z, Light.LIGHT_INFINITY, Color.GREEN);
	newLight.setStartTouchedObject(this);
	newBeam.add(newLight);
	beamCollection.add(newBeam);
	
	// light 3, side 2, light dir x -up	
	dir = Colliders.crossProduct(l.getRay().getDirection(), Colliders.DOWN);
	newBeam = new LightBeam();
	newLight = new Light(_position.x, _position.y, _position.z, dir.x, dir.y, dir.z, Light.LIGHT_INFINITY, Color.BLUE);
	newLight.setStartTouchedObject(this);
	newBeam.add(newLight);
	beamCollection.add(newBeam);
	
    }

}
