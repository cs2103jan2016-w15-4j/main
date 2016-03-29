package dooyit.common.datatype;

public class FloatingTaskData extends TaskData{
	
	public FloatingTaskData(String name, boolean isCompleted) {
		this.taskName = name;
		this.isCompleted = isCompleted;
	}
	
	public FloatingTaskData(String name, String category, boolean isCompleted) {
		this.taskName = name;
		this.isCompleted = isCompleted;
		this.category = category;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof FloatingTaskData) {
			FloatingTaskData data = (FloatingTaskData) o;
			if(this.hasCategory()) {
				return this.taskName.equals(data.getName())
						&& this.isCompleted == data.isCompleted()
						&& this.category.equals(data.getCategory());
			} else {
				return this.taskName.equals(data.getName())
						&& this.isCompleted == data.isCompleted();
			}
		}
		
		return false;
	}
	
	@Override
	public Task convertToTask() {
		Task task = new FloatingTask(taskName);
		
		if(isCompleted){
			task.mark();
		}
		
		return task;
	}
}
