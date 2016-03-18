package dooyit.storage;

public class StorageConstants {
	protected String filePath;

	static final String DEFAULT_CATEGORIES_DESTINATION = "\\data\\categories.txt";
	static final String DEFAULT_TASKS_DESTINATION = "\\data\\tasks.txt";
	static final String DEFAULT_LOG_DESTINATION = "\\MyLogs.log";

	static final String EMPTY_STRING = "";
	static final String WHITESPACE = " ";

	public StorageConstants() {

	}

	protected void setFileDestination(String newFilePath) {
		filePath = newFilePath;
	}
}
