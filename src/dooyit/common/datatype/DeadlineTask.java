//@@author A0126356E
package dooyit.common.datatype;

import dooyit.common.Constants;
import dooyit.common.datatype.DateTime.Day;
import dooyit.common.datatype.DateTime.Month;

public class DeadlineTask extends Task {

	DateTime dateTimeDeadline;

	public DeadlineTask(String taskName, DateTime deadline) {
		assert (taskName != null && deadline != null);

		taskType = TaskType.DEADLINE;
		this.taskName = taskName;
		this.dateTimeDeadline = deadline;
	}

	public DeadlineTask(String taskName, DateTime deadline, Category category) {
		assert (taskName != null && deadline != null);

		taskType = TaskType.DEADLINE;
		this.taskName = taskName;
		this.dateTimeDeadline = deadline;
		this.category = category;
	}

	public DeadlineTask(Task task, DateTime dateTimeDeadline) {
		assert (task != null);

		this.taskType = TaskType.DEADLINE;
		this.displayId = task.displayId;
		this.uniqueId = task.uniqueId;
		this.taskName = task.taskName;
		this.category = task.category;
		this.isCompleted = task.isCompleted;
		this.dateTimeDeadline = dateTimeDeadline;
	}

	public DeadlineTask(DeadlineTask deadlineTask) {
		assert (deadlineTask != null);

		this.taskType = TaskType.DEADLINE;
		this.displayId = deadlineTask.displayId;
		this.uniqueId = deadlineTask.uniqueId;
		this.taskName = deadlineTask.taskName;
		this.dateTimeDeadline = deadlineTask.dateTimeDeadline;
		this.category = deadlineTask.category;
		this.isCompleted = deadlineTask.isCompleted;
	}

	public DateTime getDateTimeDeadline() {
		return dateTimeDeadline;
	}

	@Override
	public boolean hasOverlap(Task task) {
		return false;
	}

	@Override
	public DateTime getDateTime() {
		return dateTimeDeadline;
	}

	@Override
	public int compareDateTo(Task task) {
		if (task instanceof FloatingTask) {
			return -1;
		}

		return this.dateTimeDeadline.compareTo(task.getDateTime());
	}

	@Override
	public Task copy() {
		return new DeadlineTask(this);
	}

	@Override
	public boolean isSameDate(DateTime dateTime) {
		return dateTimeDeadline.isTheSameDateAs(dateTime);
	}

	@Override
	public boolean isOverDue(DateTime dateTime) {
		return !isSameDate(dateTime) && !isCompleted && dateTimeDeadline.compareTo(dateTime) == -1;
	}

	@Override
	public boolean isMonth(Month month) {
		return dateTimeDeadline.isMonth(month);
	}

	@Override
	public boolean isDay(Day day) {
		return dateTimeDeadline.isDay(day);
	}

	@Override
	public String getDateString() {
		if (dateTimeDeadline.hasTime()) {
			return dateTimeDeadline.getTime24hStr();
		} else {
			return Constants.EMPTY_STRING;
		}
	}

	@Override
	public String toString() {
		String taskString = String.format(Constants.DISPLAY_TASK_DEADLINE, taskName, dateTimeDeadline.toString());
		String categoryString = Constants.EMPTY_STRING;

		if (hasCategory()) {
			categoryString = String.format(Constants.DISPLAY_CATEGORY, category.getName(), category.getCustomColourName());
		}

		return taskString + categoryString;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof DeadlineTask) {
			DeadlineTask deadlineTask = (DeadlineTask) o;
			return this.uniqueId == deadlineTask.uniqueId && this.getName().equals(deadlineTask.getName()) && this.getDateTimeDeadline().equals(deadlineTask.getDateTimeDeadline());
		}
		return false;
	}

	@Override
	public TaskData convertToData() {
		DeadlineTaskData deadlineTaskData;

		if (!hasCategory()) {
			deadlineTaskData = new DeadlineTaskData(taskName, dateTimeDeadline, isCompleted);
		} else {
			deadlineTaskData = new DeadlineTaskData(taskName, dateTimeDeadline, category.getName(), isCompleted);
		}

		return deadlineTaskData;
	}
}
