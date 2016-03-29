package dooyit.storage;

import java.io.IOException;
import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.logic.TaskManager;

public class TaskController {
	private TaskSaver taskSaver;
	private TaskLoader taskLoader;

	public TaskController(String filePath) {
		taskLoader = new TaskLoader(filePath);
		taskSaver = new TaskSaver(filePath);
	}

	protected void setFileDestination(String newFilePath) {
		taskSaver.setFileDestination(newFilePath);
		taskLoader.setFileDestination(newFilePath);
	}

	protected boolean save(ArrayList<Task> tasks) throws IOException {
		return taskSaver.save(tasks);
	}

	protected ArrayList<TaskData> load() throws IOException {
		return taskLoader.load();
	}
}
