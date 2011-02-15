package ca.sandstorm.luminance.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class PrimitiveBox implements IRenderable
{
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
    
    private static byte[] indices =
    {
	0, 4, 5, 0, 5, 1, 1, 5, 6, 1, 6, 2, 2, 6, 7, 2, 7, 3,
	3, 7, 4, 3, 4, 0, 4, 7, 6, 4, 6, 5, 3, 0, 1, 3, 1, 2 
    };
    
    private FloatBuffer vertexBuffer;
    private ByteBuffer indexBuffer;
    
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
    public FloatBuffer getVertexBuffer()
    {
	return vertexBuffer;
    }

    @Override
    public ByteBuffer getIndexBuffer()
    {
	return indexBuffer;
    }
    
    @Override
    public FloatBuffer getNormalBuffer()
    {
        return null;
    }
}
