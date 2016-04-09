//@@author A0124586Y
package dooyit.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import dooyit.common.datatype.CategoryData;

/**
 * The CategoryLoader class contains methods and attributes necessary for
 * loading categories.
 * 
 * @author Dex
 *
 */
public class CategoryLoader extends Loader<CategoryData> {

	private String filePath;
	private Gson gson;

	protected CategoryLoader(String filePath) {
		super();
		this.filePath = filePath;
		this.gson = gsonWithCategoryDataDeserializer();
	}

	/**
	 * Loads CategoryData from the saved file after checking if the file exists.
	 * 
	 * @return An ArrayList of CategoryData.
	 * @throws IOException
	 *             If unable to read from the file.
	 */
	public ArrayList<CategoryData> load() throws IOException {
		File categoryFile = new File(filePath);
		ArrayList<CategoryData> categories = new ArrayList<CategoryData>();

		if (categoryFile.exists()) {
			categories = loadFromFile(categoryFile);
		}
		return categories;
	}

	/**
	 * Loads CategoryData from an existing file
	 * 
	 * @param file
	 *            File instance of the existing save file
	 * @return ArrayList of TaskData from the save file
	 * @throws IOException
	 *             If unable to read from the save file
	 */
	private ArrayList<CategoryData> loadFromFile(File file) throws IOException {
		FileReader fReader;
		ArrayList<CategoryData> categories = new ArrayList<CategoryData>();

		fReader = open(file);
		BufferedReader bReader = new BufferedReader(fReader);

		String categoryData = bReader.readLine();
		JsonObject jsonCategory;
		while (categoryData != null) {
			try {
				jsonCategory = getAsJson(categoryData);
			} catch (JsonSyntaxException e) {
				//Null the object if the JSON string is corrupted
				jsonCategory = null;
			}

			if (jsonCategory != null) {
				CategoryData category = resolveCategory(jsonCategory);
				categories.add(category);
			}
			categoryData = bReader.readLine();
		}
		bReader.close();
		fReader.close();

		return categories;
	}
	
	/**
	 * Converts the JSON representation of a category to CategoryData.
	 * 
	 * @param jsonCategory JsonObject of CategoryData.
	 * @return Returns the CategoryData of the JsonObject.
	 */
	private CategoryData resolveCategory(JsonObject jsonCategory) {
		CategoryData category = gson.fromJson(jsonCategory, CategoryData.class);

		return category;
	}

	/**
	 * 
	 * @return Returns a Gson object with CategoryDataDeserializer.
	 */
	private Gson gsonWithCategoryDataDeserializer() {
		return new GsonBuilder().registerTypeAdapter(CategoryData.class, new CategoryDataDeserializer()).create();
	}
}
