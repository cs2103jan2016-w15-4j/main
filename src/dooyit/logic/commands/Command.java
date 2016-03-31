package dooyit.logic.commands;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.ColourManager;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;
import dooyit.ui.UIMainViewType;

public interface Command {

	public LogicAction execute(LogicController logic) throws IncorrectInputException;
}
