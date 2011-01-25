package ca.sandstorm.luminance.camera;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sandstorm.luminance.Engine;

import android.opengl.GLU;
import android.opengl.GLUtils;

public class Camera
{
    private static final Logger logger = LoggerFactory.getLogger(Engine.class);
    
    public void setViewPort(GL10 gl, int x, int y, int w, int h)
    {
	logger.debug("setViewPort(" + x + ", " + y + ", " + w + ", " + h + ")");
		
	gl.glViewport(x, y, w, h);
    }
    
    public void setPerspective(GL10 gl, float fov, float aspect, float zNear, float zFar)
    {
	logger.debug("setPerspective(" + fov + ", " + aspect + ", " + zNear + ", " + zFar + ")");
	
	gl.glMatrixMode(GL10.GL_PROJECTION);
	gl.glLoadIdentity();
	
	GLU.gluPerspective(gl, fov, aspect, zNear, zFar);
	
	gl.glMatrixMode(GL10.GL_MODELVIEW);
	gl.glLoadIdentity();
    }
}
