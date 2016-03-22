package dooyit.storage;

import java.io.IOException;
import java.util.ArrayList;

import dooyit.common.datatype.Category;
import dooyit.logic.api.CategoryManager;

public class CategoryController extends StorageConstants {
	CategorySaver categorySaver;
	CategoryLoader categoryLoader;

	public CategoryController(String path) {
		categoryLoader = new CategoryLoader(path);
		categorySaver = new CategorySaver(path);
	}

	public boolean save(ArrayList<Category> categories) throws IOException {
		return categorySaver.saveCategory(categories);
	}

	public boolean load(CategoryManager categoryManager) throws IOException {
		return categoryLoader.loadCategory(categoryManager);
	}
}
