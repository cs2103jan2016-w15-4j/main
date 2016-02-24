package dooyit.parser;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class EditParser {
	private static final int START_INDEX_ARGS = 5;
	private static final String MARKER_TIME_START = "from";
	private static final String MARKER_TIME_END = "to";
	private static final String MARKER_DEADLINE = "by";
	
	private static String userInput;
	private static String taskName;
	private static int taskId = -1;
	private static DateTime start = null;
	private static DateTime end = null;
	private static DateTime deadline = null;
	
	enum EDIT_TYPE {
		NAME, DEADLINE, TIME_START_END, TIME_START, TIME_END, NAME_TIME_START_END, NAME_TIME_START, NAME_TIME_END, NAME_DEADLINE, INVALID
	};
	
	public EditParser(String input) {
		userInput = input.trim().toLowerCase();
	}
	
	public Command getCommand() {
		switch(getEditType()) {
		case NAME :
			parseName();
			return CommandUtils.createEditCommandName(taskId, taskName);
		
		case DEADLINE :
			parseDeadline();
			return CommandUtils.createEditCommandDeadline(taskId, deadline);
			
		case TIME_START_END : 
			parseTimeStartEnd();
			return CommandUtils.createEditCommandEvent(taskId, start, end);
		
		case TIME_START : 
			parseTimeStart();
			return CommandUtils.createEditCommandEvent(taskId, start, end);
	
		case TIME_END : 
			parseTimeEnd();
			return CommandUtils.createEditCommandEvent(taskId, start, end);
		
		case NAME_TIME_START_END : 
			parseNameTimeStartEnd();
			return CommandUtils.createEditCommandNameAndEvent(taskId, taskName, start, end);
		
		case NAME_TIME_START : 
			parseNameTimeStart();
			return CommandUtils.createEditCommandNameAndEvent(taskId, taskName, start, end);
		
		case NAME_TIME_END : 
			parseNameTimeEnd();
			return CommandUtils.createEditCommandNameAndEvent(taskId, taskName, start, end);
		
		case NAME_DEADLINE : 
			parseNameDeadline();
			return CommandUtils.createEditCommandNameAndDeadline(taskId, taskName, deadline);
			
		case INVALID :
			return CommandUtils.createInvalidCommand("Invalid Edit Command!");
		}
		return null;
	}
	
	private void parseNameDeadline() {
		parseName();
		parseDeadline();
	}

	private void parseNameTimeEnd() {
		parseName();
		parseTimeEnd();
	}

	private void parseNameTimeStart() {
		parseName();
		parseTimeStart();
	}

	private void parseNameTimeStartEnd() {
		parseName();
		parseTimeStart();
		parseTimeEnd();
	}

	private void parseTimeEnd() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexTo = userInput.lastIndexOf(MARKER_TIME_END);
		if(taskId == -1) {
			taskId = Integer.parseInt(userInput.substring(0, indexTo).trim());
		}
		end = dateTimeParser.parse((userInput.substring(indexTo)));
	}
	
	private void parseTimeStart() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexFrom = userInput.lastIndexOf(MARKER_TIME_START);
		if(taskId == -1) {
			taskId = Integer.parseInt(userInput.substring(0, indexFrom).trim());
		}
		start = dateTimeParser.parse((userInput.substring(indexFrom)));
	}

	private void parseTimeStartEnd() {
		parseName();
		parseTimeStart();
		parseTimeEnd();
	}

	private void parseDeadline() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexDeadline = userInput.lastIndexOf(MARKER_DEADLINE);
		if(taskId == -1) {
			taskId = Integer.parseInt(userInput.substring(0, indexDeadline).trim());
		}
		deadline = dateTimeParser.parse((userInput.substring(indexDeadline)));
	}

	private void parseName() {
		taskName = userInput.split("\\s+")[1].trim();
	}

	private EDIT_TYPE getEditType() {
		if(hasName()) {
			if(!hasStart() && !hasEnd() && !hasDeadline()) {
				return EDIT_TYPE.NAME; 
			} else if(hasStart() && hasEnd() && !hasDeadline()) {
				return EDIT_TYPE.NAME_TIME_START_END; 
			} else if(hasStart() && !hasEnd() && !hasDeadline()) {
				return EDIT_TYPE.NAME_TIME_START;
			} else if(!hasStart() && hasEnd() && !hasDeadline()) {
				return EDIT_TYPE.NAME_TIME_END; 
			} else if(!hasStart() && !hasEnd() && hasDeadline()){		//!hasStart() && !hasEnd()
				return EDIT_TYPE.NAME_DEADLINE;
			}
			
		} else if(hasDeadline() && !hasStart() && !hasEnd()) {
			return EDIT_TYPE.DEADLINE;
			
		} else if(hasStart() || hasEnd()){
			if(hasStart() && hasEnd()) {
				return EDIT_TYPE.TIME_START_END;
			} else if(hasStart() && !hasEnd()) {
				return EDIT_TYPE.TIME_START;
			} else { 	//!hasStart() && hasEnd()
				return EDIT_TYPE.TIME_END;
			}
			
		} else {
			//Invalid command
		}
		return null;
	}
	
	
	private static boolean hasName() {
		String mayBeName = userInput.split("\\s+")[1];
		return !mayBeName.equals(MARKER_DEADLINE) && !mayBeName.equals(MARKER_TIME_END) && !mayBeName.equals(MARKER_TIME_START);
	}
	
	private static boolean hasStart() {
		return userInput.lastIndexOf(MARKER_TIME_START) != -1;
	}
	
	private static boolean hasEnd() {
		return userInput.lastIndexOf(MARKER_TIME_END) != -1;
	}
	
	private static boolean hasDeadline() {
		return userInput.lastIndexOf(MARKER_DEADLINE) != -1;
	}

}
