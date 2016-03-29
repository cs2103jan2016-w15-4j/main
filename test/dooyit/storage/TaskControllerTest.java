package dooyit.storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTask;
import dooyit.common.datatype.DeadlineTaskData;
import dooyit.common.datatype.EventTask;
import dooyit.common.datatype.EventTaskData;
import dooyit.common.datatype.FloatingTaskData;
import dooyit.common.datatype.FloatingTask;
import dooyit.common.datatype.Task;
import dooyit.common.datatype.TaskData;

public class TaskControllerTest extends StorageConstants {

	static final String FOLDER_TEST = CURRENT_DIRECTORY + SEPARATOR_CHAR + "test" + SEPARATOR_CHAR + "dooyit"
			+ SEPARATOR_CHAR;
	static final String FOLDER_TEST_STORAGE = FOLDER_TEST + "storage" + SEPARATOR_CHAR;
	static final String EXPECTED = "expectedTasks.txt";
	static final String SAVE = "testSaveTasks.txt";

	@Test
	public void testSave() throws IOException {
		ArrayList<TaskData> tasks = new ArrayList<TaskData>();
		TaskController taskControl = new TaskController(FOLDER_TEST_STORAGE + SAVE);

		// 10 December 2016 is a Saturday
		int[] date = { 10, 12, 2016 };

		// {"deadline":{"date":"10 12 2016","time":-1},"taskName":"buy
		// milk","isCompleted":false}
		DateTime deadline_no_time = new DateTime(date);
		DeadlineTaskData dlData1 = new DeadlineTaskData("buy milk", deadline_no_time, false);
		tasks.add(dlData1);

		// {"deadline":{"date":"10 12
		// 2016","time":800},"taskName":"homework","isCompleted":false}
		DateTime deadline = new DateTime(date, 800);
		DeadlineTaskData dlData2 = new DeadlineTaskData("homework", deadline, false);
		tasks.add(dlData2);

		// {"taskName":"float","isCompleted":false}
		FloatingTaskData floatData = new FloatingTaskData("float", false);
		tasks.add(floatData);

		// {"start":{"date":"10 12 2016","time":1000},"end":{"date":"10 12
		// 2016","time":1200},"taskName":"brunch","isCompleted":false}
		DateTime event_start = new DateTime(date, 1000);
		DateTime event_end = new DateTime(date, 1200);
		EventTaskData eventData = new EventTaskData("brunch", event_start, event_end, false);
		tasks.add(eventData);

		taskControl.save(tasks);

		// Compare expected and test case result
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
		ArrayList<TaskData> loadedTasks = taskControl.load();

		// Test case of tasks
		// ArrayList<TaskData> tasks = new ArrayList<Task> ();

		// 10 December 2016 is a Saturday
		int[] date = { 10, 12, 2016 };

		// {"deadline":{"date":"10 12 2016","time":-1},"taskName":"buy
		// milk","isCompleted":false}
		DateTime deadline_no_time = new DateTime(date);
		DeadlineTaskData dlData1 = new DeadlineTaskData("buy milk", deadline_no_time, false);
		Assert.assertTrue(loadedTasks.get(0).equals(dlData1));

		// {"deadline":{"date":"10 12
		// 2016","time":800},"taskName":"homework","isCompleted":false}
		DateTime deadline = new DateTime(date, 800);
		DeadlineTaskData dlData2 = new DeadlineTaskData("homework", deadline, false);
		Assert.assertTrue(loadedTasks.get(1).equals(dlData2));

		// {"taskName":"float","isCompleted":false}
		FloatingTaskData floatData = new FloatingTaskData("float", false);
		Assert.assertTrue(loadedTasks.get(2).equals(floatData));

		// {"start":{"date":"10 12 2016","time":1000},"end":{"date":"10 12
		// 2016","time":1200},"taskName":"brunch","isCompleted":false}
		DateTime event_start = new DateTime(date, 1000);
		DateTime event_end = new DateTime(date, 1200);
		EventTaskData eventData = new EventTaskData("brunch", event_start, event_end, false);
		Assert.assertTrue(loadedTasks.get(3).equals(eventData));
	}
}
