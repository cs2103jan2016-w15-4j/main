package dooyit.main;

public class Logic {

	Parser parser;
	TaskManager taskManager;
	Storage storage;
	
	public Logic(){
		 parser = new Parser();
		 taskManager = new TaskManager();
		 storage = new Storage();
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
