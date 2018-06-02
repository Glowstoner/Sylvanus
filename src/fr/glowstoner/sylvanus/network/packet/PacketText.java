package fr.glowstoner.sylvanus.network.packet;

import fr.glowstoner.sylvanus.crypt.CryptMessage;

public class PacketText extends Packet {

	private static final long serialVersionUID = -4364299881735477296L;
	
	private String text;
	
	public PacketText(String key, String username, String text) {
		super(username);
		
		this.setText(text, key);
	}

	public String getText(String key) {
		return CryptMessage.decrypt(key, this.text);
	}
	
	public String getEncryptedText() {
		return this.text;
	}

	public void setText(String text, String key) {
		this.text = CryptMessage.encrypt(key, text);
	}

	@Override
	public Object data() {
		return this.text;
	}
}
