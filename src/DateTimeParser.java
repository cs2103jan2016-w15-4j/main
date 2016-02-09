import java.util.TimeZone;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class DateTimeParser {
	private static final int TWELVE_HOURS = 1200;
	private static final int NUM_DAYS_IN_WEEK = 7;
	private static final int INDEX_DATE = 0;
	private static final int INDEX_TIME = 1;
	private static final int INDEX_DAY = 2;
	private static final int INDEX_DAY_OF_WEEK = 3;
	private static final int NEXT_DAY = 1;
	DateFormat dateFormat;
	String DEFAULT_TIME_ZONE = "UTC+08:00"; // Singapore Time Zone
	
	// Can just look for substrings
	String[] daysInWeek = new String[]{"mon", "tue", "wed", "thur", "fri", "sat", "sun"};
	int DUMMY_VARIABLE = 100;
	int LEAP_YEAR_FEB = 29;
	int[] daysInMonth = new int[]{DUMMY_VARIABLE, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	int[] arrLeapYears = new int[]{2016, 2020, 2024, 2028, 2032, 2036, 2040, 2044, 2048, 2052, 
								   2056, 2060, 2064, 2068, 2072, 2076, 2080, 2084, 2088, 2092, 2096};
	
	String THIS = "this";
	String NEXT = "next";
	
	int currMonth;
	int currYear;
	int currDay;
	int today;
	
	String currDate;
	String currDayInWeek;
	int currTime;
	boolean isLeapYear;
	
	/*public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.print("command: ");
			DateTimeParser dt = new DateTimeParser();
			dt.parse(sc.nextLine());
		}
		
	}*/
	
	public DateTimeParser() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm E u");
		
		setCurrVariables(dateFormat.format(cal.getTime()));
	}

	private void setCurrVariables(String date) {
		String[] splitDate = date.split("\\s+");
		currDate = splitDate[INDEX_DATE];
		currTime = Integer.parseInt(splitDate[INDEX_TIME].replace(":", ""));
		// currTime = splitDate[INDEX_TIME];
		// System.out.println("currTime is " + currTime);
		currDayInWeek = splitDate[INDEX_DAY];
		today = Integer.parseInt(splitDate[INDEX_DAY_OF_WEEK]);
		
		String[] splitCurrDate = currDate.split("/");
		currDay = Integer.parseInt(splitCurrDate[0]);
		currMonth = Integer.parseInt(splitCurrDate[1]);
		currYear = Integer.parseInt(splitCurrDate[2]);
		
		isLeapYear = checkIfLeapYear(currYear);
	}

	private boolean checkIfLeapYear(int currYear) {
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

	public DateTime parse(String input) {
		String[] splitInput = input.toLowerCase().split("\\s+");
		int fastForward = 0;
		int day = -1;
		int time = -1;
		int[] date = new int[3]; // [DD, MM, YY]
		
		for(int i = 0; i < splitInput.length; i++) {
			String currWord = splitInput[i].toLowerCase();
			if(currWord.equals(THIS) &&  i != splitInput.length - 1) { 
				day = getDay(splitInput[i + 1]);
				fastForward = getFastForward(day);
				date = getDate(fastForward);
				i = i + 1;
				
			} else if(currWord.equals(NEXT)  &&  i != splitInput.length - 1) {
				day = getDay(splitInput[i + 1]);
				fastForward = getFastForward(day) + NUM_DAYS_IN_WEEK;
				date = getDate(fastForward);
				i = i + 1;
				
			} else if(isValidTime(currWord)) {
				time = getTime(splitInput, i);
				if(i != splitInput.length - 1) {
					if(splitInput[i + 1].equals("pm") || splitInput[i + 1].equals("am")) {
						i = i + 1;
					}
				}
				
			} else {
				day = getDay(currWord);
				fastForward = getFastForward(day);
				date = getDate(fastForward);
			}
			
		}
		
		if(day != -1) {
			DateTime ans = new DateTime(date, daysInWeek[day], time);
			// System.out.println(ans.toString());
			return ans;
		} else {	// Only time was given
			if(hasPassed(currTime, time)) {
				date = getDate(NEXT_DAY);
				DateTime ans = new DateTime(date, daysInWeek[today + NEXT_DAY], time);
				//System.out.println("the ans is " + ans.toString());
				return ans;
				
			} else {
				DateTime ans = new DateTime(currDate.split("/"), currDayInWeek, time);
				//System.out.println("the ans is " + ans.toString());
				return ans;
			}
		}
		
		//return null
	}

	private boolean hasPassed(int currTime2, int time) {
		return currTime2 > time;
		//return true;
				
	}

	private boolean isValidTime(String currWord) {
		boolean ans = false;
		
		if(currWord.contains("pm") || currWord.contains("am")) {
			ans = true;
		} else {
			try {
				Integer.parseInt(currWord);
				ans = true;
			} catch(NumberFormatException e) {
				
			}
		}
		// TODO Auto-generated method stub
		return ans;
	}

	private int getFastForward(int day) {
		int fastForward = 0;
		if(today > day) {
			fastForward = NUM_DAYS_IN_WEEK - today + day;
		} else if(today < day) {
			fastForward =  day - today;
		} else { // if(currDay == day)
			fastForward = 0;
		}
		return fastForward;
	}

	private int[] getDate(int fastForward) {
		int newDay = currDay + fastForward;
		int newMonth = currMonth;
		int newYear = currYear;
		
		if(newDay > daysInMonth[currMonth]) {
			newDay = newDay - daysInMonth[currMonth];
			newMonth = newMonth + 1;
		}
		
		if(newMonth > 12) {
			newMonth = 1;
			newYear = newYear + 1;
		}
		
		int[] ans = new int[]{newDay, newMonth, newYear};
		return ans;
	}

	private int getTime(String[] splitInput, int i) {
		String timeString = splitInput[i].replace(":", "").replace(".", "");
		boolean isAm = timeString.contains("am");
		boolean isPm = timeString.contains("pm");
		int timeInt = -1;
		boolean isExactlyMidnight = timeString.equals("12am");
		
		if(isAm || isPm) {
			timeString = timeString.replace("am", "");
			timeString = timeString.replace("pm", "");
		}
		
		if(isAm && isPm) {
			System.out.println("Invalid Time!");
			System.exit(0);
		}
		
		try{
			timeInt = Integer.parseInt(timeString);
		} catch(NumberFormatException e) {
			System.out.println("Invalid Time!");
			System.exit(0);
		}
		
		if(timeInt % 100 > 59) {
			System.out.println("Invalid Time");
			System.exit(0);
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
		
		if(i + 1 < splitInput.length) {
			String indicator = splitInput[i + 1];
			if(indicator.equals("pm") || isPm) {
				timeInt = timeInt + TWELVE_HOURS;
			}	
		}
		if(timeInt >= 2400) {
			System.out.println("InvalidTime");
			System.exit(0);
		}
		return timeInt;
	}
	
	private boolean isMidnight(int timeInt, String timeString, boolean isAm) {
		boolean isMidnight24H = timeInt >= 0 && timeInt <= 59 && timeString.contains("00");
		boolean isMidnight12H = timeInt >= 1200 && timeInt <= 1259 && isAm;
		return isMidnight24H || isMidnight12H;
	}

	private int getDay(String string) {
		int day = -1;
		for(int i = 0; i < daysInWeek.length; i++) {
			if(string.contains(daysInWeek[i])) {
				day = i + 1;
			}
		}
		return day;
	}

}
