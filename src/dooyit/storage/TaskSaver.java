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
import dooyit.common.datatype.EventTask;
import dooyit.common.datatype.FloatingTask;
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
		Gson gson = gsonWithDateTimeSerializer();
		String json = "";
		
		switch(task.getTaskType()) {
		case DEADLINE :
			DeadlineTaskData deadline = setDeadline((DeadlineTask)task);
			json = gson.toJson(deadline);
			break;
		case EVENT :
			EventTaskData event = setEvent((EventTask)task);
			json = gson.toJson(event);
			break;
		case FLOATING :
			FloatTaskData floatData = setFloating((FloatingTask)task);
			json = gson.toJson(floatData);
			break;
		}

		return json;
	}

	private FloatTaskData setFloating(FloatingTask floating) {
		FloatTaskData floatData;
		String name = floating.getName();
		boolean isCompleted = floating.isCompleted();
		
		if(floating.hasCategory()) {
			Category category = floating.getCategory();
			floatData = new FloatTaskData(name, category, isCompleted);
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
			deadlineData = new DeadlineTaskData(name, deadlineTime, category, isCompleted);
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
			eventData = new EventTaskData(name, start, end, category, isCompleted);
		} else {
			eventData = new EventTaskData(name, start, end, isCompleted);
		}
		return eventData;
	}
	
	private Gson gsonWithDateTimeSerializer() {
		return new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeSerializer()).create();
	}

	protected void setFileDestination(String path) {
		this.filePath = path;
	}
}
