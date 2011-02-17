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
    private static final Logger logger = LoggerFactory.getLogger("Luminance.GLRenderer");
    
    // Primitives to be reused in draws
    private PrimitiveBox box;
    private PrimitiveSphere sphere;
    
    private LinkedList<IRenderableObject> renderableObjects;
    
    /**
     * Constructor.
     * Create the primitives.
     */
    public GLRenderer()
    {
	renderableObjects = new LinkedList<IRenderableObject>();
	box = new PrimitiveBox();
	sphere = new PrimitiveSphere();
	
	logger.debug("GLRenderer created.");
    }
    
    public void addRenderable(IRenderableObject object)
    {
	renderableObjects.add(object);
    }
    
    public void removeRenderable(IRenderableObject object)
    {
	renderableObjects.remove(object);
    }
    
    /**
     * Get a box primitive.
     * @return Box primitive
     */
    public PrimitiveBox getBox()
    {
	return box;
    }
    
    /**
     * Get a sphere primitive.
     * @return Sphere primitive
     */
    public PrimitiveSphere getSphere()
    {
	return sphere;
    }
        
    public void drawObjects(GL10 gl)
    {
	for (IRenderableObject object : renderableObjects) {
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
