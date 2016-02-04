
public abstract class Command {
	public String command;
	public String data;
	public String time;
	public String day;
	public int deleteId;
	
	public Command(){
	}
	
	public abstract void execute(TaskManager taskManager);
}
