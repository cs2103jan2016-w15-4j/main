package dooyit.storage;

import dooyit.common.datatype.EventTask;

public class EventTaskData extends TaskData{
	private String start;
	private String end;
	
	public EventTaskData(EventTask task) {
		this.taskName = task.getName();
		this.isCompleted = task.isCompleted();
		if (hasCategory(task)) {
			this.category = getName(task.getCategory());
		}
		this.start = toReadableFormat(task.getDateTimeStart());
		this.end = toReadableFormat(task.getDateTimeEnd());
	}
}
