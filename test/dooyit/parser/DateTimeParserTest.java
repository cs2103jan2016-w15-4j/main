package dooyit.parser;

import dooyit.parser.DateTimeParser;
import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DateTimeParserTest {
	private static final int FORMAT_24H_6AM = 600;
	
	private static final String REFERENCE_TODAY_DAY_STRING = "Wednesday";
	private static final String REFERENCE_TODAY_DATE = "17 Feb 2016";
	private static final int REFERENCE_TODAY_DAY_INT = 3;

	private static final String REFERENCE_TOMORROW_DAY_STRING = "Thursday";
	private static final String REFERENCE_TOMORROW_DATE = "18 Feb 2016";
	private static final int REFERENCE_TOMORROW_DAY_INT = 4;
	
	private static final String REFERENCE_NEXT_WEEK_DAY_STRING = "Wednesday";
	private static final String REFERENCE_NEXT_WEEK_DATE = "24 Feb 2016";
	private static final int REFERENCE_NEXT_WEEK_DAY_INT = 3;
	
	private static final String REFERENCE_THREE_WEEKS_LATER_DAY_STRING = "Wednesday";
	private static final String REFERENCE_THREE_WEEKS_LATER_DATE = "9 Mar 2016";
	private static final int REFERENCE_THREE_WEEKS_LATER_DAY_INT = 3;

	private static final String REFERENCE_TWELVE_DAYS_LATER_DAY_STRING = "Monday";
	private static final String REFERENCE_TWELVE_DAYS_LATER_DATE = "29 Feb 2016";
	private static final int REFERENCE_TWELVE_DAYS_LATER_DAY_INT = 1;
	
	private static final String REFERENCE_THIS_SATURDAY_DAY_STRING = "Saturday";
	private static final String REFERENCE_THIS_SATURDAY_DATE = "20 Feb 2016";
	private static final int REFERENCE_THIS_SATURDAY_DAY_INT = 6;
	
	private static final String REFERENCE_NEXT_SATURDAY_DAY_STRING = "Saturday";
	private static final String REFERENCE_NEXT_SATURDAY_DATE = "27 Feb 2016";
	private static final int REFERENCE_NEXT_SATURDAY_DAY_INT = 6;
	
	private static final String REFERENCE_THIS_MONDAY_DAY_STRING = "Monday";
	private static final String REFERENCE_THIS_MONDAY_DATE = "22 Feb 2016";
	private static final int REFERENCE_THIS_MONDAY_DAY_INT = 1;
	
	private static final String REFERENCE_NEXT_MONDAY_DAY_STRING = "Monday";
	private static final String REFERENCE_NEXT_MONDAY_DATE = "29 Feb 2016";
	private static final int REFERENCE_NEXT_MONDAY_DAY_INT = 1;
	
	DateTimeParser dateTimeParser, referenceDateTimeParser;
	int[] referenceDate = new int[] {17, 2, 2016};
	int referenceDayInt, referenceTime, todayDayInt;
	DateTime referenceDateTimeObject, todayDateTimeObject;
	String referenceDayString, todayDate, todayDayString;
	
	@Before
	public void setup() {
		dateTimeParser = new DateTimeParser();
		
		referenceDate = new int[] {17, 2, 2016};
		referenceDayString = "Wednesday";
		referenceDayInt= 3;
		referenceTime = FORMAT_24H_6AM;
		referenceDateTimeObject = new DateTime(referenceDate, referenceTime);
		referenceDateTimeParser = new DateTimeParser(referenceDateTimeObject);
		
		todayDateTimeObject = new DateTime();
		todayDate = todayDateTimeObject.getDate();
		todayDayString = todayDateTimeObject.getDayStr();
		todayDayInt = todayDateTimeObject.getDayInt();
	}
	
	//********************************************
	//******** Tests for RelativeDateParser ******
	//********************************************
	@Test
	public void parseSaturday() {
		String userInput = "saturday";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_THIS_SATURDAY_DATE, parsedDate);
		assertEquals(REFERENCE_THIS_SATURDAY_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_THIS_SATURDAY_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseSaturdayCaseInsensitive() {
		String userInput = "satURDay";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_THIS_SATURDAY_DATE, parsedDate);
		assertEquals(REFERENCE_THIS_SATURDAY_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_THIS_SATURDAY_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseThisSaturday() {
		String userInput = "this saturday";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_THIS_SATURDAY_DATE, parsedDate);
		assertEquals(REFERENCE_THIS_SATURDAY_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_THIS_SATURDAY_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseThisSaturdayCaseInsensitive() {
		String userInput = "ThIs satURDay";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_THIS_SATURDAY_DATE, parsedDate);
		assertEquals(REFERENCE_THIS_SATURDAY_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_THIS_SATURDAY_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseNextSaturday() {
		String userInput = "next saturday";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_NEXT_SATURDAY_DATE, parsedDate);
		assertEquals(REFERENCE_NEXT_SATURDAY_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_NEXT_SATURDAY_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseNextSaturdayCaseInsensitive() {
		String userInput = "nExT satURday";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_NEXT_SATURDAY_DATE, parsedDate);
		assertEquals(REFERENCE_NEXT_SATURDAY_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_NEXT_SATURDAY_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseMonday() {
		String userInput = "monday";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_THIS_MONDAY_DATE, parsedDate);
		assertEquals(REFERENCE_THIS_MONDAY_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_THIS_MONDAY_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseMondayCaseInsensitive() {
		String userInput = "mONdaY";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_THIS_MONDAY_DATE, parsedDate);
		assertEquals(REFERENCE_THIS_MONDAY_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_THIS_MONDAY_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseThisMonday() {
		String userInput = "this monday";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_THIS_MONDAY_DATE, parsedDate);
		assertEquals(REFERENCE_THIS_MONDAY_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_THIS_MONDAY_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseThisMondayCaseInsensitive() {
		String userInput = "thIs mONday";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_THIS_MONDAY_DATE, parsedDate);
		assertEquals(REFERENCE_THIS_MONDAY_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_THIS_MONDAY_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseNextMonday() {
		String userInput = "next monday";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_NEXT_MONDAY_DATE, parsedDate);
		assertEquals(REFERENCE_NEXT_MONDAY_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_NEXT_MONDAY_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseNextMondayCaseInsensitive() {
		String userInput = "NExt mONday";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_NEXT_MONDAY_DATE, parsedDate);
		assertEquals(REFERENCE_NEXT_MONDAY_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_NEXT_MONDAY_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseThisMon() {
		String userInput = "this mon";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_THIS_MONDAY_DATE, parsedDate);
		assertEquals(REFERENCE_THIS_MONDAY_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_THIS_MONDAY_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseThisMonCaseInsensitive() {
		String userInput = "tHIs mON";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_THIS_MONDAY_DATE, parsedDate);
		assertEquals(REFERENCE_THIS_MONDAY_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_THIS_MONDAY_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseTwelveDaysLater() {
		String userInput = "12 days";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_TWELVE_DAYS_LATER_DATE, parsedDate);
		assertEquals(REFERENCE_TWELVE_DAYS_LATER_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_TWELVE_DAYS_LATER_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseTwelveDaysLaterCaseInsensitive() {
		String userInput = "12 DAys";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_TWELVE_DAYS_LATER_DATE, parsedDate);
		assertEquals(REFERENCE_TWELVE_DAYS_LATER_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_TWELVE_DAYS_LATER_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseTwelveDay() {
		String userInput = "12 day";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_TWELVE_DAYS_LATER_DATE, parsedDate);
		assertEquals(REFERENCE_TWELVE_DAYS_LATER_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_TWELVE_DAYS_LATER_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseTwelveDayLaterCaseInsensitive() {
		String userInput = "12 DAy";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_TWELVE_DAYS_LATER_DATE, parsedDate);
		assertEquals(REFERENCE_TWELVE_DAYS_LATER_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_TWELVE_DAYS_LATER_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseTwelveDD() {
		String userInput = "12 dd";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_TWELVE_DAYS_LATER_DATE, parsedDate);
		assertEquals(REFERENCE_TWELVE_DAYS_LATER_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_TWELVE_DAYS_LATER_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseTwelveDDCaseInsensitive() {
		String userInput = "12 Dd";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_TWELVE_DAYS_LATER_DATE, parsedDate);
		assertEquals(REFERENCE_TWELVE_DAYS_LATER_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_TWELVE_DAYS_LATER_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseNextWeek() {
		String userInput = "next week";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_NEXT_WEEK_DATE, parsedDate);
		assertEquals(REFERENCE_NEXT_WEEK_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_NEXT_WEEK_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseNextWeekCaseInsensitive() {
		String userInput = "next wEEk";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_NEXT_WEEK_DATE, parsedDate);
		assertEquals(REFERENCE_NEXT_WEEK_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_NEXT_WEEK_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseNextWeeks() {
		String userInput = "next weeks";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_NEXT_WEEK_DATE, parsedDate);
		assertEquals(REFERENCE_NEXT_WEEK_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_NEXT_WEEK_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseNextWeeksCaseInsensitive() {
		String userInput = "next wEEks";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_NEXT_WEEK_DATE, parsedDate);
		assertEquals(REFERENCE_NEXT_WEEK_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_NEXT_WEEK_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseNextWk() {
		String userInput = "next wk";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_NEXT_WEEK_DATE, parsedDate);
		assertEquals(REFERENCE_NEXT_WEEK_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_NEXT_WEEK_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseThreeWeeksLater() {
		String userInput = "3 weeks";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_THREE_WEEKS_LATER_DATE, parsedDate);
		assertEquals(REFERENCE_THREE_WEEKS_LATER_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_THREE_WEEKS_LATER_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseThreeWeeksLaterCaseInsensitive() {
		String userInput = "3 wEEks";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_THREE_WEEKS_LATER_DATE, parsedDate);
		assertEquals(REFERENCE_THREE_WEEKS_LATER_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_THREE_WEEKS_LATER_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseThreeWeekLater() {
		String userInput = "3 week";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_THREE_WEEKS_LATER_DATE, parsedDate);
		assertEquals(REFERENCE_THREE_WEEKS_LATER_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_THREE_WEEKS_LATER_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseThreeWeekLaterCaseInsensitive() {
		String userInput = "3 weEK";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_THREE_WEEKS_LATER_DATE, parsedDate);
		assertEquals(REFERENCE_THREE_WEEKS_LATER_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_THREE_WEEKS_LATER_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseThreeWkLater() {
		String userInput = "3 wk";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_THREE_WEEKS_LATER_DATE, parsedDate);
		assertEquals(REFERENCE_THREE_WEEKS_LATER_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_THREE_WEEKS_LATER_DAY_INT, parsedDayInt);
	}
	
	@Test
	public void parseThreeWkLaterCaseInsensitive() {
		String userInput = "3 WK";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(REFERENCE_THREE_WEEKS_LATER_DATE, parsedDate);
		assertEquals(REFERENCE_THREE_WEEKS_LATER_DAY_STRING, parsedDayString);
		assertEquals(REFERENCE_THREE_WEEKS_LATER_DAY_INT, parsedDayInt);
	}
	
	
	@Test
	public void parseToday() {
		String todayString = "today";
		DateTime todayParsed = dateTimeParser.parse(todayString);
		String dateParsed = todayParsed.getDate();
		String dayStrParsed = todayParsed.getDayStr();
		int dayIntParsed = todayParsed.getDayInt();
		
		assertEquals(todayDate, dateParsed);
		assertEquals(todayDayString, dayStrParsed);
		assertEquals(todayDayInt, dayIntParsed);
	}
	
	@Test
	public void parseTdy() {
		String todayShortForm = "tdy";
		DateTime todayParsed = dateTimeParser.parse(todayShortForm);
		String dateParsed = todayParsed.getDate();
		String dayStrParsed = todayParsed.getDayStr();
		int dayIntParsed = todayParsed.getDayInt();
		
		assertEquals(todayDate, dateParsed);
		assertEquals(todayDayString, dayStrParsed);
		assertEquals(todayDayInt, dayIntParsed);
	}
	
	@Test
	public void parseTodayCaseInsensitive() {
		String todayCaseInsensitive = "ToDAy";
		DateTime todayParsed = dateTimeParser.parse(todayCaseInsensitive);
		String dateParsed = todayParsed.getDate();
		String dayStrParsed = todayParsed.getDayStr();
		int dayIntParsed = todayParsed.getDayInt();
		
		assertEquals(todayDate, dateParsed);
		assertEquals(todayDayString, dayStrParsed);
		assertEquals(todayDayInt, dayIntParsed);
	}
	
	@Test
	public void parseTdyCaseInsensitive() {
		String tdyCaseInsensitive = "TDy";
		DateTime todayParsed = dateTimeParser.parse(tdyCaseInsensitive);
		String dateParsed = todayParsed.getDate();
		String dayStrParsed = todayParsed.getDayStr();
		int dayIntParsed = todayParsed.getDayInt();
		
		assertEquals(todayDate, dateParsed);
		assertEquals(todayDayString, dayStrParsed);
		assertEquals(todayDayInt, dayIntParsed);
	}
	
	@Test
	public void parseTomorrow() {
		String tomorrowInput = "tomorrow";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(tomorrowInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(REFERENCE_TOMORROW_DATE, dateParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_INT, dayIntParsed);
	}
	
	@Test
	public void parseTomorrowCaseInsensitive() {
		String tomorrowInput = "tomORroW";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(tomorrowInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(REFERENCE_TOMORROW_DATE, dateParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_INT, dayIntParsed);
	}
	
	@Test
	public void parseTmr() {
		String tomorrowInput = "tmr";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(tomorrowInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(REFERENCE_TOMORROW_DATE, dateParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_INT, dayIntParsed);
	}
	
	@Test
	public void parseTmrCaseInsensitive() {
		String tomorrowInput = "tMR";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(tomorrowInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(REFERENCE_TOMORROW_DATE, dateParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_INT, dayIntParsed);
	}
	
	
	@Test(expected = IncorrectInputException.class) 
	public void parseNegativeNumberOfWeeks() {
		String relativeDate = "-10 weeks";
		dateTimeParser.parse(relativeDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseNegativeNumberOfDays() {
		String relativeDate = "-10 days";
		dateTimeParser.parse(relativeDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseTodayTomorrowInOneInput() {
		String relativeDate = "tmr today";
		dateTimeParser.parse(relativeDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parse2Weeks2DaysInOneInput() {
		String relativeDate = "2 weeks 2 days";
		dateTimeParser.parse(relativeDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parse2Tuesdays() {
		String relativeDate = "2 tue";
		dateTimeParser.parse(relativeDate);
	}
	//********************************************
	//******** Tests for FixedDateParser *********
	//********************************************
	@Test 
	public void parseNumberDate() {
		String numberDate = "1/10/2016";
		DateTime userDate = dateTimeParser.parse(numberDate);
		
		String parsedDate = userDate.getDate();
		String expectedDate = "1 Oct 2016";
		assertEquals(expectedDate, parsedDate);
	}
	
	@Test 
	public void parseNumberDateWithoutYearNotOver() {
		String numberDate = "1/10";
		DateTime userDate = dateTimeParser.parse(numberDate);
		
		String parsedDate = userDate.getDate();
		String expectedDate = "1 Oct 2016";
		assertEquals(expectedDate, parsedDate);
	}
	
	@Test 
	public void parseNumberDateWithoutYearOver() {
		String numberDate = "1/2";
		DateTime userDate = dateTimeParser.parse(numberDate);
		
		String parsedDate = userDate.getDate();
		String expectedDate = "1 Feb 2017";
		assertEquals(expectedDate, parsedDate);
	}
	
	@Test 
	public void parseWordDate() {
		String numberDate = "12 Dec 2016";
		DateTime userDate = dateTimeParser.parse(numberDate);
		
		String parsedDate = userDate.getDate();
		String expectedDate = "12 Dec 2016";
		assertEquals(expectedDate, parsedDate);
	}
	
	@Test 
	public void parseWordDateWithoutYearOver() {
		String numberDate = "12 Mar";
		DateTime userDate = dateTimeParser.parse(numberDate);
		
		String parsedDate = userDate.getDate();
		String expectedDate = "12 Mar 2017";
		assertEquals(expectedDate, parsedDate);
	}
	
	@Test 
	public void parseWordDateWithoutYearNotOver() {
		String numberDate = "12 Sep";
		DateTime userDate = dateTimeParser.parse(numberDate);
		
		String parsedDate = userDate.getDate();
		String expectedDate = "12 Sep 2016";
		assertEquals(expectedDate, parsedDate);
	}
	
	@Test 
	public void parseWordDateMonthOnlyNotOver() {
		String numberDate = "Sep";
		DateTime userDate = dateTimeParser.parse(numberDate);
		
		String parsedDate = userDate.getDate();
		String expectedDate = "15 Sep 2016";
		assertEquals(expectedDate, parsedDate);
	}
	
	@Test 
	public void parseWordDateMonthOnlyOver() {
		String numberDate = "Feb";
		DateTime userDate = dateTimeParser.parse(numberDate);
		
		String parsedDate = userDate.getDate();
		String expectedDate = "15 Feb 2017";
		assertEquals(expectedDate, parsedDate);
	}

	@Test(expected = IncorrectInputException.class) 
	public void parseInvalidWordDateWithNegativeDay() {
		String wordDate = "-1 Mar 2016";
		dateTimeParser.parse(wordDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseInvalidWordDateWithNegativeYear() {
		String wordDate = "1 Mar -2016";
		dateTimeParser.parse(wordDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseInvalidWordDate() {
		String numberDate = "1 Gibberish 2016";
		dateTimeParser.parse(numberDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseNumberDateWithInvalidPositiveDay() {
		String numberDate = "40/10/2016";
		dateTimeParser.parse(numberDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseIncompleteNumberDate() {
		String numberDate = "4/";
		dateTimeParser.parse(numberDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseInvalidNumberDateTooManyFields() {
		String numberDate = "4/10/2016/20/15";
		dateTimeParser.parse(numberDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseInvalidNumberDate() {
		String numberDate = "a/b/c";
		dateTimeParser.parse(numberDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseNegativeNumberDate() {
		String numberDate = "-10 -12 -2016";
		dateTimeParser.parse(numberDate);
	}
	
	@Test//(expected = IncorrectInputException.class) 
	public void parseNumberDateWithInvalidPositiveYear() {
		String numberDate = "4/10/16";
		dateTimeParser.parse(numberDate);
	}
	
	/* This is a boundary case for the ‘positive value’ partition */
	@Test(expected = IncorrectInputException.class) 
	public void parseInvalidMonthInNumberDate() {
		String numberDate = "29/20/2017";
		dateTimeParser.parse(numberDate);
	}
	
	/* This is a boundary case for the ‘positive value’ partition */
	@Test(expected = IncorrectInputException.class) 
	public void parseInvalidNumberDateLeapDay() {
		String numberDate = "29/2/2017";
		dateTimeParser.parse(numberDate);
	}
	
	/* This is a boundary case for the ‘negative value’ partition */
	@Test(expected = IncorrectInputException.class) 
	public void parseNumberDateWithInvalidNegativeDay() {
		String numberDate = "-1/10/2016";
		dateTimeParser.parse(numberDate);
	}
	
	/* This is a boundary case for the ‘negative value’ partition */
	@Test(expected = IncorrectInputException.class) 
	public void parseNumberDateWithInvalidNegativeMonth() {
		String numberDate = "1/-10/2016";
		dateTimeParser.parse(numberDate);
	}
	
	/* This is a boundary case for the ‘negative value’ partition */
	@Test(expected = IncorrectInputException.class) 
	public void parseNumberDateWithInvalidNegativeYear() {
		String numberDate = "1/10/-2016";
		dateTimeParser.parse(numberDate);
	}
	
	/* This is a boundary case for the ‘negative value’ partition */
	@Test(expected = IncorrectInputException.class) 
	public void parseNumberDateWithInvalidNegativeDayMonthYear() {
		String numberDate = "-1/-10/-2016";
		dateTimeParser.parse(numberDate);
	}
	
	@Test//(expected = IncorrectInputException.class) 
	public void parseDateThatHasPassed() {
		String userInput = "17 Feb 2015";
		referenceDateTimeParser.parse(userInput);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseTwoWordDatesInOneInput() {
		String userInput = "17 Feb 2017 20 March 2018";
		dateTimeParser.parse(userInput);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseTwoShortNumberDatesInOneInput() {
		String userInput = "17/2 18/3";
		dateTimeParser.parse(userInput);
	}
	//********************************************
	//*********** Tests for TimeParser ***********
	//********************************************
	@Test
	public void parseTimeThatHasPassed() {
		String userInput = "5 am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 500;
		String expected12hString = "5.00 am";
		String expected24hString = "05:00";
		
		assertEquals(REFERENCE_TOMORROW_DATE, dateParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeNoSpace1am() {
		String userInput = "1am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 100;
		String expected12hString = "1.00 am";
		String expected24hString = "01:00";
		
		assertEquals(REFERENCE_TOMORROW_DATE, dateParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeNoSpace130am() {
		String userInput = "1.30am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 130;
		String expected12hString = "1.30 am";
		String expected24hString = "01:30";
		
		assertEquals(REFERENCE_TOMORROW_DATE, dateParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeNoSpace1240am() {
		String userInput = "12.40am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 40;
		String expected12hString = "12.40 am";
		String expected24hString = "00:40";
		
		assertEquals(REFERENCE_TOMORROW_DATE, dateParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeWithSpace1am() {
		String userInput = "1 am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 100;
		String expected12hString = "1.00 am";
		String expected24hString = "01:00";
		
		assertEquals(REFERENCE_TOMORROW_DATE, dateParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeWithSpace130am() {
		String userInput = "1.30 am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 130;
		String expected12hString = "1.30 am";
		String expected24hString = "01:30";
		
		assertEquals(REFERENCE_TOMORROW_DATE, dateParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeWithSpace1240am() {
		String userInput = "12.40 am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 40;
		String expected12hString = "12.40 am";
		String expected24hString = "00:40";
		
		assertEquals(REFERENCE_TOMORROW_DATE, dateParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TOMORROW_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeNoSpace10am() {
		String userInput = "10am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 1000;
		String expected12hString = "10.00 am";
		String expected24hString = "10:00";
		
		assertEquals(REFERENCE_TODAY_DATE, dateParsed);
		assertEquals(REFERENCE_TODAY_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TODAY_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeWithSpace10am() {
		String userInput = "10 am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 1000;
		String expected12hString = "10.00 am";
		String expected24hString = "10:00";
		
		assertEquals(REFERENCE_TODAY_DATE, dateParsed);
		assertEquals(REFERENCE_TODAY_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TODAY_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeNoSpace12pm() {
		String userInput = "12pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 1200;
		String expected12hString = "12.00 pm";
		String expected24hString = "12:00";
		
		assertEquals(REFERENCE_TODAY_DATE, dateParsed);
		assertEquals(REFERENCE_TODAY_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TODAY_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeWithSpace12pm() {
		String userInput = "12 pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 1200;
		String expected12hString = "12.00 pm";
		String expected24hString = "12:00";
		
		assertEquals(REFERENCE_TODAY_DATE, dateParsed);
		assertEquals(REFERENCE_TODAY_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TODAY_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeNoSpace1230pm() {
		String userInput = "12.30pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 1230;
		String expected12hString = "12.30 pm";
		String expected24hString = "12:30";
		
		assertEquals(REFERENCE_TODAY_DATE, dateParsed);
		assertEquals(REFERENCE_TODAY_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TODAY_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeWithSpace1230pm() {
		String userInput = "12.30 pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 1230;
		String expected12hString = "12.30 pm";
		String expected24hString = "12:30";
		
		assertEquals(REFERENCE_TODAY_DATE, dateParsed);
		assertEquals(REFERENCE_TODAY_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TODAY_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeNoSpace11pm() {
		String userInput = "11pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 2300;
		String expected12hString = "11.00 pm";
		String expected24hString = "23:00";
		
		assertEquals(REFERENCE_TODAY_DATE, dateParsed);
		assertEquals(REFERENCE_TODAY_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TODAY_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeWithSpace11pm() {
		String userInput = "11 pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 2300;
		String expected12hString = "11.00 pm";
		String expected24hString = "23:00";
		
		assertEquals(REFERENCE_TODAY_DATE, dateParsed);
		assertEquals(REFERENCE_TODAY_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TODAY_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeNoSpace1130pm() {
		String userInput = "11.30pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 2330;
		String expected12hString = "11.30 pm";
		String expected24hString = "23:30";
		
		assertEquals(REFERENCE_TODAY_DATE, dateParsed);
		assertEquals(REFERENCE_TODAY_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TODAY_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeWithSpace1130pm() {
		String userInput = "11.30 pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 2330;
		String expected12hString = "11.30 pm";
		String expected24hString = "23:30";
		
		assertEquals(REFERENCE_TODAY_DATE, dateParsed);
		assertEquals(REFERENCE_TODAY_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TODAY_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeNoSpace5pm() {
		String userInput = "5pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 1700;
		String expected12hString = "5.00 pm";
		String expected24hString = "17:00";
		
		assertEquals(REFERENCE_TODAY_DATE, dateParsed);
		assertEquals(REFERENCE_TODAY_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TODAY_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeWithSpace5pm() {
		String userInput = "5 pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 1700;
		String expected12hString = "5.00 pm";
		String expected24hString = "17:00";
		
		assertEquals(REFERENCE_TODAY_DATE, dateParsed);
		assertEquals(REFERENCE_TODAY_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TODAY_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeNoSpace530pm() {
		String userInput = "5.30pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 1730;
		String expected12hString = "5.30 pm";
		String expected24hString = "17:30";
		
		assertEquals(REFERENCE_TODAY_DATE, dateParsed);
		assertEquals(REFERENCE_TODAY_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TODAY_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTime12HourTimeWithSpace530pm() {
		String userInput = "5.30 pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		String time12hStringParsed = parsedDateTimeObject.getTime12hStr();
		String time24hStringParsed = parsedDateTimeObject.getTime24hStr();
		int expectedTime24H = 1730;
		String expected12hString = "5.30 pm";
		String expected24hString = "17:30";
		
		assertEquals(REFERENCE_TODAY_DATE, dateParsed);
		assertEquals(REFERENCE_TODAY_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TODAY_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
		assertEquals(expected12hString, time12hStringParsed);
		assertEquals(expected24hString, time24hStringParsed);
	}
	
	@Test
	public void parseTenThirtyAmWithSpaceAndDotTimeSeparator() {
		String timeInput = "10.30 am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(referenceDateTimeObject.getDate(), dateParsed);
		assertEquals(referenceDayString, dayStrParsed);
		assertEquals(referenceDayInt, dayIntParsed);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 1030;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "10:30";
		String expected12HTimeString = "10.30 am";
		
		assertEquals(expectedTimeInt, timeInt);
		assertEquals(expected24HTimeString, time24HString);
		assertEquals(expected12HTimeString, time12HString);
	}
	
	@Test
	public void parseTenThirtyAmWithSpaceAndDotTimeSeparatorCaseInsensitive() {
		String timeInput = "10.30 Am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(referenceDateTimeObject.getDate(), dateParsed);
		assertEquals(referenceDayString, dayStrParsed);
		assertEquals(referenceDayInt, dayIntParsed);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 1030;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "10:30";
		String expected12HTimeString = "10.30 am";
		
		assertEquals(expectedTimeInt, timeInt);
		assertEquals(expected24HTimeString, time24HString);
		assertEquals(expected12HTimeString, time12HString);
	}
	
	
	@Test
	public void parseTenThirtyAmWithoutSpaceAndDotTimeSeparator() {
		String timeInput = "10.30am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(referenceDateTimeObject.getDate(), dateParsed);
		assertEquals(referenceDayString, dayStrParsed);
		assertEquals(referenceDayInt, dayIntParsed);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 1030;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "10:30";
		String expected12HTimeString = "10.30 am";
		
		assertEquals(expectedTimeInt, timeInt);
		assertEquals(expected24HTimeString, time24HString);
		assertEquals(expected12HTimeString, time12HString);
	}
	
	@Test
	public void parseTenThirtyAmWithoutSpaceAndDotTimeSeparatorCaseInsensitive() {
		String timeInput = "10.30AM";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(referenceDateTimeObject.getDate(), dateParsed);
		assertEquals(referenceDayString, dayStrParsed);
		assertEquals(referenceDayInt, dayIntParsed);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 1030;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "10:30";
		String expected12HTimeString = "10.30 am";
		
		assertEquals(expectedTimeInt, timeInt);
		assertEquals(expected24HTimeString, time24HString);
		assertEquals(expected12HTimeString, time12HString);
	}
	
	@Test
	public void parseTenThirtyPmWithSpaceAndDotTimeSeparator() {
		String timeInput = "10.30 pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(referenceDateTimeObject.getDate(), dateParsed);
		assertEquals(referenceDayString, dayStrParsed);
		assertEquals(referenceDayInt, dayIntParsed);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 2230;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "22:30";
		String expected12HTimeString = "10.30 pm";
		
		assertEquals(expectedTimeInt, timeInt);
		assertEquals(expected24HTimeString, time24HString);
		assertEquals(expected12HTimeString, time12HString);
	}
	
	@Test
	public void parseTenThirtyPmWithSpaceAndDotTimeSeparatorCaseInsensitive() {
		String timeInput = "10.30 pM";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(referenceDateTimeObject.getDate(), dateParsed);
		assertEquals(referenceDayString, dayStrParsed);
		assertEquals(referenceDayInt, dayIntParsed);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 2230;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "22:30";
		String expected12HTimeString = "10.30 pm";
		
		assertEquals(expectedTimeInt, timeInt);
		assertEquals(expected24HTimeString, time24HString);
		assertEquals(expected12HTimeString, time12HString);
	}
	
	@Test
	public void parseTenThirtyPmWithoutSpaceAndDotTimeSeparator() {
		String timeInput = "10.30pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(referenceDateTimeObject.getDate(), dateParsed);
		assertEquals(referenceDayString, dayStrParsed);
		assertEquals(referenceDayInt, dayIntParsed);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 2230;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "22:30";
		String expected12HTimeString = "10.30 pm";
		
		assertEquals(expectedTimeInt, timeInt);
		assertEquals(expected24HTimeString, time24HString);
		assertEquals(expected12HTimeString, time12HString);
	}
	
	@Test
	public void parseTenThirtyPmWithoutSpaceAndDotTimeSeparatorCaseInsensitive() {
		String timeInput = "10.30PM";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(referenceDateTimeObject.getDate(), dateParsed);
		assertEquals(referenceDayString, dayStrParsed);
		assertEquals(referenceDayInt, dayIntParsed);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 2230;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "22:30";
		String expected12HTimeString = "10.30 pm";
		
		assertEquals(expectedTimeInt, timeInt);
		assertEquals(expected24HTimeString, time24HString);
		assertEquals(expected12HTimeString, time12HString);
	}
	
	@Test
	public void parseTenThirtyAmWithSpaceAndColonTimeSeparator() {
		String timeInput = "10:30 am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(referenceDateTimeObject.getDate(), dateParsed);
		assertEquals(referenceDayString, dayStrParsed);
		assertEquals(referenceDayInt, dayIntParsed);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 1030;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "10:30";
		String expected12HTimeString = "10.30 am";
		
		assertEquals(expectedTimeInt, timeInt);
		assertEquals(expected24HTimeString, time24HString);
		assertEquals(expected12HTimeString, time12HString);
	}
	
	@Test
	public void parseTenThirtyAmWithSpaceAndColonTimeSeparatorCaseInsensitive() {
		String timeInput = "10:30 AM";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(referenceDateTimeObject.getDate(), dateParsed);
		assertEquals(referenceDayString, dayStrParsed);
		assertEquals(referenceDayInt, dayIntParsed);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 1030;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "10:30";
		String expected12HTimeString = "10.30 am";
		
		assertEquals(expectedTimeInt, timeInt);
		assertEquals(expected24HTimeString, time24HString);
		assertEquals(expected12HTimeString, time12HString);
	}
	
	@Test
	public void parseTenThirtyAmWithoutSpaceAndColonTimeSeparator() {
		String timeInput = "10:30am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(referenceDateTimeObject.getDate(), dateParsed);
		assertEquals(referenceDayString, dayStrParsed);
		assertEquals(referenceDayInt, dayIntParsed);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 1030;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "10:30";
		String expected12HTimeString = "10.30 am";
		
		assertEquals(expectedTimeInt, timeInt);
		assertEquals(expected24HTimeString, time24HString);
		assertEquals(expected12HTimeString, time12HString);
	}
	
	@Test
	public void parseTenThirtyAmWithoutSpaceAndColonTimeSeparatorCaseInsensitive() {
		String timeInput = "10:30Am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(referenceDateTimeObject.getDate(), dateParsed);
		assertEquals(referenceDayString, dayStrParsed);
		assertEquals(referenceDayInt, dayIntParsed);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 1030;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "10:30";
		String expected12HTimeString = "10.30 am";
		
		assertEquals(expectedTimeInt, timeInt);
		assertEquals(expected24HTimeString, time24HString);
		assertEquals(expected12HTimeString, time12HString);
	}
	
	@Test
	public void parseTenThirtyPmWithSpaceAndColonTimeSeparator() {
		String timeInput = "10:30 pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(referenceDateTimeObject.getDate(), dateParsed);
		assertEquals(referenceDayString, dayStrParsed);
		assertEquals(referenceDayInt, dayIntParsed);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 2230;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "22:30";
		String expected12HTimeString = "10.30 pm";
		
		assertEquals(expectedTimeInt, timeInt);
		assertEquals(expected24HTimeString, time24HString);
		assertEquals(expected12HTimeString, time12HString);
	}
	
	@Test
	public void parseTenThirtyPmWithoutSpaceAndColonTimeSeparator() {
		String timeInput = "10:30pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(referenceDateTimeObject.getDate(), dateParsed);
		assertEquals(referenceDayString, dayStrParsed);
		assertEquals(referenceDayInt, dayIntParsed);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 2230;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "22:30";
		String expected12HTimeString = "10.30 pm";
		
		assertEquals(expectedTimeInt, timeInt);
		assertEquals(expected24HTimeString, time24HString);
		assertEquals(expected12HTimeString, time12HString);
	}
	
	@Test
	public void parseTenThirtyPm24HFormat() {
		String timeInput = "22:30";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(referenceDateTimeObject.getDate(), dateParsed);
		assertEquals(referenceDayString, dayStrParsed);
		assertEquals(referenceDayInt, dayIntParsed);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 2230;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "22:30";
		String expected12HTimeString = "10.30 pm";
		
		assertEquals(expectedTimeInt, timeInt);
		assertEquals(expected24HTimeString, time24HString);
		assertEquals(expected12HTimeString, time12HString);
	}
	
	@Test
	public void parseTenThirtyAm24HFormat() {
		String timeInput = "10:30";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(referenceDateTimeObject.getDate(), dateParsed);
		assertEquals(referenceDayString, dayStrParsed);
		assertEquals(referenceDayInt, dayIntParsed);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 1030;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "10:30";
		String expected12HTimeString = "10.30 am";
		
		assertEquals(expectedTimeInt, timeInt);
		assertEquals(expected24HTimeString, time24HString);
		assertEquals(expected12HTimeString, time12HString);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void parseTwo24HTimeInASingleInput() {
		String timeInput = "20:30 10:30";
		dateTimeParser.parse(timeInput);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void parseTwo12HTimeWithSpacingInASingleInput() {
		String timeInput = "12 pm 2 am";
		dateTimeParser.parse(timeInput);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void parseTwo12HTimeWithoutSpacingInASingleInput() {
		String timeInput = "12pm 2am";
		dateTimeParser.parse(timeInput);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parse12HTimeWithTooManyNumbers() {
		String time = "10.20.30.40pm";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parse24HTimeWithTooManyNumbers() {
		String time = "10:20:30:40";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseNegativeElevenAm() {
		String time = "-11am";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseZeroAm() {
		String time = "0am";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseTwentyFivePm() {
		String time = "25 Pm";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseThirteenPm() {
		String time = "13 Pm";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseThirteenSixtyPm() {
		String time = "3:60 Pm";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseThirteenSixtyAm() {
		String time = "3:60 Am";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseForteenNegativeElevenAm() {
		String time = "11.-11am";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseForteenNegativeElevenPm() {
		String time = "11.-11pm";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseForteenNegativeElevenAmWithSpacing() {
		String time = "11.-11 am";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseForteenNegativeElevenPmWithSpacing() {
		String time = "11.-11 pm";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseNegative24Hour() {
		String time = "-11:30";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseInvalid24HTime1() {
		String time = "24:30";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseInvalid24HTime2() {
		String time = "2:60";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseInvalid24HTime3() {
		String time = "24:60";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseInvalid24HTime4() {
		String time = "1:-60";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseInvalid24HTime5() {
		String time = "-1:50";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseInvalid24HTime6() {
		String time = "-1:-50";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseNestedTimeIndicator1() {
		String time = "1.40 pmer";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseNestedTimeIndicator2() {
		String time = "1.40 amer";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseNestedTimeIndicator3() {
		String time = "1.40pmer";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseNestedTimeIndicator4() {
		String time = "1.40amer";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseTimeAmAndPm1() {
		String time = "1.40ampm";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseTimeAmAndPm2() {
		String time = "1.40pmam";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseTimeAmAndPm3() {
		String time = "1.40 pmam";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseTimeAmAndPm4() {
		String time = "1.40 ampm";
		dateTimeParser.parse(time);
	}
	
	//********************************************
	//******** Tests for DateTimeParser **********
	//*** Permutation of date and time inputs ****
	//********************************************

	@Test
	public void parseTimeThatHasPassedWithToday() {
		String userInput = "today 5 am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		int expectedTime24H = 500;
		
		assertEquals(REFERENCE_TODAY_DATE, dateParsed);
		assertEquals(REFERENCE_TODAY_DAY_STRING, dayStrParsed);
		assertEquals(REFERENCE_TODAY_DAY_INT, dayIntParsed);
		assertEquals(expectedTime24H, time24hParsed);
	} 
	
	@Test
	public void parseWordDateAnd12HourAfternoonTimeWithSpacing() {
		String userInput = "15 June 2016 6 pm";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals("15 Jun 2016", date);
		assertEquals("18:00", timeStr24H);
		assertEquals("6.00 pm", timeStr12H);
		assertEquals("Wednesday", dayStr);
		assertEquals(3, dayInt);
		assertEquals(1800, timeInt);
	}
	
	@Test
	public void parse12HourAfternoonTimeWithSpacingAndWordDate12HourAfternoonTimeWithSpacing() {
		String userInput = "6 pm 15 June 2016";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals("15 Jun 2016", date);
		assertEquals("18:00", timeStr24H);
		assertEquals("6.00 pm", timeStr12H);
		assertEquals("Wednesday", dayStr);
		assertEquals(3, dayInt);
		assertEquals(1800, timeInt);
	}
	
	@Test
	public void parseDDMMYYDateAnd12HourAfternoonTimeWithoutSpacing() {
		String userInput = "19/02/2020 6.30pm";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals("19 Feb 2020", date);
		assertEquals("18:30", timeStr24H);
		assertEquals("6.30 pm", timeStr12H);
		assertEquals("Wednesday", dayStr);
		assertEquals(3, dayInt);
		assertEquals(1830, timeInt);
	}
	
	@Test
	public void parse12HourAfternoonTimeWithoutSpacingAndDDMMYYDate() {
		String userInput = "6.30pm 19/02/2020";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals("19 Feb 2020", date);
		assertEquals("18:30", timeStr24H);
		assertEquals("6.30 pm", timeStr12H);
		assertEquals("Wednesday", dayStr);
		assertEquals(3, dayInt);
		assertEquals(1830, timeInt);
	}
	
	@Test
	public void parseIncompleteWordNotOverDateAnd12HourMorningTimeWithSpacing() {
		String userInput = "15 Sept 6:45 am";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals("15 Sep 2016", date);
		assertEquals("06:45", timeStr24H);
		assertEquals("6.45 am", timeStr12H);
		assertEquals("Thursday", dayStr);
		assertEquals(4, dayInt);
		assertEquals(645, timeInt);
	}
	
	@Test
	public void parse12HourMorningTimeWithSpacingAndIncompleteWordNotOverDate() {
		String userInput = "6:45 am 15 Sept";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals("15 Sep 2016", date);
		assertEquals("06:45", timeStr24H);
		assertEquals("6.45 am", timeStr12H);
		assertEquals("Thursday", dayStr);
		assertEquals(4, dayInt);
		assertEquals(645, timeInt);
	}
	
	@Test
	public void parseIncompleteWordOverDateAnd12HourMorningTimeWithSpacing() {
		String userInput = "1 Jan 13:45";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals("1 Jan 2017", date);
		assertEquals("13:45", timeStr24H);
		assertEquals("1.45 pm", timeStr12H);
		assertEquals("Sunday", dayStr);
		assertEquals(7, dayInt);
		assertEquals(1345, timeInt);
	}
	
	@Test
	public void parse12HourMorningTimeWithSpacingAndIncompleteWordOverDate() {
		String userInput = "13:45 1 Jan";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals("1 Jan 2017", date);
		assertEquals("13:45", timeStr24H);
		assertEquals("1.45 pm", timeStr12H);
		assertEquals("Sunday", dayStr);
		assertEquals(7, dayInt);
		assertEquals(1345, timeInt);
	}
	
	@Test
	public void parse_DD_MM_OverDateAnd12HourMorningTimeWithoutSpacing() {
		String userInput = "10/2 6:45am";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals("10 Feb 2017", date);
		assertEquals("06:45", timeStr24H);
		assertEquals("6.45 am", timeStr12H);
		assertEquals("Friday", dayStr);
		assertEquals(5, dayInt);
		assertEquals(645, timeInt);
	}
	
	@Test
	public void parse12HourMorningTimeWithoutSpacingAndDDMMOverDate() {
		String userInput = "6:45am 10/2";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals("10 Feb 2017", date);
		assertEquals("06:45", timeStr24H);
		assertEquals("6.45 am", timeStr12H);
		assertEquals("Friday", dayStr);
		assertEquals(5, dayInt);
		assertEquals(645, timeInt);
	}
	
	@Test
	public void parseDDMMNotOverDateAnd24HourMorningTime() {
		String userInput = "10/10 8:45";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals("10 Oct 2016", date);
		assertEquals("08:45", timeStr24H);
		assertEquals("8.45 am", timeStr12H);
		assertEquals("Monday", dayStr);
		assertEquals(1, dayInt);
		assertEquals(845, timeInt);
	}
	
	@Test
	public void parse24HourMorningTimeAndDDMMNotOverDate() {
		String userInput = "8:45 10/10";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals("10 Oct 2016", date);
		assertEquals("08:45", timeStr24H);
		assertEquals("8.45 am", timeStr12H);
		assertEquals("Monday", dayStr);
		assertEquals(1, dayInt);
		assertEquals(845, timeInt);
	}
	
	@Test
	public void parseMMNotOverDateAnd24HourAfternoon() {
		String userInput = "Oct 19:15";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals("15 Oct 2016", date);
		assertEquals("19:15", timeStr24H);
		assertEquals("7.15 pm", timeStr12H);
		assertEquals("Saturday", dayStr);
		assertEquals(6, dayInt);
		assertEquals(1915, timeInt);
	}
	
	@Test
	public void parse24HourAfternoonAndMMNotOverDate() {
		String userInput = "19:15 Oct";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals("15 Oct 2016", date);
		assertEquals("19:15", timeStr24H);
		assertEquals("7.15 pm", timeStr12H);
		assertEquals("Saturday", dayStr);
		assertEquals(6, dayInt);
		assertEquals(1915, timeInt);
	}
	
	@Test
	public void parseMMNOverDateAnd24HourAfternoon() {
		String userInput = "Jan 19:15";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals("15 Jan 2017", date);
		assertEquals("19:15", timeStr24H);
		assertEquals("7.15 pm", timeStr12H);
		assertEquals("Sunday", dayStr);
		assertEquals(7, dayInt);
		assertEquals(1915, timeInt);
	}
	
	@Test
	public void parse24HourAfternoonAndMMNOverDate() {
		String userInput = "19:15 Jan";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals("15 Jan 2017", date);
		assertEquals("19:15", timeStr24H);
		assertEquals("7.15 pm", timeStr12H);
		assertEquals("Sunday", dayStr);
		assertEquals(7, dayInt);
		assertEquals(1915, timeInt);
	}

	@Test(expected = IncorrectInputException.class) 
	public void parseNegativeGibberish() {
		String relativeDate = "-10 Gibberish";
		dateTimeParser.parse(relativeDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseGibberish() {
		String time = "-10 -1000 -2018 pm";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseEmptyString() {
		String numberDate = "";
		dateTimeParser.parse(numberDate);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void parse24HTimeAndFullDateInOneWord() {
		String input = "10:15/5/2019";
		dateTimeParser.parse(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void parse12HTimeAndFullDateInOneWord() {
		String input = "10.15/4/2017";
		dateTimeParser.parse(input);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void parseShortDateAnd24HTimeInOneWord() {
		String input = "3/6:30";
		dateTimeParser.parse(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void parseShortDateAnd12HTimeInOneWord() {
		String input = "3/8.30";
		dateTimeParser.parse(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void parse12HTimeAnd24HTimeInOneWord() {
		String input = "16:12.40";
		dateTimeParser.parse(input);
	}
}
