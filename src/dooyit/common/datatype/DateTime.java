package dooyit.common.datatype;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class DateTime {
	private static final String FORMAT_SPACE = " ";
	private static final String TWELVE = "12";
	private static final String TIME_SEPARATOR_COLON = ":";
	private static final String TIME_SEPARATOR_DOT = ".";
	private static final String TWELVE_MIDNIGHT = "12 am";
	private static final String PM = " pm";
	private static final String AM = " am";
	private static final String DUMMY_STR = "Dummy_Str";
	private static final String ONE_ZERO = "0";
	private static final String CALENDAR_DATE_FORMAT = "dd MM yyyy HH:mm E u";
	private static final String CALENDAR_DEFAULT_TIME_ZONE = "UTC+08:00"; // Singapore Time Zone
	
	private static final String UNINITIALIZED_STRING = "-1";
	private static final int UNINITIALIZED_INT = -1;
	
	private static final int INDEX_DD = 0;
	private static final int INDEX_MM = 1;
	private static final int INDEX_YY = 2;
	private static final int INDEX_TIME_INT = 3;
	private static final int INDEX_DAY_INT = 5;
	private static final int COMPARISON_FIRST_IS_BEFORE_SECOND = -1;
	private static final int COMPARISON_FIRST_EQUALS_SECOND = 0;
	private static final int COMPARISON_FIRST_IS_AFTER_SECOND = 1;
	private static final int NUM_MONTHS_IN_A_YEAR = 12;
	private static final int NUM_DAYS_IN_A_WEEK = 7;
	private static final int FORMAT_24H_12AM = 0;
	private static final int FORMAT_24H_12PM = 1200;
	private static final int FORMAT_24H_10AM = 1000;
	private static final int FORMAT_24H_1AM = 100;
	
	private static String[] months = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
	private static String[] daysInWeek = new String[] { DUMMY_STR, "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
	private static int[] daysInMonth = new int[] { UNINITIALIZED_INT, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	private String date; // 8 March 2016
	private String timeStr24H; // 1300
	private String timeStr12H; // 1 pm, 2 am, 3.30 am
	private String dayStr; // Mon
	private int dayInt; // 1
	private int dd; // 8
	private int mm;
	private int yy; // 2016
	private int timeInt;

	public DateTime(DateTime dt) {
		this.dd = dt.getDD();
		this.mm = dt.getMM();
		this.yy = dt.getYY();
		this.timeInt = dt.getTimeInt();
		this.timeStr12H = dt.getTime12hStr();
		this.timeStr24H = dt.getTime24hStr();
		this.dayStr = dt.getDayStr();
		this.dayInt = setDayInt(this.dayStr);
		this.date = this.dd + FORMAT_SPACE + months[decrementByOne(this.mm)] + FORMAT_SPACE + this.yy;
	}

	private int decrementByOne(int number) {
		return number - 1;
	}

	public DateTime(int[] date, String day, int time) {
		this.dd = date[INDEX_DD];
		this.mm = date[INDEX_MM];
		this.yy = date[INDEX_YY];
		this.timeInt = time;
		this.timeStr24H = convertTimeIntTo24hString(time);
		this.timeStr12H = convertTimeIntTo12hString(time);
		this.dayStr = day;
		
		this.dayInt = setDayInt(dayStr);
		this.date = this.dd + FORMAT_SPACE + months[decrementByOne(this.mm)] + FORMAT_SPACE + this.yy;
	}

	public DateTime() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(CALENDAR_DEFAULT_TIME_ZONE));
		DateFormat dateFormat = new SimpleDateFormat(CALENDAR_DATE_FORMAT);

		String date = dateFormat.format(cal.getTime());
		String[] splitDate = date.split("\\s+");

		this.dd = Integer.parseInt(splitDate[INDEX_DD]);
		this.mm = Integer.parseInt(splitDate[INDEX_MM]);
		this.yy = Integer.parseInt(splitDate[INDEX_YY]);
		this.timeInt = Integer.parseInt(splitDate[INDEX_TIME_INT].replace(TIME_SEPARATOR_COLON, ""));
		this.dayInt = Integer.parseInt(splitDate[INDEX_DAY_INT]);
		this.dayStr = daysInWeek[this.dayInt];
		this.timeStr24H = convertTimeIntTo24hString(this.timeInt);
		this.timeStr12H = convertTimeIntTo12hString(this.timeInt);
		this.date = this.dd + FORMAT_SPACE + months[decrementByOne(this.mm)] + FORMAT_SPACE + this.yy;
	}

	public DateTime(int[] date, String day) {
		this.dd = date[INDEX_DD];
		this.mm = date[INDEX_MM];
		this.yy = date[INDEX_YY];
		this.timeStr24H = String.valueOf(UNINITIALIZED_INT);
		this.timeStr12H = String.valueOf(UNINITIALIZED_INT);
		this.dayStr = day;
		this.dayInt = setDayInt(dayStr);
		this.date = this.dd + FORMAT_SPACE + months[decrementByOne(this.mm)] + FORMAT_SPACE + this.yy;
	}

	public DateTime(int dd, int mm, int yy, String day, String time) throws NumberFormatException {
		this.dd = dd;
		this.mm = mm;
		this.yy = yy;
		try {
			this.timeInt = Integer.parseInt(time);
			this.timeStr24H = time;
			this.timeStr12H = convertTimeIntTo12hString(Integer.parseInt(time));
		} catch (NumberFormatException e) {
			System.out.println("Error: time is " + time);
			System.out.println(e.getMessage());
			this.timeInt = UNINITIALIZED_INT;
			this.timeStr24H = UNINITIALIZED_STRING;
			this.timeStr12H = UNINITIALIZED_STRING;
		}
		this.dayStr = day;
		this.dayInt = setDayInt(dayStr);
		this.date = this.dd + FORMAT_SPACE + months[decrementByOne(this.mm)] + FORMAT_SPACE + this.yy;
	}

	public DateTime(String[] split, String day, int time2) {
		this.dd = Integer.parseInt(split[INDEX_DD]);
		this.mm = Integer.parseInt(split[INDEX_MM]);
		this.yy = Integer.parseInt(split[INDEX_YY]);
		this.timeStr24H = convertTimeIntTo24hString(time2);
		this.timeStr12H = convertTimeIntTo12hString(time2);
		this.dayStr = day;
		this.dayInt = setDayInt(dayStr);
		this.date = this.dd + FORMAT_SPACE + months[decrementByOne(this.mm)] + FORMAT_SPACE + this.yy;
	}
	
	// Returns 0 if this DateTime object has the same date and time as the DateTime object passed in as argument
	// Returns 1 if this DateTime object lies after DateTime object passed in as argument
	// Returns -1 if this DateTime object lies before DateTime object passed in as argument
	public int compareTo(DateTime dateTime) {
		int comparison = COMPARISON_FIRST_EQUALS_SECOND;
		if(!this.equals(dateTime)) {
			int dateComparison = compareDates(this, dateTime);
			int timeComparison = compareTime(this, dateTime);
			comparison = getComparison(dateComparison, timeComparison);
		}
		return comparison;
	}
	
	private int getComparison(int dateComparison, int timeComparison) {
		int comparison;
		if(dateComparison != 0) {
			comparison = dateComparison;
		} else {
			comparison = timeComparison;
		}
		return comparison;
	}

	private int compareTime(DateTime first, DateTime second) {
		int comparison = COMPARISON_FIRST_EQUALS_SECOND;
		int firstTimeInt = first.getTimeInt();
		int secondTimeInt = second.getTimeInt();
		if(firstTimeInt != secondTimeInt) {
			if(firstTimeInt < secondTimeInt) {
				comparison = COMPARISON_FIRST_IS_BEFORE_SECOND;
			} else {
				comparison = COMPARISON_FIRST_IS_AFTER_SECOND;
			}
		}
		return comparison;
	}

	private int compareDates(DateTime first, DateTime second) {
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
	private int compareYearMonthDay(int yearComparison, int monthComparison, int dayComparison) {
		int comparison;
		if(isSameYear(yearComparison)) {
			comparison = compareMonthDay(monthComparison, dayComparison);
		} else {
			comparison = yearComparison;
		}
		return comparison;
	}

	private int compareMonthDay(int monthComparison, int dayComparison) {
		int comparison;
		if(isSameMonth(monthComparison)) {
			comparison = dayComparison;
		} else {
			comparison = monthComparison;
		}
		return comparison;
	}

	private boolean isSameMonth(int monthComparison) {
		return monthComparison == COMPARISON_FIRST_EQUALS_SECOND;
	}

	private boolean isSameYear(int yearComparison) {
		return yearComparison == COMPARISON_FIRST_EQUALS_SECOND;
	}
	

	private int compareDay(DateTime first, DateTime second) {
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

	private int compareMonth(DateTime first, DateTime second) {
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

	private int compareYear(DateTime first, DateTime second) {
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

	private boolean compareDateStrings(DateTime first, DateTime second) {
		return first.getDate().equals(second.getDate());
	}

	public boolean equals(DateTime dateTime) {
		boolean hasEqualDateString = this.getDate().equals(dateTime.getDate());
		boolean hasEqualTimeStr24H = this.getTime24hStr().equals(dateTime.getTime24hStr());
		boolean hasEqualTimeStr12H = this.getTime12hStr().equals(dateTime.getTime12hStr());
		boolean hasEqualDayStr = this.getDayStr().equals(dateTime.getDayStr());
		boolean hasEqualDayInt = this.getDayInt() == dateTime.getDayInt();
		return hasEqualDateString && hasEqualTimeStr24H && hasEqualTimeStr12H && hasEqualDayStr && hasEqualDayInt;
	}
	
	private int setDayInt(String str) {
		int ans = UNINITIALIZED_INT;
		for (int i = 0; i < daysInWeek.length; i++) {
			if (str.equals(daysInWeek[i])) {
				ans = i;
				break;
			}
		}
		return ans;
	}

	// Converts the 24h time int to 12h String format
	private String convertTimeIntTo12hString(int time) {
		String timeString12H;
		if (isUninitialized(time)) {
			timeString12H = UNINITIALIZED_STRING;
		} else if (isMidnight(time)) {
			timeString12H = TWELVE_MIDNIGHT;
		} else if (isAfter12amAndBefore1am(time)) {
			timeString12H = TWELVE + TIME_SEPARATOR_DOT + time + AM;
		} else if (isAfter1amAndBefor12pm(time)) {
			if (timeHasNoMinutes(time)) { // 12 am etc
				timeString12H = getHourNumeralIn12HStringForMorningTiming(time) + AM;
			} else { // 12.30 am etc
				timeString12H = getHourNumeralIn12HStringForMorningTiming(time) + TIME_SEPARATOR_DOT + getMinutesNumeral(time) + AM;
			}
		} else {
			if (timeHasNoMinutes(time)) { // 12 pm etc
				timeString12H = getHourNumberalIn12HStringForAfternoonTiming(time) + PM;
			} else { // 12.30 pm etc
				if (timeHasLessThanTenMinutes(time)) {
					timeString12H = getHourNumberalIn12HStringForAfternoonTiming(time) + TIME_SEPARATOR_DOT + ONE_ZERO + getMinutesNumeral(time) + PM;
				} else {
					timeString12H = getHourNumberalIn12HStringForAfternoonTiming(time) + TIME_SEPARATOR_DOT + getMinutesNumeral(time) + PM;
				}
			}
		}
		return timeString12H;
	}

	private int getHourNumeralIn12HStringForMorningTiming(int time) {
		return getHourNumeral(time);
	}

	private int getHourNumberalIn12HStringForAfternoonTiming(int time) {
		return getHourNumeral(time - FORMAT_24H_12PM);
	}

	private int getHourNumeral(int time) {
		return time / 100;
	}

	private boolean timeHasLessThanTenMinutes(int time) {
		return getMinutesNumeral(time) < 10;
	}

	private boolean timeHasNoMinutes(int time) {
		return getMinutesNumeral(time) == 0;
	}

	private int getMinutesNumeral(int time) {
		return time % 100;
	}

	private boolean isUninitialized(int time) {
		return time == UNINITIALIZED_INT;
	}

	private boolean isAfter1amAndBefor12pm(int time) {
		return time > FORMAT_24H_1AM && time < FORMAT_24H_12PM;
	}

	private boolean isAfter12amAndBefore1am(int time) {
		return time < FORMAT_24H_1AM && time > FORMAT_24H_12AM;
	}

	private boolean isMidnight(int time) {
		return time == FORMAT_24H_12AM;
	}

	// Converts the 24h time int to 24h String format
	private String convertTimeIntTo24hString(int time) {
		String timeString24H;
		if (isUninitialized(time)) {
			timeString24H = UNINITIALIZED_STRING;
		} else if (isMidnight(time)) {
			timeString24H = "0000";
		} else if (isAfter12amAndBefore1am(time)) {
			timeString24H = "00" + time;
		} else if (isAfter1amAndBefore10am(time)) {
			timeString24H = "0" + time;
		} else {
			timeString24H = String.valueOf(time);
		}
		timeString24H = timeString24H.substring(0,2) + ":" + timeString24H.substring(2);
		return timeString24H;
	}

	private boolean isAfter1amAndBefore10am(int time) {
		return time > FORMAT_24H_1AM && time < FORMAT_24H_10AM;
	}

	public String getDayStr() {
		return this.dayStr;
	}

	public int getDayInt() {
		return this.dayInt;
	}

	public String getDate() {
		return this.date;
	}

	public String getTime24hStr() {
		return this.timeStr24H;
	}

	public String getTime12hStr() {
		return this.timeStr12H;
	}

	public int getTimeInt() {
		return this.timeInt;
	}

	public int getDD() {
		return this.dd;
	}

	public int getMM() {
		return this.mm;
	}

	public int getYY() {
		return this.yy;
	}

	public String toString() {
		String ans = this.date + FORMAT_SPACE + this.dayStr + FORMAT_SPACE + this.timeStr24H + FORMAT_SPACE + this.timeStr12H;
		return ans;
	}

	public boolean hasTime() {
		return this.getTimeInt() != UNINITIALIZED_INT;
	}

	public boolean isTheSameDateAs(DateTime obj) {
		return this.getDD() == obj.getDD() && this.getMM() == obj.getMM() && this.getYY() == obj.getYY();
	}

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
		this.dayInt = increaseDayIntByOne();
		this.dayStr = daysInWeek[this.dayInt];
		this.date = this.dd + FORMAT_SPACE + months[decrementByOne(this.mm)] + FORMAT_SPACE + this.yy;
	}

	private int increaseDayIntByOne() {
		if (this.dayInt == NUM_DAYS_IN_A_WEEK) {
			return 1;
		} else {
			this.dayInt += 1;
			return this.dayInt;
		}
	}

	public String convertToSavableString() {
		String dateTimeString = "";
		dateTimeString += String.valueOf(dd) + FORMAT_SPACE;
		dateTimeString += String.valueOf(mm) + FORMAT_SPACE;
		dateTimeString += String.valueOf(yy) + FORMAT_SPACE;
		dateTimeString += dayStr + FORMAT_SPACE;
		dateTimeString += timeStr24H;

		return dateTimeString;
	}

}
