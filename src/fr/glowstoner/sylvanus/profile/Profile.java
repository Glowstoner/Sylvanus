package fr.glowstoner.sylvanus.profile;

import java.io.IOException;
import java.io.Serializable;

public abstract class Profile implements Serializable {
	
	private static final long serialVersionUID = -3839459989530500794L;
	
	private String key;
	private ProfileType type;
	
	public Profile(String key, ProfileType type) {
		this.key = key;
		this.type = type;
	}
	
	public Profile(String key) {
		this(key, ProfileType.OTHER);
	}
	
	public Profile() {
		
	}
	
	public abstract void serialize() throws IOException;
	public abstract Profile deserialize() throws IOException;
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public void setType(ProfileType type) {
		this.type = type;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public ProfileType getType() {
		return this.type;
	}
}
