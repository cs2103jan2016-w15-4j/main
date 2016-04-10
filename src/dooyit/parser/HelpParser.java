
package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.common.utils.CommandUtils;
import dooyit.logic.commands.Command;

public class HelpParser implements ParserCommons {
	private static final String ERROR_MESSAGE_INVALID_HELP_COMMAND = "Invalid Help Command: ";
	Command command;
	
	public HelpParser() {
	}

	public Command getCommand(String input) throws IncorrectInputException {
		resetFields();
		
		switch(getHelpCommandType(input)) {
		case COMMAND_ADD :
			//command = CommandUtils.createHelpCommand(COMMAND_ADD);
			break;
			
		case COMMAND_ADD_CAT :
			//command = CommandUtils.createHelpCommand(COMMAND_ADD_CAT);
			break;
			
		case COMMAND_CLEAR :
			//command = CommandUtils.createHelpCommand(COMMAND_CLEAR);
			break;

		case COMMAND_DELETE :
			//command = CommandUtils.createHelpCommand(COMMAND_DELETE);
			break;
			
		case COMMAND_DELETE_CAT :
			//command = CommandUtils.createHelpCommand(COMMAND_DELETE_CAT);
			break;
			
		case COMMAND_EDIT :
			//command = CommandUtils.createHelpCommand(COMMAND_EDIT);
			break;
			
		case COMMAND_EDIT_CAT :
			//command = CommandUtils.createHelpCommand(COMMAND_EDIT_CAT);
			break;

		case COMMAND_EXIT :
			//command = CommandUtils.createHelpCommand(COMMAND_EDIT_CAT);
			break;
			
		case COMMAND_FLOAT :
			//command = CommandUtils.createHelpCommand(COMMAND_FLOAT);
			break;
			
		case COMMAND_HELP :
			command = CommandUtils.createHelpCommand();
			break;
			
		case COMMAND_MARK :
			//command = CommandUtils.createHelpCommand(COMMAND_MARK);
			break;
			
		case COMMAND_MOVE_TO_CAT :
			//command = CommandUtils.createHelpCommand(COMMAND_MOVE_TO_CAT);
			break;
			
		case COMMAND_REDO :
			//command = CommandUtils.createHelpCommand(COMMAND_REDO);
			break;
			
		case COMMAND_SEARCH :
			//command = CommandUtils.createHelpCommand(COMMAND_SEARCH);
			break;
			
		case COMMAND_SHOW :
			//command = CommandUtils.createHelpCommand(COMMAND_SHOW);
			break; 
			
		case COMMAND_SHOW_CATEGORY :
			//command = CommandUtils.createHelpCommand(COMMAND_SHOW_CATEGORY);

		case COMMAND_SKIN :
			//command = CommandUtils.createHelpCommand(COMMAND_SKIN);
			break;

		case COMMAND_STORAGE :
			//command = CommandUtils.createHelpCommand(COMMAND_STORAGE);
			break;
			
		case COMMAND_UNDO :
			//command = CommandUtils.createHelpCommand(COMMAND_UNDO);
			break;

		case COMMAND_UNMARK : 
			//command = CommandUtils.createHelpCommand(COMMAND_UNMARK);
			break;

		default :
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_HELP_COMMAND + input);
		}
		
		return command;
	}

	private String getHelpCommandType(String input) {
		String type;
		if (input.equals(EMPTY_STRING)) {
			type = COMMAND_HELP;
		} else {
			type = ParserCommons.getCommandType(input);
		}
		return type;
	}

	private void resetFields() {
		command = null;
	}

}
