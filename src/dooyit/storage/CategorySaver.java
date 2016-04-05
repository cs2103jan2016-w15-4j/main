//@@author A0124586Y
package dooyit.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import dooyit.common.datatype.CategoryData;

public class CategorySaver {
	private String filePath;
	private Gson gson;

	protected CategorySaver(String filePath) {
		this.filePath = filePath;
		this.gson = new Gson();
	}

	protected boolean save(ArrayList<CategoryData> categories) throws IOException {
		File file = new File(filePath);

		BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));

		for (CategoryData existingCategory : categories) {
			bWriter.append(setFormat(existingCategory));
			bWriter.newLine();
		}
		bWriter.close();

		return true;
	}

	private String setFormat(CategoryData category) {
		String categoryName = category.getName();
		String colorName = category.getColor();
		CategoryData categoryFormat = new CategoryData(categoryName, colorName);
		String json = gson.toJson(categoryFormat);
		
		return json;
	}
}
