//@@author A0133338J
package dooyit.common.datatype;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Logger;

/** 
 * 
 * @author Annabel
 *
 */
public class DateTime {
	private static final String FORMAT_SPACE = " ";
	private static final String TIME_SEPARATOR_COLON = ":";
	private static final String PM = "pm";
	private static final String AM = "am";
	private static final String DUMMY_STR = "Dummy_Str";
	private static final String CALENDAR_DATE_FORMAT = "dd MM yyyy HH:mm E u";
	private static final String CALENDAR_DEFAULT_TIME_ZONE = "UTC+08:00"; // Singapore Time Zone
	private static final String UNINITIALIZED_STRING = "-1";
	
	private static final int INDEX_DD = 0;
	private static final int INDEX_MM = 1;
	private static final int INDEX_YY = 2;
	private static final int INDEX_TIME_INT = 3;
	private static final int COMPARISON_FIRST_IS_BEFORE_SECOND = -1;
	private static final int COMPARISON_FIRST_EQUALS_SECOND = 0;
	private static final int COMPARISON_FIRST_IS_AFTER_SECOND = 1;
	private static final int NUM_MONTHS_IN_A_YEAR = 12;
	private static final int NUMBER_OF_DAYS_IN_WEEK = 7;
	private static final int UNINITIALIZED_INT = -1;
	private static final int MIDNIGHT_INT = 0;
	private static final int RIGHT_BEFORE_MIDNIGHT_INT = 2359;
	private static final String RIGHT_BEFORE_MIDNIGHT_STRING = "23:59";
	private static final String MIDNIGHT_STRING ="00:00";
	
	public static final int MONDAY = 1;
	public static final int TUESDAY = 2;
	public static final int WEDNESDAY = 3;
	public static final int THURSDAY = 4;
	public static final int FRIDAY = 5;
	public static final int SATURDAY = 6;
	public static final int SUNDAY = 7;
	
	private static final String DAY_MON = "mon";
	private static final String DAY_TUE = "tue";
	private static final String DAY_WED = "wed";
	private static final String DAY_THU = "thu";
	private static final String DAY_FRI = "fri";
	private static final String DAY_SAT = "sat";
	private static final String DAY_SUN = "sun";
	
	private static final String MONTH_JAN = "jan";
	private static final String MONTH_FEB = "feb";
	private static final String MONTH_MARCH = "mar";
	private static final String MONTH_APR = "apr";
	private static final String MONTH_MAY = "may";
	private static final String MONTH_JUN = "jun";
	private static final String MONTH_JUL = "jul";
	private static final String MONTH_AUG = "aug";
	private static final String MONTH_SEP = "sep";
	private static final String MONTH_OCT = "oct";
	private static final String MONTH_NOV = "nov";
	private static final String MONTH_DEC = "dec";
	
	private static final String FORMAT_12H = "%d.%02d %s";
	private static final String FORMAT_24H = "%02d:%02d";
	
	private static String[] months = new String[] { DUMMY_STR, "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
	private static String[] daysInWeek = new String[] { DUMMY_STR, "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
	private static int[] daysInMonth = new int[] { UNINITIALIZED_INT, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	private int dd; // 8
	private int mm;	// 2
	private int yy; // 2016
	private int timeInt;
	
	// Logger for DateTime class
	private static Logger logger = Logger.getLogger("DateTime");
	
	/** */
	public enum Day {
		MON, TUE, WED, THU, FRI, SAT, SUN, INVALID;
	}

	/** */
	public enum Month {
		JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC, INVALID
	}
	
	//********************************************
	//************* Constructors *****************
	//********************************************
	/** */
	public DateTime(DateTime dt) {
		this.dd = dt.getDD();
		this.mm = dt.getMM();
		this.yy = dt.getYY();
		this.timeInt = dt.getTimeInt();
	}
	
	/** */
	public DateTime(int[] date) {
		this.dd = date[INDEX_DD];
		this.mm = date[INDEX_MM];
		this.yy = date[INDEX_YY];
		this.timeInt = UNINITIALIZED_INT;
	}
	
	/** */
	public DateTime(int[] date, int time) {
		this.dd = date[INDEX_DD];
		this.mm = date[INDEX_MM];
		this.yy = date[INDEX_YY];
		this.timeInt = time;
	}

	/** */
	public DateTime() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(CALENDAR_DEFAULT_TIME_ZONE));
		DateFormat dateFormat = new SimpleDateFormat(CALENDAR_DATE_FORMAT);

		String date = dateFormat.format(cal.getTime());
		String[] splitDate = date.split("\\s+");

		this.dd = Integer.parseInt(splitDate[INDEX_DD]);
		this.mm = Integer.parseInt(splitDate[INDEX_MM]);
		this.yy = Integer.parseInt(splitDate[INDEX_YY]);
		this.timeInt = Integer.parseInt(splitDate[INDEX_TIME_INT].replace(TIME_SEPARATOR_COLON, ""));
	}
	
	//********************************************
	//** Methods to retrieve object attributes ***
	//********************************************
	/** 
	 * 
	 * @return
	 */
	public String getDayStr() {
		return daysInWeek[this.getDayInt()];
	}
 
	/** 
	 * 
	 * @return
	 */
	public int getDayInt() {
		return getDayOfWeekFromADate();
	}

	/** 
	 * 
	 * @return
	 */
	public String getDate() {
		return this.dd + FORMAT_SPACE + months[this.mm] + FORMAT_SPACE + this.yy;
	}

	/** 
	 * 
	 * @return
	 */
	public String getTime24hStr() {
		String timeString24H;
		if(this.timeInt == UNINITIALIZED_INT) {
			timeString24H = UNINITIALIZED_STRING;
		} else {
			int hour = getHourNumeral(this.timeInt);
			int minute = getMinutesNumeral(this.timeInt);
			timeString24H = String.format(FORMAT_24H, hour, minute);
		}
		return timeString24H;
	}

	/** 
	 * 
	 * @return
	 */
	public String getTime12hStr() {
		int hour = getHourNumeral(this.timeInt);
		int minute = getMinutesNumeral(this.timeInt);
		String timeString12H;
		if(this.timeInt == UNINITIALIZED_INT) {
			timeString12H = UNINITIALIZED_STRING;
		} else if(hour == 0) {
			hour += 12;
			timeString12H = String.format(FORMAT_12H, hour, minute, AM);
		} else if(hour < 12) {
			timeString12H = String.format(FORMAT_12H, hour, minute, AM);
		} else if(hour == 12) {
			timeString12H = String.format(FORMAT_12H, hour, minute, PM);
		} else {
			hour -= 12;
			timeString12H = String.format(FORMAT_12H, hour, minute, PM);
		}
		return timeString12H;
	}
	
	/** 
	 * 
	 * @return
	 */
	public int getTimeInt() {
		return this.timeInt;
	}

	/** 
	 * 
	 * @return
	 */
	public int getDD() {
		return this.dd;
	}

	/** 
	 * 
	 * @return
	 */
	public int getMM() {
		return this.mm;
	}

	/** 
	 * 
	 * @return
	 */
	public int getYY() {
		return this.yy;
	}
	
	//****************************************************
	//****** Methods to compare 2 DateTime objects *******
	//****************************************************
	
	// Returns 0 if this DateTime object has the same date and time as the DateTime object passed in as argument
	// Returns 1 if this DateTime object lies after DateTime object passed in as argument
	// Returns -1 if this DateTime object lies before DateTime object passed in as argument
	/** 
	 * 
	 * @param dateTime
	 * @return
	 */
	public int compareTo(DateTime dateTime) {
		int comparison = COMPARISON_FIRST_EQUALS_SECOND;
		if(!this.equals(dateTime)) {
			int dateComparison = compareDates(this, dateTime);
			int timeComparison = compareTime(this, dateTime);
			comparison = getComparison(dateComparison, timeComparison);
		}
		return comparison;
	}
	
	/** 
	 * 
	 * @param dateComparison
	 * @param timeComparison
	 * @return
	 */
	private static int getComparison(int dateComparison, int timeComparison) {
		int comparison;
		if(dateComparison != COMPARISON_FIRST_EQUALS_SECOND) {
			comparison = dateComparison;
		} else {
			comparison = timeComparison;
		}
		return comparison;
	}

	/** 
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	private static int compareTime(DateTime first, DateTime second) {
		int comparison = COMPARISON_FIRST_EQUALS_SECOND;
		int firstTimeInt = first.getTimeInt();
		int secondTimeInt = second.getTimeInt();
		if(firstTimeInt != secondTimeInt) {
			if(firstTimeInt == UNINITIALIZED_INT || secondTimeInt == UNINITIALIZED_INT) {
				comparison = compareAgainstUninitializedTime(firstTimeInt, secondTimeInt);
			} else if(firstTimeInt < secondTimeInt) {
				comparison = COMPARISON_FIRST_IS_BEFORE_SECOND;
			} else {
				comparison = COMPARISON_FIRST_IS_AFTER_SECOND;
			}
		} 
		return comparison;
	}

	/** 
	 * 
	 * @param firstTimeInt
	 * @param secondTimeInt
	 * @return
	 */
	private static int compareAgainstUninitializedTime(int firstTimeInt, int secondTimeInt) {
		int comparison;
		if(firstTimeInt == UNINITIALIZED_INT) {
			comparison = COMPARISON_FIRST_IS_AFTER_SECOND;
		} else {
			comparison = COMPARISON_FIRST_IS_BEFORE_SECOND;
		}
		return comparison;
	}

	/** 
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	private static int compareDates(DateTime first, DateTime second) {
		boolean isSameDate = compareDateStrings(first, second);
		int yearComparison = compareYear(first, second);
		int monthComparison = compareMonth(first, second);
		int dayComparison = compareDay(first, second);
		
		int comparison = COMPARISON_FIRST_EQUALS_SECOND;
		
		if(!isSameDate) {
			comparison = compareYearMonthDay(yearComparison, monthComparison, dayComparison);
		}
		
		return comparison;
			
	}
	
	/** 
	 * 
	 * @param yearComparison
	 * @param monthComparison
	 * @param dayComparison
	 * @return
	 */
	private static int compareYearMonthDay(int yearComparison, int monthComparison, int dayComparison) {
		int comparison;
		if(isSameYear(yearComparison)) {
			comparison = compareMonthDay(monthComparison, dayComparison);
		} else {
			comparison = yearComparison;
		}
		return comparison;
	}

	/** 
	 * 
	 * @param monthComparison
	 * @param dayComparison
	 * @return
	 */
	private static int compareMonthDay(int monthComparison, int dayComparison) {
		int comparison;
		if(isSameMonth(monthComparison)) {
			comparison = dayComparison;
		} else {
			comparison = monthComparison;
		}
		return comparison; 
	}

	/** 
	 * 
	 * @param monthComparison
	 * @return
	 */
	private static boolean isSameMonth(int monthComparison) {
		return monthComparison == COMPARISON_FIRST_EQUALS_SECOND;
	}
	
	/** 
	 * 
	 * @param yearComparison
	 * @return
	 */
	private static boolean isSameYear(int yearComparison) {
		return yearComparison == COMPARISON_FIRST_EQUALS_SECOND;
	}

	/** 
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	private static int compareDay(DateTime first, DateTime second) {
		int comparison = COMPARISON_FIRST_EQUALS_SECOND;
		int firstDD = first.getDD();
		int secondDD = second.getDD();
		if(firstDD != secondDD) {
			if(firstDD < secondDD) {
				comparison = COMPARISON_FIRST_IS_BEFORE_SECOND;
			} else {
				comparison = COMPARISON_FIRST_IS_AFTER_SECOND;
			}
		}
		return comparison;
	}

	/** 
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	private static int compareMonth(DateTime first, DateTime second) {
		int comparison = COMPARISON_FIRST_EQUALS_SECOND;
		int firstMM = first.getMM();
		int secondMM = second.getMM();
		if(firstMM != secondMM) {
			if(firstMM < secondMM) {
				comparison = COMPARISON_FIRST_IS_BEFORE_SECOND;
			} else {
				comparison = COMPARISON_FIRST_IS_AFTER_SECOND;
			}
		}
		return comparison;
	}

	/** 
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	private static int compareYear(DateTime first, DateTime second) {
		int comparison = COMPARISON_FIRST_EQUALS_SECOND;
		int firstYY = first.getYY();
		int secondYY = second.getYY();
		if(firstYY != secondYY) {
			if(firstYY < secondYY) {
				comparison = COMPARISON_FIRST_IS_BEFORE_SECOND;
			} else {
				comparison = COMPARISON_FIRST_IS_AFTER_SECOND;
			}
		}
		return comparison;
	}

	/** 
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	private static boolean compareDateStrings(DateTime first, DateTime second) {
		return first.getDate().equals(second.getDate());
	}
	
	/** 
	 * 
	 * @param day
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isWithinDay(Day day, DateTime start, DateTime end) {
		int startDayInt = start.getDayInt();
		int endDayInt = end.getDayInt();
		int dayEnumInt = convertDayEnumToInt(day);
		
		boolean isAfterStart = dayEnumInt >= startDayInt;
		boolean isBeforeEnd = dayEnumInt <= endDayInt;
		return isAfterStart && isBeforeEnd;
	}
	
	/** 
	 * 
	 * @param day
	 * @return
	 */
	private static int convertDayEnumToInt(Day day) {
		String dayEnumString = day.toString().toLowerCase();
		int dayEnumInt = UNINITIALIZED_INT;
		for (int i = 1; i < daysInWeek.length; i++) {
			String daysInWeekString = daysInWeek[i].toLowerCase();
			if (daysInWeekString.contains(dayEnumString)) {
				dayEnumInt = i;
				break;
			}
		}
		return dayEnumInt;
	}

	/** 
	 * 
	 * @param dateTime
	 * @return
	 */
	public boolean equals(DateTime dateTime) {
		boolean hasEqualDateString = this.getDate().equals(dateTime.getDate());
		boolean hasEqualTimeStr24H = this.getTime24hStr().equals(dateTime.getTime24hStr());
		boolean hasEqualTimeStr12H = this.getTime12hStr().equals(dateTime.getTime12hStr());
		boolean hasEqualDayStr = this.getDayStr().equals(dateTime.getDayStr());
		boolean hasEqualDayInt = this.getDayInt() == dateTime.getDayInt();
		return hasEqualDateString && hasEqualTimeStr24H && hasEqualTimeStr12H && hasEqualDayStr && hasEqualDayInt;
	}
	
	//**********************************************
	//*************** Public Methods ***************
	//**********************************************

	/**
	 * 
	 */
	public String toString() {
		String ans = this.getDate() + FORMAT_SPACE + this.getDayStr() + FORMAT_SPACE + this.getTime24hStr() + FORMAT_SPACE + this.getTime12hStr();
		return ans;
	}

	/** 
	 * 
	 * @return
	 */
	public boolean hasTime() {
		return this.getTimeInt() != UNINITIALIZED_INT;
	}

	/** 
	 * 
	 * @param obj
	 * @return
	 */
	public boolean isTheSameDateAs(DateTime obj) {
		return this.getDD() == obj.getDD() && this.getMM() == obj.getMM() && this.getYY() == obj.getYY();
	}

	/** 
	 * 
	 */
	public void increaseByOneDay() {
		this.dd += 1;
		if (this.dd > daysInMonth[this.mm]) {
			this.dd = 1;
			this.mm += 1;
		}
		if (this.mm > NUM_MONTHS_IN_A_YEAR) {
			this.mm = 1;
			this.yy += 1;
		}
	}

	/** 
	 * 
	 * @return
	 */
	public String convertToSavableString() {
		String dateTimeString = "";
		dateTimeString += String.valueOf(dd) + FORMAT_SPACE;
		dateTimeString += String.valueOf(mm) + FORMAT_SPACE;
		dateTimeString += String.valueOf(yy) + FORMAT_SPACE;
		return dateTimeString;
	}
	
	//**********************************************
	//************** Private Methods ***************
	//**********************************************
	/** 
	 * 
	 * @param time
	 * @return
	 */
	private int getHourNumeral(int time) {
		return time / 100;
	}
	
	/** 
	 * 
	 * @param time
	 * @return
	 */
	private int getMinutesNumeral(int time) {
		return time % 100;
	}

	// This method calculates that day of the week that the date falls on
	/** 
	 * 
	 * @return
	 */
	private int getDayOfWeekFromADate() {
		int[] dayTable = new int[] { 7, 1, 2, 3, 4, 5, 6 };
		int[] monthTable = new int[] {UNINITIALIZED_INT, 6, 2, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
		
		int lastTwoDigitsOfYear = this.yy % 1000;
		int divideLastTwoDigitsOfYearByFour = lastTwoDigitsOfYear / 4;
		int sum = lastTwoDigitsOfYear + divideLastTwoDigitsOfYearByFour + this.dd + monthTable[this.mm];
		if(DateTime.isLeapYear(this.yy) && this.mm <= 2 && this.dd <= 31) {
			sum--;
		}
		return dayTable[sum % NUMBER_OF_DAYS_IN_WEEK];
	}
	
	/** 
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		int[] listOfLeapYears = new int[] { 2016, 2020, 2024, 2028, 2032, 2036, 2040, 2044, 2048, 2052, 2056, 2060, 2064, 2068, 2072, 2076, 2080, 2084, 2088, 2092, 2096 };

		boolean ans = false;
		for (int i = 0; i < listOfLeapYears.length; i++) {
			if (year < listOfLeapYears[i]) {
				break;
			}
			if (year == listOfLeapYears[i]) {
				ans = true;
				break;
			}
		} 
		return ans;
	}
	
	/** 
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static ArrayList<String> getMultiDayString(DateTime start, DateTime end) {
		assert start.compareTo(end) == COMPARISON_FIRST_IS_BEFORE_SECOND;
		ArrayList<String> listOfTimings = new ArrayList<String>();
		DateTime startCopy = new DateTime(start);
		
		String startTimeString = startCopy.getTime24hStr();
		String endTimeString = end.getTime24hStr();
		String timeToBeAdded = startTimeString + " - " + RIGHT_BEFORE_MIDNIGHT_STRING;
		listOfTimings.add(timeToBeAdded);
		startCopy.increaseByOneDay();
		
		while(compareDates(startCopy, end) != COMPARISON_FIRST_EQUALS_SECOND) {
			timeToBeAdded = MIDNIGHT_STRING + " - " + RIGHT_BEFORE_MIDNIGHT_STRING;
			listOfTimings.add(timeToBeAdded);
			startCopy.increaseByOneDay();
		}
		timeToBeAdded = MIDNIGHT_STRING + " - " + endTimeString;
		listOfTimings.add(timeToBeAdded);
		
		return listOfTimings;
	}
	
	/** 
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean hasMultiDay(DateTime start, DateTime end) {
		return !start.isTheSameDateAs(end);
	}
	
	/** 
	 * 
	 * @param dt
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isWithin(DateTime dt, DateTime start, DateTime end) {
		boolean dtIsAfterStart = dt.compareTo(start) != COMPARISON_FIRST_IS_BEFORE_SECOND;
		boolean dtIsBeforeEnd = dt.compareTo(end) != COMPARISON_FIRST_IS_AFTER_SECOND;
		return dtIsAfterStart && dtIsBeforeEnd;
	}
	
	/** 
	 * 
	 * @param month
	 * @return
	 */
	public boolean isMonth(Month month) {
		String monthString = month.name();
		String dateTimeMonth = months[this.getMM()];
		return monthString.equalsIgnoreCase(dateTimeMonth);
	}
	
	/** 
	 * 
	 * @param day
	 * @return
	 */
	public boolean isDay(Day day) {
		String dayString = day.name();
		String dateTimeDay = daysInWeek[this.getDayInt()].substring(0, 3);
		return dayString.equalsIgnoreCase(dateTimeDay);
	}
	
	/** 
	 * 
	 * @param input
	 * @return
	 */
	public static Day getDayType(String input) {
		Day type;
		if(input.contains(DAY_MON)) {
			type = Day.MON ;
		} else if(input.contains(DAY_TUE)) {
			type = Day.TUE;
		} else if(input.contains(DAY_WED)) {
			type = Day.WED;
		} else if(input.contains(DAY_THU)) {
			type = Day.THU;
		} else if(input.contains(DAY_FRI)) {
			type = Day.FRI;
		} else if(input.contains(DAY_SAT)) {
			type = Day.SAT;
		} else if(input.contains(DAY_SUN)) {
			type = Day.SUN;
		} else {
			type = Day.INVALID;
		}
		return type;
	}
	
	/** 
	 * 
	 * @param input
	 * @return
	 */
	public static Month getMonthType(String input) {
		Month type;
		if(input.contains(MONTH_JAN)) {
			type = Month.JAN;
		} else if(input.contains(MONTH_FEB)) {
			type = Month.FEB;
		} else if(input.contains(MONTH_MARCH)) {
			type = Month.MAR;
		} else if(input.contains(MONTH_APR)) {
			type = Month.APR;
		} else if(input.contains(MONTH_MAY)) {
			type = Month.MAY;
		} else if(input.contains(MONTH_JUN)) {
			type = Month.JUN;
		} else if(input.contains(MONTH_JUL)) {
			type = Month.JUL;
		} else if(input.contains(MONTH_AUG)) {
			type = Month.AUG;
		} else if(input.contains(MONTH_SEP)) {
			type = Month.SEP;
		} else if(input.contains(MONTH_OCT)) {
			type = Month.OCT;
		} else if(input.contains(MONTH_NOV)) {
			type = Month.NOV;
		} else if(input.contains(MONTH_DEC)) {
			type = Month.DEC;
		} else {
			type = Month.INVALID;
		}
		return type;
	}
	
	/** 
	 * 
	 * @param start
	 */
	public void setDate(DateTime start) {
		this.dd = start.getDD();
		this.mm = start.getMM();
		this.yy = start.getYY();
	}

	/** 
	 * 
	 */
	public void setTimeToMidnight() {
		this.timeInt = MIDNIGHT_INT;
	}

	/** 
	 * 
	 */
	public void setTimeToRightBeforeMidnight() {
		this.timeInt = RIGHT_BEFORE_MIDNIGHT_INT;
	}
	
	/** 
	 * 
	 * @param startOne
	 * @param endOne
	 * @param startTwo
	 * @param endTwo
	 * @return
	 */
	public static boolean isOverlap(DateTime startOne, DateTime endOne, DateTime startTwo, DateTime endTwo) {
		boolean startOneIsBeforeEndTwo = startOne.compareTo(endTwo) == COMPARISON_FIRST_IS_BEFORE_SECOND;
		boolean endOneIsAfterEndTwo = endOne.compareTo(endTwo) == COMPARISON_FIRST_IS_AFTER_SECOND;
		boolean firstIntervalIsAfterSecond = !startOneIsBeforeEndTwo && endOneIsAfterEndTwo;
		
		boolean startOneIsBeforeStartTwo = startOne.compareTo(startTwo) == COMPARISON_FIRST_IS_BEFORE_SECOND;
		boolean endOneIsBeforeStartTwo = endOne.compareTo(startTwo) != COMPARISON_FIRST_IS_AFTER_SECOND;
		boolean firstIntervalIsBeforeSecond = startOneIsBeforeStartTwo && endOneIsBeforeStartTwo;
		
		boolean hasNoOverlap = firstIntervalIsAfterSecond || firstIntervalIsBeforeSecond;
		return !hasNoOverlap;
	}
	
	/** 
	 * 
	 * @param deadline
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isOverlap(DateTime deadline, DateTime start, DateTime end) {
		boolean deadlineIsBeforeStart = deadline.compareTo(start) == COMPARISON_FIRST_IS_BEFORE_SECOND;
		boolean deadlineIsAfterEnd = deadline.compareTo(end) == COMPARISON_FIRST_IS_AFTER_SECOND;
		return deadlineIsBeforeStart || deadlineIsAfterEnd;
	}
	
	/** 
	 * 
	 * @param dateTime
	 * @param day
	 * @return
	 */
	public static boolean isSpecifiedDay(DateTime dateTime, int day) {
		return dateTime.getDayInt() == day;
	}
}
