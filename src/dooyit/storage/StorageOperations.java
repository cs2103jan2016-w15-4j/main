package dooyit.storage;
public class StorageOperations {
	protected String filePath;

	static final String STORAGE_FLOAT_FORMAT = "%1$s";
	static final String STORAGE_DEADLINE_FORMAT = "%1$s, %2$s, %3$s, %4$s, %5$s, %6$s";
	static final String STORAGE_EVENT_FORMAT = "%1$s, %2$s, %3$s, %4$s, %5$s, %6$s, %7$s, %8$s, %9$s, %10$s, 11$s";
	
	static final String NAME_FILE_STORAGE = "tasks.txt";

	static final String NAME_FOLDER_STORAGE = "data\\";
	static final String NAME_FOLDER_ALIAS = "alias\\";
	
	static final String EMPTY_STRING = "";
	static final String WHITESPACE = " ";
	
	static final int TYPE_FLOAT = 1;
	static final int TYPE_DEADLINE = 6;
	static final int TYPE_EVENT = 11;

	protected void setFileDestination(String newFilePath) {
		filePath = newFilePath;
	}
}
