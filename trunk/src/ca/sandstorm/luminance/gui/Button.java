package ca.sandstorm.luminance.gui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

import ca.sandstorm.luminance.graphics.PrimitiveQuad;
import ca.sandstorm.luminance.resources.TextureResource;

/**
 * Standard Button widget.
 * 
 * @author Kumaran Vijayan.
 * Modified by Zenja to use a PrimitiveQuad for rendering.
 */
public class Button implements IWidget
{
    private float _x;
    private float _y;
    private float _width;
    private float _height;
        
    private PrimitiveQuad _quad;
    private String _textureLocation;
    private String _tappedTextureLocation;
    private TextureResource _texture;
    private TextureResource _tappedTexture;
    
    // This texture's value is swapped between _texture and _tappedTexture.
    // Used for drawing in the draw(GL10) method.
    private TextureResource _drawTexture;
    
    private String _title;
    
    private Object _callee;
    private Method _method;
    private boolean _isTapped;
    private boolean _isSelected;

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
	_isTapped = false;
	
	_quad = new PrimitiveQuad(
	    new Vector3f(0, 0, 0),
	    new Vector3f(width, height, 0)
	);
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
	return _textureLocation;
    }
    
    public void setTextureResourceLocation(String textureResourceLocation)
    {
	_textureLocation = textureResourceLocation;
    }
    
    /**
     * Get the location for the tapped texture.
     * 
     * @return the location for the tapped texture.
     */
    public String getTappedTextureLocation()
    {
	return _tappedTextureLocation;
    }
    
    /**
     * Set the location for the tapped texture.
     * 
     * @param tappedTextureLocation The location for the tapped texture.
     * @precond n/a
     * @postcond this.getTappedTextureLocation().equals(tappedTextureLocation) == true
     */
    public void setTappedTextureLocation(String tappedTextureLocation)
    {
	_tappedTextureLocation = tappedTextureLocation;
    }
    
    public TextureResource getTexture()
    {
	return _texture;
    }
    
    public void setTexture(TextureResource texture)
    {
	// Immediately set both _texture and _drawTexture 
	_texture = texture;
	_drawTexture = texture;
    }
    
    /**
     * Get the texture the button switches to when tapped.
     * 
     * @return texture the button switches to when tapped.
     */
    public TextureResource getTappedTexture()
    {
	return _tappedTexture;
    }
    
    /**
     * Set the texture the button switches to when tapped.
     * 
     * @param tappedTexture The texture the button switches to when tapped.
     * @precond n/a
     * @postcond this.getTappedTexture() == tappedTexture
     */
    public void setTappedTexture(TextureResource tappedTexture)
    {
	_tappedTexture = tappedTexture;
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
     * Tells you whether this Button is currently being tapped, indicating
     * which texture is being used to draw itself: the tapped texture
     * (returns true) or the standard texture (returns false).
     * 
     * @return the boolean indicating whether this button is currently being
     * tapped. If true is returned, then the tapped texture is being drawn. If
     * false is returned, then the standard texture is being drawn.
     */
    public boolean getIsTapped()
    {
	return _isTapped;
    }
    
    /**
     * Lets you set this Button as being tapped. This boolean will cause the
     * Button to switch between drawing the tapped texture and the standard
     * texture.
     *  
     * @param isTapped A boolean indicating whether the Button should draw
     * itself using the tapped texture (isTapped == true) or the standard
     * texture (isTapped == false).
     * @precond n/a
     * @postcond this.getIsTapped() == isTapped
     */
    public void setIsTapped(boolean isTapped)
    {
	_isTapped = isTapped;
	if (_isTapped && _tappedTexture != null) {
	    _drawTexture = _tappedTexture;
	} else if (!_isSelected){
	    _drawTexture = _texture;
	}
    }
    
    public void setIsSelected(boolean isSelected)
    {
	_isSelected = isSelected;
	if(_isSelected && _tappedTexture != null){
	    _drawTexture = _tappedTexture;
	} else {
	    _drawTexture = _texture;
	}
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
	
	gl.glTranslatef(_x, _y, 0);		
	gl.glEnable(GL10.GL_TEXTURE_2D);
	gl.glColor4f(1f, 1f, 1f, 1f);
	gl.glBindTexture(GL10.GL_TEXTURE_2D, _drawTexture.getTexture());
	_quad.draw(gl);
	
	gl.glPopMatrix();
    }
}
