package fr.glowstoner.sylvanus.commands;

public abstract class Command {

	private String name;
	
	public Command(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public abstract void execute(String command, String[] args);

	public abstract String description();
}
