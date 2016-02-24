package dooyit.logic;

import dooyit.storage.Storage;
import dooyit.ui.UIController;
import dooyit.parser.Parser;
import dooyit.exception.IncorrectInputException;
import dooyit.logic.commands.*;

import java.io.IOException;

public class Logic {

	private Parser parser;
	private TaskManager taskManager;
	private Storage storage;
	private UIController ui;
	
	public Logic(){
		parser = new Parser();
		taskManager = new TaskManager();
		
		
		try{
			storage = new Storage();
		}catch(IOException e){
			System.out.println("ERROR: CREATING STORAGE");
			ui.displayMessage("ERROR: CREATING STORAGE");
		}
		
		try{
			storage.loadTasks(taskManager);
		}catch(IOException e){
			System.out.println("ERROR: LOAD TASK");
			ui.displayMessage("ERROR: LOAD TASK");
		}
		
	}
	
	
	public void processCommand(String input){
		Command command = parser.getCommand(input);
		
		try{
			
			command.execute(this);
			
		}catch(IncorrectInputException e){
			System.out.println(e.getMessage());
			ui.displayMessage(e.getMessage());
		}
		
		ui.refreshMainView(taskManager.getTodayTaskGroups());
		
		try{
			
			save();
			
		} catch(IOException e){
			System.out.println("ERROR: SAVING");
			ui.displayMessage("ERROR: SAVING");
		}
		
		//update UI - UI.update();
		taskManager.display();
	}
	
	private void save() throws IOException{
		storage.saveTasks(taskManager.getAllTasks());
	}
	
	
	public void setUIController(UIController ui){
		this.ui = ui;
		ui.refreshMainView(taskManager.getAllTaskGroups());
	}
	
	public TaskManager getTaskManager(){
		return taskManager;
	}
	
}
