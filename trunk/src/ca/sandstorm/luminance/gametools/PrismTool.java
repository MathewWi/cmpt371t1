package ca.sandstorm.luminance.gametools;

import javax.vecmath.Vector3f;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.gameobject.IGameObject;
import ca.sandstorm.luminance.gameobject.Prism;
import ca.sandstorm.luminance.resources.TextureResource;

public class PrismTool implements ITool
{
    private Prism _prism;

    @Override
    public IGameObject getGameObject()
    {
	if(_prism == null) {
	    _prism = new Prism(new Vector3f(0,0,0));
	}
	
	return _prism;
    }

    @Override
    public TextureResource getIcon()
    {
	return (TextureResource)Engine.getInstance().getResourceManager().getResource("textures/missing.jpg");
    }

}
