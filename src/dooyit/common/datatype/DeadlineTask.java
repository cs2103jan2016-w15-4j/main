package dooyit.common.datatype;

public class DeadlineTask extends Task {

	DateTime dateTimeDeadline;
	
	public DeadlineTask(String taskName, DateTime deadline){
		assert (taskName != null && deadline != null);

		taskType = TaskType.DEADLINE;
		this.taskName = taskName;
		this.dateTimeDeadline = deadline;
	}
	
	public DeadlineTask(String taskName, DateTime deadline, Category category){
		assert (taskName != null && deadline != null);

		taskType = TaskType.DEADLINE;
		this.taskName = taskName;
		this.dateTimeDeadline = deadline;
		this.category = category;
	}
	
	public DeadlineTask(DeadlineTask deadlineTask){
		assert(deadlineTask != null);
		
		this.taskType = deadlineTask.taskType;
		this.uniqueId = deadlineTask.uniqueId;
		this.taskName = deadlineTask.taskName;
		this.dateTimeDeadline = deadlineTask.dateTimeDeadline;
		this.category = deadlineTask.category;
		this.isCompleted = deadlineTask.isCompleted;
	}
	
	public DateTime getDateTimeDeadline() {
		return dateTimeDeadline;
	}
	
	@Override
	public Task copy(){
		return new DeadlineTask(this);
	}
	
	@Override
	public boolean isToday(DateTime dateTime){
		return dateTimeDeadline.isTheSameDateAs(dateTime);
	}
	
	@Override
	public boolean isOverDue(DateTime dateTime){
		return !isToday(dateTime) && !isCompleted && dateTimeDeadline.compareTo(dateTime) == -1;
	}
	
	@Override
	public String getDateString(){
		if(dateTimeDeadline.hasTime()){
			return dateTimeDeadline.getTime24hStr();
		}
		else{
			return "";
		}
	}
	
	@Override
	public String toString() {
		String taskString = taskName + ", Deadline: " + dateTimeDeadline.toString();
		String categoryString = "";
		
		if(hasCategory()){
			categoryString = " ,Cat: " + category.getName() + "-" + category.getCustomColourName();
		}
		
		return taskString + categoryString;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof DeadlineTask) {
			DeadlineTask deadlineTask = (DeadlineTask) o;
			return this.getName().equals(deadlineTask.getName()) 
					&& this.getDateTimeDeadline().equals(deadlineTask.getDateTimeDeadline());
		}
		return false;
	}
	
	@Override
	public TaskData convertToData(){
		DeadlineTaskData deadlineTaskData;
		
		if(!hasCategory()){
			deadlineTaskData = new DeadlineTaskData(taskName, dateTimeDeadline, isCompleted);
		}else{
			deadlineTaskData = new DeadlineTaskData(taskName, dateTimeDeadline, category.getName(), isCompleted);
		}
		
		return deadlineTaskData;
	}
}
