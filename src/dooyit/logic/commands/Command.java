package dooyit.logic.commands;

import com.sun.istack.internal.logging.Logger;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.LogicController;

public abstract class Command {

	public static enum ShowCommandType {
		ALL, TODAY, NEXT7DAYS, DONE, CATEGORY
	};

	public enum EditCommandType {
		NAME, DEADLINE, EVENT, NAME_N_DEADLINE, NAME_N_EVENT
	};

	public static enum CommandType {

	}

	public Command() {

	}

	public abstract void execute(LogicController logic) throws IncorrectInputException;
}
