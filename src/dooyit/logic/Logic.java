package dooyit.logic;

import dooyit.main.Storage;
import dooyit.parser.Parser;
import dooyit.logic.commands.*;

import java.io.IOException;

public class Logic {

	Parser parser;
	TaskManager taskManager;
	Storage storage;
	
	public Logic() throws IOException{
		parser = new Parser();
		taskManager = new TaskManager();
		storage = new Storage();
		storage.loadTasks(taskManager);
	}
	
	
	public void processCommand(String input) throws IOException{
		Command command = parser.getCommand(input);
		
//		EditCommand a = ((EditCommand)command);
//		if( a != null)
//		System.out.println("String: " + a.taskName);
		
		command.execute(taskManager);
		save();
		
		//update UI - UI.update();
		taskManager.display();
	}
	
	private void save() throws IOException{
		storage.saveTasks(taskManager.getTasks());
	}
	
}
