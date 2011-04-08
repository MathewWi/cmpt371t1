package ca.sandstorm.luminance.mock;

import ca.sandstorm.luminance.resources.IResource;

public class mockIResource implements IResource{

	public String name;
	public int memSize;
	
	public mockIResource(){
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public int getMemorySize() {
		// TODO Auto-generated method stub
		return memSize;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
