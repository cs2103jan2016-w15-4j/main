package dooyit.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTask;
import dooyit.common.datatype.EventTask;
import dooyit.common.datatype.FloatingTask;
import dooyit.common.datatype.Task;
import dooyit.common.exception.MissingFileException;

/**
 * The TaskLoader class contains attributes and methods necessary for loading
 * tasks.
 * 
 * @author Dex
 *
 */
public class TaskLoader {

	private static final String DEADLINE = "deadline";
	private static final String EVENT_START = "start";
	private static final String EVENT_END = "end";
	private static final String NAME = "taskName";
	private static final String CATEGORY = "category";
	private static final String IS_COMPLETED = "isCompleted";
	private static final String DATE = "date";
	private static final String TIME = "time";
	private static final String PLACEHOLDER = "task %1$s";
	private static final int DAY = 0;
	private static final int MONTH = 1;
	private static final int YEAR = 2;
	private static final int DAY_OF_WEEK = 3;
	private int count;

	private String filePath;

	TaskLoader(String filePath) {
		this.filePath = filePath;
		this.count = 0;
	}

	/**
	 * Loads tasks to the Task Manager.
	 * 
	 * @param taskManager
	 *            The task manager
	 * @return Returns true if tasks are successfully loaded
	 * @throws IOException
	 *             If loading fails
	 */
	public ArrayList<Task> load() throws IOException {
		File file = new File(filePath);
		File directory = file.getParentFile();
		ArrayList<Task> taskList = new ArrayList<Task>();

		if (directory.exists()) {
			if (file.exists()) {
				taskList = loadFromFile(file);
			} else {
				createFile(file);
			}
		} else {
			createFile(directory, file);
		}

		return taskList;
	}

	private void createFile(File file) throws IOException {
		try {
			file.createNewFile();
		} catch (IOException e) {
			throw new IOException("Failed to create " + file.getName());
		}
	}

	/**
	 * Creates the file specified and its parent directories if they do not
	 * exist.
	 * 
	 * @param parent
	 *            The parent abstract pathname
	 * @param file
	 *            The save file
	 * @throws IOException
	 *             If unable to create file or parent directories
	 */
	private void createFile(File parent, File file) throws IOException {
		// creates the parent directories
		parent.mkdirs();
		createFile(file);
	}

	/**
	 * Attempts to load tasks from File to TaskManager.
	 * 
	 * @param file
	 *            The save file
	 * @param taskManager
	 *            The task manager
	 * @throws IOException
	 *             If unable to read from the save file
	 */
	private ArrayList<Task> loadFromFile(File file) throws IOException {
		FileReader fReader;
		ArrayList<Task> taskList = new ArrayList<Task>();

		fReader = open(file);
		BufferedReader bReader = new BufferedReader(fReader);
		String taskInfo = bReader.readLine();

		while (taskInfo != null) {
			JsonObject jsonTask = getAsJson(taskInfo);
			Task existingTask = resolveTask(jsonTask);
			taskList.add(existingTask);
			taskInfo = bReader.readLine();
		}

		return taskList;
	}

	/**
	 * Attempts to create the FileReader, given the File to read from.
	 * 
	 * @param file
	 *            The save file
	 * @return The FileReader associated with the File specified.
	 * @throws FileNotFoundException
	 *             If the save file is missing
	 */
	private FileReader open(File file) throws FileNotFoundException {
		FileReader fReader = null;
		if (file.exists()) {
			try {
				fReader = new FileReader(file);
			} catch (MissingFileException mfe) {
				throw new MissingFileException(file.getName());
			}
		}
		return fReader;
	}

	public Task resolveTask(JsonObject jsonTask) {

		Task task;
		String name = getName(jsonTask);
	
		boolean isCompleted = jsonTask.get(IS_COMPLETED).getAsBoolean();

		if (jsonTask.has(DEADLINE)) {
			DateTime deadline = resolveDateTime(jsonTask, DEADLINE);
			task = (Task) new DeadlineTask(name, deadline);
		} else if (jsonTask.has(EVENT_START) && jsonTask.has(EVENT_END)) {
			DateTime eventStart = resolveDateTime(jsonTask, EVENT_START);
			DateTime eventEnd = resolveDateTime(jsonTask, EVENT_END);
			task = (Task) new EventTask(name, eventStart, eventEnd);
		} else {
			task = (Task) new FloatingTask(name);
		}
		
		if(isCompleted) {
			task.mark();
		}
		
		if(jsonTask.has(CATEGORY)) {
			String categoryName = jsonTask.get(CATEGORY).getAsString();
			//task.setUncheckCategory(categoryName);
		}

		return task;
	}
	
	private String getName(JsonObject taskData) {
		String name = "";
		
		if(taskData.has(NAME)) {
			name = taskData.get(NAME).getAsString();
		} else {
			name = String.format(PLACEHOLDER, count++);
		}
		
		return name;
	}

	private DateTime resolveDateTime(JsonObject taskInfo, String type) {
		JsonObject dateTimeJson = taskInfo.get(type).getAsJsonObject();
		String dateString = dateTimeJson.get(DATE).getAsString();
		int timeInt = dateTimeJson.get(TIME).getAsInt();
		
		String[] parts = dateString.split(" ");
		int[] date = new int[] {Integer.valueOf(parts[DAY]),
				Integer.valueOf(parts[MONTH]),
				Integer.valueOf(parts[YEAR])};
		
		String dayStr = parts[DAY_OF_WEEK];
		
		
		DateTime dateTime = new DateTime(date, timeInt);

		return dateTime;
	}

	protected void setFileDestination(String path) {
		this.filePath = path;
	}
	
	private JsonObject getAsJson(String format) {
		JsonParser parser = new JsonParser();
		JsonObject object = parser.parse(format).getAsJsonObject();
		
		return object;
	}
}
