//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.exception.IncorrectInputException;

/**
 * TimeParser takes in a string and return the time in 24 hour integer format.
 * It implements DateTimeparserCommons and ParserCommons to use the shared 
 * methods and constants.
 * 
 * @author Annabel
 *
 */
public class TimeParser implements DateTimeParserCommons, ParserCommons {
	// Error messages
	private static final String ERROR_MESSAGE_INVALID_TIME = "Invalid time!";
	private static final String ERROR_MESSAGE_INVALID_HOURS_OR_MINUTES = "Invalid Time! Hours must fall within the 24h range and Minutes must be between 0 to 59 inclusive";
	private static final String ERROR_MESSAGE_TIME_EXCEEDS_24H = "Invalid Time! Time must not exceed 24 hours!";
	private static final String ERROR_MESSAGE_AM_AND_PM = "Time cannot be contain both am and pm!";
	
	// For checking if the time string is exactly midnight
	private static final String TWELVE_MIDNIGHT_ONE_STRING = "12am";
	
	// For converting 12h afternoon timings like 2.30pm from 230 to 1430
	private static final int TWELVE_HOURS = 1200;
	
	// For checking if 24h time int is equal to or greater than 24h
	private static final int TWENTY_FOUR_HOURS = 2400;
	
	// Constants for the isMidnight method
	private static final int FORMAT_24H_1259PM = 1259;
	private static final int FORMAT_24H_12PM = 1200;
	private static final int FORMAT_24H_1PM = 1300;
	private static final int FORMAT_24H_1259AM = 59;
	private static final int FORMAT_24H_12AM = 0;
	private static final String FORMAT_DOUBLE_ZEROES = "00";
	
	// Constants for the accepted range of minutes
	private static final int MINUTES_MINIMUM = 0;
	private static final int MINUTES_MAXIMUM = 59;

	// Logger for TimeParser
	private static Logger logger = Logger.getLogger("TimeParser");
	
	/** Initializes a TimeParser object */
	public TimeParser() {
		logger.log(Level.INFO, "Initialised TimeParser object");
	}
	
	/**
	 * Takes a valid time string from the user input and converts it into a time int.
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @param combined
	 *        An int array that contains the day int, time int, date (DD,
	 *        MM, YY) and the current index in the user input string array.
	 *        
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @return 24h time int of the valid time string in the user input.
	 * 
	 * @throws IncorrectInputException if the user input does not have a valid time string.
	 */
	public int[] parse(String[] splitUserInput, int[] combined, int index) throws IncorrectInputException {
		try {
			combined = getTimeFromUserInput(splitUserInput, index, combined);
		} catch (IncorrectInputException e) {
			throw e;
		}
		return combined;
	}
	
	/**
	 * Checks if the user input contains a valid time string.
	 * 
	 * @param currWord
	 * 		  The word of the current word in the user input string array
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @return true if user input has a valid time string and false otherwise.
	 */
	public boolean isValidTime(String currWord, String[] splitUserInput, int index) {
		boolean ans = false;
		if (currWord.contains(PM) || currWord.contains(AM)) {
			currWord = currWord.replace(PM, EMPTY_STRING).replace(AM, EMPTY_STRING);
			
			if (currWord.contains(TIME_SEPARATOR_COLON) || currWord.contains(TIME_SEPARATOR_DOT)) { // Eg: 12pm, 12.30pm, 12:50am
				currWord = removeTimeSeparators(currWord);
				ans = true;
			} else {
				ans = true;
			}

		} else if (currWord.contains(TIME_SEPARATOR_COLON)) { // 24Hour formats eg: 23:30
			currWord = removeTimeSeparators(currWord);
			if (ParserCommons.isNumber(currWord)) {
				ans = true;
			}
			
		} else if (ParserCommons.isNumber(removeTimeSeparators(currWord))
				&& DateTimeParserCommons.hasAWordAfterCurrWord(splitUserInput, index)) {
			String nextWord = splitUserInput[index + 1];
			if (nextWord.equals(AM) ^ nextWord.equals(PM)) {
				ans = true;
			}
		}  else {
			ans = false;
		}
		return ans;
	}

	/**
	 * Removes the recognized time separators ":" and "." from the word input.
	 * 
	 * @param currWord
	 * 		  The word of the current word in the user input string array
	 * 
	 * @return the word input without the time separators.
	 */
	private String removeTimeSeparators(String currWord) {
		return currWord.replace(TIME_SEPARATOR_COLON, EMPTY_STRING).replace(TIME_SEPARATOR_DOT, EMPTY_STRING);
	}
	
	/**
	 * Converts valid time string from user input into 24 time int and put the int into the 
	 * combined int array.
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @param combined
	 *        An int array that contains the day int, time int, date (DD,
	 *        MM, YY) and the current index in the user input string array.
	 *        
	 * @return combined int array with the time value updated.
	 * 
	 * @throws IncorrectInputException if the user input does not contain a valid time string.
	 */
	private int[] getTimeFromUserInput(String[] splitUserInput, int index, int[] combined) throws IncorrectInputException {
		String timeString = removeTimeSeparators(splitUserInput[index]);
		boolean isAm = timeString.contains(AM);
		boolean isPm = timeString.contains(PM);
		int timeInt = UNINITIALIZED_INT;
		boolean isExactlyMidnight = timeString.equals(TWELVE_MIDNIGHT_ONE_STRING);

		if (isAm && isPm) {
			throw new IncorrectInputException(ERROR_MESSAGE_AM_AND_PM);
		}

		timeString = removeAmAndPmFromTimeString(timeString, isAm, isPm);
		if (!ParserCommons.isNumber(timeString)) {
			throw new IncorrectInputException(ERROR_MESSAGE_INVALID_TIME);
		} else {
			timeInt = Integer.parseInt(timeString);
		} 
		
		if (hasInvalidHoursOrMinutes(timeInt, splitUserInput, index, isAm, isPm)) {
			throw new IncorrectInputException(ERROR_MESSAGE_INVALID_HOURS_OR_MINUTES);
		}

		timeInt = getTimeIntByAssumingOneWordTimeInput(timeString, isAm, isPm, timeInt, isExactlyMidnight);
		timeInt = getTimeIntByAssumingTwoWordsTimeInput(splitUserInput, index, timeInt);
		
		if (timeExceeds24H(timeInt)) {
			throw new IncorrectInputException(ERROR_MESSAGE_TIME_EXCEEDS_24H);
		}

		index = getNewArrayIndex(splitUserInput, index);
		combined = updateTimeAndIndexInCombinedArray(index, combined, timeInt);
		
		return combined;
	}
	
	/**
	 * Checks if the time int has invalid hour and/or minute value.
	 * 
	 * @param timeInt
	 * 		  Can be 12h (eg. 1100) or 24h (2300) format time integers.
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @param isAm
	 * 		  Boolean input that indicates if the time String contains the "am" time marker.
	 * 
	 * @param isPm
	 * 		  Boolean input that indicates if the time String contains the "pm" time marker.
	 * 
	 * @return true if the timeInt has invalid hour and/or minute value and false otherwise.
	 */
	private boolean hasInvalidHoursOrMinutes(int timeInt, String[] splitUserInput, int index, boolean isAm, boolean isPm) {
		return hasInvalidHours(timeInt, splitUserInput, index, isAm, isPm) || hasInvalidMinutes(timeInt);
	}
	
	/**
	 * Checks if the time int has invalid hour value like 100 or 25.
	 * 
	 * @param timeInt
	 * 		  Can be 12h (eg. 1100) or 24h (2300) format time integers.
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @param isAm
	 * 		  Boolean input that indicates if the time String contains the "am" time marker.
	 * 
	 * @param isPm
	 * 		  Boolean input that indicates if the time String contains the "pm" time marker.
	 * 
	 * @return true if the timeInt has invalid hour value and false otherwise.
	 */
	private boolean hasInvalidHours(int timeInt, String[] splitUserInput, int index, boolean isAm, boolean isPm) {
		String nextWord;
		boolean isAmOrPm = false, ans = false;
		if (DateTimeParserCommons.hasAWordAfterCurrWord(splitUserInput, index)) {
			nextWord = splitUserInput[index + 1];
			isAmOrPm = nextWord.contains(PM) || nextWord.contains(AM);
		}
		
		if (isAmOrPm || isAm || isPm) {
			boolean isValidHour = timeInt >= 1 && timeInt <= 12;
			boolean hasOnlyTwoDigits = timeInt < 100;
			ans = !isValidHour && hasOnlyTwoDigits;
		}
		return ans;
	}

	/**
	 * Checks if hour value exceeds or is equals to 24.
	 * 
	 * @param timeInt
	 * 		  Time string converted to 24h format int, For eg. 12.00am is 0 and 11.59pm is 2359.
	 * 
	 * @return true if the hour is equal to or greater than 24 and false otherwise.
	 */
	private boolean timeExceeds24H(int timeInt) {
		return timeInt >= TWENTY_FOUR_HOURS;
	}

	/**
	 * Check if the minute value of the time int is within the acceptable range for 
	 * minutes.
	 * 
	 * @param timeInt
	 * 		  Time string converted to 24h format int, For eg. 12.00am is 0 and 11.59pm is 2359.
	 * 
	 * @return true if the minute value is within the range and false otherwise.
	 */
	private boolean hasInvalidMinutes(int timeInt) {
		int minutes = getMinuteNumeralOfTime(timeInt);
		return minutes > MINUTES_MAXIMUM || minutes < MINUTES_MINIMUM;
	}

	/**
	 * Removes "am" and "pm" marker from the timeString.
	 * 
	 * @param timeString
	 *   	  The time string from the user input.
	 *   
	 * @param isAm
	 * 		  Boolean input that indicates if the time String contained the "am" time marker.
	 * 
	 * @param isPm
	 * 		  Boolean input that indicates if the time String contained the "pm" time marker.
	 * 
	 * @return the current word without the "am" and "pm" time string.
	 */
	private String removeAmAndPmFromTimeString(String timeString, boolean isAm, boolean isPm) {
		if (isAm ^ isPm) {
			timeString = timeString.replace(AM, EMPTY_STRING).trim();
			timeString = timeString.replace(PM, EMPTY_STRING).trim();
		}
		return timeString;
	}
	
	/**
	 * Checks if there is a need to increase the array index by 1. If the next word 
	 * if am or pm, the array index will be incremented, else it won't be increased.
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @return the correct array index.
	 */
	private int getNewArrayIndex(String[] splitUserInput, int index) {
		if (DateTimeParserCommons.hasAWordAfterCurrWord(splitUserInput, index)) {
			String nextWord = splitUserInput[index + 1];
			if (nextWord.equals(PM) || nextWord.equals(AM)) {
				index += 1; // To skip the next word
			}
		}
		return index;
	}
	
	/**
	 * Converts time string from the user into 24h time int. This is for time string of the
	 * format "12.20 pm" or "8:15 am". Note the spacing before the time markers "am" and "pm"
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @param timeInt
	 * 		  Time int in 12h format, 12pm and 12am are both 1200
	 * 
	 * @return 24h time int
	 */
	private int getTimeIntByAssumingTwoWordsTimeInput(String[] splitUserInput, int index, int timeInt) {
		index += 1;
		if (index < splitUserInput.length) {
			String indicator = splitUserInput[index];
			if (indicator.equals(PM) && !timeIsBetween1200And1259Inclusive(timeInt)) {
				timeInt += TWELVE_HOURS;
			}
			if (timeIsBetween1200And1259Inclusive(timeInt) && indicator.equals(AM)) {
				timeInt -= TWELVE_HOURS;
			}
		}
		return timeInt;
	}
	
	/**
	 * Converts time string from the user into 24h time int. This is for time string of the
	 * format "12.20pm" or "8:15am".
	 * 
	 * @param timeString
	 *   	  The time string from the user input.
	 *   
	 * @param isAm
	 * 		  Boolean input that indicates if the time String contains the "am" time marker.
	 * 
	 * @param isPm
	 * 		  Boolean input that indicates if the time String contains the "pm" time marker.
	 * 
	 * @param timeInt
	 * 		  Can be either 12h or 24 time format.
	 * 
	 * @param isExactlyMidnight
	 *        Boolean input that indicates if the time string from the user input is exactly 12 midnight.
	 *        
	 * @return 24h format time int.
	 */
	private int getTimeIntByAssumingOneWordTimeInput(String timeString, boolean isAm, boolean isPm, int timeInt, boolean isExactlyMidnight) {
		if (isMidnight(timeInt, timeString, isAm) || isExactlyMidnight) {
			if (isExactlyMidnight) {
				timeInt = FORMAT_24H_12AM;
			} else {
				timeInt = getMinuteNumeralOfTime(timeInt);
			}
		} else if (is12HNoMinutesFormatTime(timeInt)) { // Eg 12am or 1pm
			timeInt = convertTimeHourFrom12HTo24HFormat(timeInt);
		}

		if (isPm) {
			if(!timeIsBetween1200And1259Inclusive(timeInt)) {
				// Convert time int from eg 100 to 1300 for 1pm
				timeInt = timeInt + TWELVE_HOURS;
			}
		}
		return timeInt;
	}

	/**
	 * Checks if the time int is between 12pm and 11.59pm.
	 * 
	 * @param timeInt
	 * 		  Can be in 12h or 24h time format.
	 * 
	 * @return true if time int is within the stated range and false otherwise.
	 */
	private boolean timeIsBetween1200And1259Inclusive(int timeInt) {
		return timeInt >= FORMAT_24H_12PM && timeInt < FORMAT_24H_1PM;
	}
	
	/**
	 * Converts hour value time int from 12h format to 24h format. 
	 * Eg. "1" for 1pm and 1am is converted to "100" format.
	 * 
	 * @param timeInt
	 * 		  Hour value of time int in 12h format.
	 * 
	 * @return hour value in 24h format
	 */
	private int convertTimeHourFrom12HTo24HFormat(int timeInt) {
		return timeInt * 100;
	}

	/**
	 * Checks if the time int is of the format 12am or 1pm or 2pm, eg no minutes.
	 * 
	 * @param timeInt
	 * 		  Hour value of time int converted from time string.
	 * 
	 * @return true if it the stated format and false otherwise.
	 */
	private boolean is12HNoMinutesFormatTime(int timeInt) {
		return timeInt <= 12;
	}
	
	/**
	 * Updates the time and counter fields in the combined int array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 * 
	 * @param combined
	 *        An int array that contains the day int, time int, date (DD,
	 *        MM, YY) and the current index in the user input string array.
	 *        
	 * @param timeInt
	 * 		  Time string in 24h format int, For eg. 12.00am is 0 and 11.59pm is 2359.
	 * 
	 * @return combined int array with time and counter fields updated.
	 */
	private int[] updateTimeAndIndexInCombinedArray(int index, int[] combined, int timeInt) {
		combined[COMBINED_INDEX_TIME] = timeInt;
		combined[COMBINED_INDEX_COUNTER] = index;
		return combined;
	}
	
	/**
	 * Gets the minute numeral from the 24h time int.
	 * 
	 * @param timeInt
	 * 		  Time string converted 24h format int, For eg. 12.00am is 0 and 11.59pm is 2359.
	 * 
	 * @return the minute numeral of the time int.
	 */
	private int getMinuteNumeralOfTime(int timeInt) {
		return timeInt % 100;
	}

	/**
	 * Checks if the time string is after 12am and before 1am 
	 * 
	 * @param timeInt
	 * 		  Time string in 24h format int, For eg. 12.00am is 0 and 11.59pm is 2359.
	 * 
	 * @param timeString
	 *   	  The time string from the user input.
	 *   
	 * @param isAm
	 * 		  Boolean input that indicates if the time String contained the "am" time marker
	 * 
	 * @return true if the timeString is between 12.00am to 12.59am and false otherwise.
	 */
	private boolean isMidnight(int timeInt, String timeString, boolean isAm) {
		boolean isMidnight24H = timeInt >= FORMAT_24H_12AM && timeInt <= FORMAT_24H_1259AM && timeString.contains(FORMAT_DOUBLE_ZEROES);
		boolean isMidnight12H = timeInt >= FORMAT_24H_12PM && timeInt <= FORMAT_24H_1259PM && isAm;
		return isMidnight24H || isMidnight12H;
	}

}
