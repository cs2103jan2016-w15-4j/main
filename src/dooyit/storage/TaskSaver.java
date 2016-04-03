package dooyit.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTaskData;
import dooyit.common.datatype.EventTaskData;
import dooyit.common.datatype.FloatingTaskData;
import dooyit.common.datatype.TaskData;
import dooyit.storage.Constants;

public class TaskSaver {
	private String filePath;
	private static int count = 0;

	private static final int BACKUP_LIMIT = 5;

	protected TaskSaver(String filePath) {
		this.filePath = filePath;
	}

	public boolean save(ArrayList<TaskData> tasks) throws IOException {
		File file = new File(filePath);
		File directory = file.getParentFile();
		boolean isSaved = false;

		if (directory.exists()) {
			if (file.exists()) {
			} else {
				createFile(file);
			}
		} else {
			createFile(directory, file);
		}

		isSaved = saveTasks(file, tasks);

		return isSaved;
	}

	private boolean saveTasks(File file, ArrayList<TaskData> tasks) throws IOException {
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));

		for (TaskData existingTask : tasks) {
			bWriter.append(setFormat(existingTask));
			bWriter.newLine();
		}
		bWriter.close();
		count++;

		if (count > BACKUP_LIMIT) {
			saveBackup(tasks);
			count = 0;
		}

		return true;
	}

	private void saveBackup(ArrayList<TaskData> tasks) throws IOException {
		File backupFile = new File(Constants.DEFAULT_BACKUP_DESTINATION);
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(backupFile));
		for (TaskData existingTask : tasks) {
			bWriter.append(setFormat(existingTask));
			bWriter.newLine();
		}
		bWriter.close();
	}

	protected void setFileDestination(String path) {
		this.filePath = path;
	}

	private String setFormat(TaskData task) {
		Gson gson = gsonWithDateTimeSerializer();
		String json = "";

		if (task instanceof DeadlineTaskData) {
			DeadlineTaskData deadline = (DeadlineTaskData) task;
			json = gson.toJson(deadline);
		} else if (task instanceof EventTaskData) {
			EventTaskData event = (EventTaskData) task;
			json = gson.toJson(event);
		} else {
			FloatingTaskData floatData = (FloatingTaskData) task;
			json = gson.toJson(floatData);
		}

		return json;
	}

	private void createFile(File file) throws IOException {
		try {
			file.createNewFile();
		} catch (IOException e) {
			throw new IOException("Failed to create " + file.getName());
		}
	}

	private void createFile(File parent, File file) throws IOException {
		// creates the parent directories
		parent.mkdirs();

		createFile(file);
	}

	private Gson gsonWithDateTimeSerializer() {
		return new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeSerializer()).create();
	}
}
