package dooyit.storage;

import java.io.File;

public class StorageConstants {

	static final char SEPARATOR_CHAR = File.separatorChar;
	
	static final String CURRENT_DIRECTORY = System.getProperty("user.dir");
	
	static final String FOLDER_DATA = CURRENT_DIRECTORY + SEPARATOR_CHAR + "data" + SEPARATOR_CHAR;

	static final String DEFAULT_CATEGORIES_DESTINATION = FOLDER_DATA + "categories.txt";
	static final String DEFAULT_BACKUP_DESTINATION = FOLDER_DATA + "backup.txt";
	static final String DEFAULT_TASKS_DESTINATION = FOLDER_DATA + "tasks.txt";
	static final String DEFAULT_THEME_DESTINATION = FOLDER_DATA + "custom.css";
	

	public StorageConstants() {

	}
}
