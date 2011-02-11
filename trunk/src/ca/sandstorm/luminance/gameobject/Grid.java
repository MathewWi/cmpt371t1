package ca.sandstorm.luminance.gameobject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Grid implements IGameObject
{
    private FloatBuffer _vertexBuffer;
    private ShortBuffer _indexBuffer;
    
    
    public Grid(int rows, int cols, float cellWidth, float cellHeight)
    {
	// calculate vertices
	float x = 0.0f;
	float z = 0.0f;
	float[] vertices = new float[(rows+1)*(cols+1)*3];
	
	int tmpIndex = 0;
	
	for (int i = 0; i < rows + 1; i++)
	{
	    for (int j = 0; j < cols + 1; j++)
	    {
		x = j * cellWidth;
		z = i * cellHeight;
		
		// set x,y,z
		vertices[tmpIndex++] = x;
		vertices[tmpIndex++] = 0.0f;
		vertices[tmpIndex++] = z;
	    }
	}
	
	ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
	byteBuf.order(ByteOrder.nativeOrder());
	_vertexBuffer = byteBuf.asFloatBuffer();
	_vertexBuffer.put(vertices);
	_vertexBuffer.position(0);
	
	
	// calculate indices 
	short[] indices = new short[(rows+1) * cols * 2 * 2];
	
	// horizontal line indices
	tmpIndex = 0;
	for (int i = 0; i < rows + 1; i++)
	{
	    for (int j = 0; j < cols; j++)
	    {
		indices[tmpIndex++] = (short)( j + (i * (rows+1)) );
		indices[tmpIndex++] = (short)( (j+1) + (i * (rows+1)) );
	    }
	}
	
	byteBuf = ByteBuffer.allocateDirect(indices.length * 2);
	byteBuf.order(ByteOrder.nativeOrder());
	_indexBuffer = byteBuf.asShortBuffer();
	_indexBuffer.put(indices);
	_indexBuffer.position(0);	
    }
    
    
    public void update(GL10 gl)
    {
	
    }
    
    
    public void draw(GL10 gl)
    {
	// Set the face rotation
	gl.glFrontFace(GL10.GL_CW);

	// Point to our buffers
	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
	//gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);

	// Enable the vertex and color state
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	//gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

	// Draw the vertices as triangles, based on the Index Buffer information
	gl.glDrawElements(GL10.GL_LINES, 48, GL10.GL_UNSIGNED_SHORT,
	                  _indexBuffer);

	// Disable the client state before leaving
	gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	//gl.glDisableClientState(GL10.GL_COLOR_ARRAY);	
    }
}
