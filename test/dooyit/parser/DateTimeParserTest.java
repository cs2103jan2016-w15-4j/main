package dooyit.parser;

import dooyit.parser.DateTimeParser;
import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.*;
import org.junit.Assert.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class DateTimeParserTest {
	private static final int FORMAT_24H_6AM = 600;

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
	
	DateTimeParser dateTimeParser = new DateTimeParser();
	
	int[] referenceDate = new int[] {17, 2, 2016};
	String referenceDayString = "Wednesday";
	int referenceDayInt= 3;
	int referenceTime = FORMAT_24H_6AM;
	DateTime referenceDateTimeObject = new DateTime(referenceDate, referenceDayString, referenceTime);
	DateTimeParser referenceDateTimeParser = new DateTimeParser(referenceDateTimeObject);
	
	DateTime todayDateTimeObject = new DateTime();
	String todayDate = todayDateTimeObject.getDate();
	String todayDayString = todayDateTimeObject.getDayStr();
	int todayDayInt = todayDateTimeObject.getDayInt();
	
	@Test(expected = IncorrectInputException.class) 
	public void testInvalidWordDateWithNegativeDay() {
		String wordDate = "-1 Mar 2016";
		dateTimeParser.parse(wordDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testInvalidWordDateWithNegativeYear() {
		String wordDate = "1 Mar -2016";
		dateTimeParser.parse(wordDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testInvalidWordDate() {
		String numberDate = "1 Gibberish 2016";
		dateTimeParser.parse(numberDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseNumberDateWithInvalidPositiveDay() {
		String numberDate = "40/10/2016";
		dateTimeParser.parse(numberDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseIncompleteNumberDate() {
		String numberDate = "4/";
		dateTimeParser.parse(numberDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseInvalidNumberDateTooManyFields() {
		String numberDate = "4/10/2016/20/15";
		dateTimeParser.parse(numberDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseInvalidNumberDate() {
		String numberDate = "a/b/c";
		dateTimeParser.parse(numberDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseNumberDateWithInvalidPositiveYear() {
		String numberDate = "4/10/16";
		dateTimeParser.parse(numberDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseNegative24Hour() {
		String time = "-11:30";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseNumberOfWeeks() {
		String relativeDate = "-10 weeks";
		dateTimeParser.parse(relativeDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseNegativeGibberish() {
		String relativeDate = "-10 Gibberish";
		dateTimeParser.parse(relativeDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseGibberish() {
		String time = "-10 -1000 -2018 pm";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseNegativeElevenAm() {
		String time = "-11am";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseZeroAm() {
		String time = "0am";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseTwentyFivePm() {
		String time = "25 Pm";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseThirteenPm() {
		String time = "13 Pm";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseThirteenSixtyPm() {
		String time = "3:60 Pm";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseThirteenSixtyAm() {
		String time = "3:60 Am";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseForteenNegativeElevenAm() {
		String time = "11.-11am";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseForteenNegativeElevenPm() {
		String time = "11.-11pm";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseForteenNegativeElevenAmWithSpacing() {
		String time = "11.-11 am";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseForteenNegativeElevenPmWithSpacing() {
		String time = "11.-11 pm";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseEmptyString() {
		String numberDate = "";
		dateTimeParser.parse(numberDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseInvalid24HTime1() {
		String time = "24:30";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseInvalid24HTime2() {
		String time = "2:60";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseInvalid24HTime3() {
		String time = "24:60";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseInvalid24HTime4() {
		String time = "1:-60";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseInvalid24HTime5() {
		String time = "-1:50";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseInvalid24HTime6() {
		String time = "-1:-50";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseNestedTimeIndicator1() {
		String time = "1.40 pmer";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseNestedTimeIndicator2() {
		String time = "1.40 amer";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseNestedTimeIndicator3() {
		String time = "1.40pmer";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseNestedTimeIndicator4() {
		String time = "1.40amer";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseTimeAmAndPm1() {
		String time = "1.40ampm";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseTimeAmAndPm2() {
		String time = "1.40pmam";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseTimeAmAndPm3() {
		String time = "1.40 pmam";
		dateTimeParser.parse(time);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseTimeAmAndPm4() {
		String time = "1.40 ampm";
		dateTimeParser.parse(time);
	}
	
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseNegativeNumberDate() {
		String numberDate = "-10 -12 -2016";
		dateTimeParser.parse(numberDate);
	}
	
	/* This is a boundary case for the ‘positive value’ partition */
	@Test(expected = IncorrectInputException.class) 
	public void testParseInvalidMonthInNumberDate() {
		String numberDate = "29/20/2017";
		dateTimeParser.parse(numberDate);
	}
	
	/* This is a boundary case for the ‘positive value’ partition */
	@Test(expected = IncorrectInputException.class) 
	public void testParseInvalidNumberDateLeapDay() {
		String numberDate = "29/2/2017";
		dateTimeParser.parse(numberDate);
	}
	
	/* This is a boundary case for the ‘negative value’ partition */
	@Test(expected = IncorrectInputException.class) 
	public void testParseNumberDateWithInvalidNegativeDay() {
		String numberDate = "-1/10/2016";
		dateTimeParser.parse(numberDate);
	}
	
	/* This is a boundary case for the ‘negative value’ partition */
	@Test(expected = IncorrectInputException.class) 
	public void testParseNumberDateWithInvalidNegativeMonth() {
		String numberDate = "1/-10/2016";
		dateTimeParser.parse(numberDate);
	}
	
	/* This is a boundary case for the ‘negative value’ partition */
	@Test(expected = IncorrectInputException.class) 
	public void testParseNumberDateWithInvalidNegativeYear() {
		String numberDate = "1/10/-2016";
		dateTimeParser.parse(numberDate);
	}
	
	/* This is a boundary case for the ‘negative value’ partition */
	@Test(expected = IncorrectInputException.class) 
	public void testParseNumberDateWithInvalidNegativeDayMonthYear() {
		String numberDate = "-1/-10/-2016";
		dateTimeParser.parse(numberDate);
	}
	
	@Test(expected = IncorrectInputException.class) 
	public void testParseDateThatHasPassed() {
		String userInput = "17 Feb 2015";
		referenceDateTimeParser.parse(userInput);
	}
	
	@Test
	public void testParseTimeThatHasPassed() {
		String userInput = "5 am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		int expectedTime24H = 500;
		
		assertEquals(dateParsed, REFERENCE_TOMORROW_DATE);
		assertEquals(dayStrParsed, REFERENCE_TOMORROW_DAY_STRING);
		assertEquals(dayIntParsed, REFERENCE_TOMORROW_DAY_INT);
		assertEquals(time24hParsed, expectedTime24H);
	}
	
	@Test
	public void testParseTimeThatHasPassedWithToday() {
		String userInput = "today 5 am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(userInput);
		
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		int time24hParsed = parsedDateTimeObject.getTimeInt();
		int expectedTime24H = 500;
		
		assertEquals(dateParsed, REFERENCE_TOMORROW_DATE);
		assertEquals(dayStrParsed, REFERENCE_TOMORROW_DAY_STRING);
		assertEquals(dayIntParsed, REFERENCE_TOMORROW_DAY_INT);
		assertEquals(time24hParsed, expectedTime24H);
	} 
	
	@Test
	public void testParseSaturday() {
		String userInput = "saturday";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_THIS_SATURDAY_DATE);
		assertEquals(parsedDayString, REFERENCE_THIS_SATURDAY_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_THIS_SATURDAY_DAY_INT);
	}
	
	@Test
	public void testParseSaturdayCaseInsensitive() {
		String userInput = "satURDay";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_THIS_SATURDAY_DATE);
		assertEquals(parsedDayString, REFERENCE_THIS_SATURDAY_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_THIS_SATURDAY_DAY_INT);
	}
	
	@Test
	public void testParseThisSaturday() {
		String userInput = "this saturday";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_THIS_SATURDAY_DATE);
		assertEquals(parsedDayString, REFERENCE_THIS_SATURDAY_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_THIS_SATURDAY_DAY_INT);
	}
	
	@Test
	public void testParseThisSaturdayCaseInsensitive() {
		String userInput = "ThIs satURDay";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_THIS_SATURDAY_DATE);
		assertEquals(parsedDayString, REFERENCE_THIS_SATURDAY_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_THIS_SATURDAY_DAY_INT);
	}
	
	@Test
	public void testParseNextSaturday() {
		String userInput = "next saturday";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_NEXT_SATURDAY_DATE);
		assertEquals(parsedDayString, REFERENCE_NEXT_SATURDAY_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_NEXT_SATURDAY_DAY_INT);
	}
	
	@Test
	public void testParseNextSaturdayCaseInsensitive() {
		String userInput = "nExT satURday";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_NEXT_SATURDAY_DATE);
		assertEquals(parsedDayString, REFERENCE_NEXT_SATURDAY_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_NEXT_SATURDAY_DAY_INT);
	}
	
	@Test
	public void testParseMonday() {
		String userInput = "monday";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_THIS_MONDAY_DATE);
		assertEquals(parsedDayString, REFERENCE_THIS_MONDAY_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_THIS_MONDAY_DAY_INT);
	}
	
	@Test
	public void testParseMondayCaseInsensitive() {
		String userInput = "mONdaY";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_THIS_MONDAY_DATE);
		assertEquals(parsedDayString, REFERENCE_THIS_MONDAY_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_THIS_MONDAY_DAY_INT);
	}
	
	@Test
	public void testParseThisMonday() {
		String userInput = "this monday";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_THIS_MONDAY_DATE);
		assertEquals(parsedDayString, REFERENCE_THIS_MONDAY_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_THIS_MONDAY_DAY_INT);
	}
	
	@Test
	public void testParseThisMondayCaseInsensitive() {
		String userInput = "thIs mONday";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_THIS_MONDAY_DATE);
		assertEquals(parsedDayString, REFERENCE_THIS_MONDAY_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_THIS_MONDAY_DAY_INT);
	}
	
	@Test
	public void testParseNextMonday() {
		String userInput = "next monday";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_NEXT_MONDAY_DATE);
		assertEquals(parsedDayString, REFERENCE_NEXT_MONDAY_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_NEXT_MONDAY_DAY_INT);
	}
	
	@Test
	public void testParseNextMondayCaseInsensitive() {
		String userInput = "NExt mONday";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_NEXT_MONDAY_DATE);
		assertEquals(parsedDayString, REFERENCE_NEXT_MONDAY_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_NEXT_MONDAY_DAY_INT);
	}
	
	@Test
	public void testParseThisMon() {
		String userInput = "this mon";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_THIS_MONDAY_DATE);
		assertEquals(parsedDayString, REFERENCE_THIS_MONDAY_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_THIS_MONDAY_DAY_INT);
	}
	
	@Test
	public void testParseThisMonCaseInsensitive() {
		String userInput = "tHIs mON";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_THIS_MONDAY_DATE);
		assertEquals(parsedDayString, REFERENCE_THIS_MONDAY_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_THIS_MONDAY_DAY_INT);
	}
	
	@Test
	public void testParseTwelveDaysLater() {
		String userInput = "12 days";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_TWELVE_DAYS_LATER_DATE);
		assertEquals(parsedDayString, REFERENCE_TWELVE_DAYS_LATER_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_TWELVE_DAYS_LATER_DAY_INT);
	}
	
	@Test
	public void testParseTwelveDaysLaterCaseInsensitive() {
		String userInput = "12 DAys";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_TWELVE_DAYS_LATER_DATE);
		assertEquals(parsedDayString, REFERENCE_TWELVE_DAYS_LATER_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_TWELVE_DAYS_LATER_DAY_INT);
	}
	
	@Test
	public void testParseTwelveDay() {
		String userInput = "12 day";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_TWELVE_DAYS_LATER_DATE);
		assertEquals(parsedDayString, REFERENCE_TWELVE_DAYS_LATER_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_TWELVE_DAYS_LATER_DAY_INT);
	}
	
	@Test
	public void testParseTwelveDayLaterCaseInsensitive() {
		String userInput = "12 DAy";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_TWELVE_DAYS_LATER_DATE);
		assertEquals(parsedDayString, REFERENCE_TWELVE_DAYS_LATER_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_TWELVE_DAYS_LATER_DAY_INT);
	}
	
	@Test
	public void testParseTwelveDD() {
		String userInput = "12 dd";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_TWELVE_DAYS_LATER_DATE);
		assertEquals(parsedDayString, REFERENCE_TWELVE_DAYS_LATER_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_TWELVE_DAYS_LATER_DAY_INT);
	}
	
	@Test
	public void testParseTwelveDDCaseInsensitive() {
		String userInput = "12 Dd";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_TWELVE_DAYS_LATER_DATE);
		assertEquals(parsedDayString, REFERENCE_TWELVE_DAYS_LATER_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_TWELVE_DAYS_LATER_DAY_INT);
	}
	
	@Test
	public void testParseNextWeek() {
		String userInput = "next week";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_NEXT_WEEK_DATE);
		assertEquals(parsedDayString, REFERENCE_NEXT_WEEK_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_NEXT_WEEK_DAY_INT);
	}
	
	@Test
	public void testParseNextWeekCaseInsensitive() {
		String userInput = "next wEEk";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_NEXT_WEEK_DATE);
		assertEquals(parsedDayString, REFERENCE_NEXT_WEEK_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_NEXT_WEEK_DAY_INT);
	}
	
	@Test
	public void testParseNextWeeks() {
		String userInput = "next weeks";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_NEXT_WEEK_DATE);
		assertEquals(parsedDayString, REFERENCE_NEXT_WEEK_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_NEXT_WEEK_DAY_INT);
	}
	
	@Test
	public void testParseNextWeeksCaseInsensitive() {
		String userInput = "next wEEks";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_NEXT_WEEK_DATE);
		assertEquals(parsedDayString, REFERENCE_NEXT_WEEK_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_NEXT_WEEK_DAY_INT);
	}
	
	@Test
	public void testParseNextWk() {
		String userInput = "next wk";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_NEXT_WEEK_DATE);
		assertEquals(parsedDayString, REFERENCE_NEXT_WEEK_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_NEXT_WEEK_DAY_INT);
	}
	
	@Test
	public void testParseThreeWeeksLater() {
		String userInput = "3 weeks";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_THREE_WEEKS_LATER_DATE);
		assertEquals(parsedDayString, REFERENCE_THREE_WEEKS_LATER_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_THREE_WEEKS_LATER_DAY_INT);
	}
	
	@Test
	public void testParseThreeWeeksLaterCaseInsensitive() {
		String userInput = "3 wEEks";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_THREE_WEEKS_LATER_DATE);
		assertEquals(parsedDayString, REFERENCE_THREE_WEEKS_LATER_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_THREE_WEEKS_LATER_DAY_INT);
	}
	
	@Test
	public void testParseThreeWeekLater() {
		String userInput = "3 week";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_THREE_WEEKS_LATER_DATE);
		assertEquals(parsedDayString, REFERENCE_THREE_WEEKS_LATER_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_THREE_WEEKS_LATER_DAY_INT);
	}
	
	@Test
	public void testParseThreeWeekLaterCaseInsensitive() {
		String userInput = "3 weEK";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_THREE_WEEKS_LATER_DATE);
		assertEquals(parsedDayString, REFERENCE_THREE_WEEKS_LATER_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_THREE_WEEKS_LATER_DAY_INT);
	}
	
	@Test
	public void testParseThreeWkLater() {
		String userInput = "3 wk";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_THREE_WEEKS_LATER_DATE);
		assertEquals(parsedDayString, REFERENCE_THREE_WEEKS_LATER_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_THREE_WEEKS_LATER_DAY_INT);
	}
	
	@Test
	public void testParseThreeWkLaterCaseInsensitive() {
		String userInput = "3 WK";
		DateTime userDate = referenceDateTimeParser.parse(userInput);
		
		String parsedDate = userDate.getDate();
		String parsedDayString = userDate.getDayStr();
		int parsedDayInt = userDate.getDayInt();
		
		assertEquals(parsedDate, REFERENCE_THREE_WEEKS_LATER_DATE);
		assertEquals(parsedDayString, REFERENCE_THREE_WEEKS_LATER_DAY_STRING);
		assertEquals(parsedDayInt, REFERENCE_THREE_WEEKS_LATER_DAY_INT);
	}
	
	@Test 
	public void testParseNumberDate() {
		String numberDate = "1/10/2016";
		DateTime userDate = dateTimeParser.parse(numberDate);
		
		String parsedDate = userDate.getDate();
		String expectedDate = "1 Oct 2016";
		assertEquals(parsedDate, expectedDate);
	}
	
	@Test 
	public void testParseNumberDateWithoutYearNotOver() {
		String numberDate = "1/10";
		DateTime userDate = dateTimeParser.parse(numberDate);
		
		String parsedDate = userDate.getDate();
		String expectedDate = "1 Oct 2016";
		assertEquals(parsedDate, expectedDate);
	}
	
	@Test 
	public void testParseNumberDateWithoutYearOver() {
		String numberDate = "1/2";
		DateTime userDate = dateTimeParser.parse(numberDate);
		
		String parsedDate = userDate.getDate();
		String expectedDate = "1 Feb 2017";
		assertEquals(parsedDate, expectedDate);
	}
	
	@Test 
	public void testParseWordDate() {
		String numberDate = "12 Dec 2016";
		DateTime userDate = dateTimeParser.parse(numberDate);
		
		String parsedDate = userDate.getDate();
		String expectedDate = "12 Dec 2016";
		assertEquals(parsedDate, expectedDate);
	}
	
	@Test 
	public void testParseWordDateWithoutYearOver() {
		String numberDate = "12 Mar";
		DateTime userDate = dateTimeParser.parse(numberDate);
		
		String parsedDate = userDate.getDate();
		String expectedDate = "12 Mar 2017";
		assertEquals(parsedDate, expectedDate);
	}
	
	@Test 
	public void testParseWordDateWithoutYearNotOver() {
		String numberDate = "12 Sep";
		DateTime userDate = dateTimeParser.parse(numberDate);
		
		String parsedDate = userDate.getDate();
		String expectedDate = "12 Sep 2016";
		assertEquals(parsedDate, expectedDate);
	}
	
	@Test 
	public void testParseWordDateMonthOnlyNotOver() {
		String numberDate = "Sep";
		DateTime userDate = dateTimeParser.parse(numberDate);
		
		String parsedDate = userDate.getDate();
		String expectedDate = "15 Sep 2016";
		assertEquals(parsedDate, expectedDate);
	}
	
	@Test 
	public void testParseWordDateMonthOnlyOver() {
		String numberDate = "Feb";
		DateTime userDate = dateTimeParser.parse(numberDate);
		
		String parsedDate = userDate.getDate();
		String expectedDate = "15 Feb 2017";
		assertEquals(parsedDate, expectedDate);
	}
	
	@Test
	public void testParseTenThirtyAmWithSpaceAndDotTimeSeparator() {
		String timeInput = "10.30 am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, referenceDateTimeObject.getDate());
		assertEquals(dayStrParsed, referenceDayString);
		assertEquals(dayIntParsed, referenceDayInt);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 1030;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "10:30";
		String expected12HTimeString = "10.30 am";
		
		assertEquals(timeInt, expectedTimeInt);
		assertEquals(time24HString, expected24HTimeString);
		assertEquals(time12HString, expected12HTimeString);
	}
	
	@Test
	public void testParseTenThirtyAmWithSpaceAndDotTimeSeparatorCaseInsensitive() {
		String timeInput = "10.30 Am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, referenceDateTimeObject.getDate());
		assertEquals(dayStrParsed, referenceDayString);
		assertEquals(dayIntParsed, referenceDayInt);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 1030;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "10:30";
		String expected12HTimeString = "10.30 am";
		
		assertEquals(timeInt, expectedTimeInt);
		assertEquals(time24HString, expected24HTimeString);
		assertEquals(time12HString, expected12HTimeString);
	}
	
	
	@Test
	public void testParseTenThirtyAmWithoutSpaceAndDotTimeSeparator() {
		String timeInput = "10.30am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, referenceDateTimeObject.getDate());
		assertEquals(dayStrParsed, referenceDayString);
		assertEquals(dayIntParsed, referenceDayInt);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 1030;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "10:30";
		String expected12HTimeString = "10.30 am";
		
		assertEquals(timeInt, expectedTimeInt);
		assertEquals(time24HString, expected24HTimeString);
		assertEquals(time12HString, expected12HTimeString);
	}
	
	@Test
	public void testParseTenThirtyAmWithoutSpaceAndDotTimeSeparatorCaseInsensitive() {
		String timeInput = "10.30AM";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, referenceDateTimeObject.getDate());
		assertEquals(dayStrParsed, referenceDayString);
		assertEquals(dayIntParsed, referenceDayInt);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 1030;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "10:30";
		String expected12HTimeString = "10.30 am";
		
		assertEquals(timeInt, expectedTimeInt);
		assertEquals(time24HString, expected24HTimeString);
		assertEquals(time12HString, expected12HTimeString);
	}
	
	@Test
	public void testParseTenThirtyPmWithSpaceAndDotTimeSeparator() {
		String timeInput = "10.30 pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, referenceDateTimeObject.getDate());
		assertEquals(dayStrParsed, referenceDayString);
		assertEquals(dayIntParsed, referenceDayInt);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 2230;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "22:30";
		String expected12HTimeString = "10.30 pm";
		
		assertEquals(timeInt, expectedTimeInt);
		assertEquals(time24HString, expected24HTimeString);
		assertEquals(time12HString, expected12HTimeString);
	}
	
	@Test
	public void testParseTenThirtyPmWithSpaceAndDotTimeSeparatorCaseInsensitive() {
		String timeInput = "10.30 pM";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, referenceDateTimeObject.getDate());
		assertEquals(dayStrParsed, referenceDayString);
		assertEquals(dayIntParsed, referenceDayInt);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 2230;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "22:30";
		String expected12HTimeString = "10.30 pm";
		
		assertEquals(timeInt, expectedTimeInt);
		assertEquals(time24HString, expected24HTimeString);
		assertEquals(time12HString, expected12HTimeString);
	}
	
	@Test
	public void testParseTenThirtyPmWithoutSpaceAndDotTimeSeparator() {
		String timeInput = "10.30pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, referenceDateTimeObject.getDate());
		assertEquals(dayStrParsed, referenceDayString);
		assertEquals(dayIntParsed, referenceDayInt);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 2230;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "22:30";
		String expected12HTimeString = "10.30 pm";
		
		assertEquals(timeInt, expectedTimeInt);
		assertEquals(time24HString, expected24HTimeString);
		assertEquals(time12HString, expected12HTimeString);
	}
	
	@Test
	public void testParseTenThirtyPmWithoutSpaceAndDotTimeSeparatorCaseInsensitive() {
		String timeInput = "10.30PM";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, referenceDateTimeObject.getDate());
		assertEquals(dayStrParsed, referenceDayString);
		assertEquals(dayIntParsed, referenceDayInt);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 2230;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "22:30";
		String expected12HTimeString = "10.30 pm";
		
		assertEquals(timeInt, expectedTimeInt);
		assertEquals(time24HString, expected24HTimeString);
		assertEquals(time12HString, expected12HTimeString);
	}
	
	@Test
	public void testParseTenThirtyAmWithSpaceAndColonTimeSeparator() {
		String timeInput = "10:30 am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, referenceDateTimeObject.getDate());
		assertEquals(dayStrParsed, referenceDayString);
		assertEquals(dayIntParsed, referenceDayInt);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 1030;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "10:30";
		String expected12HTimeString = "10.30 am";
		
		assertEquals(timeInt, expectedTimeInt);
		assertEquals(time24HString, expected24HTimeString);
		assertEquals(time12HString, expected12HTimeString);
	}
	
	@Test
	public void testParseTenThirtyAmWithSpaceAndColonTimeSeparatorCaseInsensitive() {
		String timeInput = "10:30 AM";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, referenceDateTimeObject.getDate());
		assertEquals(dayStrParsed, referenceDayString);
		assertEquals(dayIntParsed, referenceDayInt);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 1030;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "10:30";
		String expected12HTimeString = "10.30 am";
		
		assertEquals(timeInt, expectedTimeInt);
		assertEquals(time24HString, expected24HTimeString);
		assertEquals(time12HString, expected12HTimeString);
	}
	
	@Test
	public void testParseTenThirtyAmWithoutSpaceAndColonTimeSeparator() {
		String timeInput = "10:30am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, referenceDateTimeObject.getDate());
		assertEquals(dayStrParsed, referenceDayString);
		assertEquals(dayIntParsed, referenceDayInt);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 1030;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "10:30";
		String expected12HTimeString = "10.30 am";
		
		assertEquals(timeInt, expectedTimeInt);
		assertEquals(time24HString, expected24HTimeString);
		assertEquals(time12HString, expected12HTimeString);
	}
	
	@Test
	public void testParseTenThirtyAmWithoutSpaceAndColonTimeSeparatorCaseInsensitive() {
		String timeInput = "10:30Am";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, referenceDateTimeObject.getDate());
		assertEquals(dayStrParsed, referenceDayString);
		assertEquals(dayIntParsed, referenceDayInt);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 1030;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "10:30";
		String expected12HTimeString = "10.30 am";
		
		assertEquals(timeInt, expectedTimeInt);
		assertEquals(time24HString, expected24HTimeString);
		assertEquals(time12HString, expected12HTimeString);
	}
	
	@Test
	public void testParseTenThirtyPmWithSpaceAndColonTimeSeparator() {
		String timeInput = "10:30 pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, referenceDateTimeObject.getDate());
		assertEquals(dayStrParsed, referenceDayString);
		assertEquals(dayIntParsed, referenceDayInt);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 2230;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "22:30";
		String expected12HTimeString = "10.30 pm";
		
		assertEquals(timeInt, expectedTimeInt);
		assertEquals(time24HString, expected24HTimeString);
		assertEquals(time12HString, expected12HTimeString);
	}
	
	@Test
	public void testParseTenThirtyPmWithoutSpaceAndColonTimeSeparator() {
		String timeInput = "10:30pm";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, referenceDateTimeObject.getDate());
		assertEquals(dayStrParsed, referenceDayString);
		assertEquals(dayIntParsed, referenceDayInt);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 2230;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "22:30";
		String expected12HTimeString = "10.30 pm";
		
		assertEquals(timeInt, expectedTimeInt);
		assertEquals(time24HString, expected24HTimeString);
		assertEquals(time12HString, expected12HTimeString);
	}
	
	@Test
	public void testParseTenThirtyPm24HFormat() {
		String timeInput = "22:30";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, referenceDateTimeObject.getDate());
		assertEquals(dayStrParsed, referenceDayString);
		assertEquals(dayIntParsed, referenceDayInt);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 2230;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "22:30";
		String expected12HTimeString = "10.30 pm";
		
		assertEquals(timeInt, expectedTimeInt);
		assertEquals(time24HString, expected24HTimeString);
		assertEquals(time12HString, expected12HTimeString);
	}
	
	@Test
	public void testParseTenThirtyAm24HFormat() {
		String timeInput = "10:30";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(timeInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, referenceDateTimeObject.getDate());
		assertEquals(dayStrParsed, referenceDayString);
		assertEquals(dayIntParsed, referenceDayInt);
		
		int timeInt = parsedDateTimeObject.getTimeInt();
		int expectedTimeInt = 1030;
		String time24HString = parsedDateTimeObject.getTime24hStr();
		String time12HString = parsedDateTimeObject.getTime12hStr();
		String expected24HTimeString = "10:30";
		String expected12HTimeString = "10.30 am";
		
		assertEquals(timeInt, expectedTimeInt);
		assertEquals(time24HString, expected24HTimeString);
		assertEquals(time12HString, expected12HTimeString);
	}
	
	@Test
	public void testParseToday() {
		String todayString = "today";
		DateTime todayParsed = dateTimeParser.parse(todayString);
		String dateParsed = todayParsed.getDate();
		String dayStrParsed = todayParsed.getDayStr();
		int dayIntParsed = todayParsed.getDayInt();
		
		assertEquals(dateParsed, todayDate);
		assertEquals(dayStrParsed, todayDayString);
		assertEquals(dayIntParsed, todayDayInt);
	}
	
	@Test
	public void testParseTdy() {
		String todayShortForm = "tdy";
		DateTime todayParsed = dateTimeParser.parse(todayShortForm);
		String dateParsed = todayParsed.getDate();
		String dayStrParsed = todayParsed.getDayStr();
		int dayIntParsed = todayParsed.getDayInt();
		
		assertEquals(dateParsed, todayDate);
		assertEquals(dayStrParsed, todayDayString);
		assertEquals(dayIntParsed, todayDayInt);
	}
	
	@Test
	public void testParseTodayCaseInsensitive() {
		String todayCaseInsensitive = "ToDAy";
		DateTime todayParsed = dateTimeParser.parse(todayCaseInsensitive);
		String dateParsed = todayParsed.getDate();
		String dayStrParsed = todayParsed.getDayStr();
		int dayIntParsed = todayParsed.getDayInt();
		
		assertEquals(dateParsed, todayDate);
		assertEquals(dayStrParsed, todayDayString);
		assertEquals(dayIntParsed, todayDayInt);
	}
	
	@Test
	public void testParseTdyCaseInsensitive() {
		String tdyCaseInsensitive = "TDy";
		DateTime todayParsed = dateTimeParser.parse(tdyCaseInsensitive);
		String dateParsed = todayParsed.getDate();
		String dayStrParsed = todayParsed.getDayStr();
		int dayIntParsed = todayParsed.getDayInt();
		
		assertEquals(dateParsed, todayDate);
		assertEquals(dayStrParsed, todayDayString);
		assertEquals(dayIntParsed, todayDayInt);
	}
	
	@Test
	public void testParseTomorrow() {
		String tomorrowInput = "tomorrow";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(tomorrowInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, REFERENCE_TOMORROW_DATE);
		assertEquals(dayStrParsed, REFERENCE_TOMORROW_DAY_STRING);
		assertEquals(dayIntParsed, REFERENCE_TOMORROW_DAY_INT);
	}
	
	@Test
	public void testParseTomorrowCaseInsensitive() {
		String tomorrowInput = "tomORroW";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(tomorrowInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, REFERENCE_TOMORROW_DATE);
		assertEquals(dayStrParsed, REFERENCE_TOMORROW_DAY_STRING);
		assertEquals(dayIntParsed, REFERENCE_TOMORROW_DAY_INT);
	}
	
	@Test
	public void testParseTmr() {
		String tomorrowInput = "tmr";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(tomorrowInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, REFERENCE_TOMORROW_DATE);
		assertEquals(dayStrParsed, REFERENCE_TOMORROW_DAY_STRING);
		assertEquals(dayIntParsed, REFERENCE_TOMORROW_DAY_INT);
	}
	
	@Test
	public void testParseTmrCaseInsensitive() {
		String tomorrowInput = "tMR";
		DateTime parsedDateTimeObject = referenceDateTimeParser.parse(tomorrowInput);
		String dateParsed = parsedDateTimeObject.getDate();
		String dayStrParsed = parsedDateTimeObject.getDayStr();
		int dayIntParsed = parsedDateTimeObject.getDayInt();
		
		assertEquals(dateParsed, REFERENCE_TOMORROW_DATE);
		assertEquals(dayStrParsed, REFERENCE_TOMORROW_DAY_STRING);
		assertEquals(dayIntParsed, REFERENCE_TOMORROW_DAY_INT);
	}
	
	@Test
	public void testParseWordDateAnd12HourAfternoonTimeWithSpacing() {
		String userInput = "15 June 2016 6 pm";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "15 Jun 2016");
		assertEquals(timeStr24H, "18:00");
		assertEquals(timeStr12H, "6 pm");
		assertEquals(dayStr, "Wednesday");
		assertEquals(dayInt, 3);
		assertEquals(timeInt, 1800);
	}
	
	@Test
	public void testParse12HourAfternoonTimeWithSpacingAndWordDate12HourAfternoonTimeWithSpacing() {
		String userInput = "6 pm 15 June 2016";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "15 Jun 2016");
		assertEquals(timeStr24H, "18:00");
		assertEquals(timeStr12H, "6 pm");
		assertEquals(dayStr, "Wednesday");
		assertEquals(dayInt, 3);
		assertEquals(timeInt, 1800);
	}
	
	@Test
	public void testParseDDMMYYDateAnd12HourAfternoonTimeWithoutSpacing() {
		String userInput = "19/02/2020 6.30pm";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "19 Feb 2020");
		assertEquals(timeStr24H, "18:30");
		assertEquals(timeStr12H, "6.30 pm");
		assertEquals(dayStr, "Wednesday");
		assertEquals(dayInt, 3);
		assertEquals(timeInt, 1830);
	}
	
	@Test
	public void testParse12HourAfternoonTimeWithoutSpacingAndDDMMYYDate() {
		String userInput = "6.30pm 19/02/2020";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "19 Feb 2020");
		assertEquals(timeStr24H, "18:30");
		assertEquals(timeStr12H, "6.30 pm");
		assertEquals(dayStr, "Wednesday");
		assertEquals(dayInt, 3);
		assertEquals(timeInt, 1830);
	}
	
	@Test
	public void testParseIncompleteWordNotOverDateAnd12HourMorningTimeWithSpacing() {
		String userInput = "15 Sept 6:45 am";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "15 Sep 2016");
		assertEquals(timeStr24H, "06:45");
		assertEquals(timeStr12H, "6.45 am");
		assertEquals(dayStr, "Thursday");
		assertEquals(dayInt, 4);
		assertEquals(timeInt, 645);
	}
	
	@Test
	public void testParse12HourMorningTimeWithSpacingAndIncompleteWordNotOverDate() {
		String userInput = "6:45 am 15 Sept";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "15 Sep 2016");
		assertEquals(timeStr24H, "06:45");
		assertEquals(timeStr12H, "6.45 am");
		assertEquals(dayStr, "Thursday");
		assertEquals(dayInt, 4);
		assertEquals(timeInt, 645);
	}
	
	@Test
	public void testParseIncompleteWordOverDateAnd12HourMorningTimeWithSpacing() {
		String userInput = "1 Jan 13:45";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "1 Jan 2017");
		assertEquals(timeStr24H, "13:45");
		assertEquals(timeStr12H, "1.45 pm");
		assertEquals(dayStr, "Sunday");
		assertEquals(dayInt, 7);
		assertEquals(timeInt, 1345);
	}
	
	@Test
	public void testParse12HourMorningTimeWithSpacingAndIncompleteWordOverDate() {
		String userInput = "13:45 1 Jan";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "1 Jan 2017");
		assertEquals(timeStr24H, "13:45");
		assertEquals(timeStr12H, "1.45 pm");
		assertEquals(dayStr, "Sunday");
		assertEquals(dayInt, 7);
		assertEquals(timeInt, 1345);
	}
	
	@Test
	public void testParse_DD_MM_OverDateAnd12HourMorningTimeWithoutSpacing() {
		String userInput = "10/2 6:45am";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "10 Feb 2017");
		assertEquals(timeStr24H, "06:45");
		assertEquals(timeStr12H, "6.45 am");
		assertEquals(dayStr, "Friday");
		assertEquals(dayInt, 5);
		assertEquals(timeInt, 645);
	}
	
	@Test
	public void testParse12HourMorningTimeWithoutSpacingAndDDMMOverDate() {
		String userInput = "6:45am 10/2";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "10 Feb 2017");
		assertEquals(timeStr24H, "06:45");
		assertEquals(timeStr12H, "6.45 am");
		assertEquals(dayStr, "Friday");
		assertEquals(dayInt, 5);
		assertEquals(timeInt, 645);
	}
	
	@Test
	public void testParseDDMMNotOverDateAnd24HourMorningTime() {
		String userInput = "10/10 8:45";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "10 Oct 2016");
		assertEquals(timeStr24H, "08:45");
		assertEquals(timeStr12H, "8.45 am");
		assertEquals(dayStr, "Monday");
		assertEquals(dayInt, 1);
		assertEquals(timeInt, 845);
	}
	
	@Test
	public void testParse24HourMorningTimeAndDDMMNotOverDate() {
		String userInput = "8:45 10/10";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "10 Oct 2016");
		assertEquals(timeStr24H, "08:45");
		assertEquals(timeStr12H, "8.45 am");
		assertEquals(dayStr, "Monday");
		assertEquals(dayInt, 1);
		assertEquals(timeInt, 845);
	}
	
	@Test
	public void testParseMMNotOverDateAnd24HourAfternoon() {
		String userInput = "Oct 19:15";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "15 Oct 2016");
		assertEquals(timeStr24H, "19:15");
		assertEquals(timeStr12H, "7.15 pm");
		assertEquals(dayStr, "Saturday");
		assertEquals(dayInt, 6);
		assertEquals(timeInt, 1915);
	}
	
	@Test
	public void testParse24HourAfternoonAndMMNotOverDate() {
		String userInput = "19:15 Oct";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "15 Oct 2016");
		assertEquals(timeStr24H, "19:15");
		assertEquals(timeStr12H, "7.15 pm");
		assertEquals(dayStr, "Saturday");
		assertEquals(dayInt, 6);
		assertEquals(timeInt, 1915);
	}
	
	@Test
	public void testParseMMNOverDateAnd24HourAfternoon() {
		String userInput = "Jan 19:15";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "15 Jan 2017");
		assertEquals(timeStr24H, "19:15");
		assertEquals(timeStr12H, "7.15 pm");
		assertEquals(dayStr, "Sunday");
		assertEquals(dayInt, 7);
		assertEquals(timeInt, 1915);
	}
	
	@Test
	public void testParse24HourAfternoonAndMMNOverDate() {
		String userInput = "19:15 Jan";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "15 Jan 2017");
		assertEquals(timeStr24H, "19:15");
		assertEquals(timeStr12H, "7.15 pm");
		assertEquals(dayStr, "Sunday");
		assertEquals(dayInt, 7);
		assertEquals(timeInt, 1915);
	}
	
}
