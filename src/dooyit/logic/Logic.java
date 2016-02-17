package dooyit.logic;

import dooyit.main.Storage;
import dooyit.parser.Parser;
import dooyit.logic.commands.*;

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
		
//		EditCommand a = ((EditCommand)command);
//		if( a != null)
//		System.out.println("String: " + a.taskName);
		
		command.execute(taskManager);
		save();
		
		//update UI - UI.update();
		taskManager.display();
	}
	
	private void save(){
		storage.saveTasks(taskManager.getTasks());
	}
	
}
