//@@author A0124586Y
package dooyit.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTaskData;
import dooyit.common.datatype.EventTaskData;
import dooyit.common.datatype.FloatingTaskData;
import dooyit.common.datatype.TaskData;

/**
 * The TaskSaver class contains methods and attributes necessary for saving
 * tasks.
 * 
 * @author Dex
 *
 */
public class TaskSaver extends Saver<TaskData> {
	private static final int BACKUP_LIMIT = 5;

	// Error Messages
	private static final String ERROR_MESSAGE_TASK_SAVING = "Unable to save task data";

	private static final String EMPTY_STRING = "";

	private String filePath;
	private Gson gson;

	//Backup counter
	private static int count = 0;

	protected TaskSaver(String filePath) {
		this.filePath = filePath;
		this.gson = gsonWithDateTimeSerializer();
	}

	/**
	 * Creates the directories if necessary before saving the list of tasks.
	 * 
	 * @param tasks
	 *        An ArrayList of TaskData to be saved
	 *        
	 * @return Returns true if save is successful, otherwise returns false
	 * 
	 * @throws IOException
	 *         If the saved file cannot be written or failure to create save
	 *         file
	 */
	boolean save(ArrayList<TaskData> tasks) throws IOException {
		File file = new File(filePath);
		boolean isSaved = false;

		try {
			isSaved = saveToFile(file, tasks);
		} catch (IOException e) {
			throw new IOException(ERROR_MESSAGE_TASK_SAVING);
		}

		return isSaved;
	}

	/**
	 * Writes the list of TaskData into the File specified. A backup is also
	 * saved when count exceeds the limit.
	 * 
	 * @param file
	 *        The File specified by the filePath
	 *        
	 * @param tasks
	 *        The list of TaskData
	 *        
	 * @return Returns true if the save is successful, otherwise returns false
	 * 
	 * @throws IOException
	 *         If unable to write to save file.
	 */
	private boolean saveToFile(File file, ArrayList<TaskData> tasks) throws IOException {
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));

		for (TaskData existingTask : tasks) {
			bWriter.append(setFormat(existingTask));
			bWriter.newLine();
		}
		bWriter.close();
		count++;

		if (count > BACKUP_LIMIT) {
			saveBackup(tasks);
			count = 0;
		}

		return true;
	}

	/**
	 * Saves a backup of the list of tasks.
	 * 
	 * @param tasks
	 *        The list of tasks to be saved
	 * @throws IOException
	 *         If unable to write to the backup file
	 */
	private void saveBackup(ArrayList<TaskData> tasks) throws IOException {
		File backupFile = new File(Constants.DEFAULT_BACKUP_DESTINATION);
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(backupFile));

		for (TaskData existingTask : tasks) {
			bWriter.append(setFormat(existingTask));
			bWriter.newLine();
		}
		bWriter.close();
	}

	/**
	 * Updates the file path for saving.
	 * 
	 * @param path
	 *        The new file path for saving
	 */
	protected void setFileDestination(String path) {
		this.filePath = path;
	}

	/**
	 * Converts a TaskData object to its specific TaskData type before
	 * further conversion to a JSON string.
	 * 
	 * @param task
	 *        The task to be converted
	 *        
	 * @return The JSON string representation of the TaskData
	 */
	String setFormat(TaskData task) {
		String json = EMPTY_STRING;

		if (task instanceof DeadlineTaskData) {
			DeadlineTaskData deadline = (DeadlineTaskData) task;
			json = gson.toJson(deadline);
		} else if (task instanceof EventTaskData) {
			EventTaskData event = (EventTaskData) task;
			json = gson.toJson(event);
		} else {
			FloatingTaskData floatData = (FloatingTaskData) task;
			json = gson.toJson(floatData);
		}

		return json;
	}

	/**
	 * 
	 * @return Gson object with DateTimeSerializer
	 */
	private Gson gsonWithDateTimeSerializer() {
		return new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeSerializer()).create();
	}
}
