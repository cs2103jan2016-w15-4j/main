package dooyit.parser;

import dooyit.logic.commands.CommandUtils;

public class DeleteParser {
	private static String userInput;
	
	public DeleteParser(String input) {
		userInput = input.split(" ")[1];
	}

	public Command getCommand() {
		int taskId = Integer.parseInt(userInput);
		return CommandUtils.createDeleteCommand(taskId);
	}
}