package fr.glowstoner.sylvanus.commands;

public class SetKeyCommand extends Command{

	public SetKeyCommand() {
		super("setkey");
	}

	@Override
	public void execute(String command, String[] args) {
		if(args.length == 0) {
			System.out.println("Usage /setkey <clé>");
		}else if(args.length == 1) {
			super.getInstance().getClient().setConnectionKey(args[0]);
			
			System.out.println("La clé a été bien définie pour la connection distante.");
		}else {
			System.out.println("Usage /setkey <clé>");
		}
	}

	@Override
	public String description() {
		return "Cette commande permet de définir la clé de la connection distante.";
	}

}
