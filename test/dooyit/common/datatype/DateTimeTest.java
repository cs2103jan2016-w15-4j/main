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
		assertEquals(dt2.compareTo(dt4), 0);
	}
	
	@Test
	public void testGetDayStr() {
		
	}

	@Test
	public void testGetDayInt() {
		
	}

	@Test
	public void testGetDate() {
		
	}

	@Test
	public void testGetTime24hStr() {
		
	}

	@Test
	public void testGetTime12hStr() {
		
	}

	@Test
	public void testGetTimeInt() {
		
	}

	@Test
	public void testGetDD() {
		
	}

	@Test
	public void testGetMM() {
		
	}

	@Test
	public void testGetYY() {
		
	}

	@Test
	public void testToString() {
		
	}

	@Test
	public void testHasTime() {
		
	}

	@Test
	public void testIsTheSameDateAs() {
		
	}

	@Test
	public void testIncreaseByOneDay() {
		
	}

	@Test
	public void testConvertToSavableString() {
		
	}
}
