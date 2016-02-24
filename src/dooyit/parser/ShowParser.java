package dooyit.parser;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class ShowParser {
	private static String userInput;
	
	enum DISPLAY_TYPE {
		TODAY, NEXT_SEVEN, DONE, ALL, DATE, CATERGORY
	};
	
	public ShowParser(String input) {
		userInput = input.toLowerCase();
		System.out.println("userInput is " + userInput);
	}

	public Command getCommand() {
		switch(getDisplayType()) {
		case TODAY :
			return CommandUtils.createShowTodayCommand();
		
		case NEXT_SEVEN :
			return CommandUtils.createShowNext7DaysCommand();
			
		case DONE : 
			return CommandUtils.createShowCompletedCommand();
			
		case ALL : 
			return CommandUtils.createShowAllCommand();
			
//		case DATE : 
//			DateTimeParser dateTimeParser = new DateTimeParser();
//			DateTime date = dateTimeParser.parse(userInput);
//			return CommandUtils.createShowCommand(COMMAND_DATE, date);		
			
		case CATERGORY : 
			return CommandUtils.createShowCategoryCommand(getCatName(userInput));
			
		default:
			return CommandUtils.createInvalidCommand("Invalid Show command");
		}
	}

	private String getCatName(String userInput2) {
		int index = userInput.indexOf(" ");
		String ans = userInput.substring(index).trim();
		return ans;
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
