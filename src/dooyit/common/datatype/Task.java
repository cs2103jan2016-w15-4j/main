//@@author A0126356E
package dooyit.common.datatype;

import dooyit.common.Constants;
import dooyit.common.datatype.DateTime.Day;
import dooyit.common.datatype.DateTime.Month;

public abstract class Task {

	public enum TaskType {
		DEADLINE, EVENT, FLOATING
	};

	protected String taskName;
	protected int displayId;
	protected TaskType taskType;
	protected boolean isCompleted;
	protected Category category;
	protected static int curUniqueTaskId = 1;
	protected int uniqueId; // to determine which task is created first
	
	public Task() {
		uniqueId = curUniqueTaskId;
		displayId = curUniqueTaskId;
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

	/**
	 * 
	 * @param taskId
	 * @return true if ID is successfully set
	 */
	public boolean setDisplayId(int taskId) {
		this.displayId = taskId;
		return true;
	}

	public void resetDisplayId() {
		this.displayId = Constants.UNINITIALISED;
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

	@Override
	public abstract boolean equals(Object o);

	@Override
	public abstract String toString();
	
	public abstract boolean hasOverlap(Task task);
	
	public abstract DateTime getDateTime();

	public abstract int compareDateTo(Task task);

	public abstract Task copy();

	public abstract String getDateString();

	public abstract TaskData convertToData();

	public abstract boolean isOverDue(DateTime dateTime);

	public abstract boolean isSameDate(DateTime dateTime);
	
	public abstract boolean isMonth(Month month);
	
	public abstract boolean isDay(Day month);
}
