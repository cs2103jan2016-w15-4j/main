package dooyit.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CustomColor;
import dooyit.common.exception.MissingFileException;
import dooyit.logic.core.CategoryManager;

public class CategoryController extends StorageConstants {

	static final String CATEGORY_NAME = "name";
	static final String CATEGORY_COLOUR = "colour";

	public CategoryController(String path) {
		filePath = path + DEFAULT_CATEGORIES_DESTINATION;
	}

	public boolean saveCategory(ArrayList<Category> categories) throws IOException {
		File file = new File(filePath);

		BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));

		for (Category existingCategory : categories) {
			bWriter.append(setFormat(existingCategory));
			bWriter.newLine();
		}
		bWriter.close();

		return true;
	}

	private String setFormat(Category category) {
		CategoryStorageFormat catFormat = new CategoryStorageFormat(category);
		Gson gson = new Gson();
		String json = gson.toJson(catFormat);
		return json;
	}

	public boolean loadCategory(CategoryManager categoryManager) throws IOException {
		File catFile = new File(filePath);
		FileReader fReader = null;

		if (catFile.exists()) {
			fReader = tryToOpen(catFile);
			if (fReader == null) {
				return false;
			} else {
				readFromFile(fReader, categoryManager);
			}
		}
		return true;
	}

	private FileReader tryToOpen(File file) throws FileNotFoundException {
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

	private void loadToMemory(CategoryManager categoryManager, String catFormat) {
		JsonParser parser = new JsonParser();
		JsonObject category = parser.parse(catFormat).getAsJsonObject();
		String name = category.get(CATEGORY_NAME).getAsString();
		CustomColor colour = resolveColour(category.get(CATEGORY_COLOUR).getAsString());
		categoryManager.addCategory(name, colour);
	}

	private CustomColor resolveColour(String colourFormat) {
		String[] rgb = colourFormat.split(" ");
		CustomColor colour = new CustomColor(Float.valueOf(rgb[0]), Float.valueOf(rgb[1]), Float.valueOf(rgb[2]));

		return colour;
	}

	private void readFromFile(FileReader fReader, CategoryManager categoryManager) throws IOException {
		BufferedReader bReader = new BufferedReader(fReader);
		String catInfo = bReader.readLine();
		while (catInfo != null) {
			loadToMemory(categoryManager, catInfo);
			catInfo = bReader.readLine();
		}
		bReader.close();
		fReader.close();
	}

	public String getFilePath() {
		return this.filePath;
	}
}
