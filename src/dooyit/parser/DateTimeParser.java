package dooyit.parser;
import java.text.DateFormat;
import java.util.logging.*;


import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;

public class DateTimeParser {
	private static final int TWELVE_HOURS = 1200;
	private static final int NUM_DAYS_IN_WEEK = 7;
	private static final int NEXT_DAY = 1;
	
	private static final int COMBINED_INDEX_DAY_OF_WEEK = 0;
	private static final int COMBINED_INDEX_TIME = 1;
	private static final int COMBINED_INDEX_DD = 2;
	private static final int COMBINED_INDEX_MM = 3;
	private static final int COMBINED_INDEX_YY = 4;
	private static final int COMBINED_INDEX_COUNTER = 5;
	
	private static final int DATE_INDEX_DD = 0;
	private static final int DATE_INDEX_MM = 1;
	private static final int DATE_INDEX_YY = 2;
	
	private static final String DUMMY_STR = "Dummy_Str";
	private static final int DUMMY_INT = -1;
	private static final int DEFAULT_DD_IN_MONTH = 15;
	
	private static boolean isInvalidDateTime;
	DateFormat dateFormat;
	
	// Can just look for substrings
	String[] daysInWeek = new String[]{DUMMY_STR, "mon", "tue", "wed", "thur", "fri", "sat", "sun"};
	String[] monthsInYear = new String[]{DUMMY_STR, "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};

	int LEAP_YEAR_FEB = 29;
	public static int[] daysInMonth = new int[]{DUMMY_INT, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	String THIS = "this";
	String NEXT = "next";
	
	int currMM;
	int currYY;
	int currDD;
	int currDayInWeekInt;
	
	String currDayInWeekString;
	int currTime;
	
	private static Logger logger = Logger.getLogger("DateTimeParser");
	
	enum DATE_TIME_FORMAT {
		TYPE_THIS_DAY_OF_WEEK, TYPE_NEXT_DAY_OF_WEEK, TYPE_DAY_OF_WEEK, 
		TYPE_NEXT_WEEK, TYPE_NUM_DAYS, TYPE_NUM_WEEKS,
		TYPE_TODAY, TYPE_TOMORROW, TYPE_DATE, TYPE_TIME,
		TYPE_INVALID
	};
	
	public DateTimeParser() {
		DateTime dt = new DateTime();
		currTime = dt.getTimeInt();
		currDayInWeekString = dt.getDayStr();
		currDayInWeekInt = dt.getDayInt();
		currDD = dt.getDD();
		currMM = dt.getMM();
		currYY = dt.getYY();
		
		if(isLeapYear(currYY)) {
			daysInMonth[2] = LEAP_YEAR_FEB;
		}
	}

	private boolean isLeapYear(int currYear) {
		int[] arrLeapYears = new int[]{2016, 2020, 2024, 2028, 2032, 2036, 2040, 2044, 2048, 2052, 
				   2056, 2060, 2064, 2068, 2072, 2076, 2080, 2084, 2088, 2092, 2096};
		
		boolean ans = false;
		for(int i = 0; i < arrLeapYears.length; i++) {
			if(currYear < arrLeapYears[i]) {
				break;
			}
			if(currYear == arrLeapYears[i]) {
				ans = true;
				break;
			}
		}
		return ans;
	}
	
	private DATE_TIME_FORMAT getDateTimeType(String currWord, String[] arr, int index) {
		System.out.println("currWord is " + currWord);
		if(isThisMonday(currWord, arr, index)) {
			//System.out.println("isThisMonday");
			logger.log(Level.INFO, "is this Monday");
			return DATE_TIME_FORMAT.TYPE_THIS_DAY_OF_WEEK;
			
		} else if(isNextWeek(currWord, arr, index)) {
			//System.out.println("isNextWeek");
			logger.log(Level.INFO, "isNextWeek");
			return DATE_TIME_FORMAT.TYPE_NEXT_WEEK;
			
		} else if(isNextWeekday(currWord, arr, index)) {
			//System.out.println("isNextWeekday");
			logger.log(Level.INFO, "isNextWeekday");
			return DATE_TIME_FORMAT.TYPE_NEXT_DAY_OF_WEEK;
			
		} else if(isValidDay(currWord)) {
			//System.out.println("isValidDay");
			logger.log(Level.INFO, "isValidDay");
			return DATE_TIME_FORMAT.TYPE_DAY_OF_WEEK;
			
		} else if(isValidTime(currWord,arr, index)) {
			//System.out.println("isValidTime");
			logger.log(Level.INFO, "isValidTime");
			return DATE_TIME_FORMAT.TYPE_TIME;
			
		} else if(isValidDate(currWord, arr, index)) {
			//System.out.println("isValidDate");
			logger.log(Level.INFO, "isValidDate");
			return DATE_TIME_FORMAT.TYPE_DATE;
			
		} else if(isToday(currWord)) {
			//System.out.println("isToday");
			logger.log(Level.INFO, "isToday");
			return DATE_TIME_FORMAT.TYPE_TODAY;
			
		} else if(isTomorrow(currWord)) {
			//System.out.println("isTomorrow");
			logger.log(Level.INFO, "isTomorrow");
			return DATE_TIME_FORMAT.TYPE_TOMORROW;
			
		} else if(isNumDays(currWord, arr, index)) {
			//System.out.println("isNumDays");
			logger.log(Level.INFO, "isNumDays");
			return DATE_TIME_FORMAT.TYPE_NUM_DAYS;
			
		} else if(isNumWeeks(currWord, arr, index)) {
			//System.out.println("isNumWeeks");
			logger.log(Level.INFO, "isNumWeeks");
			return DATE_TIME_FORMAT.TYPE_NUM_WEEKS;
			
		} else {
			//System.out.println("isInvalidType");
			logger.log(Level.INFO, "isInvalidType");
			return DATE_TIME_FORMAT.TYPE_INVALID;
		}
	}
	
	private boolean isNumDays(String currWord, String[] arr, int index) {
		boolean ans = false;
		//System.out.println("isNumber(" + currWord + ") is " + isNumber(currWord));
		if(isNumber(currWord) && index < arr.length -1) {
			String nextWord = arr[index + 1];
			ans = nextWord.equals("days") || nextWord.equals("day") || nextWord.equals("dd");
		}
		return ans;
	}

	private boolean isNumWeeks(String currWord, String[] arr, int index) {
		boolean ans = false;
		if(isNumber(currWord) && index < arr.length -1) {
			String nextWord = arr[index + 1];
			ans = nextWord.equals("weeks") || nextWord.equals("week") || nextWord.equals("wk");
		}
		return ans;
	}

	public DateTime parse(String input) throws IncorrectInputException {
		String[] splitInput = input.toLowerCase().split("\\s+");
									//[dayOfWeek, time, DD, MM, YY, indexInArray]
		int[] combined = new int[] {currDayInWeekInt, DUMMY_INT, currDD, currMM, currYY, 0}; 	
		
		for(int i = 0; i < splitInput.length; i++) {			
			String currWord = splitInput[i];
			switch(getDateTimeType(currWord, splitInput, i)) {
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
				combined = getNextWeek(splitInput, i, combined);
				break;
				
			case TYPE_NUM_DAYS : 
				combined = getNumDays(splitInput, i, combined);
				break;
				
			case TYPE_NUM_WEEKS :
				combined = getNumWeeks(splitInput, i, combined);
				break;
				
			case TYPE_TODAY :
				combined = getToday(combined);
				break;
				
			case TYPE_TOMORROW :
				combined = getTomorrow(combined);
				break;
				
			case TYPE_DATE :
				try {
					combined = getDate(splitInput, i, combined);
				} catch(IncorrectInputException e) {
					throw e;
				}
				break;
				
			case TYPE_TIME :
				try {
					combined = getTime(splitInput, i, combined);
				} catch(IncorrectInputException e) {
					throw e;
				}
				break;
				
			case TYPE_INVALID :
				combined[COMBINED_INDEX_COUNTER] += 1;
				throw new IncorrectInputException("\"" + input + "\"" + " is an invalid date time input");
			
			}
			i = combined[COMBINED_INDEX_COUNTER];
			System.out.println("i is " + i);
		}
		return getDateTimeObject(combined);
	}
	
	//This method converts the array into a DateTime object
	private DateTime getDateTimeObject(int[] combined) {
		DateTime dt;
		if(!isInvalidDateTime) {
			int[] date = new int[]{combined[COMBINED_INDEX_DD], combined[COMBINED_INDEX_MM], combined[COMBINED_INDEX_YY]}; 
			int time = combined[COMBINED_INDEX_TIME];
			System.out.println("time is " + time);
			int day = combined[COMBINED_INDEX_DAY_OF_WEEK];
			
			if(hasPassed(currTime, time, date)) {
				date = getDate(NEXT_DAY); 
				dt = new DateTime(date, daysInWeek[getNextDayInt()], time);
			} else {
				dt = new DateTime(date, daysInWeek[day], time);			
			}
		} else {
			dt = null;
		}
		return dt;
	}

	private int[] getTomorrow(int[] combined) {
		int day = getNextDayInt();
		int[] date = getDate(NEXT_DAY);
		int[] ans = new int[] {day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_DD], date[DATE_INDEX_MM], date[DATE_INDEX_YY], combined[COMBINED_INDEX_COUNTER]};
		printArray(ans);
		return ans;
	}

	private int[] getToday(int[] combined) {
		//combined[COMBINED_INDEX_COUNTER] += 1;
		return combined;
	}
	
	private void printArray(int[] arr) {
		String temp = "";
		for(int i = 0; i < arr.length; i++) {
			temp += arr[i];
			temp += " ";
		}
		System.out.println(temp);
	}
	
	//Eg. 2 weeks later
	private int[] getNumWeeks(String[] splitInput, int i, int[] combined) {
		int numWeeksLater = Integer.parseInt(splitInput[i]);
		int day = currDayInWeekInt;
		int fastForward = getFastForward(day) + NUM_DAYS_IN_WEEK*numWeeksLater;
		int[] date = getDate(fastForward);
		int[] ans = new int[] {day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_DD], date[DATE_INDEX_MM], date[DATE_INDEX_YY], i + 1};
		return ans;
	}
	
	//Eg. 2 days
	private int[] getNumDays(String[] splitInput, int i, int[] combined) {
		int numDaysLater = Integer.parseInt(splitInput[i]);
		int day = get_Int_Day_From(numDaysLater);
		int[] date = getDate(numDaysLater);
		printArray(date);
		int[] ans = new int[] {day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_DD], date[DATE_INDEX_MM], date[DATE_INDEX_YY], i + 1};
		return ans;
	}
	
	//Eg. next week
	private int[] getNextWeek(String[] splitInput, int i, int[] combined) {
		int day = currDayInWeekInt;
		int fastForward = getFastForward(day) + NUM_DAYS_IN_WEEK;
		int[] date = getDate(fastForward);
		int[] ans = new int[] {day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_DD], date[DATE_INDEX_MM], date[DATE_INDEX_YY], i + 1};
		return ans;
	}
	
	//Eg. monday, wed
	private int[] getDayOfWeek(String[] splitInput, int i, int[] combined) {
		int day = convertDayStrToInt(splitInput[i]);
		int fastForward = getFastForward(day);
		int[] date = getDate(fastForward);
		int[] ans = new int[] {day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_DD], date[DATE_INDEX_MM], date[DATE_INDEX_YY], i};
		return ans;
	}
	
	//Eg. next monday, next wed
	private int[] getNextDayOfWeek(String[] splitInput, int i, int[] combined) {
		int day = convertDayStrToInt(splitInput[i + 1]);
		int fastForward = getFastForward(day) + NUM_DAYS_IN_WEEK;
		int[] date = getDate(fastForward);
		int[] ans = new int[] {day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_DD], date[DATE_INDEX_MM], date[DATE_INDEX_YY], i + 1};
		return ans;
	}
	
	//Eg. this monday, this wed, this wednesday
	private int[] getThisDayOfWeek(String[] splitInput, int i, int[] combined) {
		//splitInput[i].equals("this")
		int day = convertDayStrToInt(splitInput[i + 1]);
		int fastForward = getFastForward(day);
		int[] date = getDate(fastForward);
		int[] ans = new int[] {day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_DD], date[DATE_INDEX_MM], date[DATE_INDEX_YY], i + 1};
		return ans;
	}
	
	private int[] getDate(String[] splitInput, int i, int[] combined) {
		int[] newDate = getDateAndAdvanceInt(splitInput, i);			//[dd, mm, yy, advanceInt]
		int[] ans = new int[]{getDay(newDate), combined[COMBINED_INDEX_TIME], newDate[DATE_INDEX_DD], 
							  newDate[DATE_INDEX_MM], newDate[DATE_INDEX_YY], getAdvanceInt(newDate, combined[COMBINED_INDEX_COUNTER])};
		return ans;
	}
	
	private int getAdvanceInt(int[] newDate, int index) {
		int ans = newDate[3] + index;
		return ans;
	}
	
	//Given a date, this method will calculate which day of the week the date falls on
	private int getDay(int[] date) {
		int[] monthTable = new int[]{-1, 6, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5};
		int[] dayTable = new int[]{6, 7, 1, 2, 3, 4, 5};
		int dd = date[0];
		int mm = date[1];
		int yy = date[2];
		boolean isFeb = mm == 2;
		
		int first = dd + monthTable[mm];
		first %= 7;
		int second = yy % 2000;
		int third = second %= 28;
		int fourth =  second / 4;
		int sum = first + third + fourth;
		
		if(isLeapYear(yy) && isFeb) {
			sum -= 1;
		} 
		sum %= 7;
		return dayTable[sum];
	}
	
	//To Do: include the check for valid dates
	private int[] getDateAndAdvanceInt(String[] splitInput, int i) {
		String currWord = splitInput[i];
		int[] ans = new int[]{-1, -1, -1, -1};
		int numEntries = -1;
		
		if(currWord.contains("/")) {
			String[] temp = currWord.split("/");
			for(int j = 0; j < temp.length; j++) {
				ans[j] = Integer.parseInt(temp[j]);
				
			}
			numEntries += 1;
		} else {
			
			int counter = 0;
			for(int j = i; j < splitInput.length; j++) {
				if(counter > 2) {
					break;
				} else {
					String currWord2 = splitInput[j];
					if(isNumber(currWord2)) { 
						int currInt = Integer.parseInt(currWord2);
						if(currInt <= 31 && ans[0] == -1) {
							ans[0] = currInt;
							numEntries++;
							
						} else if(currInt >= 2000 && ans[2] == -1){
							if(ans[2] == -1) {
								ans[2] = currInt;
								numEntries++;
							}
						}
					} else if(isMonth(currWord2) && ans[1] == -1) {
						ans[1] = convertMonthStrToInt(currWord2);
						numEntries++;
					}
				}
				counter++;
			}
		}
		
		if(ans[2] == -1) {
			ans[2] = currYY;
		}
		
		if(ans[0] == -1) {
			ans[0] = DEFAULT_DD_IN_MONTH;			//Default
		}
		
		if(isInvalidDate(ans)) {
			throw new IncorrectInputException("Invalid date!");
		}
		
		ans[3] = numEntries;
		return ans;
	}
	
	//Checks that the num of months does not exceed the max num of days in that month, 
	//Eg. 30 Feb will return an error
	private boolean isInvalidDate(int[] ans) {
		int mm = ans[1];
		return ans[0] > daysInMonth[mm];
	}

	private boolean isValidTime(String currWord, String[] arr, int index) {
		boolean ans = false;
		
		if(currWord.contains("pm") || currWord.contains("am")) {
			//System.out.println("reached here in isValidTime");
			logger.log(Level.INFO, "reached here in isValidTime");
			currWord = currWord.replace("pm", "").replace("am", "");
			if(currWord.contains(":") || currWord.contains(".")) {								//Eg: 12pm, 12.30pm, 12:50am
				currWord = currWord.replace(":", "").replace(".", "");
				ans = true;
			} else {
				ans = true;
			}
			//ans = isValidTimeRange(currWord);
			//} else {
			//ans = isValidTimeRange(currWord);
			//}

		} else if(isNumber(currWord.replace(":", "").replace(".", "")) && index < arr.length - 1) {								//Eg. 9.30 pm, 9
				String nextWord = arr[index + 1];
				if(nextWord.equals("am") ^ nextWord.equals("pm")) {
					ans = true;
					//ans = isValidTimeRange(currWord.replace(":", "").replace(".", ""));
				}
		} else if(currWord.contains(":")) {														//24Hour formats eg: 23:30		
			currWord = currWord.replace(":", "");
			if(isNumber(currWord)) {
				//int currInt = Integer.parseInt(currWord);
				ans = true;
				//ans = currInt <= 2359 && currInt >= 0;
			}
		} else {
			//Invalid command here
		}
		return ans;
	}
	
	private boolean isTomorrow(String currWord) {
		return currWord.equals("tomorrow") || currWord.equals("tmr");
	}

	private boolean isNextWeekday(String currWord, String[] arr, int index) {
		boolean ans = false;
		if(currWord.equals(NEXT) && index != arr.length -1) {	//Check if there is a word that follow "this"
			ans = isValidDay(arr[index + 1]);
		}
		return ans;
	}

	private boolean isNextWeek(String currWord, String[] arr, int index) {
		boolean ans = false;
		if(currWord.equals(NEXT) && index != arr.length - 1) {
			ans = arr[index + 1].equals("week") || arr[index + 1].equals("weeks") || arr[index + 1].equals("wk");
		}
		return ans;
	}

	private boolean isThisMonday(String currWord, String[] arr, int index) {
		boolean ans = false;
		if(currWord.equals(THIS) && index != arr.length -1) {	//Check if there is a word that follow "this"
			ans = isValidDay(arr[index + 1]);
		}
		return ans;
	}
	
	private boolean isValidDay(String currWord) {
		return convertDayStrToInt(currWord) != -1;
	}

	private boolean isValidDate(String currWord, String[] splitInput, int i) {
		boolean ans = false;
		
		if(currWord.contains("/")) {		//Date is 11/2/2016
			ans = true;
		} else {
			int numEntries = 0;
			//boolean hasMM = false;
			for(int j = i; j < splitInput.length; j++) {
				if(numEntries > 2) {
					break;
				} else {
					String splitWord = splitInput[j];	//splitWord is "feb" of "feb 2015"
					if(!isNumber(splitWord) && isMonth(splitWord)) {
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
		for(int i = 1; i < monthsInYear.length; i++) {
			if(nextWord.contains(monthsInYear[i])) {
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
	
	private int convertMonthStrToInt(String mm) {
		int ans = -1;
		for(int j = 1; j < monthsInYear.length; j++){
			if(mm.contains(monthsInYear[j])) {
				ans = j;
				break;
			}
		}
		return ans;
	}

	private int getNextDayInt() {
		int temp = currDayInWeekInt + NEXT_DAY;
		temp %= 7;
		return temp;
	}

	private boolean hasPassed(int currTime2, int time, int[] date) {
		return currTime2 > time && date[DATE_INDEX_DD] == currDD && date[DATE_INDEX_MM] == currMM && date[DATE_INDEX_YY] == currYY && time != -1;			
	}

	// Input number of days to fastforward from today
	// Output the day of the week
	private int get_Int_Day_From(int daysLater) {
		int dayOfWeek = daysLater + currDayInWeekInt;
		dayOfWeek %= 7;
		if(dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		return dayOfWeek;
	}
	
	// Input desired day of the week in int
	// Output days to fastForward from today
	private int getFastForward(int desiredDay) {
		int fastForward = 0;
		if(currDayInWeekInt > desiredDay) {
			fastForward = NUM_DAYS_IN_WEEK - currDayInWeekInt + desiredDay;
		} else if(currDayInWeekInt < desiredDay) {
			fastForward =  desiredDay - currDayInWeekInt;
		} else { // if(currDay == day)
			fastForward = 0;
		}
		return fastForward;
	}

	private int[] getDate(int fastForward) {
		int newDay = currDD + fastForward;
		int newMonth = currMM;
		int newYear = currYY;
		
		while(newDay > daysInMonth[newMonth]) {
			newDay -= daysInMonth[newMonth];
			newMonth += 1;
		}
		
		if(newMonth > 12) {
			newMonth = 1;
			newYear = newYear + 1;
		}
		
		int[] ans = new int[]{newDay, newMonth, newYear};
		return ans;
	}

	private int[] getTime(String[] splitInput, int i, int[] combined) throws IncorrectInputException {
		String timeString = splitInput[i].replace(".", "").replace(":", "");
		boolean isAm = timeString.contains("am");
		boolean isPm = timeString.contains("pm");
		int timeInt = -1;
		boolean isExactlyMidnight = timeString.equals("12am"); 		
		
		if(isAm && isPm) {
			throw new IncorrectInputException("\"" + timeString + "\"" + " cannot be am and pm!");
		}
		
		if(isAm ^ isPm) {
			timeString = timeString.replace("am", "").trim();
			timeString = timeString.replace("pm", "").trim();
		}
		
		timeInt = Integer.parseInt(timeString);
		
		if(timeInt % 100 > 59) {
			throw new IncorrectInputException("Invalid time! Minutes must be greater than 0 and smaller than 60");
		}
		
		if(isMidnight(timeInt, timeString, isAm) || isExactlyMidnight)  {
			if(isExactlyMidnight) {
				timeInt = 0;
			} else {
				timeInt = timeInt % 100;
			}
		} else if(timeInt < 100) { 	// this is for the 12h format
			timeInt = timeInt*100;
		}
		
		if(isPm) {
			timeInt = timeInt + TWELVE_HOURS;
		}
		
		if(i + 1 < splitInput.length) {
			String indicator = splitInput[i + 1];
			if(indicator.equals("pm")) {
				timeInt = timeInt + TWELVE_HOURS;
			}	
			if(timeInt == 1200 && indicator.equals("am")) {
				timeInt = 0;
			}
		}
		if(timeInt >= 2400) {
			throw new IncorrectInputException("Invalid Time! Time must not exceed 24 hours!");
		}
		
		if(i < splitInput.length - 1) {
			if(splitInput[i + 1].equals("pm") || splitInput[i + 1].equals("am")) {
				i += 1;		// To skip the next word
			}
		}
		combined[COMBINED_INDEX_TIME] = timeInt;
		combined[COMBINED_INDEX_COUNTER] = i;
		return combined;
	}
	
	private boolean isMidnight(int timeInt, String timeString, boolean isAm) {
		boolean isMidnight24H = timeInt >= 0 && timeInt <= 59 && timeString.contains("00");
		boolean isMidnight12H = timeInt >= 1200 && timeInt <= 1259 && isAm;
		return isMidnight24H || isMidnight12H;
	}

	private int convertDayStrToInt(String string) {
		int day = -1;
		for(int i = 0; i < daysInWeek.length; i++) {
			if(string.contains(daysInWeek[i])) {
				day = i;
				break;
			}
		}
		return day;
	}

}
