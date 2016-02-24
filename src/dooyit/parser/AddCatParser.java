package dooyit.parser;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class AddCatParser {
	
	private static final int START_INDEX_ARGS = 7;
	private static final String MARKER = " by ";
	
	private static String userInput;
	private static String catName;
	private static String colour;
	private static Command cmd;
	
	public AddCatParser(String input) {
		//userInput ignore the word "addcat"
		userInput = input.trim().toLowerCase();
	}
	
	public Command getCommand() {
		parse();
		setCmd();
		return cmd;
	}

	private void setCmd() {
		//cmd = CommandUtils.createAddCatCommand(catName, colour);
	}

	private void parse() {
		int lastOccurrenceOfBy = userInput.lastIndexOf(MARKER);
		catName = userInput.substring(0, lastOccurrenceOfBy).trim();
		colour = userInput.substring(lastOccurrenceOfBy).replace(MARKER, "");
	}
}
