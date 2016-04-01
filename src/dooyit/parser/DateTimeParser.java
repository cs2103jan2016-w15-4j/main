package dooyit.parser;

import dooyit.parser.DateTimeParserCommon;
import java.util.logging.*;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;

public class DateTimeParser extends DateTimeParserCommon {
	public static final String ERROR_MESSAGE_ONLY_ONE_DATE = "Error: You can only key in ONE date!";
	public static final String ERROR_MESSAGE_ONLY_ONE_TIMING = "Error: You can only key in ONE timing!";
	public static final String ERROR_MESSAGE_GOING_BACK_IN_TIME = "Error: You can't go back in time to add a task or event!";
	public static final String ERROR_MESSAGE_INVALID_DATE_TIME = "Error: Invalid Date Time!";
	
	private DateTime dateTime;
	private RelativeDateParser relativeDateParser;
	private TimeParser timeParser;
	private FixedDateParser fixedDateParser;
	private static Logger logger = Logger.getLogger("DateTimeParser");

	private int currMM;
	private int currYY;
	private int currDD;
	private int currDayInWeekInt;
	private int currTime;
	private boolean hasDate, hasTime;

	enum DATE_TIME_FORMAT {
		TYPE_RELATIVE_DATE, TYPE_FIXED_DATE, TYPE_TIME, TYPE_INVALID
	};

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
	}
	
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
	}
	
	public DateTime parse(String input) throws IncorrectInputException {
		String[] splitInput = input.toLowerCase().split("\\s+");
		int[] combined = new int[] { currDayInWeekInt, UNINITIALIZED_INT, currDD, currMM, currYY, 0 };
		resetBooleanValues();
		for(int i = 0; i < splitInput.length; i++) {
			combined[COMBINED_INDEX_COUNTER] = i;
			String currWord = splitInput[i];
			switch(getDateTimeType(currWord, splitInput, i)) {
			case TYPE_RELATIVE_DATE:
				try{
					setHasDateBoolean();
				} catch (IncorrectInputException e) {
					throw e;
				}
				combined = relativeDateParser.parse(splitInput, combined, i);
				break;
			
			case TYPE_FIXED_DATE:
				try{
					setHasDateBoolean();
				} catch(IncorrectInputException e) {
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
		DateTime temp; 
		try {
			temp = getDateTimeObject(combined);
		} catch (IncorrectInputException e) {
			throw e;
		}
		return temp;
	}
	
	private void resetBooleanValues() {
		hasTime = false;
		hasDate = false;
	}

	private void setHasTimeBoolean() throws IncorrectInputException {
		if(!hasTime) {
			hasTime = true;
		} else {
			throw new IncorrectInputException(ERROR_MESSAGE_ONLY_ONE_TIMING);
		}
	}
	
	private void setHasDateBoolean() throws IncorrectInputException {
		if(!hasDate) {
			hasDate = true;
		} else {
			throw new IncorrectInputException(ERROR_MESSAGE_ONLY_ONE_DATE);
		}
	}


	private DATE_TIME_FORMAT getDateTimeType(String currWord, String[] splitUserInput, int index) {
		DATE_TIME_FORMAT type;
		if(timeParser.isValidTime(currWord, splitUserInput, index)) {
			type = DATE_TIME_FORMAT.TYPE_TIME;
			
		} else if(relativeDateParser.isRelativeDate(currWord, splitUserInput, index)) {
			type = DATE_TIME_FORMAT.TYPE_RELATIVE_DATE;
			
		} else if(fixedDateParser.isFixedDate(currWord, splitUserInput, index)) {
			type = DATE_TIME_FORMAT.TYPE_FIXED_DATE;
			
		} else {
			type = DATE_TIME_FORMAT.TYPE_INVALID; 
		}
		return type;
	}
	

	// This method converts the combined array into a DateTime object
	private DateTime getDateTimeObject(int[] combined) throws IncorrectInputException {
		DateTime dateTime;
		int[] date = new int[] { combined[COMBINED_INDEX_DD], combined[COMBINED_INDEX_MM], combined[COMBINED_INDEX_YY] };
		int time = combined[COMBINED_INDEX_TIME];
		if (inputTimeIsOverToday(time, date) && !hasDate) {
			date = getDateAfterANumberOfDays(NEXT_DAY, currDD, currMM, currYY);
			dateTime = new DateTime(date, time);
		} else {
			dateTime = new DateTime(date, time);
		}

		logger.log(Level.INFO, "Date is " + dateTime.toString());
		return dateTime;
	}

	private boolean inputTimeIsOverToday(int inputTime, int[] date) {
		return currTime > inputTime && inputDateIsToday(date) && inputTime != UNINITIALIZED_INT;
	}
	
	private boolean inputDateIsToday(int[] date) {
		return date[DATE_INDEX_OF_DD] == currDD && date[DATE_INDEX_OF_MM] == currMM && date[DATE_INDEX_OF_YY] == currYY;
	}

	public boolean isValidDateTime(String timeString) {
		String[] splitInput = timeString.toLowerCase().split("\\s+");
		return getDateTimeType(splitInput[0], splitInput, 0) != DATE_TIME_FORMAT.TYPE_INVALID;
	}
}
