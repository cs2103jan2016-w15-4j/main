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
	public Task copy(){
		return new EventTask(taskName, new DateTime(dateTimeStart), new DateTime(dateTimeEnd));
	}
	
	@Override
	public boolean isToday(DateTime dateTime){
		return dateTimeStart.isTheSameDateAs(dateTime);
	}
	
	@Override
	public boolean isOverDue(DateTime dateTime){
		return !isToday(dateTime) && !isCompleted && dateTimeEnd.compareTo(dateTime) == -1;
	}
	
	@Override
	public String getDateString() {
		return dateTimeStart.getTime24hStr() + " - " + dateTimeEnd.getTime24hStr();
	}

	@Override
	public String toString() {
		String taskString = taskName + ", Event: " + dateTimeStart.toString() + "to" + dateTimeEnd.toString();
		String categoryString = "";
		
		if(hasCategory()){
			categoryString = ", Cat: " + category.getName() + "-" + category.getCustomColourName();
		}
		
		return taskString + categoryString;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof EventTask) {
			EventTask eventTask = (EventTask) o;
			return this.getName().equals(eventTask.getName()) 
					&& this.getDateTimeStart().equals(eventTask.getDateTimeStart())
					&& this.getDateTimeEnd().equals(eventTask.getDateTimeEnd());
		}
		return false;
	}
	
	@Override
	public TaskData convertToData(){
		EventTaskData eventTaskData;
		
		if(!hasCategory()){
			eventTaskData = new EventTaskData(taskName, dateTimeStart, dateTimeEnd, isCompleted);
		}else{
			eventTaskData = new EventTaskData(taskName, dateTimeStart, dateTimeEnd, category.getName(), isCompleted);
		}
		
		return eventTaskData;
	}
}
