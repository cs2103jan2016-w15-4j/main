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
	static final String COMMAND_STORAGE = "storage";
	
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

		//String data = "";
		//if (splittedInput.length > 1) {
		//	data = splittedInput[1];
		//}
		
		Command command = null;

		switch (commandString.toLowerCase()) {
		case COMMAND_ADD:
			AddParser addParser = new AddParser(input.replace(COMMAND_ADD, ""));
			command = addParser.getCommand();
			break;
			
		case COMMAND_SHOW:
			ShowParser showParser = new ShowParser(input.replace(COMMAND_SHOW, ""));
			command = showParser.getCommand();
			break;
			
		case COMMAND_EDIT:
			EditParser editParser = new EditParser(input.replace(COMMAND_EDIT, ""));
			command = editParser.getCommand();
			break;
			
		case COMMAND_DELETE:
			DeleteParser deleteParser = new DeleteParser(input.replace(COMMAND_DELETE, ""));
			command = deleteParser.getCommand();
			break;
		
		case COMMAND_ADD_CAT:
			AddCatParser addCatParser = new AddCatParser(input.replace(COMMAND_ADD_CAT, ""));
			command = addCatParser.getCommand();
			break;
			
		case COMMAND_SKIN :
			String colour = parseSkinChange(input);
			//command = CommandUtils.createSkinChangeCommand(colour);
			break;
		
		case COMMAND_STORAGE :
			String filePath = parseStorage(input);
			//command = CommandUtils.createStorageCommand(colour);
			break;
			
			
		case COMMAND_EXIT:
			command = CommandUtils.createExitCommand();
			break;

		default:
			command = CommandUtils.createInvalidCommand("Invalid Command: " + input);
		}

		return command;
	}


	private String parseStorage(String input) {
		return input.split("//s+")[1].trim();
	}


	private String parseSkinChange(String input) {
		return input.split("//s+")[1].trim();
	}
}


