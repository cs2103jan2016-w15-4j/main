package dooyit.logic;

import dooyit.storage.Storage;
import dooyit.ui.UIController;
import dooyit.ui.UIMainViewType;
import dooyit.parser.Parser;
import dooyit.exception.IncorrectInputException;
import dooyit.logic.commands.*;

import java.io.IOException;

public class Logic {

	private Parser parser;
	private TaskManager taskManager;
	private CategoryManager categoryManager;
	private Storage storage;
	private UIController uiController;
	
	public Logic(){
		parser = new Parser();
		taskManager = new TaskManager();
		categoryManager = new CategoryManager();
		
		try{
			storage = new Storage();
		}catch(IOException e){
			System.out.println("ERROR: CREATING STORAGE");
			uiController.displayMessage("ERROR: CREATING STORAGE");
		}
		
		try{
			storage.loadTasks(taskManager);
		}catch(IOException e){
			System.out.println("ERROR: LOAD TASK");
			uiController.displayMessage("ERROR: LOAD TASK");
		}
		
	}
	
	
	public void processCommand(String input){
		Command command = parser.getCommand(input);
		
		try{
			
			command.execute(this);
			
		}catch(IncorrectInputException e){
			System.out.println(e.getMessage());
			uiController.displayMessage(e.getMessage());
		}
		
		refreshUIController();
		
		try{
			
			save();
			
		} catch(IOException e){
			System.out.println("ERROR: SAVING");
			uiController.displayMessage("ERROR: SAVING");
		}
		
		//update UI - UI.update();
		taskManager.display();
	}
	
	private void save() throws IOException{
		storage.saveTasks(taskManager.getAllTasks());
	}
	
	public void refreshUIController(){
		//uiController.refreshMainView(taskManager.getTaskGroupsToday());
		UIMainViewType uiMainViewType = uiController.getActiveViewType();
		
		switch (uiMainViewType) {

			case TODAY:
				uiController.refreshMainView(taskManager.getTaskGroupsToday());
				break;
	
			case EXTENDED:
				uiController.refreshMainView(taskManager.getTaskGroupsNext7Days());
				break;
	
			case ALL:
				uiController.refreshMainView(taskManager.getTaskGroupsAll());
				break;
	
			case COMPLETED:
				uiController.refreshMainView(taskManager.getTaskGroupsCompleted());
				break;
	
			case CATEGORY:
	
				break;
		}
			
		uiController.refreshCategoryMenuView(categoryManager.getCategoryList());
	}
	
	public void setUIController(UIController ui){
		this.uiController = ui;
		refreshUIController();
	}
	
	public TaskManager getTaskManager(){
		return taskManager;
	}
	
	public UIController getUIController(){
		return uiController;
	}
	
	public Storage getStorage(){
		return storage;
	}
	
	public CategoryManager getCategoryManager(){
		return categoryManager;
	}
	
}
