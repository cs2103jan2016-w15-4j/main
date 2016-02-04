
public class Logic {

	Parser parser = new Parser();
	TaskManager taskManager = new TaskManager();
	Storage storage = new Storage();
	
	public Logic(){
		
	}
	
	
	public void processCommand(String input){
		Command command = parser.getCommand(input);
		command.execute(taskManager);
		save();
		
		//update UI - UI.update();
		taskManager.display();
	}
	
	private void save(){
		storage.saveTasks(taskManager.getTasks());
	}
	
}
