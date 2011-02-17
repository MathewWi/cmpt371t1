package ca.sandstorm.luminance.gui;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import ca.sandstorm.luminance.input.InputXY;

public class Button implements IWidget
{
    private float _x;
    private float _y;
    private float _width;
    private float _height;
    
    // static because all quads only need one of these, just change textures
    private static FloatBuffer _vertexBuffer;
    private static float[] _vertices;
    
    private String _title;

    /**
     * Constructor for creating a Button.
     * @param x X coordinate of the button.
     * @param y Y coordinate of the button.
     * @param width Width of the button.
     * @param height Height of the button.
     * @param title String to be used for the buton title.
     */
    public Button(float x, float y, float width, float height, String title)
    {
	this._x = x;
	this._y = y;
	this._width = width;
	this._height = height;
	_title = title;
	
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
    
    public String getTitle()
    {
	return _title;
    }
    
    public void setTitle(String title)
    {
	_title = title;
    }
    
    public void draw(GL10 gl)
    {
	gl.glPushMatrix();
	
	//gl.glScalef(this.width, this.height, 1.0f);
	gl.glTranslatef(this._x, this._y, 0);
	
	gl.glFrontFace(GL10.GL_CW);
	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
	//gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, _texCoordBuffer);
	
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	//gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
	//gl.glEnable(GL10.GL_TEXTURE_2D);
	
	gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, _vertices.length / 3);
	
	gl.glPopMatrix();
    }
}
