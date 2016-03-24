package dooyit.common.datatype;

public class FloatingTask extends Task {

	public FloatingTask(String taskName){
		assert(taskName != null);
		
		taskType = TaskType.FLOATING;
		this.taskName = taskName;
	}
	
	@Override
	public boolean isOverDue(DateTime dateTime){
		return false;
	}
	
	@Override
	public String getDateString(){
		return "";
	}
	
	@Override
	public String toString() {
		return taskName;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof FloatingTask){
			FloatingTask floatingTask = (FloatingTask)o;
			return this.getName() == floatingTask.getName();
		}
		return false;
	}
}
