//@@author A0124586Y
package dooyit.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import dooyit.common.datatype.CategoryData;

public class CategoryLoader {
	private static final String EMPTY_STRING = "";

	private String filePath;
	private JsonParser parser;
	private Gson gson;

	protected CategoryLoader(String filePath) {
		this.filePath = filePath;
		this.parser = new JsonParser();
		this.gson = gsonWithCategoryDataDeserializer();
	}

	public ArrayList<CategoryData> load() throws IOException {
		File categoryFile = new File(filePath);
		ArrayList<CategoryData> categories = new ArrayList<CategoryData>();

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
			} catch (FileNotFoundException e) {
				throw new FileNotFoundException(file.getName());
			}
		}
		return fReader;
	}

	private ArrayList<CategoryData> loadFromFile(File categoryFile) throws IOException {
		FileReader fReader;
		ArrayList<CategoryData> categories = new ArrayList<CategoryData>();

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

	private CategoryData resolveCategory(JsonObject jsonCategory) {
		CategoryData category = gson.fromJson(jsonCategory, CategoryData.class);

		return category;
	}

	private JsonObject getAsJson(String format) {
		JsonObject object = null;

		if (!format.equals(EMPTY_STRING)) {
			object = parser.parse(format).getAsJsonObject();
		}

		return object;
	}

	private Gson gsonWithCategoryDataDeserializer() {
		return new GsonBuilder().registerTypeAdapter(CategoryData.class, new CategoryDataDeserializer()).create();
	}
}
