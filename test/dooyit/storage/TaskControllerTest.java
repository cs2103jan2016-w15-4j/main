//@@author A0124586Y
package dooyit.storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTaskData;
import dooyit.common.datatype.EventTaskData;
import dooyit.common.datatype.FloatingTaskData;
import dooyit.common.datatype.TaskData;

public class TaskControllerTest extends Constants {

	static final String FOLDER_TEST = CURRENT_DIRECTORY + SEPARATOR_CHAR + "test" + SEPARATOR_CHAR + "dooyit"
			+ SEPARATOR_CHAR;
	static final String FOLDER_TEST_STORAGE = FOLDER_TEST + "storage" + SEPARATOR_CHAR;
	static final String EXPECTED = "expectedTasks.txt";
	static final String SAVE = "testSaveTasks.txt";

	@Test
	public void testSave() throws IOException {
		ArrayList<TaskData> tasks = new ArrayList<TaskData>();
		TaskController taskControl = new TaskController(FOLDER_TEST_STORAGE + SAVE);

		// 10 December 2016
		int[] date = { 10, 12, 2016 };

		// {"deadline":{"date":"10 12 2016","time":-1},"taskName":"buy
		// milk","isCompleted":false}
		DateTime deadline_no_time = new DateTime(date);
		DeadlineTaskData deadlineData1 = new DeadlineTaskData("buy milk", deadline_no_time, false);
		tasks.add(deadlineData1);

		// {"deadline":{"date":"10 12
		// 2016","time":800},"taskName":"homework","isCompleted":false}
		DateTime deadline = new DateTime(date, 800);
		DeadlineTaskData deadlineData2 = new DeadlineTaskData("homework", deadline, false);
		tasks.add(deadlineData2);

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

		// 10 December 2016
		int[] date = { 10, 12, 2016 };

		// {"deadline":{"date":"10 12 2016","time":-1},"taskName":"buy
		// milk","isCompleted":false}
		DateTime deadline_no_time = new DateTime(date);
		DeadlineTaskData deadlineData1 = new DeadlineTaskData("buy milk", deadline_no_time, false);
		DeadlineTaskData expectedDeadline1 = (DeadlineTaskData) loadedTasks.get(0);
		Assert.assertTrue(expectedDeadline1.equals(deadlineData1));

		// {"deadline":{"date":"10 12
		// 2016","time":800},"taskName":"homework","isCompleted":false}
		DateTime deadline = new DateTime(date, 800);
		DeadlineTaskData deadlineData2 = new DeadlineTaskData("homework", deadline, false);
		DeadlineTaskData expectedDeadline2 = (DeadlineTaskData) loadedTasks.get(1);
		Assert.assertTrue(expectedDeadline2.equals(deadlineData2));

		// {"taskName":"float","isCompleted":false}
		FloatingTaskData floatData = new FloatingTaskData("float", false);
		FloatingTaskData expectedFloat = (FloatingTaskData) loadedTasks.get(2);
		Assert.assertTrue(expectedFloat.equals(floatData));

		// {"start":{"date":"10 12 2016","time":1000},"end":{"date":"10 12
		// 2016","time":1200},"taskName":"brunch","isCompleted":false}
		DateTime event_start = new DateTime(date, 1000);
		DateTime event_end = new DateTime(date, 1200);
		EventTaskData eventData = new EventTaskData("brunch", event_start, event_end, false);
		EventTaskData expectedEvent = (EventTaskData) loadedTasks.get(3);
		Assert.assertTrue(expectedEvent.equals(eventData));
	}
}
