package ca.sandstorm.luminance.gameobject;

import javax.vecmath.Vector3f;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.graphics.IRenderable;


public class Box implements IRenderableObject
{
    private IRenderable model;
    private Vector3f position;
    
    public Box(Vector3f position)
    {
	this.position = position;
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
    public void initialize()
    {
	// TODO Auto-generated method stub
	
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
}
