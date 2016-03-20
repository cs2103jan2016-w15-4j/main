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
import dooyit.logic.core.CategoryManager;
import dooyit.logic.core.TaskManager;

public class StorageController extends StorageConstants {
	
	private static final int TASK_DESTINATION = 0;
	private static final int THEME_DESTINATION = 1;

	private String configFilePath;
	private String[] preferences;
	private CategoryController categoryControl;
	private TaskController taskControl;
	private static Logger logger = Logger.getLogger("Storage");

	static final String NAME_FILE_CONFIG = "config.txt";

	public StorageController() throws IOException {
		preferences = new String[2];
		configFilePath = getConfigPath(CURRENT_DIRECTORY);
		preferences = loadPreferences(configFilePath);
		categoryControl = new CategoryController(CURRENT_DIRECTORY);
		taskControl = new TaskController(preferences[0]);
	}

	private String getConfigPath(String currentPath) {
		logger.log(Level.INFO, "Getting save destination");
		return currentPath + SEPARATOR_CHAR + NAME_FILE_CONFIG;
	}

	public boolean setFileDestination(String newFilePath) throws IOException, InvalidFilePathException {
		logger.log(Level.INFO, "Changing save destination");
		boolean isValid = isValidSavePath(newFilePath);
		assert isValid;
		preferences[TASK_DESTINATION] = newFilePath;
		modifyConfig(preferences);
		taskControl.setFileDestination(newFilePath);

		return true;
	}

	public boolean saveTasks(ArrayList<Task> tasks) throws IOException {
		logger.log(Level.INFO, "Attempting to save tasks to " + preferences);
		assert tasks != null;
		if (taskControl.save(tasks)) {
			logger.log(Level.INFO, "Successfully saved tasks!");
			return true;
		} else {
			logger.log(Level.SEVERE, "Failed to save tasks");
			return false;
		}

	}

	public boolean loadTasks(TaskManager taskManager) throws IOException {
		logger.log(Level.INFO, "Checking if Task Manager is there");
		assert taskManager != null;
		logger.log(Level.INFO, "Attempting to load tasks from " + preferences);
		if (taskControl.load(taskManager)) {
			logger.log(Level.INFO, "Successfully loaded tasks!");
			return true;
		} else {
			logger.log(Level.SEVERE, "Failed to load tasks");
			return false;
		}
	}

	public boolean saveCategory(ArrayList<Category> categories) throws IOException {
		assert categories != null;
		return categoryControl.save(categories);
	}

	public boolean loadCategory(CategoryManager categoryManager) throws IOException {
		assert categoryManager != null;
		return categoryControl.load(categoryManager);
	}

	private String[] loadPreferences(String configFilePath) throws IOException {
		File configFile = new File(configFilePath);
		String[] preferences = new String[2];
		if (configFile.exists()) {
			BufferedReader bReader = new BufferedReader(new FileReader(configFile));
			for(int i=0; i<preferences.length; i++) {
				preferences[i] = bReader.readLine();
			}
			bReader.close();
		}
		
		if(!isValidLoadPath(preferences[0])) {
			preferences[TASK_DESTINATION] = setDefaultPath(CURRENT_DIRECTORY, DEFAULT_TASKS_DESTINATION);
		}
		if(!isValidThemePath(preferences[1])) {
			preferences[THEME_DESTINATION] = setDefaultPath(CURRENT_DIRECTORY, DEFAULT_THEME_DESTINATION);
		}
		
		modifyConfig(preferences);
		
		return preferences;
	}
	
	private boolean isValidSavePath(String filePath) throws InvalidFilePathException {
		if(!filePath.endsWith(".txt")) {
			throw new InvalidFilePathException("MISSING FILE EXTENSON \".txt\"");
		}
	
		return true;
	}

	private boolean isValidLoadPath(String filePath) {
		if (filePath == null) {
			return false;
		} else if (!filePath.endsWith(".txt")) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean isValidThemePath(String filePath) {
		if (filePath == null) {
			return false;
		} else if (!filePath.endsWith(".css")) {
			return false;
		} else {
			return true;
		}
	}

	public String getFilePath() throws IOException {
		return preferences[TASK_DESTINATION];
	}
	
	public String[] getPreferences() {
		return this.preferences;
	}

	private String setDefaultPath(String currentPath, String type) {
		String path = currentPath + type;
		return path;
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
