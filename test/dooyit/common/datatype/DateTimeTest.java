package dooyit.common.datatype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @@author Wu Wenqi <A0124278A>
 *
 */

public class DateTimeTest {
	DateTime dt1, dt2, dt3, dt4, currDT;

	@Before
	public void setUp() {
		dt1 = new DateTime();
		int[] date = { 28, 3, 2016 };
		dt2 = new DateTime(date);
		dt3 = new DateTime(date, 1200);
		dt4 = new DateTime(date, 0);
		currDT = new DateTime();
	}

	@Test
	public void getMultiDayString() {
		int[] startDate = new int[] { 3, 4, 2016 };
		int startTime = 900;
		DateTime start = new DateTime(startDate, startTime);

		int[] endDate = new int[] { 4, 4, 2016 };
		int endTime = 1600;
		DateTime end = new DateTime(endDate, endTime);

		ArrayList<String> listOfString = DateTime.getMultiDayString(start, end);
		ArrayList<String> expected = new ArrayList<String>(Arrays.asList("09:00 - 23:59", "00:00 - 16:00"));
		assertEquals(expected, listOfString);
	}

	@Test
	public void compareTo() {
		assertEquals(dt1.compareTo(dt2), 1);
		assertEquals(dt2.compareTo(dt3), 1);
		assertEquals(dt2.compareTo(dt4), 1);
	}

	@Test
	public void getDayStr() {
		assertEquals(dt1.getDayStr(), currDT.getDayStr());
		assertEquals(dt2.getDayStr(), "Monday");
		assertEquals(dt3.getDayStr(), "Monday");
	}

	@Test
	public void getDayInt() {
		assertEquals(dt1.getDayInt(), currDT.getDayInt());
		assertEquals(dt2.getDayInt(), 1);
	}

	@Test
	public void getDate() {
		assertEquals(dt1.getDate(), currDT.getDate());
	}

	@Test
	public void getTime24hStr() {
		assertEquals(dt2.getTime24hStr(), "-1");
		assertEquals(dt3.getTime24hStr(), "12:00");
		assertEquals(dt4.getTime24hStr(), "00:00");
	}

	@Test
	public void getTime12hStr() {
		assertEquals(dt2.getTime12hStr(), "-1");
		assertEquals(dt3.getTime12hStr(), "12.00 pm");
		assertEquals(dt4.getTime12hStr(), "12.00 am");
	}

	@Test
	public void getTimeInt() {
		assertEquals(dt2.getTimeInt(), -1);
		assertEquals(dt3.getTimeInt(), 1200);
		assertEquals(dt4.getTimeInt(), 0);
	}

	@Test
	public void getDD() {
		assertEquals(dt2.getDD(), 28);
		assertEquals(dt3.getDD(), 28);
	}

	@Test
	public void getMM() {
		assertEquals(dt2.getMM(), 3);
		assertEquals(dt3.getMM(), 3);
	}

	@Test
	public void getYY() {
		assertEquals(dt2.getYY(), 2016);
		assertEquals(dt3.getYY(), 2016);
	}

	@Test
	public void testToString() {
		assertEquals(dt2.toString(), "28 Mar 2016 Monday -1 -1");
		assertEquals(dt3.toString(), "28 Mar 2016 Monday 12:00 12.00 pm");
		assertEquals(dt4.toString(), "28 Mar 2016 Monday 00:00 12.00 am");
	}

	@Test
	public void hasTime() {
		assertFalse(dt2.hasTime());
		assertTrue(dt3.hasTime());
		assertTrue(dt4.hasTime());
	}

	@Test
	public void isTheSameDateAs() {
		assertTrue(dt2.isTheSameDateAs(dt3));
		assertTrue(dt3.isTheSameDateAs(dt4));
	}

	@Test
	public void increaseByOneDay() {
		dt1.increaseByOneDay();
		currDT.increaseByOneDay();
		assertEquals(dt1.getDD(), currDT.getDD());
	}
	
	@Test
	public void overlapOfTwoDates_FirstInterval_IsWithin_SecondInterval() {
		int[] dt1Arr = new int[]{3, 4, 2016};
		int dt1Start = 1900;
		int dt1End = 2000;
		int[] dt2Arr = new int[]{3, 4, 2016};
		int dt2Start = 1800;
		int dt2End = 2100;
		DateTime startOne = new DateTime(dt1Arr, dt1Start);
		DateTime endOne = new DateTime(dt1Arr, dt1End);
		DateTime startTwo = new DateTime(dt2Arr, dt2Start);
		DateTime endTwo = new DateTime(dt2Arr, dt2End);
		
		assertEquals(true, DateTime.isOverlap(startOne, endOne, startTwo, endTwo));
	}
	
	
	@Test
	public void overlapOfTwoDates_SecondInterval_IsWithin_FirstInterval() {
		int[] dt1Arr = new int[]{3, 4, 2016};
		int dt1Start = 1900;
		int dt1End = 2000;
		int[] dt2Arr = new int[]{3, 4, 2016};
		int dt2Start = 1930;
		int dt2End = 1945;
		DateTime startOne = new DateTime(dt1Arr, dt1Start);
		DateTime endOne = new DateTime(dt1Arr, dt1End);
		DateTime startTwo = new DateTime(dt2Arr, dt2Start);
		DateTime endTwo = new DateTime(dt2Arr, dt2End);
		
		assertEquals(true, DateTime.isOverlap(startOne, endOne, startTwo, endTwo));
	}
	
	@Test
	public void overlapOfTwoDates_StartOfFirstInterval_Equals_EndOfSecondInterval() {
		int[] dt1Arr = new int[]{3, 4, 2016};
		int dt1Start = 1900;
		int dt1End = 2000;
		int[] dt2Arr = new int[]{3, 4, 2016};
		int dt2Start = 1800;
		int dt2End = 1900;
		DateTime startOne = new DateTime(dt1Arr, dt1Start);
		DateTime endOne = new DateTime(dt1Arr, dt1End);
		DateTime startTwo = new DateTime(dt2Arr, dt2Start);
		DateTime endTwo = new DateTime(dt2Arr, dt2End);
		
		assertEquals(false, DateTime.isOverlap(startOne, endOne, startTwo, endTwo));
	}
	
	@Test
	public void overlapOfTwoDates_EndOfFirstInterval_Equals_StartOfSecondInterval() {
		int[] dt1Arr = new int[]{3, 4, 2016};
		int dt1Start = 1900;
		int dt1End = 2000;
		int[] dt2Arr = new int[]{3, 4, 2016};
		int dt2Start = 2000;
		int dt2End = 2100;
		DateTime startOne = new DateTime(dt1Arr, dt1Start);
		DateTime endOne = new DateTime(dt1Arr, dt1End);
		DateTime startTwo = new DateTime(dt2Arr, dt2Start);
		DateTime endTwo = new DateTime(dt2Arr, dt2End);
		
		assertEquals(false, DateTime.isOverlap(startOne, endOne, startTwo, endTwo));
	}
	
	@Test
	public void overlapOfTwoDates_StartOfFirstInterval_IsWithin_SecondInterval() {
		int[] dt1Arr = new int[]{3, 4, 2016};
		int dt1Start = 1900;
		int dt1End = 2200;
		int[] dt2Arr = new int[]{3, 4, 2016};
		int dt2Start = 1800;
		int dt2End = 2100;
		DateTime startOne = new DateTime(dt1Arr, dt1Start);
		DateTime endOne = new DateTime(dt1Arr, dt1End);
		DateTime startTwo = new DateTime(dt2Arr, dt2Start);
		DateTime endTwo = new DateTime(dt2Arr, dt2End);
		
		assertEquals(true, DateTime.isOverlap(startOne, endOne, startTwo, endTwo));
	}
	
	@Test
	public void overlapOfTwoDates_EndOfFirstInterval_IsWithin_SecondInterval() {
		int[] dt1Arr = new int[]{3, 4, 2016};
		int dt1Start = 1900;
		int dt1End = 2200;
		int[] dt2Arr = new int[]{3, 4, 2016};
		int dt2Start = 1100;
		int dt2End = 2300;
		DateTime startOne = new DateTime(dt1Arr, dt1Start);
		DateTime endOne = new DateTime(dt1Arr, dt1End);
		DateTime startTwo = new DateTime(dt2Arr, dt2Start);
		DateTime endTwo = new DateTime(dt2Arr, dt2End);
		
		assertEquals(true, DateTime.isOverlap(startOne, endOne, startTwo, endTwo));
	}
	
	@Test
	public void overlapOfTwoDates_NoOverlap() {
		int[] dt1Arr = new int[]{4, 4, 2016};
		int dt1Start = 1900;
		int dt1End = 2000;
		int[] dt2Arr = new int[]{3, 4, 2016};
		int dt2Start = 1800;
		int dt2End = 2100;
		DateTime startOne = new DateTime(dt1Arr, dt1Start);
		DateTime endOne = new DateTime(dt1Arr, dt1End);
		DateTime startTwo = new DateTime(dt2Arr, dt2Start);
		DateTime endTwo = new DateTime(dt2Arr, dt2End);
		
		assertEquals(false, DateTime.isOverlap(startOne, endOne, startTwo, endTwo));
	}
	/*
	 * @Test public void convertToSavableString() {
	 * assertEquals(dt3.convertToSavableString(), "28 3 2016"); }
	 */
}
