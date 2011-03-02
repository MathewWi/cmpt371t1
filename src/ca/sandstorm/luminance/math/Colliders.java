package ca.sandstorm.luminance.math;

import javax.vecmath.Vector3f;

public class Colliders
{
    private static final float EPSILON = 1e-8f;
    
    /**
     * Fast Intersection Function between ray/plane
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
