package dooyit.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import dooyit.common.datatype.Category;

public class CategorySaver {
	private String filePath;

	protected CategorySaver(String filePath) {
		this.filePath = filePath;
	}

	protected boolean saveCategory(ArrayList<Category> categories) throws IOException {
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
		CategoryStorageFormat categoryFormat = new CategoryStorageFormat(category);
		Gson gson = new Gson();
		String json = gson.toJson(categoryFormat);
		return json;
	}

	protected String getSaveDestination() {
		return filePath;
	}
}
