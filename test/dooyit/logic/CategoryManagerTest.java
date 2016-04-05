package dooyit.logic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CustomColor;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.LogicController;

public class CategoryManagerTest {

	LogicController logicController;
	CategoryManager categoryManager;

	Category schoolCat1;
	Category schoolCat2;
	Category schoolCat3;
	Category shoppingCat;

	@Before
	public void setUp() {
		logicController = new LogicController();
		logicController.disableSave();
		logicController.clearTasks();

		categoryManager = new CategoryManager();
		setUpCategory();
	}

	public void setUpCategory() {
		schoolCat1 = new Category("school");
		schoolCat2 = new Category("school");
		schoolCat3 = new Category("school", CustomColor.BLUE);
		shoppingCat = new Category("shopping");
	}

	@Test
	public void findCategory() {
		categoryManager.clear();
		Category addedCategory = categoryManager.addCategory("school");
		assertTrue(categoryManager.find("school").equals(addedCategory));
		assertTrue(categoryManager.find(addedCategory).equals(addedCategory));
		assertTrue(categoryManager.find("hello") == null);
		assertTrue(categoryManager.find(shoppingCat) == null);
	}

	@Test
	public void containsCategory() {
		categoryManager.clear();
		Category addedCategory = categoryManager.addCategory("school");
		assertTrue(categoryManager.contains("school"));
		assertFalse(categoryManager.contains("hello"));
		assertTrue(categoryManager.contains(addedCategory));
		assertFalse(categoryManager.contains(shoppingCat));
	}

	@Test
	public void addCategory() {
		categoryManager.clear();
		categoryManager.addCategory("school");
		categoryManager.contains("school");
		categoryManager.contains(schoolCat1);
	}

	@Test
	public void addCategoryWithColour() {
		categoryManager.clear();
		categoryManager.addCategory("school", "blue");
		categoryManager.contains(schoolCat3);
	}

	@Test(expected = IncorrectInputException.class)
	public void addExistingCategory() {
		categoryManager.clear();
		categoryManager.addCategory("school");
		categoryManager.addCategory("school");
	}

	@Test(expected = IncorrectInputException.class)
	public void addCategoryWithInvalidColour() {
		categoryManager.clear();
		categoryManager.addCategory("school", "darkorange");
	}
	
	@Test
	public void addCategoryWithDifferentLetterCase() {
		categoryManager.clear();
		categoryManager.addCategory("school");
		categoryManager.contains("school");
		categoryManager.contains(schoolCat1);

		categoryManager.clear();
		categoryManager.addCategory("school");
		categoryManager.contains("School");
		categoryManager.contains(schoolCat1);

		categoryManager.clear();
		categoryManager.addCategory("School");
		categoryManager.contains("school");
		categoryManager.contains(schoolCat1);

		categoryManager.clear();
		categoryManager.addCategory("SCHOOL");
		categoryManager.contains("school");
		categoryManager.contains(schoolCat1);
	}

	@Test
	public void setDefaultCategories(){
		categoryManager.clear();
		categoryManager.setDefaultCategories();
		categoryManager.find("school");
		categoryManager.find("entertainment");
	}
	
	@Test
	public void getAllCategories(){
		categoryManager.clear();
		Category addedCat1 = categoryManager.addCategory("school");
		Category addedCat2 =categoryManager.addCategory("vehicle");
		
		ArrayList<Category> categories = categoryManager.getAllCategories();
		assertTrue(categories.contains(addedCat1));
		assertTrue(categories.contains(addedCat2));
	}
	
	@Test
	public void loadCategories(){
		categoryManager.clear();
		ArrayList<Category> categories = new ArrayList<Category>();
		categories.add(schoolCat1);
		categories.add(shoppingCat);
		categoryManager.load(categories);
		
		assertTrue(categoryManager.contains(schoolCat1));
		assertTrue(categoryManager.contains(shoppingCat));
	}
	
}
