//@@author A0126356E
package dooyit.logic.commands;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.Constants;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class AddTaskCommand implements Command, ReversibleCommand {

	private String taskName;
	private Task.TaskType taskType;
	private DateTime dateTimeDeadline;
	private DateTime dateTimeStart;
	private DateTime dateTimeEnd;
	private Task addedTask;
	private boolean hasError = false;

	public AddTaskCommand(String taskName) {
		this.taskName = taskName;
		taskType = Task.TaskType.FLOATING;
	}

	public AddTaskCommand(String data, DateTime deadline) {
		this.taskName = data;
		this.dateTimeDeadline = deadline;
		taskType = Task.TaskType.DEADLINE;
	}

	public AddTaskCommand(String data, DateTime start, DateTime end) {
		this.taskName = data;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
		taskType = Task.TaskType.EVENT;
	}

	public boolean hasError() {
		return hasError;
	}

	public void undo(LogicController logic) {
		logic.removeTask(addedTask);
	}

	public void redo(LogicController logic) {
		logic.addTask(addedTask);
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);
		LogicAction logicAction = null;

		switch (taskType) {
		case FLOATING:
			addedTask = logic.addFloatingTask(taskName);
			break;

		case DEADLINE:
			addedTask = logic.addDeadlineTask(taskName, dateTimeDeadline);
			break;

		case EVENT:
			addedTask = logic.addEventTask(taskName, dateTimeStart, dateTimeEnd);
			
			if(logic.hasOverlap(addedTask)){
				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			}
			break;
		}

		logicAction = getActionBasedOnAddedTask(logic, addedTask);
		return logicAction;
	}

	public LogicAction getActionBasedOnAddedTask(LogicController logic, Task addedTask) {
		LogicAction logicAction;

		String taskName = addedTask.getName();
		String feedBackMsg = String.format(Constants.FEEDBACK_TASK_ADDED, taskName);
		
		if (logic.isFloatingTask(addedTask)) {
			logicAction = new LogicAction(Action.ADD_FLOATING_TASK, feedBackMsg);

		} else if (logic.isTodayTask(addedTask)) {
			logicAction = new LogicAction(Action.ADD_TODAY_TASK, feedBackMsg);

		} else if (logic.isNext7daysTask(addedTask)) {
			logicAction = new LogicAction(Action.ADD_NEXT7DAY_TASK, feedBackMsg);

		} else {
			logicAction = new LogicAction(Action.ADD_ALL_TASK, feedBackMsg);
		}

		return logicAction;
	}

}
