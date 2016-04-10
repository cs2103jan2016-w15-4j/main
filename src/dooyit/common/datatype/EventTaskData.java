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
	public boolean equals(Object o) {
		boolean isEquals = false;

		if (o instanceof EventTaskData) {
			EventTaskData data = (EventTaskData) o;

			isEquals = this.taskName.equals(data.getName())
					&& this.isCompleted == data.isCompleted()
					&& this.dateTimeStart.equals(data.getStart())
					&& this.dateTimeEnd.equals(data.getEnd());

			if (this.hasCategory() && data.hasCategory()) {
				isEquals = isEquals && this.category.equals(data.getCategory());
			} else if (this.hasCategory() || data.hasCategory()) {
				isEquals = false;
			}
		}

		return isEquals;
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
