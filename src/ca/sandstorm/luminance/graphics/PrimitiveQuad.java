package ca.sandstorm.luminance.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

/**
 * Primitive quad renderable
 * @author zenja
 */
public class PrimitiveQuad implements IRenderable
{
    // Vertex and texture coordinate buffers
    private float[] _vertices;
    private float[] _texCoords;
    private FloatBuffer _vertexBuffer;
    private FloatBuffer _texCoordBuffer;

    /**
     * Create the quad based on the minimum and maximum point.
     * @param min Minimum corner of the quad
     * @param max Maximum corner of the quad
     */
    public PrimitiveQuad(Vector3f min, Vector3f max)
    {	
	// Set up and create vertex buffer
	_vertices = new float[] {				
		min.x, min.y, 0f,
		max.x, min.y, 0f,
		min.x, max.y, 0f,
		max.x, max.y, 0f
	};
	ByteBuffer byteBuf = ByteBuffer.allocateDirect(_vertices.length * 4);
	byteBuf.order(ByteOrder.nativeOrder());
	_vertexBuffer = byteBuf.asFloatBuffer();
	_vertexBuffer.put(_vertices);
	_vertexBuffer.position(0);

	// Set up and create texture coordinate buffer
	_texCoords = new float[] {
		0f, 0f,
		1f, 0f,
		0f, 1f,
		1f, 1f
	};
	ByteBuffer byteBuf2 = ByteBuffer.allocateDirect(_texCoords.length * 4);
	byteBuf2.order(ByteOrder.nativeOrder());
	_texCoordBuffer = byteBuf2.asFloatBuffer();
	_texCoordBuffer.put(_texCoords);
	_texCoordBuffer.position(0);
    }
    
    /**
     * Adjust texture coordinate offset to allow for spritemap functionality.
     * @param offset Horizontal offset, from 0-1
     * @param width Width as proportion of total width, from 0-1
     */
    public void setWidthOffset(float offset, float width)
    {
	_texCoords[0] = 1 - offset;
	_texCoords[2] = 1 - offset + width;
	_texCoords[4] = 1 - offset;
	_texCoords[6] = 1 - offset + width;
	
	_texCoordBuffer.clear();
	_texCoordBuffer.put(_texCoords);
	_texCoordBuffer.position(0);
    }

    /**
     * Draw the quad.
     * @param gl OpenGL instance to draw with
     */
    @Override
    public void draw(GL10 gl)
    {
	gl.glPushMatrix();
		
	gl.glFrontFace(GL10.GL_CW);
	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
	gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, _texCoordBuffer);
	
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
	gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, _vertices.length / 3);
	
	gl.glPopMatrix();
    }
}
