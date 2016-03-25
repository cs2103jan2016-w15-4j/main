package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;

public class TimeParser implements DateTimeParserCommon {
	private static final String ERROR_MESSAGE_MINUTES_EXCEED_SIXTY = "Invalid time! Minutes must be smaller than 60";
	private static final String ERROR_MESSAGE_TIME_EXCEEDS_24H = "Invalid Time! Time must not exceed 24 hours!";
	private static final String ERROR_MESSAGE_AM_AND_PM = " cannot be am and pm!";
	private static final String TWELVE_MIDNIGHT_ONE_STRING = "12am";
	private static final int FORMAT_24H_12PM = 1200;
	private static final int TWELVE_HOURS = 1200;
	
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
			if (isNumber(currWord)) {
				ans = true;
			}
			
		} else if (isNumber(removeTimeSeparators(currWord)) && hasAWordAfterCurrWord(userInput, index)) { // Eg.9.30 pm, 9
			String nextWord = userInput[incrementByOne(index)];
			if (nextWord.equals(AM) ^ nextWord.equals(PM)) {
				ans = true;
			}
		}  else {
			// Invalid command here
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
	
	private int getNewAdvanceInt(String[] splitInput, int index) {
		if (hasAWordAfterCurrWord(splitInput, index)) {
			String nextWord = splitInput[incrementByOne(index)];
			if (nextWord.equals(PM) || nextWord.equals(AM)) {
				index += 1; // To skip the next word
			}
		}
		return index;
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

	private int getTimeIntByAssumingOneWordTimeInput(String timeString, boolean isAm, boolean isPm, int timeInt, boolean isExactlyMidnight) {
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
	
	private int[] updateTimeAndIndexInCombinedArray(int index, int[] combined, int timeInt) {
		combined[COMBINED_INDEX_TIME] = timeInt;
		combined[COMBINED_INDEX_COUNTER] = index;
		return combined;
	}
	
	private int getMinuteNumeralOfTime(int timeInt) {
		return timeInt % 100;
	}

	private boolean isMidnight(int timeInt, String timeString, boolean isAm) {
		boolean isMidnight24H = timeInt >= 0 && timeInt <= 59 && timeString.contains("00");
		boolean isMidnight12H = timeInt >= 1200 && timeInt <= 1259 && isAm;
		return isMidnight24H || isMidnight12H;
	}

}
