
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
	
	public static Command createExitCommand(){
		ExitCommand exitCommand = new ExitCommand();
		return exitCommand;
	}
}
