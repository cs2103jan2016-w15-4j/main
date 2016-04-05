//@@author A0126356E
package dooyit.common.datatype;

public abstract class Task {

	public enum TaskType {
		DEADLINE, EVENT, FLOATING
	};

	protected String taskName;
	protected int displayId;
	protected TaskType taskType;

	protected boolean isCompleted;
	protected Category category;

	public static int curUniqueTaskId = 1;
	public int uniqueId; // to determine which task is created first
	private boolean isNewlyCreated;
	
	public Task() {
		uniqueId = curUniqueTaskId;
		displayId = curUniqueTaskId;
		curUniqueTaskId++;
		isNewlyCreated = true;
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

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getCategory() {
		return category;
	}

	public void removeCategory() {
		category = null;
	}

	public boolean hasCategory() {
		return category != null;
	}

	public String getName() {
		return taskName;
	}

	public int getDisplayId() {
		return displayId;
	}

	public void setDisplayId(int taskId) {
		this.displayId = taskId;
	}

	public void resetDisplayId() {
		this.displayId = -1;
	}

	public int getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	public TaskType getTaskType() {
		return taskType;
	}
	
	public boolean isNewlyCreated(){
		return isNewlyCreated;
	}
	
	public void setOld(){
		isNewlyCreated = false;
	}

	@Override
	public abstract boolean equals(Object o);

	public abstract boolean hasOverlap(Task task);
	
	public abstract String toString();

	public abstract DateTime getDateTime();

	public abstract int compareDateTo(Task task);

	public abstract Task copy();

	public abstract String getDateString();

	public abstract TaskData convertToData();

	public abstract boolean isOverDue(DateTime dateTime);

	public abstract boolean isSameDate(DateTime dateTime);
}
