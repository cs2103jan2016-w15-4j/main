package dooyit.common.datatype;

import dooyit.common.datatype.Task.TaskType;

public class EventTask extends Task {

	public EventTask(String taskName, DateTime start, DateTime end){
		assert (start != null && end != null);

		taskType = TaskType.EVENT;
		this.taskName = taskName;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
	}
	
	
	
}
