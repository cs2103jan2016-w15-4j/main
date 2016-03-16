package dooyit.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.MissingFileException;
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
		File file = new File(filePath);
		File directory = file.getParentFile();
		
		if(directory.exists()) {
			String path = filePath;
			loadFromFile(path, taskManager);
		}
		else {
			createFile(directory, file);
		}

		return true;
	}
	
	private void createFile(File directory, File file) throws IOException{
		//creates the parent directories
		directory.mkdirs();
		
		try {
			file.createNewFile();
		} catch (IOException e) {
			throw new IOException("Failed to create " + file.getName());
		}
	}
	
	public void loadFromFile(String path, TaskManager taskManager) throws IOException{
		File file = new File(path);
		FileReader fReader;
		fReader = tryToOpen(file);

		try {
			readFromFile(fReader, taskManager);
		} catch (IOException e) {
			throw new IOException("Unable to read " + file.getName());
		}
	}
	
	private FileReader tryToOpen(File file) throws FileNotFoundException{
		FileReader fReader = null;
		if(file.exists()) {
			try {
				fReader = new FileReader(file);
			} catch (MissingFileException mfe) {
				throw new MissingFileException(file.getName());
			}
		}
		return fReader;
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
	
	private void loadToMemory(TaskManager taskManager, String taskFormat) {
		loadTask(taskManager, taskFormat);
	}
	
	public boolean loadTask(TaskManager taskManager, String taskFormat){
		JsonParser parser = new JsonParser();
		JsonObject taskInfo = parser.parse(taskFormat).getAsJsonObject();
		boolean isCompleted = false;
		
		String name = taskInfo.get(NAME).getAsString();
		
		if(taskInfo.has(IS_COMPLETED)) {
		isCompleted = taskInfo.get(IS_COMPLETED).getAsBoolean();
		}
		
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
}
