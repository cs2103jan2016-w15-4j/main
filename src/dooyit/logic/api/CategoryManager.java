package dooyit.logic.api;

import java.util.ArrayList;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CustomColor;

public class CategoryManager {
	ArrayList<Category> categories;
	ColourManager colourManager;

	public CategoryManager() {
		colourManager = ColourManager.getInstance();
		categories = new ArrayList<Category>();
		setDefaultCategories();
	}

	private void setDefaultCategories() {
		if (categories.size() == 0) {
			add("School");
			add("Entertainment");
		}
	}

	public Category add(String categoryName) {
		if (find(categoryName) != null) {
			return null;
		}
		
		categoryName = capitalizeFirstCharacter(categoryName);
		Category category = new Category(categoryName, colourManager.pickRandomColour());
		categories.add(category);
		return category;
	}

	public Category addCategory(String categoryName, CustomColor colour) {
		if (find(categoryName) != null) {
			return null;
		}
		
		categoryName = capitalizeFirstCharacter(categoryName);
		Category category = new Category(categoryName, colour);
		categories.add(category);
		return category;
	}

	public boolean contains(String categoryName) {
		for (int i = 0; i < categories.size(); i++) {
			if(categories.get(i).equals(categoryName)){
				return true;
			}
		}
		return false;
	}

	public boolean contains(Category category) {
		for (int i = 0; i < categories.size(); i++) {
			if(categories.get(i).equals(category)){
				return true;
			}
		}
		return false;
	}
	
	public Category find(String categoryName) {
		for (int i = 0; i < categories.size(); i++) {
			if (categories.get(i).equals(categoryName)) {
				return categories.get(i);
			}
		}
		return null;
	}
	
	public Category find(Category category) {
		for (int i = 0; i < categories.size(); i++) {
			if (categories.get(i).equals(category)) {
				return categories.get(i);
			}
		}
		return null;
	}

	public ArrayList<Category> getCategoryList() {
		return categories;
	}
	
	private String capitalizeFirstCharacter(String categoryName){
		assert(categoryName != null);
		
		if(categoryName == ""){
			return "";
		}
		
		categoryName = categoryName.toLowerCase();
		char capitalFirstLetter = Character.toUpperCase(categoryName.charAt(0));
		String string = capitalFirstLetter + categoryName.substring(1);
		
		return string;
	}
}
