//@@author A0133338J
package dooyit.parser;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class DeleteCategoryParser {
	public DeleteCategoryParser() {
		
	}
	
	public Command getCommand(String input) {
		return CommandUtils.createDeleteCategoryCommand(input);
	}
}
