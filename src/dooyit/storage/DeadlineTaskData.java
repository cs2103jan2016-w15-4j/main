package dooyit.storage;

import dooyit.common.datatype.DeadlineTask;

public class DeadlineTaskData extends TaskData {
	private String deadline;

	public DeadlineTaskData(DeadlineTask task) {
		this.taskName = task.getName();
		this.isCompleted = task.isCompleted();
		if (hasCategory(task)) {
			this.category = getName(task.getCategory());
		}
		this.deadline = toReadableFormat(task.getDateTimeDeadline());
	}
}
