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
     * Get the String that uniquely identifies this IWidget.
     * 
     * @return the String that uniquely identifies this IWidget.
     */
    String getIdentifier();
    
    /**
     * Set the String will uniquely identify this IWidget.
     * 
     * @param identifier the String that will uniquely identify this IWidget.
     * @precond identifier != null
     * @postcond this.getIdentifier() != null &&
     * this.getIdentifier().equals(identifier)
     */
    void setIdentifier(String identifier);
    
    /**
     * Get the X position of the button.
     * @return the X position of the button.
     */
    float getX();
    
    /**
     * Set the X position of the button
     * @param x The new X position
     * @precond x >= 0
     * @postcond this.getX() == x
     */
    void setX(float x);
    
    /**
     * Get the Y position of the button
     * @return the Y position of the button
     */
    float getY();
    
    /**
     * Set the Y position of the button
     * @param y The new Y position
     * @precond y >= negative
     * @postcond this.getY() == y
     */
    void setY(float y);
    
    /**
     * Get the Width of the button
     * @return the Width of the button
     */
    float getWidth();
    
    /**
     * Set the Width of the button
     * @param width The new width
     * @precond width >= negative
     * @postcond this.getWidth() == width
     */
    void setWidth(float width);
    
    /**
     * Get the height of the button
     * @return the height of the button
     */
    float getHeight();
    
    /**
     * Set the height of the button
     * @param height The new height
     * @precond height >= negative
     * @postcond this.getHeight() == height
     */
    void setHeight(float height);
    
    /**
     * Get the texture resource location string that locates the texture being
     * used.
     * 
     * @return texture resource location string being used by this IWidget
     */
    String getTextureResourceLocation();
    
    /**
     * Set the texture resource location string that locates the texture being
     * used.
     * 
     * @param textureResourceLocation The texture resource location string
     * @precond textureResourceLocation != null
     * @postcond this.getTextureResourceLocation().equals(textureResourceLocation) == true
     */
    void setTextureResourceLocation(String textureResourceLocation);
    
    /**
     * Get the texture being used by this IWidget.
     * 
     * @return the texture being used this IWidget
     */
    TextureResource getTexture();
    
    /**
     * Set the texture to be used by this IWidget.
     * 
     * @param texture the texture to be used by this IWidget.
     * @precond texture != null
     * @postcond this.getTexture() == texture
     */
    void setTexture(TextureResource texture);
    
    /**
     * The draw method to be called automatically by a GUIManager.
     * 
     * @param gl The GL10 that handles drawing to OpenGL.
     * @precond this.getTexture() != null
     * @postcond n/a
     */
    void draw(GL10 gl);
}
