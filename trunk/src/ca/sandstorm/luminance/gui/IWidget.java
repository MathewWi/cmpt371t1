package ca.sandstorm.luminance.gui;

import javax.microedition.khronos.opengles.GL10;

public interface IWidget
{
    // Standard x,y coordinates and width,height dimensions
    /**
     * Get the X position of the button.
     * @return the X position of the button.
     */
    public float getX();
    
    /**
     * Set the X position of the button
     * @param x The new X position
     */
    public void setX(float x);
    
    /**
     * Get the Y position of the button
     * @return the Y position of the button
     */
    public float getY();
    
    /**
     * Set the Y position of the button
     * @param y The new Y position
     */
    public void setY(float y);
    
    /**
     * Get the Width of the button
     * @return the Width of the button
     */
    public float getWidth();
    
    /**
     * Set the Width of the button
     * @param width The new width
     */
    public void setWidth(float width);
    
    /**
     * Get the height of the button
     * @return the height of the button
     */
    public float getHeight();
    
    /**
     * Set the height of the button
     * @param height The new height
     */
    public void setHeight(float height);
    
    // Every widget must know how to draw itself.
    public void draw(GL10 gl);
}
