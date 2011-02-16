package ca.sandstorm.luminance.graphics;

import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.gameobject.IGameObject;
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
	    gl.glColor4f(1.0f, 0.5f, 0.5f, 1.0f);
	    
	    Vector3f position = object.getPosition();
	    gl.glTranslatef(position.x, position.y, position.z);
	    gl.glScalef(1f, 1f, 1f);
	    object.getRenderable().draw(gl);
	    
	    gl.glPopMatrix();
	}
    }
}
