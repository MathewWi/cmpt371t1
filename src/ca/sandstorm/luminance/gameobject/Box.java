package ca.sandstorm.luminance.gameobject;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.graphics.IRenderable;
import ca.sandstorm.luminance.resources.TextureResource;


public class Box implements IRenderableObject
{
    private static final Logger logger = LoggerFactory.getLogger("Luminance.Box");

    private IRenderable model;
    private Vector3f position;
    private Vector4f rotation;
    private Vector3f scale;
    int texture;
    
    public Box(Vector3f position, Vector3f scale)
    {
	this.position = new Vector3f(position);
	rotation = new Vector4f(1.0f, 1.0f, 1.0f, 0f);
	this.scale = new Vector3f(scale);
	model = Engine.getInstance().getRenderer().getBox();
    }
    
    @Override
    public IRenderable getRenderable()
    {
	return model;
    }
	
    @Override
    public int getTexture()
    {
	return texture;
    }

    @Override
    public Vector3f getPosition()
    {
	return position;
    }
    
    @Override
    public Vector4f getRotation()
    {
        return rotation;
    }

    @Override
    public Vector3f getScale()
    {
	return scale;
    }

    @Override
    public void initialize()
    {
	// Texture needs to be loaded ahead of time because an instance of GL
	// is needed to load it, which is unavailable here.
	TextureResource tex = (TextureResource)Engine.getInstance().getResourceManager().getResource("textures/wallBrick.jpg");
	if(tex == null) {
	    throw new RuntimeException("Unable to get wallBrick texture. It hasn't been loaded yet!");
	}
	texture = tex.getTexture();
	logger.debug("Box texture: " + Integer.toString(texture));
    }

    @Override
    public void update()
    {

    }

    @Override
    public void destroy()
    {
	
    }
}
