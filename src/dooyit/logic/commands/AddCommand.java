package dooyit.logic.commands;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.LogicController;
import dooyit.logic.api.TaskManager;
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
	public void undo(LogicController logic){
		TaskManager taskManager = logic.getTaskManager();
		taskManager.remove(addedTask);
	}
	
	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);

		switch (taskType) {
		case FLOATING:
			addedTask = logic.addFloatingTask(taskName);
			//logic.setActiveView(UIMainViewType.FLOAT);
			break;

		case DEADLINE:
			addedTask = logic.addDeadlineTask(taskName, dateTimeDeadline);
			//logic.setActiveView(UIMainViewType.ALL);
			break;

		case EVENT:
			addedTask = logic.addEventTask(taskName, dateTimeStart, dateTimeEnd);
			//logic.setActiveView(UIMainViewType.ALL);
			break;
		}
	}
}
