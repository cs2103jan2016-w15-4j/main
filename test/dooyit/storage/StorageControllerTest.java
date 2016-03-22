package dooyit.storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import dooyit.storage.StorageController;
import dooyit.storage.StorageConstants;
import dooyit.common.exception.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(StorageController.class)
public class StorageControllerTest extends StorageConstants{
//	
//	static final String FOLDER_TEST = CURRENT_DIRECTORY + SEPARATOR_CHAR + "test" + SEPARATOR_CHAR + "dooyit" + SEPARATOR_CHAR;
//	static final String FOLDER_TEST_STORAGE = FOLDER_TEST + "storage" + SEPARATOR_CHAR;
//	static final String TEST_SAVE = "testSave.txt";
//	
//	@Test(expected = InvalidFilePathException.class)
//	public void testBadSetFileDestination() throws IOException {
//		StorageController storage = new StorageController();
//		
//		//To change the location of config.txt to use the one inside test folder
//		//Set visibility from private to protected when testing StorageController
//		storage.configFilePath = FOLDER_TEST_STORAGE + "config.txt";
//		storage.preferences = storage.loadPreferences(storage.configFilePath);
//		
//		//filePath is expected to end with .txt extension, this should throw exception
//		String filePath = FOLDER_TEST_STORAGE;
//		storage.setFileDestination(filePath);
//	}
//	
//	@Test
//	public void testValidSetFileDestination() throws IOException {
//		StorageController storage = new StorageController();
//		
//		//To change the location of config.txt to use the one inside test folder
//		//Set visibility from private to protected when testing StorageController
//		storage.configFilePath = FOLDER_TEST_STORAGE + "config.txt";
//		storage.preferences = storage.loadPreferences(storage.configFilePath);
//		
//		//get the original path
//		String originalPath = storage.getFilePath();
//		
//		//filePath ending with .txt extension
//		String filePath = FOLDER_TEST_STORAGE + "testDestinaton.txt";
//		storage.setFileDestination(filePath);
//		
//		//Checking if file path is saved correctly on application
//		Assert.assertEquals(storage.getFilePath(), filePath);
//		
//		BufferedReader bReader = new BufferedReader(new FileReader(FOLDER_TEST_STORAGE + "config.txt"));
//		String taskPath = bReader.readLine();
//		bReader.close();
//		
//		//Checking if the file contains the same path as application
//		Assert.assertEquals(taskPath, filePath);
//		
//		//Revert the path to original
//		storage.setFileDestination(originalPath);
//		Assert.assertEquals(storage.getFilePath(), originalPath);
//	}
}
