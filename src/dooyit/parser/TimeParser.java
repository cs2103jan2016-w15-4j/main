//@@author A0133338J
package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;

public class TimeParser implements DateTimeParserCommons {
	private static final String ERROR_MESSAGE_INVALID_TIME = "Invalid time!";
	private static final String ERROR_MESSAGE_INVALID_HOURS_OR_MINUTES = "Invalid Time! Hours must fall within the 24h range and Minutes must be between 0 to 59 inclusive";
	private static final String ERROR_MESSAGE_TIME_EXCEEDS_24H = "Invalid Time! Time must not exceed 24 hours!";
	private static final String ERROR_MESSAGE_AM_AND_PM = "Time cannot be contain both am and pm!";
	private static final String TWELVE_MIDNIGHT_ONE_STRING = "12am";
	private static final int TWELVE_HOURS = 1200;
	private static final int TWENTY_FOUR_HOURS = 2400;
	private static final int FORMAT_24H_1259PM = 1259;
	private static final int FORMAT_24H_12PM = 1200;
	private static final int FORMAT_24H_1PM = 1300;
	private static final int FORMAT_24H_1259AM = 59;
	private static final int FORMAT_24H_12AM = 0;
	
	public TimeParser() {
		
	}
	
	public int[] parse(String[] splitInput, int[] combined, int i) throws IncorrectInputException {
		try {
			combined = getTimeFromUserInput(splitInput, i, combined);
		} catch (IncorrectInputException e) {
			throw e;
		}
		return combined;
	}
	
	public boolean isValidTime(String currWord, String[] userInput, int index) {
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
			
		} else if (ParserCommons.isNumber(removeTimeSeparators(currWord)) && DateTimeParserCommons.hasAWordAfterCurrWord(userInput, index)) { // Eg.9.30 pm, 9
			String nextWord = userInput[index + 1];
			if (nextWord.equals(AM) ^ nextWord.equals(PM)) {
				ans = true;
			}
		}  else {
			ans = false;
		}
		return ans;
	}

	private String removeTimeSeparators(String currWord) {
		return currWord.replace(TIME_SEPARATOR_COLON, EMPTY_STRING).replace(TIME_SEPARATOR_DOT, EMPTY_STRING);
	}
	
	private int[] getTimeFromUserInput(String[] splitInput, int index, int[] combined) throws IncorrectInputException {
		String timeString = removeTimeSeparators(splitInput[index]);
		boolean isAm = timeString.contains(AM);
		boolean isPm = timeString.contains(PM);
		int timeInt = UNINITIALIZED_INT;
		boolean isExactlyMidnight = timeString.equals(TWELVE_MIDNIGHT_ONE_STRING);

		if (isAm && isPm) {
			throw new IncorrectInputException(ERROR_MESSAGE_AM_AND_PM);
		}

		timeString = removeAmAndPmFromTimeString(timeString, isAm, isPm);
		if(!ParserCommons.isNumber(timeString)) {
			throw new IncorrectInputException(ERROR_MESSAGE_INVALID_TIME);
		} else {
			timeInt = Integer.parseInt(timeString);
		} 
		
		if(hasInvalidHoursOrMinutes(timeInt, splitInput, index, isAm, isPm)) {
			throw new IncorrectInputException(ERROR_MESSAGE_INVALID_HOURS_OR_MINUTES);
		}

		timeInt = getTimeIntByAssumingOneWordTimeInput(timeString, isAm, isPm, timeInt, isExactlyMidnight);
		timeInt = getTimeIntByAssumingTwoWordsTimeInput(splitInput, index, timeInt);
		
		if (timeExceeds24H(timeInt)) {
			throw new IncorrectInputException(ERROR_MESSAGE_TIME_EXCEEDS_24H);
		}

		index = getNewIndex(splitInput, index);
		combined = updateTimeAndIndexInCombinedArray(index, combined, timeInt);
		
		return combined;
	}
	
	private boolean hasInvalidHoursOrMinutes(int timeInt, String[] splitInput, int index, boolean isAm, boolean isPm) {
		return hasInvalidHours(timeInt, splitInput, index, isAm, isPm) || hasInvalidMinutes(timeInt);
	}
	
	private boolean hasInvalidHours(int timeInt, String[] splitInput, int index, boolean isAm, boolean isPm) {
		String nextWord;
		boolean isAmOrPm = false, ans = false;
		if(DateTimeParserCommons.hasAWordAfterCurrWord(splitInput, index)) {
			nextWord = splitInput[index + 1];
			isAmOrPm = nextWord.contains(PM) || nextWord.contains(AM);
		}
		
		if(isAmOrPm || isAm || isPm) {
			boolean isValidHour = timeInt >= 1 && timeInt <= 12;
			boolean hasOnlyTwoDigits = timeInt < 100;
			ans = !isValidHour && hasOnlyTwoDigits;
		}
		return ans;
	}

	private boolean timeExceeds24H(int timeInt) {
		return timeInt >= TWENTY_FOUR_HOURS;
	}

	private boolean hasInvalidMinutes(int timeInt) {
		int minutes = getMinuteNumeralOfTime(timeInt);
		return minutes > 59 || minutes < 0;
	}

	private String removeAmAndPmFromTimeString(String timeString, boolean isAm, boolean isPm) {
		if (isAm ^ isPm) {
			timeString = timeString.replace(AM, EMPTY_STRING).trim();
			timeString = timeString.replace(PM, EMPTY_STRING).trim();
		}
		return timeString;
	}
	
	private int getNewIndex(String[] splitInput, int index) {
		if (DateTimeParserCommons.hasAWordAfterCurrWord(splitInput, index)) {
			String nextWord = splitInput[index + 1];
			if (nextWord.equals(PM) || nextWord.equals(AM)) {
				index += 1; // To skip the next word
			}
		}
		return index;
	}
	
	private int getTimeIntByAssumingTwoWordsTimeInput(String[] splitInput, int index, int timeInt) {
		index += 1;
		if (index < splitInput.length) {
			String indicator = splitInput[index];
			if (indicator.equals(PM) && !timeIsBetween1200And1259Inclusive(timeInt)) {
				timeInt += TWELVE_HOURS;
			}
			if (timeIsBetween1200And1259Inclusive(timeInt) && indicator.equals(AM)) {
				timeInt -= TWELVE_HOURS;
			}
		}
		return timeInt;
	}

	private int getTimeIntByAssumingOneWordTimeInput(String timeString, boolean isAm, boolean isPm, int timeInt, boolean isExactlyMidnight) {
		if (isMidnight(timeInt, timeString, isAm) || isExactlyMidnight) {
			if (isExactlyMidnight) {
				timeInt = FORMAT_24H_12AM;
			} else {
				timeInt = getMinuteNumeralOfTime(timeInt);
			}
		} else if (is12HFormatTime(timeInt)) { // this is for the 12h format
			timeInt = convertTimeFrom12HTo24HFormat(timeInt);
		}

		if (isPm) {
			if(!timeIsBetween1200And1259Inclusive(timeInt)) {
				timeInt = timeInt + TWELVE_HOURS;
			}
		}
		return timeInt;
	}

	private boolean timeIsBetween1200And1259Inclusive(int timeInt) {
		return timeInt >= FORMAT_24H_12PM && timeInt < FORMAT_24H_1PM;
	}
	
	private int convertTimeFrom12HTo24HFormat(int timeInt) {
		return timeInt * 100;
	}

	private boolean is12HFormatTime(int timeInt) {
		return timeInt <= 12;
	}
	
	private int[] updateTimeAndIndexInCombinedArray(int index, int[] combined, int timeInt) {
		combined[COMBINED_INDEX_TIME] = timeInt;
		combined[COMBINED_INDEX_COUNTER] = index;
		return combined;
	}
	
	private int getMinuteNumeralOfTime(int timeInt) {
		return timeInt % 100;
	}

	private boolean isMidnight(int timeInt, String timeString, boolean isAm) {
		boolean isMidnight24H = timeInt >= FORMAT_24H_12AM && timeInt <= FORMAT_24H_1259AM && timeString.contains("00");
		boolean isMidnight12H = timeInt >= FORMAT_24H_12PM && timeInt <= FORMAT_24H_1259PM && isAm;
		return isMidnight24H || isMidnight12H;
	}

}
