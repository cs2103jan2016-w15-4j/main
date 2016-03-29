package dooyit.common.datatype;

public class EventTaskData extends TaskData {
	private DateTime start;
	private DateTime end;

	public EventTaskData(String name, DateTime start, DateTime end,
						String category, boolean isCompleted) {
		this.taskName = name;
		this.isCompleted = isCompleted;
		this.category = category;
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
	
	public boolean equals(Object o) {
		if(o instanceof EventTaskData) {
			EventTaskData data = (EventTaskData) o;
			if(this.hasCategory()) {
				return this.taskName.equals(data.getName())
						&& this.isCompleted == data.isCompleted()
						&& this.start.equals(data.getStart())
						&& this.end.equals(data.getEnd())
						&& this.category.equals(data.getCategory());
			}
			return this.taskName.equals(data.getName())
					&& this.isCompleted == data.isCompleted()
					&& this.start.equals(data.getStart())
					&& this.end.equals(data.getEnd());
		}
		return false;
	}
}
