package dooyit.storage;
import java.io.IOException;
import java.util.ArrayList;

import dooyit.logic.TaskManager;
import dooyit.logic.Task;

public class TaskController {
	TaskSaver saver;
	TaskLoader loader;
	
	public TaskController(String filePath) {
		loader = new TaskLoader(filePath);
		saver = new TaskSaver(filePath);
	}
	
	public void setFileDestination(String newFilePath) {
		saver.setFileDestination(newFilePath);
		loader.setFileDestination(newFilePath);
	}
	
	public boolean save(ArrayList<Task> tasks) throws IOException {
		return saver.saveTasks(tasks);
	}
	
	public boolean load(TaskManager taskManager) throws IOException {
		return loader.loadTasks(taskManager);
	}
}
