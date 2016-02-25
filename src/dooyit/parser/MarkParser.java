package dooyit.parser;

import java.util.ArrayList;

import dooyit.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class MarkParser {
	private static final int INDEX_SINGLE = 0;
	private static String userInput;
	private static String[] splitInput;
	private static ArrayList<Integer> taskIdsCompleted;
	private static int taskIdCompleted;
	private static Command cmd;
	
	enum MARK_TYPE {
		SINGLE, MULTIPLE, INTERVAL, INVALID
	};
	
	public MarkParser(String input) {
		userInput = input;
		splitInput = userInput.split("\\s+");
		taskIdsCompleted = new ArrayList<Integer>();
		cmd = null;
	}

	public Command getCommand() {
		switch(getMarkType()) {
		case SINGLE :
			try {
				parseSingleType();
			} catch(IncorrectInputException e) {
				cmd = getInvalidCommand(e.getMessage());
				break;
			}
			cmd = getSingleTypeMarkCmd();
			break;
			
		case MULTIPLE :
			try {
				parseMultipleType();
			} catch(IncorrectInputException e) {
				cmd = getInvalidCommand(e.getMessage());
				break;
			}
			cmd = getMultipleTypeMarkCmd();
			break;
			
		case INTERVAL :
			try {
				parseIntervalType();
			} catch(IncorrectInputException e) {
				cmd = getInvalidCommand(e.getMessage());
				break;
			}
			cmd = getIntervalTypeMarkCmd();
			break;
		
		case INVALID :
			cmd = getInvalidCmd();
			break;
		}
		return cmd;
	}
	
	private Command getIntervalTypeMarkCmd() {
		return CommandUtils.createMarkCommand(taskIdsCompleted);
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
			taskIdsCompleted.add(i);
		}
	}

	//Eg. delete 5 6 8
	//Eg. mark 2 4 0 9
	private Command getMultipleTypeMarkCmd() {
		return CommandUtils.createMarkCommand(taskIdsCompleted);
	}

	private void parseMultipleType() {
		for(int i = INDEX_SINGLE; i < splitInput.length; i++) {
			String currWord = splitInput[i];
			if(!isNumber(currWord)) {
				throw new IncorrectInputException("Invalid Number!");
			} else {
				taskIdsCompleted.add(Integer.parseInt(currWord));
			}
		}
	}

	private Command getSingleTypeMarkCmd() {
		return CommandUtils.createMarkCommand(taskIdCompleted);
	}

	private Command getInvalidCmd() {
		return CommandUtils.createInvalidCommand("Invalid Delete Command!");
	}

	private Command getInvalidCommand(String message) {
		return CommandUtils.createInvalidCommand(message);
	}

	private void parseSingleType() {
		if(isNumber(splitInput[INDEX_SINGLE])) {
			taskIdCompleted = Integer.parseInt(splitInput[INDEX_SINGLE]);
		} else {
			throw new IncorrectInputException("Invalid Task ID!");
		}
	}

	private MARK_TYPE getMarkType() {
		if(userInput.contains("-")) {
			return MARK_TYPE.INTERVAL;
		} else if(splitInput.length == 1) {
			return MARK_TYPE.SINGLE;
		} else if(splitInput.length > 1) {
			return MARK_TYPE.MULTIPLE;
		} else {
			return MARK_TYPE.INVALID;
		}
	}

	private boolean isNumber(String currWord) {
		return currWord.matches("[0-9]+");
	}
}
