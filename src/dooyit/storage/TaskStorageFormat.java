package dooyit.storage;

import dooyit.common.datatype.Task;

public class TaskStorageFormat {
	private String taskName;
	private String dateTimeDeadline;
	private String dateTimeStart;
	private String dateTimeEnd;
	private boolean isCompleted;

	public TaskStorageFormat(Task task) {
		switch (task.getTaskType()) {
		case DEADLINE:
			this.taskName = task.getName();
			this.dateTimeDeadline = task.getDeadlineTime().convertToSavableString();
			break;

		case EVENT:
			this.taskName = task.getName();
			this.dateTimeStart = task.getDateTimeStart().convertToSavableString();
			this.dateTimeEnd = task.getDateTimeEnd().convertToSavableString();
			break;

		default:
			this.taskName = task.getName();
			break;
		}
		this.isCompleted = task.isCompleted();
	}
}
