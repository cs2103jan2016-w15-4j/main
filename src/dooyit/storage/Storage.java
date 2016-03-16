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
import dooyit.storage.StorageConstants;
import dooyit.common.datatype.Category;
import dooyit.common.datatype.Task;
import dooyit.logic.core.CategoryManager;
import dooyit.logic.core.TaskManager;

public class Storage {

	String currentPath;
	String configFilePath;
	String filePath;
	CategoryController catControl;
	TaskController taskControl;
	private static Logger logger = Logger.getLogger("Storage");

	static final String NAME_FILE_CONFIG = "config.txt";

	public Storage() throws IOException {
		currentPath = System.getProperty("user.dir");
		configFilePath = getConfigPath(currentPath);
		filePath = getTaskDestination(configFilePath);
		catControl = new CategoryController(currentPath);
		taskControl = new TaskController(filePath);
	}

	private String getConfigPath(String currentPath) {
		logger.log(Level.INFO, "Getting save destination");
		return currentPath + File.separatorChar + NAME_FILE_CONFIG;
	}

	public boolean setFileDestination(String newFilePath) throws IOException {
		logger.log(Level.INFO, "Changing save destination");
		boolean isNotEmpty = !newFilePath.equals("");
		assert isNotEmpty;
		modifyConfig(newFilePath);
		taskControl.setFileDestination(newFilePath);

		return true;
	}

	public boolean saveTasks(ArrayList<Task> tasks) throws IOException {
		logger.log(Level.INFO, "Attempting to save tasks\n");
		assert tasks != null;
		if (taskControl.save(tasks)) {
			logger.log(Level.INFO, "Successfully saved tasks!\n");
			return true;
		} else {
			logger.log(Level.SEVERE, "Failed to save tasks\n");
			return false;
		}

	}

	public boolean loadTasks(TaskManager taskManager) throws IOException {
		logger.log(Level.INFO, "Checking if Task Manager is there\n");
		assert taskManager != null;
		logger.log(Level.INFO, "Attempting to load tasks\n");
		if (taskControl.load(taskManager)) {
			logger.log(Level.INFO, "Successfully loaded tasks!\n");
			return true;
		} else {
			logger.log(Level.SEVERE, "Failed to load tasks");
			return false;
		}
	}

	public boolean saveCategory(ArrayList<Category> categories) throws IOException {
		assert categories != null;
		return catControl.saveCategory(categories);
	}

	public boolean loadCategory(CategoryManager categoryManager) throws IOException {
		assert categoryManager != null;
		return catControl.loadCategory(categoryManager);
	}

	private String getTaskDestination(String configFilePath) throws IOException {
		File configFile = new File(configFilePath);
		String saveDestination = "";
		if (configFile.exists()) {
			BufferedReader bReader = new BufferedReader(new FileReader(configFile));
			saveDestination = bReader.readLine();
			bReader.close();
		} else {
			String defaultSavePath = setDefaultPath(currentPath);
			modifyConfig(defaultSavePath);
			return defaultSavePath;
		}

		return saveDestination;
	}

	public String getTaskDestination() throws IOException {
		return taskControl.getSaveDestination();
	}

	private String setDefaultPath(String currentPath) {
		String path = currentPath + StorageConstants.DEFAULT_TASKS_DESTINATION;
		return path;
	}

	private void modifyConfig(String newFilePath) throws IOException {
		File configFile = new File(configFilePath);
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(configFile));
		bWriter.write(newFilePath);
		bWriter.close();
	}
}
