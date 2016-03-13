package dooyit.storage;
public class StorageConstants {
	protected String filePath;

	static final String NAME_FILE_STORAGE = "tasks.txt";
	static final String NAME_FILE_CATEGORY = "categories.txt";

	static final String DEFAULT_FOLDER_STORAGE = "data\\";
	
	static final String EMPTY_STRING = "";
	static final String WHITESPACE = " ";

	protected void setFileDestination(String newFilePath) {
		filePath = newFilePath;
	}
}
