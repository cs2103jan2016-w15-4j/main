package dooyit.logic.commands;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public interface Command {

	public LogicAction execute(LogicController logic) throws IncorrectInputException;
	
	public boolean hasError();
}
