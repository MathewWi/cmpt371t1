package ca.sandstorm.luminance.gameobject;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.graphics.IRenderable;


public class Box implements IRenderableObject
{
    private IRenderable model;
    private Vector3f position;
    private Vector4f rotation;
    
    public Box(Vector3f position)
    {
	this.position = new Vector3f(position);
	rotation = new Vector4f(1.0f, 1.0f, 1.0f, 0f);
	model = Engine.getInstance().getRenderer().getBox();
    }
    
    public IRenderable getRenderable()
    {
	return model;
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
    public void initialize()
    {
	
    }

    @Override
    public void update()
    {
	// Box rotates, for now
	rotation.w -= 0.45f;
    }

    @Override
    public void destroy()
    {
	
    }
}
