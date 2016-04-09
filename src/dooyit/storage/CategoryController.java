//@@author A0124586Y
package dooyit.storage;

import java.io.IOException;
import java.util.ArrayList;

import dooyit.common.datatype.CategoryData;

/**
 * The CategoryController is a facade class for the StorageController to save or
 * load categories
 * 
 * @author Dex
 *
 */
public class CategoryController {
	private CategorySaver categorySaver;
	private CategoryLoader categoryLoader;

	public CategoryController(String path) {
		categoryLoader = new CategoryLoader(path);
		categorySaver = new CategorySaver(path);
	}

	/**
	 * Saves the list of categories
	 * 
	 * @param tasks
	 *            An ArrayList of CategoryData to be saved
	 * @return Returns true if categories are saved successfully, otherwise returns
	 *         false.
	 * @throws IOException
	 *             If the save file cannot be accessed.
	 */
	public boolean save(ArrayList<CategoryData> categories) throws IOException {
		return categorySaver.save(categories);
	}

	/**
	 * Loads the list of categories from the save file
	 * 
	 * @return A list of CategoryData to be loaded into the application.
	 * @throws IOException
	 *             If loading fails
	 */
	public ArrayList<CategoryData> load() throws IOException {
		return categoryLoader.load();
	}
}
