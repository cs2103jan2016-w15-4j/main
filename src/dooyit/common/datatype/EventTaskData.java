//@@author A0124586Y
package dooyit.common.datatype;

public class EventTaskData extends TaskData {
	private DateTime dateTimeStart;
	private DateTime dateTimeEnd;

	public EventTaskData(String name, DateTime start, DateTime end,
						boolean isCompleted) {
		this.taskName = name;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
		this.isCompleted = isCompleted;
	}
	
	public EventTaskData(String name, DateTime start, DateTime end,
						String category, boolean isCompleted) {
		this(name, start, end, isCompleted);
		this.category = category;
	}

	public DateTime getStart() {
		return this.dateTimeStart;
	}

	public DateTime getEnd() {
		return this.dateTimeEnd;
	}

	@Override
	public Task convertToTask() {
		Task task = new EventTask(taskName, dateTimeStart, dateTimeEnd);

		if (isCompleted) {
			task.mark();
		}

		return task;
	}
}
