package ca.sandstorm.luminance.gameobject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import android.graphics.Color;

import ca.sandstorm.luminance.math.Ray;
import ca.sandstorm.luminance.math.Sphere;

public class Light extends GameObject implements IGameObject
{
    public static final float LIGHT_INFINITY = 65535.0f;
    
    private float _distance;
    
    private Vector3f _startPoint;
    private Vector3f _endPoint;
    private Vector3f _direction;
    private Vector3f _tmpDistance;
    
    // vertex and index buffers
    private FloatBuffer _vertexBuffer;
    private ShortBuffer _indexBuffer;

    // total indice count for rendering
    private int _totalIndices;   
    
    private Ray _ray;
    
    private int _color;
    
    private IGameObject _startTouchingObject = null;
    private IGameObject _endTouchingObject = null;
    
    
    public Light(float xStart, float yStart, float zStart, float xDir, float yDir, float zDir, float distance, int color)
    {
	_distance = distance;
	_startPoint = new Vector3f(xStart, yStart, zStart);
	_direction = new Vector3f(xDir, yDir, zDir);
	_endPoint = new Vector3f();
	
	// calc ray for collision
	_ray = new Ray(_startPoint.x, _startPoint.y, _startPoint.z,
	               _direction.x, _direction.y, _direction.z);	
	
	// indices will never change, create once
	_totalIndices = 2;
	short[] indices = new short[_totalIndices];
	indices[0] = 0;
	indices[0] = 1;
	
	ByteBuffer byteBuf = ByteBuffer.allocateDirect(indices.length * 2);
	byteBuf.order(ByteOrder.nativeOrder());
	_indexBuffer = byteBuf.asShortBuffer();
	_indexBuffer.put(indices);
	_indexBuffer.position(0);
	
	// init the vertices (dynamic)
	_initVertices();
	
	_color = color;
    }
    
    
    private void _initVertices()
    {
	_endPoint = new Vector3f(_startPoint.x + (_direction.x * _distance), 
	                         _startPoint.y + (_direction.y * _distance), 
	                         _startPoint.z + (_direction.z * _distance));	
	
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
    }
    
    
    public Vector3f getStartPoint()
    {
	return _startPoint;
    }
    
    public void setStartPoint(float x, float y, float z)
    {
	_startPoint.set(x, y, z);
	
	_initVertices();
    }
    
    
    public Vector3f getDirection()
    {
	return _direction;
    }
    
    
    public void setDirection(float x, float y, float z)
    {
	_direction.set(x, y, z);
	
	_initVertices();
    }
      
    
    public float getDistance()
    {
	return _distance;
    }
    
    
    public void setDistance(float d)
    {
	_distance = d;
	
	_initVertices();
    }
    
    
    public int getColor()
    {
	return _color;
    }
    
    
    public IGameObject getStartTouchedObject()
    {
	return _startTouchingObject;
    }
    
    
    public void setStartTouchedObject(IGameObject o)
    {
	_startTouchingObject = o;
    }
    
    
    public IGameObject getEndTouchedObject()
    {
	return _endTouchingObject;
    }
    
    
    public void setEndTouchedObject(IGameObject o)
    {
	_endTouchingObject = o;
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
	return _startPoint;
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
	gl.glScalef(1, 1, 1);
	
	// Set the face rotation
	gl.glFrontFace(GL10.GL_CW);

	// Point to our buffers
	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);

	// Enable the vertex and color state
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

	// Set color
	gl.glColor4f(Color.red(_color) / 255.0f,
	             Color.green(_color) / 255.0f,
	             Color.blue(_color) / 255.0f,
	             1.0f);

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


    @Override
    public void beamInteract(LightBeamCollection beamCollection, int beamIndex, int lightIndex)
    {
	// TODO Auto-generated method stub
	
    }
}
