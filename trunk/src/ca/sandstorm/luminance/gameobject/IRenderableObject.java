package ca.sandstorm.luminance.gameobject;

import ca.sandstorm.luminance.graphics.IRenderable;

public interface IRenderableObject extends IGameObject
{
    IRenderable getRenderable();
    int getTexture();
}
