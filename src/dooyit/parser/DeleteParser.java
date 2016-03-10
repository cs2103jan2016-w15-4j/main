package dooyit.parser;

import java.util.ArrayList;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class DeleteParser {
	private static final int INDEX_SINGLE = 0;
	private static String userInput;
	private static String[] splitInput;
	private static ArrayList<Integer> taskIdsForDeletion;
	private static int taskIdForDeletion;
	private static Command cmd;
	
	enum DELETE_TYPE {
		SINGLE, MULTIPLE, INTERVAL, INVALID
	};
	
	public DeleteParser(String input) {
		userInput = input;
		splitInput = userInput.split("\\s+");
		taskIdsForDeletion = new ArrayList<Integer>();
		cmd = null;
	}

	public Command getCommand() {
		switch(getDeleteType()) {
		case SINGLE :
			try {
				parseSingleType();
			} catch(IncorrectInputException e) {
				cmd = getInvalidCommand(e.getMessage());
				break;
			}
			cmd = getSingleTypeDeleteCmd();
			break;
			
		case MULTIPLE :
			try {
				parseMultipleType();
			} catch(IncorrectInputException e) {
				cmd = getInvalidCommand(e.getMessage());
				break;
			}
			cmd = getMultipleTypeDeleteCmd();
			break;
			
		case INTERVAL :
			try {
				parseIntervalType();
			} catch(IncorrectInputException e) {
				cmd = getInvalidCommand(e.getMessage());
				break;
			}
			cmd = getIntervalTypeDeleteCmd();
			break;
		
		case INVALID :
			cmd = getInvalidCmd();
			break;
		}
		return cmd;
	}
	
	private Command getIntervalTypeDeleteCmd() {
		return CommandUtils.createDeleteCommand(taskIdsForDeletion);
	}

	private void parseIntervalType() {
		for(int i = INDEX_SINGLE; i < splitInput.length; i++) {
			if(splitInput[i].equals("-")) {
				if(!isNumber(splitInput[i - 1]) || !isNumber(splitInput[i + 1])) {
					throw new IncorrectInputException("Invalid Number!");
				} else {
					setInterval(splitInput, i);
				}
			}
		}
	}

	private void setInterval(String[] arr, int index) {
		int start = Integer.parseInt(arr[index - 1]);
		int end = Integer.parseInt(arr[index + 1]);
		for(int i = start; i <= end; i++) {
			taskIdsForDeletion.add(i);
		}
	}

	//Eg. delete 5 6 8
	private Command getMultipleTypeDeleteCmd() {
		return CommandUtils.createDeleteCommand(taskIdsForDeletion);
	}

	private void parseMultipleType() {
		for(int i = INDEX_SINGLE; i < splitInput.length; i++) {
			String currWord = splitInput[i];
			if(!isNumber(currWord)) {
				throw new IncorrectInputException("Invalid Number!");
			} else {
				taskIdsForDeletion.add(Integer.parseInt(currWord));
			}
		}
	}

	private Command getSingleTypeDeleteCmd() {
		return CommandUtils.createDeleteCommand(taskIdForDeletion);
	}

	private Command getInvalidCmd() {
		return CommandUtils.createInvalidCommand("Invalid Delete Command!");
	}

	private Command getInvalidCommand(String message) {
		return CommandUtils.createInvalidCommand(message);
	}

	private void parseSingleType() {
		//System.out.println("currWord at parseSingleType() is " + splitInput[INDEX_SINGLE]);
		if(isNumber(splitInput[INDEX_SINGLE])) {
			taskIdForDeletion = Integer.parseInt(splitInput[INDEX_SINGLE]);
		} else {
			throw new IncorrectInputException("Invalid Task ID!");
		}
	}

	private DELETE_TYPE getDeleteType() {
		if(userInput.contains("-")) {
			return DELETE_TYPE.INTERVAL;
		} else if(splitInput.length == 1) {
			return DELETE_TYPE.SINGLE;
		} else if(splitInput.length > 1) {
			return DELETE_TYPE.MULTIPLE;
		} else {
			return DELETE_TYPE.INVALID;
		}
	}

	private boolean isNumber(String currWord) {
		return currWord.matches("[0-9]+");
	}
}
