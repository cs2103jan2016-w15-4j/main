package dooyit.common.datatype;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import dooyit.parser.DateTimeParser;

public class DateTime {
	private static final int UNINITIALIZED = -1;
	private static final String NO_TIME_INDICATED = "NIL";
	private static final String CALENDAR_DATE_FORMAT = "dd MM yyyy HH:mm E u";
	private static final String CALENDAR_DEFAULT_TIME_ZONE = "UTC+08:00"; // Singapore Time Zone

	private static final int INDEX_DD = 0;
	private static final int INDEX_MM = 1;
	private static final int INDEX_YY = 2;
	private static final int INDEX_TIME_INT = 3;
	private static final int INDEX_DAY_INT = 5;
	private static final String DUMMY_STR = "Dummy_Str";

	private String date; // 08/02/2016
	private String timeStr24H; // 1300
	private String timeStr12H; // 1 pm, 2 am, 3.30 am
	private String dayStr; // Mon
	private int dayInt; // 1
	private int dd; // 8
	private int mm;
	private int yy; // 2016
	private int timeInt;
	private String[] months = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
			"Nov", "Dec" };
	private String[] daysInWeek = new String[] { DUMMY_STR, "mon", "tue", "wed", "thu", "fri", "sat", "sun" };

	public DateTime(DateTime dt) {
		this.dd = dt.getDD();
		this.mm = dt.getMM();
		this.yy = dt.getYY();
		this.timeInt = dt.getTimeInt();
		this.timeStr12H = dt.getTime12hStr();
		this.timeStr24H = dt.getTime24hStr();
		this.dayStr = dt.getDayStr();
		this.dayInt = setDayInt(this.dayStr);
		this.date = this.dd + " " + months[decrementByOne(this.mm)] + " " + this.yy;
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
		this.date = this.dd + " " + months[decrementByOne(this.mm)] + " " + this.yy;
	}

	public DateTime() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(CALENDAR_DEFAULT_TIME_ZONE));
		DateFormat dateFormat = new SimpleDateFormat(CALENDAR_DATE_FORMAT);

		String date = dateFormat.format(cal.getTime());
		String[] splitDate = date.split("\\s+");

		this.dd = Integer.parseInt(splitDate[INDEX_DD]);
		this.mm = Integer.parseInt(splitDate[INDEX_MM]);
		this.yy = Integer.parseInt(splitDate[INDEX_YY]);
		this.timeInt = Integer.parseInt(splitDate[INDEX_TIME_INT].replace(":", ""));
		this.dayInt = Integer.parseInt(splitDate[INDEX_DAY_INT]);
		this.dayStr = daysInWeek[this.dayInt];
		this.timeStr24H = convertTimeIntTo24hString(this.timeInt);
		this.timeStr12H = convertTimeIntTo12hString(this.timeInt);
		this.date = this.dd + " " + months[decrementByOne(this.mm)] + " " + this.yy;
	}

	public DateTime(int[] date, String day) {
		this.dd = date[INDEX_DD];
		this.mm = date[INDEX_MM];
		this.yy = date[INDEX_YY];
		this.timeStr24H = String.valueOf(UNINITIALIZED);
		this.timeStr12H = String.valueOf(UNINITIALIZED);
		this.dayStr = day;
		this.dayInt = setDayInt(dayStr);
		this.date = this.dd + " " + months[decrementByOne(this.mm)] + " " + this.yy;
	}

	public DateTime(int dd, int mm, int yy, String day, String time) {
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
			this.timeInt = UNINITIALIZED;
			this.timeStr24H = NO_TIME_INDICATED;
			this.timeStr12H = NO_TIME_INDICATED;
		}
		this.dayStr = day;
		this.dayInt = setDayInt(dayStr);
		this.date = this.dd + " " + months[decrementByOne(this.mm)] + " " + this.yy;
	}

	public DateTime(String[] split, String day, int time2) {
		this.dd = Integer.parseInt(split[INDEX_DD]);
		this.mm = Integer.parseInt(split[INDEX_MM]);
		this.yy = Integer.parseInt(split[INDEX_YY]);
		this.timeStr24H = convertTimeIntTo24hString(time2);
		this.timeStr12H = convertTimeIntTo12hString(time2);
		this.dayStr = day;
		this.dayInt = setDayInt(dayStr);
		this.date = this.dd + " " + months[decrementByOne(this.mm)] + " " + this.yy;
	}

	private int setDayInt(String str) {
		int ans = UNINITIALIZED;
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
		if (time == -1) {
			timeString12H = NO_TIME_INDICATED;
		} else if (isMidnight(time)) {
			timeString12H = "12 am";
		} else if (isAfter12amAndBefore1am(time)) {
			timeString12H = "12." + time + " am";
		} else if (isAfter1amAndBefor12pm(time)) {
			if (time % 100 == 0) { // 12 am etc
				timeString12H = time / 100 + " am";
			} else { // 12.30 am etc
				timeString12H = time / 100 + "." + time % 100 + " am";
			}
		} else {
			if (time % 100 == 0) { // 12 pm etc
				timeString12H = (time - 1200) / 100 + " pm";
			} else { // 12.30 pm etc
				if (time % 100 < 10) {
					timeString12H = (time - 1200) / 100 + ".0" + time % 100 + " pm";
				} else {
					timeString12H = (time - 1200) / 100 + "." + time % 100 + " pm";
				}
			}
		}
		return timeString12H;
	}

	private boolean isAfter1amAndBefor12pm(int time) {
		return time > 100 && time < 1200;
	}

	private boolean isAfter12amAndBefore1am(int time) {
		return time < 100 && time > 0;
	}

	private boolean isMidnight(int time) {
		return time == 0;
	}

	// Converts the 24h time int to 24h String format
	private String convertTimeIntTo24hString(int time) {
		String timeString24H;
		if (time == -1) {
			timeString24H = NO_TIME_INDICATED;
		} else if (isMidnight(time)) {
			timeString24H = "0000";
		} else if (isAfter12amAndBefore1am(time)) {
			timeString24H = "00" + time;
		} else if (isAfter1amAndBefore10am(time)) {
			timeString24H = "0" + time;
		} else {
			timeString24H = String.valueOf(time);
		}
		return timeString24H;
	}

	private boolean isAfter1amAndBefore10am(int time) {
		return time > 100 && time < 1000;
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
		String ans = this.date + " " + this.dayStr + " " + this.timeStr24H + " " + this.timeStr12H;
		return ans;
	}

	public boolean hasTime() {
		return this.getTimeInt() != UNINITIALIZED;
	}

	public boolean isTheSameDateAs(DateTime obj) {
		return this.getDD() == obj.getDD() && this.getMM() == obj.getMM() && this.getYY() == obj.getYY();
	}

	public void increaseByOne() {
		this.dd += 1;
		if (this.dd > DateTimeParser.daysInMonth[this.mm]) {
			this.dd = 1;
			this.mm += 1;
		}
		if (this.mm > 12) {
			this.mm = 1;
			this.yy += 1;
		}
		this.dayInt = increaseDayIntByOne();
		this.dayStr = daysInWeek[this.dayInt];
		this.date = this.dd + " " + months[decrementByOne(this.mm)] + " " + this.yy;
	}

	private int increaseDayIntByOne() {
		if (this.dayInt == 7) {
			return 1;
		} else {
			this.dayInt += 1;
			return this.dayInt;
		}
	}

	public String convertToSavableString() {
		String dateTimeString = "";
		dateTimeString += String.valueOf(dd) + " ";
		dateTimeString += String.valueOf(mm) + " ";
		dateTimeString += String.valueOf(yy) + " ";
		dateTimeString += dayStr + " ";
		dateTimeString += timeStr24H;

		return dateTimeString;
	}

}
