package fr.glowstoner.sylvanus.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {
	
	private Map<String, Command> commands = new HashMap<>();
	
	public CommandManager() {
		
	}

	public void register(Command command) {
		if(!this.commands.containsKey(command.getName())) {
			this.commands.put(command.getName(), command);
		}
	}
	
	public void runCommand(String base) {
		//detection
		if(base.length() == 0) {
			return;
		}
		
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
		
		String[] all = base.split(" ");
		String commandBase = all[0];
		
		List<String> alll = Arrays.asList(all);
		alll.remove(commandBase);
		
		String[] args = (String[]) alll.toArray();
		
		//execution
		if(this.commands.containsKey(commandBase)) {
			Command c = this.commands.get(commandBase);
			
			c.execute(commandBase, args);
		}else {
			System.out.println("Commande inconnue ! :(");
		}
	}
}