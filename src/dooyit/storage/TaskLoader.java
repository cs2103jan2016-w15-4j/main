package dooyit.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import dooyit.common.datatype.DateTime;
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
	public ArrayList<TaskData> load() throws IOException {
		File file = new File(filePath);
		File directory = file.getParentFile();
		ArrayList<TaskData> taskList = new ArrayList<TaskData>();

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
	private ArrayList<TaskData> loadFromFile(File file) throws IOException {
		FileReader fReader;
		ArrayList<TaskData> taskList = new ArrayList<TaskData>();
		Gson gson = new Gson();

		fReader = open(file);
		BufferedReader bReader = new BufferedReader(fReader);
		String taskInfo = bReader.readLine();
		
		JsonObject jsonTask;
		while (taskInfo != null) {
			try {
				jsonTask = getAsJson(taskInfo);
			} catch (JsonSyntaxException e) {
				jsonTask = null;
			}
			
			if(jsonTask != null) {
				TaskData existingTask = resolveTask(jsonTask);
				taskList.add(existingTask);
			}
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

	public TaskData resolveTask(JsonObject jsonTask) {

		TaskData task;

		String name = getName(jsonTask);
		boolean isCompleted = isCompleted(jsonTask);
		String categoryName = getCategoryName(jsonTask);

		if (jsonTask.has(DEADLINE)) {
			DateTime deadline = resolveDateTime(jsonTask, DEADLINE);
			task = (TaskData) new DeadlineTaskData(name, deadline, categoryName, isCompleted);
		} else if (jsonTask.has(EVENT_START) && jsonTask.has(EVENT_END)) {
			DateTime eventStart = resolveDateTime(jsonTask, EVENT_START);
			DateTime eventEnd = resolveDateTime(jsonTask, EVENT_END);
			task = (TaskData) new EventTaskData(name, eventStart, eventEnd, categoryName, isCompleted);
		} else {
			task = (TaskData) new FloatTaskData(name, categoryName, isCompleted);
		}

		return task;
	}

	/**
	 * Checks if the task is completed
	 * 
	 * @param jsonTask JsonObject of TaskData
	 * @return The boolean value of isCompleted, default false
	 */
	private boolean isCompleted(JsonObject jsonTask) {
		boolean isCompleted = false;

		if (jsonTask.has(IS_COMPLETED)) {
			isCompleted = jsonTask.get(IS_COMPLETED).getAsBoolean();
		}
		return isCompleted;
	}
	
	/**
	 * Get the name of the task.
	 * 
	 * @param taskData JsonObject of TaskData
	 * @return The name of the TaskData, otherwise returns a placeholder name
	 */
	private String getName(JsonObject jsonTask) {
		String name = "";

		if (jsonTask.has(NAME)) {
			name = jsonTask.get(NAME).getAsString();
		} else {
			name = String.format(PLACEHOLDER, count++);
		}

		return name;
	}

	private String getCategoryName(JsonObject taskData) {
		String catName = null;
		if (taskData.has(CATEGORY)) {
			catName = taskData.get(CATEGORY).getAsString();
		}

		return catName;
	}

	private DateTime resolveDateTime(JsonObject taskInfo, String type) {
		JsonObject dateTimeJson = taskInfo.get(type).getAsJsonObject();
		String dateString = dateTimeJson.get(DATE).getAsString();
		int timeInt = dateTimeJson.get(TIME).getAsInt();

		String[] parts = dateString.split(" ");
<<<<<<< HEAD
		int[] date = new int[] {Integer.valueOf(parts[DAY]),
				Integer.valueOf(parts[MONTH]),
				Integer.valueOf(parts[YEAR])};
		
		String dayStr = parts[DAY_OF_WEEK];
		
		
=======
		int[] date = new int[] { Integer.valueOf(parts[DAY]), Integer.valueOf(parts[MONTH]),
				Integer.valueOf(parts[YEAR]) };

>>>>>>> e49544bec27d3da09d4787a64acf265812a60e65
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
