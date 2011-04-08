package ca.sandstorm.luminance.gui;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.microedition.khronos.opengles.GL10;
import ca.sandstorm.luminance.resources.TextureResource;

/**
 * Standard Button widget.
 * 
 * @author Kumaran Vijayan.
 * Modified by Zenja to use a PrimitiveQuad for rendering.
 */
public class Button extends Label
{
    private String _tappedTextureLocation;
    private TextureResource _tappedTexture;
    private boolean _isToggle = false;
    
    // This texture's value is swapped between _texture and _tappedTexture.
    // Used for drawing in the draw(GL10) method.
    private TextureResource _drawTexture;
    
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
	super(x, y, width, height, title);
	
	_isTapped = false;
	_isSelected = false;
    }
    
    /**
     * Constructor for creating a Button with a standard texture, tapped texture, callee and method.
     * 
     * @param x X coordinate of the button.
     * @param y Y coordinate of the button.
     * @param width Width of the button.
     * @param height Height of the button.
     * @param title String to be used for the buton title.
     * @param textureLocation The location of the texture to be used for the
     * Button.
     * @param tappedTextureLocation The location of the texture to be used for
     * the Button when it is tapped.
     * @param callee The object to be called with method when the Button is
     * tapped.
     * @param method The string representing the method that will be called on
     * callee when the Button is tapped.
     * @precond x >= 0 && y >= 0 && width >= 0 && height >= 0 &&
     * textureLocation != null && callee != null && method != null
     * @postcond this.getX() == x, this.getY() == y, this.getWidth() == width,
     * this.getHeight() == height, this.getTitle() == title &&
     * this.getTextureResourceLocation() == textureLocation &&
     * this.getCallee() == callee && this.getMethod() == method
     */
    public Button(float x, float y, float width, float height, String title, String textureLocation, String tappedTextureLocation, Object callee, String method)
    {
	super(x, y, width, height, title, textureLocation);
	
	_isTapped = false;
	_isSelected = false;
	
	this.setTextureResourceLocation(textureLocation);
	_callee = callee;
	
	try {
	    _method = _callee.getClass().getMethod(method, ((Class[])null));
	} catch (SecurityException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (NoSuchMethodException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
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
     * Set whether a button is a toggle-button or not. If it is, it won't light up on touch-down.
     */
    public void setIsToggle(boolean isToggle)
    {
	_isToggle = isToggle;
    }
    
    /**
     * Get whether a button is a toggle-button or not. If it is, it won't light up on touch-down.
     */
    public boolean isToggle()
    {
	return _isToggle;
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
     * Find out whether the button is currently in a selected state.
     * 
     * @return the boolean indicating whether this button is in a selected
     * state. If true, the button is selected. If false, the button is not
     * selected.
     */
    public boolean getIsSelected()
    {
	return _isSelected;
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
    
    /**
     * Set the callee and method to call on the callee.
     * 
     * @param callee The object which we call the method represented by method.
     * @param method The string representation of the method to call on callee.
     * @precond callee != null && method != null && method.isEmpty() == false
     * @postcond this.getCallee() == callee && this.getMethod() != null
     */
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
     * Set the callee and method to call on the callee and pass in a reference
     * to yourself.
     * 
     * @param callee The object which we call the method on.
     * @param method The string representation of the method to call on callee.
     * @precond callee != null && method != null && method.isEmpty() == false
     * @postcond this.getCallee() == callee && this.getMethod() != null
     */
    public void setCalleeAndMethodWithParameter(Object callee, String method)
    {
	_callee = callee;
	
	Class parameterTypes[] = {this.getClass()};
	try {
	    _method = callee.getClass().getMethod(method, parameterTypes);
	} catch (SecurityException e) {
	    e.printStackTrace();
	} catch (NoSuchMethodException e) {
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
	    if (_method.getParameterTypes().length <= 0) {
		_method.invoke(_callee, (Object[])null);
	    } else {
		Object[] buttons = {this};
		_method.invoke(_callee, buttons);
	    }
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
