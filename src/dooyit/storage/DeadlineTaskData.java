package dooyit.storage;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTask;

public class DeadlineTaskData extends TaskData {
	private DateTime deadline;

	public DeadlineTaskData(String name, DateTime deadline, 
							String category, boolean isCompleted) {
		this.taskName = name;
		this.isCompleted = isCompleted;
		this.category = category;
		this.deadline = deadline;
	}

	public DeadlineTaskData(String name, DateTime deadline, boolean isCompleted) {
		this.taskName = name;
		this.isCompleted = isCompleted;
		this.deadline = deadline;
	}
	
	public DateTime getDeadline() {
		return this.deadline;
	}
	
	public boolean equals(Object o) {
		if(o instanceof DeadlineTaskData) {
			DeadlineTaskData data = (DeadlineTaskData) o;
			if(this.hasCategory()) {
				return this.taskName.equals(data.getName())
						&& this.isCompleted == data.isCompleted()
						&& this.deadline.equals(data.getDeadline())
						&& this.category.equals(data.getCategory());
			}
			return this.taskName.equals(data.getName())
					&& this.isCompleted == data.isCompleted()
					&& this.deadline.equals(data.getDeadline());
		}
		return false;
	}
}
