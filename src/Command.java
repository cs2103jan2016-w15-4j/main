
public abstract class Command {
	
	public static enum CommandShowType{
		ALL, TODAY, NEXT7DAYS, DONE, CATEGORY
	}
	
	
	public String command;
	public String data;
	public String time;
	public String day;
	public int deleteId;
	
	public Command(){
	}
	
	public abstract void execute(TaskManager taskManager);
}
