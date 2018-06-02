package fr.glowstoner.sylvanus.network.packet;

import java.io.Serializable;

public abstract class Packet implements Serializable{

	private static final long serialVersionUID = -4409759475320459861L;
	
	private String username;
	
	public Packet(String username) {
		this.username = username;
	}
	
	public String getFormat(String decryped) {
		return this.username+" > "+decryped;
	}
	
	public abstract Object data();
	
	public String getSender() {
		return this.username;
	}
}
