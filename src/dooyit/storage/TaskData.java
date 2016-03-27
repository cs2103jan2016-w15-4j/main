package dooyit.storage;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTask;
import dooyit.common.datatype.EventTask;
import dooyit.common.datatype.Task;

public class TaskData {
	protected String taskName;
	protected String category;
	protected boolean isCompleted;
	
	protected String toReadableFormat(DateTime dt) {
		String format = "";
		format += String.valueOf(dt.getDD()) + " ";
		format += String.valueOf(dt.getMM()) + " ";
		format += String.valueOf(dt.getYY()) + " ";
		format += dt.getDayStr() + " ";
		format += dt.getTime24hStr();
		
		return format;
	}
	
	protected String getName(Category category) {
		return category.getName();
	}
	
	protected boolean hasCategory(Task task) {
		return task.hasCategory();
	}
}
