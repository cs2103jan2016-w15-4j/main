//@@author A0133338J
package dooyit.common.datatype;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 * DateTime is the date structure for date and and time
 * 
 * @author Annabel
 *
 */
public class DateTime {
	// For to string method
	private static final String FORMAT_SPACE = " ";
	
	// Time indicators
	private static final String TIME_SEPARATOR_COLON = ":";
	private static final String PM = "pm";
	private static final String AM = "am";

	// Calendar for date format and time zone
	private static final String CALENDAR_DATE_FORMAT = "dd MM yyyy HH:mm E u";
	private static final String CALENDAR_DEFAULT_TIME_ZONE = "UTC+08:00"; // Singapore Time Zone
	
	// Uninitialized strings and integers
	private static final int UNINITIALIZED_INT = -1;
	private static final String UNINITIALIZED_STRING = "-1";
	private static final String DUMMY_STR = "Dummy_Str";
	
	// Index of DD, MM, YY and timeInt in the date int array input
	private static final int INDEX_DD = 0;
	private static final int INDEX_MM = 1;
	private static final int INDEX_YY = 2;
	private static final int INDEX_TIME_INT = 3;
	
	// Constants for comparison of DateTime objects
	private static final int COMPARISON_FIRST_IS_BEFORE_SECOND = -1;
	private static final int COMPARISON_FIRST_EQUALS_SECOND = 0;
	private static final int COMPARISON_FIRST_IS_AFTER_SECOND = 1;
	
	// Number of months in a year and week
	private static final int NUM_MONTHS_IN_A_YEAR = 12;
	private static final int NUMBER_OF_DAYS_IN_WEEK = 7;

	// Constants for midnight and before midnight
	private static final int MIDNIGHT_INT = 0;
	private static final int RIGHT_BEFORE_MIDNIGHT_INT = 2359;
	
	// String constants for midnight and before midnight
	private static final String RIGHT_BEFORE_MIDNIGHT_STRING = "23:59";
	private static final String MIDNIGHT_STRING ="00:00";
	
	// Int constants for the days of the week
	public static final int INT_MONDAY = 1;
	public static final int INT_TUESDAY = 2;
	public static final int INT_WEDNESDAY = 3;
	public static final int INT_THURSDAY = 4;
	public static final int INT_FRIDAY = 5;
	public static final int INT_SATURDAY = 6;
	public static final int INT_SUNDAY = 7;
	
	// Short form of the days of the week
	private static final String DAY_MON = "mon";
	private static final String DAY_TUE = "tue";
	private static final String DAY_WED = "wed";
	private static final String DAY_THU = "thu";
	private static final String DAY_FRI = "fri";
	private static final String DAY_SAT = "sat";
	private static final String DAY_SUN = "sun";
	
	// Short form of the months in a year
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
	
	// For the formatting of time string
	private static final String FORMAT_12H = "%d.%02d %s";
	private static final String FORMAT_24H = "%02d:%02d";
	
	// Array constants
	private static final String[] months = new String[] { DUMMY_STR, "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
	private static final String[] daysInWeek = new String[] { DUMMY_STR, "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
	private static final int[] daysInMonth = new int[] { UNINITIALIZED_INT, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	// DateTime object attributes
	private int dd; 
	private int mm;	
	private int yy; 
	private int timeInt;
	
	// Logger for DateTime class
	private static Logger logger = Logger.getLogger("DateTime");
	
	/** Types of day of the week */
	public enum Day {
		MON, TUE, WED, THU, FRI, SAT, SUN, INVALID;
	}

	/** Types of month of the year */
	public enum Month {
		JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC, INVALID
	}
	
	//********************************************
	//************* Constructors *****************
	//********************************************
	/** 
	 * Initialize a DateTime object with the same attributes as the
	 * DateTime object passed into the method
	 */
	public DateTime(DateTime dt) {
		this.dd = dt.getDD();
		this.mm = dt.getMM();
		this.yy = dt.getYY();
		this.timeInt = dt.getTimeInt();
		logger.log(Level.INFO, "Initialised DateTime object");
	}
	
	/** 
	 * Initialize a DateTime object with the same date as the 
	 * int array passed into the method
	 */
	public DateTime(int[] date) {
		this.dd = date[INDEX_DD];
		this.mm = date[INDEX_MM];
		this.yy = date[INDEX_YY];
		this.timeInt = UNINITIALIZED_INT;
		logger.log(Level.INFO, "Initialised DateTime object");
	}
	
	/** 
	 * Initialize a DateTime object with the same date as the 
	 * int array and the same time int passed into the method
	 */
	public DateTime(int[] date, int time) {
		this.dd = date[INDEX_DD];
		this.mm = date[INDEX_MM];
		this.yy = date[INDEX_YY];
		this.timeInt = time;
		logger.log(Level.INFO, "Initialised DateTime object");
	}

	/**
	 * Initialize a DateTime object with the date and time that
	 * the object is initialized. 
	 */
	public DateTime() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(CALENDAR_DEFAULT_TIME_ZONE));
		DateFormat dateFormat = new SimpleDateFormat(CALENDAR_DATE_FORMAT);

		String date = dateFormat.format(cal.getTime());
		String[] splitDate = date.split("\\s+");

		this.dd = Integer.parseInt(splitDate[INDEX_DD]);
		this.mm = Integer.parseInt(splitDate[INDEX_MM]);
		this.yy = Integer.parseInt(splitDate[INDEX_YY]);
		this.timeInt = Integer.parseInt(splitDate[INDEX_TIME_INT].replace(TIME_SEPARATOR_COLON, ""));
		logger.log(Level.INFO, "Initialised DateTime object");
	}
	
	//********************************************
	//** Methods to retrieve object attributes ***
	//********************************************
	/** 
	 * Gets the string of the day of the week like wed and thur
	 * @return day of the week in string
	 */
	public String getDayStr() {
		return daysInWeek[this.getDayInt()];
	}
 
	/** 
	 * Gets the int of the day of the week like 3 for wed
	 * @return day of the week in int form
	 */
	public int getDayInt() {
		return getDayOfWeekFromADate();
	}

	/** 
	 * Get the string format of the date.
	 * @return string format of the date.
	 */
	public String getDate() {
		return this.dd + FORMAT_SPACE + months[this.mm] + FORMAT_SPACE + this.yy;
	}

	/** 
	 * Get the 24h time string
	 * @return 24h time string
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
	 * Get the 12h time string
	 * @return 12h time string
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
	 * Get the 24h time int of the object
	 * @return the 24h time int.
	 */
	public int getTimeInt() {
		return this.timeInt;
	}

	/** 
	 * Get the DD of the object.
	 * @return DD of the date.
	 */
	public int getDD() {
		return this.dd;
	}

	/** 
	 * Get the MM attribute of the object
	 * @return MM of the date.
	 */
	public int getMM() {
		return this.mm;
	}

	/** 
	 * Get the YY attribute of the object
	 * @return YY of the date.
	 */
	public int getYY() {
		return this.yy;
	}
	
	//****************************************************
	//****** Methods to compare 2 DateTime objects *******
	//****************************************************
	

	/** 
	 * Check if one date time object is before, equal or after another date time object.
	 * 
	 * @param DateTime
	 *        The DateTime object passed in for comparison
	 *        
	 * @return	Returns 0 if this DateTime object has the same date and time as the DateTime object passed in as argument
	 *			Returns 1 if this DateTime object lies after DateTime object passed in as argument
	 *			Returns -1 if this DateTime object lies before DateTime object passed in as argument
	 */
	public int compareTo(DateTime DateTime) {
		int comparison = COMPARISON_FIRST_EQUALS_SECOND;
		if(!this.equals(DateTime)) {
			int dateComparison = compareDates(this, DateTime);
			int timeComparison = compareTime(this, DateTime);
			comparison = getComparison(dateComparison, timeComparison);
		}
		return comparison;
	}
	
	/** 
	 * Compares the comparison values for date and time.
	 * 
	 * @param dateComparison
	 *        Possible values are -1, 0, and 1
	 *        
	 * @param timeComparison
	 *        Possible values are -1, 0, and 1
	 *        
	 * @return -1, 0, and 1 depending on whether the DateTime object if before
	 * 			equals to or after the other DateTime object.
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
	 * Compares the time int of 2 DateTime objects.
	 * 
	 * @param first
	 * 		  First DateTime object passed in.
	 * 
	 * @param second
	 *        Second DateTime object passed in.
	 *        
	 * @return -1, 0, 1 if first timing is before, equals to or after second timing.
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
	 * Checks if the first timeint is uninitialized before comparing the timings.
	 * This is when the first and second timings cannot be equal to each other.
	 * 
	 * @param firstTimeInt
	 *        TimeInt of the first DateTime object.
	 *        
	 * @param secondTimeInt
	 *        TimeInt of the second dadtetime object.
	 *        
	 * @return -1, 1 when first timing is before or after second timing.
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
	 * Compares just the dates of 2 DateTime objects.
	 * 
	 * @param first
	 * 		  First DateTime object input
	 * 
	 * @param second
	 * 		  Second DateTime object input
	 * 
	 * @return -1, 0, 1 if first date is before, equals to or after second date.
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
	 * Compare the comparison values of day, month and year.
	 * 
	 * @param yearComparison
	 * 		  Possible values are -1, 0, 1
	 * 
	 * @param monthComparison
	 * 		  Possible values are -1, 0, 1
	 * 
	 * @param dayComparison
	 *        Possible values are -1, 0, 1
	 *        
	 * @return -1, 0, 1 after taking into account the year, month and day comparisons.
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
	 * Compare the comparison values of day and month.
	 * 
	 * @param monthComparison
	 * 		  Possible values are -1, 0, 1
	 * 
	 * @param dayComparison
	 *        Possible values are -1, 0, 1
	 *        
	 * @return -1, 0, 1 after taking into account the month and day comparisons.
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
	 * Checks if monthComparison value is 0
	 * 
	 * @param monthComparison
	 * 		  Possible values are -1, 0, 1
	 *        
	 * @return true if is 0 and false otherwise.
	 */
	private static boolean isSameMonth(int monthComparison) {
		return monthComparison == COMPARISON_FIRST_EQUALS_SECOND;
	}
	
	/**
	 * Checks if yearComparison value is 0
	 * 
	 * @param yearComparison
	 * 		  Possible values are -1, 0, 1
	 *        
	 * @return true if is 0 and false otherwise.
	 */
	private static boolean isSameYear(int yearComparison) {
		return yearComparison == COMPARISON_FIRST_EQUALS_SECOND;
	}

	/** 
	 * Compares the days of 2 DateTime objects.
	 * 
	 * @param first
	 * 		  First DateTime object input
	 * 
	 * @param second
	 * 		  Second DateTime object input
	 * 
	 * @return -1, 0, 1 if first day is before, equals to or after second day.
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
	 * Compares the months of 2 DateTime objects.
	 * 
	 * @param first
	 * 		  First DateTime object input
	 * 
	 * @param second
	 * 		  Second DateTime object input
	 * 
	 * @return -1, 0, 1 if first month is before, equals to or after second month.
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
	 * Compares the months of 2 DateTime objects
	 * 
	 * @param first
	 * 		  First DateTime object input
	 * 
	 * @param second
	 * 		  Second DateTime object input
	 * 
	 * @return -1, 0, 1 if first year is before, equals to or after second year.
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
	 * Compares the date strings of 2 DateTime objects
	 * 
	 * @param first
	 * 		  First DateTime object input
	 * 
	 * @param second
	 * 		  Second DateTime object input
	 * 
	 * @return true if the date strings are equal and false otherwise.
	 */
	private static boolean compareDateStrings(DateTime first, DateTime second) {
		return first.getDate().equals(second.getDate());
	}
	
	/** 
	 * Checks if the stated day is between the start and end DateTime objects
	 * 
	 * @param day
	 * 		  Day Enum
	 * 
	 * @param first
	 * 		  First DateTime object input
	 * 
	 * @param second
	 * 		  Second DateTime object input
	 * 
	 * @return true if it lies between the start and end and false otherwise.
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
	 * Checks if this DateTime object is equal to the DateTime object passed 
	 * into the method.
	 * 
	 * @param dateTime
	 * 		  A DateTim object
	 * 
	 * @return true if the two object have the same attributes and false otherwise.
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
	 * Converts a DateTime object to string format
	 */
	public String toString() {
		String ans = this.getDate() + FORMAT_SPACE + this.getDayStr() + FORMAT_SPACE + this.getTime24hStr() + FORMAT_SPACE + this.getTime12hStr();
		return ans;
	}

	/** 
	 * Checks if the time field is initialised in this DateTime object
	 * 
	 * @return true is initialized and false otherwise.
	 */
	public boolean hasTime() {
		return this.getTimeInt() != UNINITIALIZED_INT;
	}

	/** 
	 * Checks if this DateTime object has the same date as the DateTime
	 * object passed into the method.
	 * 
	 * @param obj
	 *        A DateTime object
	 *        
	 * @return true if the 2 DateTime objects have the same date and false otherwise.
	 */
	public boolean isTheSameDateAs(DateTime obj) {
		return this.getDD() == obj.getDD() && this.getMM() == obj.getMM() && this.getYY() == obj.getYY();
	}

	/** 
	 * Increase the DD in the date field by one
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
	 * Converts the DateTime object into string format for saving into task list
	 * @return string of DateTime object
	 */
	public String convertToSavableString() {
		String DateTimeString = "";
		DateTimeString += String.valueOf(dd) + FORMAT_SPACE;
		DateTimeString += String.valueOf(mm) + FORMAT_SPACE;
		DateTimeString += String.valueOf(yy) + FORMAT_SPACE;
		return DateTimeString;
	}
	
	
	/** 
	 * Checks if a year is an input year
	 * 
	 * @param year
	 * 		  Input year integer
	 * 
	 * @return true if it is leap year and false otherwise.
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
	 * Converts the start and end timings of a multi-day event into an
	 * ArrayList of time strings.
	 * 
	 * @param start
	 * 		  A DateTime object.
	 * 
	 * @param end
	 * 		  A DateTime object.
	 * 
	 * @return an ArrayList of 24h format time string
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
	 * Checks if 2 DateTime object falls on the same date to determine if an event
	 * task spans across multiple days or not.
	 * 
	 * @param start
	 * 		  A DateTime object.
	 * 
	 * @param end
	 * 		  A DateTime object.
	 * 
	 * @return true if the 2 DateTime objects have different dates and false otherwise.
	 */
	public static boolean hasMultiDay(DateTime start, DateTime end) {
		return !start.isTheSameDateAs(end);
	}
	
	/** 
	 * Checks if a DateTime object lies between a start and end DateTime objects.
	 * 
	 * @param dt
	 * 		  A DateTime object.
	 * 
	 * @param start
	 * 		  A DateTime object.
	 * 
	 * @param end
	 * 		  A DateTime object.
	 * 
	 * @return true is dt is between the start and the end
	 */
	public static boolean isWithin(DateTime dt, DateTime start, DateTime end) {
		boolean dtIsAfterStart = dt.compareTo(start) != COMPARISON_FIRST_IS_BEFORE_SECOND;
		boolean dtIsBeforeEnd = dt.compareTo(end) != COMPARISON_FIRST_IS_AFTER_SECOND;
		return dtIsAfterStart && dtIsBeforeEnd;
	}
	
	/** 
	 * Checks if a DateTime object has the same month as the specified month.
	 * 
	 * @param month
	 * 		  Month enum
	 * 
	 * @return true if the date lies on the same month and false otherwise.
	 */
	public boolean isMonth(Month month) {
		String monthString = month.name();
		String DateTimeMonth = months[this.getMM()];
		return monthString.equalsIgnoreCase(DateTimeMonth);
	}
	
	/** 
	 * Checks if a DateTime object has the same month as the specified month.
	 * 
	 * @param day
	 * 		  Day enum for day of the week
	 * 
	 * @return true if the date lies on the same day of the week and false otherwise.
	 */
	public boolean isDay(Day day) {
		String dayString = day.name();
		
		// Get the first 3 letters of the day of the week
		String DateTimeDay = daysInWeek[this.getDayInt()].substring(0, 3);
		
		return dayString.equalsIgnoreCase(DateTimeDay);
	}
	
	/** 
	 * Converts day of the week string to Day enum
	 * 
	 * @param input
	 * 		  Day of the week string
	 * 
	 * @return the respective Day Enum
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
	 * Converts month of the year string to Month enum
	 * 
	 * @param input
	 * 		  Month of the year string
	 * 
	 * @return the respective Month Enum
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
	 * Sets date of this DateTime object to the same date as the DateTime object
	 * 
	 * @param start
	 * 		  A DateTime object
	 */
	public void setDate(DateTime start) {
		this.dd = start.getDD();
		this.mm = start.getMM();
		this.yy = start.getYY();
	}

	/** 
	 * Sets time of this DateTime object to the same time as the DateTime object
	 */
	public void setTimeToMidnight() {
		this.timeInt = MIDNIGHT_INT;
	}

	/** 
	 * Sets time of this DateTime object to right before midnight
	 */
	public void setTimeToRightBeforeMidnight() {
		this.timeInt = RIGHT_BEFORE_MIDNIGHT_INT;
	}
	
	/** 
	 * Checks if there is an overlap between the first start and end date and timings and the
	 * second start and end timings. This is to check if 2 events overlap.
	 * 
	 * @param startOne
	 * 		  A DateTime object
	 * 
	 * @param endOne
	 *  	  A DateTime object
	 *  
	 * @param startTwo
	 *        A DateTime object
	 *        
	 * @param endTwo
	 *        A DateTime object
	 *        
	 * @return true if there is an overlap and false otherwise.
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
	 * Checks if the deadline lies within the event start and end date and timings.
	 * 
	 * @param deadline
	 * 	      A DateTime object
	 * 
	 * @param start
	 * 	      A DateTime object
	 * 
	 * @param end
	 * 	      A DateTime object
	 * 
	 * @return true if there is an overlap and false otherwise.
	 */
	public static boolean isOverlap(DateTime deadline, DateTime start, DateTime end) {
		boolean deadlineIsBeforeStart = deadline.compareTo(start) == COMPARISON_FIRST_IS_BEFORE_SECOND;
		boolean deadlineIsAfterEnd = deadline.compareTo(end) == COMPARISON_FIRST_IS_AFTER_SECOND;
		return deadlineIsBeforeStart || deadlineIsAfterEnd;
	}
	
	/** 
	 * Checks if the DateTime object passed in has the specified day of the week int passed in
	 * 
	 * @param dateTime
	 * 		  A DateTime object
	 * 
	 * @param day
	 * 		  Day of the week in int form
	 * 
	 * @return true if the DateTime object lies on the same day as the day int passed in.
	 */
	public static boolean isSpecifiedDay(DateTime dateTime, int day) {
		return dateTime.getDayInt() == day;
	}
	
	//**********************************************
	//************** Private Methods ***************
	//**********************************************
	
	/** 
	 * Converts Day enum to respective day int.
	 * 
	 * @param day
	 * 		  Day of the week in int form.
	 * 
	 * @return the day of the week in int form.
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
	 * Gets the hour value of a timeInt
	 * 
	 * @param time
	 * 		  24h time int
	 * 
	 * @return returns the hour time value.
	 */
	private int getHourNumeral(int time) {
		return time / 100;
	}
	
	/** 
	 * Gets the minutes value of a time int
	 * 
	 * @param time
	 * 		  24h time int
	 * 
	 * @return the minute time value.
	 */
	private int getMinutesNumeral(int time) {
		return time % 100;
	}

	/** 
	 * Calculates the day of the week that the date falls on. 
	 * Eg. Given 17/2/2016, method will return 3 indicating wed
	 * 
	 * @return day of the week in int formg
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
}
