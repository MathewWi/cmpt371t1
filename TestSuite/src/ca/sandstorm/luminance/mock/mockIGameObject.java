package ca.sandstorm.luminance.mock;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import ca.sandstorm.luminance.gameobject.IGameObject;
import ca.sandstorm.luminance.gameobject.LightBeamCollection;
import ca.sandstorm.luminance.math.Sphere;

public class mockIGameObject implements IGameObject {
	public Vector3f v;
	
	public mockIGameObject() {
		v = new Vector3f(1f,1f,1f);
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		v = new Vector3f(1f,1f,1f);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beamInteract(LightBeamCollection beamCollection,
			int beamIndex, int lightIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector3f getPosition() {
		// TODO Auto-generated method stub
		return v;
	}

	@Override
	public void setPosition(Vector3f position) {
		// TODO Auto-generated method stub
		v = position;
	}

	@Override
	public void setRotation(float x, float y, float z) {
		// TODO Auto-generated method stub
		v.x = x;
		v.y = y;
		v.z = z;
	}

	@Override
	public Vector4f getRotation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector3f getScale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getCurrentYRotation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getNextYRotation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getPrevYRotation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Sphere getCollisionSphere() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
