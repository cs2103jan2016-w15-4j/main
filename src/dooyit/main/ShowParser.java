package dooyit.main;

import dooyit.logic.commands.CommandUtils;
import dooyit.parser.Command;

public class ShowParser {
	private static String userInput;
	private static final int START_INDEX_ARGS = 5;
	private static final int COMMAND_TODAY = 1;
	private static final int COMMAND_NEXT_SEVEN = 2;
	private static final int COMMAND_DONE = 3;
	private static final int COMMAND_ALL = 4;
	private static final int COMMAND_DATE = 5;
	private static final int COMMAND_CATERGORY = 6;
	private static final String TODAY = null;
	
	enum DISPLAY_TYPE {
		TODAY, NEXT_SEVEN, DONE, ALL, DATE, CATERGORY
	};
	
	public ShowParser(String input) {
		userInput = input.trim().toLowerCase().substring(START_INDEX_ARGS);
	}

	public Command getCommand() {
		switch(getDisplayType()) {
		case TODAY :
			return CommandUtils.createShowTodayCommand();
		
		case NEXT_SEVEN :
			return CommandUtils.createShowNext7DaysCommand();
			
		case DONE : 
			return CommandUtils.createShowDoneCommand();
			
		case ALL : 
			return CommandUtils.createShowAllCommand();
			
//		case DATE : 
//			DateTimeParser dateTimeParser = new DateTimeParser();
//			DateTime date = dateTimeParser.parse(userInput);
//			return CommandUtils.createShowCommand(COMMAND_DATE, date);		
			
		case CATERGORY : 
			return CommandUtils.createShowCategoryCommand("cat name");
			
		default:
			return null;
		}
	}

	private DISPLAY_TYPE getDisplayType() {
		if(userInput.contains("cat")) {
			return DISPLAY_TYPE.CATERGORY;
		} else if(userInput.equals("today")) {
			return DISPLAY_TYPE.TODAY;
		} else if(userInput.equals("next7")) {
			return DISPLAY_TYPE.NEXT_SEVEN;
		} else if (userInput.equals("done")) {
			return DISPLAY_TYPE.DONE;
		} else if (userInput.equals("all")) {
			return DISPLAY_TYPE.ALL;
		} else { //useInput is a date
			return DISPLAY_TYPE.DATE;
		}
	}
	
	
}
