package dooyit.storage;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTask;
import dooyit.common.datatype.EventTask;
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
			DeadlineTask datelineTask = (DeadlineTask) task;
			this.taskName = task.getName();
			this.dateTimeDeadline = toReadableFormat(datelineTask.getDateTimeDeadline());
			break;

		case EVENT:
			EventTask eventTask = (EventTask) task;
			this.taskName = task.getName();
			this.dateTimeStart = toReadableFormat(eventTask.getDateTimeStart());
			this.dateTimeEnd = toReadableFormat(eventTask.getDateTimeEnd());
			break;

		default:
			this.taskName = task.getName();
			break;
		}
		this.isCompleted = task.isCompleted();
	}
	
	private String toReadableFormat(DateTime dt) {
		String format = "";
		format += String.valueOf(dt.getDD()) + " ";
		format += String.valueOf(dt.getMM()) + " ";
		format += String.valueOf(dt.getYY()) + " ";
		format += dt.getDayStr() + " ";
		format += dt.getTime24hStr();
		
		return format;
	}
}
