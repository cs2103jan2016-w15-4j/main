package dooyit.storage;

public class Task {
	protected String taskName;
	protected String time;
	protected Category category;
	protected String day;
	protected int taskId;
	
	private static int curTaskId = 0;
	
	public Task() {
		taskName = "";
		time = "";
		day = "";
		category = new Category();
		taskId = curTaskId++;
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
	
	public String getName(){
		return taskName;
	}
	
	public int getId(){
		return taskId;
	}
	
	@Override
	public String toString(){
		return taskName;
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
