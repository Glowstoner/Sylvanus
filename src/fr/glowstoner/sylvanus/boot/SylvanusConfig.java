package fr.glowstoner.sylvanus.boot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SylvanusConfig {
	
	private final String regex = 
			"\\s+(?=((\\\\[\\\\\"]|[^\\\\\"])*\"(\\\\[\\\\\"]|[^\\\\\"])*\")*(\\\\[\\\\\"]|[^\\\\\"])*$)";
	private final List<String> keys = Arrays.asList("username", "description", "port");
	
	private Map<String, String> data = new HashMap<>();
	private File config, path;
	
	/*
	 * @username=SylvainDuriff
	 * @description="Bonjour, je suis un habitant du réseau sous-terrain d'Agartha ! \n J'aime beaucoup le frommage !"
	 * @port=666
	 */
	
	public SylvanusConfig(File path) {
		this.path = path;
	}
	
	public void load() throws IOException {
		if(this.hasConfigurationFile()) {
			System.out.println("Fichier de configuration existant ! :) Chargement de celui-ci ...");
			this.loadFile();
		}else {
			System.out.println("Fichier de configuration inexistant ! :( Création et chargement de celui-ci ...");
			
			this.createNewConfigurationFile();
			this.loadFile();
		}
	}

	public void createNewConfigurationFile() throws IOException {
		this.config = new File(this.path, "config.sylc");
		
		this.config.createNewFile();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(this.config));
		
		bw.write("@username=Sylvanuser\n");
		bw.write("@description=\"Aucune description n'a été définie.\"\n");
		bw.write("@port=6666");
		
		bw.close();
	}
	
	public boolean hasConfigurationFile() {
		return new File(this.path, "config.sylc").exists();
	}
	
	public void loadFile() throws IOException {
		this.config = new File(this.path, "config.sylc");
		
		List<String> lines = Files.readAllLines(this.config.toPath());
		
		for(String line : lines) {
			this.loadLine(line);
		}
	}
	
	public void showData() {
		if(this.data != null) {
			for(String keys : this.data.keySet()) {
				String value = this.data.get(keys);
				
				System.out.println("Valeur de la configuration : "+keys+" -> "+value);
			}
		}
	}
	
	public Map<String, String> getData() {
		return this.data;
	}
	
	private void loadLine(String line) {
		String base = line.replaceAll(this.regex, "");
		
		if(line.startsWith("@")) {
			String key = base.substring(1, base.indexOf("="));
			
			for(String keyw : this.keys) {
				if(keyw.equalsIgnoreCase(key)) {
					if(!this.data.containsKey(keyw)) {
						String val = base.substring(base.indexOf("=") + 1, base.length());
						
						//special keys
						val = (keyw.equals("description")) ? val.substring(1, val.length() - 1) : val;
						
						this.data.put(keyw, val);
					}
				}
			}
		}
	}
}