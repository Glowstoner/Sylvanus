package fr.glowstoner.sylvanus.api;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
	
	private List<SylvanusAPI> mods = new ArrayList<>();
	
	public ModuleManager() {
		
	}
	
	public void register(SylvanusAPI mod) {
		mods.add(mod);
	}

	public void startAll() {
		for(SylvanusAPI apis : mods) {
			apis.start();
		}
	}
	
	public void stopAll() {
		for(SylvanusAPI apis : mods) {
			apis.stop();
		}
	}
}
