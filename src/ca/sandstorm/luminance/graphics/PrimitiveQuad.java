package ca.sandstorm.luminance.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

public class PrimitiveQuad implements IRenderable
{
    private static float[] _vertices;
    private static float[] _texCoords;
    private FloatBuffer _vertexBuffer;
    private FloatBuffer _texCoordBuffer;

    public PrimitiveQuad(Vector3f min, Vector3f max)
    {	
	_vertices = new float[] {		
		min.x, max.y, 0f,
		max.x, max.y, 0f,
		min.x, min.y, 0f,
		max.x, min.y, 0f
	};
	ByteBuffer byteBuf = ByteBuffer.allocateDirect(_vertices.length * 4);
	byteBuf.order(ByteOrder.nativeOrder());
	_vertexBuffer = byteBuf.asFloatBuffer();
	_vertexBuffer.put(_vertices);
	_vertexBuffer.position(0);

	_texCoords = new float[] {
		0f, 0f,
		0f, 1f,
		1f, 0f,
		1f, 1f
	};
	ByteBuffer byteBuf2 = ByteBuffer.allocateDirect(_texCoords.length * 4);
	byteBuf2.order(ByteOrder.nativeOrder());
	_texCoordBuffer = byteBuf2.asFloatBuffer();
	_texCoordBuffer.put(_texCoords);
	_texCoordBuffer.position(0);
    }

    @Override
    public void draw(GL10 gl)
    {
	gl.glPushMatrix();
		
	gl.glFrontFace(GL10.GL_CCW);
	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
	gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, _texCoordBuffer);
	
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
	//gl.glEnable(GL10.GL_TEXTURE_2D);
	//gl.glColor4f(1f, 1f, 1f, 1f);
	//gl.glBindTexture(GL10.GL_TEXTURE_2D, _texture.getTexture());
	gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, _vertices.length / 3);
	
	gl.glPopMatrix();
    }
}
