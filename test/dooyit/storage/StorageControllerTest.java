package dooyit.storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.AssertionError;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dooyit.storage.StorageController;
import dooyit.storage.StorageConstants;
import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTask;
import dooyit.common.datatype.EventTask;
import dooyit.common.datatype.FloatingTask;
import dooyit.common.datatype.Task;
import dooyit.common.exception.InvalidFilePathException;

public class StorageControllerTest extends StorageConstants{
	
	static final String FOLDER_TEST = CURRENT_DIRECTORY + SEPARATOR_CHAR 
									+ "test" + SEPARATOR_CHAR + "dooyit" 
									+ SEPARATOR_CHAR;
	static final String FOLDER_TEST_STORAGE = FOLDER_TEST + "storage" + SEPARATOR_CHAR;
	
	static final String TEST_VALID_EXTENSION = "testSave.txt";
	static final String TEST_INVALID_EXTENSION = "abc.css";
	
	static final String NAME_FILE_CONFIG = SEPARATOR_CHAR + "config.txt";
	
	StorageController storage;
	
	@Before
	public void setup() throws IOException {
	storage = new StorageController();
	}
	
	
	//Case for negative partition - folder
	@Test(expected = AssertionError.class)
	public void testFolderSetFileDestination() throws IOException {
		//StorageController storage = new StorageController();
		
		//filePath is expected to end with .txt extension, this should throw exception
		String filePath = FOLDER_TEST_STORAGE;
		storage.setFileDestination(filePath);
	}
	
	//Case for negative partition - file extension
	@Test(expected = AssertionError.class)
	public void testBadExtensionSetFileDestination() throws IOException {
		//StorageController storage = new StorageController();
		
		//filePath is expected to end with .txt extension, this should throw exception
		String filePath = FOLDER_TEST_STORAGE + TEST_INVALID_EXTENSION;
		storage.setFileDestination(filePath);
	}
	
	@Test
	public void testValidSetFileDestination() throws IOException {
		//StorageController storage = new StorageController();
		
		//get the original path
		String originalPath = storage.getFilePath();
		
		//filePath ending with .txt extension
		String filePath = FOLDER_TEST_STORAGE + TEST_VALID_EXTENSION;
		storage.setFileDestination(filePath);
		
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
	public void testBadTaskArraySaving() throws IOException {
		ArrayList<TaskData> tasks = null;
		storage.saveTasks(tasks);
	}
	
	@Test
	public void testTaskControllerSave() throws IOException {
		ArrayList<Task> tasks = new ArrayList<Task> ();
		//TaskController taskControl = new TaskController(FOLDER_TEST_STORAGE + SAVE);
		
		//10 December 2016 is a Saturday
		int[] date = {10, 12, 2016};
		
		//{"taskName":"abc","dateTimeDeadline":"10 12 2016 sat -1","isCompleted":false}
		DateTime deadline_no_time = new DateTime(date);
		tasks.add((Task)new DeadlineTask("abc", deadline_no_time));
		
		//{"taskName":"homework","dateTimeDeadline":"10 12 2016 sat 0800","isCompleted":false}
		DateTime deadline = new DateTime(date, 800);
		tasks.add((Task)new DeadlineTask("homework", deadline));
		
		//{"taskName":"float","isCompleted":true}
		Task floating = (Task)new FloatingTask("float");
		floating.mark();
		tasks.add(floating);
		
		//{"taskName":"brunch","dateTimeStart":"10 12 2016 sat 1000","dateTimeEnd":"10 12 2016 sat 1200","isCompleted":false}
		DateTime event_start = new DateTime(date, 1000);
		DateTime event_end = new DateTime(date, 1200);
		tasks.add((Task)new EventTask("brunch", event_start, event_end));
	//	Assert.assertTrue(storage.saveTasks(tasks));
	}
	
	@Test
	public void testTaskControllerLoad() throws IOException {
		StorageController storage = new StorageController();
		
		//Test case of tasks
		ArrayList<Task> tasks = new ArrayList<Task> ();
		
		//10 December 2016 is a Saturday
		int[] date = {10, 12, 2016};
			
		//{"taskName":"abc","dateTimeDeadline":"10 12 2016 sat -1","isCompleted":false}
		DateTime deadline_no_time = new DateTime(date);
		tasks.add((Task)new DeadlineTask("abc", deadline_no_time));
		
		//{"taskName":"homework","dateTimeDeadline":"10 12 2016 sat 0800","isCompleted":false}
		DateTime deadline = new DateTime(date, 800);
		tasks.add((Task)new DeadlineTask("homework", deadline));
		
		//{"taskName":"float","isCompleted":true}
		Task floating = (Task)new FloatingTask("float");
		floating.mark();
		tasks.add(floating);
		
		//{"taskName":"brunch","dateTimeStart":"10 12 2016 sat 1000","dateTimeEnd":"10 12 2016 sat 1200","isCompleted":false}
		DateTime event_start = new DateTime(date, 1000);
		DateTime event_end = new DateTime(date, 1200);
		tasks.add((Task)new EventTask("brunch", event_start, event_end));
		//ArrayList<Task> existingTasks = storage.loadTasks();
		//Assert.assertEquals(existingTasks.toString(), tasks.toString());
	}
}
