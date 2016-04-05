//@@author A0124586Y
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
import java.io.InputStreamReader;
import java.net.URL;

import dooyit.storage.TaskController;
import dooyit.storage.CategoryController;
import dooyit.storage.Constants;
import dooyit.common.datatype.CategoryData;
import dooyit.common.datatype.TaskData;
import dooyit.common.exception.IncorrectInputException;

public class StorageController {

	private String configFilePath;
	private String[] preferences;
	private CategoryController categoryControl;
	private TaskController taskControl;
	private static Logger logger = Logger.getLogger("Storage");

	private static final String NAME_FILE_CONFIG = "config.txt";
	
	private static final String CSS = ".css";
	private static final String TXT = ".txt";
	private static final String FORWARD_SLASH = "/";
	private static final String BACK_SLASH = "\\";;
	
	private static final String ERROR_MESSAGE_FILEPATH = "INVALID path. Path needs to end with %1$s";
	private static final String ERROR_MESSAGE_CONFIG = "Error: Cannot modify configurations";
	
	private static final int TASK_DESTINATION = 0;
	private static final int THEME_DESTINATION = 1;
	//private static final int SKIN_CHOICE = 2;
	private static final int PREFERENCES_SIZE = 2;

	public StorageController() throws IOException {
		preferences = new String[PREFERENCES_SIZE];
		configFilePath = getConfigPath(Constants.CURRENT_DIRECTORY);
		preferences = loadPreferences(configFilePath);
		categoryControl = new CategoryController(Constants.DEFAULT_CATEGORIES_DESTINATION);
		taskControl = new TaskController(preferences[TASK_DESTINATION]);
	}

	private String getConfigPath(String currentPath) {
		logger.log(Level.INFO, "Getting save destination");
		return currentPath + Constants.SEPARATOR_CHAR + NAME_FILE_CONFIG;
	}

	public boolean setFileDestination(String newFilePath) throws IOException {
		logger.log(Level.INFO, "Changing save destination");

		if (isValidPath(newFilePath, TXT)) {
			preferences[TASK_DESTINATION] = newFilePath;
			modifyConfig(preferences);
			taskControl.setFileDestination(newFilePath);
			File file = new File(newFilePath);
			if (file.exists()) {
				return true;
			}
		}

		return false;
	}
	
	public boolean loadCustomCss(String path) throws IOException {
		if(isValidPath(path, CSS)) {
			copyCss(path);
			return true;
		}
		
		return false;
	}
	
	public String getCssPath() {
		String cssPath = preferences[THEME_DESTINATION].replace(BACK_SLASH, FORWARD_SLASH);
		
		return cssPath;
	}

	public boolean saveTasks(ArrayList<TaskData> tasks) throws IOException {
		logger.log(Level.INFO, "Attempting to save tasks");
		assert tasks != null;

		return taskControl.save(tasks);
	}

	public ArrayList<TaskData> loadTasks() throws IOException {
		logger.log(Level.INFO, "Attempting to load tasks");
		ArrayList<TaskData> taskList = taskControl.load();
		assert taskList != null;

		return taskList;
	}

	public boolean saveCategory(ArrayList<CategoryData> categories) throws IOException {
		assert categories != null;
		return categoryControl.save(categories);
	}

	public ArrayList<CategoryData> loadCategory() throws IOException {
		ArrayList<CategoryData> categories = categoryControl.load();
		assert categories != null;

		return categories;
	}
	
	public void copyCss(String path) throws IOException {
		logger.log(Level.INFO, "Attempting to generate CSS");
		String defaultPath = preferences[THEME_DESTINATION];

		File file = new File(defaultPath);
		if(!file.exists()) {
			BufferedReader bReader = new BufferedReader(new FileReader(path));
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(defaultPath));
			String line = bReader.readLine();
			while(line != null) {
				bWriter.append(line);
				bWriter.newLine();
				line = bReader.readLine();
			}
			bReader.close();
			bWriter.close();
		}
	}
	
	public void generateCss(URL path) throws IOException {
		logger.log(Level.INFO, "Attempting to generate CSS");
		String defaultPath = preferences[THEME_DESTINATION];

		File file = new File(defaultPath);
		if(!file.exists()) {
			BufferedReader bReader = new BufferedReader(new InputStreamReader(path.openStream()));
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(defaultPath));
			String line = bReader.readLine();
			while(line != null) {
				bWriter.append(line);
				bWriter.newLine();
				line = bReader.readLine();
			}
			bReader.close();
			bWriter.close();
		}
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
			preferences[TASK_DESTINATION] = Constants.DEFAULT_TASKS_DESTINATION;
		}
		if (isInvalidPath(preferences[THEME_DESTINATION], CSS)) {
			preferences[THEME_DESTINATION] = Constants.DEFAULT_THEME_DESTINATION;
		}

		modifyConfig(preferences);

		return preferences;
	}


	private boolean isValidPath(String filePath, String type) throws IncorrectInputException {
		if (!filePath.endsWith(type)) {
			String errorMessage = String.format(ERROR_MESSAGE_FILEPATH, type);
			throw new IncorrectInputException(errorMessage);
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
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(configFile);
		} catch (IOException e) {
			throw new IOException(ERROR_MESSAGE_CONFIG);
		}

		assert fileWriter != null;
		BufferedWriter bWriter = new BufferedWriter(fileWriter);
		for (String path : preferences) {
			bWriter.append(path);
			bWriter.newLine();
		}
		bWriter.close();
	}
}
