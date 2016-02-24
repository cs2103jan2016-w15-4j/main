package dooyit.parser;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;
import dooyit.main.Main;

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

		Command command = null;

		switch (commandString.toLowerCase()) {
		case COMMAND_ADD:
			AddParser addParser = new AddParser(getInputWithoutCommand(input, COMMAND_ADD));
			command = addParser.getCommand();
			break;
			
		case COMMAND_SHOW:
			ShowParser showParser = new ShowParser(getInputWithoutCommand(input, COMMAND_SHOW));
			command = showParser.getCommand();
			break;
			
		case COMMAND_EDIT:
			EditParser editParser = new EditParser(getInputWithoutCommand(input, COMMAND_EDIT));
			command = editParser.getCommand();
			break;
			
		case COMMAND_DELETE:
			DeleteParser deleteParser = new DeleteParser(getInputWithoutCommand(input, COMMAND_DELETE));
			command = deleteParser.getCommand();
			break;
		
		case COMMAND_MARK :
			DeleteParser markParser = new DeleteParser(getInputWithoutCommand(input, COMMAND_MARK));
			command = markParser.getCommand();
			break;
		
		case COMMAND_ADD_CAT:
			AddCatParser addCatParser = new AddCatParser(getInputWithoutCommand(input, COMMAND_ADD_CAT));
			command = addCatParser.getCommand();
			break;
			
		case COMMAND_SKIN :
			String colour = getInputWithoutCommand(input, COMMAND_SKIN);
			//command = CommandUtils.createSkinChangeCommand(colour);
			break;
		
		case COMMAND_STORAGE :
			String filePath = getInputWithoutCommand(input, COMMAND_STORAGE);
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


	private String getInputWithoutCommand(String input, String command) {
		return input.replace(command, "").trim();
	}
}


