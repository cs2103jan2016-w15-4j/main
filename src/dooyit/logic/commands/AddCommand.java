package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.Logic;
import dooyit.logic.core.Task;
import dooyit.logic.core.TaskManager;
import dooyit.parser.DateTime;

public class AddCommand extends Command {

	private String taskName;
	private Task.TaskType taskType;
	private DateTime dateTimeDeadline;
	private DateTime dateTimeStart;
	private DateTime dateTimeEnd;
	
	public AddCommand(){
	
	}
	
	public void initAddCommandFloat(String taskName){
		this.taskName = taskName;
		taskType = Task.TaskType.FLOATING;
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
	
	@Override
	public void execute(Logic logic) throws IncorrectInputException{
		TaskManager taskManager = logic.getTaskManager();
		
		switch(taskType){
			case FLOATING:
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
