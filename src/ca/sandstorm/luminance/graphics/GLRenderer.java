package ca.sandstorm.luminance.graphics;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Graphics renderer for drawing objects.
 * @author zenja
 */
public class GLRenderer
{
    private static final Logger logger = LoggerFactory.getLogger("Luminance.GLRenderer");
    private GL10 gl;  // currently unused
    
    // Primitives to be reused in draws
    private PrimitiveBox box;
    private PrimitiveSphere sphere;
    
    /**
     * Constructor.
     * Create the primitives.
     */
    public GLRenderer()
    {
	box = new PrimitiveBox();
	sphere = new PrimitiveSphere();
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
    
    /**
     * Draw a renderable object.
     * @param object Object to draw
     * @param position Position in world
     * @param rotation Rotation vector
     * @param scale Scale vector
     * @param color Color to draw in
     * @param gl OpenGL instance to draw with
     */
    public void draw(IRenderable object, Vector3f position, Vector3f rotation, Vector3f scale, Vector3f color, GL10 gl)
    {
	gl.glPushMatrix();
	
	// Rotate, scale, and translate object
	//gl.glRotatef(0.0f, rotation.x, rotation.y, rotation.z);  //TODO: fix
	gl.glScalef(scale.x, scale.y, scale.z);
	gl.glTranslatef(position.x, position.y, position.z);
	
	// Set the face rotation
	gl.glFrontFace(GL10.GL_CW);
	
	// Set the color
	gl.glColor4f(color.x, color.y, color.z, 1.0f);

	// Point to the vertex buffer
	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, object.getVertexBuffer());
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	
	// Use a normal buffer if available
	if(object.getNormalBuffer() != null) {
	    gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
	    gl.glNormalPointer(GL10.GL_FLOAT, 0, object.getNormalBuffer());
	}
	
	// Use index buffer if available, or just draw the vertices if not
	if(object.getIndexBuffer() == null)
	    gl.glDrawArrays(GL10.GL_TRIANGLES, 0, object.getVertexBuffer().limit() / 3);
	else 
	    gl.glDrawElements(GL10.GL_TRIANGLES, object.getIndexBuffer().limit(), GL10.GL_UNSIGNED_BYTE, object.getIndexBuffer());

	// Restore state
	gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
	gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	gl.glPopMatrix();
    }
}
