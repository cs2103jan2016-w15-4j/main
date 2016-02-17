package dooyit.parser;
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

	private static final String DEFAULT_TIME_ZONE = "UTC+08:00"; // Singapore Time Zone
	private static final String DUMMY_STR = "Dummy_Str";
	private static final int DUMMY_INT = 100;
	
	DateFormat dateFormat;
	
	// Can just look for substrings
	String[] daysInWeek = new String[]{DUMMY_STR, "mon", "tue", "wed", "thur", "fri", "sat", "sun"};
	String[] monthsInYear = new String[]{DUMMY_STR, "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};

	int LEAP_YEAR_FEB = 29;
	int[] daysInMonth = new int[]{DUMMY_INT, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

	
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
	private static Scanner sc;
	
	public static void main(String[] args) {
		sc = new Scanner(System.in);
		
		while(true) {
			System.out.print("command: ");
			DateTimeParser dt = new DateTimeParser();
			dt.parse(sc.nextLine());
		}
		
	}
	
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
		
		if(isLeapYear(currYear)) {
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

	public DateTime parse(String input) {
		String[] splitInput = input.toLowerCase().split("\\s+");
		int fastForward = 0;
		int day = -1;
		int time = -1;
		int[] date = new int[3]; // [DD, MM, YY]
		
		for(int i = 0; i < splitInput.length; i++) {
			String currWord = splitInput[i].toLowerCase();
			
			if(currWord.equals(THIS) &&  i != splitInput.length - 1) { 
				System.out.println("Part E");
				day = convertDayStrToInt(splitInput[i + 1]);
				fastForward = getFastForward(day);
				date = getDate(fastForward);
				i += 1;				// To skip the next word
				
			} else if(currWord.equals(NEXT)  &&  i != splitInput.length - 1) {
				System.out.println("Part f");
				if(nextWordIsWeek(splitInput, i)) {
					day = today;
					fastForward = getFastForward(day) + NUM_DAYS_IN_WEEK;
					date = getDate(fastForward);
					i += 1;				// To skip the next word
					
				} else if(nextWordIsValidDay(splitInput, i)) {
					day = convertDayStrToInt(splitInput[i + 1]);
					fastForward = getFastForward(day) + NUM_DAYS_IN_WEEK;
					date = getDate(fastForward);
					i += 1;				// To skip the next word
					
				} else {
					System.out.println("Invalid Cmd!");
				}

			} else if(isTodayOrTmr(currWord)) {
				System.out.println("Part G");
				if(isToday(currWord)) {
					day = today;
					date = getDate(0);
				} else if(isTmr(currWord)) {
					day = getNextDayInt();
					date = getDate(NEXT_DAY);
				}
				
			} else if(isDate(splitInput, i) && !isValidTime(splitInput, i)) {
				System.out.println("part P");
				date = getDate(splitInput, i);
				day = getDay(date);
				
				int temp = getAdvanceInt(splitInput, i); 
				i += temp;
				System.out.println("getAdvanceInt(splitInput, i) is " + temp);
			} else if(isValidTime(splitInput, i)) { 		//8pm, 8.30am, 8 am etc
				System.out.println("part M");
				time = getTime(splitInput, i);

				if(i != splitInput.length - 1) {
					if(splitInput[i + 1].equals("pm") || splitInput[i + 1].equals("am")) {
						i += 1;		// To skip the next word
					}
				}
			} else if(isNumber(currWord)){
				int tempNum = Integer.parseInt(currWord);
				
				if(splitInput[i + 1].equals("days") || splitInput[i + 1].equals("day")) {
					System.out.println("Part A");
					day = get_Int_Day_From(tempNum);
					date = getDate(tempNum);
					i += 1;				// To skip the next word
					
				} else if(nextWordIsWeek(splitInput, i)) {
					System.out.println("Part B");
					day = today;
					fastForward = getFastForward(day) + NUM_DAYS_IN_WEEK*tempNum;
					date = getDate(fastForward);
					i += 1;				// To skip the next word
				}
			} else if(isValidDay(currWord)){		//Mon, Tue etc
				System.out.println("Part C");
				day = convertDayStrToInt(currWord);
				fastForward = getFastForward(day);
				date = getDate(fastForward);
				
			} else {
				//do nothing cuz invalid command
			}
		}
		
		
		// These if else statements fix the correct dates into the DateTime object
		DateTime ans;
		if(day != -1) {
			ans = new DateTime(date, daysInWeek[day], time);
			System.out.println(ans.toString());
		} else {	// Only time was given
			if(hasPassed(currTime, time)) {
				System.out.println("part Z");
				date = getDate(NEXT_DAY);
				ans = new DateTime(date, daysInWeek[getNextDayInt()], time);
				System.out.println("the ans is " + ans.toString());
			} else {
				System.out.println("part Y");
				ans = new DateTime(currDate.split("/"), currDayInWeek, time);
				System.out.println("the ans is " + ans.toString());
			}
		}
		
		if(hasPassed(ans)) {
			System.out.println("You can't go back in time!!");
		}
		
		return ans;
	}
	
	private boolean hasPassed(DateTime ans) {
		return ans.getDD() < currDay || ans.getMM() < currMonth || ans.getYY() < currYear || ans.getTimeInt() < currTime;
	}

	private boolean isValidDay(String currWord) {
		return convertDayStrToInt(currWord) != -1;
	}

	private int getAdvanceInt(String[] splitInput, int i) {
		int[] temp = getDateAndAdvanceInt(splitInput, i);		
		return temp[3];
	}

	private int[] getDate(String[] splitInput, int i) {
		int[] temp = getDateAndAdvanceInt(splitInput, i);
		int[] ans = new int[]{temp[0], temp[1], temp[2]};
		return ans;
	}

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
		int numEntries = 0;
		
		if(currWord.contains("/")) {
			String[] temp = currWord.split("/");
			for(int j = 0; j < temp.length; j++) {
				ans[j] = Integer.parseInt(temp[j]);
				System.out.println("numEntries is " + numEntries);
				
			}
			numEntries = 1;
		} else {
			
			int counter = 0;
			for(int j = i; j < splitInput.length; j++) {
				if(counter > 2) {
					break;
				} else {
					String currWord2 = splitInput[j];
					System.out.println("currWord2 is " + currWord2);
					if(isNumber(currWord2)) { //word is dd or yy
						int currInt = Integer.parseInt(currWord2);
						if(currInt <= 31 && ans[0] == -1) {
							//dd = currInt;
							ans[0] = currInt;
							numEntries++;
							
						} else if(currInt >= 2000 && ans[2] == -1){
							//yy = currInt;
							if(ans[2] == -1) {
								ans[2] = currInt;
								numEntries++;
							}
						}
					} else if(isMonth(currWord2) && ans[1] == -1) {
						//mm = convertMonthStrToInt(currWord2);
						ans[1] = convertMonthStrToInt(currWord2);
						numEntries++;
						
					}
				}
				counter++;
			}
		}
		
		if(ans[2] == -1) {
			ans[2] = currYear;
		}
		
		if(ans[0] == -1) {
			System.out.println("reached here");
			ans[0] = currDay;
		}
		
		ans[3] = numEntries - 1;
		return ans;
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

	private boolean isDate(String[] splitInput, int i) {
		String currWord = splitInput[i];
		boolean ans = false;
		
		if(currWord.contains("/")) {		//Date is 11/2/2016
			ans = true;
		} else {
			int numEntries = 0;
			boolean hasMM = false;
			for(int j = i; j < splitInput.length; j++) {
				if(numEntries > 3) {
					break;
				} else {
					String currWord2 = splitInput[j];
					if(!isNumber(currWord2) && isMonth(currWord2)) {
						hasMM = true;
					}
				}
				numEntries++;
			}
			ans = hasMM;
		}
		return ans;
	}

	private boolean isMonth(String nextWord) {
		boolean ans = false;
		for(int i = 1; i < monthsInYear.length; i++) {
			if(nextWord.equals(monthsInYear[i])) {
				ans = true;
				break;
			}
		}
		return ans;
	}

	private boolean nextWordIsValidDay(String[] splitInput, int i) {
		String nextWord = splitInput[i + 1];
		return isValidDay(nextWord);
	}

	private boolean nextWordIsWeek(String[] splitInput, int i) {
		return splitInput[i + 1].equals("week") || splitInput[i + 1].equals("weeks") || splitInput[i + 1].equals("wk");
	}

	private boolean isNumber(String currWord) {
		return currWord.matches("[0-9]+");
	}

	private boolean isTodayOrTmr(String currWord) {
		return isToday(currWord) || isTmr(currWord);
	}
	
	private boolean isToday(String currWord) {
		return currWord.equals("today") || currWord.equals("tdy");
	}
	
	private boolean isTmr(String currWord) {
		return currWord.equals("tomorrow") || currWord.equals("tmr");
	}

	private int getNextDayInt() {
		return today + NEXT_DAY - 1;
	}

	private boolean hasPassed(int currTime2, int time) {
		return currTime2 > time;			
	}

	private boolean isValidTime(String[] splitInput, int i) {
		String currWord = splitInput[i];
		boolean ans = false;
		
		if(currWord.contains("pm") || currWord.contains("am")) {			// what if its 25am??
			ans = true;
		} else {
			if(isNumber(currWord) && i + 1 < splitInput.length) {
				String nextWord = splitInput[i + 1];
				System.out.println("nextWord is " + nextWord);
				if(nextWord.equals("am") || nextWord.equals("pm")) {
					ans = true;
				}
			}
		}
		return ans;
	}
	
	// Input number of days to fastforward from today
	// Output the day of the week
	private int get_Int_Day_From(int daysLater) {
		System.out.println("today is " + today);
		int dayOfWeek = daysLater + today;
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
		if(today > desiredDay) {
			fastForward = NUM_DAYS_IN_WEEK - today + desiredDay;
		} else if(today < desiredDay) {
			fastForward =  desiredDay - today;
		} else { // if(currDay == day)
			fastForward = 0;
		}
		return fastForward;
	}

	private int[] getDate(int fastForward) {
		int newDay = currDay + fastForward;
		int newMonth = currMonth;
		int newYear = currYear;
		
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

	private int getTime(String[] splitInput, int i) {
		String timeString = splitInput[i].replace(":", "").replace(".", "");
		System.out.println("timeString is " + timeString);
		boolean isAm = timeString.contains("am");
		boolean isPm = timeString.contains("pm");
		int timeInt = -1;
		boolean isExactlyMidnight = timeString.equals("12am"); 		// What about "12 am"???
		
		if(isAm || isPm) {
			timeString = timeString.replace("am", "");
			timeString = timeString.replace("pm", "");
		}
		
		if(isAm && isPm) {
			System.out.println("Invalid Time!");
		}
		timeInt = Integer.parseInt(timeString);
		
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
