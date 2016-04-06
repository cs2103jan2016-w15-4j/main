package dooyit.common.datatype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @@author Wu Wenqi <A0124278A>
 *
 */

public class CategoryTest {
	Category cat1;
	Category cat2;
	Category cat3;

	@Before
	public void setUp() {
		cat1 = new Category("School", CustomColor.BLUE);
		cat2 = new Category("Chores");
		cat3 = new Category("CHORES");
	}

	@Test
	public void testGetName() {
		assertEquals(cat1.getName(), "School");
		assertEquals(cat2.getName(), "Chores");
	}

	@Test
	public void testGetColor() {
		assertTrue(cat1.getColour().equals(CustomColor.BLUE.getColor()));
	}

	@Test
	public void testGetCustomColor() {
		assertTrue(cat1.getCustomColour().equals(CustomColor.BLUE));
	}

	@Test
	public void testGetCustomColorName() {
		assertEquals(cat1.getCustomColourName(), "blue");
	}

	@Test
	public void testEquals() {
		assertFalse(cat1.equals(cat2));
		assertTrue(cat2.equals(cat3));
		assertTrue(cat2.equals("chores"));
	}

	@Test
	public void testToString() {
		String s = "School " + CustomColor.BLUE.toString();
		assertEquals(cat1.toString(), s);
	}

}
