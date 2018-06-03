package fr.glowstoner.sylvanus.commands;

public class StopCommand extends Command {

	public StopCommand() {
		super("stop");
	}

	@Override
	public void execute(String command, String[] args) {
		super.getInstance().stopInit();
	}

	@Override
	public String description() {
		return "Permet d'arrêter l'éxecution du programme.";
	}
}
