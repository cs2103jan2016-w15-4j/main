package dooyit.storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
//import org.powermock.core.classloader.annotations.PrepareForTest;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CategoryData;
import dooyit.common.datatype.CustomColor;
import dooyit.storage.StorageConstants;

//@PrepareForTest(CategoryController.class)
public class CategoryControllerTest extends StorageConstants {

	static final String FOLDER_TEST = CURRENT_DIRECTORY + SEPARATOR_CHAR + "test" + SEPARATOR_CHAR + "dooyit"
			+ SEPARATOR_CHAR;
	static final String FOLDER_TEST_STORAGE = FOLDER_TEST + "storage" + SEPARATOR_CHAR;
	
	private static final String CATEGORY_NAME = "name";
	private static final String CATEGORY_COLOR = "color";
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
		ArrayList<Category> categories = new ArrayList<Category>();

		categories.add(new Category("A", CustomColor.BLUE));
		categories.add(new Category("B", CustomColor.RED));
		categories.add(new Category("C", CustomColor.PINK));
		categories.add(new Category("D", CustomColor.YELLOW));
		//Assert.assertTrue(categoryController.save(categories));

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

		categories.add(new Category("A", CustomColor.BLUE));
		categories.add(new Category("B", CustomColor.RED));
		categories.add(new Category("C", CustomColor.PINK));
		categories.add(new Category("D", CustomColor.YELLOW));
		
		ArrayList<CategoryData> loaded = categoryController.load();
		ArrayList<Category> existingCat = new ArrayList<Category> ();
		for(CategoryData data: loaded) {
			Category existing = new Category(data.getName(), resolveColor(data.getColor()));
			existingCat.add(existing);
		}
		Assert.assertTrue(categories.equals(existingCat));
	}
	
	//Referenced from CustomColor class
	private CustomColor resolveColor(String colorName) {
		CustomColor color;
		String name = colorName.toLowerCase();
		
		if(name.equals(BLACK)) {
			color = CustomColor.BLACK;
		} else if (name.equals(BLUE)) {
			color = CustomColor.BLUE;
		} else if (name.equals(CYAN)) {
			color = CustomColor.CYAN;
		} else if (name.equals(GREY)) {
			color = CustomColor.GREY;
		} else if (name.equals(GREEN)) {
			color = CustomColor.GREEN;
		} else if (name.equals(MAGENTA)) {
			color = CustomColor.MAGENTA;
		} else if (name.equals(PINK)){
			color = CustomColor.PINK;
		} else if (name.equals(RED)) {
			color = CustomColor.RED;
		} else if (name.equals(YELLOW)) {
			color = CustomColor.YELLOW;
		} else if (name.equals(WHITE)) {
			color = CustomColor.WHITE;
		} else {
			//Random color generated but assume blue
			color = CustomColor.BLUE;
		}

		return color;
	}
}
