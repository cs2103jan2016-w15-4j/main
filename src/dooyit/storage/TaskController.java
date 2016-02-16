package dooyit.storage;
import java.io.IOException;
import java.util.ArrayList;

public class TaskController {
	TaskSaver saver;
	TaskLoader loader;
	
	public TaskController(String filePath) {
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
	
	protected ArrayList<Task> load() throws IOException {
		return loader.loadTasks();
	}
}
