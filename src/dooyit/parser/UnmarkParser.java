package dooyit.parser;

import java.util.ArrayList;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class UnmarkParser {
	private static final int INDEX_SINGLE = 0;
	private static String userInput;
	private static String[] splitInput;
	private static ArrayList<Integer> taskIdsCompleted;
	private static int taskIdCompleted;
	private static Command cmd;
	
	enum UNMARK_TYPE {
		SINGLE, MULTIPLE, INTERVAL, INVALID
	};
	
	public UnmarkParser(String input) {
		userInput = input;
		splitInput = userInput.split("\\s+");
		taskIdsCompleted = new ArrayList<Integer>();
		cmd = null;
	}

	public Command getCommand() throws IncorrectInputException {
		switch(getUnmarkType()) {
		case SINGLE :
			try {
				parseSingleType();
			} catch(IncorrectInputException e) {
				cmd = getInvalidCommand(e.getMessage());
				break;
			}
			cmd = getSingleTypeUnmarkCmd();
			break;
			
		case MULTIPLE :
			try {
				parseMultipleType();
			} catch(IncorrectInputException e) {
				cmd = getInvalidCommand(e.getMessage());
				break;
			}
			cmd = getMultipleTypeUnmarkCmd();
			break;
			
		case INTERVAL :
			try {
				parseIntervalType();
			} catch(IncorrectInputException e) {
				cmd = getInvalidCommand(e.getMessage());
				break;
			}
			cmd = getIntervalTypeUnmarkCmd();
			break;
		
		case INVALID :
			cmd = getInvalidCmd();
			break;
		}
		return cmd;
	}
	
	private Command getIntervalTypeUnmarkCmd() {
		return null;
		//return CommandUtils.createUnmarkCommand(taskIdsCompleted);
	}

	private void parseIntervalType() throws IncorrectInputException {
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

	//Eg. unmark 2 4 0 9
	private Command getMultipleTypeUnmarkCmd() {
		return null;
		//return CommandUtils.createUnmarkCommand(taskIdsCompleted);
	}

	private void parseMultipleType() throws IncorrectInputException {
		for(int i = INDEX_SINGLE; i < splitInput.length; i++) {
			String currWord = splitInput[i];
			if(!isNumber(currWord)) {
				throw new IncorrectInputException("Invalid Number!");
			} else {
				taskIdsCompleted.add(Integer.parseInt(currWord));
			}
		}
	}

	private Command getSingleTypeUnmarkCmd() {
		return null;
		//return CommandUtils.createUnmarkCommand(taskIdCompleted);
	}

	private Command getInvalidCmd() {
		return CommandUtils.createInvalidCommand("Invalid Unmark Command!");
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

	private UNMARK_TYPE getUnmarkType() {
		if(userInput.contains("-")) {
			return UNMARK_TYPE.INTERVAL;
		} else if(splitInput.length == 1) {
			return UNMARK_TYPE.SINGLE;
		} else if(splitInput.length > 1) {
			return UNMARK_TYPE.MULTIPLE;
		} else {
			return UNMARK_TYPE.INVALID;
		}
	}

	private boolean isNumber(String currWord) {
		return currWord.matches("[0-9]+");
	}
}
