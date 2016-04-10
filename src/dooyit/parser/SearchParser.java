package dooyit.parser;

import dooyit.common.CommandUtils;
import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DateTime.Day;
import dooyit.common.datatype.DateTime.Month;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;

public class SearchParser implements ParserCommons {
	private static final String ERROR_MESSAGE_EMPTY_SEARCH_COMMAND = "Empty search command!";
	Command command;
	boolean isValidDate, isValidDay, isValidMonth, isEmptyString;
	DateTime dateTime;
	Day dayEnum;
	Month monthEnum;
	
	
	public SearchParser() {
		
	}
	
	public Command getCommand(String input) {
		input = input.toLowerCase();
		resetFields();
		
		try {
			DateTimeParser dtParser = new DateTimeParser();
			dateTime = dtParser.parse(input);
		} catch (IncorrectInputException e) {
			isValidDate = false;
		}
		setBooleanValues(input);
		setCorrectSearchCommand(input);
		return command;
	}

	private void setBooleanValues(String input) {
		dayEnum = DateTime.getDayType(input);
		monthEnum = DateTime.getMonthType(input);
		isValidDay = dayEnum != Day.INVALID;
		isValidMonth = monthEnum != Month.INVALID;
		isEmptyString = input.equals(EMPTY_STRING);
	}

	private void resetFields() {
		isValidDate = true;
		isValidDay = false;
		isValidMonth = false;
		isEmptyString = false;
		command = null;
	}

	private void setCorrectSearchCommand(String input) {
		if (isEmptyString) {
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_EMPTY_SEARCH_COMMAND);
		} else if (isValidDay) {
			command = CommandUtils.createSearchCommand(input, dayEnum);
		} else if (isValidMonth) {
			command = CommandUtils.createSearchCommand(input, monthEnum);
		} else if (isValidDate) {
			command = CommandUtils.createSearchCommand(dateTime);
		} else {
			command = CommandUtils.createSearchCommand(input);
		}
	}
}
