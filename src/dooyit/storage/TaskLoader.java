package dooyit.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTaskData;
import dooyit.common.datatype.EventTaskData;
import dooyit.common.datatype.FloatingTaskData;
import dooyit.common.datatype.TaskData;
import dooyit.common.exception.MissingFileException;

/**
 * The TaskLoader class contains attributes and methods necessary for loading
 * tasks.
 * 
 * @author Dex
 *
 */
public class TaskLoader {

	private static final String EMPTY_STRING = "";
	
	private String filePath;
	private JsonParser parser;
	private Gson gson;
	private int count;

	TaskLoader(String filePath) {
		this.filePath = filePath;
		this.parser = new JsonParser();
		this.gson = gsonWithTaskDataDeserializer();
		this.count = 1;
	}

	/**
	 * Loads TaskData from the saved file after checking if the path exists
	 * 
	 * @return ArrayList of TaskData
	 * @throws IOException If unable to generate missing save file
	 */
	public ArrayList<TaskData> load() throws IOException {
		File file = new File(filePath);
		File directory = file.getParentFile();
		ArrayList<TaskData> taskList = new ArrayList<TaskData>();

		if (directory.exists()) {
			if (file.exists()) {
				taskList = loadFromFile(file);
			} else { //create the file before finishing load
				createFile(file);
			}
		} else { //create parent directories and file before finishing load
			createFile(directory, file);
		}

		return taskList;
	}
	
	/** 
	 * Replaces the current filePath with path
	 * 
	 * @param path String representation of the save file path
	 */
	protected void setFileDestination(String path) {
		this.filePath = path;
	}
	
	/**
	 * Creates the save file
	 * 
	 * @param file File instance of save file
	 * @throws IOException If unable to create file
	 */
	private void createFile(File file) throws IOException {
		try {
			file.createNewFile();
		} catch (IOException e) {
			throw new IOException("Failed to create " + file.getName());
		}
	}

	/**
	 * Creates the parent directories before creating the file
	 * 
	 * @param parent File instance of the parent directories
	 * @param file File instance of the save file
	 * @throws IOException If unable to create file
	 */
	private void createFile(File parent, File file) throws IOException {
		parent.mkdirs();
		createFile(file);
	}

	/**
	 * Loads TaskData from an existing file
	 * 
	 * @param file File instance of the existing save file
	 * @return ArrayList of TaskData from the save file
	 * @throws IOException If unable to read from the save file
	 */
	private ArrayList<TaskData> loadFromFile(File file) throws IOException {
		FileReader fReader = open(file);
		ArrayList<TaskData> taskList = new ArrayList<TaskData>();

		BufferedReader bReader = new BufferedReader(fReader);
		String taskInfo = bReader.readLine();
		
		JsonObject jsonTask;
		while (taskInfo != null) {
			try {
				jsonTask = getAsJson(taskInfo);
			} catch (JsonSyntaxException e) {
				jsonTask = null;
				System.out.println("Line " + count + " deleted for bad format");
			}

			if (jsonTask != null) {
				TaskData existingTask = gson.fromJson(jsonTask, TaskData.class);
				taskList.add(existingTask);
			}
			
			taskInfo = bReader.readLine();
		}

		return taskList;
	}

	/**
	 * Creates a new FileReader given the File to read from
	 * 
	 * @param file File to read from
	 * @return FileReader instance of the File to read from
	 * @throws FileNotFoundException If the file is not found
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

	/**
	 * Converts the Json String to a JsonObject
	 * 
	 * @param format
	 *            The String representation of the JsonObject
	 * @return JsonObject from String representation if it is not an empty
	 *         string. Otherwise return null
	 */
	private JsonObject getAsJson(String format) {
		JsonObject object = null;
	
		if (!format.equals(EMPTY_STRING)) {
			object = parser.parse(format).getAsJsonObject();
		}

		return object;
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
