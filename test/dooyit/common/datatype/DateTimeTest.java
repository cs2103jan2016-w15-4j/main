package dooyit.common.datatype;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DateTimeTest {
	DateTime dt1, dt2, dt3, dt4;
	
	@Before
	public void setUp(){
		dt1 = new DateTime();
		int[] date = {28, 3, 2016};
		dt2 = new DateTime(date, "Monday");
		dt3 = new DateTime(date, "Monday", 1200);
		dt4 = new DateTime(date, "Monday", 0);
	}
	
	@Test
	public void testCompareTo(){
		assertEquals(dt1.compareTo(dt2), 1);
		assertEquals(dt2.compareTo(dt3), -1);
		assertEquals(dt2.compareTo(dt4), -1);
	}
	
	@Test
	public void testGetDayStr() {
		assertEquals(dt1.getDayStr(), "Monday");
		assertEquals(dt2.getDayStr(), "Monday");
		assertEquals(dt3.getDayStr(), "Monday");
	}

	@Test
	public void testGetDayInt() {
		assertEquals(dt1.getDayInt(), 1);
		assertEquals(dt2.getDayInt(), 1);
	}

	@Test
	public void testGetDate() {
		assertEquals(dt1.getDate(), "28 Mar 2016");
	}

	@Test
	public void testGetTime24hStr() {
		assertEquals(dt2.getTime24hStr(), "-1");
		assertEquals(dt3.getTime24hStr(), "12:00");
		assertEquals(dt4.getTime24hStr(), "00:00");
	}

	@Test
	public void testGetTime12hStr() {
		assertEquals(dt2.getTime12hStr(), "-1");
		assertEquals(dt3.getTime12hStr(), "12.00 pm");
		assertEquals(dt4.getTime12hStr(), "12 am");
	}

	@Test
	public void testGetTimeInt() {
		assertEquals(dt2.getTimeInt(), -1);
		assertEquals(dt3.getTimeInt(), 1200);
		assertEquals(dt4.getTimeInt(), 0);
	}

	@Test
	public void testGetDD() {
		assertEquals(dt2.getDD(), 28);
		assertEquals(dt3.getDD(), 28);
	}

	@Test
	public void testGetMM() {
		assertEquals(dt2.getMM(), 3);
		assertEquals(dt3.getMM(), 3);
	}

	@Test
	public void testGetYY() {
		assertEquals(dt2.getYY(), 2016);
		assertEquals(dt3.getYY(), 2016);
	}

	@Test
	public void testToString() {
		assertEquals(dt2.toString(), "28 Mar 2016 Monday -1 -1");
		assertEquals(dt3.toString(), "28 Mar 2016 Monday 12:00 12.00 pm");
		assertEquals(dt4.toString(), "28 Mar 2016 Monday 00:00 12 am");
	}

	@Test
	public void testHasTime() {
		assertFalse(dt2.hasTime());
		assertTrue(dt3.hasTime());
		assertTrue(dt4.hasTime());
	}

	@Test
	public void testIsTheSameDateAs() {
		assertTrue(dt2.isTheSameDateAs(dt3));
		assertTrue(dt3.isTheSameDateAs(dt4));
	}

	@Test
	public void testIncreaseByOneDay() {
		dt1.increaseByOneDay();
		assertEquals(dt1.getDD(), 29);
	}

	@Test
	public void testConvertToSavableString() {
		assertEquals(dt3.convertToSavableString(), "28 3 2016 Monday 12:00");
	}
}
