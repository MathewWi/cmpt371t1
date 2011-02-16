package ca.sandstorm.luminance.gui;

import javax.microedition.khronos.opengles.GL10;

public class Label implements IWidget
{

    String title;
    
    public Label(String title)
    {
	this.title = title;
    }
    
    public String getTitle()
    {
	return title;
    }
    
    @Override
    public float getX()
    {
	// TODO Auto-generated method stub
	return 0;
    }


    @Override
    public void setX(float x)
    {
	// TODO Auto-generated method stub

    }


    @Override
    public float getY()
    {
	// TODO Auto-generated method stub
	return 0;
    }


    @Override
    public void setY(float y)
    {
	// TODO Auto-generated method stub

    }


    @Override
    public float getWidth()
    {
	// TODO Auto-generated method stub
	return 0;
    }


    @Override
    public void setWidth(float width)
    {
	// TODO Auto-generated method stub

    }


    @Override
    public float getHeight()
    {
	// TODO Auto-generated method stub
	return 0;
    }


    @Override
    public void setHeight(float height)
    {
	// TODO Auto-generated method stub

    }


    @Override
    public void draw(GL10 gl)
    {
	// TODO Auto-generated method stub

    }

}
