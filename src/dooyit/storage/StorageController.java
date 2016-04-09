//@@author A0124586Y
package dooyit.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.datatype.CategoryData;
import dooyit.common.datatype.TaskData;
import dooyit.common.exception.IncorrectInputException;
import dooyit.common.system.OsUtils;

/**
 * The StorageController class provides methods and attributes necessary for
 * loading and saving categories, tasks and custom css. It also contains methods to
 * change the storage location and save the user's preferences
 * 
 * @author Dex
 *
 */
public class StorageController {

	// Path related constants
	private static final String CSS = ".css";
	private static final String TXT = ".txt";
	private static final String FORWARD_SLASH = "/";
	private static final String BACK_SLASH = "\\";;

	// Error Messages
	private static final String ERROR_MESSAGE_FILEPATH = "INVALID path. Path needs to end with %1$s";
	private static final String ERROR_MESSAGE_ACCESS_CONFIG = "Cannot access configurations";
	private static final String ERROR_MESSAGE_LOAD_CSS = "Unable to load custom css";
	private static final String ERROR_MESSAGE_COPY_CSS = "Unable to copy css contents";

	// Preferences related constants
	private static final int TASK_DESTINATION = 0;
	private static final int THEME_DESTINATION = 1;
	private static final int PREFERENCES_SIZE = 2;

	private String configFilePath;
	private String[] preferences;
	private CategoryController categoryControl;
	private TaskController taskControl;
	private static Logger logger = Logger.getLogger("Storage");

	public StorageController() throws IOException {
		preferences = new String[PREFERENCES_SIZE];
		configFilePath = Constants.DEFAULT_CONFIG_DESTINATION;
		preferences = loadPreferences(configFilePath);
		categoryControl = new CategoryController(Constants.DEFAULT_CATEGORIES_DESTINATION);
		taskControl = new TaskController(preferences[TASK_DESTINATION]);
	}

	/**
	 * Sets a new storage location.
	 * 
	 * @param newFilePath
	 *            The new storage location.
	 * @return If the file exists, returns true. Otherwise returns false.
	 * @throws IOException
	 *             If unable to modify the config file.
	 */
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

	/**
	 * Saves the existing tasks.
	 * 
	 * @param categories
	 *            ArrayList of TaskData
	 * @return Returns true if save is successful.
	 * @throws IOException
	 *             If saving fails.
	 */
	public boolean saveTasks(ArrayList<TaskData> tasks) throws IOException {
		logger.log(Level.INFO, "Attempting to save tasks");
		assert tasks != null;

		return taskControl.save(tasks);
	}

	/**
	 * Loads the existing tasks.
	 * 
	 * @return Returns true if the load is successful.
	 * @throws IOException
	 *             If loading fails.
	 */
	public ArrayList<TaskData> loadTasks() throws IOException {
		logger.log(Level.INFO, "Attempting to load tasks");
		ArrayList<TaskData> taskList = taskControl.load();
		assert taskList != null;

		return taskList;
	}

	/**
	 * Saves the existing categories.
	 * 
	 * @param categories
	 *            ArrayList of CategoryData
	 * @return Returns true if save is successful.
	 * @throws IOException
	 *             If saving fails.
	 */
	public boolean saveCategory(ArrayList<CategoryData> categories) throws IOException {
		assert categories != null;
		return categoryControl.save(categories);
	}

	/**
	 * Loads the existing categories.
	 * 
	 * @return Returns true if the load is successful.
	 * @throws IOException
	 *             If loading fails.
	 */
	public ArrayList<CategoryData> loadCategory() throws IOException {
		ArrayList<CategoryData> categories = categoryControl.load();
		assert categories != null;

		return categories;
	}

	/**
	 * Loads the contents of a separate css file by copying it to the default
	 * custom css file
	 * 
	 * @param path
	 *            The path to the external css file.
	 * @return Returns true if the contents are successfully copied.
	 * @throws IOException
	 *             If unable to copy the contents from the external css file.
	 * @throws IncorrectInputException
	 *             If the path is not valid.
	 */
	public boolean loadCustomCss(String path) throws IOException, IncorrectInputException {
		if (isValidPath(path, CSS)) {
			copyCss(path);
			return true;
		}

		return false;
	}

	/**
	 * Checks if the path specified ends with the file extension required.
	 * 
	 * @param path
	 *            The specified path.
	 * @param fileExtension
	 *            The file extension.
	 * @return Returns true if the path is valid.
	 * @throws IncorrectInputException
	 *             If the path is invalid.
	 */
	private boolean isValidPath(String path, String fileExtension) throws IncorrectInputException {
		if (!path.endsWith(fileExtension)) {
			String errorMessage = String.format(ERROR_MESSAGE_FILEPATH, fileExtension);
			throw new IncorrectInputException(errorMessage);
		}

		return true;
	}

	/**
	 * Returns the default css path in a standardised format.
	 * 
	 * @return The String representation of the default css path.
	 */
	public String getCssPath() {
		String cssPath = preferences[THEME_DESTINATION].replace(BACK_SLASH, FORWARD_SLASH);

		if (OsUtils.isWindows()) {
			// Prepend "/" to Windows file path since Mac file system has "/"
			// for root
			cssPath = FORWARD_SLASH + cssPath;
		}

		return cssPath;
	}

	/**
	 * Copy the contents of an external css file to the default css file.
	 * 
	 * @param path
	 *            The path to the external css file.
	 * @throws IOException
	 *             If
	 */
	public void copyCss(String path) throws IOException {
		logger.log(Level.INFO, "Attempting to generate CSS");
		String defaultPath = preferences[THEME_DESTINATION];

		File file = new File(defaultPath);
		if (!file.exists()) {
			try {
				BufferedReader bReader = new BufferedReader(new FileReader(path));
				BufferedWriter bWriter = new BufferedWriter(new FileWriter(defaultPath));
				String line = bReader.readLine();
				while (line != null) {
					bWriter.append(line);
					bWriter.newLine();
					line = bReader.readLine();
				}
				bReader.close();
				bWriter.close();
			} catch (IOException e) {
				throw new IOException(ERROR_MESSAGE_COPY_CSS);
			}
		}
	}

	/**
	 * Generates the default custom.css file if it does not exist
	 * 
	 * @param path
	 *            URL representation of the path to the default custom css file
	 *            inside the JAR
	 * @throws IOException
	 *             If unable to generate the css file
	 */
	public void generateCss(URL path) throws IOException {
		logger.log(Level.INFO, "Attempting to generate CSS");
		String defaultPath = preferences[THEME_DESTINATION];

		File file = new File(defaultPath);
		if (!file.exists()) {
			try {
				BufferedReader bReader = new BufferedReader(new InputStreamReader(path.openStream()));
				BufferedWriter bWriter = new BufferedWriter(new FileWriter(defaultPath));
				String line = bReader.readLine();
				while (line != null) {
					bWriter.append(line);
					bWriter.newLine();
					line = bReader.readLine();
				}
				bReader.close();
				bWriter.close();
			} catch (IOException e) {
				throw new IOException(ERROR_MESSAGE_LOAD_CSS);
			}
		}
	}

	/**
	 * Loads the user's preferences from the config file and sets any invalid
	 * paths to the default
	 * 
	 * @param configFilePath
	 *            The path to the config file.
	 * @return The preferences of the user.
	 * @throws IOException
	 *             If unable to access the config file.
	 */
	private String[] loadPreferences(String configFilePath) throws IOException {
		File configFile = new File(configFilePath);
		String[] preferences = new String[PREFERENCES_SIZE];
		if (configFile.exists()) {
			try {
				BufferedReader bReader = new BufferedReader(new FileReader(configFile));
				for (int i = 0; i < PREFERENCES_SIZE; i++) {
					preferences[i] = bReader.readLine();
				}
				bReader.close();
			} catch (IOException e) {
				throw new IOException(ERROR_MESSAGE_ACCESS_CONFIG);
			}
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

	/**
	 * Checks if the specified path is invalid.
	 * 
	 * @param filePath
	 *            The specified path.
	 * @param fileExtension
	 *            The required file extension.
	 * @return Returns true if the path is invalid, otherwise returns false.
	 */
	private boolean isInvalidPath(String filePath, String fileExtension) {
		if (filePath == null || !filePath.endsWith(fileExtension)) {
			return true;
		}
		return false;
	}

	/**
	 * Modifies the config file with the updated preferences.
	 * 
	 * @param preferences
	 *            The preferences containing the save path and the location of
	 *            custom.css
	 * @throws IOException
	 *             If unable to write to the config file
	 */
	private void modifyConfig(String[] preferences) throws IOException {
		File configFile = new File(configFilePath);
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(configFile);
		} catch (IOException e) {
			throw new IOException(ERROR_MESSAGE_ACCESS_CONFIG);
		}

		assert fileWriter != null;
		BufferedWriter bWriter = new BufferedWriter(fileWriter);
		for (String path : preferences) {
			bWriter.append(path);
			bWriter.newLine();
		}
		bWriter.close();
	}

	/**
	 * 
	 * @return Returns the tasks save path in String representation.
	 */
	public String getFilePath() {
		return preferences[TASK_DESTINATION];
	}

	// @@author A0124586Y-unused
	/**
	 * 
	 * @return Returns the preferences of the user from config.txt
	 */
	public String[] getPreferences() {
		return this.preferences;
	}
}
