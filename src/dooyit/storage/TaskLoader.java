package dooyit.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.MissingFileException;
import dooyit.logic.core.TaskManager;

/**
 * The TaskLoader class contains attributes and methods necessary for loading
 * tasks.
 * 
 * @author Dex
 *
 */
public class TaskLoader {

	private static final String DEADLINE = "dateTimeDeadline";
	private static final String EVENT_START = "dateTimeStart";
	private static final String EVENT_END = "dateTimeEnd";
	private static final String NAME = "taskName";
	private static final String IS_COMPLETED = "isCompleted";

	private String filePath;

	TaskLoader(String filePath) {
		this.filePath = filePath;
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
	public boolean load(TaskManager taskManager) throws IOException {
		File file = new File(filePath);
		File directory = file.getParentFile();

		if (directory.exists()) {
			if (file.exists()) {
				loadFromFile(file, taskManager);
			} else {
				createFile(file);
			}
		} else {
			createFile(directory, file);
		}

		return true;
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
	public void loadFromFile(File file, TaskManager taskManager) throws IOException {
		FileReader fReader;
		fReader = tryToOpen(file);

		try {
			readFromFile(fReader, taskManager);
		} catch (IOException e) {
			throw new IOException("Unable to read " + file.getName());
		}
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
	private FileReader tryToOpen(File file) throws FileNotFoundException {
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

	/**
	 * Reads input from FileReader and passes it to TaskManager.
	 * 
	 * @param fReader
	 *            The FileReader associated with the save file.
	 * @param taskManager
	 *            The Task Manager.
	 * @throws IOException
	 *             If unable to read from file.
	 */
	private void readFromFile(FileReader fReader, TaskManager taskManager) throws IOException {
		BufferedReader bReader = new BufferedReader(fReader);
		String taskInfo = bReader.readLine();
		while (taskInfo != null) {
			loadToMemory(taskManager, taskInfo);
			taskInfo = bReader.readLine();
		}
		bReader.close();
		fReader.close();
	}

	private void loadToMemory(TaskManager taskManager, String taskFormat) {
		loadTask(taskManager, taskFormat);
	}

	public boolean loadTask(TaskManager taskManager, String taskFormat) {
		JsonParser parser = new JsonParser();
		JsonObject taskInfo = parser.parse(taskFormat).getAsJsonObject();
		boolean isCompleted = false;

		String name = taskInfo.get(NAME).getAsString();

		if (taskInfo.has(IS_COMPLETED)) {
			isCompleted = taskInfo.get(IS_COMPLETED).getAsBoolean();
		}

		if (taskInfo.has(DEADLINE)) {
			DateTime deadline = resolveDateTime(taskInfo, DEADLINE);
			taskManager.AddTaskDeadline(name, deadline, isCompleted);
			return true;
		} else if (taskInfo.has(EVENT_START) && taskInfo.has(EVENT_END)) {
			DateTime eventStart = resolveDateTime(taskInfo, EVENT_START);
			DateTime eventEnd = resolveDateTime(taskInfo, EVENT_END);
			taskManager.AddTaskEvent(name, eventStart, eventEnd, isCompleted);
			return true;
		} else {
			taskManager.AddTaskFloat(name, isCompleted);
			return true;
		}
	}

	private DateTime resolveDateTime(JsonObject taskInfo, String type) {
		String dateTimeString = taskInfo.get(type).getAsString();
		String[] parts = dateTimeString.split(" ");
		DateTime dateTime = new DateTime(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]),
				Integer.valueOf(parts[2]), parts[3], parts[4]);

		return dateTime;
	}

	protected void setFileDestination(String path) {
		this.filePath = path;
	}
}
