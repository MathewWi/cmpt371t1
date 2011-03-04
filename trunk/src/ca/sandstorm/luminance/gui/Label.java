package ca.sandstorm.luminance.gui;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import ca.sandstorm.luminance.graphics.PrimitiveBox;
import ca.sandstorm.luminance.resources.TextureResource;

/**
 * Standard label for showing static text.
 * 
 * @author Kumaran Vijayan
 *
 */
public class Label implements IWidget
{
    private float _x;
    private float _y;
    private float _width;
    private float _height;
    private String _text;
    
    // static because all quads only need one of these, just change textures
    private static FloatBuffer _vertexBuffer;
    private static float[] _vertices;
    
    private String _textureResourceLocation;
    private TextureResource _texture;

    /**
     * Constructor for creating a Label.
     * @param text String to use for the Label.
     * @precond n/a
     * @postcond this.getText() == text
     */
    public Label(float x, float y, float width, float height, String text)
    {
	_x = x;
	_y = y;
	_width = width;
	_height = height;
	_text = text;
	
	// allocate these only once
	if (_vertices == null || _vertexBuffer == null)
	{
	     _vertices = new float[] {
		         // Vertices for the square
		          0, height,  0.0f,  // 0. left-bottom
		          width, height,  0.0f,  // 1. right-bottom
		          0,  0,  0.0f,  // 2. left-top
		          width,  0,  0.0f   // 3. right-top
		};
        	
        	ByteBuffer byteBuf = ByteBuffer.allocateDirect(_vertices.length * 4);
        	byteBuf.order(ByteOrder.nativeOrder());
        	_vertexBuffer = byteBuf.asFloatBuffer();
        	_vertexBuffer.put(_vertices);
        	_vertexBuffer.position(0);
	}
    }
    
    /**
     * Get the String title for the label.
     * @return the String that is being used for the content of the Label.
     */
    public String getText()
    {
	return _text;
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
	gl.glPushMatrix();
	
	PrimitiveBox box = new PrimitiveBox();
	gl.glEnable(GL10.GL_TEXTURE_2D);
	gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	gl.glBindTexture(GL10.GL_TEXTURE_2D, _texture.getTexture());

	//gl.glScalef(this.width, this.height, 1.0f);
	gl.glTranslatef(this._x + _width/2, this._y + _height/2, -10);
	gl.glScalef(100f, 15f, 2f);
	
	box.draw(gl);
	
	gl.glPopMatrix();
    }
}
