package dooyit.parser;

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
		FLOATING, WORK, EVENT
	};
	
	public AddParser(String input) {
		//userInput ignore the word "add"
		userInput = input.trim().toLowerCase().substring(START_INDEX_ARGS);
	}
	
	public Command getCommand() {
		switch(getTaskType()) {
		case FLOATING :
			parseFloat();
			setToFloatCmd();
			break;
		
		case WORK :
			parseWork();
			setToWorkCmd();
			break;
			
		case EVENT : 
			parseEvent();
			setToEventCmd();
			break;
		}
		return cmd;
		//return CommandUtils.createInvalidCommand("Invalid Command at addParser: ");
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
		start = dateTimeParser.parse((userInput.substring(indexFrom, indexTo).trim()));
		end = dateTimeParser.parse((userInput.substring(indexTo)));
	}

	private void parseWork() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexBy = userInput.lastIndexOf(MARKER_WORK);
		taskName = userInput.substring(0, indexBy);
		deadline = dateTimeParser.parse((userInput.substring(indexBy).trim()));
	}

	private void parseFloat() {
		taskName = userInput;
	}

	private TASK_TYPE getTaskType() {
		if(isEvent()) {
			return TASK_TYPE.EVENT;
		} else if(isWork()) {
			return TASK_TYPE.WORK;
		} else {
			return TASK_TYPE.FLOATING; 
		}
	}
	
	private boolean isEvent() {
		return userInput.lastIndexOf(MARKER_START_EVENT) != -1 &&
				userInput.lastIndexOf(MARKER_END_EVENT) != -1;
	}
	
	private boolean isWork() {
		return userInput.lastIndexOf(MARKER_WORK) != -1;
	}
}