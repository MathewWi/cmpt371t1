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
    private float[] vertices = {
//        -0.5f, -0.5f, -0.5f,
//        0.5f, -0.5f, -0.5f,
//        0.5f, 0.5f, -0.5f,
//        -0.5f, 0.5f, -0.5f,
//        -0.5f, -0.5f, 0.5f,
//        0.5f, -0.5f, 0.5f,
//        0.5f, 0.5f, 0.5f,
//        -0.5f, 0.5f, 0.5f
		-1.0f, -1.0f, 1.0f, //Vertex 0
    		1.0f, -1.0f, 1.0f,  //v1
    		-1.0f, 1.0f, 1.0f,  //v2
    		1.0f, 1.0f, 1.0f,   //v3
    		
    		1.0f, -1.0f, 1.0f,	//...
    		1.0f, -1.0f, -1.0f,    		
    		1.0f, 1.0f, 1.0f,
    		1.0f, 1.0f, -1.0f,
    		
    		1.0f, -1.0f, -1.0f,
    		-1.0f, -1.0f, -1.0f,    		
    		1.0f, 1.0f, -1.0f,
    		-1.0f, 1.0f, -1.0f,
    		
    		-1.0f, -1.0f, -1.0f,
    		-1.0f, -1.0f, 1.0f,    		
    		-1.0f, 1.0f, -1.0f,
    		-1.0f, 1.0f, 1.0f,
    		
    		-1.0f, -1.0f, -1.0f,
    		1.0f, -1.0f, -1.0f,    		
    		-1.0f, -1.0f, 1.0f,
    		1.0f, -1.0f, 1.0f,
    		
    		-1.0f, 1.0f, 1.0f,
    		1.0f, 1.0f, 1.0f,    		
    		-1.0f, 1.0f, -1.0f,
    		1.0f, 1.0f, -1.0f,

    };
    
    // Indices
    private byte[] indices = {
//        0, 4, 5, 0, 5, 1, 1, 5, 6, 1, 6, 2, 2, 6, 7, 2, 7, 3,
//        3, 7, 4, 3, 4, 0, 4, 7, 6, 4, 6, 5, 3, 0, 1, 3, 1, 2 
		0,1,3, 0,3,2, 			//Face front
    		4,5,7, 4,7,6, 			//Face right
    		8,9,11, 8,11,10, 		//... 
    		12,13,15, 12,15,14, 	
    		16,17,19, 16,19,18, 	
    		20,21,23, 20,23,22 	

    };
    
    // Texture coordinates
    //TODO: There's a 90% chance these are wrong
    private float[] textureCoords = {
//		0.0f, 0.0f,
//		0.0f, 1.0f,
//		1.0f, 0.0f,
//		1.0f, 1.0f, 
//		
//		0.0f, 0.0f,
//		0.0f, 1.0f,
//		1.0f, 0.0f,
//		1.0f, 1.0f
		0.0f, 0.0f,
    		0.0f, 1.0f,
    		1.0f, 0.0f,
    		1.0f, 1.0f, 
    		
    		0.0f, 0.0f,
    		0.0f, 1.0f,
    		1.0f, 0.0f,
    		1.0f, 1.0f,
    		
    		0.0f, 0.0f,
    		0.0f, 1.0f,
    		1.0f, 0.0f,
    		1.0f, 1.0f,
    		
    		0.0f, 0.0f,
    		0.0f, 1.0f,
    		1.0f, 0.0f,
    		1.0f, 1.0f,
    		
    		0.0f, 0.0f,
    		0.0f, 1.0f,
    		1.0f, 0.0f,
    		1.0f, 1.0f,
    		
    		0.0f, 0.0f,
    		0.0f, 1.0f,
    		1.0f, 0.0f,
    		1.0f, 1.0f

    };
    
    private FloatBuffer vertexBuffer;
    private ByteBuffer indexBuffer;
    private FloatBuffer textureBuffer;
    
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
	
	// Create texture buffer
	ByteBuffer byteTexBuf = ByteBuffer.allocateDirect(textureCoords.length * 4);
	byteTexBuf.order(ByteOrder.nativeOrder());
	textureBuffer = byteTexBuf.asFloatBuffer();
	textureBuffer.put(textureCoords);
	textureBuffer.position(0);

	// Create index buffer
	indexBuffer = ByteBuffer.allocateDirect(indices.length);
	indexBuffer.put(indices);
	indexBuffer.position(0);
    }
    
    @Override
    public void draw(GL10 gl)
    {
	//gl.glBindTexture(GL10.GL_TEXTURE_2D, 1);

	// Set the face rotation
	gl.glFrontFace(GL10.GL_CCW);
	
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	
	// Point to the vertex buffer
	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
	gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);

	// Use index buffer if available, or just draw the vertices if not
	gl.glDrawElements(GL10.GL_TRIANGLES, indexBuffer.limit(), GL10.GL_UNSIGNED_BYTE, indexBuffer);

	// Restore state
	gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
