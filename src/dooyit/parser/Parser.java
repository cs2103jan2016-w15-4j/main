//@@author A0133338J
package dooyit.parser;

import dooyit.common.CommandUtils;
import dooyit.logic.commands.Command;

public class Parser implements ParserCommons {
	private static final String ERROR_MESSAGE_INVALID_COMMAND = "Invalid Command: ";

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
	private FloatParser floatParser;
	private HelpParser helpParser;
	private SearchParser searchParser;
	private UnmoveParser unmoveParser;

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
		floatParser = new FloatParser();
		helpParser = new HelpParser();
		searchParser = new SearchParser();
		unmoveParser = new UnmoveParser();
	}

	public Command getCommand(String input) {
		input = input.trim();

		String[] splittedInput = input.split("\\s+", 2);
		String commandString = splittedInput[0].toLowerCase();
		String commandInput = getInputWithoutCommand(input, commandString);

		Command command = null; 
		switch (ParserCommons.getCommandType(commandString)) {
		case COMMAND_ADD :
			command = addParser.getCommand(commandInput);
			break;
			
		case COMMAND_ADD_CAT :
			command = addCatParser.getCommand(commandInput);
			break;
			
		case COMMAND_CLEAR :
			command = CommandUtils.createClearCommand();
			break;

		case COMMAND_DELETE :
			command = deleteParser.getCommand(commandInput);
			break;
			
		case COMMAND_DELETE_CAT :
			command = deleteCatParser.getCommand(commandInput);
			break;
			
		case COMMAND_EDIT :
			command = editParser.getCommand(commandInput);
			break;
			
		case COMMAND_EDIT_CAT :
			command = editCatParser.getCommand(commandInput);
			break;

		case COMMAND_EXIT :
			command = CommandUtils.createExitCommand();
			break;
			
		case COMMAND_FLOAT :
			command = floatParser.getCommand(commandInput);
			break;
			
		case COMMAND_HELP :
			command = helpParser.getCommand(commandInput);
			break;
			
		case COMMAND_MARK :
			command = markParser.getCommand(commandInput);
			break;
			
		case COMMAND_MOVE_TO_CAT :
			command = moveParser.getCommand(commandInput);
			break;
			
		case COMMAND_REDO :
			command = CommandUtils.createRedoCommand();
			break;
			
		case COMMAND_SEARCH :
			command = searchParser.getCommand(commandInput);
			break;
			
		case COMMAND_SHOW :
			command = showParser.getCommand(commandInput);
			break; 
			
		case COMMAND_SHOW_CATEGORY :
			command = CommandUtils.createShowCategoryCommand(commandInput);
			break;

		case COMMAND_SKIN :
			command = CommandUtils.createChangeThemeCommand(commandInput);
			break;

		case COMMAND_STORAGE :
			command = CommandUtils.createStorageCommand(commandInput);
			break;
			
		case COMMAND_UNDO :
			command = CommandUtils.createUndoCommand();
			break;

		case COMMAND_UNMARK : 
			command = unmarkParser.getCommand(commandInput);
			break;
			
		case COMMAND_UNMOVE : 
			command = unmoveParser.getCommand(commandInput);
			break;

		default :
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_COMMAND + input);
		}

		return command;
	}

	private String getInputWithoutCommand(String input, String command) {
		return input.substring(command.length()).trim();
	}
}
