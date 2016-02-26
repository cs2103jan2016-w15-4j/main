package dooyit.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class DateTime {
	private static final int UNINITIALIZED = -1;
	private static final String NO_TIME_INDICATED = "NIL";
	private static final String CALENDAR_DATE_FORMAT = "dd MM yyyy HH:mm E u";
	private static final String CALENDAR_DEFAULT_TIME_ZONE = "UTC+08:00"; // Singapore Time Zone
	
	private static final int INDEX_DD = 0;
	private static final int INDEX_MM = 1;
	private static final int INDEX_YY = 2;
	private static final int INDEX_TIME_INT = 3;
	private static final int INDEX_DAY_STRING = 4;
	private static final int INDEX_DAY_INT = 5;
	
	private String date;	// 08/02/2016
	private String timeStr24H;		// 1300
	private String timeStr12H;		// 1 pm, 2 am, 3.30 am
	private String dayStr;		// Mon
	private int dayInt;		//1
	private int dd;			// 8
	private int mm;			
	private int yy;			// 2016 	
	private int timeInt;
	private String[] months = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	private String[] daysInWeek = new String[] {"Mon", "Tue", "Wed", "Thur", "Fri", "Sat", "Sun"};
	
	public DateTime(int[] date, String day, int time) {
		this.dd = date[INDEX_DD];
		this.mm = date[INDEX_MM];
		this.yy = date[INDEX_YY];
		this.timeInt = time;
		this.timeStr24H = convertTimeIntTo24hString(time);
		this.timeStr12H = convertTimeIntTo12hString(time);
		this.dayStr = day;
		this.dayInt = setDayInt(dayStr);
		this.date = this.dd + " " + months[this.mm - 1] + " " + this.yy;
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
		this.dayStr = splitDate[INDEX_DAY_STRING];
		this.dayInt = Integer.parseInt(splitDate[INDEX_DAY_INT]);
		this.timeStr24H = convertTimeIntTo24hString(this.timeInt);
		this.timeStr12H = convertTimeIntTo12hString(this.timeInt);
		this.date = this.dd + " " + months[this.mm - 1] + " " + this.yy;
	}
	
	public DateTime(int[] date, String day) {		
		this.dd = date[INDEX_DD];
		this.mm = date[INDEX_MM];
		this.yy = date[INDEX_YY];
		this.timeStr24H = String.valueOf(UNINITIALIZED);
		this.timeStr12H = String.valueOf(UNINITIALIZED);
		this.dayStr = day;
		this.dayInt = setDayInt(dayStr);
		this.date = this.dd + " " + months[this.mm - 1] + " " + this.yy;
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
			System.out.println("time is " + time);
			System.out.println(e.getMessage());
		}
		this.dayStr = day;
		this.dayInt = setDayInt(dayStr);
		this.date = this.dd + " " + months[this.mm - 1] + " " + this.yy;
	}
	
	public DateTime(String[] split, String day, int time2) {
		this.dd = Integer.parseInt(split[INDEX_DD]);
		this.mm = Integer.parseInt(split[INDEX_MM]);
		this.yy = Integer.parseInt(split[INDEX_YY]);
		this.timeStr24H = convertTimeIntTo24hString(time2);
		this.timeStr12H = convertTimeIntTo12hString(time2);
		this.dayStr = day;
		this.dayInt = setDayInt(dayStr);
		this.date = this.dd + " " + months[this.mm - 1] + " " + this.yy;
	}
	
	private int setDayInt(String str) {
		int ans = UNINITIALIZED;
		for(int i = 0; i < daysInWeek.length; i++) {
			if(str.equals(daysInWeek)) {
				ans = i + 1;
				break;
			}
		}
		return ans;
	}

	//Converts the 24h time int to 12h String format
	private String convertTimeIntTo12hString(int time2) {
		String temp;
		if(time2 == -1) {
			temp = NO_TIME_INDICATED;
		} else if(time2 == 0) {
			temp = "12 am";
		} else if(time2 < 100 && time2 > 0) {
			temp = "12." + time2 + " am";
		} else if(time2 > 100 && time2 < 1200) {
			if(time2 % 100 == 0) {		//12 am etc
				temp = time2 / 100 + " am";
			} else {					//12.30 am etc
				temp = time2 / 100 + "." + time2 % 100 + " am";
			}
		} else {
			if(time2 % 100 == 0) {		//12 pm etc
				temp = (time2 - 1200) / 100 + " pm";
			} else {					//12.30 pm etc
				if(time2 % 100 < 10) {
					temp = (time2 - 1200) / 100 + ".0" + time2 % 100 + " pm";
				} else {
					temp = (time2 - 1200) / 100 + "." + time2 % 100 + " pm";
				}
			}
		}
		return temp;
	}
	
	//Converts the 24h time int to 24h String format
	private String convertTimeIntTo24hString(int time2) {
		String temp;
		if(time2 == -1) {
			temp = NO_TIME_INDICATED;
		} else if(time2 == 0) {
			temp = "0000";
		} else if(time2 < 100 && time2 > 0) {
			temp = "00" + time2;
		} else if(time2 > 100 && time2 < 1000) {
			temp = "0" + time2;
		} else {
			temp = String.valueOf(time2);
		}
		return temp;
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
		if(this.dd > DateTimeParser.daysInMonth[this.mm]) {
			this.dd = 1;
			this.mm += 1;
		}
		if(this.mm > 12) {
			this.mm = 1;
			this.yy += 1;
		}
		this.dayInt = increaseDayIntByOne();
		this.dayStr = daysInWeek[this.dayInt - 1];
		this.date = this.dd + " " + months[this.mm - 1] + " " + this.yy;
	}
	
	private int increaseDayIntByOne() {
		if(this.dayInt == 7) {
			return 1;
		} else {
			this.dayInt += 1;
			return this.dayInt;
		}
	}

	public String convertToSavableString(){
		String dateTimeString = "";
		dateTimeString += String.valueOf(dd) + " ";
		dateTimeString += String.valueOf(mm) + " ";
		dateTimeString += String.valueOf(yy) + " ";
		dateTimeString += dayStr + " ";
		dateTimeString += timeStr24H;
		
		return dateTimeString;
	}
	
}
