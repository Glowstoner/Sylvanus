package fr.glowstoner.sylvanus.network;

import java.io.IOException;

import fr.glowstoner.sylvanus.network.packet.Packet;

public interface ConnectionHandler extends Connection{
	
	void sendPacket(Packet packet) throws IOException;
	
}
