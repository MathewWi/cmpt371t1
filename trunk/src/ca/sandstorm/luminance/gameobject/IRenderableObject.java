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
    
    
    /**
     * Get the Render type for this object.
     * Used by the renderer to determine how to deal with it.
     * @return
     */
    RenderType getRenderType();
}
