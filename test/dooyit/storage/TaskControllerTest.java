package dooyit.storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTask;
import dooyit.common.datatype.EventTask;
import dooyit.common.datatype.FloatingTask;
import dooyit.common.datatype.Task;

public class TaskControllerTest extends StorageConstants{
	
	static final String FOLDER_TEST = CURRENT_DIRECTORY + SEPARATOR_CHAR + "test" 
									+ SEPARATOR_CHAR + "dooyit"
									+ SEPARATOR_CHAR;
	static final String FOLDER_TEST_STORAGE = FOLDER_TEST + "storage" + SEPARATOR_CHAR;
	static final String EXPECTED = "expectedTasks.txt";
	static final String SAVE = "testSaveTasks.txt";
	
	@Test
	public void testSave() throws IOException {
		ArrayList<Task> tasks = new ArrayList<Task> ();
		TaskController taskControl = new TaskController(FOLDER_TEST_STORAGE + SAVE);
		
		//10 December 2016 is a Saturday
		int[] date = {10, 12, 2016};
		
		//{"taskName":"abc","dateTimeDeadline":"10 12 2016 sat -1","isCompleted":false}
		DateTime deadline_no_time = new DateTime(date, "sat");
		tasks.add((Task)new DeadlineTask("abc", deadline_no_time));
		
		//{"taskName":"homework","dateTimeDeadline":"10 12 2016 sat 0800","isCompleted":false}
		DateTime deadline = new DateTime(date, "sat", 800);
		tasks.add((Task)new DeadlineTask("homework", deadline));
		
		//{"taskName":"float","isCompleted":true}
		Task floating = (Task)new FloatingTask("float");
		floating.mark();
		tasks.add(floating);
		
		//{"taskName":"brunch","dateTimeStart":"10 12 2016 sat 1000","dateTimeEnd":"10 12 2016 sat 1200","isCompleted":false}
		DateTime event_start = new DateTime(date, "sat", 1000);
		DateTime event_end = new DateTime(date, "sat", 1200);
		tasks.add((Task)new EventTask("brunch", event_start, event_end));
		taskControl.save(tasks);
		
		
		//Compare expected and test case result
		String expected = "", saved = "";
		BufferedReader bReader = new BufferedReader(new FileReader(FOLDER_TEST_STORAGE + EXPECTED));
		String taskInfo = bReader.readLine();
		while (taskInfo != null) {
			expected += taskInfo;
			taskInfo = bReader.readLine();
		}
		bReader.close();
		
		bReader = new BufferedReader(new FileReader(FOLDER_TEST_STORAGE + SAVE));
		taskInfo = bReader.readLine();
		while (taskInfo != null) {
			saved += taskInfo;
			taskInfo = bReader.readLine();
		}
		bReader.close();
		
		Assert.assertEquals(expected, saved);
	}
	
	@Test
	public void testLoad() throws IOException {
		TaskController taskControl = new TaskController(FOLDER_TEST_STORAGE + EXPECTED);
		//ArrayList<Task> loadedTasks = taskControl.load();
		
		//Test case of tasks
		ArrayList<Task> tasks = new ArrayList<Task> ();
		
		//10 December 2016 is a Saturday
		int[] date = {10, 12, 2016};
			
		//{"taskName":"abc","dateTimeDeadline":"10 12 2016 sat -1","isCompleted":false}
		DateTime deadline_no_time = new DateTime(date, "sat");
		tasks.add((Task)new DeadlineTask("abc", deadline_no_time));
		
		//{"taskName":"homework","dateTimeDeadline":"10 12 2016 sat 0800","isCompleted":false}
		DateTime deadline = new DateTime(date, "sat", 800);
		tasks.add((Task)new DeadlineTask("homework", deadline));
		
		//{"taskName":"float","isCompleted":true}
		Task floating = (Task)new FloatingTask("float");
		floating.mark();
		tasks.add(floating);
		
		//{"taskName":"brunch","dateTimeStart":"10 12 2016 sat 1000","dateTimeEnd":"10 12 2016 sat 1200","isCompleted":false}
		DateTime event_start = new DateTime(date, "sat", 1000);
		DateTime event_end = new DateTime(date, "sat", 1200);
		tasks.add((Task)new EventTask("brunch", event_start, event_end));

		//Assert.assertEquals(tasks.toString(), loadedTasks.toString());
	}
}
