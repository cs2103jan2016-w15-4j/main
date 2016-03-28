package dooyit.storage;

import dooyit.common.datatype.Category;

public class FloatTaskData extends TaskData{
	
	public FloatTaskData(String name, boolean isCompleted) {
		this.taskName = name;
		this.isCompleted = isCompleted;
	}
	
	public FloatTaskData(String name, Category category, boolean isCompleted) {
		this.taskName = name;
		this.isCompleted = isCompleted;
		this.category = getName(category);
	}
}
