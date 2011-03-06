package ca.sandstorm.luminance.gametools;

import ca.sandstorm.luminance.Engine;
import ca.sandstorm.luminance.gameobject.IGameObject;
import ca.sandstorm.luminance.resources.TextureResource;

public class PrismTool implements ITool
{

    @Override
    public IGameObject getGameObject()
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public TextureResource getIcon()
    {
	return (TextureResource)Engine.getInstance().getResourceManager().getResource("textures/missing.jpg");
    }

}
