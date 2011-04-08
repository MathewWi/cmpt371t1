package ca.sandstorm.luminance.mock;

import ca.sandstorm.luminance.gameobject.IGameObject;
import ca.sandstorm.luminance.gametools.ITool;
import ca.sandstorm.luminance.resources.TextureResource;

public class mockITool implements ITool{

	public mockIGameObject migo;
	
	public mockITool(){
		migo = new mockIGameObject();
	}
	
	@Override
	public IGameObject getGameObject() {
		// TODO Auto-generated method stub
		return migo;
	}

	@Override
	public TextureResource getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
