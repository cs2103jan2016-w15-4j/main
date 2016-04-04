//@@author A0126356E
package dooyit.logic;

import java.util.ArrayList;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CustomColor;
import dooyit.common.exception.IncorrectInputException;

public class CategoryManager {
	ArrayList<Category> categories;
	ColourManager colourManager;

	public CategoryManager() {
		colourManager = new ColourManager();
		categories = new ArrayList<Category>();
		// setDefaultCategories();
	}

	public void setDefaultCategories() {
		if (categories.size() == 0) {
			addCategory(Constants.DEFAULT_CATEGORY_SCHOOL);
			addCategory(Constants.DEFAULT_CATEGORY_ENTERTAINMENT);
		}
	}

	public void addCategory(Category category) throws IncorrectInputException {
		if (contains(category)) {
			throw new IncorrectInputException(String.format(Constants.FEEDBACK_FAIL_CATEGORY_EXISTS, category.getName()));
		}

		categories.add(category);
	}

	public Category addCategory(String categoryName) throws IncorrectInputException {
		if (contains(categoryName)) {
			throw new IncorrectInputException(String.format(Constants.FEEDBACK_FAIL_CATEGORY_EXISTS, categoryName));
		}

		categoryName = capitalizeFirstCharacter(categoryName);
		Category category = new Category(categoryName, colourManager.pickRandomCustomColour());
		categories.add(category);
		return category;
	}

	public Category addCategory(String categoryName, String customColourString) throws IncorrectInputException {
		if (contains(categoryName)) {
			throw new IncorrectInputException(String.format(Constants.FEEDBACK_FAIL_CATEGORY_EXISTS, categoryName));
		}

		if (!colourManager.contains(customColourString)) {
			addCategory(categoryName);
			throw new IncorrectInputException(String.format(Constants.FEEDBACK_INVALID_COLOUR, customColourString));
		}

		categoryName = capitalizeFirstCharacter(categoryName);
		CustomColor customColour = colourManager.find(customColourString);
		Category category = new Category(categoryName, customColour);
		categories.add(category);
		return category;
	}

	public Category remove(String categoryName) {
		for (Category category : categories) {
			if (category.equals(categoryName)) {
				categories.remove(category);
				return category;
			}
		}
		return null;
	}

	public boolean remove(Category inCategory) {
		for (Category category : categories) {
			if (category.equals(inCategory)) {
				categories.remove(category);
				return true;
			}
		}
		return false;
	}

	public boolean contains(String categoryName) {
		for (int i = 0; i < categories.size(); i++) {
			if (categories.get(i).equals(categoryName)) {
				return true;
			}
		}
		return false;
	}

	public boolean contains(Category category) {
		for (int i = 0; i < categories.size(); i++) {
			if (categories.get(i).equals(category)) {
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

	public ArrayList<Category> getAllCategories() {
		return categories;
	}

	public ArrayList<Category> clear() {
		ArrayList<Category> clearedCategories = new ArrayList<Category>(categories);
		this.categories.clear();
		return clearedCategories;
	}

	public void load(ArrayList<Category> categories) {
		this.categories.addAll(categories);
	}

	private String capitalizeFirstCharacter(String categoryName) {
		assert (categoryName != null);

		if (categoryName == "") {
			return "";
		}

		categoryName = categoryName.toLowerCase();
		char capitalFirstLetter = Character.toUpperCase(categoryName.charAt(0));
		String string = capitalFirstLetter + categoryName.substring(1);

		return string;
	}
}
