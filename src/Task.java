
public class Task {
	
	public enum TaskType{
		DEADLINE, EVENT, FLOAT
	}
	
	private String taskName;
	private int taskId;
	private TaskType taskType;
	private DateTime dateTimeDeadline;
	private DateTime dateTimeStart;
	private DateTime dateTimeEnd;
	
	private static int curTaskId = 0;
	
	public Task(){
		taskId = curTaskId++;
	}
	
	public void initTaskFloat(String taskName){
		taskType = TaskType.FLOAT;
		this.taskName = taskName;
	}
	
	public void initTaskDeadline(String taskName, DateTime deadline){
		taskType = TaskType.DEADLINE;
		this.taskName = taskName;
		this.dateTimeDeadline = deadline;
	}
	
	public void initTaskEvent(String taskName, DateTime start, DateTime end){
		taskType = TaskType.EVENT;
		this.taskName = taskName;
		this.dateTimeStart = start;
		this.dateTimeStart = end;
	}
	
	public void changeName(String taskName){
		this.taskName = taskName;
	}
	
	public void changeDeadline(DateTime deadline){
		taskType = TaskType.DEADLINE;
		this.dateTimeDeadline = deadline;
	}
	
	public void changeEvent(DateTime start, DateTime end){
		taskType = TaskType.EVENT;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
	}
	
	public String getName(){
		return taskName;
	}

	public int getId(){
		return taskId;
	}
	
	public TaskType getTaskType(){
		return taskType;
	}
	
	public DateTime getDeadline(){
		return dateTimeDeadline;
	}
	
	public DateTime getDateTimeStart(){
		return dateTimeStart;
	}
	
	public DateTime getDateTimeEnd(){
		return dateTimeEnd;
	}
	
	@Override
	public String toString(){
		String str = "";
		
		switch(taskType){
			case FLOAT:
				str = taskName;
				break;
				
			case DEADLINE:
				str = taskName + ": deadline: " + dateTimeDeadline.toString();
				break;
				
			case EVENT:
				str = taskName + ": event: " + dateTimeStart.toString() + "," + dateTimeEnd.toString();
				break;
			default:
				str = "";
		}
		
		return str;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Task){
			Task task = (Task)o;
			return task.getId() == taskId;
		}
		else return false;
	}
}
