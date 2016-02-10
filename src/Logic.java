import java.io.IOException;

public class Logic {

	Parser parser = new Parser();
	TaskManager taskManager = new TaskManager();
	Storage storage = new Storage();
	
	public Logic(){
		
	}
	
	
	public void processCommand(String input) throws IOException{
		Command command = parser.getCommand(input);
		command.execute(taskManager);
		save();
		
		//update UI - UI.update();
		taskManager.display();
	}
	
	private void save() throws IOException{
		storage.saveTasks(taskManager.getTasks());
	}
	
}
