public class StorageOperations {
	protected String filePath;

	static final String STORAGE_FORMAT = "%1$s, %2$s, %3$s, %4$s";

	static final String NAME_FILE_STORAGE = "tasks.txt";

	static final String NAME_FOLDER_STORAGE = "data\\";
	static final String NAME_FOLDER_ALIAS = "alias\\";

	protected Task setTaskType(String[] taskInfo) {		
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

	protected boolean hasNoDayAndTime(String[] taskInfo) {
		return (taskInfo[1].isEmpty() && taskInfo[2].isEmpty());
	}

	protected String setFormat(Task task){
		return String.format(STORAGE_FORMAT, task.taskName, task.day, task.time, task.taskId);
	}

	protected void setFileDestination(String newFilePath) {
		filePath = newFilePath;
	}
}
