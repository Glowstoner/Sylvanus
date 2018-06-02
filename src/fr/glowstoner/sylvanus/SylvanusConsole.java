package fr.glowstoner.sylvanus;

import java.io.DataInputStream;
import java.io.IOException;

import fr.glowstoner.sylvanus.commands.CommandManager;
import fr.glowstoner.sylvanus.network.ConnectionHandler;
import fr.glowstoner.sylvanus.network.PacketHandler;
import fr.glowstoner.sylvanus.network.PacketListenerManager;
import fr.glowstoner.sylvanus.network.packet.PacketText;

public class SylvanusConsole {
	
	private DataInputStream in;
	private CommandManager cm;
	private boolean active;
	
	public SylvanusConsole() {
		this.in = new DataInputStream(System.in);
		this.active = true;
		
		this.cm = new CommandManager();
	}

	public void start() throws IOException {
		PacketListenerManager.register(new PacketHandler());
		
		while(this.active) {
			@SuppressWarnings("deprecation")
			String line = in.readLine();
			
			if(line.length() > 0) {
				if(line.startsWith("/")) {
					//cmds
					this.cm.runCommand(line);
				}else {
					this.sendMessage(line);
				}
			}
		}
	}
	
	public void close() {
		
	}
	
	public void sendMessage(String message) {
		PacketText text = new PacketText(Sylvanus.getInstance().getPrivateProfile().getKey(),
				Sylvanus.getInstance().getUsername(), message);
		
		switch (Sylvanus.getInstance().getMode()) {
			case SERVER:
				for(ConnectionHandler ch : Sylvanus.getInstance().getServer().getConnections()) {
					try {
						ch.sendPacket(text);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				System.out.println(text.getFormat(message));
				
				break;
			case CLIENT:
				try {
					Sylvanus.getInstance().getClient().sendPacket(text);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				System.out.println(text.getFormat(message));
				
				break;
		}
	}
	
	public CommandManager getCommandManager() {
		return this.cm;
	}
}
