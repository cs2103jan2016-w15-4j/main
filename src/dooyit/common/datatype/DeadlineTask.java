package dooyit.common.datatype;

public class DeadlineTask extends Task {

	DateTime dateTimeDeadline;
	
	public DeadlineTask(String taskName, DateTime deadline){
		assert (taskName != null && deadline != null);

		taskType = TaskType.DEADLINE;
		this.taskName = taskName;
		this.dateTimeDeadline = deadline;
	}
	
	public DateTime getDateTimeDeadline() {
		return dateTimeDeadline;
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
		return taskName + ": deadline: " + dateTimeDeadline.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof DeadlineTask) {
			DeadlineTask deadlineTask = (DeadlineTask) o;
			return this.getName() == deadlineTask.getName() 
					&& this.getDateTimeDeadline().equals(deadlineTask.getDateTimeDeadline());
		}
		return false;
	}
}
