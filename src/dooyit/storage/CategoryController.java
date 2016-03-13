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
import dooyit.common.datatype.Colour;

public class CategoryController extends StorageConstants{

	static final String CATEGORY_NAME = "name";
	static final String CATEGORY_COLOUR = "colour";

	public CategoryController(String path) {
		filePath = path + File.separatorChar + DEFAULT_FOLDER_STORAGE
				+ NAME_FILE_CATEGORY;
	}

	public boolean saveCategory(ArrayList<Category> categories) throws IOException {
		File file = new File(filePath);
		
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));

		for(Category existingCategory: categories) {
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

	public ArrayList<Category> loadCategory() throws IOException {
		ArrayList<Category> categories = new ArrayList<Category>();
		File catFile = new File(filePath);
		FileReader fReader = null;
		
		if(catFile.exists()) {
			fReader = tryToOpen(catFile);
			if(fReader == null) {
				return categories;
			}
			else {
				readFromFile(fReader, categories);
			}
		}
		return categories;
	}
	
	private FileReader tryToOpen(File file) {
		FileReader fReader = null;
		if(file.exists()) {
			try {
				fReader = new FileReader(file);
			} catch (FileNotFoundException fnfe) {
				System.out.println("Unable to access file");
				System.exit(2);
			}
		}
		return fReader;
	}
	
	private void loadToMemory(ArrayList<Category> categories, String catFormat) {
		JsonParser parser = new JsonParser();
		JsonObject category = parser.parse(catFormat).getAsJsonObject();
		String name = category.get(CATEGORY_NAME).getAsString();
		Colour colour = resolveColour(category.get(CATEGORY_COLOUR).getAsString());
		
	}
	
	private Colour resolveColour(String colourFormat) {
		String[] rgb = colourFormat.split(" ");
		Colour colour = new Colour(Float.valueOf(rgb[0]), Float.valueOf(rgb[1]),
				Float.valueOf(rgb[2]));
		
		return colour;
	}
	
	private void readFromFile(FileReader fReader, ArrayList<Category> categories) throws IOException {
		BufferedReader bReader = new BufferedReader(fReader);
		String catInfo = bReader.readLine();
		while(catInfo != null) {
			loadToMemory(categories, catInfo);
			catInfo = bReader.readLine();
		}
		bReader.close();
		fReader.close();
	}
	
	public String getFilePath() {
		return this.filePath;
	}
}
