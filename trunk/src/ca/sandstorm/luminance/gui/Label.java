package ca.sandstorm.luminance.gui;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

import ca.sandstorm.luminance.graphics.PrimitiveQuad;
import ca.sandstorm.luminance.resources.TextureResource;

/**
 * Standard label for showing static text.
 * 
 * @author Kumaran Vijayan
 *
 */
public class Label implements IWidget
{
    protected float _x;
    protected float _y;
    private float _width;
    private float _height;
    protected String _identifier;
    
    protected PrimitiveQuad _quad;
    private String _textureResourceLocation;
    protected TextureResource _texture;

    protected boolean _isVisible;
    
    /**
     * Constructor for creating a Label.
     * 
     * @param x The x position for the Label.
     * @param y The y position for the Label.
     * @param width The width for the Label.
     * @param height The height for the Label.
     * @param identifier String to use for the Label.
     * @precond n/a
     * @postcond this.getX() == x && this.getY() == y &&
     * this.getWidth() == width && this.getHeight() == height &&
     * this.getIdentifier() == identifier
     */
    public Label(float x, float y, float width, float height, String identifier)
    {
	_x = x;
	_y = y;
	_width = width;
	_height = height;
	_identifier = identifier;
	
	_isVisible = true;
	
	_quad = new PrimitiveQuad(
	                          new Vector3f(0, 0, 0),
	                          new Vector3f(width, height, 0)
	);
    }
    
    /**
     * Constructor for creating a Label with a texture resource location.
     * 
     * @param x The x position for the Label.
     * @param y The y position for the Label.
     * @param width The width for the Label.
     * @param height The height for the Label.
     * @param identifier String to use for the Label.
     * @param textureLocation The location of the texture to be used for the Label.
     * @precond n/a
     * @postcond this.getX() == x && this.getY() == y &&
     * this.getWidth() == width && this.getHeight() == height &&
     * this.getIdentifier() == identifier &&
     * this.getTextureResourceLocation() == textureResourceLocation
     */
    public Label(float x, float y, float width, float height, String identifier, String textureLocation)
    {
	this(x, y, width, height, identifier);
	
	_isVisible = true;
	
	_textureResourceLocation = textureLocation;
    }

    public String getIdentifier()
    {
	return _identifier;
    }

    public void setIdentifier(String identifier)
    {
	_identifier = identifier;
    }
    
    public float getX()
    {
	return _x;
    }

    public void setX(float x)
    {
	_x = x;
    }

    public float getY()
    {
	return _y;
    }

    public void setY(float y)
    {
	_y = y;
    }

    public float getWidth()
    {
	return _width;
    }

    public void setWidth(float width)
    {
	_width = width;
    }

    public float getHeight()
    {
	return _height;
    }

    public void setHeight(float height)
    {
	_height = height;
    }

    public String getTextureResourceLocation()
    {
	return _textureResourceLocation;
    }

    public void setTextureResourceLocation(String textureResourceLocation)
    {
	_textureResourceLocation = textureResourceLocation;
    }

    public TextureResource getTexture()
    {
	return _texture;
    }

    public void setTexture(TextureResource texture)
    {
	_texture = texture;
    }
    
    public boolean getVisible()
    {
	return _isVisible;
    }
    
    public void setVisible(boolean b)
    {
	_isVisible = b;
    }

    public void draw(GL10 gl)
    {
	if (_isVisible)
	{
	    gl.glPushMatrix();
	
	    gl.glEnable(GL10.GL_TEXTURE_2D);
	    gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	    gl.glBindTexture(GL10.GL_TEXTURE_2D, _texture.getTexture());
	    gl.glTranslatef(_x, _y, 0);
	    _quad.draw(gl);	
	
	    gl.glPopMatrix();
	}
    }
}
