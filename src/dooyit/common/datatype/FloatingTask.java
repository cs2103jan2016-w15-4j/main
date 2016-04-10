//@@author A0126356E
package dooyit.common.datatype;

import dooyit.common.Constants;
import dooyit.common.datatype.DateTime.DAY;
import dooyit.common.datatype.DateTime.MONTH;

public class FloatingTask extends Task {

	public FloatingTask(String taskName) {
		assert (taskName != null);

		taskType = TaskType.FLOATING;
		this.taskName = taskName;
	}

	public FloatingTask(String taskName, Category category) {
		assert (taskName != null);

		taskType = TaskType.FLOATING;
		this.taskName = taskName;
		this.category = category;
	}

	public FloatingTask(FloatingTask floatingTask) {
		assert (floatingTask != null);

		this.taskType = TaskType.FLOATING;
		this.displayId = floatingTask.displayId;
		this.uniqueId = floatingTask.uniqueId;
		this.taskName = floatingTask.taskName;
		this.category = floatingTask.category;
		this.isCompleted = floatingTask.isCompleted;
	}

	public FloatingTask(Task task) {
		assert (task != null);

		this.taskType = TaskType.FLOATING;
		this.displayId = task.displayId;
		this.uniqueId = task.uniqueId;
		this.taskName = task.taskName;
		this.category = task.category;
		this.isCompleted = task.isCompleted;
	}

	@Override
	public boolean hasOverlap(Task task) {
		return false;
	}

	@Override
	public DateTime getDateTime() {
		return null;
	}

	@Override
	public int compareDateTo(Task task) {
		return 1;
	}

	@Override
	public Task copy() {
		return new FloatingTask(this);
	}

	@Override
	public boolean isSameDate(DateTime dateTime) {
		return false;
	}

	@Override
	public boolean isOverDue(DateTime dateTime) {
		return false;
	}

	@Override
	public boolean isMonth(MONTH month) {
		return false;
	}

	@Override
	public boolean isDay(DAY day) {
		return false;
	}

	@Override
	public String getDateString() {
		return Constants.EMPTY_STRING;
	}

	@Override
	public String toString() {

		String taskString = taskName;
		String categoryString = Constants.EMPTY_STRING;

		if (hasCategory()) {
			categoryString = String.format(Constants.DISPLAY_CATEGORY, category.getName(), category.getCustomColourName());
		}

		return taskString + categoryString;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof FloatingTask) {
			FloatingTask floatingTask = (FloatingTask) o;
			return this.uniqueId == floatingTask.uniqueId && this.getName().equals(floatingTask.getName());
		}
		return false;
	}

	@Override
	public TaskData convertToData() {
		FloatingTaskData floatingTaskData;

		if (!hasCategory()) {
			floatingTaskData = new FloatingTaskData(taskName, isCompleted);
		} else {
			floatingTaskData = new FloatingTaskData(taskName, category.getName(), isCompleted);
		}

		return floatingTaskData;
	}
}
