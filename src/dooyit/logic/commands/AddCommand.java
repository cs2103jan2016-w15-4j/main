package dooyit.logic.commands;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.LogicController;
import dooyit.logic.core.TaskManager;

public class AddCommand extends Command {

	private String taskName;
	private Task.TaskType taskType;
	private DateTime dateTimeDeadline;
	private DateTime dateTimeStart;
	private DateTime dateTimeEnd;

	public AddCommand() {

	}

	public void initAddCommandFloat(String taskName) {
		this.taskName = taskName;
		taskType = Task.TaskType.FLOATING;
	}

	public void initAddCommandDeadline(String data, DateTime deadline) {
		this.taskName = data;
		this.dateTimeDeadline = deadline;
		taskType = Task.TaskType.DEADLINE;
	}

	public void initAddCommandEvent(String data, DateTime start, DateTime end) {
		this.taskName = data;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
		taskType = Task.TaskType.EVENT;
	}

	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		TaskManager taskManager = logic.getTaskManager();
		assert (taskManager != null);

		switch (taskType) {
		case FLOATING:
			taskManager.addFloatingTask(taskName);
			break;

		case DEADLINE:
			taskManager.addDeadlineTask(taskName, dateTimeDeadline);
			break;

		case EVENT:
			taskManager.addEventTask(taskName, dateTimeStart, dateTimeEnd);
			break;
		}
	}
}
