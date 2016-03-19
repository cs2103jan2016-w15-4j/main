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
	private static final String COMMAND_DELETE_CAT = "deletecat";
	private static final String COMMAND_EDIT_CAT = "editcat";
	private static final String COMMAND_MOVE_TO_CAT = "move";
	private static final String COMMAND_REMOVE_FROM_CAT = "rm";
	private static AddParser addParser;
	private static ShowParser showParser;
	private static EditParser editParser;
	private static AddCategoryParser addCatParser;
	private static DeleteParser deleteParser;
	private static MarkParser markParser;
	private static UnmarkParser unmarkParser;
	private static DeleteCategoryParser deleteCatParser;
	private static EditCategoryParser editCatParser;
	private static MoveParser moveParser;
	private static RemoveParser removeParser;

	public Parser() {
		addParser = new AddParser();
		showParser = new ShowParser();
		editParser = new EditParser();
		addCatParser = new AddCategoryParser();
		deleteParser = new DeleteParser();
		markParser = new MarkParser();
		unmarkParser = new UnmarkParser();
		editCatParser = new EditCategoryParser();
		moveParser = new MoveParser();
		removeParser = new RemoveParser();
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
			
		case COMMAND_DELETE_CAT:
			command = deleteCatParser.getCommand(getInputWithoutCommand(input, COMMAND_DELETE_CAT));
			break;
			
		case COMMAND_EDIT_CAT:
			command = editCatParser.getCommand(getInputWithoutCommand(input, COMMAND_EDIT_CAT));
			break;

		case COMMAND_SKIN:
			String colour = getInputWithoutCommand(input, COMMAND_SKIN);
			command = CommandUtils.createChangeThemeCommand(colour);
			break;

		case COMMAND_STORAGE:
			String filePath = getInputWithoutCommand(input, COMMAND_STORAGE);
			command = CommandUtils.createStorageCommand(filePath);
			break;

		case COMMAND_UNMARK:
			command = unmarkParser.getCommand(getInputWithoutCommand(input, COMMAND_MARK));
			break;
		
		case COMMAND_MOVE_TO_CAT:
			System.out.println("reached move to cat case");
			command = moveParser.getCommand(getInputWithoutCommand(input, COMMAND_MOVE_TO_CAT));
			break;
			
		case COMMAND_REMOVE_FROM_CAT:
			command = removeParser.getCommand(getInputWithoutCommand(input, COMMAND_REMOVE_FROM_CAT));
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
		return input.substring(command.length()).trim();
	}
}
