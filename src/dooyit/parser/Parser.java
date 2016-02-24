package dooyit.parser;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;
import dooyit.main.Main;
import dooyit.main.ShowParser;

public class Parser {

	static final String COMMAND_EXIT = "exit";
	static final String COMMAND_CLEAR = "clear";
	static final String COMMAND_SHOW = "show";
	static final String COMMAND_DELETE = "delete";
	static final String COMMAND_EDIT = "edit";
	static final String COMMAND_ADD = "add";
	static final String COMMAND_ADD_CAT = "addcat";
	static final String COMMAND_SKIN = "skin";
	
	
	public Parser(){
		
	}
	
	
	public Command getCommand(String input){
		input = input.trim();

		if (input == "") {
			Main.showToUser(Main.MESAGE_EMPTY_COMMAND);
			return null;
		}

		String[] splittedInput = input.split("\\s+", 2);
		String commandString = splittedInput[0];

		String data = "";
		if (splittedInput.length > 1) {
			data = splittedInput[1];
		}
		
		Command command = null;

		switch (commandString.toLowerCase()) {
		case COMMAND_ADD:
			AddParser addParser = new AddParser(input);
			command = addParser.getCommand();
			break;
			
		case COMMAND_SHOW:
			ShowParser showParser = new ShowParser(input);
			command = showParser.getCommand();
			break;
			
		case COMMAND_EDIT:
			EditParser editParser = new EditParser(input);
			command = editParser.getCommand();
			break;
			
		case COMMAND_DELETE:
			DeleteParser deleteParser = new DeleteParser(input);
			command = deleteParser.getCommand();
			break;
		
		case COMMAND_ADD_CAT:
			AddCatParser addCatParser = new AddCatParser(input);
			command = addCatParser.getCommand();
			break;
			
		case COMMAND_SKIN :
			String colour = parseSkinChange(input);
			//command = CommandUtils.createSkinChangeCommand();
			break;
			
		case COMMAND_EXIT:
			command = CommandUtils.createExitCommand();
			break;

		default:
			command = CommandUtils.createInvalidCommand("Invalid Command: " + input);
		}

		return command;
	}


	private String parseSkinChange(String input) {
		return input.split("//s+")[1];
	}
}


