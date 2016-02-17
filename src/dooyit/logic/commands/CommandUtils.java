package dooyit.logic.commands;

import dooyit.parser.DateTime;

public class CommandUtils {
	
	public static Command createAddCommandFloat(String data){
		AddCommand addCommand = new AddCommand();
		addCommand.initAddCommandFloat(data);
		return addCommand;
	}
	
	public static Command createAddCommandDeadline(String data, DateTime deadline){
		AddCommand addCommand = new AddCommand();
		addCommand.initAddCommandDeadline(data, deadline);
		return addCommand;
	}
	
	public static Command createAddCommandEvent(String data, DateTime start, DateTime end){
		AddCommand addCommand = new AddCommand();
		addCommand.initAddCommandEvent(data, start, end);
		return addCommand;
	}
	
	
	
	
	public static Command createDeleteCommand(int id){
		DeleteCommand deleteCommand = new DeleteCommand();
		deleteCommand.initDeleteCommand(id);
		return deleteCommand;
	}
	
	public static Command createDeleteCommand(String stringId){
		int id = Integer.parseInt(stringId);
		DeleteCommand deleteCommand = new DeleteCommand();
		deleteCommand.initDeleteCommand(id);
		return deleteCommand;
	}
	
	
	
	
	public static Command createShowTodayCommand(){
		ShowCommand showCommand = new ShowCommand(Command.ShowCommandType.TODAY);
		
		return showCommand;
	}
	
	public static Command createShowNext7DaysCommand(){
		ShowCommand showCommand = new ShowCommand(Command.ShowCommandType.NEXT7DAYS);
		
		return showCommand;
	}
	
	public static Command createShowAllCommand(){
		ShowCommand showCommand = new ShowCommand(Command.ShowCommandType.ALL);
		
		return showCommand;
	}
	
	public static Command createShowDoneCommand(){
		ShowCommand showCommand = new ShowCommand(Command.ShowCommandType.DONE);
		
		return showCommand;
	}
	
	public static Command createShowCategoryCommand(String categoryName){
		ShowCommand showCommand = new ShowCommand(Command.ShowCommandType.CATEGORY, categoryName);
		
		return showCommand;
	}
	
	
	
	
	
	public static Command createEditCommandName(int taskId, String taskName){
		EditCommand editCommand = new EditCommand();
		editCommand.initEditCommandName(taskId, taskName);
		return editCommand;
	}
	
	public static Command createEditCommandDeadline(int taskId, DateTime deadline){
		EditCommand editCommand = new EditCommand();
		editCommand.initEditCommandDeadline(taskId, deadline);
		return editCommand;
	}
	
	public static Command createEditCommandEvent(int taskId, DateTime start, DateTime end){
		EditCommand editCommand = new EditCommand();
		editCommand.initEditCommandEvent(taskId, start, end);
		return editCommand;
	}
	
	public static Command createEditCommandNameAndDeadline(int taskId, String taskName, DateTime deadline){
		EditCommand editCommand = new EditCommand();
		editCommand.initEditCommandNameAndDeadline(taskId, taskName, deadline);
		return editCommand;
	}
	
	public static Command createEditCommandNameAndEvent(int taskId, String taskName, DateTime start, DateTime end){
		EditCommand editCommand = new EditCommand();
		editCommand.initEditCommandNameAndEvent(taskId, taskName, start, end);
		return editCommand;
	}
	
	
	
	public static Command createInvalidCommand(String errorMessage){
		InvalidCommand invalidCommand = new InvalidCommand(errorMessage);
		return invalidCommand;
	}
	
	
	public static Command createExitCommand(){
		ExitCommand exitCommand = new ExitCommand();
		return exitCommand;
	}

}
