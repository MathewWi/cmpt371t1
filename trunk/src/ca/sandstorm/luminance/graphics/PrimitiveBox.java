package ca.sandstorm.luminance.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * A primitive box using an index buffer.
 * @author zenja
 */
public class PrimitiveBox implements IRenderable
{
    // Vertex coordinates
    private static float[] vertices =
    {
	-0.5f, -0.5f, -0.5f,
	0.5f, -0.5f, -0.5f,
	0.5f, 0.5f, -0.5f,
	-0.5f, 0.5f, -0.5f,
	-0.5f, -0.5f, 0.5f,
	0.5f, -0.5f, 0.5f,
	0.5f, 0.5f, 0.5f,
	-0.5f, 0.5f, 0.5f
    };
    
    // Indices
    private static byte[] indices =
    {
	0, 4, 5, 0, 5, 1, 1, 5, 6, 1, 6, 2, 2, 6, 7, 2, 7, 3,
	3, 7, 4, 3, 4, 0, 4, 7, 6, 4, 6, 5, 3, 0, 1, 3, 1, 2 
    };
    
    private FloatBuffer vertexBuffer;
    private ByteBuffer indexBuffer;
    
    /**
     * Constructor.
     * Create the buffers from the data.
     */
    public PrimitiveBox()
    {
	// Create vertex buffer
	ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
	byteBuf.order(ByteOrder.nativeOrder());
	vertexBuffer = byteBuf.asFloatBuffer();
	vertexBuffer.put(vertices);
	vertexBuffer.position(0);

	// Create index buffer
	indexBuffer = ByteBuffer.allocateDirect(indices.length);
	indexBuffer.put(indices);
	indexBuffer.position(0);
    }
    
    @Override
    public void draw(GL10 gl)
    {
	// Set the face rotation
	gl.glFrontFace(GL10.GL_CW);
	
	// Point to the vertex buffer
	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	
	// Use index buffer if available, or just draw the vertices if not
	gl.glDrawElements(GL10.GL_TRIANGLES, indexBuffer.limit(), GL10.GL_UNSIGNED_BYTE, indexBuffer);

	// Restore state
	gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
