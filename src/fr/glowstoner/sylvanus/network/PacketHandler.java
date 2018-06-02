package fr.glowstoner.sylvanus.network;

import fr.glowstoner.sylvanus.Sylvanus;
import fr.glowstoner.sylvanus.network.packet.Packet;
import fr.glowstoner.sylvanus.network.packet.PacketText;

public class PacketHandler implements PacketListener{

	@Override
	public void onPacketReceive(Packet packet) {
		if(packet instanceof PacketText) {
			PacketText text = (PacketText) packet;
			
			if(Sylvanus.getInstance().getMode().equals(Mode.CLIENT)) {
				if(Sylvanus.getInstance().getClient().hasConnectionKey()) {
					System.out.println(text.getFormat(text.getText
							(Sylvanus.getInstance().getClient().getConnectionKey())));
				}else {
					System.out.println("Clé de décryptage non présente ! Message crypté : "+text.getEncryptedText());
				}
			}else if(Sylvanus.getInstance().getMode().equals(Mode.SERVER)) {
				System.out.println(text.getFormat(text.getText(Sylvanus.getInstance().getPrivateProfile().getKey())));
			}
		}
	}

}
