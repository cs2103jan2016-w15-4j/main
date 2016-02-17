package dooyit.logic;

import dooyit.storage.Storage;
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
	
	
	public void processCommand(String input){
		Command command = parser.getCommand(input);
		
		command.execute(taskManager);
		
		try{
			save();
		} catch(IOException e){
			System.out.println("ERROR: SAVING" );
		}
		
		
		//update UI - UI.update();
		taskManager.display();
	}
	
	private void save() throws IOException{
		storage.saveTasks(taskManager.getTasks());
	}
	
}
