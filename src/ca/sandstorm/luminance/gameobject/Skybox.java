package ca.sandstorm.luminance.gameobject;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.resources.TextureResource;


public class Skybox implements IGameObject
{
    private FloatBuffer _vertexBuffer;
    private FloatBuffer _texCoordBuffer;
    private ShortBuffer _indexBuffer;
    
    private TextureResource[] _textures;


    public Skybox()
    {
	// define the squares vertices
	// each line is one side of the cube
	float squareVertices[] =
	{
		-1, -1, 1,		1, -1, 1,		1, 1, 1,		-1, 1, 1,
		-1, 1, -1,		1, 1, -1,		1, -1, -1,		-1, -1, -1,
		1, -1, -1,		1, 1, -1,		1, 1, 1,		1, -1, 1,
		-1, -1, 1,		-1, 1, 1,		-1, 1, -1,		-1, -1, -1,
		-1, 1, -1,		1, 1, -1,		1, 1, 1,		-1, 1, 1,
		-1, -1, 1,		1, -1, 1,		1, -1, -1,		-1, -1, -1,
	};

	// define the texture coordinates
	// each line is each side of the cube
	float texCoords[] = {
		1, 1,		0, 1,		0, 0,		1, 0,
		0, 0,		1, 0,		1, 1,		0, 1,
		0, 1,		0, 0,		1, 0,		1, 1,
		0, 1,		0, 0,		1, 0,		1, 1,
		0, 1,		1, 1,		1, 0,		0, 0,
		0, 1,		1, 1,		1, 0,		0, 0,
	};
	
	// define the indices which make up the quads
	// for now each tuple is a triangle
	short indices[] =
	{
		0,	2,	1,	0,	3,	2,
		4,	6,	5,	4,	7,	6,
		8,	10,	9,	8,	11,	10,
		12,	14,	13,	12,	15,	14,
		16,	17,	18,	16,	18,	19,
		20,	21,	22,	20,	22,	23,		
	};
	
	// init the buffers
	ByteBuffer byteBuf = ByteBuffer.allocateDirect(squareVertices.length * 4);
	byteBuf.order(ByteOrder.nativeOrder());
	_vertexBuffer = byteBuf.asFloatBuffer();
	_vertexBuffer.put(squareVertices);
	_vertexBuffer.position(0);
	
	byteBuf = ByteBuffer.allocateDirect(texCoords.length * 4);
	byteBuf.order(ByteOrder.nativeOrder());
	_texCoordBuffer = byteBuf.asFloatBuffer();
	_texCoordBuffer.put(texCoords);
	_texCoordBuffer.position(0);
	
	byteBuf = ByteBuffer.allocateDirect(indices.length * 2);
	byteBuf.order(ByteOrder.nativeOrder());
	_indexBuffer = byteBuf.asShortBuffer();
	_indexBuffer.put(indices);
	_indexBuffer.position(0);
    }
    
    
    public void init(GL10 gl) throws IOException
    {
	_textures = new TextureResource[6];
	
	// load the assets for this object
	_textures[0] = Engine.getInstance().getResourceManager().loadTexture(gl, "textures/skyFront.jpg");
	_textures[1] = Engine.getInstance().getResourceManager().loadTexture(gl, "textures/skyBack.jpg");
	_textures[2] = Engine.getInstance().getResourceManager().loadTexture(gl, "textures/skyLeft.jpg");
	_textures[3] = Engine.getInstance().getResourceManager().loadTexture(gl, "textures/skyRight.jpg");
	_textures[4] = Engine.getInstance().getResourceManager().loadTexture(gl, "textures/skyTop.jpg");
	_textures[5] = Engine.getInstance().getResourceManager().loadTexture(gl, "textures/skyBottom.jpg");
    }


    public void update(GL10 gl)
    {

    }


    public void draw(GL10 gl)
    {
	int index = 0;
	
	gl.glFrontFace(GL10.GL_CW);
	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
	gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, _texCoordBuffer);
	
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
	gl.glEnable(GL10.GL_TEXTURE_2D);
	
	for(int side = 0; side < 6; side++)
	{
	    gl.glBindTexture(GL10.GL_TEXTURE0, _textures[side].getTexture());
	    _indexBuffer.position(index);
	    gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, _indexBuffer);

	    index += 6;
	}	
	
	// Disable the client state before leaving
	gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	
	gl.glDisable(GL10.GL_TEXTURE_2D);
    }


    @Override
    public Vector3f getPosition()
    {
	return null;
    }

    @Override
    public Vector4f getRotation()
    {
        return null;
    }
	
	@Override
	public Vector3f getScale()
	{
		return null;
	}

    @Override
    public void initialize()
    {
	// TODO Auto-generated method stub
	
    }


    @Override
    public void update()
    {
	// TODO Auto-generated method stub
	
    }


    @Override
    public void destroy()
    {
	// TODO Auto-generated method stub
	
    }
}
