

public class CommandUtils {
	
	public static Command createAddCommandFloat(String data){
		AddCommand addCommand = new AddCommand();
		addCommand.initAddCommandFloat(data);
		return addCommand;
	}
	
	public static Command createAddCommandWork(String data, DateTime deadline){
		AddCommand addCommand = new AddCommand();
		addCommand.initAddCommandWork(data, deadline);
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
		ShowCommand showCommand = new ShowCommand(Command.CommandShowType.TODAY);
		
		return showCommand;
	}
	
	public static Command createShowNext7DaysCommand(){
		ShowCommand showCommand = new ShowCommand(Command.CommandShowType.NEXT7DAYS);
		
		return showCommand;
	}
	
	public static Command createShowAllCommand(){
		ShowCommand showCommand = new ShowCommand(Command.CommandShowType.ALL);
		
		return showCommand;
	}
	
	public static Command createShowDoneCommand(){
		ShowCommand showCommand = new ShowCommand(Command.CommandShowType.DONE);
		
		return showCommand;
	}
	
	public static Command createShowCategoryCommand(String categoryName){
		ShowCommand showCommand = new ShowCommand(Command.CommandShowType.CATEGORY, categoryName);
		
		return showCommand;
	}
	
	public static Command createExitCommand(){
		ExitCommand exitCommand = new ExitCommand();
		return exitCommand;
	}

}
