package ca.sandstorm.luminance.gameobject;

import ca.sandstorm.luminance.graphics.IRenderable;

public interface IRenderableObject extends IGameObject
{
    /**
     * Get the model associated with this object.
     * @return Associated model
     */
    IRenderable getRenderable();
    
    /**
     * Get the texture associated with this object.
     * @return Associated texture
     */
    int getTexture();
}
