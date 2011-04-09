package ca.sandstorm.luminance.mock;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import ca.sandstorm.luminance.gameobject.IRenderableObject;
import ca.sandstorm.luminance.gameobject.LightBeamCollection;
import ca.sandstorm.luminance.gameobject.RenderType;
import ca.sandstorm.luminance.graphics.IRenderable;
import ca.sandstorm.luminance.math.Sphere;

public class mockIRenderableObject implements IRenderableObject{

	public Vector3f position, scale;
	public Vector4f rotation;
	public float cur, prev, next;
	public mockIRenderable mir;
	public RenderType rt;
	public int texture;
	public Sphere sphere;
	
	public mockIRenderableObject(){
		initialize();
	}
	
	public void initialize() {
		// TODO Auto-generated method stub
		position = new Vector3f();
		scale = new Vector3f();
		rotation = new Vector4f();
		mir = new mockIRenderable();
		sphere = new Sphere(0,0,0,0);
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void beamInteract(LightBeamCollection beamCollection, int beamIndex,
			int lightIndex) {
		// TODO Auto-generated method stub
		
	}

	public Vector3f getPosition() {
		// TODO Auto-generated method stub
		return position;
	}

	public void setPosition(Vector3f position) {
		// TODO Auto-generated method stub
		this.position = position;
		
	}

	public void setRotation(float x, float y, float z) {
		// TODO Auto-generated method stub
		rotation = new Vector4f(x,y,z, 0);
		
	}

	public Vector4f getRotation() {
		// TODO Auto-generated method stub
		return rotation;
	}

	public Vector3f getScale() {
		// TODO Auto-generated method stub
		return scale;
	}

	public float getCurrentYRotation() {
		// TODO Auto-generated method stub
		return cur;
	}

	public float getNextYRotation() {
		// TODO Auto-generated method stub
		return next;
	}

	public float getPrevYRotation() {
		// TODO Auto-generated method stub
		return prev;
	}

	public Sphere getCollisionSphere() {
		// TODO Auto-generated method stub
		return sphere;
	}

	public IRenderable getRenderable() {
		// TODO Auto-generated method stub
		return mir;
	}

	public int getTexture() {
		// TODO Auto-generated method stub
		return texture;
	}

	public RenderType getRenderType() {
		// TODO Auto-generated method stub
		return rt;
	}

	public int getYRotationCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
