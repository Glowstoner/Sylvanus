package fr.glowstoner.sylvanus.network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import fr.glowstoner.sylvanus.network.packet.Packet;

public class Client implements ConnectionHandler, Serializable{
	
	private static final long serialVersionUID = -6666814765157664167L;
	
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String connectionKey;

	public Client(String ip, int port) throws IOException{
		socket = new Socket(ip, port);
	}
	
	@Override
	public void sendPacket(Packet packet) throws IOException {
		this.out.writeObject(packet);
		this.out.flush();
	}


	@Override
	public void start() throws IOException {  
		this.out = new ObjectOutputStream(socket.getOutputStream());
		this.in = new ObjectInputStream(socket.getInputStream());
      
		ClientServerThread re = new ClientServerThread(this.in);
		re.start();
	}
   

	@Override
	public void close() throws IOException {
		if(this.socket != null)  this.socket.close();
		if(this.in != null) this.in.close();
		if(this.out != null) this.out.close();
	}
	
	public void setConnectionKey(String key) {
		this.connectionKey = key;
	}
	
	public String getConnectionKey() {
		return this.connectionKey;
	}
	
	public boolean hasConnectionKey() {
		return this.connectionKey != null;
	}
   
	public class ClientServerThread extends Thread {
	   
		private ObjectInputStream in;
		private boolean active;
	   
		public ClientServerThread(ObjectInputStream in) {
			this.in = in;
			this.active = true;
		}
	   
		@Override
		public void run() {
			while(this.active) {
				Object o = null;
			   
				try {
					try {
						o = this.in.readObject();
					}catch(EOFException e) {
						try {
							o = this.in.readObject();
						}catch (EOFException ex) {
							break;
						}
					}
				   
					if(o instanceof Packet) {
						Packet p = (Packet) o;
						
						PacketListenerManager.call(p);
					}
				   
				} catch (Exception e) {
					e.printStackTrace();
				   
					active = false;
				}
			}
		   
			super.interrupt();
	   	}
   	}
}