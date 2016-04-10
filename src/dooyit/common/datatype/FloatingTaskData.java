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
	public boolean equals(Object o) {
		boolean isEquals = false;

		if (o instanceof FloatingTaskData) {
			FloatingTaskData data = (FloatingTaskData) o;

			isEquals = this.taskName.equals(data.getName())
					&& this.isCompleted == data.isCompleted();

			if (this.hasCategory() && data.hasCategory()) {
				isEquals = isEquals && this.category.equals(data.getCategory());
			} else if (this.hasCategory() || data.hasCategory()) {
				isEquals = false;
			}
		}

		return isEquals;
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
