package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.Logic;

public abstract class Command {
	
	public static enum ShowCommandType{
		ALL, TODAY, NEXT7DAYS, DONE, CATEGORY
	};
	
	public enum EditCommandType{
		NAME, DEADLINE, EVENT, NAME_N_DEADLINE, NAME_N_EVENT
	};
	
	public static enum CommandType{
		
	}
	
	public String time;
	public String day;
	
	
	public Command(){
		
	}
	
	public abstract void execute(Logic logic) throws IncorrectInputException;
}
