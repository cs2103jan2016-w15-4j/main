//@@author A0133338J
package dooyit.parser;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class DeleteCategoryParser implements ParserCommons {
	private static final String ERROR_MESSAGE_INVALID_DELETE_CATEGORY_COMMAND = "Invalid Delete Category Command!";

	public DeleteCategoryParser() {
		
	}
	
	public Command getCommand(String input) {
		Command command = null;
		
		if (input.equals(EMPTY_STRING)) {
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_DELETE_CATEGORY_COMMAND);
		} else {
			command = CommandUtils.createDeleteCategoryCommand(input);
		}
		return command;
	}
}
