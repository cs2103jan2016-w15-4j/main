package dooyit.logic.commands;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.TaskManager;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;
import dooyit.ui.UIMainViewType;

public class EditCommand implements Command, ReversibleCommand {

	private enum EditCommandType {
		NAME, DEADLINE, EVENT, NAME_N_DEADLINE, NAME_N_EVENT
	};

	private EditCommandType editCommandType;
	private int taskId;
	private String taskName;
	private Task originalTask;
	private Task newTask;
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
		assert(start != null && end != null);
		
		editCommandType = EditCommandType.NAME_N_EVENT;
		this.taskName = taskName;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
		this.taskId = taskId;
	}

	public void undo(LogicController logic) {
		assert (logic != null);
		
		logic.removeTask(newTask);
		logic.addTask(originalTask);
	}

	public void redo(LogicController logic){
		logic.addTask(newTask);
		logic.removeTask(originalTask);
	}
	
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);
		LogicAction logicAction;

		if (!logic.containsTask(taskId)) {
			throw new IncorrectInputException("Cant find task ID: " + taskId);
		}

		// save original task for undo
		originalTask = logic.findTask(taskId);

		switch (editCommandType) {
		case NAME:
			newTask = logic.changeTaskName(taskId, taskName);
			break;

		case DEADLINE:
			newTask = logic.changeTaskToDeadline(taskId, dateTimeDeadline);
			break;

		case EVENT:
			newTask = logic.changeTaskToEvent(taskId, dateTimeStart, dateTimeEnd);
			break;

		case NAME_N_DEADLINE:
			logic.changeTaskName(taskId, taskName);
			newTask = logic.changeTaskToDeadline(taskId, dateTimeDeadline);
			break;

		case NAME_N_EVENT:
			logic.changeTaskName(taskId, taskName);
			newTask = logic.changeTaskToEvent(taskId, dateTimeStart, dateTimeEnd);
			break;
		}
		
		logicAction = getActionBasedOnEditedTask(logic, newTask);
		return logicAction;
	}
	
	public LogicAction getActionBasedOnEditedTask(LogicController logic, Task newTask){
		LogicAction logicAction;
		
		if (logic.isFloatingTask(newTask)) {
			logic.setActiveView(UIMainViewType.FLOAT);
			logicAction = new LogicAction(Action.EDIT_FLOATING_TASK);
			
		}
		else if (logic.isTodayTask(newTask)) {
			logic.setActiveView(UIMainViewType.TODAY);
			logicAction = new LogicAction(Action.EDIT_TODAY_TASK);
			
		} else if (logic.isNext7daysTask(newTask)) {
			logic.setActiveView(UIMainViewType.EXTENDED);
			logicAction = new LogicAction(Action.EDIT_NEXT7DAY_TASK);
			
		} else {
			logic.setActiveView(UIMainViewType.ALL);
			logicAction = new LogicAction(Action.EDIT_ALL_TASK);
		}
		
		return logicAction;
	}
	
	
}
