package dooyit.logic.commands;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.TaskManager;
import dooyit.logic.api.LogicController;
import dooyit.ui.UIMainViewType;

public class AddCommand extends ReversibleCommand {

	private String taskName;
	private Task.TaskType taskType;
	private DateTime dateTimeDeadline;
	private DateTime dateTimeStart;
	private DateTime dateTimeEnd;
	private Task addedTask;

	public AddCommand(String taskName) {
		this.taskName = taskName;
		taskType = Task.TaskType.FLOATING;
	}

	public AddCommand(String data, DateTime deadline) {
		this.taskName = data;
		this.dateTimeDeadline = deadline;
		taskType = Task.TaskType.DEADLINE;
	}

	public AddCommand(String data, DateTime start, DateTime end) {
		this.taskName = data;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
		taskType = Task.TaskType.EVENT;
	}

	@Override
	public void undo(LogicController logic) {
		TaskManager taskManager = logic.getTaskManager();
		taskManager.remove(addedTask);
	}

	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);

		switch (taskType) {
		case FLOATING:
			addedTask = logic.addFloatingTask(taskName);
			logic.setActiveView(UIMainViewType.FLOAT);
			break;

		case DEADLINE:
			addedTask = logic.addDeadlineTask(taskName, dateTimeDeadline);

			if (logic.isTodayTask(addedTask)) {
				logic.setActiveView(UIMainViewType.TODAY);
			} else if (logic.isNext7daysTask(addedTask)) {
				logic.setActiveView(UIMainViewType.EXTENDED);
			} else {
				logic.setActiveView(UIMainViewType.ALL);
			}
			break;

		case EVENT:
			addedTask = logic.addEventTask(taskName, dateTimeStart, dateTimeEnd);
			
			if (logic.isTodayTask(addedTask)) {
				logic.setActiveView(UIMainViewType.TODAY);
			} else if (logic.isNext7daysTask(addedTask)) {
				logic.setActiveView(UIMainViewType.EXTENDED);
			} else {
				logic.setActiveView(UIMainViewType.ALL);
			}
			break;
		}
	}
}
