//@@author A0124586Y
package dooyit.storage;

import java.io.IOException;
import java.util.ArrayList;

import dooyit.common.datatype.TaskData;

/**
 * The TaskController is a facade class for the StorageController to save or
 * load tasks
 * 
 * @author Dex
 *
 */
public class TaskController {
	private TaskSaver taskSaver;
	private TaskLoader taskLoader;

	public TaskController(String filePath) {
		taskLoader = new TaskLoader(filePath);
		taskSaver = new TaskSaver(filePath);
	}

	/**
	 * Updates the path which tasks will be saved and loaded.
	 * 
	 * @param newFilePath
	 *            The new path for saving and loading
	 */
	protected void setFileDestination(String newFilePath) {
		taskSaver.setFileDestination(newFilePath);
		taskLoader.setFileDestination(newFilePath);
	}

	/**
	 * Saves the list of tasks
	 * 
	 * @param tasks
	 *            A list of TaskData to be saved.
	 * @return Returns true if tasks are saved successfully, otherwise returns
	 *         false.
	 * @throws IOException
	 *             If the save file cannot be accessed.
	 */
	protected boolean save(ArrayList<TaskData> tasks) throws IOException {
		return taskSaver.save(tasks);
	}

	/**
	 * Loads the list of tasks from the save file.
	 * 
	 * @return A list of TaskData to be loaded.
	 * @throws IOException
	 *             If loading fails.
	 */
	protected ArrayList<TaskData> load() throws IOException {
		return taskLoader.load();
	}
}
