package ca.sandstorm.luminance.gametools;

import javax.vecmath.Vector3f;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.gameobject.IGameObject;
import ca.sandstorm.luminance.gameobject.Mirror;
import ca.sandstorm.luminance.resources.TextureResource;

public class MirrorTool implements ITool
{
    private Mirror _mirror;

    @Override
    public IGameObject getGameObject()
    {
	if(_mirror == null) {
	    _mirror = new Mirror(new Vector3f(0,0,0), new Vector3f(0, 45, 0));
	}
	
	return _mirror;
    }

    @Override
    public TextureResource getIcon()
    {
	return (TextureResource)Engine.getInstance().getResourceManager().getResource("textures/missing.jpg");
    }

}
