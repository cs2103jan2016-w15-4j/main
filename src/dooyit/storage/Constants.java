//@@author A0124586Y
package dooyit.storage;

import java.io.File;

/**
 * The Constants class holds all the Storage constants which are shared by the
 * various classes in the Storage component
 * 
 * @author Dex
 *
 */
public class Constants {

	// System related constants
	static final char SEPARATOR_CHAR = File.separatorChar;
	static final String CURRENT_DIRECTORY = System.getProperty("user.dir");

	// Default data folder
	static final String FOLDER_DATA = CURRENT_DIRECTORY + SEPARATOR_CHAR + "data" + SEPARATOR_CHAR;

	// Default file paths
	static final String DEFAULT_BACKUP_DESTINATION = FOLDER_DATA + "backup.txt";
	static final String DEFAULT_CATEGORIES_DESTINATION = FOLDER_DATA + "categories.txt";
	static final String DEFAULT_CONFIG_DESTINATION = CURRENT_DIRECTORY + SEPARATOR_CHAR + "config.txt";
	static final String DEFAULT_TASKS_DESTINATION = FOLDER_DATA + "tasks.txt";
	static final String DEFAULT_THEME_DESTINATION = FOLDER_DATA + "custom.css";

	public Constants() {

	}
}
