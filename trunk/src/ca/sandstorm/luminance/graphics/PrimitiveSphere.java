package ca.sandstorm.luminance.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import ca.sandstorm.luminance.Engine;

import android.opengl.GLU;
import android.opengl.GLUtils;

/**
 * A primitive box using an index buffer.
 * @author zenja
 */
public class PrimitiveSphere implements IRenderable
{          
    private FloatBuffer _verticesBuffer;
    private FloatBuffer _textureBuffer; 
    
    /**
     * Constructor.
     * Create the buffers from the data.
     */
    public PrimitiveSphere()
    {
	// Create vertices buffer
	/*ByteBuffer byteBufVert = ByteBuffer.allocateDirect(_vertices.length * 4);
	byteBufVert.order(ByteOrder.nativeOrder());
	_verticesBuffer = byteBufVert.asFloatBuffer();
	_verticesBuffer.put(_vertices);
	_verticesBuffer.position(0);*/
	
	// Create normal buffer
	/*ByteBuffer byteBufNorm = ByteBuffer.allocateDirect(_normals.length * 4);
	byteBufNorm.order(ByteOrder.nativeOrder());
	_normalBuffer = byteBufNorm.asFloatBuffer();
	_normalBuffer.put(_normals);
	_normalBuffer.position(0);*/
	
	CreateSphere(0.8f, 8, 8);
    }
    
    /**
     * Draw the sphere.
     * Texture or color should be set before this call.
     * @param gl The OpenGL context to use for drawing.
     */
    @Override
    public void draw(GL10 gl)
    {
	// Set the face rotation
	gl.glFrontFace(GL10.GL_CW);
	
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

	// Point to the vertices and normal buffers
	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _verticesBuffer);
	gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, _textureBuffer);
	
	gl.glEnable(GL10.GL_TEXTURE_2D);
	gl.glEnable(GL10.GL_BLEND);
	gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);			
	
	gl.glBindTexture(GL10.GL_TEXTURE_2D, Engine.getInstance().getResourceManager().getOpenGLTexture("textures/inGameMirror.png"));	
	
	// Use vertices buffer to draw
	gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, _verticesBuffer.limit() / 3);

	// Restore state
	gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);  
	
	gl.glDisable(GL10.GL_BLEND);	
    }
    
    
    
    
    
    
    void CreateSphere(double r, int lats, int longs)
    {
	int i, j;
	float vertices[] = new float[(lats+1) * (longs + 1) * 2 * 3];
	float textureCoords[] = new float[(lats+1) * (longs + 1) * 2 * 2];
	
	int curVert = 0;
	int curTex = 0;
	
	for(i = 0; i <= lats; i++)
	{
		double lat0 = Math.PI * (-0.5 + (double) (i - 1) / lats);
		double z0  = Math.sin(lat0);
		double zr0 =  Math.cos(lat0);

		double lat1 = Math.PI * (-0.5 + (double) i / lats);
		double z1 = Math.sin(lat1);
		double zr1 = Math.cos(lat1);

		double tx1 = 0;
		double ty1 = 0;
		double tx2 = 0;
		double ty2 = 0;

		for(j = 0; j <= longs; j++)
		{
			double lng = 2 * Math.PI * (double) (j - 1) / longs;
			double x = Math.cos(lng);
			double y = Math.sin(lng);

			tx1 = Math.atan2(x * zr0, z0) / (2. * Math.PI) + 0.5;
			ty1 = Math.asin(y * zr0) / Math.PI + .5;
			if(tx1 < 0.75 && tx2 > 0.75)
			{
				tx1 += 1.0;
			}
			else if(tx1 > 0.75 && tx2 < 0.75)
			{
				tx1 -= 1.0;
			}

			tx2 = Math.atan2(x * zr1, z1) / (2. * Math.PI) + 0.5;
			ty2 = Math.asin(y * zr1) / Math.PI + .5;
			if(tx2 < 0.75 && tx1 > 0.75)
			{
				tx2 += 1.0;
			}
			else if(tx2 > 0.75 && tx1 < 0.75)
			{
				tx2 -= 1.0;
			}

			vertices[curVert++] = (float)(x * zr0 * r);
			vertices[curVert++] = (float)(y * zr0 * r);
			vertices[curVert++] = (float)(z0 * r);
			
			textureCoords[curTex++] = (float)(tx1);
			textureCoords[curTex++] = (float)(ty1);
			 
			//glNormal3f(x * zr0, y * zr0, z0);

			vertices[curVert++] = (float)(x * zr1 * r);
			vertices[curVert++] = (float)(y * zr1 * r);
			vertices[curVert++] = (float)(z1 * r);
			
			textureCoords[curTex++] = (float)(tx2);
			textureCoords[curTex++] = (float)(ty2);			
						
			//glNormal3f(x * zr1, y * zr1, z1);			
		}
	}
	
	// Create vertices buffer
	ByteBuffer byteBufVert = ByteBuffer.allocateDirect(vertices.length * 4);
	byteBufVert.order(ByteOrder.nativeOrder());
	_verticesBuffer = byteBufVert.asFloatBuffer();
	_verticesBuffer.put(vertices);
	_verticesBuffer.position(0);	
	
	ByteBuffer byteTexBuf = ByteBuffer.allocateDirect(textureCoords.length * 4);
	byteTexBuf.order(ByteOrder.nativeOrder());
	_textureBuffer = byteTexBuf.asFloatBuffer();
	_textureBuffer.put(textureCoords);
	_textureBuffer.position(0);	
    }   
}
