package fr.glowstoner.sylvanus.network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import fr.glowstoner.sylvanus.network.packet.Packet;

public class Server implements Connection, Runnable {

	private List<ConnectionHandler> connections = new ArrayList<>();
	private ServerSocket server;
	private Thread t;
	
	public Server(int port) throws IOException {
		this.server = new ServerSocket(port);
	}
	
	@Override
	public void start() {
		this.t = new Thread(this);
		t.start();
	}
	
	private void addConnection(Socket socket) {
		ServerClientThread c = new ServerClientThread(socket);
		
		try {
			c.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Thread t = new Thread(c);
		t.start();
		
		this.connections.add(c);
	}
	
	public List<ConnectionHandler> getConnections() {
		return this.connections;
	}
	
	@Override
	public void close() {
		if(this.server != null) {
			try {
				this.server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			this.server = null;
		}
	}

	@Override
	public void run() {
		while(t != null) {
			try {
				if(this.server != null) {
					this.addConnection(this.server.accept());
				}	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class ServerClientThread implements ConnectionHandler, Runnable, Serializable{
		
		private static final long serialVersionUID = -3378783954703324349L;
		
		private Socket socket;
		private ObjectInputStream in;
		private ObjectOutputStream out;
		
		public ServerClientThread(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void start() throws IOException {
			this.in = new ObjectInputStream(socket.getInputStream());
			this.out = new ObjectOutputStream(socket.getOutputStream());
		}
		
		@Override
		public void close() throws IOException {			
			if(this.socket != null) this.socket.close();
			if(this.in != null) this.in.close();
			if(this.out != null) this.out.close();
		}
		
		public Socket getSocket() {
			return this.socket;
		}
		
		@Override
		public void run() {
			Object o = null;
			
			while(true) {
				try {
					try {
						o = this.in.readObject();
					}catch (EOFException ex) {
						o = this.in.readObject();
					}catch (IOException ex) {
						ex.printStackTrace();
						
						if(ex instanceof OptionalDataException) {
							OptionalDataException ode = (OptionalDataException) ex;
							
							System.out.println("eof = "+ode.eof);
							System.out.println("available = "+this.in.available());
							
							this.in.skipBytes(ode.length);
							break;
						}else {
							break;
						}
					}
					
					if(o instanceof Packet) {
						Packet p = (Packet) o;
						
						PacketListenerManager.call(p);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					
					break;
				}
			}
			
			try {
				this.close();
			} catch (IOException e) {
				return;
			}
		}

		@Override
		public void sendPacket(Packet packet) throws IOException {
			this.out.writeObject(packet);
			this.out.flush();
		}
	}
}