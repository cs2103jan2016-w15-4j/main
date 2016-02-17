package dooyit.logic;

import dooyit.storage.Storage;
import dooyit.ui.UIController;
import dooyit.parser.Parser;
import dooyit.exception.IncorrectInputException;
import dooyit.logic.commands.*;

import java.io.IOException;

public class Logic {

	Parser parser;
	TaskManager taskManager;
	Storage storage;
	UIController ui;
	
	public Logic() throws IOException{
		parser = new Parser();
		taskManager = new TaskManager();
		storage = new Storage();
		storage.loadTasks(taskManager);
	}
	
	
	public void processCommand(String input){
		Command command = parser.getCommand(input);
		
		try{
			
			command.execute(taskManager);
			
		}catch(IncorrectInputException e){
			System.out.println(e.getMessage());
		}
		
		ui.refreshDayView(taskManager.getTasks(), "today");
		
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
	
	
	public void setUIController(UIController ui){
		this.ui = ui;
	}
}
