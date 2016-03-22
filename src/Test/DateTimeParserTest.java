package Test;

import dooyit.parser.DateTimeParser;
import dooyit.common.datatype.DateTime;
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
	DateTimeParser dateTimeParser = PowerMockito.spy(new DateTimeParser());
	
	@Test
	public void testParseToday() {
		String todayString = "today";
		DateTime todayParsed = dateTimeParser.parse(todayString);
		String dateParsed = todayParsed.getDate();
		String dayStrParsed = todayParsed.getDayStr();
		int dayIntParsed = todayParsed.getDayInt();
		
		DateTime today = new DateTime();
		String dateTemplate = today.getDate();
		String dayStrTemplate = today.getDayStr();
		int dayIntTemplate = today.getDayInt();
		
		assertEquals(dateParsed, dateTemplate);
		assertEquals(dayStrParsed, dayStrTemplate);
		assertEquals(dayIntParsed, dayIntTemplate);
	}
	
	@Test
	public void testParseTdy() {
		String todayShortForm = "tdy";
		DateTime todayParsed = dateTimeParser.parse(todayShortForm);
		String dateParsed = todayParsed.getDate();
		String dayStrParsed = todayParsed.getDayStr();
		int dayIntParsed = todayParsed.getDayInt();
		
		DateTime today = new DateTime();
		String dateTemplate = today.getDate();
		String dayStrTemplate = today.getDayStr();
		int dayIntTemplate = today.getDayInt();
		
		assertEquals(dateParsed, dateTemplate);
		assertEquals(dayStrParsed, dayStrTemplate);
		assertEquals(dayIntParsed, dayIntTemplate);
	}
	
	@Test
	public void testParseTodayCaseInsensitive() {
		String todayCaseInsensitive = "ToDAy";
		DateTime todayParsed = dateTimeParser.parse(todayCaseInsensitive);
		String dateParsed = todayParsed.getDate();
		String dayStrParsed = todayParsed.getDayStr();
		int dayIntParsed = todayParsed.getDayInt();
		
		DateTime today = new DateTime();
		String dateTemplate = today.getDate();
		String dayStrTemplate = today.getDayStr();
		int dayIntTemplate = today.getDayInt();
		
		assertEquals(dateParsed, dateTemplate);
		assertEquals(dayStrParsed, dayStrTemplate);
		assertEquals(dayIntParsed, dayIntTemplate);
	}
	
	@Test
	public void testParseTdyCaseInsensitive() {
		String tdyCaseInsensitive = "TDy";
		DateTime todayParsed = dateTimeParser.parse(tdyCaseInsensitive);
		String dateParsed = todayParsed.getDate();
		String dayStrParsed = todayParsed.getDayStr();
		int dayIntParsed = todayParsed.getDayInt();
		
		DateTime today = new DateTime();
		String dateTemplate = today.getDate();
		String dayStrTemplate = today.getDayStr();
		int dayIntTemplate = today.getDayInt();
		
		assertEquals(dateParsed, dateTemplate);
		assertEquals(dayStrParsed, dayStrTemplate);
		assertEquals(dayIntParsed, dayIntTemplate);
	}
	
	public void testParseTomorrow() {
		
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
		System.out.println("dayStr is " + dayStr);
		assertEquals(dayStr, "thu");
		assertEquals(dayInt, 4);
		assertEquals(timeInt, 1915);
	}
}
