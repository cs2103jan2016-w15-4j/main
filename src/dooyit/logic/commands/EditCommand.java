//@@author A0126356E
package dooyit.logic.commands;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

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
	private boolean hasError = false;

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
		assert (start != null && end != null);

		editCommandType = EditCommandType.NAME_N_EVENT;
		this.taskName = taskName;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
		this.taskId = taskId;
	}

	public boolean hasError() {
		return hasError;
	}

	public void undo(LogicController logic) {
		assert (logic != null);

		logic.removeTask(newTask);
		logic.addTask(originalTask);
	}

	public void redo(LogicController logic) {
		logic.addTask(newTask);
		logic.removeTask(originalTask);
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);
		LogicAction logicAction = null;

		if (!logic.containsTask(taskId)) {
			hasError = true;
			throw new IncorrectInputException("Cant find task ID: " + taskId);
		}

		// save original task for undo
		originalTask = logic.findTask(taskId);

		switch (editCommandType) {
		case NAME:
			newTask = logic.changeTaskName(taskId, taskName);
			logicAction = new LogicAction(Action.EDIT_NAME);
			break;

		case DEADLINE:
			newTask = logic.changeTaskToDeadline(taskId, dateTimeDeadline);
			logicAction = getActionBasedOnEditedTask(logic, newTask);
			break;

		case EVENT:
			newTask = logic.changeTaskToEvent(taskId, dateTimeStart, dateTimeEnd);
			logicAction = getActionBasedOnEditedTask(logic, newTask);
			break;

		case NAME_N_DEADLINE:
			logic.changeTaskName(taskId, taskName);
			newTask = logic.changeTaskToDeadline(taskId, dateTimeDeadline);
			logicAction = getActionBasedOnEditedTask(logic, newTask);
			break;

		case NAME_N_EVENT:
			logic.changeTaskName(taskId, taskName);
			newTask = logic.changeTaskToEvent(taskId, dateTimeStart, dateTimeEnd);
			logicAction = getActionBasedOnEditedTask(logic, newTask);
			break;
		}

		return logicAction;
	}

	public LogicAction getActionBasedOnEditedTask(LogicController logic, Task newTask) {
		LogicAction logicAction;

		if (logic.isFloatingTask(newTask)) {
			logicAction = new LogicAction(Action.EDIT_TO_FLOATING_TASK);
		} else if (logic.isTodayTask(newTask)) {
			logicAction = new LogicAction(Action.EDIT_TO_TODAY_TASK);

		} else if (logic.isNext7daysTask(newTask)) {
			logicAction = new LogicAction(Action.EDIT_TO_NEXT7DAY_TASK);

		} else {
			logicAction = new LogicAction(Action.EDIT_TO_ALL_TASK);
		}

		return logicAction;
	}

}
