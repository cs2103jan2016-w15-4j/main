
public class AddCommand extends Command {

	public AddCommand(){
		command = "add";
	}
	
	public void initAddCommand(String data){
		this.data = data;
	}
	
	public void initAddCommandWithTime(String data, String time){
		this.data = data;
	}
	
	public void initAddCommandWithDay(String data, String day){
		this.data = data;
	}
	
	public void initAddCommandWithDayAndTime(String data, String day, String time){
		this.data = data;
	}
	
	@Override
	public void execute(TaskManager taskManager){
		taskManager.AddTask(data);
	}
}
