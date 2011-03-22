package ca.sandstorm.luminance.gamelogic;

import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.graphics.Color;

import ca.sandstorm.luminance.gameobject.IRenderableObject;
import ca.sandstorm.luminance.gameobject.Receptor;
import ca.sandstorm.luminance.gameobject.RenderType;
import ca.sandstorm.luminance.graphics.IRenderable;
import ca.sandstorm.luminance.graphics.PrimitiveBox;
import ca.sandstorm.luminance.graphics.PrimitivePrism;
import ca.sandstorm.luminance.graphics.PrimitiveSphere;


/**
 * Graphics renderer for drawing objects.
 * @author zenja and halsafar
 */
public class GameRenderer
{
    private static final Logger _logger = LoggerFactory.getLogger("Luminance.GLRenderer");
    
    // Primitives to be reused in draws
    private PrimitiveBox _box;
    private PrimitiveSphere _sphere;
    private PrimitivePrism _prism;
    
    private LinkedList<IRenderableObject> _normalObjects;
    private LinkedList<IRenderableObject> _alphaObjects;
    private LinkedList<IRenderableObject> _reflectionObjects;
    
    /**
     * Constructor.
     * Create the primitives.
     */
    public GameRenderer()
    {
	_logger.debug("GameRenderer()");
	
	_normalObjects = new LinkedList<IRenderableObject>();
	_alphaObjects = new LinkedList<IRenderableObject>();
	_reflectionObjects = new LinkedList<IRenderableObject>();
	
	_box = new PrimitiveBox();
	_sphere = new PrimitiveSphere();
	_prism = new PrimitivePrism();
    }
    
    /**
     * Remove all objects from renderer list.
     */
    public void removeAll()
    {
	_normalObjects.clear();
	_alphaObjects.clear();
	_reflectionObjects.clear();
    }
    
    
    /**
     * Add a new object to be drawn on every frame render.
     * @param object Renderable object to be added.
     */
    public void add(IRenderableObject object)
    {
	if (object.getRenderType() == RenderType.Normal)
	{
	    _normalObjects.add(object);
	}
	else if (object.getRenderType() == RenderType.Alpha)
	{
	    _alphaObjects.add(object);
	}
	else if (object.getRenderType() == RenderType.Reflection)
	{
	    _reflectionObjects.add(object);
	}
    }
    
        
    /**
     * Remove an object from the automatic draw list.
     * @param object Renderable object to remove.
     */
    public void remove(IRenderableObject object)
    {
	if (object.getRenderType() == RenderType.Normal)
	{
	    _normalObjects.remove(object);
	}
	else if (object.getRenderType() == RenderType.Alpha)
	{
	    _alphaObjects.remove(object);
	}
	else if (object.getRenderType() == RenderType.Reflection)
	{
	    _reflectionObjects.remove(object);
	}
    }
    
    
    /**
     * Get a box primitive.
     * @return Box primitive
     */
    public PrimitiveBox getBox()
    {
	return _box;
    }
    
    
    /**
     * Get a sphere primitive.
     * @return Sphere primitive
     */
    public PrimitiveSphere getSphere()
    {
	return _sphere;
    }
    
    /**
     * Get a prism primitive.
     * @return Prism primitive
     */
    public IRenderable getPrism()
    {
	return _prism;
    }
    
    
    /**
     * Draw the normal objects which require no special rendering.
     * @param gl OpenGL context
     */
    private void drawNormalObjects(GL10 gl)
    {
	for (IRenderableObject object : _normalObjects) {
	    gl.glPushMatrix();
	    
	    // Position object
	    Vector3f position = object.getPosition();
	    gl.glTranslatef(position.x, position.y, position.z);	    
	    
	    // Rotate object
	    Vector4f rotation = object.getRotation();
	    if (rotation != null) {
		gl.glRotatef(rotation.z, 0, 0, 1);
		gl.glRotatef(rotation.y, 0, 1, 0);
		gl.glRotatef(rotation.x, 1, 0, 0);
	    }
	        

	    // Scale
	    Vector3f scale = object.getScale();
	    if (scale != null) {
		gl.glScalef(scale.x, scale.y, scale.z);
	    }
	    
	    // Color - only Receptors have tint
	    if (object instanceof Receptor) {
		int color = ((Receptor)object).getColor();
		gl.glColor4f(Color.red(color) / 255.0f, Color.green(color) / 255.0f, Color.blue(color) / 255.0f, Color.alpha(color) / 255.0f);
		/*switch(color) {
		    case Color.RED:
			gl.glColor4f(1.0f, 0.2f, 0.2f, 1.0f);
			break;
		    case Color.BLUE:
			gl.glColor4f(0.2f, 0.2f, 1.0f, 1.0f);
			break;
		    case Color.GREEN:
			gl.glColor4f(0.2f, 1.0f, 0.2f, 1.0f);
			break;
		}*/
	    } else {
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	    }

	    // Texture
	    int texture = object.getTexture();
	    if (texture > 0) {  // 0 = no texture
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, object.getTexture());
	    } else {
	    }

	    // Draw
	    object.getRenderable().draw(gl);
	    
	    // Reset state
	    gl.glDisable(GL10.GL_TEXTURE_2D);
	    gl.glPopMatrix();
	}	
    }
    
    
    
    /**
     * Draw the alpha objects which require sorted and special rendering.
     * @param gl OpenGL context
     */
    private void drawAlphaObjects(GL10 gl)
    {
	
    }
    
    
    /**
     * Draw the reflection objects which require special rendering.
     * @param gl OpenGL context
     */
    private void drawReflectionObjects(GL10 gl)
    {
	
    }
    
    
    /**
     * Render all objects that are being tracked by the renderer.
     * @param gl OpenGL context to render with
     */
    public void draw(GL10 gl)
    {
	drawNormalObjects(gl);
	drawAlphaObjects(gl);
	drawReflectionObjects(gl);
    }
}
