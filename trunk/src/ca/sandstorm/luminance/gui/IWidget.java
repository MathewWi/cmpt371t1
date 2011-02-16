package ca.sandstorm.luminance.gui;

import javax.microedition.khronos.opengles.GL10;

public interface IWidget
{
    // Standard x,y coordinates and width,height dimensions
    public float getX();
    public void setX(float x);
    public float getY();
    public void setY(float y);
    public float getWidth();
    public void setWidth(float width);
    public float getHeight();
    public void setHeight(float height);
    
    // Every widget must know how to draw itself.
    public void draw(GL10 gl);
}
