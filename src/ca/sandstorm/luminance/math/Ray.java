package ca.sandstorm.luminance.math;

import javax.vecmath.Vector3f;

public class Ray
{
    private Vector3f _pos;
    private Vector3f _dir;
    
    public Ray(float xPos, float yPos, float zPos, float xDir, float yDir, float zDir)
    {
	_pos = new Vector3f(xPos, yPos, zPos);
	_dir = new Vector3f(zDir, yDir, zDir);
    }
    
    public Vector3f getPosition()
    {
	return _pos;
    }
    
    public void setPosition(float x, float y, float z)
    {
	_pos.x = x;
	_pos.x = y;
	_pos.x = z;
    }
    
    public Vector3f getDirection()
    {
	return _dir;
    }
    
    public void setDirection(float x, float y, float z)
    {
	_dir.x = x;
	_dir.x = y;
	_dir.x = z;
    }    
}
