package dooyit.logic.commands;

import dooyit.logic.TaskManager;

public abstract class Command {
	
	public static enum ShowCommandType{
		ALL, TODAY, NEXT7DAYS, DONE, CATEGORY
	};
	
	public enum EditCommandType{
		NAME, DEADLINE, EVENT, NAME_N_DEADLINE, NAME_N_EVENT
	};
	
	public static enum CommandType{
		
	}
	
	public String command;
	public String time;
	public String day;
	public int deleteId;
	
	public Command(){
		
	}
	
	public abstract void execute(TaskManager taskManager);
}
