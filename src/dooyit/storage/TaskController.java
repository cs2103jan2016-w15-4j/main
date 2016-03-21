package dooyit.storage;

import java.io.IOException;
import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.logic.core.TaskManager;

public class TaskController {
	TaskSaver taskSaver;
	TaskLoader taskLoader;

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

	protected ArrayList<Task> load() throws IOException {
		return taskLoader.load();
	}

	protected String getFilePath() {
		return taskSaver.getFilePath();
	}
}
