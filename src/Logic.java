import java.io.IOException;

public class Logic {

	Parser parser;
	TaskManager taskManager;
	Storage storage;
	
	public Logic(){
		parser = new Parser();
		taskManager = new TaskManager();
		storage = new Storage();
		taskManager.tasks = storage.loadTasks();
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
