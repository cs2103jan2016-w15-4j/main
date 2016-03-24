package dooyit.common.datatype;

public abstract class Task {

	public enum TaskType {
		DEADLINE, EVENT, FLOATING
	};

	protected String taskName;
	protected int taskId;
	protected TaskType taskType;
	
	protected boolean isCompleted;
	protected Category category;

	public static int curTaskId = 1;

	public Task() {
		taskId = curTaskId++;
	}

	public void changeName(String taskName) {
		this.taskName = taskName;
	}

	public void mark() {
		isCompleted = true;
	}

	public void unMark() {
		isCompleted = false;
	}

	public boolean isCompleted() {
		return isCompleted;
	}
	
	

	public boolean setCategory(Category category) {
		assert (category != null);

		if(hasCategory()){
			return false;
		}else{
			this.category = category;
			return true;
		}
	}
	
	public Category getCategory(){
		return category;
	}
	
	public boolean hasCategory(){
		return category != null;
	}

	public String getName() {
		return taskName;
	}

	public int getId() {
		return taskId;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Task) {
			Task task = (Task) o;
			return task.getId() == taskId;
		} else {
			return false;
		}
	}
	
	public abstract String getDateString();
	
	public abstract boolean isOverDue(DateTime dateTime);
}
