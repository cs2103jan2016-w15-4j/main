package dooyit.storage;

import java.util.ArrayList;
import java.util.logging.Logger;

import java.util.logging.Level;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import dooyit.storage.TaskController;
import dooyit.storage.CategoryController;
import dooyit.common.datatype.Category;
import dooyit.common.datatype.Task;
import dooyit.common.exception.InvalidFilePathException;
import dooyit.logic.api.CategoryManager;
import dooyit.logic.api.TaskManager;

//@@author A0124586Y
public class StorageController extends StorageConstants {

	private String configFilePath;
	private String[] preferences;
	private CategoryController categoryControl;
	private TaskController taskControl;
	//private static Logger logger = Logger.getLogger("Storage");

	private static final String NAME_FILE_CONFIG = "config.txt";
	private static final int TASK_DESTINATION = 0;
	private static final int THEME_DESTINATION = 1;
	private static final int PREFERENCES_SIZE = 2;
	private static final String CSS = ".css";
	private static final String TXT = ".txt";

	public StorageController() throws IOException {
		preferences = new String[PREFERENCES_SIZE];
		configFilePath = getConfigPath(CURRENT_DIRECTORY);
		preferences = loadPreferences(configFilePath);
		categoryControl = new CategoryController(DEFAULT_CATEGORIES_DESTINATION);
		taskControl = new TaskController(preferences[TASK_DESTINATION]);
	}

	private String getConfigPath(String currentPath) {
		//logger.log(Level.INFO, "Getting save destination");
		return currentPath + SEPARATOR_CHAR + NAME_FILE_CONFIG;
	}

	public boolean setFileDestination(String newFilePath) throws IOException {
		//logger.log(Level.INFO, "Changing save destination");
		boolean isValid = isValidPath(newFilePath);
		assert isValid;

		preferences[TASK_DESTINATION] = newFilePath;
		modifyConfig(preferences);
		taskControl.setFileDestination(newFilePath);

		return true;
	}

	public boolean saveTasks(ArrayList<Task> tasks) throws IOException {
		//logger.log(Level.INFO, "Attempting to save tasks to " + preferences[TASK_DESTINATION]);
		assert tasks != null;

		return taskControl.save(tasks);
	}

	public ArrayList<Task> loadTasks() throws IOException {
		//logger.log(Level.INFO, "Attempting to load tasks from " + preferences[TASK_DESTINATION]);
		ArrayList<Task> taskList = taskControl.load();
		assert taskList != null;

		return taskList;

	}

	public boolean saveCategory(ArrayList<Category> categories) throws IOException {
		assert categories != null;
		return categoryControl.save(categories);
	}

	public ArrayList<Category> loadCategory() throws IOException {
		ArrayList<Category> categories = categoryControl.load();
		assert categories != null;
		
		return categories;
	}

	private String[] loadPreferences(String configFilePath) throws IOException {
		File configFile = new File(configFilePath);
		String[] preferences = new String[PREFERENCES_SIZE];
		if (configFile.exists()) {
			BufferedReader bReader = new BufferedReader(new FileReader(configFile));
			for (int i = 0; i < PREFERENCES_SIZE; i++) {
				preferences[i] = bReader.readLine();
			}
			bReader.close();
		}

		if (isInvalidPath(preferences[TASK_DESTINATION], TXT)) {
			preferences[TASK_DESTINATION] = DEFAULT_TASKS_DESTINATION;
		}
		if (isInvalidPath(preferences[THEME_DESTINATION], CSS)) {
			preferences[THEME_DESTINATION] = DEFAULT_THEME_DESTINATION;
		}

		modifyConfig(preferences);

		return preferences;
	}

	private boolean isValidPath(String filePath) {
		if (!filePath.endsWith(TXT)) {
			System.out.println("Error: Invalid path");
			return false;
		}

		return true;
	}

	private boolean isInvalidPath(String filePath, String fileType) {
		if (filePath == null || !filePath.endsWith(fileType)) {
			return true;
		}
		return false;
	}

	public String getFilePath() {
		return preferences[TASK_DESTINATION];
	}

	public String[] getPreferences() {
		return this.preferences;
	}

	private void modifyConfig(String[] preferences) throws IOException {
		File configFile = new File(configFilePath);
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(configFile));
		for (String path : preferences) {
			bWriter.append(path);
			bWriter.newLine();
		}
		bWriter.close();
	}
}
