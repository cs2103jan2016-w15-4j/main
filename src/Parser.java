
public class Parser {

	
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
		case Main.COMMAND_ADD:
			//executeAddCommand(data);
			AddParser addParser = new AddParser(input);
			command = addParser.getCommand();
			//command = CommandUtils.createAddCommand(data);
			break;
			
		case Main.COMMAND_SHOW:
			ShowParser showParser = new ShowParser(input);
			command = showParser.getCommand();
			
		case Main.COMMAND_EDIT:
			EditParser editParser = new EditParser(input);
			command = editParser.getCommand();

		case Main.COMMAND_DELETE:
			DeleteParser deleteParser = new DeleteParser(input);
			command = deleteParser.getCommand();
			//command = CommandUtils.createDeleteCommand(data);
			break;

		case Main.COMMAND_EXIT:
			command = CommandUtils.createExitCommand();
			break;

		default:
			//handlesInvalidCommand(command);
		}
		
		return command;
	}
}


