//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;

/** 
 * 
 * @author Annabel
 *
 */
public class RelativeDateParser implements DateTimeParserCommons {
	private static final String ERROR_MESSAGE_INVALID_DATE_INPUT = "Invalid date input!";
	private static String[] validWordForDay = new String[] { "day", "days", "dd" };
	private static String[] validWordForWeek = new String[] { "week", "weeks", "wk" };
	private static String[] validWordForToday = new String[] { "today", "tdy"};
	private static String[] validWordForTomorrow = new String[] { "tomorrow", "tmr"};
	private static String[] daysInWeekShortForm = new String[] { EMPTY_STRING, "mon", "tue", "wed", "thu", "fri", "sat", "sun" };
	private static final String THIS = "this";
	private static final String NEXT = "next";

	// Logger for RelativeDateParser
	private static Logger logger = Logger.getLogger("RelativeDateParser");
	
	private int currMM;
	private int currYY;
	private int currDD;
	private int currDayInWeekInt;
	
	/**  */
	private enum RelativeDateFormat {
		TYPE_THIS_DAY_OF_WEEK, TYPE_NEXT_DAY_OF_WEEK, TYPE_DAY_OF_WEEK, 
		TYPE_NEXT_WEEK, TYPE_NUM_DAYS, TYPE_NUM_WEEKS, TYPE_TODAY, 
		TYPE_TOMORROW, TYPE_INVALID
	};
	
	/** 
	 * 
	 * @param dateTime
	 */
	public RelativeDateParser(DateTime dateTime) {
		currDayInWeekInt = dateTime.getDayInt();
		currDD = dateTime.getDD();
		currMM = dateTime.getMM();
		currYY = dateTime.getYY();
		
		logger.log(Level.INFO, "Initialised RelativeDateParser object");

	}
	
	/** 
	 * 
	 * @param splitInput
	 * @param combined
	 * @param i
	 * @return
	 * @throws IncorrectInputException
	 */
	public int[] parse(String[] splitInput, int[] combined, int i) throws IncorrectInputException {
		
		String currWord = splitInput[i];
		switch (getRelativeDateType(currWord, splitInput, i)) {
		case TYPE_THIS_DAY_OF_WEEK :
			combined = getThisDayOfWeek(splitInput, i, combined);
			break;

		case TYPE_NEXT_DAY_OF_WEEK :
			combined = getNextDayOfWeek(splitInput, i, combined);
			break;

		case TYPE_DAY_OF_WEEK :
			combined = getDayOfWeek(splitInput, i, combined);
			break;

		case TYPE_NEXT_WEEK :
			combined = getDateAfterOneWeek(splitInput, i, combined);
			break;

		case TYPE_NUM_DAYS : 
			combined = getDateAndDayAfterANumberOfDays(splitInput, i, combined);
			break;

		case TYPE_NUM_WEEKS :
			combined = getDateAndDayAfterNumberOfWeeks(splitInput, i, combined);
			break;

		case TYPE_TODAY :
			combined = getCombinedArrayForToday(combined);
			break;

		case TYPE_TOMORROW :
			combined = getCombinedArrayForTomorrow(combined);
			break;

		default :
			combined[COMBINED_INDEX_COUNTER] += 1;
			throw new IncorrectInputException(ERROR_MESSAGE_INVALID_DATE_INPUT);
		}
			
		return combined;
	}
	
	/** 
	 * 
	 * @param currWord
	 * @param splitUserInput
	 * @param index
	 * @return
	 */
	private RelativeDateFormat getRelativeDateType(String currWord, String[] splitUserInput, int index) {
		RelativeDateFormat type;
		if (isThisMonday(currWord, splitUserInput, index)) {
			type = RelativeDateFormat.TYPE_THIS_DAY_OF_WEEK;

		} else if (isNextWeek(currWord, splitUserInput, index)) {
			type = RelativeDateFormat.TYPE_NEXT_WEEK;

		} else if (isNextWeekday(currWord, splitUserInput, index)) {
			type = RelativeDateFormat.TYPE_NEXT_DAY_OF_WEEK;

		} else if (isValidDay(currWord)) {
			type = RelativeDateFormat.TYPE_DAY_OF_WEEK;

		} else if (isToday(currWord)) {
			type = RelativeDateFormat.TYPE_TODAY;

		} else if (isTomorrow(currWord)) {
			type = RelativeDateFormat.TYPE_TOMORROW;

		} else if (isNumberOfDays(currWord, splitUserInput, index)) {
			type = RelativeDateFormat.TYPE_NUM_DAYS;

		} else if (isNumberOfWeeks(currWord, splitUserInput, index)) {
			type = RelativeDateFormat.TYPE_NUM_WEEKS;

		} else {
			type = RelativeDateFormat.TYPE_INVALID;
		}
		return type;
	}
	
	/** 
	 * 
	 * @param currWord
	 * @param splitUserInput
	 * @param index
	 * @return
	 */
	public boolean isRelativeDate(String currWord, String[] splitUserInput, int index) {
		return getRelativeDateType(currWord, splitUserInput, index) != RelativeDateFormat.TYPE_INVALID;
	}

	/** 
	 * 
	 * @param currWord
	 * @param splitUserInput
	 * @param index
	 * @return
	 */
	private boolean isNumberOfDays(String currWord, String[] splitUserInput, int index) {
		boolean ans = false;
		if (ParserCommons.isNumber(currWord) && DateTimeParserCommons.hasAWordAfterCurrWord(splitUserInput, index)) {
			String nextWord = splitUserInput[index + 1];
			ans = checkIfWordIsInArray(nextWord, validWordForDay);
		}
		return ans;
	}

	/** 
	 * 
	 * @param currWord
	 * @param arr
	 * @param index
	 * @return
	 */
	private boolean isNumberOfWeeks(String currWord, String[] arr, int index) {
		boolean ans = false;
		if (ParserCommons.isNumber(currWord) && DateTimeParserCommons.hasAWordAfterCurrWord(arr, index)) {
			String nextWord = arr[index + 1];
			ans = checkIfWordIsInArray(nextWord, validWordForWeek);
		}
		return ans;
	}
	
	/** 
	 * 
	 * @param currWord
	 * @return
	 */
	private boolean isTomorrow(String currWord) {
		return checkIfWordIsInArray(currWord, validWordForTomorrow);
	}

	/** 
	 * 
	 * @param currWord
	 * @return
	 */
	private boolean isToday(String currWord) {
		return checkIfWordIsInArray(currWord, validWordForToday);
	}
	
	/** 
	 * 
	 * @param currWord
	 * @param wordArray
	 * @return
	 */
	private boolean checkIfWordIsInArray(String currWord, String[] wordArray) {
		boolean ans = false;
		for (int i = 0; i < wordArray.length; i++) {
			if (currWord.equals(wordArray[i])) {
				ans = true;
				break;
			}
		}
		return ans;
	}
	
	/** 
	 * 
	 * @param currWord
	 * @param userInput
	 * @param index
	 * @return
	 */
	private boolean isNextWeekday(String currWord, String[] userInput, int index) {
		boolean ans = false;
		if (currWord.equals(NEXT) && DateTimeParserCommons.hasAWordAfterCurrWord(userInput, index)) { 
			ans = isValidDay(userInput[index + 1]);
		}
		return ans;
	}

	/** 
	 * 
	 * @param currentWord
	 * @param userInput
	 * @param index
	 * @return
	 */
	private boolean isNextWeek(String currentWord, String[] userInput, int index) {
		boolean ans = false;
		if (currentWord.equals(NEXT) && DateTimeParserCommons.hasAWordAfterCurrWord(userInput, index)) {
			int indexOfNextWord = index + 1;
			String nextWord = userInput[indexOfNextWord];
			ans = checkIfWordIsInArray(nextWord, validWordForWeek);
		}
		return ans;
	}
	
	// Eg. 2 weeks later
	/** 
	 * 
	 * @param splitInput
	 * @param index
	 * @param combined
	 * @return
	 */
	private int[] getDateAndDayAfterNumberOfWeeks(String[] splitInput, int index, int[] combined) {
		int numWeeksLater = Integer.parseInt(splitInput[index]);
		int day = currDayInWeekInt;
		int fastForward = getFastForwardFromDayOfWeek(day) + NUMBER_OF_DAYS_IN_WEEK * numWeeksLater;
		int[] date = DateTimeParserCommons.getDateAfterANumberOfDays(fastForward, currDD, currMM, currYY);
		int[] ans = DateTimeParserCommons.getNewCombinedArray(combined, date, day, index + 1);
		return ans;
	}

	/** 
	 * 
	 * @param currentWord
	 * @param userInput
	 * @param index
	 * @return
	 */
	private boolean isThisMonday(String currentWord, String[] userInput, int index) {
		boolean ans = false;
		if (currentWord.equals(THIS) && DateTimeParserCommons.hasAWordAfterCurrWord(userInput, index)) { 
			int indexOfNextWord = index + 1;
			String nextWord = userInput[indexOfNextWord];
			ans = isValidDay(nextWord);
		}
		return ans;
	}
	
	/** 
	 * 
	 * @param combined
	 * @return
	 */
	private int[] getCombinedArrayForTomorrow(int[] combined) {
		int day = DateTimeParserCommons.getNextDayInt(currDayInWeekInt);
		int[] date = DateTimeParserCommons.getDateAfterANumberOfDays(NEXT_DAY, currDD, currMM, currYY);
		int[] ans = DateTimeParserCommons.getNewCombinedArray(combined, date, day);
		return ans;
	}
	
	/** 
	 * 
	 * @param combined
	 * @return
	 */
	private int[] getCombinedArrayForToday(int[] combined) {
		return combined;
	}
	
	/** 
	 * 
	 * @param splitInput
	 * @param index
	 * @param combined
	 * @return
	 */
	private int[] getDateAndDayAfterANumberOfDays(String[] splitInput, int index, int[] combined) {
		int numDaysLater = Integer.parseInt(splitInput[index]);
		int day = getDayOfWeekAfterANumberOfDays(numDaysLater);
		int[] date = DateTimeParserCommons.getDateAfterANumberOfDays(numDaysLater, currDD, currMM, currYY);
		return DateTimeParserCommons.getNewCombinedArray(combined, date, day, index + 1);
	}

	// Eg. next week
	/** 
	 * 
	 * @param splitInput
	 * @param index
	 * @param combined
	 * @return
	 */
	private int[] getDateAfterOneWeek(String[] splitInput, int index, int[] combined) {
		int day = currDayInWeekInt;
		int fastForward = getFastForwardFromDayOfWeek(day) + NUMBER_OF_DAYS_IN_WEEK;
		int[] date = DateTimeParserCommons.getDateAfterANumberOfDays(fastForward, currDD, currMM, currYY);
		return DateTimeParserCommons.getNewCombinedArray(combined, date, day, index + 1);
	}

	// Eg. monday, wed
	/** 
	 * 
	 * @param splitInput
	 * @param index
	 * @param combined
	 * @return
	 */
	private int[] getDayOfWeek(String[] splitInput, int index, int[] combined) {
		int day = convertDayStringToInt(splitInput[index]);
		int fastForward = getFastForwardFromDayOfWeek(day);
		int[] date = DateTimeParserCommons.getDateAfterANumberOfDays(fastForward, currDD, currMM, currYY);
		return DateTimeParserCommons.getNewCombinedArray(combined, date, day, index);
	}

	// Eg. next monday, next wed
	/** 
	 * 
	 * @param splitInput
	 * @param index
	 * @param combined
	 * @return
	 */
	private int[] getNextDayOfWeek(String[] splitInput, int index, int[] combined) {
		int day = convertDayStringToInt(splitInput[index + 1]);
		int fastForward = getFastForwardFromDayOfWeek(day) + NUMBER_OF_DAYS_IN_WEEK;
		int[] date = DateTimeParserCommons.getDateAfterANumberOfDays(fastForward, currDD, currMM, currYY);
		return DateTimeParserCommons.getNewCombinedArray(combined, date, day, index + 1);
	}

	// Eg. this monday, this wed, this wednesday
	/** 
	 * 
	 * @param splitInput
	 * @param index
	 * @param combined
	 * @return
	 */
	private int[] getThisDayOfWeek(String[] splitInput, int index, int[] combined) {
		// splitInput[i].equals("this")
		int day = convertDayStringToInt(splitInput[index + 1]);
		int fastForward = getFastForwardFromDayOfWeek(day);
		int[] date = DateTimeParserCommons.getDateAfterANumberOfDays(fastForward, currDD, currMM, currYY);
		return DateTimeParserCommons.getNewCombinedArray(combined, date, day, index + 1);
	}
	
	/** 
	 * 
	 * @param string
	 * @return
	 */
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
	/** 
	 * 
	 * @param daysLater
	 * @return
	 */
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
	/** 
	 * 
	 * @param desiredDay
	 * @return
	 */
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
	
	/** 
	 * 
	 * @param currWord
	 * @return
	 */
	private boolean isValidDay(String currWord) {
		boolean ans = convertDayStringToInt(currWord) != -1;
		return ans;
	}
}
