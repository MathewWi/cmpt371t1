package ca.sandstorm.luminance.gameobject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import ca.sandstorm.luminance.math.Ray;
import ca.sandstorm.luminance.math.Sphere;

public class Light extends GameObject implements IGameObject
{
    private Vector3f _startPoint;
    private Vector3f _endPoint;
    
    // vertex and index buffers
    private FloatBuffer _vertexBuffer;
    private ShortBuffer _indexBuffer;

    // total indice count for rendering
    private int _totalIndices;   
    
    private Ray _ray;
    
    
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
	
	
	// calc ray for collision
	Vector3f dir = new Vector3f(_endPoint.x, _endPoint.y, _endPoint.z);
	dir.sub(_startPoint);
	dir.normalize();
	
	_ray = new Ray(_startPoint.x, _startPoint.y, _startPoint.z,
	               dir.x, dir.y, dir.z);
	
    }
    
    
    public Vector3f getStartPoint()
    {
	return _startPoint;
    }
    
    public void setStartPoint(float x, float y, float z)
    {
	_startPoint.set(x, y, z);	
    }
    
    
    public Vector3f getEndPoint()
    {
	return _endPoint;
    }
    
    public void setEndPoint(float x, float y, float z)
    {
	_endPoint.set(x, y, z);
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
	// Set the face rotation
	gl.glFrontFace(GL10.GL_CW);

	// Point to our buffers
	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);

	// Enable the vertex and color state
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

	// Set color
	gl.glColor4f(1f, 1f, 1f, 1f);

	// Draw the vertices as triangles, based on the Index Buffer information
	gl.glDrawElements(GL10.GL_LINES, _totalIndices, GL10.GL_UNSIGNED_SHORT,
			  _indexBuffer);

	// Disable the client state before leaving
	gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);	
    }
    
    
    public Ray getRay()
    {
	return _ray;
    }


    @Override
    public Sphere getCollisionSphere()
    {
	// TODO Auto-generated method stub
	return null;
    }
}
