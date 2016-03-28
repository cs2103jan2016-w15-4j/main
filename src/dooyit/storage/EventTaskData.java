package dooyit.storage;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.DateTime;

public class EventTaskData extends TaskData {
	private DateTime start;
	private DateTime end;

	public EventTaskData(String name, DateTime start, DateTime end,
						Category category, boolean isCompleted) {
		this.taskName = name;
		this.isCompleted = isCompleted;
		this.category = getName(category);
		this.start = start;
		this.end = end;
	}

	public EventTaskData(String name, DateTime start, DateTime end,
						boolean isCompleted) {
		this.taskName = name;
		this.isCompleted = isCompleted;
		this.start = start;
		this.end = end;
	}
	
	public DateTime getStart() {
		return this.start;
	}
	
	public DateTime getEnd() {
		return this.end;
	}
}
