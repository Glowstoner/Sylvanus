package fr.glowstoner.sylvanus.commands;

import fr.glowstoner.sylvanus.Sylvanus;

public abstract class Command {

	private String name;
	
	public Command(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Sylvanus getInstance() {
		return Sylvanus.getInstance();
	}
	
	public abstract void execute(String command, String[] args);

	public abstract String description();
}
