package dooyit.storage;

import java.io.File;

public class StorageConstants {
	protected String filePath;

	static final char SEPARATOR_CHAR = File.separatorChar;
	static final String FOLDER_DATA = SEPARATOR_CHAR + "data" + SEPARATOR_CHAR;

	static final String DEFAULT_CATEGORIES_DESTINATION = FOLDER_DATA + "categories.txt";
	static final String DEFAULT_TASKS_DESTINATION = FOLDER_DATA + "tasks.txt";
	static final String DEFAULT_LOG_DESTINATION = SEPARATOR_CHAR + "MyLogs.log";

	static final String EMPTY_STRING = "";
	static final String WHITESPACE = " ";

	public StorageConstants() {

	}

	protected void setFileDestination(String newFilePath) {
		filePath = newFilePath;
	}
}
