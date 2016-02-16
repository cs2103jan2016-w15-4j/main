 
public class AddCommand extends Command {

	private String taskName;
	private Task.TaskType taskType;
	private DateTime dateTimeDeadline;
	private DateTime dateTimeStart;
	private DateTime dateTimeEnd;
	
	public AddCommand(){
		command = "add";
	}
	
	public void initAddCommandFloat(String taskName){
		this.taskName = taskName;
		taskType = Task.TaskType.FLOAT;
	}
	
	public void initAddCommandDeadline(String data, DateTime deadline){
		this.taskName = data;
		this.dateTimeDeadline = deadline;
		taskType = Task.TaskType.DEADLINE;
	}
	
	public void initAddCommandEvent(String data, DateTime start, DateTime end){
		this.taskName = data;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
		taskType = Task.TaskType.EVENT;
	}
	
//	public void initAddCommandWithDayAndTime(String data, String day, String time){
//		this.data = data;
//		taskType = Task.TaskType.FLOAT;
//	}
	
	@Override
	public void execute(TaskManager taskManager){
		switch(taskType){
			case FLOAT:
					taskManager.AddTaskFloat(taskName);
				break;
			
			case DEADLINE:
					taskManager.AddTaskDeadline(taskName, dateTimeDeadline);
				break;
				
			case EVENT:
					taskManager.AddTaskEvent(taskName, dateTimeStart, dateTimeEnd);
				break;
		}
	}
}
