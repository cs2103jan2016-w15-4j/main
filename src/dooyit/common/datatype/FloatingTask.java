package dooyit.common.datatype;

import dooyit.common.datatype.Task.TaskType;

public class FloatingTask extends Task {

	public FloatingTask(String taskName){
		taskType = TaskType.FLOATING;
		this.taskName = taskName;
	}
	
	@Override
	public String getDateString(){
		return "";
	}
	
	@Override
	public String toString() {
		return taskName;
	}
}
