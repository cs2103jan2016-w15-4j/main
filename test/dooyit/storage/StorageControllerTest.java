//@@author A0124586Y
package dooyit.storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dooyit.common.datatype.TaskData;
import dooyit.common.exception.IncorrectInputException;

public class StorageControllerTest extends Constants {
	
	static final String FOLDER_TEST = CURRENT_DIRECTORY + SEPARATOR_CHAR 
									+ "test" + SEPARATOR_CHAR + "dooyit" 
									+ SEPARATOR_CHAR;
	
	static final String FOLDER_TEST_STORAGE = FOLDER_TEST + "storage" + SEPARATOR_CHAR;
	
	static final String TEST_VALID_EXTENSION = "testSave.txt";
	static final String TEST_MISSING_FILE = "missing.txt";
	static final String TEST_INVALID_EXTENSION = "abc.css";
	
	static final String NAME_FILE_CONFIG = SEPARATOR_CHAR + "config.txt";
	
	StorageController storage;
	
	@Before
	public void setup() throws IOException {
	storage = new StorageController();
	}
	
	
	//Case for negative partition - folder
	@Test(expected = IncorrectInputException.class)
	public void SetFileDestination_InvalidAsFolder_AssertionError() throws IOException {
		
		//filePath is expected to end with .txt extension, this should throw exception
		String filePath = FOLDER_TEST_STORAGE;
		storage.setFileDestination(filePath);
	}
	
	//Case for negative partition - file extension
	@Test(expected = IncorrectInputException.class)
	public void testBadExtensionSetFileDestination() throws IOException {
		
		//filePath is expected to end with .txt extension
		String filePath = FOLDER_TEST_STORAGE + TEST_INVALID_EXTENSION;
		storage.setFileDestination(filePath);
	}
	
	@Test
	public void setFileDestination_ValidMissingFile_ExpectedPass() throws IOException {
		
		String originalPath = storage.getFilePath();
		
		//filePath ending with .txt extension
		String filePath = FOLDER_TEST_STORAGE + TEST_MISSING_FILE;
		
		//Expected false if file does not exist
		Assert.assertFalse(storage.setFileDestination(filePath));
		
		//Read file path from config
		BufferedReader bReader = new BufferedReader(new FileReader(CURRENT_DIRECTORY + NAME_FILE_CONFIG));
		String taskPath = bReader.readLine();
		bReader.close();
		
		//Checking if the file contains the same path as application
		Assert.assertEquals(taskPath, filePath);
		
		//Revert to original path
		Assert.assertTrue(storage.setFileDestination(originalPath));
		Assert.assertEquals(storage.getFilePath(), originalPath);
		
	}
	
	@Test
	public void setFileDestination_ValidExistingFile_ExpectedPass() throws IOException {
		
		String originalPath = storage.getFilePath();
		
		String filePath = FOLDER_TEST_STORAGE + TEST_VALID_EXTENSION;
		
		//Expected true since file exists
		Assert.assertTrue(storage.setFileDestination(filePath));

		//Checking if file path is saved correctly on application
		Assert.assertEquals(storage.getFilePath(), filePath);
		
		BufferedReader bReader = new BufferedReader(new FileReader(CURRENT_DIRECTORY + NAME_FILE_CONFIG));
		String taskPath = bReader.readLine();
		bReader.close();
		
		//Checking if the file contains the same path as application
		Assert.assertEquals(taskPath, filePath);
		
		//Revert the path to original
		storage.setFileDestination(originalPath);
		Assert.assertEquals(storage.getFilePath(), originalPath);
	}
	
	@Test (expected = AssertionError.class)
	public void save_NullArray_AssertionError() throws IOException {
		ArrayList<TaskData> tasks = null;
		storage.saveTasks(tasks);
	}
}
