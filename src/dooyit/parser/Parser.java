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
			
		case COMMAND_ADD_CAT:
			command = addCatParser.getCommand(getInputWithoutCommand(input, COMMAND_ADD_CAT));
			break;
			
		case COMMAND_CLEAR:
			command = CommandUtils.createClearCommand();
			break;
			
		case COMMAND_CLOSE:
			command = CommandUtils.createExitCommand();
			break;

		case COMMAND_DELETE:
			command = deleteParser.getCommand(getInputWithoutCommand(input, COMMAND_DELETE));
			break;
			
		case COMMAND_DELETE_CAT:
			command = deleteCatParser.getCommand(getInputWithoutCommand(input, COMMAND_DELETE_CAT));
			break;
			
		case COMMAND_EDIT:
			command = editParser.getCommand(getInputWithoutCommand(input, COMMAND_EDIT));
			break;
			
		case COMMAND_EDIT_CAT:
			command = editCatParser.getCommand(getInputWithoutCommand(input, COMMAND_EDIT_CAT));
			break;

		case COMMAND_EXIT:
			command = CommandUtils.createExitCommand();
			break;
			
		case COMMAND_HELP:
			command = CommandUtils.createHelpCommand();
			break;
			
		case COMMAND_MARK:
			command = markParser.getCommand(getInputWithoutCommand(input, COMMAND_MARK));
			break;
			
		case COMMAND_MOVE_TO_CAT:
			command = moveParser.getCommand(getInputWithoutCommand(input, COMMAND_MOVE_TO_CAT));
			break;
			
		case COMMAND_REMOVE_FROM_CAT:
			command = removeParser.getCommand(getInputWithoutCommand(input, COMMAND_REMOVE_FROM_CAT));
			break;
			
		case COMMAND_SEARCH:
			command = CommandUtils.createSearchCommand(getInputWithoutCommand(input, COMMAND_SEARCH));
			break;
			
		case COMMAND_SHOW:
			command = showParser.getCommand(getInputWithoutCommand(input, COMMAND_SHOW));
			break; 

		case COMMAND_SKIN:
			String colour = getInputWithoutCommand(input, COMMAND_SKIN);
			command = CommandUtils.createChangeThemeCommand(colour);
			break;

		case COMMAND_STORAGE:
			String filePath = getInputWithoutCommand(input, COMMAND_STORAGE);
			command = CommandUtils.createStorageCommand(filePath);
			break;
			
		case COMMAND_UNDO:
			command = CommandUtils.createUndoCommand();
			break;

		case COMMAND_UNMARK: 
			command = unmarkParser.getCommand(getInputWithoutCommand(input, COMMAND_UNMARK));
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
