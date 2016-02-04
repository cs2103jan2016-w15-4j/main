
public class CommandUtils {
	
	public static Command createAddCommand(String data){
		AddCommand addCommand = new AddCommand();
		addCommand.initAddCommand(data);
		return addCommand;
	}
	
	public static Command createAddCommandWithDay(String data, String day){
		AddCommand addCommand = new AddCommand();
		addCommand.initAddCommandWithDay(data, day);
		return addCommand;
	}
	
	public static Command createAddCommandWithTime(String data, String time){
		AddCommand addCommand = new AddCommand();
		addCommand.initAddCommandWithTime(data, time);
		return addCommand;
	}
	
	public static Command createAddCommandWithDayAndTime(String data, String day, String time){
		AddCommand addCommand = new AddCommand();
		addCommand.initAddCommandWithDayAndTime(data, day, time);
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
	
	public static Command createExitCommand(){
		ExitCommand exitCommand = new ExitCommand();
		return exitCommand;
	}
}
