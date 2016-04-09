//@@author A0124586Y
package dooyit.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import dooyit.common.datatype.CategoryData;

/**
 * The CategorySaver class contains methods and attributes necessary for saving
 * categories.
 * 
 * @author Dex
 *
 */
public class CategorySaver extends Saver<CategoryData> {
	private static final String ERROR_MESSAGE_CATEGORY_SAVING = "Unable to save category data";

	private String filePath;
	private Gson gson;

	protected CategorySaver(String filePath) {
		this.filePath = filePath;
		this.gson = new Gson();
	}

	/**
	 * Saves the list of categories.
	 * 
	 * @param tasks
	 *            An ArrayList of CategoryData to be saved.
	 * @return Returns true if save is successful, otherwise returns false.
	 * @throws IOException
	 *             If the saved file cannot be written to.
	 */
	boolean save(ArrayList<CategoryData> categories) throws IOException {
		File file = new File(filePath);

		try {
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));

			for (CategoryData existingCategory : categories) {
				bWriter.append(setFormat(existingCategory));
				bWriter.newLine();
			}
			bWriter.close();
		} catch (IOException e) {
			throw new IOException(ERROR_MESSAGE_CATEGORY_SAVING);
		}

		return true;
	}

	/**
	 * Converts the Category object to CategoryData before further conversion to
	 * its JSON string.
	 * 
	 * @param category
	 *            The category to be converted.
	 * @return Returns the JSON string representation of the CategoryData.
	 */
	String setFormat(CategoryData category) {
		String categoryName = category.getName();
		String colorName = category.getColor();
		CategoryData categoryFormat = new CategoryData(categoryName, colorName);
		String json = gson.toJson(categoryFormat);

		return json;
	}
}
