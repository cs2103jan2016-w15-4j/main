//@@author A0133338J
package dooyit.parser;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

/**
 * The ParserController class takes in the userInput and returns 
 * a Command object. For straightforward commands like help and exit,
 * ParserController will directly parse and return the correct Command
 * objects. For more complicated cases, ParserController will call the 
 * getCommand methods of the relevant parser objects. 
 * 
 * @author Annabel
 *
 */
public class ParserController {
	// Error message
	private static final String ERROR_MESSAGE_INVALID_COMMAND = "Invalid Command: ";
	
	// Types of commands
	public static final String COMMAND_ADD = "add";
	public static final String COMMAND_ADD_CAT = "addcat";
	public static final String COMMAND_CLEAR = "clear";
	public static final String COMMAND_DELETE = "delete";
	public static final String COMMAND_DELETE_CAT = "deletecat";
	public static final String COMMAND_EDIT = "edit";
	public static final String COMMAND_EDIT_CAT = "editcat";
	public static final String COMMAND_EXIT = "exit";
	public static final String COMMAND_FLOAT = "float";
	public static final String COMMAND_HELP = "help";
	public static final String COMMAND_MARK = "mark";
	public static final String COMMAND_MOVE_TO_CAT = "move";
	public static final String COMMAND_REDO = "redo";
	public static final String COMMAND_SEARCH = "search";
	public static final String COMMAND_SHOW = "show";
	public static final String COMMAND_SHOW_CATEGORY = "showcat";
	public static final String COMMAND_SKIN = "skin";
	public static final String COMMAND_STORAGE = "storage"; 
	public static final String COMMAND_UNDO = "undo";
	public static final String COMMAND_UNMARK = "unmark";
	public static final String COMMAND_UNMOVE = "unmove";
	
	// String arrays of Command Aliases
	public static final String[] exitCommandAlias = new String[]{"quit", "close", COMMAND_EXIT};
	public static final String[] deleteCommandAlias = new String[]{"rm", "remove", COMMAND_DELETE};
	public static final String[] floatCommandAlias = new String[]{"edittofloat", COMMAND_FLOAT};

	// Parser object attributes
	private AddParser addParser;
	private ShowParser showParser;
	private EditParser editParser;
	private AddCategoryParser addCatParser;
	private DeleteParser deleteParser;
	private MarkParser markParser;
	private UnmarkParser unmarkParser;
	private DeleteCategoryParser deleteCatParser;
	private EditCategoryParser editCategoryParser;
	private MoveParser moveParser;
	private FloatParser floatParser;
	private SearchParser searchParser;
	private UnmoveParser unmoveParser;

	/** Initializes a ParserController object */
	public ParserController() {
		addParser = new AddParser();
		showParser = new ShowParser();
		editParser = new EditParser();
		addCatParser = new AddCategoryParser();
		deleteParser = new DeleteParser();
		deleteCatParser = new DeleteCategoryParser();
		markParser = new MarkParser();
		unmarkParser = new UnmarkParser();
		editCategoryParser = new EditCategoryParser();
		moveParser = new MoveParser();
		floatParser = new FloatParser();
		searchParser = new SearchParser();
		unmoveParser = new UnmoveParser();
	}

	/**
	 * Parses the input string and returns the correct Command object.
	 * 
	 * @param input
	 * 		  The input from the user.
	 * 
	 * @return the correct command object.
	 */
	public Command getCommand(String input) {
		String commandString = getCommandString(input);
		String commandInput = getInputWithoutCommand(input, commandString);
		Command command = getCommand(input, commandString, commandInput);
		return command;
	}
	
	/**
	 * Extracts the command like "add", "move", "remove" etc
	 * from the input string.
	 * 
	 * @param input
	 * 		  The user input.
	 * 
	 * @return the one word command string.
	 */
	private String getCommandString(String input) {
		input = input.trim();
		
		//Splits the userInput into a 2-element String array
		String[] splittedInput = input.split("\\s+", 2);
		return splittedInput[0].toLowerCase();
	}

	/**
	 * Checks the command type and call the getCommand method of the 
	 * relevant parser objects (if needed) to get the correct command.
	 * 
	 * @param input
	 * 		  The user input
	 * 
	 * @param commandString
	 * 		  The command string like "add", "editcat" of the user input
	 * 
	 * @param commandInput
	 * 		  The userInput with the commandString removed.
	 * 
	 * @return the correct command object.
	 */
	private Command getCommand(String input, String commandString, String commandInput) {
		Command command;
		switch (getCommandType(commandString)) {
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
			command = editCategoryParser.getCommand(commandInput);
			break;

		case COMMAND_EXIT :
			command = CommandUtils.createExitCommand();
			break;
			
		case COMMAND_FLOAT :
			command = floatParser.getCommand(commandInput);
			break;
			
		case COMMAND_HELP :
			command = CommandUtils.createHelpCommand();
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

	/**
	 * Removes the commandString like "showcat" from the user input.
	 * 
	 * @param input
	 * 		  The user input
	 * 
	 * @param command
	 * 		  The commandString like "addcat" etc.
	 * 
	 * @return the user input without the command string.
	 */
	private String getInputWithoutCommand(String input, String command) {
		return input.substring(command.length()).trim();
	}

	/**
	 * Checks if the commandString is a recognized alias. 
	 * 
	 * @param commandString
	 * 		  String indicating the type of command
	 * 
	 * @param aliasArray
	 * 		  String array of aliases
	 * 
	 * @return true if the commandString is an alias in the array
	 * 		   and false if the commandString isn't in the array.
	 */
	public static boolean isAliasOf(String commandString, String[] aliasArray) {
		boolean ans = false;
		for(int i = 0; i < aliasArray.length; i++) {
			if(commandString.equals(aliasArray[i])) {
				ans = true;
			}
		}
		return ans;
	}
	
	/**
	 * Checks if the commandString is a recognized alias. Changes
	 * the commandString to a generic commandString if it is a 
	 * recognized alias. If the commandString isn't a recognized
	 * alias, this method will return the original string.
	 * 
	 * @param commandString
	 * 		  The String that indicates the type of user input
	 * 
	 * @return the generic commandString
	 */
	public static String getCommandType(String commandString) {
		String type;
		if (isAliasOf(commandString, exitCommandAlias)) {
			type = COMMAND_EXIT;
		} else if (isAliasOf(commandString, deleteCommandAlias)) {
			type = COMMAND_DELETE;
		} else if (isAliasOf(commandString, floatCommandAlias)) {
			type = COMMAND_FLOAT;
		} else {
			type = commandString;
		}
		return type;
	}

}
