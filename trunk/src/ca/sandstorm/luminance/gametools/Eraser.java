package ca.sandstorm.luminance.gametools;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.gameobject.IGameObject;
import ca.sandstorm.luminance.resources.TextureResource;

public class Eraser implements ITool
{

    @Override
    public IGameObject getGameObject()
    {
	return null;
    }

    @Override
    public TextureResource getIcon()
    {
	return (TextureResource)Engine.getInstance().getResourceManager().getResource("textures/missing.jpg");
    }
    
}
