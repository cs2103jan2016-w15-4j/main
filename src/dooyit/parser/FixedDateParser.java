package dooyit.parser;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;
import dooyit.parser.DateTimeParser.DATE_TIME_FORMAT;

public class FixedDateParser implements DateTimeParserCommon {
	private static final int YEARY_2000 = 2000;
	private static final String DATE_SEPARATOR = "/";
	private static final int DATE_INDEX_OF_ADVANCE_INT = 3;
	private static final int DEFAULT_DD_IN_MONTH = 15;
	
	private int[] daysInMonth;
	private int currMM;
	private int currYY;
	private int currDD;
	private int currDayInWeekInt;
	private String currDayInWeekString;
	
	public FixedDateParser(DateTime dateTime) {
		currDayInWeekString = dateTime.getDayStr();
		currDayInWeekInt = dateTime.getDayInt();
		currDD = dateTime.getDD();
		currMM = dateTime.getMM();
		currYY = dateTime.getYY();
		
		if (isLeapYear(currYY)) {
			daysInMonth = daysInMonthLeapYear;
		} else {
			daysInMonth = daysInMonthNonLeapYear;
		}
	}
	
	public boolean isFixedDate(String currWord, String[] splitUserInput, int index) {
		return isValidDate(currWord, splitUserInput, index);
	}

	public int[] parse(String[] splitInput, int[] combined, int i) throws IncorrectInputException {
		String currWord = splitInput[i];
		try {
			combined = getDate(splitInput, i, combined);
		} catch (IncorrectInputException e) {
			throw e;
		}
		return combined;
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
		int[] dateAndAdvanceIntArray= new int[] { UNINITIALIZED_INT, UNINITIALIZED_INT, UNINITIALIZED_INT, UNINITIALIZED_INT };
		int numEntries = UNINITIALIZED_INT;
		
		System.out.println("firstWord is " + firstWord);

		if (firstWord.contains(DATE_SEPARATOR)) {
			//firstWord is in the form of DD/MM or DD/MM/YY
			String[] userInputForDate = firstWord.split(DATE_SEPARATOR);
			for (int j = 0; j < userInputForDate.length; j++) {
				dateAndAdvanceIntArray[j] = convertStringToInt(userInputForDate[j]);
				if(dateAndAdvanceIntArray[j] <= 0) {
					throw new IncorrectInputException("Date Inputs must be greater than 0!");
				}
				System.out.println("dateAdvanceIntArray[" + j + "] is " + dateAndAdvanceIntArray[j]);
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
						 try {
							 dateAndAdvanceIntArray = setDayAndYearValues(dateAndAdvanceIntArray, numEntries, currInt);
						 } catch (IncorrectInputException e) {
							 throw e;
						 }
						 numEntries++;
					} else if (isMonth(currWord) && isUninitialized(dateAndAdvanceIntArray, DATE_INDEX_OF_MM)) {
						dateAndAdvanceIntArray[DATE_INDEX_OF_MM] = convertMonthStrToInt(currWord);
						numEntries++;
					} else {
						if(!isValidTime(currWord)) {
							throw new IncorrectInputException("Invalid date!");
						}
					}
				}
				counter++;
			}
		}
		
		dateAndAdvanceIntArray = setUninitializedValuesToDefault(dateAndAdvanceIntArray);

		if (isInvalidDate(dateAndAdvanceIntArray)) {
			throw new IncorrectInputException("Invalid date!");
		}

		dateAndAdvanceIntArray[DATE_INDEX_OF_ADVANCE_INT] = numEntries;
		return dateAndAdvanceIntArray;
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

	private int[] setDayAndYearValues(int[] dateAdvanceIntArray, int numEntries, int currInt) {
		if (currInt <= MAX_NUM_OF_DAYS_IN_A_MONTH && isUninitialized(dateAdvanceIntArray, DATE_INDEX_OF_DD)) {
			dateAdvanceIntArray[DATE_INDEX_OF_DD] = currInt;

		} else if (currInt >= YEARY_2000 && isUninitialized(dateAdvanceIntArray, DATE_INDEX_OF_YY)) {
			dateAdvanceIntArray[DATE_INDEX_OF_YY] = currInt;
			
		} else {
			throw new IncorrectInputException("Invalid date!");
		}
		return dateAdvanceIntArray;
	}

	private int[] setUninitializedValuesToDefault(int[] dateAdvanceIntArray) {
		if (isUninitialized(dateAdvanceIntArray, DATE_INDEX_OF_YY)) {
			dateAdvanceIntArray[DATE_INDEX_OF_YY] = getCorrectYear(dateAdvanceIntArray);
		}

		if (isUninitialized(dateAdvanceIntArray, DATE_INDEX_OF_DD)) {
			dateAdvanceIntArray[DATE_INDEX_OF_DD] = DEFAULT_DD_IN_MONTH; // Default
		}
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



	// Checks that the num of months does not exceed the max num of days in that month,
	// Eg. 30 Feb will return an error
	private boolean isInvalidDate(int[] ans) {
		int mm = ans[DATE_INDEX_OF_MM];
		return ans[DATE_INDEX_OF_DD] > daysInMonth[mm];
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

	private boolean isMonth(String currWord) {
		boolean ans = false;
		for (int i = STARTING_VALUE_OF_ARRAY_CONSTANTS; i < monthsInYear.length; i++) {
			if (currWord.contains(monthsInYear[i])) {
				ans = true;
				break;
			}
		}
		return ans;
	}

	private int convertMonthStrToInt(String monthInput) {
		int monthInt = UNINITIALIZED_INT;
		for (int j = 1; j < monthsInYear.length; j++) {
			if (monthInput.contains(monthsInYear[j])) {
				monthInt = j;
				break;
			}
		}
		return monthInt;
	}
}
