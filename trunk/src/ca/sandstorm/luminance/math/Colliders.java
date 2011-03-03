package ca.sandstorm.luminance.math;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class Colliders
{
    private static final float EPSILON = 1e-8f;
    
    private static Vector3f _tmpCollisionPoint = new Vector3f();
    
    public static double dotProduct(Vector3f v1, Vector3f v2)
    {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }    
    
    public static double intersect(Ray r, Plane p)
    {
        // Calc D for the plane (this is usually pre-calculated
        // and stored with the plane)
        double D = dotProduct(p.getPosition(), p.getNormal());

        // Determine the distance from rOrigin to the point of
        // intersection on the plane

        double numer = dotProduct(p.getNormal(), r.getPosition()) + D;
        double denom = dotProduct(p.getNormal(), r.getDirection());
        return -(numer / denom);
    }
     
    
    public static Vector3f collide(Ray r, Plane p)
    {
        // Get the distance to the collision point

        float time = (float)intersect(r, p);

        // Calculate the collision point
        
        _tmpCollisionPoint.x = r.getPosition().x + r.getDirection().x * time;
        _tmpCollisionPoint.y = r.getPosition().y + r.getDirection().y * time;
        _tmpCollisionPoint.z = r.getPosition().z + r.getDirection().z * time;

        // Here it is...

        return _tmpCollisionPoint;
    }
         
    
    /**
     * Fast Intersection Function between ray/plane
     * @unused
     */
    public static boolean IntersionPlane(Plane plane, Vector3f position, Vector3f direction,
                                       double[] lamda, Vector3f pNormal) {

        double dotProduct = direction.dot(plane.getNormal());
        double l2;

        //determine if ray paralle to plane
        if ((dotProduct < EPSILON) && (dotProduct > -EPSILON))
            return false;

        Vector3f substract = new Vector3f(plane.getPosition());
        substract.sub(position);
        l2 = (plane.getNormal().dot(substract)) / dotProduct;

        if (l2 < -EPSILON)
            return false;

        pNormal.set(plane.getNormal());
        lamda[0] = l2;
        return true;
    }
}
