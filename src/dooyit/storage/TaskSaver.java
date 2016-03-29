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
import dooyit.common.datatype.FloatTaskData;
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
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
		//Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeSerializer()).setPrettyPrinting().create();
		//String json = gson.toJson(tasks);

		for (TaskData existingTask : tasks) {
			//bWriter.append(json);
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
			FloatTaskData floatData = (FloatTaskData)task;
			json = gson.toJson(floatData);
		}

		return json;
	}

	/*
	private FloatTaskData setFloating(FloatingTask floating) {
		FloatTaskData floatData;
		String name = floating.getName();
		boolean isCompleted = floating.isCompleted();
		
		if(floating.hasCategory()) {
			Category category = floating.getCategory();
			floatData = new FloatTaskData(name, category.getName(), isCompleted);
		} else {
			floatData = new FloatTaskData(name, isCompleted);
		}
		return floatData;
	}
	
	private DeadlineTaskData setDeadline(DeadlineTask deadline) {
		DeadlineTaskData deadlineData;
		String name = deadline.getName();
		DateTime deadlineTime = deadline.getDateTimeDeadline();
		
		boolean isCompleted = deadline.isCompleted();
		if(deadline.hasCategory()) {
			Category category = deadline.getCategory();
			deadlineData = new DeadlineTaskData(name, deadlineTime, category.getName(), isCompleted);
		} else {
			deadlineData = new DeadlineTaskData(name, deadlineTime, isCompleted);
		}
		
		return deadlineData;
	}

	private EventTaskData setEvent(EventTask event) {
		EventTaskData eventData;
		String name = event.getName();
		DateTime start = event.getDateTimeStart();
		DateTime end = event.getDateTimeEnd();
		boolean isCompleted = event.isCompleted();
		
		if(event.hasCategory()) {
			Category category = event.getCategory();
			eventData = new EventTaskData(name, start, end, category.getName(), isCompleted);
		} else {
			eventData = new EventTaskData(name, start, end, isCompleted);
		}
		return eventData;
	}*/
	
	private Gson gsonWithDateTimeSerializer() {
		return new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeSerializer()).create();
	}

	protected void setFileDestination(String path) {
		this.filePath = path;
	}
}
