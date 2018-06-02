package fr.glowstoner.sylvanus.profile;

import java.util.UUID;

public class KeyGenerator {
	
	private String finalKey;
	
	public KeyGenerator() {
		this.finalKey = "staline-"+UUID.randomUUID().toString();
	}

	public String getKey() {
		return this.finalKey;
	}
}
