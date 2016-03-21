package dooyit.common.datatype;

import dooyit.common.datatype.Task.TaskType;

public class DeadlineTask extends Task {

	DateTime dateTimeDeadline;
	
	public DeadlineTask(String taskName, DateTime deadline){
		assert (deadline != null);

		taskType = TaskType.DEADLINE;
		this.taskName = taskName;
		this.dateTimeDeadline = deadline;
	}
	
	public DateTime getDeadlineTime() {
		return dateTimeDeadline;
	}
	
	@Override
	public String getDateString(){
		return dateTimeDeadline.getTime24hStr();
	}
	
	@Override
	public String toString() {
		return taskName + ": deadline: " + dateTimeDeadline.toString();
	}
}
