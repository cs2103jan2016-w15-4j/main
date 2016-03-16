package dooyit.logic.core;

import dooyit.storage.Storage;

import dooyit.ui.UIController;
import dooyit.ui.UIMainViewType;
import dooyit.parser.Parser;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.*;

import java.io.IOException;


import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logic {

	private Parser parser;
	private TaskManager taskManager;
	private CategoryManager categoryManager;
	private Storage storage;
	private UIController uiController;
	private static Logger logger = Logger.getLogger("Logic");
	
	public Logic(){
		logger.log(Level.INFO, "Initialising logic class");
		
		parser = new Parser();
		taskManager = new TaskManager();
		categoryManager = new CategoryManager();
		
		try{
			storage = new Storage();
		}catch(IOException e){
			logger.log(Level.SEVERE, "ERROR: Fail to create storage");
			uiController.displayMessage("ERROR: CREATING STORAGE");
		}
		
//		try {
//			categoryManager.categories = storage.loadCategory();
//		} catch (IOException e) {
//			System.out.println("ERROR: LOAD CATEGORY");
//			uiController.displayMessage("ERROR: LOAD CATEGORY");
//		}
		
		try{
			storage.loadTasks(taskManager);
		}catch(IOException e){
			logger.log(Level.SEVERE, "ERROR: Fail to load task from storage");
			uiController.displayMessage("ERROR: LOAD TASK");
		}
		
		logger.log(Level.INFO, "End of initialising logic class");
	}
	
	/*
	 * process and execute command input from user
	 * 
	 */
	public void processCommand(String input){
		Command command = parser.getCommand(input);
		
		try{
			
			if(command != null){
				command.execute(this);
			}else{
				System.out.println("ERROR: Comman Object is null");
			}
			
		}catch(IncorrectInputException e){
			uiController.displayMessage(e.getMessage());
		}
		
		refreshUIController();
		
		try{
			
			save();
			
		} catch(IOException e){
			logger.log(Level.SEVERE, "ERROR: Fail to save");
			uiController.displayMessage("ERROR: SAVING");
		}
		
		//update UI - UI.update();
		taskManager.display();
	}
	
	private void save() throws IOException{
		storage.saveTasks(taskManager.getAllTasks());
	}
	
	private void refreshUIController(){
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
	
	/**
	 * pass the uicontroller references to this class
	 * @param ui
	 */
	public void setUIController(UIController ui){
		this.uiController = ui;
		refreshUIController();
	}
	
	/**
	 * Get the TaskManager object
	 * @return TaskManager
	 */
	public TaskManager getTaskManager(){
		return taskManager;
	}
	
	/**
	 * Get UIController object
	 * @return UIController
	 */
	public UIController getUIController(){
		return uiController;
	}
	
	/**
	 * Get Storage object
	 * @return Storage
	 */
	public Storage getStorage(){
		return storage;
	}
	
	/**
	 * Get CategoryManager object
	 * @return CategoryManager
	 */
	public CategoryManager getCategoryManager(){
		return categoryManager;
	}
	
}
