package dooyit.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dooyit.common.datatype.CustomColor;
import dooyit.common.exception.MissingFileException;
import dooyit.logic.api.CategoryManager;

public class CategoryLoader {
	private String filePath;

	private static final String CATEGORY_NAME = "name";
	private static final String CATEGORY_COLOR = "color";

	protected CategoryLoader(String filePath) {
		this.filePath = filePath;
	}

	public boolean loadCategory(CategoryManager categoryManager) throws IOException {
		File categoryFile = new File(filePath);
		FileReader fReader = null;

		if (categoryFile.exists()) {
			fReader = tryToOpen(categoryFile);
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
		CustomColor customColor = resolveColour(category.get(CATEGORY_COLOR).getAsString());
		categoryManager.addCategory(name, customColor);
	}

	private CustomColor resolveColour(String colourFormat) {
		String[] rgb = colourFormat.split(" ");
		//CustomColor customColor = new CustomColor("",Float.valueOf(rgb[0]), Float.valueOf(rgb[1]), Float.valueOf(rgb[2]));

		return null;
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
}
