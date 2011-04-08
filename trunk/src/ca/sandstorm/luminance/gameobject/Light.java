package ca.sandstorm.luminance.gameobject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.level.Color;
import ca.sandstorm.luminance.math.Colliders;
import ca.sandstorm.luminance.math.Ray;
import ca.sandstorm.luminance.math.Sphere;

public class Light extends GameObject implements IGameObject
{
    public static final float LIGHT_INFINITY = 127.5f;
    
    // particle quads info, reused for all lights 
    private int _texture;
    
    private float _distance;
    
    private Vector3f _startPoint;
    private Vector3f _endPoint;
    private Vector3f _direction;
    
    // total indice count for rendering
    private int _totalIndices;   
    
    private Ray _ray;
    
    private int _color;   
    
    private IGameObject _startTouchingObject = null;
    private IGameObject _endTouchingObject = null;
    
    private static float _pulse = 1.0f;
        
    // Indices
    private short[] _indices = {
		0,1,3, 0,3,2,
    		4,5,7, 4,7,6,
    		/*8,9,11, 8,11,10,
    		12,13,15, 12,15,14, 	
    		16,17,19, 16,19,18, 	
    		20,21,23, 20,23,22 */	

    };
    
    // Texture coordinates    
    private FloatBuffer _vertexBuffer;
    private ShortBuffer _indexBuffer;
    private FloatBuffer _textureBuffer;    
    
    
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
	_totalIndices = _indices.length;
	
	ByteBuffer byteBuf = ByteBuffer.allocateDirect(_indices.length * 2);
	byteBuf.order(ByteOrder.nativeOrder());
	_indexBuffer = byteBuf.asShortBuffer();
	_indexBuffer.put(_indices);
	_indexBuffer.position(0);	
	
	// init the vertices (dynamic)
	_initVertices();
	
	_color = color;	
	
	_texture = Engine.getInstance().getResourceManager().getOpenGLTexture("textures/beam.png");
    }
    
    
    private void _initVertices()
    {
	_endPoint = new Vector3f(_startPoint.x + (_direction.x * _distance), 
	                         _startPoint.y + (_direction.y * _distance), 
	                         _startPoint.z + (_direction.z * _distance));	
	
	Vector3f scaleDir = new Vector3f(Colliders.crossProduct(_direction, Colliders.UP));
	scaleDir.normalize();
	scaleDir.scale(0.15f);
	Vector3f boxStart = new Vector3f(_startPoint);
	boxStart.add(scaleDir);
		
	scaleDir = new Vector3f(Colliders.crossProduct(_direction, Colliders.DOWN));
	scaleDir.normalize();
	scaleDir.scale(0.15f);
	Vector3f boxEnd = new Vector3f(_endPoint);
	boxEnd.add(scaleDir);
	
	
	float[] _vertices = {
		/*boxStart.x, boxStart.y, boxEnd.z,
    		boxEnd.x, boxStart.y, boxEnd.z,
    		boxStart.x, boxEnd.y, boxEnd.z,
    		boxEnd.x, boxEnd.y, boxEnd.z,
    		
    		boxEnd.x, boxStart.y, boxEnd.z,
    		boxEnd.x, boxStart.y, boxStart.z,    		
    		boxEnd.x, boxEnd.y, boxEnd.z,
    		boxEnd.x, boxEnd.y, boxStart.z,
    		
    		boxEnd.x, boxStart.y, boxStart.z,
    		boxStart.x, boxStart.y, boxStart.z,    		
    		boxEnd.x, boxEnd.y, boxStart.z,
    		boxStart.x, boxEnd.y, boxStart.z,
    		
    		boxStart.x, boxStart.y, boxStart.z,
    		boxStart.x, boxStart.y, boxEnd.z,    		
    		boxStart.x, boxEnd.y, boxStart.z,
    		boxStart.x, boxEnd.y, boxEnd.z,*/
    		
    		boxStart.x, boxStart.y, boxStart.z,
    		boxEnd.x, boxStart.y, boxStart.z,    		
    		boxStart.x, boxStart.y, boxEnd.z,
    		boxEnd.x, boxStart.y, boxEnd.z,
    		
    		boxStart.x, boxEnd.y, boxEnd.z,
    		boxEnd.x, boxEnd.y, boxEnd.z,    		
    		boxStart.x, boxEnd.y, boxStart.z,
    		boxEnd.x, boxEnd.y, boxStart.z,

	};
	
	//float[] vertices = new float[2 * 3];
	
	/*vertices[0] = _startPoint.x;
	vertices[1] = _startPoint.y;
	vertices[2] = _startPoint.z;
	
	vertices[3] = _endPoint.x;
	vertices[4] = _endPoint.y;
	vertices[5] = _endPoint.z;*/
	
	ByteBuffer byteBuf = ByteBuffer.allocateDirect(_vertices.length * 4);
	byteBuf.order(ByteOrder.nativeOrder());
	_vertexBuffer = byteBuf.asFloatBuffer();
	_vertexBuffer.put(_vertices);
	_vertexBuffer.position(0);	
	
	
	float textureScale = _distance / 1.0f;
	Vector3f scale = new Vector3f(_direction);
	scale.scale(textureScale);
	scale.x = Math.max(1.0f, Math.abs(scale.x));
	scale.z = Math.max(1.0f, Math.abs(scale.z));
	float[] _textureCoords = {
		0.0f, 0.0f,
    		0.0f,  scale.x,
    		scale.z, 0.0f,
    		scale.z,  scale.x,
    		
    		0.0f, 0.0f,
    		scale.x, 0,
    		0, scale.z,
    		scale.x, scale.z,
    		
    		/*0.0f, 0.0f,
    		0.0f, scale.x,
    		scale.z, 0.0f,
    		scale.z, scale.x,
    		
    		0.0f, 0.0f,
    		0.0f,  scale.x,
    		scale.z, 0.0f,
    		scale.z, scale.x,
    		
    		0.0f, 0.0f,
    		0.0f,  scale.x,
    		scale.z, 0.0f,
    		scale.z,  scale.x,
    		
    		0.0f, 0.0f,
    		0.0f, scale.x,
    		scale.z, 0.0f,
    		scale.z, scale.x*/

    };	
	
	ByteBuffer byteTexBuf = ByteBuffer.allocateDirect(_textureCoords.length * 4);
	byteTexBuf.order(ByteOrder.nativeOrder());
	_textureBuffer = byteTexBuf.asFloatBuffer();
	_textureBuffer.put(_textureCoords);
	_textureBuffer.position(0);	
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
    
    public static void incrementLightScroll()
    {
	_pulse += 0.10f;
    }
    
    public static void resetLightScroll()
    {
	_pulse = 0;
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
	gl.glMatrixMode(GL10.GL_TEXTURE);
	gl.glLoadIdentity();
	gl.glTranslatef(-1.0f * _pulse, 0, 0);
	gl.glMatrixMode(GL10.GL_MODELVIEW);
	
	// don't cull face here
	//gl.glDisable(GL10.GL_CULL_FACE);

	// Point to our buffers
	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);

	// Enable the vertex and color state
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	
	gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, _textureBuffer);
	
	//gl.glDisable(GL10.GL_DEPTH_TEST);
	gl.glEnable(GL10.GL_TEXTURE_2D);
	gl.glEnable(GL10.GL_BLEND);
	gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
	//gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);	
	
	gl.glBindTexture(GL10.GL_TEXTURE_2D, _texture);
	gl.glTexParameterf( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT );
	gl.glTexParameterf( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT );	
		
	// Set color
	gl.glColor4f(Color.red(_color) / 255.0f,
	             Color.green(_color) / 255.0f,
	             Color.blue(_color) / 255.0f,
	             0.85f);
	
	// Draw the vertices as triangles, based on the Index Buffer information
	gl.glDrawElements(GL10.GL_TRIANGLES, _totalIndices, GL10.GL_UNSIGNED_SHORT,
			  _indexBuffer);

	// Disable the client state before leaving
	gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	gl.glDisable(GL10.GL_TEXTURE_2D);
	gl.glDisable(GL10.GL_BLEND);
	gl.glEnable(GL10.GL_DEPTH_TEST);
	//gl.glEnable(GL10.GL_CULL_FACE);
	
	gl.glMatrixMode(GL10.GL_TEXTURE);
	gl.glLoadIdentity();
	gl.glMatrixMode(GL10.GL_MODELVIEW);	
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


    @Override
    public float getNextYRotation()
    {
	// TODO Auto-generated method stub
	return 0;
    }


    @Override
    public float getPrevYRotation()
    {
	// TODO Auto-generated method stub
	return 0;
    }


    @Override
    public float getCurrentYRotation()
    {
	// TODO Auto-generated method stub
	return 0;
    }


    @Override
    public int getYRotationCount()
    {
	// TODO Auto-generated method stub
	return 0;
    }
}
