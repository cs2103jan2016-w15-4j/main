//@@author A0124586Y
package dooyit.common.datatype;

public class DeadlineTaskData extends TaskData {
	private DateTime dateTimeDeadline;

	public DeadlineTaskData(String name, DateTime deadline, boolean isCompleted) {
		this.taskName = name;
		this.dateTimeDeadline = deadline;
		this.isCompleted = isCompleted;
	}
	
	public DeadlineTaskData(String name, DateTime deadline,
							String category, boolean isCompleted) {
		this(name, deadline, isCompleted);
		this.category = category;
}
	
	public DateTime getDeadline() {
		return this.dateTimeDeadline;
	}
	
	@Override
	public Task convertToTask() {
		Task task = new DeadlineTask(taskName, dateTimeDeadline);
		
		if(isCompleted){
			task.mark();
		}
		
		return task;
	}
}
