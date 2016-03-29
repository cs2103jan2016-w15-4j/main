package dooyit.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import dooyit.common.datatype.CustomColor;
import dooyit.common.datatype.Category;
import dooyit.common.datatype.CategoryData;
import dooyit.common.exception.MissingFileException;

public class CategoryLoader {
	private String filePath;

	private static final String CATEGORY_NAME = "name";
	private static final String CATEGORY_COLOR = "color";

	protected CategoryLoader(String filePath) {
		this.filePath = filePath;
	}

	public ArrayList<CategoryData> load() throws IOException {
		File categoryFile = new File(filePath);
		ArrayList<CategoryData> categories = new ArrayList<CategoryData> ();

		if (categoryFile.exists()) {
			categories = loadFromFile(categoryFile);
		}
		return categories;
	}
	
	private FileReader open(File file) throws FileNotFoundException {
		FileReader fReader = null;
		if (file.exists()) {
			try {
				fReader = new FileReader(file);
			} catch (MissingFileException mfe) {
				throw new MissingFileException(file.getName());
			}
		}
		return fReader;
	}

	private ArrayList<CategoryData> loadFromFile(File categoryFile) throws IOException {
		FileReader fReader;
		ArrayList<CategoryData> categories = new ArrayList<CategoryData> ();
		
		fReader = open(categoryFile);
		BufferedReader bReader = new BufferedReader(fReader);
		
		String categoryData = bReader.readLine();
		JsonObject jsonCategory;
		while (categoryData != null) {
			try {
				jsonCategory = getAsJson(categoryData);
			} catch (JsonSyntaxException e) {
				jsonCategory = null;
			}
			
			if(jsonCategory != null) {
				CategoryData category = resolveCategory(jsonCategory);
				categories.add(category);
			}
			categoryData = bReader.readLine();
		}
		bReader.close();
		fReader.close();
		
		return categories;
	}
	
	private CategoryData resolveCategory(JsonObject jsonCategory) {
		CategoryData category = null;
		String name = jsonCategory.get(CATEGORY_NAME).getAsString();
		String colorName = jsonCategory.get(CATEGORY_COLOR).getAsString();
		category = new CategoryData(name, colorName);
		
		return category;
	}
	
	private JsonObject getAsJson(String format) {
		JsonParser parser = new JsonParser();
		JsonObject object = parser.parse(format).getAsJsonObject();
		
		return object;
	}
}
