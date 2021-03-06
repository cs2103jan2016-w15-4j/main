//@@author A0126356E
package dooyit.common.datatype;

import java.util.ArrayList;

import dooyit.common.Constants;
import dooyit.common.datatype.DateTime.Day;
import dooyit.common.datatype.DateTime.Month;

public class EventTask extends Task {

	private DateTime dateTimeStart;
	private DateTime dateTimeEnd;
	private boolean hasMultiDay = false;
	private boolean isMultiDayOn = false;
	private int currentMultiDay = 0;
	private ArrayList<String> multiDayString;

	public EventTask(String taskName, DateTime start, DateTime end) {
		assert (taskName != null && start != null && end != null);

		taskType = TaskType.EVENT;
		this.taskName = taskName;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;

		checkMultiDay(start, end);
	}

	public EventTask(String taskName, DateTime start, DateTime end, Category category) {
		assert (taskName != null && start != null && end != null);

		taskType = TaskType.EVENT;
		this.taskName = taskName;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
		this.category = category;

		checkMultiDay(start, end);
	}

	public EventTask(Task task, DateTime start, DateTime end) {
		assert (task != null);

		this.taskType = TaskType.EVENT;
		this.displayId = task.displayId;
		this.uniqueId = task.uniqueId;
		this.taskName = task.taskName;
		this.category = task.category;
		this.isCompleted = task.isCompleted;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;

		checkMultiDay(start, end);
	}

	public EventTask(EventTask eventTask) {
		assert (eventTask != null);

		this.taskType = TaskType.EVENT;
		this.displayId = eventTask.displayId;
		this.uniqueId = eventTask.uniqueId;
		this.taskName = eventTask.taskName;
		this.dateTimeStart = eventTask.dateTimeStart;
		this.dateTimeEnd = eventTask.dateTimeEnd;
		this.category = eventTask.category;
		this.isCompleted = eventTask.isCompleted;

		checkMultiDay(dateTimeStart, dateTimeEnd);
	}

	public void checkMultiDay(DateTime start, DateTime end) {
		if (DateTime.hasMultiDay(start, end)) {
			multiDayString = DateTime.getMultiDayString(start, end);
			hasMultiDay = true;
		}
	}

	public void onMultiDay() {
		isMultiDayOn = true;
		currentMultiDay = 0;
		displayId = Constants.UNINITIALISED;
	}

	public void offMultiDay() {
		isMultiDayOn = false;
	}

	public int size() {
		if (hasMultiDay) {
			return multiDayString.size();
		}

		return 1;
	}

	@Override
	public DateTime getDateTime() {
		return dateTimeStart;
	}

	@Override
	public int compareDateTo(Task task) {
		if (task instanceof FloatingTask) {
			return -1;
		}

		return this.dateTimeStart.compareTo(task.getDateTime());
	}

	public DateTime getDateTimeStart() {
		return dateTimeStart;
	}

	public DateTime getDateTimeEnd() {
		return dateTimeEnd;
	}

	@Override
	public boolean hasOverlap(Task task) {

		if (task instanceof EventTask) {
			EventTask eventTask = (EventTask) task;

			return DateTime.isOverlap(dateTimeStart, dateTimeEnd, eventTask.dateTimeStart, eventTask.dateTimeEnd);
		}

		return false;
	}

	@Override
	public boolean isMonth(Month month) {
		return dateTimeStart.isMonth(month) || dateTimeEnd.isMonth(month);
	}

	@Override
	public boolean setDisplayId(int taskId) {

		if (hasMultiDay) {
			if (this.displayId == Constants.UNINITIALISED) {
				this.displayId = taskId;
				return true;
			} else {
				return false;
			}
		} else {
			this.displayId = taskId;
			return true;
		}
	}

	@Override
	public Task copy() {
		return new EventTask(this);
	}

	@Override
	public boolean isSameDate(DateTime dateTime) {
		if (hasMultiDay && isMultiDayOn) {
			return dateTimeStart.isTheSameDateAs(dateTime) 
					|| DateTime.isWithin(dateTime, dateTimeStart, dateTimeEnd) 
					|| dateTimeEnd.isTheSameDateAs(dateTime);
		} else {
			return dateTimeStart.isTheSameDateAs(dateTime);
		}
	}

	@Override
	public boolean isOverDue(DateTime dateTime) {
		boolean isOverDue = !dateTimeStart.isTheSameDateAs(dateTime) 
								&& dateTimeStart.compareTo(dateTime) == -1 
								&& !isCompleted;

		if (isOverDue) {
			hasMultiDay = false;
		}

		return isOverDue;
	}

	@Override
	public boolean isDay(Day day) {
		return DateTime.isWithinDay(day, dateTimeStart, dateTimeEnd);
	}

	@Override
	public String getDateString() {
		if (hasMultiDay && isMultiDayOn) {
			return multiDayString.get((currentMultiDay++) % multiDayString.size());
		}

		return dateTimeStart.getTime24hStr() + Constants.SYMBOL_DASH + dateTimeEnd.getTime24hStr();
	}

	@Override
	public String toString() {
		String taskString = String.format(Constants.DISPLAY_TASK_EVENT, taskName, dateTimeStart.toString(), dateTimeEnd.toString());
		String categoryString = Constants.EMPTY_STRING;

		if (hasCategory()) {
			categoryString = String.format(Constants.DISPLAY_CATEGORY, category.getName(), category.getCustomColourName());
		}

		return taskString + categoryString;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof EventTask) {
			EventTask eventTask = (EventTask) o;
			return this.uniqueId == eventTask.uniqueId 
					&& this.getName().equals(eventTask.getName()) 
					&& this.getDateTimeStart().equals(eventTask.getDateTimeStart()) 
					&& this.getDateTimeEnd().equals(eventTask.getDateTimeEnd());
		}
		return false;
	}

	@Override
	public TaskData convertToData() {
		EventTaskData eventTaskData;

		if (!hasCategory()) {
			eventTaskData = new EventTaskData(taskName, dateTimeStart, dateTimeEnd, isCompleted);
		} else {
			eventTaskData = new EventTaskData(taskName, dateTimeStart, dateTimeEnd, category.getName(), isCompleted);
		}

		return eventTaskData;
	}
}
