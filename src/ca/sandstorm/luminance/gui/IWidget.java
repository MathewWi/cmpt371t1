package ca.sandstorm.luminance.gui;

import javax.microedition.khronos.opengles.GL10;

import ca.sandstorm.luminance.resources.TextureResource;

/**
 * User interface Widget interface
 * 
 * @author Kumaran Vijayan
 *
 */
public interface IWidget
{
    /**
     * Get the X position of the button.
     * @return the X position of the button.
     */
    public float getX();
    
    /**
     * Set the X position of the button
     * @param x The new X position
     * @precond x >= 0
     * @postcond this.getX() == x
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
     * @precond y >= negative
     * @postcond this.getY() == y
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
     * @precond width >= negative
     * @postcond this.getWidth() == width
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
     * @precond height >= negative
     * @postcond this.getHeight() == height
     */
    public void setHeight(float height);
    
    /**
     * Get the texture resource location string that locates the texture being
     * used.
     * 
     * @return texture resource location string being used by this IWidget
     */
    public String getTextureResourceLocation();
    
    /**
     * Set the texture resource location string that locates the texture being
     * used.
     * 
     * @param textureResourceLocation The texture resource location string
     * @precond textureResourceLocation != null
     * @postcond this.getTextureResourceLocation().equals(textureResourceLocation) == true
     */
    public void setTextureResourceLocation(String textureResourceLocation);
    
    /**
     * Get the texture being used by this IWidget.
     * 
     * @return the texture being used this IWidget
     */
    public TextureResource getTexture();
    
    /**
     * Set the texture to be used by this IWidget.
     * 
     * @param texture the texture to be used by this IWidget.
     * @precond texture != null
     * @postcond this.getTexture() == texture
     */
    public void setTexture(TextureResource texture);
    
    // Every widget must know how to draw itself.
    public void draw(GL10 gl);
}
