//@@author A0124586Y
package dooyit.storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CategoryData;
import dooyit.common.datatype.CustomColour;

public class CategoryControllerTest extends Constants {

	static final String FOLDER_TEST = CURRENT_DIRECTORY + SEPARATOR_CHAR + "test" + SEPARATOR_CHAR + "dooyit"
			+ SEPARATOR_CHAR;
	static final String FOLDER_TEST_STORAGE = FOLDER_TEST + "storage" + SEPARATOR_CHAR;
	
	private static final String BLACK = "black";
	private static final String BLUE = "blue";
	private static final String CYAN = "cyan";
	private static final String WHITE = "white";
	private static final String GREY = "grey";
	private static final String MAGENTA = "magenta";
	private static final String GREEN = "green";
	private static final String YELLOW = "yellow";
	private static final String RED = "red";
	private static final String PINK = "pink";
	
	@Test
	public void Save_ContentComparison_ExpectedPass() throws IOException {
		CategoryController categoryController = new CategoryController(FOLDER_TEST_STORAGE + "testSaveCat.txt");
		ArrayList<CategoryData> categories = new ArrayList<CategoryData>();
		categories.add(new CategoryData("A", BLUE));
		categories.add(new CategoryData("B", RED));
		categories.add(new CategoryData("C", PINK));
		categories.add(new CategoryData("D", YELLOW));
		
		Assert.assertTrue(categoryController.save(categories));

		String expected = "", saved = "";
		BufferedReader bReader = new BufferedReader(new FileReader(FOLDER_TEST_STORAGE + "expectedSaveCat.txt"));
		String categoryInfo = bReader.readLine();
		while (categoryInfo != null) {
			expected += categoryInfo;
			categoryInfo = bReader.readLine();
		}
		bReader.close();
		
		bReader = new BufferedReader(new FileReader(FOLDER_TEST_STORAGE + "testSaveCat.txt"));
		categoryInfo = bReader.readLine();
		while (categoryInfo != null) {
			saved += categoryInfo;
			categoryInfo = bReader.readLine();
		}
		bReader.close();
		
		Assert.assertEquals(expected, saved);
	}
	
	@Test
	public void Load_ContentComparison_ExpectedPass() throws IOException {
		CategoryController categoryController = new CategoryController(FOLDER_TEST_STORAGE + "testSaveCat.txt");
		ArrayList<Category> categories = new ArrayList<Category>();

		categories.add(new Category("A", CustomColour.BLUE));
		categories.add(new Category("B", CustomColour.RED));
		categories.add(new Category("C", CustomColour.PINK));
		categories.add(new Category("D", CustomColour.YELLOW));
		
		ArrayList<CategoryData> loaded = categoryController.load();
		ArrayList<Category> existingCat = new ArrayList<Category> ();
		for(CategoryData data: loaded) {
			Category existing = new Category(data.getName(), resolveColor(data.getColor()));
			existingCat.add(existing);
		}
		Assert.assertTrue(categories.equals(existingCat));
	}
	
	//Referenced from CustomColor class
	private CustomColour resolveColor(String colorName) {
		CustomColour color;
		String name = colorName.toLowerCase();
		
		if(name.equals(BLACK)) {
			color = CustomColour.BLACK;
		} else if (name.equals(BLUE)) {
			color = CustomColour.BLUE;
		} else if (name.equals(CYAN)) {
			color = CustomColour.CYAN;
		} else if (name.equals(GREY)) {
			color = CustomColour.GREY;
		} else if (name.equals(GREEN)) {
			color = CustomColour.GREEN;
		} else if (name.equals(MAGENTA)) {
			color = CustomColour.MAGENTA;
		} else if (name.equals(PINK)){
			color = CustomColour.PINK;
		} else if (name.equals(RED)) {
			color = CustomColour.RED;
		} else if (name.equals(YELLOW)) {
			color = CustomColour.YELLOW;
		} else if (name.equals(WHITE)) {
			color = CustomColour.WHITE;
		} else {
			//Random color generated but assume blue
			color = CustomColour.BLUE;
		}

		return color;
	}
}
