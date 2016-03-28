package dooyit.parser;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;

public class RelativeDateParser implements DateTimeParserCommon {
	private static final String ERROR_MESSAGE_INVALID_DATE_INPUT = "Error: Invalid date input!";
	private static String[] validWordForDay = new String[] { "day", "days", "dd" };
	private static String[] validWordForWeek = new String[] { "week", "weeks", "wk" };
	private static String[] validWordForToday = new String[] { "today", "tdy"};
	private static String[] validWordForTomorrow = new String[] { "tomorrow", "tmr"};
	private static String[] daysInWeekShortForm = new String[] { EMPTY_STRING, "mon", "tue", "wed", "thu", "fri", "sat", "sun" };
	private static final String THIS = "this";
	private static final String NEXT = "next";

	private int currMM;
	private int currYY;
	private int currDD;
	private int currDayInWeekInt;
	private String currDayInWeekString;
	
	enum RELATIVE_DATE_FORMAT {
		TYPE_THIS_DAY_OF_WEEK, TYPE_NEXT_DAY_OF_WEEK, TYPE_DAY_OF_WEEK, TYPE_NEXT_WEEK, TYPE_NUM_DAYS, TYPE_NUM_WEEKS, TYPE_TODAY, TYPE_TOMORROW, TYPE_INVALID
	};
	
	public RelativeDateParser(DateTime dateTime) {
		currDayInWeekString = dateTime.getDayStr();
		currDayInWeekInt = dateTime.getDayInt();
		currDD = dateTime.getDD();
		currMM = dateTime.getMM();
		currYY = dateTime.getYY();
		
	}
	
	public int[] parse(String[] splitInput, int[] combined, int i) throws IncorrectInputException {
		
		String currWord = splitInput[i];
		switch (getRelativeDateType(currWord, splitInput, i)) {
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
			combined = getDateAndDayAfterANumberOfDays(splitInput, i, combined);
			break;

		case TYPE_NUM_WEEKS:
			combined = getDateAndDayAfterNumberOfWeeks(splitInput, i, combined);
			break;

		case TYPE_TODAY:
			combined = getCombinedArrayForToday(combined);
			break;

		case TYPE_TOMORROW:
			combined = getCombinedArrayForTomorrow(combined);
			break;

		default:
			combined[COMBINED_INDEX_COUNTER] += 1;
			throw new IncorrectInputException(ERROR_MESSAGE_INVALID_DATE_INPUT);
		}
			
		return combined;
	}
	
	private RELATIVE_DATE_FORMAT getRelativeDateType(String currWord, String[] splitUserInput, int index) {
		if (isThisMonday(currWord, splitUserInput, index)) {
			return RELATIVE_DATE_FORMAT.TYPE_THIS_DAY_OF_WEEK;

		} else if (isNextWeek(currWord, splitUserInput, index)) {
			return RELATIVE_DATE_FORMAT.TYPE_NEXT_WEEK;

		} else if (isNextWeekday(currWord, splitUserInput, index)) {
			return RELATIVE_DATE_FORMAT.TYPE_NEXT_DAY_OF_WEEK;

		} else if (isValidDay(currWord)) {
			return RELATIVE_DATE_FORMAT.TYPE_DAY_OF_WEEK;

		} else if (isToday(currWord)) {
			return RELATIVE_DATE_FORMAT.TYPE_TODAY;

		} else if (isTomorrow(currWord)) {
			return RELATIVE_DATE_FORMAT.TYPE_TOMORROW;

		} else if (isNumberOfDays(currWord, splitUserInput, index)) {
			return RELATIVE_DATE_FORMAT.TYPE_NUM_DAYS;

		} else if (isNumberOfWeeks(currWord, splitUserInput, index)) {
			return RELATIVE_DATE_FORMAT.TYPE_NUM_WEEKS;

		} else {
			return RELATIVE_DATE_FORMAT.TYPE_INVALID;
		}
	}
	
	public boolean isRelativeDate(String currWord, String[] splitUserInput, int index) {
		return getRelativeDateType(currWord, splitUserInput, index) != RELATIVE_DATE_FORMAT.TYPE_INVALID;
	}

	
	private boolean isNumberOfDays(String currWord, String[] splitUserInput, int index) {
		boolean ans = false;
		if (isNumber(currWord) && hasAWordAfterCurrWord(splitUserInput, index)) {
			String nextWord = splitUserInput[incrementByOne(index)];
			ans = checkIfWordIsInArray(nextWord, validWordForDay);
		}
		return ans;
	}

	private boolean isNumberOfWeeks(String currWord, String[] arr, int index) {
		boolean ans = false;
		if (isNumber(currWord) && hasAWordAfterCurrWord(arr, index)) {
			String nextWord = arr[incrementByOne(index)];
			ans = checkIfWordIsInArray(nextWord, validWordForWeek);
		}
		return ans;
	}
	
	private boolean isTomorrow(String currWord) {
		return checkIfWordIsInArray(currWord, validWordForTomorrow);
	}

	private boolean isToday(String currWord) {
		return checkIfWordIsInArray(currWord, validWordForToday);
	}
	
	private boolean checkIfWordIsInArray(String currWord, String[] wordArray) {
		boolean ans = false;
		for(int i = 0; i < wordArray.length; i++) {
			if(currWord.equals(wordArray[i])) {
				ans = true;
				break;
			}
		}
		return ans;
	}
	
	private boolean isNextWeekday(String currWord, String[] userInput, int index) {
		boolean ans = false;
		if (currWord.equals(NEXT) && hasAWordAfterCurrWord(userInput, index)) { 
			ans = isValidDay(userInput[incrementByOne(index)]);
		}
		return ans;
	}

	private boolean isNextWeek(String currentWord, String[] userInput, int index) {
		boolean ans = false;
		if (currentWord.equals(NEXT) && hasAWordAfterCurrWord(userInput, index)) {
			int indexOfNextWord = incrementByOne(index);
			String nextWord = userInput[indexOfNextWord];
			ans = checkIfWordIsInArray(nextWord, validWordForWeek);
		}
		return ans;
	}
	
	// Eg. 2 weeks later
	private int[] getDateAndDayAfterNumberOfWeeks(String[] splitInput, int index, int[] combined) {
		int numWeeksLater = convertStringToInt(splitInput[index]);
		int day = currDayInWeekInt;
		int fastForward = getFastForwardFromDayOfWeek(day) + NUMBER_OF_DAYS_IN_WEEK * numWeeksLater;
		int[] date = getDateAfterANumberOfDays(fastForward, currDD, currMM, currYY);
		int[] ans = getNewCombinedArray(combined, date, day, incrementByOne(index));
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
	
	private int[] getCombinedArrayForTomorrow(int[] combined) {
		int day = getNextDayInt(currDayInWeekInt);
		int[] date = getDateAfterANumberOfDays(NEXT_DAY, currDD, currMM, currYY);
		int[] ans = getNewCombinedArray(combined, date, day);
		return ans;
	}
	
	private int[] getCombinedArrayForToday(int[] combined) {
		return combined;
	}
	
	// Eg. 2 days
	private int[] getDateAndDayAfterANumberOfDays(String[] splitInput, int index, int[] combined) {
		int numDaysLater = convertStringToInt(splitInput[index]);
		int day = getDayOfWeekAfterANumberOfDays(numDaysLater);
		int[] date = getDateAfterANumberOfDays(numDaysLater, currDD, currMM, currYY);
		return getNewCombinedArray(combined, date, day, incrementByOne(index));
	}

	// Eg. next week
	private int[] getDateAfterOneWeek(String[] splitInput, int index, int[] combined) {
		int day = currDayInWeekInt;
		int fastForward = getFastForwardFromDayOfWeek(day) + NUMBER_OF_DAYS_IN_WEEK;
		int[] date = getDateAfterANumberOfDays(fastForward, currDD, currMM, currYY);
		return getNewCombinedArray(combined, date, day, incrementByOne(index));
	}

	// Eg. monday, wed
	private int[] getDayOfWeek(String[] splitInput, int index, int[] combined) {
		int day = convertDayStringToInt(splitInput[index]);
		int fastForward = getFastForwardFromDayOfWeek(day);
		int[] date = getDateAfterANumberOfDays(fastForward, currDD, currMM, currYY);
		return getNewCombinedArray(combined, date, day, index);
	}

	// Eg. next monday, next wed
	private int[] getNextDayOfWeek(String[] splitInput, int index, int[] combined) {
		int day = convertDayStringToInt(splitInput[incrementByOne(index)]);
		int fastForward = getFastForwardFromDayOfWeek(day) + NUMBER_OF_DAYS_IN_WEEK;
		int[] date = getDateAfterANumberOfDays(fastForward, currDD, currMM, currYY);
		return getNewCombinedArray(combined, date, day, incrementByOne(index));
	}

	// Eg. this monday, this wed, this wednesday
	private int[] getThisDayOfWeek(String[] splitInput, int index, int[] combined) {
		// splitInput[i].equals("this")
		int day = convertDayStringToInt(splitInput[incrementByOne(index)]);
		int fastForward = getFastForwardFromDayOfWeek(day);
		int[] date = getDateAfterANumberOfDays(fastForward, currDD, currMM, currYY);
		return getNewCombinedArray(combined, date, day, incrementByOne(index));
	}
	
	private int convertDayStringToInt(String string) {
		int day = UNINITIALIZED_INT;
		for (int i = STARTING_VALUE_OF_ARRAY_CONSTANTS; i < daysInWeekShortForm.length; i++) {
			if (string.contains(daysInWeekShortForm[i])) {
				day = i;
				break;
			}
		}
		return day;
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
	private int getFastForwardFromDayOfWeek(int desiredDay) {
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
	
	private boolean isValidDay(String currWord) {
		boolean ans = convertDayStringToInt(currWord) != -1;
		return ans;
	}
}
