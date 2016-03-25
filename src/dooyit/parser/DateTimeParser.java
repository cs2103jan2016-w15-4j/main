package dooyit.parser;

import dooyit.parser.DateTimeParserCommon;
import java.text.DateFormat;
import java.util.logging.*;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;

public class DateTimeParser implements DateTimeParserCommon {
	private static final int INDEX_FEBRUARY = 2;
	DateFormat dateFormat;
	DateTime dateTime;
	RelativeDateParser relativeDateParser;
	TimeParser timeParser;
	FixedDateParser fixedDateParser;

	private static Logger logger = Logger.getLogger("DateTimeParser");
	private static String[] daysInWeekFull = new String[] { EMPTY_STRING, "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

	private int currMM;
	private int currYY;
	private int currDD;
	private int currDayInWeekInt;
	private int currTime;
	private String currDayInWeekString;

	enum DATE_TIME_FORMAT {
		TYPE_RELATIVE_DATE, TYPE_FIXED_DATE, TYPE_TIME, TYPE_INVALID
	};

	public DateTimeParser() {
		dateTime = new DateTime();
		currTime = dateTime.getTimeInt();
		currDayInWeekString = dateTime.getDayStr();
		currDayInWeekInt = dateTime.getDayInt();
		currDD = dateTime.getDD();
		currMM = dateTime.getMM();
		currYY = dateTime.getYY();
		relativeDateParser = new RelativeDateParser(dateTime);
		timeParser = new TimeParser();
		fixedDateParser = new FixedDateParser(dateTime);
	}
	
	public DateTimeParser(DateTime dateTime) {
		this.dateTime = dateTime;
		currTime = dateTime.getTimeInt();
		currDayInWeekString = dateTime.getDayStr();
		currDayInWeekInt = dateTime.getDayInt();
		currDD = dateTime.getDD();
		currMM = dateTime.getMM();
		currYY = dateTime.getYY();
		
		relativeDateParser = new RelativeDateParser(this.dateTime);
		timeParser = new TimeParser();
		fixedDateParser = new FixedDateParser(this.dateTime);
		

	}
	
	public DateTime parse(String input) throws IncorrectInputException {
		logger.log(Level.INFO, "input is " + input);
		String[] splitInput = input.toLowerCase().split("\\s+");
		int[] combined = new int[] { currDayInWeekInt, UNINITIALIZED_INT, currDD, currMM, currYY, 0 };
		
		for(int i = 0; i < splitInput.length; i++) {
			System.out.println("START: i is " + i);
			combined[COMBINED_INDEX_COUNTER] = i;
			String currWord = splitInput[i];
			switch(getDateTimeType(currWord, splitInput, i)) {
			case TYPE_RELATIVE_DATE:
				System.out.println("isRelativeDate");
				combined = relativeDateParser.parse(splitInput, combined, i);
				break;
			
			case TYPE_FIXED_DATE:
				System.out.println("isFixedDate");
				combined = fixedDateParser.parse(splitInput, combined, i);
				break;
			
			case TYPE_TIME:
				System.out.println("is timetype");
				combined = timeParser.parse(splitInput, combined, i);
				break;
				
			default:
				System.out.println("is default");
				throw new IncorrectInputException("Invalid Date Time!");
			}
			
			i = combined[COMBINED_INDEX_COUNTER];
			//printArray(combined);
			System.out.println("END: i is " + i);
		}
		
		return getDateTimeObject(combined);
	}
	
	private DATE_TIME_FORMAT getDateTimeType(String currWord, String[] splitUserInput, int index) {
		if(timeParser.isValidTime(currWord, splitUserInput, index)) {
			return DATE_TIME_FORMAT.TYPE_TIME;
			
		} else if(relativeDateParser.isRelativeDate(currWord, splitUserInput, index)) {
			return DATE_TIME_FORMAT.TYPE_RELATIVE_DATE;
			
		} else if(fixedDateParser.isFixedDate(currWord, splitUserInput, index)) {
			return DATE_TIME_FORMAT.TYPE_FIXED_DATE;
			
		} else {
			return DATE_TIME_FORMAT.TYPE_INVALID;
		}
	}
	

	// This method converts the combined array into a DateTime object
	private DateTime getDateTimeObject(int[] combined) {
		DateTime dateTime;
		int[] date = new int[] { combined[COMBINED_INDEX_DD], combined[COMBINED_INDEX_MM], combined[COMBINED_INDEX_YY] };
		int time = combined[COMBINED_INDEX_TIME];
		int day = combined[COMBINED_INDEX_DAY_OF_WEEK];

		if (hasPassed(currTime, time, date)) {
			date = getDateAfterANumberOfDays(NEXT_DAY);
			dateTime = new DateTime(date, daysInWeekFull[getNextDayInt()], time);
		} else {
			dateTime = new DateTime(date, daysInWeekFull[day], time);
		}
		System.out.println(dateTime.toString());
		return dateTime;
	}
	
	//This method is repeated in relative date parser, i need to remove this
	private int[] getDateAfterANumberOfDays(int fastForward) {
		int newDay = currDD + fastForward;
		int newMonth = currMM;
		int newYear = currYY;
		
		int[] daysInMonth;
		if (isLeapYear(currYY)) {
			daysInMonth = daysInMonthLeapYear;
		} else {
			daysInMonth = daysInMonthNonLeapYear;
		}

		while (newDay > daysInMonth[newMonth]) {
			newDay -= daysInMonth[newMonth];
			newMonth += 1;
		}

		if (newMonth > NUMBER_OF_MONTHS_IN_A_YEAR) {
			newMonth = 1;
			newYear = incrementByOne(newYear);
		}

		int[] ans = new int[] { newDay, newMonth, newYear };
		return ans;
	}
	
	//This method is repeated in relative date parser, i need to remove this
	private int getNextDayInt() {
		int temp = incrementByOne(currDayInWeekInt);
		temp %= NUMBER_OF_DAYS_IN_WEEK ;
		return temp;
	}

	private void printArray(int[] arr) {
		String temp = EMPTY_STRING;
		for (int i = 0; i < arr.length; i++) {
			temp += arr[i];
			temp += " ";
		}
		System.out.println(temp);
	}

	private boolean hasPassed(int currTime2, int time, int[] date) {
		return currTime2 > time && date[DATE_INDEX_OF_DD] == currDD && date[DATE_INDEX_OF_MM] == currMM
				&& date[DATE_INDEX_OF_YY] == currYY && time != UNINITIALIZED_INT;
	}
}
