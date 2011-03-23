package ca.sandstorm.luminance.gamelogic;

import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

import ca.sandstorm.luminance.gameobject.Light;
import ca.sandstorm.luminance.gameobject.LightBeamCollection;


public class LightPath
{
    private LightBeamCollection _lights;
    
    public LightPath()
    {
	_lights = new LightBeamCollection();
    }
    
    
    public LightBeamCollection getLightPaths()
    {
	return _lights;
    }
    
    
    public void draw(GL10 gl)
    {
	for (LinkedList<Light> ll : _lights)
	{
	    for (Light l : ll)
	    {
		l.draw(gl);
	    }
	}
    }
}
