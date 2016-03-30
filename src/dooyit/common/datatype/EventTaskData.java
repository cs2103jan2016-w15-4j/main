package dooyit.common.datatype;

public class EventTaskData extends TaskData {
	private DateTime dateTimeStart;
	private DateTime dateTimeEnd;

	public EventTaskData(String name, DateTime start, DateTime end,
						String category, boolean isCompleted) {
		this.taskName = name;
		this.isCompleted = isCompleted;
		this.category = category;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
	}

	public EventTaskData(String name, DateTime start, DateTime end,
						boolean isCompleted) {
		this.taskName = name;
		this.isCompleted = isCompleted;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
	}
	
	public DateTime getStart() {
		return this.dateTimeStart;
	}
	
	public DateTime getEnd() {
		return this.dateTimeEnd;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof EventTaskData) {
			EventTaskData data = (EventTaskData) o;
			if(this.hasCategory()) {
				return this.taskName.equals(data.getName())
						&& this.isCompleted == data.isCompleted()
						&& this.dateTimeStart.equals(data.getStart())
						&& this.dateTimeEnd.equals(data.getEnd())
						&& this.category.equals(data.getCategory());
			}
			return this.taskName.equals(data.getName())
					&& this.isCompleted == data.isCompleted()
					&& this.dateTimeStart.equals(data.getStart())
					&& this.dateTimeEnd.equals(data.getEnd());
		}
		return false;
	}

	@Override
	public Task convertToTask() {
		Task task = new EventTask(taskName, dateTimeStart, dateTimeEnd);
		
		if(isCompleted){
			task.mark();
		}
		
		return task;
	}
}
