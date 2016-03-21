package dooyit.common.datatype;

import dooyit.common.datatype.Task.TaskType;

public class DeadlineTask extends Task {

	DateTime dateTime;
	
	public DeadlineTask(String taskName, DateTime deadline){
		assert (deadline != null);

		taskType = TaskType.DEADLINE;
		this.taskName = taskName;
		this.dateTimeDeadline = deadline;
	}
	
	
}
