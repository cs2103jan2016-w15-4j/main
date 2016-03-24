package dooyit.parser;

import dooyit.parser.DateTimeParser;
import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.*;
import org.junit.Assert.*;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DateTimeParser.class)
public class DateTimeParserTest {
	private static final int FORMAT_24H_2AM = 200;

	private static final String REFERENCE_TOMORROW_DAY_STR = "tue";
	private static final String REFERENCE_TOMORROW_DATE = "16 Feb 2016";
	private static final int REFERENCE_TOMORROW_DAY_INT = 2;
	
	private static final String REFERENCE_NEXT_WEEK_DAY_STR = "mon";
	private static final String REFERENCE_NEXT_WEEK_DATE = "22 Feb 2016";
	private static final int REFERENCE_NEXT_WEEK_DAY_INT = 1;
	
	private static final String REFERENCE_THREE_WEEKS_LATER_DAY_STR = "mon";
	private static final String REFERENCE_THREE_WEEKS_LATER_DATE = "7 Mar 2016";
	private static final int REFERENCE_THREE_WEEKS_LATER_DAY_INT = 1;

	DateTimeParser dateTimeParser = PowerMockito.spy(new DateTimeParser());
	
	int[] referenceDate = new int[] {15, 2, 2016};
	String referenceDayString = "mon";
	int referenceDayInt= 1;
	int referenceTime = FORMAT_24H_2AM;
	DateTime referenceDateTimeObject = new DateTime(referenceDate, referenceDayString, referenceTime);
	DateTimeParser referenceDateTimeParser = new DateTimeParser(referenceDateTimeObject);
	
	DateTime todayDateTimeObject = new DateTime();
	String todayDate = todayDateTimeObject.getDate();
	String todayDayString = todayDateTimeObject.getDayStr();
	int todayDayInt = todayDateTimeObject.getDayInt();
	
	@Test 
	public void testParseValidNumberDate() {
		String numberDate = "1/10/2016";
		DateTime userDate = dateTimeParser.parse(numberDate);
		
		String parsedDate = userDate.getDate();
		String expectedDate = "1 Oct 2016";
		assertEquals(parsedDate, expectedDate);
	}
	
	/* This is a boundary case for the ‘positive value’ partition */
	@Test(expected = IncorrectInputException.class) 
	public void testParseNumberDateWithInvalidPositiveDay() {
		String numberDate = "40/10/2016";
		DateTime userDate = dateTimeParser.parse(numberDate);
	}
	
	/* This is a boundary case for the ‘negative value’ partition */
	@Test(expected = IncorrectInputException.class) 
	public void testParseNumberDateWithInvalidNegativeDay() {
		String numberDate = "-1/10/2016";
		DateTime userDate = dateTimeParser.parse(numberDate);
		System.out.println("userDate is " + userDate);
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
		String expected24HTimeString = "1030";
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
		String expected24HTimeString = "1030";
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
		String expected24HTimeString = "1030";
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
		String expected24HTimeString = "1030";
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
		String expected24HTimeString = "2230";
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
		String expected24HTimeString = "2230";
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
		String expected24HTimeString = "2230";
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
		String expected24HTimeString = "2230";
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
		String expected24HTimeString = "1030";
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
		String expected24HTimeString = "1030";
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
		String expected24HTimeString = "1030";
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
		String expected24HTimeString = "1030";
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
		String expected24HTimeString = "2230";
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
		String expected24HTimeString = "2230";
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
		String expected24HTimeString = "2230";
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
		String expected24HTimeString = "1030";
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
		assertEquals(dayStrParsed, REFERENCE_TOMORROW_DAY_STR);
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
		assertEquals(dayStrParsed, REFERENCE_TOMORROW_DAY_STR);
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
		assertEquals(dayStrParsed, REFERENCE_TOMORROW_DAY_STR);
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
		assertEquals(dayStrParsed, REFERENCE_TOMORROW_DAY_STR);
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
		assertEquals(timeStr24H, "1800");
		assertEquals(timeStr12H, "6 pm");
		assertEquals(dayStr, "wed");
		assertEquals(dayInt, 3);
		assertEquals(timeInt, 1800);
	}
	
	@Test
	public void testParse_DD_MM_YY_DateAnd12HourAfternoonTimeWithoutSpacing() {
		String userInput = "19/02/2020 6.30pm";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "19 Feb 2020");
		assertEquals(timeStr24H, "1830");
		assertEquals(timeStr12H, "6.30 pm");
		assertEquals(dayStr, "wed");
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
		assertEquals(timeStr24H, "0645");
		assertEquals(timeStr12H, "6.45 am");
		assertEquals(dayStr, "thu");
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
		assertEquals(timeStr24H, "1345");
		assertEquals(timeStr12H, "1.45 pm");
		assertEquals(dayStr, "sun");
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
		assertEquals(timeStr24H, "0645");
		assertEquals(timeStr12H, "6.45 am");
		assertEquals(dayStr, "fri");
		assertEquals(dayInt, 5);
		assertEquals(timeInt, 645);
	}
	
	@Test
	public void testParse_DD_MM_NotOverDateAnd24HourMorning() {
		String userInput = "10/10 8:45";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "10 Oct 2016");
		assertEquals(timeStr24H, "0845");
		assertEquals(timeStr12H, "8.45 am");
		assertEquals(dayStr, "mon");
		assertEquals(dayInt, 1);
		assertEquals(timeInt, 845);
	}
	
	@Test
	public void testParse_MM_NotOverDateAnd24HourAfternoon() {
		String userInput = "Oct 19:15";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "15 Oct 2016");
		assertEquals(timeStr24H, "1915");
		assertEquals(timeStr12H, "7.15 pm");
		assertEquals(dayStr, "sat");
		assertEquals(dayInt, 6);
		assertEquals(timeInt, 1915);
	}
	
	@Test
	public void testParse_MM_OverDateAnd24HourAfternoon() {
		String userInput = "Dec 19:15";
		DateTime dateTime = dateTimeParser.parse(userInput);
		
		String date = dateTime.getDate();
		String timeStr24H = dateTime.getTime24hStr();
		String timeStr12H = dateTime.getTime12hStr();
		String dayStr = dateTime.getDayStr();
		int dayInt = dateTime.getDayInt();
		int timeInt = dateTime.getTimeInt();
		
		assertEquals(date, "15 Dec 2016");
		assertEquals(timeStr24H, "1915");
		assertEquals(timeStr12H, "7.15 pm");
		assertEquals(dayStr, "thu");
		assertEquals(dayInt, 4);
		assertEquals(timeInt, 1915);
	}
}
