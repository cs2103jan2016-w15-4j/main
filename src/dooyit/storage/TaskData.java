package dooyit.storage;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTask;
import dooyit.common.datatype.EventTask;
import dooyit.common.datatype.Task;

public class TaskData {
	protected String taskName;
	protected String category;
	protected boolean isCompleted;
	
	public boolean hasCategory(Task task) {
		return task.hasCategory();
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public String getName() {
		return this.taskName;
	}
	
	public boolean isCompleted() {
		return this.isCompleted;
	}
	
	public boolean hasCategory() {
		return category != null;
	}
}
