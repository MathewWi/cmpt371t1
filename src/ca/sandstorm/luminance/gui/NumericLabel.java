package ca.sandstorm.luminance.gui;

import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.graphics.PrimitiveQuad;
import ca.sandstorm.luminance.resources.TextureResource;

public class NumericLabel implements IWidget
{
    private String _identifier;
    private float _x;
    private float _y;
    private float _width;
    private float _height;
    private int _number = 0;
    
    private LinkedList<PrimitiveQuad> _digitQuads;
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
	_texture = (TextureResource)Engine.getInstance().getResourceManager().getResource("textures/numbers.png");
	_textureResourceLocation = "textures/numbers.png";
	
	// Create the digits list and add the first one
	_digitQuads = new LinkedList<PrimitiveQuad>();
	setNumber(number);
    }

    public String getIdentifier()
    {
	return _identifier;
    }

    public void setIdentifier(String identifier)
    {
	_identifier = identifier;
    }
    
    public void setNumber(int number)
    {
	// Don't allow negative numbers
	if (_number < 0) {
	    _number = 0;
	}
	
	// Zero is a special case
	int oldNumDigits = _digitQuads.size();
	int numDigits = (number == 0) ? 1 : (int) Math.log10(number) + 1;
	
	// Add digits while necessary
	while (numDigits > oldNumDigits) {
	    _digitQuads.add(new PrimitiveQuad(new Vector3f(0,0,0), new Vector3f(_width,_height,0)));
	    oldNumDigits++;
	}
	
	// Remove digits while necessary
	while (numDigits < oldNumDigits) {
	    _digitQuads.removeLast();
	    oldNumDigits--;
	}
	
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
	gl.glEnable(GL10.GL_TEXTURE_2D);
	gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	gl.glBindTexture(GL10.GL_TEXTURE_2D, _texture.getTexture());
	
	// Go through all the digits individually
	int digitIndex = 0;
	for (PrimitiveQuad quad : _digitQuads) {
	    // Calculate value of this digit and move the texture to the appropriate place
	    float digit = (int)((_number % Math.pow(10, digitIndex+1)) / (Math.pow(10, digitIndex)));
	    quad.setWidthOffset(digit / 10f, 0.1f);
	    
	    // Draw the digit
	    gl.glPushMatrix();
	    gl.glTranslatef(_x + ((_digitQuads.size() - digitIndex - 1) * _width), _y, 0);
	    quad.draw(gl);
	    gl.glPopMatrix();
	    
	    digitIndex++;
	}
    }
}
