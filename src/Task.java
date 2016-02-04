
public class Task {
	public String taskName;
	public String time;
	public String day;
	
	public Task(){
		
	}
	
	
	public void initTask(String taskName){
		this.taskName = taskName;
		
	}
	
	public void initTaskWithTime(String taskName, String time){
		this.taskName = taskName;
	}
	
	public void initTaskWithDay(String taskName, String day){
		this.taskName = taskName;
	}
	
	public void initTaskDayAndTime(String taskName, String day, String time){
		this.taskName = taskName;
	}
	
	@Override
	public String toString(){
		return taskName;
	}
}
