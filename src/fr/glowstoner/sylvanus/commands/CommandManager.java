package fr.glowstoner.sylvanus.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {
	
	private Map<String, Command> commands = new HashMap<>();
	
	public CommandManager() {
		
	}

	public void register(Command command) {
		System.out.println("register cmd -> "+command.getName()+" / "+command.description());
		
		if(!this.commands.containsKey(command.getName().toLowerCase())) {
			this.commands.put(command.getName().toLowerCase(), command);
		}
	}
	
	public void runCommand(String base) {
		//detection
		if(base.length() == 0) {
			return;
		}
		
		base = base.substring(1);
		
		if(base.startsWith(" ")) {
			int index = -1;
			
			for(int i = 0 ; i < base.length() ; i++) {
				if(base.charAt(i) == ' '){
					continue;
				}else {
					index = i + 1;
				}
			}
			
			base = base.substring(index);
		}
		
		String[] splited = base.split(" ");
		List<String> spl = new ArrayList<String>();
		
		for(String el : splited) {
			if(el.length() > 0) {
				spl.add(el);
			}
		}
		
		String[] all = new String[spl.size()];
		
		for(int j = 0 ; j < spl.size() ; j++) {
			all[j] = spl.get(j);
		}
		
		String commandBase = all[0];
		
		List<String> alll = new ArrayList<>(Arrays.asList(all));
		
		alll.remove(0);
		
		String[] args = new String[alll.size()];
		
		for(int j = 0 ; j < alll.size() ; j++) {
			args[j] = alll.get(j);
		}
		
		//execution
		if(this.commands.containsKey(commandBase.toLowerCase())) {
			Command c = this.commands.get(commandBase.toLowerCase());
			
			c.execute(commandBase, args);
		}else {
			System.out.println("Commande inconnue ! :(");
		}
	}
}