package dooyit.parser;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DateTime.DAY;
import dooyit.common.datatype.DateTime.MONTH;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class SearchParser implements ParserCommons {
	private static final String ERROR_MESSAGE_EMPTY_SEARCH_COMMAND = "Empty search command!";

	public SearchParser() {
		
	}
	
	public Command getCommand(String input) {
		input = input.toLowerCase();
		boolean isValidDate = true;
		Command command = null;
		DateTime dateTime;
		
		try {
			DateTimeParser dtParser = new DateTimeParser();
			dateTime = dtParser.parse(input);
		} catch (IncorrectInputException e) {
			isValidDate = false;
		}
		
		DAY dayEnum = DateTime.getDayType(input);
		MONTH monthEnum = DateTime.getMonthType(input);
		boolean isValidDay = dayEnum != DAY.INVALID;
		boolean isValidMonth = monthEnum != MONTH.INVALID;
		boolean isEmptyString = input.equals(EMPTY_STRING);
		
		if(isEmptyString) {
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_EMPTY_SEARCH_COMMAND);
		} else if(isValidDay) {
			//command = CommandUtils.createSearchCommand(input, dayEnum);
		} else if(isValidMonth) {
			//command = CommandUtils.createSearchCommand(input, monthEnum);
		} else if(isValidDate) {
			//command = CommandUtils.createSearchCommand(dateTime);
		} else {
			command = CommandUtils.createSearchCommand(input);
		}
		return command;
	}
}
