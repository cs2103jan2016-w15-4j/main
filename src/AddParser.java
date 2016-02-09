public class AddParser {
	String userInput;
	int START_INDEX_ARGS = 4;
	String MARKER_START_EVENT = "from";
	String MARKER_END_EVENT = "to";
	String MARKER_WORK = "by";
	String data;
	DateTime start;
	DateTime end;
	DateTime deadline;
	
	enum TASK_TYPE {
		FLOATING, WORK, EVENT
	};
	
	public AddParser(String input) {
		userInput = input.trim().toLowerCase().substring(START_INDEX_ARGS);
	}
	
	public Command getCommand() {
		switch(getTaskType()) {
		case FLOATING :
			parseFloat();
			return CommandUtils.createAddCommandFloat(data);
		
		case WORK :
			parseWork();
			return CommandUtils.createAddCommandWork(data, deadline);
			
		case EVENT : 
			parseEvent();
			return CommandUtils.createAddCommandEvent(data, start, end);
		}
		return null;
	}
	
	private void parseEvent() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexFrom = userInput.indexOf(MARKER_START_EVENT);
		int indexTo = userInput.indexOf(MARKER_END_EVENT);
		data = userInput.substring(0, indexFrom);
		start = dateTimeParser.parse((userInput.substring(indexFrom, indexTo)));
		end = dateTimeParser.parse((userInput.substring(indexTo)));
	}

	private void parseWork() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexBy = userInput.indexOf(MARKER_WORK);
		data = userInput.substring(0, indexBy);
		deadline = dateTimeParser.parse((userInput.substring(indexBy).trim()));
	}

	private void parseFloat() {
		data = userInput;
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