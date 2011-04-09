package ca.sandstorm.luminance.mock;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import ca.sandstorm.luminance.gameobject.IGameObject;
import ca.sandstorm.luminance.gameobject.LightBeamCollection;
import ca.sandstorm.luminance.math.Sphere;

public class mockIGameObject implements IGameObject {
	public Vector3f v3;
	public Vector4f v4;
	public float cur, prev, next;
	public Sphere sphere;
	
	public mockIGameObject(){
		initialize();
	}

	public void initialize() {
		// TODO Auto-generated method stub
		v3 = new Vector3f(1f,1f,1f);
		v4 = new Vector4f();
		sphere = new Sphere(0,0,0,0);
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}


	public void beamInteract(LightBeamCollection beamCollection,
			int beamIndex, int lightIndex) {
		// TODO Auto-generated method stub
		
	}

	public Vector3f getPosition() {
		// TODO Auto-generated method stub
		return v3;
	}

	public void setPosition(Vector3f position) {
		// TODO Auto-generated method stub
		v3 = position;
	}

	public void setRotation(float x, float y, float z) {
		// TODO Auto-generated method stub
		v3.x = x;
		v3.y = y;
		v3.z = z;
	}

	public Vector4f getRotation() {
		// TODO Auto-generated method stub
		return v4;
	}

	public Vector3f getScale() {
		// TODO Auto-generated method stub
		return v3;
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

	public int getYRotationCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
