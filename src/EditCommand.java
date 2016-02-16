
public class EditCommand extends Command {
	
	EditCommandType editCommandType;
	int taskId;
	String taskName;
	private DateTime dateTimeDeadline;
	private DateTime dateTimeStart;
	private DateTime dateTimeEnd;

	
	public EditCommand(){
	}
	
	public void initEditCommandName(int taskId, String taskName){
		editCommandType = EditCommandType.NAME;
		this.taskName = taskName;
	}
	
	public void initEditCommandDeadline(int taskId, DateTime deadline){
		editCommandType = EditCommandType.DEADLINE;
		this.dateTimeDeadline = deadline;
	}

	public void initEditCommandEvent(int taskId, DateTime start, DateTime end){
		editCommandType = EditCommandType.EVENT;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
	}
	
	public void initEditCommandNameAndDeadline(int taskId, String taskName, DateTime deadline){
		editCommandType = EditCommandType.NAME_N_DEADLINE;
		this.taskName = taskName;
		this.dateTimeDeadline = deadline;
	}

	public void initEditCommandNameAndEvent(int taskId, String taskName, DateTime start, DateTime end){
		editCommandType = EditCommandType.NAME_N_EVENT;
		this.taskName = taskName;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
	}
	
	@Override
	public void execute(TaskManager taskManager) {
		
		Task task = taskManager.findTask(taskId);
		
		switch(editCommandType){
			case NAME:
				task.changeName(taskName);
				break;
			
			case DEADLINE:
				task.changeDeadline(dateTimeDeadline);			
				break;
							
			case EVENT:
				task.changeEvent(dateTimeStart, dateTimeEnd);
				break;
				
			case NAME_N_DEADLINE:
				task.changeName(taskName);
				task.changeDeadline(dateTimeDeadline);
				break;
				
			case NAME_N_EVENT:
				task.changeName(taskName);
				task.changeEvent(dateTimeStart, dateTimeEnd);
				break;
		}
	}

}
