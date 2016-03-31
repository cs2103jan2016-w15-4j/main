package dooyit.parser;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;
import dooyit.ui.Main;

public class Parser {
	public static final String ERROR_MESSAGE_INVALID_COMMAND = "Invalid Command: ";
	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_ADD_CAT = "addcat";
	private static final String COMMAND_CLEAR = "clear";
	private static final String COMMAND_CLOSE = "close";
	private static final String COMMAND_DELETE = "delete";
	private static final String COMMAND_DELETE_CAT = "deletecat";
	private static final String COMMAND_EDIT = "edit";
	private static final String COMMAND_EDIT_CAT = "editcat";
	private static final String COMMAND_EXIT = "exit";
	private static final String COMMAND_HELP = "help";
	private static final String COMMAND_MARK = "mark";
	private static final String COMMAND_MOVE_TO_CAT = "move";
	private static final String COMMAND_REMOVE_FROM_CAT = "rm";
	private static final String COMMAND_SEARCH = "search";
	private static final String COMMAND_SHOW = "show";
	private static final String COMMAND_SKIN = "skin";
	private static final String COMMAND_STORAGE = "storage"; 
	private static final String COMMAND_UNDO = "undo";
	private static final String COMMAND_UNMARK = "unmark";

	private AddParser addParser;
	private ShowParser showParser;
	private EditParser editParser;
	private AddCategoryParser addCatParser;
	private DeleteParser deleteParser;
	private MarkParser markParser;
	private UnmarkParser unmarkParser;
	private DeleteCategoryParser deleteCatParser;
	private EditCategoryParser editCatParser;
	private MoveParser moveParser;
	private RemoveParser removeParser;

	public Parser() {
		addParser = new AddParser();
		showParser = new ShowParser();
		editParser = new EditParser();
		addCatParser = new AddCategoryParser();
		deleteParser = new DeleteParser();
		deleteCatParser = new DeleteCategoryParser();
		markParser = new MarkParser();
		unmarkParser = new UnmarkParser();
		editCatParser = new EditCategoryParser();
		moveParser = new MoveParser();
		removeParser = new RemoveParser();
	}

	public Command getCommand(String input) {
		input = input.trim();

		String[] splittedInput = input.split("\\s+", 2);
		String commandString = splittedInput[0].toLowerCase();
		String commandInput = getInputWithoutCommand(input, commandString);

		Command command = null; 
		switch (commandString) {
		case COMMAND_ADD:
			command = addParser.getCommand(commandInput);
			break;
			
		case COMMAND_ADD_CAT:
			command = addCatParser.getCommand(commandInput);
			break;
			
		case COMMAND_CLEAR:
			command = CommandUtils.createClearCommand();
			break;
			
		case COMMAND_CLOSE:
			command = CommandUtils.createExitCommand();
			break;

		case COMMAND_DELETE:
			command = deleteParser.getCommand(commandInput);
			break;
			
		case COMMAND_DELETE_CAT:
			command = deleteCatParser.getCommand(commandInput);
			break;
			
		case COMMAND_EDIT:
			command = editParser.getCommand(commandInput);
			break;
			
		case COMMAND_EDIT_CAT:
			command = editCatParser.getCommand(commandInput);
			break;

		case COMMAND_EXIT:
			command = CommandUtils.createExitCommand();
			break;
			
		case COMMAND_HELP:
			command = CommandUtils.createHelpCommand();
			break;
			
		case COMMAND_MARK:
			command = markParser.getCommand(commandInput);
			break;
			
		case COMMAND_MOVE_TO_CAT:
			command = moveParser.getCommand(commandInput);
			break;
			
		case COMMAND_REMOVE_FROM_CAT:
			command = removeParser.getCommand(commandInput);
			break;
			
		case COMMAND_SEARCH:
			command = CommandUtils.createSearchCommand(commandInput);
			break;
			
		case COMMAND_SHOW:
			command = showParser.getCommand(commandInput);
			break; 

		case COMMAND_SKIN:
			command = CommandUtils.createChangeThemeCommand(commandInput);
			break;

		case COMMAND_STORAGE:
			command = CommandUtils.createStorageCommand(commandInput);
			break;
			
		case COMMAND_UNDO:
			command = CommandUtils.createUndoCommand();
			break;

		case COMMAND_UNMARK: 
			command = unmarkParser.getCommand(commandInput);
			break;

		default:
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_COMMAND + input);
		}

		return command;
	}

	private String getInputWithoutCommand(String input, String command) {
		return input.substring(command.length()).trim();
	}
}
