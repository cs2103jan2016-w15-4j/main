package dooyit.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTask;
import dooyit.common.datatype.DeadlineTaskData;
import dooyit.common.datatype.EventTask;
import dooyit.common.datatype.EventTaskData;
import dooyit.common.datatype.FloatingTaskData;
import dooyit.common.datatype.FloatingTask;
import dooyit.common.datatype.Task;
import dooyit.common.datatype.TaskData;
import dooyit.storage.StorageConstants;

public class TaskSaver {
	private String filePath;
	
	private static final String backupPath = StorageConstants.DEFAULT_BACKUP_DESTINATION;

	protected TaskSaver(String filePath) {
		this.filePath = filePath;
	}

	public boolean save(ArrayList<TaskData> tasks) throws IOException {
		File file = new File(filePath);
		File directory = file.getParentFile();
		boolean isSaved = false;
		
		if(directory.exists()) {
			if(file.exists()) {
				isSaved = saveTasks(file, tasks);
			} else {
				createFile(file);
				isSaved = saveTasks(file, tasks);
			}
		} else {
			createFile(directory, file);
			isSaved = saveTasks(file, tasks);
		}
		
		return isSaved;
	}
	
	private boolean saveTasks(File file, ArrayList<TaskData> tasks) throws IOException {
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));

		for (TaskData existingTask : tasks) {
			bWriter.append(setFormat(existingTask));
			bWriter.newLine();
		}
		bWriter.close();
		saveBackup(tasks);

		return true;
	}

	private void saveBackup(ArrayList<TaskData> tasks) throws IOException {
		File backupFile = new File(backupPath);
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(backupFile));
		for (TaskData existingTask : tasks) {
			bWriter.append(setFormat(existingTask));
			bWriter.newLine();
		}
		bWriter.close();
	}
	

	private String setFormat(TaskData task) {
		Gson gson = gsonWithDateTimeSerializer();
		String json = "";
		
		if(task instanceof DeadlineTaskData) {
			DeadlineTaskData deadline = (DeadlineTaskData)task;
			json = gson.toJson(deadline);
		} else if(task instanceof EventTaskData) {
			EventTaskData event = (EventTaskData)task;
			json = gson.toJson(event);
		} else {
			FloatingTaskData floatData = (FloatingTaskData)task;
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

	protected void setFileDestination(String path) {
		this.filePath = path;
	}
}
