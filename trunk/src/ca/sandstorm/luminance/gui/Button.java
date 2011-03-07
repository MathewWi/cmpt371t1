package ca.sandstorm.luminance.gui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import ca.sandstorm.luminance.graphics.PrimitiveBox;
import ca.sandstorm.luminance.resources.TextureResource;

/**
 * Standard Button widget.
 * 
 * @author Kumaran Vijayan
 *
 */
public class Button implements IWidget
{
    private float _x;
    private float _y;
    private float _width;
    private float _height;
    
    // static because all quads only need one of these, just change textures
    private static FloatBuffer _vertexBuffer;
    private static float[] _vertices;
    private static FloatBuffer _texCoordBuffer;
    private static float[] _texCoords;
    
    private String _textureResourceLocation;
    private TextureResource _texture;
    
    private String _title;
    
    private Object _callee;
    private Method _method;

    /**
     * Constructor for creating a Button.
     * @param x X coordinate of the button.
     * @param y Y coordinate of the button.
     * @param width Width of the button.
     * @param height Height of the button.
     * @param title String to be used for the buton title.
     * @precond x >= 0, y >= 0, width >= 0, height >= 0
     * @postcond this.getX() == x, this.getY() == y, this.getWidth() == width,
     * this.getHeight() == height, this.getTitle() == title
     */
    public Button(float x, float y, float width, float height, String title)
    {
	this._x = x;
	this._y = y;
	this._width = width;
	this._height = height;
	_title = title;
	
	// allocate these only once
	if (_vertices == null || _vertexBuffer == null)
	{
	     _vertices = new float[] {
		         // Vertices for the square
		          0, height,  0.0f,  // 0. left-bottom
		          width, height,  0.0f,  // 1. right-bottom
		          0,  0,  0.0f,  // 2. left-top
		          width,  0,  0.0f   // 3. right-top
		};
        	
        	ByteBuffer byteBuf = ByteBuffer.allocateDirect(_vertices.length * 4);
        	byteBuf.order(ByteOrder.nativeOrder());
        	_vertexBuffer = byteBuf.asFloatBuffer();
        	_vertexBuffer.put(_vertices);
        	_vertexBuffer.position(0);
	}
	if(_texCoordBuffer == null || _texCoords == null) {
	    _texCoords = new float[] {
		    0f, 0f,
		    0f, 1f,
		    1f, 0f,
		    1f, 1f
	    };
	    
	    ByteBuffer byteBuf = ByteBuffer.allocateDirect(_texCoords.length * 4);
	    byteBuf.order(ByteOrder.nativeOrder());
	    _texCoordBuffer = byteBuf.asFloatBuffer();
	    _texCoordBuffer.put(_texCoords);
	    _texCoordBuffer.position(0);
	}
    }

    public float getX()
    {
	return _x;
    }


    public void setX(float x)
    {
	_x = x;
    }


    public float getY()
    {
	return _y;
    }


    public void setY(float y)
    {
	_y = y;
    }


    public float getWidth()
    {
	return _width;
    }


    public void setWidth(float width)
    {
	_width = width;
    }


    public float getHeight()
    {
	return _height;
    }


    public void setHeight(float height)
    {
	_height = height;
    }
    
    
    public String getTextureResourceLocation()
    {
	return _textureResourceLocation;
    }
    
    public void setTextureResourceLocation(String textureResourceLocation)
    {
	_textureResourceLocation = textureResourceLocation;
    }
    
    public TextureResource getTexture()
    {
	return _texture;
    }
    
    public void setTexture(TextureResource texture)
    {
	_texture = texture;
    }
    
    /**
     * Get the title for the button.
     * @return the title used for the button
     */
    public String getTitle()
    {
	return _title;
    }
    
    /**
     * Set the title for the button.
     * @param title The title used for the button.
     * @precond n/a
     * @postcond this.getTitle() == title
     */
    public void setTitle(String title)
    {
	_title = title;
    }
    
    /**
     * Get the object that will be called by the method
     * 
     * @return the object that will be called with the method by this.getMethod()
     */
    public Object getCallee()
    {
	return _callee;
    }
    
    /**
     * Get the Method that will be called on the this.getCallee()
     * 
     * @return the Method that will be called on this.getCallee()
     */
    public Method getMethod()
    {
	return _method;
    }
    
    /**
     * Set the callee and the method to be called on it.
     * 
     * @param callee The object to be called with method.
     * @param method The Method representing the method that will be called on
     * callee when the method is tapped.
     * @precond method != null && callee != null
     * @postcond this.getCallee() == callee && this.getMethod() == method
     */
    public void setCalleeAndMethod(Object callee, Method method)
    {
	_callee = callee;
	_method = method;
    }
    
    public void setCalleeAndMethod(Object callee, String method)
    {
	_callee = callee;
	try {
	    _method = callee.getClass().getMethod(method, (Class[])null);
	} catch (SecurityException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (NoSuchMethodException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /**
     * When a button is tapped, this method is called. This method should only
     * be called by GUIManager from inside its touchOccurred(float, float) 
     * method.
     */
    protected void tapped()
    {
	try {
	    _method.invoke(_callee, (Object[])null);
	} catch (IllegalArgumentException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    public void draw(GL10 gl)
    {
	gl.glPushMatrix();
	
	//gl.glScalef(this.width, this.height, 1.0f);
	gl.glTranslatef(this._x, this._y, 0);
	
	gl.glFrontFace(GL10.GL_CCW);
	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
	gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, _texCoordBuffer);
	
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
	gl.glEnable(GL10.GL_TEXTURE_2D);
	gl.glColor4f(1f, 1f, 1f, 1f);
	gl.glBindTexture(GL10.GL_TEXTURE_2D, _texture.getTexture());
	gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, _vertices.length / 3);
	
	gl.glPopMatrix();
    }
}
