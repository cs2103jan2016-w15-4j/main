package dooyit.storage;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTask;

public class DeadlineTaskData extends TaskData {
	private DateTime deadline;

	public DeadlineTaskData(String name, DateTime deadline, 
							Category category, boolean isCompleted) {
		this.taskName = name;
		this.isCompleted = isCompleted;
		this.category = getName(category);
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
}
