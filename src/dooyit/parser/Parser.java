package dooyit.parser;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;
import dooyit.ui.Main;

public class Parser {

	static final String COMMAND_EXIT = "exit";
	static final String COMMAND_CLEAR = "clear";
	static final String COMMAND_SHOW = "show";
	static final String COMMAND_DELETE = "delete";
	static final String COMMAND_MARK = "mark";
	static final String COMMAND_EDIT = "edit";
	static final String COMMAND_ADD = "add";
	static final String COMMAND_ADD_CAT = "addcat";
	static final String COMMAND_SKIN = "skin";
	static final String COMMAND_STORAGE = "storage";
	static final String COMMAND_UNMARK = "unmark";
	private static AddParser addParser;
	private static ShowParser showParser;
	private static EditParser editParser;
	private static AddCatParser addCatParser;
	private static DeleteParser deleteParser;
	private static MarkParser markParser;
	private static UnmarkParser unmarkParser;

	public Parser() {
		addParser = new AddParser();
		showParser = new ShowParser();
		editParser = new EditParser();
		addCatParser = new AddCatParser();
		deleteParser = new DeleteParser();
		markParser = new MarkParser();
		unmarkParser = new UnmarkParser();
	}

	public Command getCommand(String input) {
		input = input.trim();

		if (input == "") {
			Main.showToUser(Main.MESAGE_EMPTY_COMMAND);
			return null;
		}

		String[] splittedInput = input.split("\\s+", 2);
		String commandString = splittedInput[0].toLowerCase();

		Command command = null;
		// assert false;
		switch (commandString) {
		case COMMAND_ADD:
			command = addParser.getCommand(getInputWithoutCommand(input, COMMAND_ADD));
			break;

		case COMMAND_SHOW:
			command = showParser.getCommand(getInputWithoutCommand(input, COMMAND_SHOW));
			break;

		case COMMAND_EDIT:
			command = editParser.getCommand(getInputWithoutCommand(input, COMMAND_EDIT));
			break;

		case COMMAND_DELETE:
			command = deleteParser.getCommand(getInputWithoutCommand(input, COMMAND_DELETE));
			break;

		case COMMAND_MARK:
			command = markParser.getCommand(getInputWithoutCommand(input, COMMAND_MARK));
			break;

		case COMMAND_ADD_CAT:
			command = addCatParser.getCommand(getInputWithoutCommand(input, COMMAND_ADD_CAT));
			break;

		case COMMAND_SKIN:
			String colour = getInputWithoutCommand(input, COMMAND_SKIN);
			// command = CommandUtils.createSkinChangeCommand(colour);
			break;

		case COMMAND_STORAGE:
			String filePath = getInputWithoutCommand(input, COMMAND_STORAGE);
			// command = CommandUtils.createStorageCommand(filePath);
			break;

		case COMMAND_UNMARK:
			// command = unmarkParser.getCommand(getInputWithoutCommand(input, COMMAND_MARK));
			break;

		case COMMAND_EXIT:
			command = CommandUtils.createExitCommand();
			break;

		default:
			command = CommandUtils.createInvalidCommand("Invalid Command: " + input);
		}

		return command;
	}

	private String getInputWithoutCommand(String input, String command) {
		String temp = input.substring(command.length()).trim();
		System.out.println("temp is " + temp);
		return temp;
	}
}
