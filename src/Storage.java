import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Storage {

	static final String STORAGE_FORMAT = "%1$s, %2$s, %3$s, %4$s";

	public Storage(){

	}

	public String setFormat(Task task){
		return String.format(STORAGE_FORMAT, task.taskName, task.day, task.time, task.taskId);
	}

	public void saveTasks(ArrayList<Task> tasks) throws IOException{
		File file = new File("mytextfile.txt");
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));

		for(Task existingTask: tasks) {
			bWriter.append(setFormat(existingTask));
			bWriter.newLine();
		}
		bWriter.close();
	}

	public ArrayList<Task> loadTasks() throws IOException{
		File file = new File("mytextfile.txt");
		ArrayList<Task> taskList = new ArrayList<Task>();
		FileReader fReader = null;

		if(file.exists()) {
			fReader = tryToOpen(file);
		}
		
		BufferedReader bReader = new BufferedReader(fReader);
		String taskInfo = bReader.readLine();
		while(taskInfo != null) {
			loadToMemory(taskList, taskInfo);
			taskInfo = bReader.readLine();
		}
		bReader.close();

		return taskList;
	}

	private FileReader tryToOpen(File file) {
		FileReader fReader = null;

		try {
			fReader = new FileReader(file);
		} catch (FileNotFoundException fnfe) {
			System.out.println("Unable to access file");
			System.exit(2);
		}
		return fReader;
	}

	public boolean hasNoDayAndTime(String[] taskInfo) {
		return (taskInfo[1].isEmpty() && taskInfo[2].isEmpty());
	}

	public Task setTaskType(String[] taskInfo) {		
		Task existingTask = new Task();

		if (taskInfo[1].isEmpty()) { //if date is empty
			existingTask.initTaskWithTime(taskInfo[0], taskInfo[2]);
		} else if (taskInfo[2].isEmpty()) { //if time is empty
			existingTask.initTaskWithDay(taskInfo[0], taskInfo[1]);
		} else if (hasNoDayAndTime(taskInfo)) { //floating task
			existingTask.initTask(taskInfo[0]);
		} else {
			existingTask.initTaskDayAndTime(taskInfo[0], taskInfo[1], taskInfo[2]);
		}

		return existingTask;
	}

	private void loadToMemory(ArrayList<Task> taskList, String taskFormat) {
		String[] taskInfo = taskFormat.split(", ");
		Task existingTask = setTaskType(taskInfo);
		taskList.add(existingTask);
	}
}
