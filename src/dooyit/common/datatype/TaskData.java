package dooyit.common.datatype;

public abstract class TaskData {
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
	
	public abstract Task convertToTask();
}
