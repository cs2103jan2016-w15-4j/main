package dooyit.parser;

import java.text.DateFormat;
import java.util.logging.*;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;

public class DateTimeParser {
	
	private static final String ERROR_MESSAGE_MINUTES_EXCEED_SIXTY = "Invalid time! Minutes must be smaller than 60";
	private static final String ERROR_MESSAGE_TIME_EXCEEDS_24H = "Invalid Time! Time must not exceed 24 hours!";
	private static final String ERROR_MESSAGE_AM_AND_PM = " cannot be am and pm!";
	private static final String TWELVE_MIDNIGHT_ONE_STRING = "12am";
	private static final int FORMAT_24H_12PM = 1200;
	private static final String EMPTY_STRING = "";
	private static final String TIME_SEPARATOR_DOT = ".";
	private static final String TIME_SEPARATOR_COLON = ":";
	private static final int MAX_NUM_OF_DAYS_IN_A_MONTH = 31;
	private static final String PM = "pm";
	private static final String AM = "am";
	private static final String DATE_SEPARATOR = "/";
	private static final int TWELVE_HOURS = 1200;
	private static final int NUMBER_OF_DAYS_IN_WEEK = 7;
	private static final int NEXT_DAY = 1;

	private static final int COMBINED_INDEX_DAY_OF_WEEK = 0;
	private static final int COMBINED_INDEX_TIME = 1;
	private static final int COMBINED_INDEX_DD = 2;
	private static final int COMBINED_INDEX_MM = 3;
	private static final int COMBINED_INDEX_YY = 4;
	private static final int COMBINED_INDEX_COUNTER = 5;

	private static final int DATE_INDEX_OF_DD = 0;
	private static final int DATE_INDEX_OF_MM = 1;
	private static final int DATE_INDEX_OF_YY = 2;
	private static final int DATE_INDEX_OF_ADVANCE_INT = 3;

	private static final String DUMMY_STR = "Dummy_Str";
	private static final int UNINITIALIZED_INT = -1;
	private static final int DEFAULT_DD_IN_MONTH = 15;
	private static final int INDEX_FEBRUARY = 2;
	private static final String THIS = "this";
	private static final String NEXT = "next";

	private static boolean isInvalidDateTime;
	DateFormat dateFormat;

	// Can just look for substrings
	private static String[] daysInWeek = new String[] { DUMMY_STR, "mon", "tue", "wed", "thu", "fri", "sat", "sun" };
	private static String[] monthsInYear = new String[] { DUMMY_STR, "jan", "feb", "mar", "apr", "may", "jun", 
															"jul", "aug", "sep", "oct", "nov", "dec" };
	private static final int NUMBER_OF_MONTHS_IN_A_YEAR = monthsInYear.length;

	int LEAP_YEAR_FEB = 29;
	private static int[] daysInMonth = new int[] { UNINITIALIZED_INT, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };


	private int currMM;
	private int currYY;
	private int currDD;
	private int currDayInWeekInt;
	private int currTime;
	private String currDayInWeekString;


	private static Logger logger = Logger.getLogger("DateTimeParser");

	enum DATE_TIME_FORMAT {
		TYPE_THIS_DAY_OF_WEEK, TYPE_NEXT_DAY_OF_WEEK, TYPE_DAY_OF_WEEK, TYPE_NEXT_WEEK, TYPE_NUM_DAYS, TYPE_NUM_WEEKS, TYPE_TODAY, TYPE_TOMORROW, TYPE_DATE, TYPE_TIME, TYPE_INVALID
	};

	public DateTimeParser() {
		DateTime dateTime = new DateTime();
		currTime = dateTime.getTimeInt();
		currDayInWeekString = dateTime.getDayStr();
		currDayInWeekInt = dateTime.getDayInt();
		currDD = dateTime.getDD();
		currMM = dateTime.getMM();
		currYY = dateTime.getYY();

		if (isLeapYear(currYY)) {
			daysInMonth[INDEX_FEBRUARY] = LEAP_YEAR_FEB;
		}
	}
	
	public DateTimeParser(DateTime dateTime) {
		currTime = dateTime.getTimeInt();
		currDayInWeekString = dateTime.getDayStr();
		currDayInWeekInt = dateTime.getDayInt();
		currDD = dateTime.getDD();
		currMM = dateTime.getMM();
		currYY = dateTime.getYY();

		if (isLeapYear(currYY)) {
			daysInMonth[INDEX_FEBRUARY] = LEAP_YEAR_FEB;
		}
	}

	private boolean isLeapYear(int currYear) {
		int[] arrLeapYears = new int[] { 2016, 2020, 2024, 2028, 2032, 2036, 2040, 2044, 2048, 2052, 2056, 2060, 2064,
				2068, 2072, 2076, 2080, 2084, 2088, 2092, 2096 };

		boolean ans = false;
		for (int i = 0; i < arrLeapYears.length; i++) {
			if (currYear < arrLeapYears[i]) {
				break;
			}
			if (currYear == arrLeapYears[i]) {
				ans = true;
				break;
			}
		}
		return ans;
	}

	private DATE_TIME_FORMAT getDateTimeType(String currWord, String[] splitUserInput, int index) {
		if (isThisMonday(currWord, splitUserInput, index)) {
			logger.log(Level.INFO, "is this Monday");
			return DATE_TIME_FORMAT.TYPE_THIS_DAY_OF_WEEK;

		} else if (isNextWeek(currWord, splitUserInput, index)) {
			logger.log(Level.INFO, "isNextWeek");
			return DATE_TIME_FORMAT.TYPE_NEXT_WEEK;

		} else if (isNextWeekday(currWord, splitUserInput, index)) {
			logger.log(Level.INFO, "isNextWeekday");
			return DATE_TIME_FORMAT.TYPE_NEXT_DAY_OF_WEEK;

		} else if (isValidDay(currWord)) {
			logger.log(Level.INFO, "isValidDay");
			return DATE_TIME_FORMAT.TYPE_DAY_OF_WEEK;

		} else if (isValidTime(currWord, splitUserInput, index)) {
			logger.log(Level.INFO, "isValidTime");
			return DATE_TIME_FORMAT.TYPE_TIME;

		} else if (isValidDate(currWord, splitUserInput, index)) {
			logger.log(Level.INFO, "isValidDate");
			return DATE_TIME_FORMAT.TYPE_DATE;

		} else if (isToday(currWord)) {
			logger.log(Level.INFO, "isToday");
			return DATE_TIME_FORMAT.TYPE_TODAY;

		} else if (isTomorrow(currWord)) {
			logger.log(Level.INFO, "isTomorrow");
			return DATE_TIME_FORMAT.TYPE_TOMORROW;

		} else if (isNumberOfDays(currWord, splitUserInput, index)) {
			logger.log(Level.INFO, "isNumDays");
			return DATE_TIME_FORMAT.TYPE_NUM_DAYS;

		} else if (isNumberOfWeeks(currWord, splitUserInput, index)) {
			logger.log(Level.INFO, "isNumWeeks");
			return DATE_TIME_FORMAT.TYPE_NUM_WEEKS;

		} else {
			logger.log(Level.INFO, "isInvalidType");
			return DATE_TIME_FORMAT.TYPE_INVALID;
		}
	}

	private boolean isNumberOfDays(String currWord, String[] splitUserInput, int index) {
		boolean ans = false;
		if (isNumber(currWord) && hasAWordAfterCurrWord(splitUserInput, index)) {
			String nextWord = splitUserInput[incrementByOne(index)];
			ans = nextWord.equals("days") || nextWord.equals("day") || nextWord.equals("dd");
		}
		return ans;
	}

	private boolean isNumberOfWeeks(String currWord, String[] arr, int index) {
		boolean ans = false;
		if (isNumber(currWord) && hasAWordAfterCurrWord(arr, index)) {
			String nextWord = arr[incrementByOne(index)];
			ans = nextWord.equals("weeks") || nextWord.equals("week") || nextWord.equals("wk");
		}
		return ans;
	}

	private boolean hasAWordAfterCurrWord(String[] arr, int index) {
		return index < arr.length - 1;
	}

	private int incrementByOne(int index) {
		return index + 1;
	}

	public DateTime parse(String input) throws IncorrectInputException {
		String[] splitInput = input.toLowerCase().split("\\s+");
		// [dayOfWeek, time, DD, MM, YY, indexInArray]
		int[] combined = new int[] { currDayInWeekInt, UNINITIALIZED_INT, currDD, currMM, currYY, 0 };
		int maxCounter = 0;
		
		for (int i = 0; i < splitInput.length; i++) {
			if(maxCounter == 10) {
				break;
			}
			String currWord = splitInput[i];
			switch (getDateTimeType(currWord, splitInput, i)) {
			case TYPE_THIS_DAY_OF_WEEK:
				combined = getThisDayOfWeek(splitInput, i, combined);
				break;

			case TYPE_NEXT_DAY_OF_WEEK:
				combined = getNextDayOfWeek(splitInput, i, combined);
				break;

			case TYPE_DAY_OF_WEEK:
				combined = getDayOfWeek(splitInput, i, combined);
				break;

			case TYPE_NEXT_WEEK:
				combined = getDateAfterOneWeek(splitInput, i, combined);
				break;

			case TYPE_NUM_DAYS:
				combined = getDateAndDayAfterNumberOfDays(splitInput, i, combined);
				break;

			case TYPE_NUM_WEEKS:
				combined = getDateAndDayAfterNumberOfWeeks(splitInput, i, combined);
				break;

			case TYPE_TODAY:
				combined = getToday(combined);
				break;

			case TYPE_TOMORROW:
				combined = getTomorrow(combined);
				break;

			case TYPE_DATE:
				try {
					combined = getDate(splitInput, i, combined);
				} catch (IncorrectInputException e) {
					throw e;
				}
				break;

			case TYPE_TIME:
				try {
					combined = getTimeFromUserInput(splitInput, i, combined);
				} catch (IncorrectInputException e) {
					throw e;
				}
				break;

			default:
				combined[COMBINED_INDEX_COUNTER] += 1;
				throw new IncorrectInputException("\"" + input + "\"" + " is an invalid date time input");

			}
			i = combined[COMBINED_INDEX_COUNTER];
			
			
			maxCounter++;
		}
		return getDateTimeObject(combined);
	}

	// This method converts the array into a DateTime object
	private DateTime getDateTimeObject(int[] combined) {
		DateTime dateTime;
		if (!isInvalidDateTime) {
			int[] date = new int[] { combined[COMBINED_INDEX_DD], combined[COMBINED_INDEX_MM], combined[COMBINED_INDEX_YY] };
			int time = combined[COMBINED_INDEX_TIME];
			int day = combined[COMBINED_INDEX_DAY_OF_WEEK];

			if (hasPassed(currTime, time, date)) {
				date = getDateAfterANumberOfDays(NEXT_DAY);
				dateTime = new DateTime(date, daysInWeek[getNextDayInt()], time);
			} else {
				dateTime = new DateTime(date, daysInWeek[day], time);
			}
		} else {
			dateTime = null;
		}
		return dateTime;
	}

	private int[] getTomorrow(int[] combined) {
		int day = getNextDayInt();
		int[] date = getDateAfterANumberOfDays(NEXT_DAY);
		int[] ans = getNewCombinedArray(combined, date, day);
		printArray(ans);
		return ans;
	}

	private int[] getNewCombinedArray(int[] combined, int[] date, int day ) {
		return new int[] { day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_OF_DD], date[DATE_INDEX_OF_MM], date[DATE_INDEX_OF_YY], combined[COMBINED_INDEX_COUNTER] };
	}
	
	private int[] getToday(int[] combined) {
		return combined;
	}

	private void printArray(int[] arr) {
		String temp = EMPTY_STRING;
		for (int i = 0; i < arr.length; i++) {
			temp += arr[i];
			temp += " ";
		}
		System.out.println(temp);
	}

	// Eg. 2 weeks later
	private int[] getDateAndDayAfterNumberOfWeeks(String[] splitInput, int index, int[] combined) {
		int numWeeksLater = Integer.parseInt(splitInput[index]);
		int day = currDayInWeekInt;
		int fastForward = getFastForward(day) + NUMBER_OF_DAYS_IN_WEEK * numWeeksLater;
		int[] date = getDateAfterANumberOfDays(fastForward);
		int[] ans = getNewCombinedArray(combined, date, day, incrementByOne(index));
		return ans;
	}
	
	private int[] getNewCombinedArray(int[] combined, int[] date, int day, int index) {
		return new int[] { day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_OF_DD], date[DATE_INDEX_OF_MM], date[DATE_INDEX_OF_YY], index };
	}

	// Eg. 2 days
	private int[] getDateAndDayAfterNumberOfDays(String[] splitInput, int index, int[] combined) {
		int numDaysLater = Integer.parseInt(splitInput[index]);
		int day = getDayOfWeekAfterANumberOfDays(numDaysLater);
		int[] date = getDateAfterANumberOfDays(numDaysLater);
		printArray(date);
		return getNewCombinedArray(combined, date, day, incrementByOne(index));
	}

	// Eg. next week
	private int[] getDateAfterOneWeek(String[] splitInput, int index, int[] combined) {
		int day = currDayInWeekInt;
		int fastForward = getFastForward(day) + NUMBER_OF_DAYS_IN_WEEK;
		int[] date = getDateAfterANumberOfDays(fastForward);
		return getNewCombinedArray(combined, date, day, incrementByOne(index));
	}

	// Eg. monday, wed
	private int[] getDayOfWeek(String[] splitInput, int index, int[] combined) {
		int day = convertDayStringToInt(splitInput[index]);
		int fastForward = getFastForward(day);
		int[] date = getDateAfterANumberOfDays(fastForward);
		return getNewCombinedArray(combined, date, day, index);
	}

	// Eg. next monday, next wed
	private int[] getNextDayOfWeek(String[] splitInput, int index, int[] combined) {
		int day = convertDayStringToInt(splitInput[incrementByOne(index)]);
		int fastForward = getFastForward(day) + NUMBER_OF_DAYS_IN_WEEK;
		int[] date = getDateAfterANumberOfDays(fastForward);
		return getNewCombinedArray(combined, date, day, incrementByOne(index));
	}

	// Eg. this monday, this wed, this wednesday
	private int[] getThisDayOfWeek(String[] splitInput, int index, int[] combined) {
		// splitInput[i].equals("this")
		int day = convertDayStringToInt(splitInput[incrementByOne(index)]);
		int fastForward = getFastForward(day);
		int[] date = getDateAfterANumberOfDays(fastForward);
		return getNewCombinedArray(combined, date, day, incrementByOne(index));
	}

	private int[] getDate(String[] splitInput, int index, int[] combined) throws IncorrectInputException {
		int[] newDate;
		try {
			newDate = getDateAndAdvanceInt(splitInput, index); // [dd, mm, yy, advanceInt]
		} catch (IncorrectInputException e) {
			throw e;
		}
		
		if(isInvalidDate(newDate)) {
			throw new IncorrectInputException();
		}
		
		return getNewCombinedArray(combined, newDate, getDayOfWeekFromADate(newDate), getAdvanceInt(newDate, combined[COMBINED_INDEX_COUNTER]));
	}

	private int getAdvanceInt(int[] newDate, int index) {
		int ans = newDate[DATE_INDEX_OF_ADVANCE_INT] + index;
		return ans;
	}

	// This method calculates that day of the week that the date falls on
	private int getDayOfWeekFromADate(int[] date) {
		int[] dayTable = new int[] { 7, 1, 2, 3, 4, 5, 6 };
		int[] monthTable = new int[] {UNINITIALIZED_INT, 6, 2, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
		int dd = date[DATE_INDEX_OF_DD];
		int mm = date[DATE_INDEX_OF_MM];
		int yy = date[DATE_INDEX_OF_YY];
		
		int lastTwoDigitsOfYear = yy % 1000;
		int divideLastTwoDigitsOfYearByFour = lastTwoDigitsOfYear / 4;
		int sum = lastTwoDigitsOfYear + divideLastTwoDigitsOfYearByFour + dd + monthTable[mm];
		if(isLeapYear(yy) && mm <= 2 && dd <= 31) {
			sum--;
		}
		return dayTable[sum % NUMBER_OF_DAYS_IN_WEEK];
		
	}

	// To Do: include the check for valid dates
	private int[] getDateAndAdvanceInt(String[] splitInput, int currIndexInSplitInput) throws IncorrectInputException {
		String firstWord = splitInput[currIndexInSplitInput];
		int[] dateAdvanceIntArray= new int[] { UNINITIALIZED_INT, UNINITIALIZED_INT, UNINITIALIZED_INT, UNINITIALIZED_INT };
		int numEntries = UNINITIALIZED_INT;

		if (firstWord.contains(DATE_SEPARATOR)) {
			//firstWord is in the form of DD/MM or DD/MM/YY
			String[] userInputForDate = firstWord.split(DATE_SEPARATOR);
			for (int j = 0; j < userInputForDate.length; j++) {
				dateAdvanceIntArray[j] = Integer.parseInt(userInputForDate[j]);
			}
			numEntries++;
			
		} else {
			int counter = 0;
			for (int j = currIndexInSplitInput; j < splitInput.length; j++) {
				if (counter > 2) {
					break;
				} else {
					String currWord = splitInput[j];
					if (isNumber(currWord)) {			//currWord is either DD or YY
						int currInt = convertStringToInt(currWord);
						if (currInt <= MAX_NUM_OF_DAYS_IN_A_MONTH && isUninitialized(dateAdvanceIntArray, DATE_INDEX_OF_DD)) {
							dateAdvanceIntArray[DATE_INDEX_OF_DD] = currInt;
							numEntries++;

						} else if (currInt >= 2000 && isUninitialized(dateAdvanceIntArray, DATE_INDEX_OF_YY)) {
							dateAdvanceIntArray[DATE_INDEX_OF_YY] = currInt;
							numEntries++;
						}
					} else if (isMonth(currWord) && isUninitialized(dateAdvanceIntArray, DATE_INDEX_OF_MM)) {
						dateAdvanceIntArray[DATE_INDEX_OF_MM] = convertMonthStrToInt(currWord);
						numEntries++;
					}
				}
				counter++;
			}
		}

		if (isUninitialized(dateAdvanceIntArray, DATE_INDEX_OF_YY)) {
			dateAdvanceIntArray[DATE_INDEX_OF_YY] = getCorrectYear(dateAdvanceIntArray);
		}

		if (isUninitialized(dateAdvanceIntArray, DATE_INDEX_OF_DD)) {
			dateAdvanceIntArray[DATE_INDEX_OF_DD] = DEFAULT_DD_IN_MONTH; // Default
		}

		if (isInvalidDate(dateAdvanceIntArray)) {
			throw new IncorrectInputException("Invalid date!");
		}

		dateAdvanceIntArray[DATE_INDEX_OF_ADVANCE_INT] = numEntries;
		return dateAdvanceIntArray;
	}

	private int getCorrectYear(int[] dateAdvanceIntArray) {
		int newDD = dateAdvanceIntArray[DATE_INDEX_OF_DD];
		int newMM = dateAdvanceIntArray[DATE_INDEX_OF_MM];
		int newYY = UNINITIALIZED_INT;
		if(newMM <= currMM && newDD < currDD) {		// Date has passed
			newYY = incrementByOne(currYY);			// Increase the year by one
		} else {				// Date has not passed
			newYY = currYY;		// Set the year to current year
		}
		return newYY;
	}

	private boolean isUninitialized(int[] ans, int index) {
		return ans[index] == UNINITIALIZED_INT;
	}

	// Checks that the num of months does not exceed the max num of days in that
	// month,
	// Eg. 30 Feb will return an error
	private boolean isInvalidDate(int[] ans) {
		int mm = ans[DATE_INDEX_OF_MM];
		return ans[DATE_INDEX_OF_DD] > daysInMonth[mm];
	}

	private boolean isValidTime(String currWord, String[] userInput, int index) {
		boolean ans = false;

		if (currWord.contains(PM) || currWord.contains(AM)) {
			currWord = currWord.replace(PM, EMPTY_STRING).replace(AM, EMPTY_STRING);
			if (currWord.contains(TIME_SEPARATOR_COLON) || currWord.contains(TIME_SEPARATOR_DOT)) { // Eg: 12pm, 12.30pm, 12:50am
				currWord = removeTimeSeparators(currWord);
				ans = true;
			} else {
				ans = true;
			}

		} else if (isNumber(removeTimeSeparators(currWord)) && hasAWordAfterCurrWord(userInput, index)) { // Eg.9.30 pm, 9
			String nextWord = userInput[incrementByOne(index)];
			if (nextWord.equals(AM) ^ nextWord.equals(PM)) {
				ans = true;
			}
		} else if (currWord.contains(TIME_SEPARATOR_COLON)) { // 24Hour formats eg: 23:30
			currWord = removeTimeSeparators(currWord);
			if (isNumber(currWord)) {
				ans = true;
			}
		} else {
			// Invalid command here
		}
		return ans;
	}

	private String removeTimeSeparators(String currWord) {
		return currWord.replace(TIME_SEPARATOR_COLON, EMPTY_STRING).replace(TIME_SEPARATOR_DOT, EMPTY_STRING);
	}

	private boolean isTomorrow(String currWord) {
		return currWord.equals("tomorrow") || currWord.equals("tmr");
	}

	private boolean isNextWeekday(String currWord, String[] userInput, int index) {
		boolean ans = false;
		if (currWord.equals(NEXT) && hasAWordAfterCurrWord(userInput, index)) { 
			//Check if there is a word that follow "this"
			ans = isValidDay(userInput[incrementByOne(index)]);
		}
		return ans;
	}

	private boolean isNextWeek(String currentWord, String[] userInput, int index) {
		boolean ans = false;
		if (currentWord.equals(NEXT) && hasAWordAfterCurrWord(userInput, index)) {
			int indexOfNextWord = incrementByOne(index);
			String nextWord = userInput[indexOfNextWord];
			ans = nextWord.equals("week") || nextWord.equals("weeks") || nextWord.equals("wk");
		}
		return ans;
	}

	private boolean isThisMonday(String currentWord, String[] userInput, int index) {
		boolean ans = false;
		if (currentWord.equals(THIS) && hasAWordAfterCurrWord(userInput, index)) { 
			int indexOfNextWord = incrementByOne(index);
			String nextWord = userInput[indexOfNextWord];
			ans = isValidDay(nextWord);
		}
		return ans;
	}

	private boolean isValidDay(String currWord) {
		return convertDayStringToInt(currWord) != -1;
	}

	private boolean isValidDate(String currWord, String[] splitInput, int i) {
		boolean ans = false;

		if (currWord.contains(DATE_SEPARATOR)) { // Date is 11/2/2016
			ans = true;
		} else {
			int numEntries = 0;
			
			for (int j = i; j < splitInput.length; j++) {
				if (numEntries > 2) {
					break;
				} else {
					String splitWord = splitInput[j]; // splitWord is "feb" of
														// "feb 2015"
					if (!isNumber(splitWord) && isMonth(splitWord)) {
						ans = true;
						break;
					}
				}
				numEntries++;
			}
		}
		return ans;
	}

	private boolean isMonth(String nextWord) {
		boolean ans = false;
		for (int i = 1; i < NUMBER_OF_MONTHS_IN_A_YEAR; i++) {
			if (nextWord.contains(monthsInYear[i])) {
				ans = true;
				break;
			}
		}
		return ans;
	}

	private boolean isNumber(String currWord) {
		return currWord.matches("[0-9]+");
	}

	private boolean isToday(String currWord) {
		return currWord.equals("today") || currWord.equals("tdy");
	}

	private int convertMonthStrToInt(String monthInput) {
		int monthInt = UNINITIALIZED_INT;
		for (int j = 1; j < NUMBER_OF_MONTHS_IN_A_YEAR; j++) {
			if (monthInput.contains(monthsInYear[j])) {
				monthInt = j;
				break;
			}
		}
		return monthInt;
	}

	private int getNextDayInt() {
		int temp = currDayInWeekInt + NEXT_DAY;
		temp %= NUMBER_OF_DAYS_IN_WEEK ;
		return temp;
	}

	private boolean hasPassed(int currTime2, int time, int[] date) {
		return currTime2 > time && date[DATE_INDEX_OF_DD] == currDD && date[DATE_INDEX_OF_MM] == currMM
				&& date[DATE_INDEX_OF_YY] == currYY && time != UNINITIALIZED_INT;
	}

	// Input number of days to fastforward from today
	// Output the day of the week
	private int getDayOfWeekAfterANumberOfDays(int daysLater) {
		int dayOfWeek = daysLater + currDayInWeekInt;
		dayOfWeek %= NUMBER_OF_DAYS_IN_WEEK ;
		if (dayOfWeek == 0) {
			dayOfWeek = NUMBER_OF_DAYS_IN_WEEK;;
		}
		return dayOfWeek;
	}

	// Input desired day of the week in int
	// Output days to fastForward from today
	private int getFastForward(int desiredDay) {
		int fastForward = 0;
		if (currDayInWeekInt > desiredDay) {
			fastForward = NUMBER_OF_DAYS_IN_WEEK - currDayInWeekInt + desiredDay;
		} else if (currDayInWeekInt < desiredDay) {
			fastForward = desiredDay - currDayInWeekInt;
		} else { // if(currDay == day)
			fastForward = 0;
		}
		return fastForward;
	}

	private int[] getDateAfterANumberOfDays(int fastForward) {
		int newDay = currDD + fastForward;
		int newMonth = currMM;
		int newYear = currYY;

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

	private int[] getTimeFromUserInput(String[] splitInput, int index, int[] combined) throws IncorrectInputException {
		String timeString = removeTimeSeparators(splitInput[index]);
		boolean isAm = timeString.contains(AM);
		boolean isPm = timeString.contains(PM);
		int timeInt = UNINITIALIZED_INT;
		boolean isExactlyMidnight = timeString.equals(TWELVE_MIDNIGHT_ONE_STRING);

		if (isAm && isPm) {
			throw new IncorrectInputException("\"" + timeString + "\"" + ERROR_MESSAGE_AM_AND_PM);
		}

		timeString = removeAmAndPmFromTimeString(timeString, isAm, isPm);
		timeInt = convertStringToInt(timeString);

		if (hasInvalidMinutes(timeInt)) {
			throw new IncorrectInputException(ERROR_MESSAGE_MINUTES_EXCEED_SIXTY);
		}

		timeInt = getTimeIntByAssumingOneWordTimeInput(timeString, isAm, isPm, timeInt, isExactlyMidnight);
		timeInt = getTimeIntByAssumingTwoWordsTimeInput(splitInput, index, timeInt);
		
		if (timeExceeds24H(timeInt)) {
			throw new IncorrectInputException(ERROR_MESSAGE_TIME_EXCEEDS_24H);
		}

		index = getNewAdvanceInt(splitInput, index);
		combined = updateTimeAndIndexInCombinedArray(index, combined, timeInt);
		
		return combined;
	}

	private int[] updateTimeAndIndexInCombinedArray(int index, int[] combined, int timeInt) {
		combined[COMBINED_INDEX_TIME] = timeInt;
		combined[COMBINED_INDEX_COUNTER] = index;
		return combined;
	}

	private int convertStringToInt(String timeString) {
		return Integer.parseInt(timeString);
	}

	private int getTimeIntByAssumingTwoWordsTimeInput(String[] splitInput, int index, int timeInt) {
		if (incrementByOne(index) < splitInput.length) {
			String indicator = splitInput[incrementByOne(index)];
			if (indicator.equals(PM)) {
				timeInt = timeInt + TWELVE_HOURS;
			}
			if (timeInt == FORMAT_24H_12PM && indicator.equals(AM)) {
				timeInt = 0;
			}
		}
		return timeInt;
	}

	private int getTimeIntByAssumingOneWordTimeInput(String timeString, boolean isAm, boolean isPm, int timeInt,
			boolean isExactlyMidnight) {
		if (isMidnight(timeInt, timeString, isAm) || isExactlyMidnight) {
			if (isExactlyMidnight) {
				timeInt = 0;
			} else {
				timeInt = getMinuteNumeralOfTime(timeInt);
			}
		} else if (is12HFormatTime(timeInt)) { // this is for the 12h format
			timeInt = convertTimeFrom12HTo24HFormat(timeInt);
		}

		if (isPm) {
			timeInt = timeInt + TWELVE_HOURS;
		}
		return timeInt;
	}

	private int convertTimeFrom12HTo24HFormat(int timeInt) {
		return timeInt * 100;
	}

	private boolean is12HFormatTime(int timeInt) {
		return timeInt <= 12;
	}

	private int getNewAdvanceInt(String[] splitInput, int index) {
		if (hasAWordAfterCurrWord(splitInput, index)) {
			if (splitInput[incrementByOne(index)].equals(PM) || splitInput[incrementByOne(index)].equals(AM)) {
				index += 1; // To skip the next word
			}
		}
		return index;
	}

	private boolean timeExceeds24H(int timeInt) {
		return timeInt >= 2400;
	}

	private boolean hasInvalidMinutes(int timeInt) {
		return getMinuteNumeralOfTime(timeInt) > 59;
	}

	private String removeAmAndPmFromTimeString(String timeString, boolean isAm, boolean isPm) {
		if (isAm ^ isPm) {
			timeString = timeString.replace(AM, EMPTY_STRING).trim();
			timeString = timeString.replace(PM, EMPTY_STRING).trim();
		}
		return timeString;
	}

	private int getMinuteNumeralOfTime(int timeInt) {
		return timeInt % 100;
	}

	private boolean isMidnight(int timeInt, String timeString, boolean isAm) {
		boolean isMidnight24H = timeInt >= 0 && timeInt <= 59 && timeString.contains("00");
		boolean isMidnight12H = timeInt >= 1200 && timeInt <= 1259 && isAm;
		return isMidnight24H || isMidnight12H;
	}

	private int convertDayStringToInt(String string) {
		int day = UNINITIALIZED_INT;
		for (int i = 0; i < daysInWeek.length; i++) {
			if (string.contains(daysInWeek[i])) {
				day = i;
				break;
			}
		}
		return day;
	}
}
