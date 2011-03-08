package ca.sandstorm.luminance.gameobject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class Light extends GameObject implements IGameObject
{
    private Vector3f _startPoint;
    private Vector3f _endPoint;
    
    // vertex and index buffers
    private FloatBuffer _vertexBuffer;
    private ShortBuffer _indexBuffer;

    // total indice count for rendering
    private int _totalIndices;    
    
    
    public Light(float xStart, float yStart, float zStart, float xEnd, float yEnd, float zEnd)
    {
	_startPoint = new Vector3f(xStart, yStart, zStart);
	_endPoint = new Vector3f(xEnd, yEnd, zEnd);
	
	float[] vertices = new float[2 * 3];
	
	vertices[0] = _startPoint.x;
	vertices[1] = _startPoint.y;
	vertices[2] = _startPoint.z;
	
	vertices[3] = _endPoint.x;
	vertices[4] = _endPoint.y;
	vertices[5] = _endPoint.z;
	
	ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
	byteBuf.order(ByteOrder.nativeOrder());
	_vertexBuffer = byteBuf.asFloatBuffer();
	_vertexBuffer.put(vertices);
	_vertexBuffer.position(0);
	
	_totalIndices = 2;
	short[] indices = new short[_totalIndices];
	indices[0] = 0;
	indices[0] = 1;
	
	byteBuf = ByteBuffer.allocateDirect(indices.length * 2);
	byteBuf.order(ByteOrder.nativeOrder());
	_indexBuffer = byteBuf.asShortBuffer();
	_indexBuffer.put(indices);
	_indexBuffer.position(0);	
    }
    
    
    public Vector3f getStartPoint()
    {
	return _startPoint;
    }
    
    
    public Vector3f getEndPoint()
    {
	return _endPoint;
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


    @Override
    public Vector3f getPosition()
    {
	// TODO Auto-generated method stub
	return null;
    }


    @Override
    public Vector4f getRotation()
    {
	// TODO Auto-generated method stub
	return null;
    }


    @Override
    public Vector3f getScale()
    {
	// TODO Auto-generated method stub
	return null;
    }

    
    // @HACK - not consistent at all
    public void draw(GL10 gl)
    {
	
    }
}
