package dooyit.common.datatype;

public class FloatingTask extends Task {

	public FloatingTask(String taskName){
		assert(taskName != null);
		
		taskType = TaskType.FLOATING;
		this.taskName = taskName;
	}
	
	@Override
	public Task copy(){
		return new FloatingTask(taskName);
	}
	
	@Override
	public boolean isToday(DateTime dateTime){
		return false;
	}
	
	@Override
	public boolean isOverDue(DateTime dateTime){
		return false;
	}
	
	@Override
	public String getDateString(){
		return "";
	}
	
	@Override
	public String toString() {
		
		String taskString = taskName;
		String categoryString = "";
		
		if(hasCategory()){
			categoryString = " ,Cat: " + category.getName() + "-" + category.getCustomColourName();
		}
		
		return taskString + categoryString;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof FloatingTask){
			FloatingTask floatingTask = (FloatingTask)o;
			return this.getName().equals(floatingTask.getName());
		}
		return false;
	}
	
	@Override
	public TaskData convertToData(){
		FloatingTaskData floatingTaskData;
		
		if(!hasCategory()){
			floatingTaskData = new FloatingTaskData(taskName, isCompleted);
		}else{
			floatingTaskData = new FloatingTaskData(taskName, category.getName(), isCompleted);
		}
		
		return floatingTaskData;
	}
}
