package dooyit.main;

public class ExitCommand extends Command {

	public ExitCommand(){
		
	}
	
	@Override
	public void execute(TaskManager taskManager){
		System.exit(1);
	}
}
