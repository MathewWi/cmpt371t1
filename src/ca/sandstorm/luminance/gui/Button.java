package ca.sandstorm.luminance.gui;

import javax.microedition.khronos.opengles.GL10;

import ca.sandstorm.luminance.input.InputXY;

public class Button implements IWidget
{
    private float x;
    private float y;
    private float width;
    private float height;


    public Button(float x, float y, float width, float height, InputXY input)
    {
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
    }


    @Override
    public float getX()
    {
	return x;
    }


    @Override
    public void setX(float x)
    {
	this.x = x;
    }


    @Override
    public float getY()
    {
	return y;
    }


    @Override
    public void setY(float y)
    {
	this.y = y;
    }


    @Override
    public float getWidth()
    {
	return width;
    }


    @Override
    public void setWidth(float width)
    {
	this.width = width;
    }


    @Override
    public float getHeight()
    {
	return height;
    }


    @Override
    public void setHeight(float height)
    {
	this.height = height;
    }
    
    @Override
    public void draw(GL10 gl)
    {
	// Drawing code goes here.
    }
}
