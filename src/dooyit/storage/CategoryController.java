package dooyit.storage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CategoryController {
	private String filePath;

	static final String DEFAULT_FOLDER_STORAGE = "data\\";
	static final String NAME_FILE_CATEGORY = "categories.txt";
	static final String CATEGORY_FORMAT = "%1$s, %2$s, %3$s, %4$s";

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
		int[] rgb = category.getRGB();
		String format = String.format(CATEGORY_FORMAT, category.getName(), rgb[0], rgb[1], rgb[2]);
		return format;
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
		String[] catInfo = catFormat.split(", ");
		Category existingCategory = new Category(catInfo);
		categories.add(existingCategory);
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
