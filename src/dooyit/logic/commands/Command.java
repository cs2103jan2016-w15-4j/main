package dooyit.logic.commands;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.ColourManager;
import dooyit.logic.api.LogicController;

public abstract class Command {

	public static enum CommandType {

	}
	
	public Command() {

	}

	public abstract void execute(LogicController logic) throws IncorrectInputException;
}
