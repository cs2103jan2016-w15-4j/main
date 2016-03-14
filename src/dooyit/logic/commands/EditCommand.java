package dooyit.logic.commands;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.Logic;
import dooyit.logic.core.TaskManager;

public class EditCommand extends Command {
	
	private EditCommandType editCommandType;
	private int taskId;
	public String taskName;
	private DateTime dateTimeDeadline;
	private DateTime dateTimeStart;
	private DateTime dateTimeEnd;
	
	
	public EditCommand(){
		
	}
	
	public void initEditCommandName(int taskId, String taskName){
		editCommandType = EditCommandType.NAME;
		this.taskName = taskName;
		this.taskId = taskId;
	}
	
	public void initEditCommandDeadline(int taskId, DateTime deadline){
		editCommandType = EditCommandType.DEADLINE;
		this.dateTimeDeadline = deadline;
		this.taskId = taskId;
	}

	public void initEditCommandEvent(int taskId, DateTime start, DateTime end){
		editCommandType = EditCommandType.EVENT;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
		this.taskId = taskId;
	}
	
	public void initEditCommandNameAndDeadline(int taskId, String taskName, DateTime deadline){
		editCommandType = EditCommandType.NAME_N_DEADLINE;
		this.taskName = taskName;
		this.dateTimeDeadline = deadline;
		this.taskId = taskId;
	}

	public void initEditCommandNameAndEvent(int taskId, String taskName, DateTime start, DateTime end){
		editCommandType = EditCommandType.NAME_N_EVENT;
		this.taskName = taskName;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
		this.taskId = taskId;
	}
	
	@Override
	public void execute(Logic logic) throws IncorrectInputException{
		TaskManager taskManager = logic.getTaskManager();
		
		Task task = taskManager.findTask(taskId);
		
		// if task is not found
		if(task == null){
			throw new IncorrectInputException("Cant find task ID: " + taskId);
		}
		else{
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

}
