package fr.glowstoner.sylvanus.commands;

import java.util.Arrays;

public class TestCommand extends Command {

	public TestCommand() {
		super("test");
	}

	@Override
	public void execute(String command, String[] args) {
		System.out.println("len args -> "+args.length);
		System.out.println("args -> "+Arrays.asList(args).toString());
		System.out.println("command -> "+command);
	}

	@Override
	public String description() {
		return "Une commande de test !";
	}
}
