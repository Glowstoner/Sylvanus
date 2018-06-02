package fr.glowstoner.sylvanus.network;

import fr.glowstoner.sylvanus.network.packet.Packet;

public interface PacketListener {
	
	void onPacketReceive(Packet packet);

}
