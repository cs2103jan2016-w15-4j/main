package dooyit.storage;

import dooyit.common.datatype.FloatingTask;

public class FloatTaskData extends TaskData{
	
	public FloatTaskData(FloatingTask task) {
		this.taskName = task.getName();
		this.isCompleted = task.isCompleted();
		if (hasCategory(task)) {
			this.category = getName(task.getCategory());
		}
	}
}
