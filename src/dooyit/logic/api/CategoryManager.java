//@@author A0126356E
package dooyit.logic.api;

import java.util.ArrayList;
import dooyit.common.datatype.Category;
import dooyit.common.datatype.CustomColour;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.Constants;

/**
 * CategoryManager handles the creation and management of category and its
 * colour. Does not allow duplicate category.
 * 
 * @author limtaeu
 *
 */
public class CategoryManager {
	ArrayList<Category> categories;
	ColourManager colourManager;
	private Category selectedCategory;

	public CategoryManager() {
		colourManager = new ColourManager();
		categories = new ArrayList<Category>();
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
			throw new IncorrectInputException(String.format(Constants.FEEDBACK_INVALID_COLOUR_WITH_SUGGESTION, customColourString));
		}

		categoryName = capitalizeFirstCharacter(categoryName);
		CustomColour customColour = colourManager.find(customColourString);
		Category category = new Category(categoryName, customColour);
		categories.add(category);
		return category;
	}

	public void editCategoryName(Category category, String newCategoryName) {
		category.setName(capitalizeFirstCharacter(newCategoryName));
	}

	public boolean editCategoryColour(Category category, String newColourString) {
		if (colourManager.contains(newColourString)) {
			CustomColour customColour = colourManager.find(newColourString);
			category.setCustomColour(customColour);
			return true;
		} else {
			return false;
		}
	}

	public boolean containsCustomColour(String customColourString) {
		return colourManager.contains(customColourString);
	}

	public void setSelectedCategory(Category category) {
		this.selectedCategory = category;
	}

	public Category getSelectedCategory() {
		return selectedCategory;
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

		if (categoryName == Constants.EMPTY_STRING) {
			return Constants.EMPTY_STRING;
		}

		categoryName = categoryName.toLowerCase();
		char capitalFirstLetter = Character.toUpperCase(categoryName.charAt(0));
		String string = capitalFirstLetter + categoryName.substring(1);

		return string;
	}
}
