package dooyit.common.datatype;

import dooyit.common.datatype.Task.TaskType;

public class EventTask extends Task {

	private DateTime dateTimeStart;
	private DateTime dateTimeEnd;

	public EventTask(String taskName, DateTime start, DateTime end) {
		assert (start != null && end != null);

		taskType = TaskType.EVENT;
		this.taskName = taskName;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
	}

	public DateTime getDateTimeStart() {
		return dateTimeStart;
	}

	public DateTime getDateTimeEnd() {
		return dateTimeEnd;
	}

	@Override
	public String getDateString() {
		return dateTimeStart.getTime24hStr() + " to " + dateTimeEnd.getTime24hStr();
	}

	@Override
	public String toString() {
		return taskName + ": event: " + dateTimeStart.toString() + "," + dateTimeEnd.toString();
	}
}
