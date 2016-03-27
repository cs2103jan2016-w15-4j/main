package dooyit.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dooyit.common.datatype.CustomColor;
import dooyit.common.datatype.Category;
import dooyit.common.exception.MissingFileException;

public class CategoryLoader {
	private String filePath;

	private static final String CATEGORY_NAME = "name";
	private static final String CATEGORY_COLOR = "color";
	private static final String BLACK = "black";
	private static final String BLUE = "blue";
	private static final String CYAN = "cyan";
	private static final String WHITE = "white";
	private static final String GREY = "grey";
	private static final String MAGENTA = "magenta";
	private static final String GREEN = "green";
	private static final String YELLOW = "yellow";
	private static final String RED = "red";
	private static final String PINK = "pink";

	protected CategoryLoader(String filePath) {
		this.filePath = filePath;
	}

	public ArrayList<Category> load() throws IOException {
		File categoryFile = new File(filePath);
		ArrayList<Category> categories = new ArrayList<Category> ();

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

	private ArrayList<Category> loadFromFile(File categoryFile) throws IOException {
		FileReader fReader;
		ArrayList<Category> categories = new ArrayList<Category> ();
		
		fReader = open(categoryFile);
		BufferedReader bReader = new BufferedReader(fReader);
		
		String categoryData = bReader.readLine();
		while (categoryData != null) {
			Category category = resolveCategory(categoryData);
			categories.add(category);
			categoryData = bReader.readLine();
		}
		bReader.close();
		fReader.close();
		
		return categories;
	}
	
	private Category resolveCategory(String categoryFormat) {
		JsonObject categoryInfo = getAsJson(categoryFormat);
		
		Category category = null;
		String name = categoryInfo.get(CATEGORY_NAME).getAsString();
		String colorName = categoryInfo.get(CATEGORY_COLOR).getAsString();
		CustomColor color = resolveColor(colorName);
		category = new Category(name, color);
		return category;
		
	}
	
	private JsonObject getAsJson(String format) {
		JsonParser parser = new JsonParser();
		JsonObject object = parser.parse(format).getAsJsonObject();
		
		return object;
	}
	
	private CustomColor resolveColor(String colorName) {
		CustomColor color;
		String name = colorName.toLowerCase();
		
		if(name.equals(BLACK)) {
			color = CustomColor.BLACK;
		} else if (name.equals(BLUE)) {
			color = CustomColor.BLUE;
		} else if (name.equals(CYAN)) {
			color = CustomColor.CYAN;
		} else if (name.equals(GREY)) {
			color = CustomColor.GREY;
		} else if (name.equals(GREEN)) {
			color = CustomColor.GREEN;
		} else if (name.equals(MAGENTA)) {
			color = CustomColor.MAGENTA;
		} else if (name.equals(PINK)){
			color = CustomColor.PINK;
		} else if (name.equals(RED)) {
			color = CustomColor.RED;
		} else if (name.equals(YELLOW)) {
			color = CustomColor.YELLOW;
		} else if (name.equals(WHITE)) {
			color = CustomColor.WHITE;
		} else {
			color = CustomColor.BLUE;
		}

		return color;
	}
}
