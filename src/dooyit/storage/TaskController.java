package dooyit.storage;

import java.io.IOException;
import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.logic.core.TaskManager;

public class TaskController extends StorageConstants {
	TaskSaver saver;
	TaskLoader loader;

	public TaskController(String filePath) {
		this.filePath = filePath;
		loader = new TaskLoader(filePath);
		saver = new TaskSaver(filePath);
	}

	protected void setFileDestination(String newFilePath) {
		saver.setFileDestination(newFilePath);
		loader.setFileDestination(newFilePath);
	}

	protected boolean save(ArrayList<Task> tasks) throws IOException {
		return saver.saveTasks(tasks);
	}

	protected boolean load(TaskManager taskManager) throws IOException {
		return loader.loadTasks(taskManager);
	}

	public String getSaveDestination() {
		return filePath;
	}
}
