package dooyit.logic;

import dooyit.parser.DateTime;

public class Task {
	
	public enum TaskType{
		DEADLINE, EVENT, FLOAT
	};
	
	private String taskName;
	private int taskId;
	private TaskType taskType;
	private DateTime dateTimeDeadline;
	private DateTime dateTimeStart;
	private DateTime dateTimeEnd;
	private boolean isOverdue;
	
	public static int curTaskId = 1;
	
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
		this.dateTimeEnd = end;
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
	
	public String[] convertToSavableString(){
		String [] strings = null;
		
		switch(taskType){
			case FLOAT:
					strings = new String[1];
					strings[0] = taskName;
				break;
				
			case DEADLINE:
				int length = 1;
			
				String[] dateStrings = dateTimeDeadline.convertToSavableStrings();
				strings = new String[dateStrings.length + length];
				
				int i = 0;
				
				strings[i++] = taskName;
				
				while(i < strings.length){
					strings[i] = dateStrings[i - length];
					
					i++;
				}
				break;
				
			case EVENT:
				
				int length2 = 1;
				
				String[] dateStringsStart = dateTimeStart.convertToSavableStrings();
				String[] dateStringsEnd = dateTimeEnd.convertToSavableStrings();
				
				strings = new String[dateStringsStart.length + dateStringsEnd.length + length2];
				
				int i2 = 0;
				
				strings[i2++] = taskName;
				
				while(i2 < (strings.length - dateStringsStart.length)){
					strings[i2] = dateStringsStart[i2 - length2];
					
					i2++;
				}
				
				while(i2 < strings.length){
					strings[i2] = dateStringsEnd[i2 - length2 - dateStringsStart.length];
					
					i2++;
				}
				
				break;
		}
		
		return strings;
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
