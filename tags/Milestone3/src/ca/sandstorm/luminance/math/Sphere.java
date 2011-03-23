package ca.sandstorm.luminance.math;

import javax.vecmath.Vector3f;

public class Sphere
{
    private Vector3f _center;
    private float _radius;
    
    public Sphere(float xPos, float yPos, float zPos, float radius)
    {
	_center = new Vector3f(xPos, yPos, zPos);
	_radius = radius;
    }
    
    
    public void setCenter(float x, float y, float z)
    {
	_center.x = x;
	_center.y = y;
	_center.z = z;
    }
    
    public Vector3f getCenter()
    {
	return _center;
    }
    
    public void setRadius(float r)
    {
	_radius = r;
    }
    
    public float getRadius()
    {
	return _radius;
    }
   
}
