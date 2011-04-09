package ca.sandstorm.luminance.mock;

import ca.sandstorm.luminance.resources.IResource;

public class mockIResource implements IResource{

	public String name;
	public int memSize;
	
	public mockIResource(){
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public int getMemorySize() {
		// TODO Auto-generated method stub
		return memSize;
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
