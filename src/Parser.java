
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
			command = CommandUtils.createAddCommand(data);
			break;

		case Main.COMMAND_DELETE:
			command = CommandUtils.createDeleteCommand(data);
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
