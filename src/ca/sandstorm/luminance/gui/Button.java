package ca.sandstorm.luminance.gui;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import ca.sandstorm.luminance.input.InputXY;

public class Button implements IWidget
{
    private float x;
    private float y;
    private float width;
    private float height;
    
    // static because all quads only need one of these, just change textures
    private static FloatBuffer _vertexBuffer;
    private static float[] _vertices;

    public Button(float x, float y, float width, float height, InputXY input)
    {
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
	
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


    @Override
    public float getX()
    {
	return x;
    }


    @Override
    public void setX(float x)
    {
	this.x = x;
    }


    @Override
    public float getY()
    {
	return y;
    }


    @Override
    public void setY(float y)
    {
	this.y = y;
    }


    @Override
    public float getWidth()
    {
	return width;
    }


    @Override
    public void setWidth(float width)
    {
	this.width = width;
    }


    @Override
    public float getHeight()
    {
	return height;
    }


    @Override
    public void setHeight(float height)
    {
	this.height = height;
    }
    
    @Override
    public void draw(GL10 gl)
    {
	gl.glPushMatrix();
	
	//gl.glScalef(this.width, this.height, 1.0f);
	gl.glTranslatef(this.x, this.y, 0);
	
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
