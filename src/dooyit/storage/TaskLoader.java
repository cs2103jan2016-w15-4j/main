package dooyit.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dooyit.common.datatype.DateTime;
import dooyit.logic.core.TaskManager;

public class TaskLoader extends StorageConstants{
	
	private static final String DEADLINE = "dateTimeDeadline";
	private static final String EVENT_START = "dateTimeStart";
	private static final String EVENT_END = "dateTimeEnd";
	private static final String NAME = "taskName";
	private static final String IS_COMPLETED = "isCompleted";
	
	TaskLoader(String filePath) {
		this.filePath = filePath;
	}
	
	public boolean loadTasks(TaskManager taskManager) throws IOException{
		File directory = new File(filePath);

		if(directory.exists()) {
			String path = filePath + File.separatorChar + NAME_FILE_STORAGE;
			loadFromFile(path, taskManager);
		}
		else {
			createDirectory(directory);
		}

		return true;
	}
	
	private void createDirectory(File directory) {
		directory.mkdirs();
	}
	
	public void loadFromFile(String filePath, TaskManager taskManager) throws IOException {
		File file = new File(filePath);
		FileReader fReader = tryToOpen(file);
		if(fReader == null) {
			return;
		}
		else {
			readFromFile(fReader, taskManager);
		}
		
		return;
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
	
	private void loadToMemory(TaskManager taskManager, String taskFormat) {
		loadTask(taskManager, taskFormat);
	}
	
	public boolean loadTask(TaskManager taskManager, String taskFormat){
		JsonParser parser = new JsonParser();
		JsonObject taskInfo = parser.parse(taskFormat).getAsJsonObject();
		
		String name = taskInfo.get(NAME).getAsString();
		boolean isCompleted = taskInfo.get(IS_COMPLETED).getAsBoolean();
		
		if(taskInfo.has(DEADLINE)) {
			DateTime deadline = resolveDateTime(taskInfo, DEADLINE);
			taskManager.AddTaskDeadline(name, deadline, isCompleted);
			return true;
		}
		else if(taskInfo.has(EVENT_START) && taskInfo.has(EVENT_END)) {
			DateTime eventStart = resolveDateTime(taskInfo, EVENT_START);
			DateTime eventEnd= resolveDateTime(taskInfo, EVENT_END);
			taskManager.AddTaskEvent(name, eventStart, eventEnd, isCompleted);
			return true;
		}
		else {
			taskManager.AddTaskFloat(name, isCompleted);
			return true;
		}
	}
	
	private DateTime resolveDateTime(JsonObject taskInfo, String type) {
		String dateTimeString = taskInfo.get(type).getAsString();
		String[] parts = dateTimeString.split(" ");
		DateTime dateTime = new DateTime(Integer.valueOf(parts[0]),Integer.valueOf(parts[1]),
				Integer.valueOf(parts[2]), parts[3], parts[4]);
		
		return dateTime;
	}
	
	private void readFromFile(FileReader fReader, TaskManager taskManager) throws IOException {
		BufferedReader bReader = new BufferedReader(fReader);
		String taskInfo = bReader.readLine();
		while(taskInfo != null) {
			loadToMemory(taskManager, taskInfo);
			taskInfo = bReader.readLine();
		}
		bReader.close();
		fReader.close();
	}
}
