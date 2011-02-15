package ca.sandstorm.luminance.graphics;

import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

public class GLRenderer
{
    private GL10 gl;
    
    private PrimitiveBox box;
    private PrimitiveSphere sphere;
    
    public GLRenderer()
    {
	box = new PrimitiveBox();
	sphere = new PrimitiveSphere();
    }
    
    public PrimitiveBox getBox()
    {
	return box;
    }
    
    public PrimitiveSphere getSphere()
    {
	return sphere;
    }
    
    public void draw(IRenderable object, Vector3f position, Vector3f rotation, Vector3f scale, Vector3f color, GL10 gl)
    {
	gl.glPushMatrix();
	
	// Rotate, scale, and translate object
	//gl.glRotatef(0.0f, rotation.x, rotation.y, rotation.z);
	gl.glScalef(scale.x, scale.y, scale.z);
	gl.glTranslatef(position.x, position.y, position.z);
	
	// Set the face rotation
	gl.glFrontFace(GL10.GL_CW);
	
	// Set the color
	gl.glColor4f(color.x, color.y, color.z, 1.0f);

	// Point to the buffers and draw the object
	//TODO: change hardcoded buffer length to proper way
	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, object.getVertexBuffer());
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	
	if(object.getNormalBuffer() != null) {
	    gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
	    gl.glNormalPointer(GL10.GL_FLOAT, 0, object.getNormalBuffer());
	}
	
	if(object.getIndexBuffer() == null)
	    gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 672);
	else
	    gl.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_BYTE, object.getIndexBuffer());

	gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
	gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	
	gl.glPopMatrix();
    }
}
