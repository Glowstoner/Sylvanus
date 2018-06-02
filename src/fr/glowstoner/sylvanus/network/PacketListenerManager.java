package fr.glowstoner.sylvanus.network;

import java.util.ArrayList;
import java.util.List;

import fr.glowstoner.sylvanus.network.packet.Packet;

public class PacketListenerManager {

	private static List<PacketListener> listeners = new ArrayList<>();
	
	public static void register(PacketListener listener) {
		listeners.add(listener);
	}
	
	public static void call(Packet packet) {
		for(PacketListener l : listeners) {
			l.onPacketReceive(packet);
		}
	}
}