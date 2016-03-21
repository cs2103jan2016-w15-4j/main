package dooyit.logic.commands;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.ColourManager;
import dooyit.logic.core.LogicController;

public abstract class Command {

	public static enum CommandType {

	}
	
	ColourManager colorManager = ColourManager.getInstance();
	
	public Command() {

	}

	public abstract void execute(LogicController logic) throws IncorrectInputException;
}
