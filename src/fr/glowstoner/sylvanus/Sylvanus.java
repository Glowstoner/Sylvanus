package fr.glowstoner.sylvanus;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import fr.glowstoner.sylvanus.boot.SylvanusBoot;
import fr.glowstoner.sylvanus.boot.SylvanusConfig;
import fr.glowstoner.sylvanus.commands.TestCommand;
import fr.glowstoner.sylvanus.network.Client;
import fr.glowstoner.sylvanus.network.Mode;
import fr.glowstoner.sylvanus.network.Server;
import fr.glowstoner.sylvanus.profile.PrivateProfile;
import fr.glowstoner.sylvanus.profile.PublicProfile;

public class Sylvanus {
	
	private static Sylvanus instance;
	
	private Map<String, String> data;
	private SylvanusBoot boot;
	private SylvanusConfig config;
	private PrivateProfile privateProfile;
	private PublicProfile publicProfile;
	private Server server;
	private Client client;
	private SylvanusConsole console;
	
	public Sylvanus() {
		
	}

	public void init() {
		this.boot = new SylvanusBoot();
		this.boot.init();
		
		for (int i = 0; i < 100; ++i) System.out.println();
		
		this.sendImage();
		
		try {
			DataInputStream inp = new DataInputStream(System.in);
			
			System.out.println("\n\n");
			
			while(true) {
				System.out.println("CHOIX (H/C)");
				
				@SuppressWarnings("deprecation")
				String l = inp.readLine();
				
				if(l.equalsIgnoreCase("H")) {
					this.selectedServer();
					this.startConsole();
					
					break;
				}else if(l.equalsIgnoreCase("C")) {
					this.selectedConnection();
					this.startConsole();
					
					break;
				}
			}
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void sendImage() {
		final String red = "\u001B[31m";
		final String reset = "\u001B[0m";
		
		System.out.println(red+" ________       ___    ___ ___       ___      ___ ________  ________   ___  ___  ________      ");
		System.out.println(red+"|\\   ____\\     |\\  \\  /  /|\\  \\     |\\  \\    /  /|\\   __  \\|\\   ___  \\|\\  \\|\\  \\|\\   ____\\     ");
		System.out.println(red+"|\\   ____\\     |\\  \\  /  /|\\  \\     |\\  \\    /  /|\\   __  \\|\\   ___  \\|\\  \\|\\  \\|\\   ____\\     ");
		System.out.println(red+"\\ \\  \\___|_    \\ \\  \\/  / | \\  \\    \\ \\  \\  /  / | \\  \\|\\  \\ \\  \\\\ \\  \\ \\  \\\\\\  \\ \\  \\___|_    ");
		System.out.println(red+" \\ \\_____  \\    \\ \\    / / \\ \\  \\    \\ \\  \\/  / / \\ \\   __  \\ \\  \\\\ \\  \\ \\  \\\\\\  \\ \\_____  \\   ");
		System.out.println(red+"    ____\\_\\  \\ __/  / /      \\ \\_______\\ \\__/ /     \\ \\__\\ \\__\\ \\__\\\\ \\__\\ \\_______\\____\\_\\  \\ ");
		System.out.println(red+"   |\\_________\\\\___/ /        \\|_______|\\|__|/       \\|__|\\|__|\\|__| \\|__|\\|_______|\\_________\\");
		System.out.println(red+"   \\|_________\\|___|/                                                              \\|_________|");
		System.out.println(red+"                                                                                               ");
		System.out.println(reset+"\n");
	}
	
	@SuppressWarnings("deprecation")
	public void selectedConnection() {
		DataInputStream inp = new DataInputStream(System.in);
		
		while(true) {
			System.out.println("Veuillez spécifier une IP sous le format suivant : IP@port");
			
			String[] args = null;
			try {
				args = inp.readLine().split("@");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(args.length > 2) {
				System.out.println("Format d'IP invalide ! "+Arrays.asList(args).toString());
				
				continue;
			}
			
			if(args.length == 1) {
				this.connect(args[0]);
			}else if(args.length == 2) {
				this.connect(args[0], Integer.valueOf(args[1]));
			}
			
			break;
		}
	}
	
	public void startConsole() {
		this.console = new SylvanusConsole();
		
		try {
			this.console.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void selectedServer() {
		this.initServer();
		
		System.out.println("Attente d'une connection distante ...");
	}
	
	public void connect(String ip, int port) {
		try {
			this.setClient(new Client(ip, port));
			this.getClient().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void connect(String ip) {
		this.connect(ip, this.getPort());
	}
	
	public void initServer() {
		System.out.println("Démarrage du serveur sur le port "+Sylvanus.getInstance().getPort()+" ...");
		
		try {
			Sylvanus.getInstance().setServer(new Server(Sylvanus.getInstance().getPort()));
			Sylvanus.getInstance().getServer().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Fait.");
	}
	
	public Mode getMode() {
		if(this.server == null && this.client != null) {
			return Mode.CLIENT;
		}else if(this.server != null && this.client == null) {
			return Mode.SERVER;
		}else {
			return null;
		}
	}
	
	public SylvanusConfig getConfig() {
		return this.config;
	}
	
	public Map<String, String> getProperties() {
		return this.data;
	}
	
	public void setProperties(Map<String, String> data) {
		this.data = data;
	}
	
	public String getUsername() {
		return this.data.get("username");
	}
	
	public File getFolder() {
		return this.boot.getFolder();
	}
	
	public static Sylvanus newInstance() {
		instance = new Sylvanus();
		
		return instance;
	}
	
	public static Sylvanus getInstance() {
		return instance;
	}
	
	public PrivateProfile getPrivateProfile() {
		return privateProfile;
	}

	public void setPrivateProfile(PrivateProfile privateProfile) {
		this.privateProfile = privateProfile;
	}
	
	public boolean checkPrivateProfile() throws IOException {
		//exists
		if(PrivateProfile.hasFile()) {
			this.privateProfile = (PrivateProfile) new PrivateProfile().deserialize();
			
			return true;
		}else {
			this.privateProfile = new PrivateProfile();
			this.privateProfile.serialize();
			
			return false;
		}
	}

	public PublicProfile getPublicProfile() {
		return publicProfile;
	}

	public void setPublicProfile(PublicProfile publicProfile) {
		this.publicProfile = publicProfile;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public int getPort() {
		return Integer.valueOf(this.data.get("port"));
	}
	
	public SylvanusConsole getConsole() {
		return this.console;
	}

	public static class Main {
		
		public static void main(String[] args) {
			Sylvanus.newInstance().init();
			
			Sylvanus.getInstance().getConsole().getCommandManager().register(new TestCommand());
		}
		
	}
}
