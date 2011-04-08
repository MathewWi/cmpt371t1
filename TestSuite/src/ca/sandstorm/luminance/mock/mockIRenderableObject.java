package ca.sandstorm.luminance.mock;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import ca.sandstorm.luminance.gameobject.IRenderableObject;
import ca.sandstorm.luminance.gameobject.LightBeamCollection;
import ca.sandstorm.luminance.gameobject.RenderType;
import ca.sandstorm.luminance.graphics.IRenderable;
import ca.sandstorm.luminance.math.Sphere;

public class mockIRenderableObject implements IRenderableObject{

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
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
	public void beamInteract(LightBeamCollection beamCollection, int beamIndex,
			int lightIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector3f getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPosition(Vector3f position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRotation(float x, float y, float z) {
		// TODO Auto-generated method stub
		
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

	@Override
	public IRenderable getRenderable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTexture() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RenderType getRenderType() {
		// TODO Auto-generated method stub
		return null;
	}

}
