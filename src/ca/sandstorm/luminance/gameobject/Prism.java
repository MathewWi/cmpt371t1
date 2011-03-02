package ca.sandstorm.luminance.gameobject;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.graphics.IRenderable;
import ca.sandstorm.luminance.resources.TextureResource;

public class Prism implements IRenderableObject
{
    private static final Logger logger = LoggerFactory.getLogger("Luminance.Box");

    private IRenderable _model;
    private Vector3f _position;
    private Vector4f _rotation;
    private float _orientation;
    private Vector3f _scale;
    private int _texture;
    
    private RenderType _renderType = RenderType.Normal;
    
    public Prism(Vector3f position, float orientation)
    {
	// TODO: Improve orientation
	// TODO: Make a prism renderable
	_orientation = orientation;
	_position = new Vector3f(position);
	_rotation = new Vector4f(1.0f, 0.0f, 0.0f, orientation);
	_scale = new Vector3f(0.2f, 1.0f, 1.0f);
	_model = Engine.getInstance().getRenderer().getBox();
    }

    @Override
    public void initialize()
    {
	// Texture needs to be loaded ahead of time because an instance of GL
	// is needed to load it, which is unavailable here.
	TextureResource tex = (TextureResource)Engine.getInstance().getResourceManager().getResource("textures/missing.jpg");
	if(tex == null) {
	    throw new RuntimeException("Unable to get prism texture. It hasn't been loaded yet!");
	}
	_texture = tex.getTexture();
    }

    @Override
    public void update()
    {
	// TODO Auto-generated method stub

    }


    @Override
    public void destroy()
    {
	// TODO Auto-generated method stub

    }


    @Override
    public Vector3f getPosition()
    {
	return _position;
    }


    @Override
    public Vector4f getRotation()
    {
	return _rotation;
    }


    @Override
    public Vector3f getScale()
    {
	return _scale;
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

}
