package dooyit.logic.commands;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.LogicController;
import dooyit.logic.api.TaskManager;

public class EditCommand extends ReversibleCommand {

	private enum EditCommandType {
		NAME, DEADLINE, EVENT, NAME_N_DEADLINE, NAME_N_EVENT
	};
	
	private EditCommandType editCommandType;
	private int taskId;
	public String taskName;
	private DateTime dateTimeDeadline;
	private DateTime dateTimeStart;
	private DateTime dateTimeEnd;

	public EditCommand(int taskId, String taskName) {
		editCommandType = EditCommandType.NAME;
		this.taskName = taskName;
		this.taskId = taskId;
	}

	public EditCommand(int taskId, DateTime deadline) {
		editCommandType = EditCommandType.DEADLINE;
		this.dateTimeDeadline = deadline;
		this.taskId = taskId;
	}

	public EditCommand(int taskId, DateTime start, DateTime end) {
		editCommandType = EditCommandType.EVENT;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
		this.taskId = taskId;
	}

	public EditCommand(int taskId, String taskName, DateTime deadline) {
		editCommandType = EditCommandType.NAME_N_DEADLINE;
		this.taskName = taskName;
		this.dateTimeDeadline = deadline;
		this.taskId = taskId;
	}

	public EditCommand(int taskId, String taskName, DateTime start, DateTime end) {
		editCommandType = EditCommandType.NAME_N_EVENT;
		this.taskName = taskName;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
		this.taskId = taskId;
	}

	@Override
	public void undo(LogicController logic) {
		
	}
	
	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);
		
		if(!logic.containsTask(taskId)){
			throw new IncorrectInputException("Cant find task ID: " + taskId);
		}
		
		switch(editCommandType){
		case NAME:
			logic.changeTaskName(taskId, taskName);
			break;
			
		case DEADLINE:
			logic.changeTaskToDeadline(taskId, dateTimeDeadline);
			break;
			
			case EVENT:
				logic.changeTaskToEvent(taskId, dateTimeStart, dateTimeEnd);
			break;

		case NAME_N_DEADLINE:
			logic.changeTaskName(taskId, taskName);
			logic.changeTaskToDeadline(taskId, dateTimeDeadline);
			break;

		case NAME_N_EVENT:
			logic.changeTaskName(taskId, taskName);
			logic.changeTaskToEvent(taskId, dateTimeStart, dateTimeEnd);
			break;		
		}
	}
}
