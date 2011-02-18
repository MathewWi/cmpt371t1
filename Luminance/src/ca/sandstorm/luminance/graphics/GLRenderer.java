package ca.sandstorm.luminance.graphics;

import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.gameobject.IRenderableObject;

/**
 * Graphics renderer for drawing objects.
 * @author zenja
 */
public class GLRenderer
{
    private static final Logger _logger = LoggerFactory.getLogger("Luminance.GLRenderer");
    
    // Primitives to be reused in draws
    private PrimitiveBox _box;
    private PrimitiveSphere _sphere;
    
    private LinkedList<IRenderableObject> _renderableObjects;
    
    /**
     * Constructor.
     * Create the primitives.
     */
    public GLRenderer()
    {
	_renderableObjects = new LinkedList<IRenderableObject>();
	_box = new PrimitiveBox();
	_sphere = new PrimitiveSphere();
	
	_logger.debug("GLRenderer created.");
    }
    
    /**
     * Add a new object to be drawn on every frame render.
     * @param object Renderable object to be added.
     */
    public void addRenderable(IRenderableObject object)
    {
	_renderableObjects.add(object);
    }
    
    /**
     * Remove an object from the automatic draw list.
     * @param object Renderable object to remove.
     */
    public void removeRenderable(IRenderableObject object)
    {
	_renderableObjects.remove(object);
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
     * Render all objects that are being tracked by the renderer.
     * @param gl OpenGL context to render with
     */
    public void drawObjects(GL10 gl)
    {
	for (IRenderableObject object : _renderableObjects) {
	    gl.glPushMatrix();
	    	    
	    // Position object
	    Vector3f position = object.getPosition();
	    gl.glTranslatef(position.x, position.y, position.z);
	    
	    // Rotate object
	    Vector4f rotation = object.getRotation();
	    if (rotation != null) {
		gl.glRotatef(rotation.w, rotation.x, rotation.y, rotation.z);
	    }

	    // Scale
	    Vector3f scale = object.getScale();
	    if (scale != null) {
		gl.glScalef(scale.x, scale.y, scale.z);
	    }
	    
	    // Texture
	    int texture = object.getTexture();
	    if (texture > 0) {  // 0 = no texture
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, object.getTexture());
	    } else {
		gl.glColor4f(1.0f, 0.2f, 0.2f, 1.0f);
	    }

	    // Draw
	    object.getRenderable().draw(gl);
	    
	    // Reset state
	    gl.glDisable(GL10.GL_TEXTURE_2D);
	    gl.glPopMatrix();
	}
    }
}
