package dooyit.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import dooyit.common.datatype.Task;

public class TaskSaver {
	private String filePath;

	protected TaskSaver(String filePath) {
		this.filePath = filePath;
	}

	public boolean save(ArrayList<Task> tasks) throws IOException {
		File file = new File(filePath);
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));

		for (Task existingTask : tasks) {
			bWriter.append(setFormat(existingTask));
			bWriter.newLine();
		}
		bWriter.close();

		return true;
	}

	protected String setFormat(Task task) {
		TaskStorageFormat storageFormat = new TaskStorageFormat(task);
		Gson gson = new Gson();
		String json = gson.toJson(storageFormat);

		return json;
	}

	protected void setFileDestination(String path) {
		this.filePath = path;
	}

	protected String getFilePath() {
		return this.filePath;
	}
}
