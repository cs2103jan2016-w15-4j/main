package dooyit.parser;

import dooyit.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class AddParser {
	
	private static final int START_INDEX_ARGS = 4;
	private static final String MARKER_START_EVENT = " from ";
	private static final String MARKER_END_EVENT = " to ";
	private static final String MARKER_WORK = " by ";
	
	private static String userInput;
	private static String taskName;
	private static DateTime start;
	private static DateTime end;
	private static DateTime deadline;
	private static Command cmd;
	
	enum TASK_TYPE {
		FLOATING, WORK, EVENT, INVALID
	};
	
	public AddParser(String input) {
		//userInput ignore the word "add"
		userInput = input.trim().toLowerCase().substring(START_INDEX_ARGS);
	}
	
	public Command getCommand() {
		switch(getTaskType()) {
		case FLOATING :
			try {
				parseFloat();
			} catch (IncorrectInputException e) {
				setToInvalidCmd(e.getMessage());
				break;
			}
			setToFloatCmd();
			break;
		
		case WORK :
			try {
				parseWork();
			}  catch (IncorrectInputException e) {
				setToInvalidCmd(e.getMessage());
				break;
			}
			setToWorkCmd();
			break;
			
		case EVENT : 
			try {
				parseEvent();
			} catch (IncorrectInputException e) {
				setToInvalidCmd(e.getMessage());
				break;
			}
			setToEventCmd();
			break;
			
		case INVALID :
			setToInvalidCmd("Invalid input!");
		}
		
		return cmd;
	}

	private void setToInvalidCmd(String message) {
		cmd = CommandUtils.createInvalidCommand(message);
	}

	private void setToEventCmd() {
		cmd = CommandUtils.createAddCommandEvent(taskName, start, end);
	}

	private void setToWorkCmd() {
		cmd = CommandUtils.createAddCommandDeadline(taskName, deadline);
	}

	private void setToFloatCmd() {
		cmd = CommandUtils.createAddCommandFloat(taskName);
	}
	
	private void parseEvent() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexFrom = userInput.lastIndexOf(MARKER_START_EVENT);
		int indexTo = userInput.lastIndexOf(MARKER_END_EVENT);			//what if indexTo < indexFrom
		taskName = userInput.substring(0, indexFrom);
		start = dateTimeParser.parse((userInput.substring(indexFrom, indexTo).replace(MARKER_START_EVENT, "").trim()));
		end = dateTimeParser.parse((userInput.substring(indexTo).replace(MARKER_END_EVENT, "").trim()));
	}

	private void parseWork() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexBy = userInput.lastIndexOf(MARKER_WORK);
		taskName = userInput.substring(0, indexBy);
		System.out.println("deadline is " + (userInput.substring(indexBy).replace(MARKER_WORK, "").trim()));
		deadline = dateTimeParser.parse((userInput.substring(indexBy).replace(MARKER_WORK, "").trim()));
		System.out.println("parsed deadline is " + deadline);
	}

	private void parseFloat() {
		taskName = userInput;
	}

	private TASK_TYPE getTaskType() {
		if(isEvent()) {
			return TASK_TYPE.EVENT;
		} else if(isWork()) {
			return TASK_TYPE.WORK;
		} else if(isFloating()){
			return TASK_TYPE.FLOATING; 
		} else {
			return TASK_TYPE.INVALID;
		}
	}
	
	private boolean isFloating() {
		return !userInput.equals("");
	}

	private boolean isEvent() {
		return userInput.lastIndexOf(MARKER_START_EVENT) != -1 &&
				userInput.lastIndexOf(MARKER_END_EVENT) != -1;
	}
	
	private boolean isWork() {
		return userInput.lastIndexOf(MARKER_WORK) != -1;
	}
}