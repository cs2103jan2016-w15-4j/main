 
public class AddCommand extends Command {

	public AddCommand(){
		command = "add";
	}
	
	public void initAddCommandFloat(String data){
		this.data = data;
	}
	
	public void initAddCommandWork(String data, DateTime deadline){
		this.data = data;
	}
	
	public void initAddCommandEvent(String data, DateTime start, DateTime end){
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
