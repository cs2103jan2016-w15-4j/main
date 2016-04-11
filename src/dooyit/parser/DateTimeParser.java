//@@author A0133338J
package dooyit.parser;

import dooyit.parser.DateTimeParserCommons;
import java.util.logging.Level;
import java.util.logging.Logger;
import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;

/**
 * DateTimeParses parses date time user inputs and returns a DateTime object.
 * It calls on RelativeDateParser, FixedDateParser and TimeParser to parse the
 * relevant inputs. It implements DateTimeparserCommons methods to use the shared
 * methods and constants.
 * 
 * @author Annabel
 *
 */
public class DateTimeParser implements DateTimeParserCommons {
	// Error messages
	private static final String ERROR_MESSAGE_ONLY_ONE_DATE = "You can only key in ONE date!";
	private static final String ERROR_MESSAGE_ONLY_ONE_TIMING = "You can only key in ONE timing!";
	private static final String ERROR_MESSAGE_INVALID_DATE_TIME = "Invalid Date Time!";

	// DateTime object attributes
	private DateTime dateTime;
	private RelativeDateParser relativeDateParser;
	private TimeParser timeParser;
	private FixedDateParser fixedDateParser;
	
	// Starting value for counter in combined array
	private static final int STARTING_ARRAY_INDEX_FOR_COMBINED_ARRAY = 0;

	// Logger for DateTimeParser
	private static Logger logger = Logger.getLogger("DateTimeParser");

	// DateTimeParser object attributes
	private int currMM;
	private int currYY;
	private int currDD;
	private int currDayInWeekInt;
	private int currTime;
	private boolean hasDate, hasTime;

	/** Types of DateTime formats */
	private enum DateTimeFormat {
		TYPE_RELATIVE_DATE, TYPE_FIXED_DATE, TYPE_TIME, TYPE_INVALID
	};

	/** Initializes a DateTimeParser object with today's date */
	public DateTimeParser() {
		dateTime = new DateTime();
		currTime = dateTime.getTimeInt();
		currDayInWeekInt = dateTime.getDayInt();
		currDD = dateTime.getDD();
		currMM = dateTime.getMM();
		currYY = dateTime.getYY();

		relativeDateParser = new RelativeDateParser(dateTime);
		timeParser = new TimeParser();
		fixedDateParser = new FixedDateParser(dateTime);
		hasTime = false;
		hasDate = false;

		logger.log(Level.INFO, "Initialised DateTimeParser object with today's date");
	}

	/**
	 * Initializes a DateTimeParser object with the specified date and time in
	 * the DateTime object
	 * 
	 * @param dateTime
	 * 		  DateTime object to specify a date and time
	 */
	public DateTimeParser(DateTime dateTime) {
		this.dateTime = dateTime;
		currTime = dateTime.getTimeInt();
		currDayInWeekInt = dateTime.getDayInt();
		currDD = dateTime.getDD();
		currMM = dateTime.getMM();
		currYY = dateTime.getYY();

		relativeDateParser = new RelativeDateParser(this.dateTime);
		timeParser = new TimeParser();
		fixedDateParser = new FixedDateParser(this.dateTime);
		hasTime = false;
		hasDate = false;

		logger.log(Level.INFO, "Initialised DateTimeParser object with the date " + dateTime.toString());
	}

	/**
	 * Parses a user input and returns a DateTime object if the input is a valid
	 * date time input or throws and exception if the user input is an invalid
	 * date time input.
	 * 
	 * @param input
	 * 		  The user string input for date time
	 * 
	 * @return DateTime object of the user string input for date time.
	 * 
	 * @throws IncorrectInputException if the user input is an invalid date time.
	 */
	public DateTime parse(String input) throws IncorrectInputException {
		String[] splitInput = input.toLowerCase().split("\\s+");
		int[] combined = new int[] { currDayInWeekInt, UNINITIALIZED_INT, currDD, currMM, currYY,
				STARTING_ARRAY_INDEX_FOR_COMBINED_ARRAY };
		DateTime temp;
		resetBooleanValues();
		
		try {
			combined = setCombinedArray(splitInput, combined);
			temp = getDateTimeObject(combined);
		} catch (IncorrectInputException e) {
			throw e;
		}
		return temp;
	}

	private int[] setCombinedArray(String[] splitInput, int[] combined) throws IncorrectInputException {
		for (int i = 0; i < splitInput.length; i++) {
			combined[COMBINED_INDEX_COUNTER] = i;
			String currWord = splitInput[i];

			switch (getDateTimeType(currWord, splitInput, i)) {
			case TYPE_RELATIVE_DATE:
				try {
					setHasDateBoolean();
				} catch (IncorrectInputException e) {
					throw e;
				}
				combined = relativeDateParser.parse(splitInput, combined, i);
				break;

			case TYPE_FIXED_DATE:
				try {
					setHasDateBoolean();
				} catch (IncorrectInputException e) {
					throw e;
				}
				combined = fixedDateParser.parse(splitInput, combined, i);
				break;

			case TYPE_TIME:
				try {
					setHasTimeBoolean();
				} catch (IncorrectInputException e) {
					throw e;
				}
				combined = timeParser.parse(splitInput, combined, i);
				break;

			default:
				throw new IncorrectInputException(ERROR_MESSAGE_INVALID_DATE_TIME);
			}

			i = combined[COMBINED_INDEX_COUNTER];
		}
		return combined;
	}

	/** 
	 * 
	 */
	private void resetBooleanValues() {
		hasTime = false;
		hasDate = false;
	}

	/**
	 * 
	 * @throws IncorrectInputException
	 */
	private void setHasTimeBoolean() throws IncorrectInputException {
		if (!hasTime) {
			hasTime = true;
		} else {
			throw new IncorrectInputException(ERROR_MESSAGE_ONLY_ONE_TIMING);
		}
	}

	/**
	 * 
	 * @throws IncorrectInputException
	 */
	private void setHasDateBoolean() throws IncorrectInputException {
		if (!hasDate) {
			hasDate = true;
		} else {
			throw new IncorrectInputException(ERROR_MESSAGE_ONLY_ONE_DATE);
		}
	}

	/**
	 * 
	 * @param currWord
	 * @param splitUserInput
	 * @param index
	 * @return
	 */
	private DateTimeFormat getDateTimeType(String currWord, String[] splitUserInput, int index) {
		DateTimeFormat type;
		if (timeParser.isValidTime(currWord, splitUserInput, index)) {
			type = DateTimeFormat.TYPE_TIME;

		} else if (relativeDateParser.isRelativeDate(currWord, splitUserInput, index)) {
			type = DateTimeFormat.TYPE_RELATIVE_DATE;

		} else if (fixedDateParser.isFixedDate(currWord, splitUserInput, index)) {
			type = DateTimeFormat.TYPE_FIXED_DATE;

		} else {
			type = DateTimeFormat.TYPE_INVALID;
		}
		return type;
	}

	// This method converts the combined array into a DateTime object
	/**
	 * 
	 * @param combined
	 * @return
	 * @throws IncorrectInputException
	 */
	private DateTime getDateTimeObject(int[] combined) throws IncorrectInputException {
		DateTime dateTime;
		int[] date = new int[] { combined[COMBINED_INDEX_DD], combined[COMBINED_INDEX_MM],
				combined[COMBINED_INDEX_YY] };
		int time = combined[COMBINED_INDEX_TIME];

		if (inputTimeIsOverToday(time, date) && !hasDate) {
			date = DateTimeParserCommons.getDateAfterANumberOfDays(NEXT_DAY, currDD, currMM, currYY);
			dateTime = new DateTime(date, time);
		} else {
			dateTime = new DateTime(date, time);
		}

		return dateTime;
	}

	/**
	 * 
	 * @param inputTime
	 * @param date
	 * @return
	 */
	private boolean inputTimeIsOverToday(int inputTime, int[] date) {
		return currTime > inputTime && inputDateIsToday(date) && inputTime != UNINITIALIZED_INT;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	private boolean inputDateIsToday(int[] date) {
		return date[DATE_INDEX_OF_DD] == currDD && date[DATE_INDEX_OF_MM] == currMM 
				&& date[DATE_INDEX_OF_YY] == currYY;
	}
}
