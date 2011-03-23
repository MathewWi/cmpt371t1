package ca.sandstorm.luminance.math;

import javax.vecmath.Vector3f;

public class Plane
{
    private Vector3f _pos;
    private Vector3f _normal;
    
    public Plane(float xPos, float yPos, float zPos, float xNormal, float yNormal, float zNormal)
    {
	_pos = new Vector3f();
	_normal = new Vector3f();
	
	setPosition(xPos, yPos, zPos);
	setNormal(xNormal, yNormal, zNormal);
    }

    public void setPosition(float x, float y, float z)
    {
	_pos.x = x;
	_pos.y = y;
	_pos.z = z;
    }

    public Vector3f getPosition()
    {
	return _pos;
    }

    public void setNormal(float x, float y, float z)
    {
	_normal.x = x;
	_normal.y = y;
	_normal.z = z;
    }

    public Vector3f getNormal()
    {
	return _normal;
    }
}
