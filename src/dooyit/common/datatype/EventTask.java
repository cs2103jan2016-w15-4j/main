package dooyit.common.datatype;

public class EventTask extends Task {

	private DateTime dateTimeStart;
	private DateTime dateTimeEnd;

	public EventTask(String taskName, DateTime start, DateTime end) {
		assert (taskName != null && start != null && end != null);

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
	public boolean isToday(DateTime dateTime){
		return dateTimeStart.isTheSameDateAs(dateTime);
	}
	
	@Override
	public boolean isOverDue(DateTime dateTime){
		return !isCompleted && dateTimeEnd.compareTo(dateTime) == -1;
	}
	
	@Override
	public String getDateString() {
		return dateTimeStart.getTime24hStr() + " - " + dateTimeEnd.getTime24hStr();
	}

	@Override
	public String toString() {
		return taskName + ": event: " + dateTimeStart.toString() + "," + dateTimeEnd.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof EventTask) {
			EventTask eventTask = (EventTask) o;
			return this.getName() == eventTask.getName() 
					&& this.getDateTimeStart().equals(eventTask.getDateTimeStart())
					&& this.getDateTimeEnd().equals(eventTask.getDateTimeEnd());
		}
		return false;
	}
}
