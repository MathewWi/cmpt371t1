package ca.sandstorm.luminance.gametools;

import ca.sandstorm.luminance.gameobject.IGameObject;
import ca.sandstorm.luminance.resources.TextureResource;

public interface ITool
{
    public IGameObject getGameObject();
    
    public TextureResource getIcon();
    
    public void interact(LightPath lp);
}
