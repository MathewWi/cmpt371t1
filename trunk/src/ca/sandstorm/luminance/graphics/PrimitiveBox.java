package ca.sandstorm.luminance.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

/**
 * A primitive box using an index buffer.
 * @author zenja
 */
public class PrimitiveBox implements IRenderable
{
    // Vertex coordinates
    private static float[] vertices;
    
    // Indices
    private static byte[] indices;
    
    private FloatBuffer vertexBuffer;
    private ByteBuffer indexBuffer;
    
    /**
     * Constructor.
     * Create the buffers from the data.
     */
    public PrimitiveBox(Vector3f min, Vector3f max)
    {
	vertices = new float[]	                    
	                    {
	                	min.x, min.y, min.z,
	                	max.x, min.y, min.z,
	                	max.x, max.y, min.z,
	                	min.x, max.y, min.z,
	                	min.x, min.y, max.z,
	                	max.x, min.y, max.z,
	                	max.x, max.y, max.z,
	                	min.x, max.y, max.z
	                    };	 
	
	indices = new byte[]
	                    {
				0, 4, 5, 0, 5, 1, 1, 5, 6, 1, 6, 2, 2, 6, 7, 2, 7, 3,
				3, 7, 4, 3, 4, 0, 4, 7, 6, 4, 6, 5, 3, 0, 1, 3, 1, 2 		    
	                    };
	
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
