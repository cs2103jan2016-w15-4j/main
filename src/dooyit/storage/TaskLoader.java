//@@author A0124586Y
package dooyit.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import dooyit.common.datatype.TaskData;

/**
 * The TaskLoader class contains methods and attributes necessary for loading
 * tasks.
 * 
 * @author Dex
 */
public class TaskLoader extends Loader<TaskData> {
	private static final String ERROR_MESSAGE_LOAD = "Unable to load";
	private static final String ERROR_MESSAGE_FILE_CREATION = "Failed to create %1$s";

	private String filePath;
	private Gson gson;

	TaskLoader(String filePath) {
		super();
		this.filePath = filePath;
		this.gson = gsonWithTaskDataDeserializer();
	}

	/**
	 * Loads TaskData from the saved file after checking if the file exists.
	 * 
	 * @return An ArrayList of TaskData.
	 * @throws IOException
	 *             If unable to generate missing save file or unable to read
	 *             from the file.
	 */
	protected ArrayList<TaskData> load() throws IOException {
		File file = new File(filePath);
		File directory = file.getParentFile();
		ArrayList<TaskData> taskList = new ArrayList<TaskData>();

		if (directory.exists()) {
			if (file.exists()) {
				taskList = loadFromFile(file);
			} else {
				// create the file before finishing load
				createFile(file);
			}
		} else {
			// create parent directories and file before finishing load
			createFile(directory, file);
		}

		return taskList;
	}

	/**
	 * Replaces the current filePath with path
	 * 
	 * @param path
	 *            String representation of the save file path
	 */
	protected void setFileDestination(String path) {
		this.filePath = path;
	}

	/**
	 * Creates the save file
	 * 
	 * @param file
	 *            File instance of save file
	 * @throws IOException
	 *             If unable to create file
	 */
	private void createFile(File file) throws IOException {
		try {
			file.createNewFile();
		} catch (IOException e) {
			String errorMessage = String.format(ERROR_MESSAGE_FILE_CREATION, file.getName());
			throw new IOException(errorMessage);
		}
	}

	/**
	 * Creates the parent directories before creating the file
	 * 
	 * @param parent
	 *            File instance of the parent directories
	 * @param file
	 *            File instance of the save file
	 * @throws IOException
	 *             If unable to create file
	 */
	private void createFile(File parent, File file) throws IOException {
		parent.mkdirs();
		createFile(file);
	}

	/**
	 * Loads TaskData from an existing file
	 * 
	 * @param file
	 *            File instance of the existing save file
	 * @return ArrayList of TaskData from the save file
	 * @throws IOException
	 *             If unable to read from the save file
	 */
	private ArrayList<TaskData> loadFromFile(File file) throws IOException {
		FileReader fReader = open(file);
		BufferedReader bReader = new BufferedReader(fReader);
		ArrayList<TaskData> taskList = new ArrayList<TaskData>();
		String taskInfo;
		JsonObject jsonTask;
		
		try {
			taskInfo = bReader.readLine();
		} catch (IOException e) {
			throw new IOException(ERROR_MESSAGE_LOAD);
		}

		while (taskInfo != null) {
			try {
				jsonTask = getAsJson(taskInfo);
			} catch (JsonSyntaxException e) {
				//Null the object if the JSON string is corrupted
				jsonTask = null;
			}	
			if (jsonTask != null) {
				TaskData existingTask = gson.fromJson(jsonTask, TaskData.class);
				taskList.add(existingTask);
			}
			taskInfo = bReader.readLine();
		}
		bReader.close();

		return taskList;
	}

	/**
	 * Registers TaskDataDeserializer when creating Gson object
	 * 
	 * @return Gson object with registered TaskDataDeserializer
	 */
	private Gson gsonWithTaskDataDeserializer() {
		return new GsonBuilder().registerTypeAdapter(TaskData.class, new TaskDataDeserializer()).create();
	}
}
