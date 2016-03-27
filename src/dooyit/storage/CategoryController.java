package dooyit.storage;

import java.io.IOException;
import java.util.ArrayList;

import dooyit.common.datatype.Category;

public class CategoryController extends StorageConstants {
	CategorySaver categorySaver;
	CategoryLoader categoryLoader;

	public CategoryController(String path) {
		categoryLoader = new CategoryLoader(path);
		categorySaver = new CategorySaver(path);
	}

	public boolean save(ArrayList<Category> categories) throws IOException {
		return categorySaver.save(categories);
	}

	public ArrayList<Category> load() throws IOException {
		return categoryLoader.load();
	}
}
