package ca.sandstorm.luminance.gametools;

import ca.sandstorm.luminance.gameobject.IGameObject;
import ca.sandstorm.luminance.resources.TextureResource;

/**
 * UNUSED - left around just in case it comes in use later
 * @author halsafar
 *
 */
public interface ITool
{
    public IGameObject getGameObject();
    
    public TextureResource getIcon();
}
