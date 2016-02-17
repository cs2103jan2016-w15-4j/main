package dooyit.main;

public class Parser {

	static final String COMMAND_EXIT = "exit";
	static final String COMMAND_CLEAR = "clear";
	static final String COMMAND_SHOW = "show";
	static final String COMMAND_DELETE = "delete";
	static final String COMMAND_EDIT = "edit";
	static final String COMMAND_ADD = "add";
	
	
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
			//executeAddCommand(data);
			AddParser addParser = new AddParser(input);
			command = addParser.getCommand();
			//command = CommandUtils.createAddCommand(data);
			break;
			
		case COMMAND_SHOW:
			ShowParser showParser = new ShowParser(input);
			command = showParser.getCommand();
			
		case COMMAND_EDIT:
			EditParser editParser = new EditParser(input);
			command = editParser.getCommand();

		case COMMAND_DELETE:
			DeleteParser deleteParser = new DeleteParser(input);
			command = deleteParser.getCommand();
			//command = CommandUtils.createDeleteCommand(data);
			break;

		case COMMAND_EXIT:
			command = CommandUtils.createExitCommand();
			break;

		default:
			//handlesInvalidCommand(command);
			command = CommandUtils.createInvalidCommand("Invalid Command: " + input);
		}

		return command;
	}
}


