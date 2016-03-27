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
	protected String uncheckCategory;

	public static int curUniqueTaskId = 1; 
	public int uniqueId; // to determine which task is created first
	
	public Task() {
		uniqueId = curUniqueTaskId;
		taskId = curUniqueTaskId;
		curUniqueTaskId++;
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
	
	public void setUncheckCategory(String categoryName){
		this.uncheckCategory = categoryName;
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
	
	public String getUncheckCategory(){
		return uncheckCategory;
	}
	
	public boolean hasUncheckCategory(){
		return uncheckCategory != null;
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
	
	public void setId(int taskId){
		this.taskId = taskId;
	}

	public int getUniqueId() {
		return uniqueId;
	}
	
	public void setUniqueId(int uniqueId){
		this.uniqueId = uniqueId;
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
	
	public abstract String toString();
	
	public abstract String getDateString();
	
	public abstract boolean isOverDue(DateTime dateTime);
	
	public abstract boolean isToday(DateTime dateTime);
}
