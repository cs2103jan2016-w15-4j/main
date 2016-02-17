package dooyit.parser;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class AddParser {
	
	private static final int START_INDEX_ARGS = 4;
	private static final String MARKER_START_EVENT = "from";
	private static final String MARKER_END_EVENT = "to";
	private static final String MARKER_WORK = "by";
	
	private static String userInput;
	private static String taskName;
	private static DateTime start;
	private static DateTime end;
	private static DateTime deadline;
	
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
			return CommandUtils.createAddCommandFloat(taskName);
		
		case WORK :
			parseWork();
			return CommandUtils.createAddCommandDeadline(taskName, deadline);
			
		case EVENT : 
			parseEvent();
			return CommandUtils.createAddCommandEvent(taskName, start, end);
		}
		
		return CommandUtils.createInvalidCommand("Invalid Command at addParser: ");
	}
	
	private void parseEvent() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexFrom = userInput.indexOf(MARKER_START_EVENT);
		int indexTo = userInput.indexOf(MARKER_END_EVENT);
		taskName = userInput.substring(0, indexFrom);
		start = dateTimeParser.parse((userInput.substring(indexFrom, indexTo).trim()));
		end = dateTimeParser.parse((userInput.substring(indexTo)));
	}

	private void parseWork() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexBy = userInput.indexOf(MARKER_WORK);
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
		return userInput.indexOf(MARKER_START_EVENT) != -1 &&
				userInput.indexOf(MARKER_END_EVENT) != -1;
	}
	
	private boolean isWork() {
		return userInput.indexOf(MARKER_WORK) != -1;
	}
}