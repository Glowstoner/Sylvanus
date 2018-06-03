package fr.glowstoner.sylvanus.api;

import fr.glowstoner.sylvanus.Sylvanus;

public abstract class SylvanusAPI {
	
	public SylvanusAPI() {
		
	}

	public Sylvanus getInstance() {
		return Sylvanus.getInstance();
	}
	
	public abstract void start();
	
	public abstract void stop();
}
