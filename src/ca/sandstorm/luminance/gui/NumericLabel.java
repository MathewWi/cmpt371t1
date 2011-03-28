package ca.sandstorm.luminance.gui;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.graphics.PrimitiveQuad;
import ca.sandstorm.luminance.resources.TextureResource;

public class NumericLabel implements IWidget
{
    private float _x;
    private float _y;
    private float _width;
    private float _height;
    private int _number;
    
    private PrimitiveQuad _quad;
    private String _textureResourceLocation;
    private TextureResource _texture;

    /**
     * Constructor for creating a Label.
     * @param text String to use for the Label.
     * @precond n/a
     * @postcond this.getText() == text
     */
    public NumericLabel(float x, float y, float width, float height, int number)
    {
	_x = x;
	_y = y;
	_width = width;
	_height = height;
	_number = number;
	_texture = (TextureResource)Engine.getInstance().getResourceManager().getResource("textures/numbers.png");
	_textureResourceLocation = "textures/numbers.png";
	
	_quad = new PrimitiveQuad(
	                          new Vector3f(0, 0, 0),
	                          new Vector3f(width, height, 0)
	);
    }
    
    public void setNumber(int number)
    {
	// TODO: Temporary until multidigit support is added
	if (_number < 0)
	    _number = 0;
	else if (_number > 9)
	    _number = 9;
	else
	    _number = number;
    }
    
    /**
     * Get the String title for the label.
     * @return the String that is being used for the content of the Label.
     */
    public String getText()
    {
	return Integer.toString(_number);
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

    public void draw(GL10 gl)
    {
	// Move to the proper digit in the texture
	_quad.setWidthOffset((float)_number * (9f/10f), 0.1f);
	
	gl.glPushMatrix();
	
	gl.glEnable(GL10.GL_TEXTURE_2D);
	gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	gl.glBindTexture(GL10.GL_TEXTURE_2D, _texture.getTexture());
	gl.glTranslatef(_x, _y, 0);
	_quad.draw(gl);	
	
	gl.glPopMatrix();
    }
}
