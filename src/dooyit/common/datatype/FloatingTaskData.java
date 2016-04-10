//@@author A0124586Y
package dooyit.common.datatype;

public class FloatingTaskData extends TaskData {

	public FloatingTaskData(String name, boolean isCompleted) {
		this.taskName = name;
		this.isCompleted = isCompleted;
	}

	public FloatingTaskData(String name, String category, boolean isCompleted) {
		this(name, isCompleted);
		this.category = category;
	}

	@Override
	public Task convertToTask() {
		Task task = new FloatingTask(taskName);

		if (isCompleted) {
			task.mark();
		}

		return task;
	}
}
