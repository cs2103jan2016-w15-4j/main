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
	public boolean equals(Object o) {
		boolean isEquals = false;

		if (o instanceof DeadlineTaskData) {
			DeadlineTaskData data = (DeadlineTaskData) o;

			isEquals = this.taskName.equals(data.getName())
					&& this.isCompleted == data.isCompleted()
					&& this.dateTimeDeadline.equals(data.getDeadline());

			if (this.hasCategory() && data.hasCategory()) {
				isEquals = isEquals && this.category.equals(data.getCategory());
			} else if (this.hasCategory() || data.hasCategory()) {
				isEquals = false;
			}
		}

		return isEquals;
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
