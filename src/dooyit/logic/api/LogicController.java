package dooyit.logic.api;

import dooyit.storage.StorageController;
import dooyit.ui.UIController;
import dooyit.ui.UIMainViewType;
import dooyit.parser.Parser;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.Task;

public class LogicController {

	private Parser parser;
	private TaskManager taskManager;
	private CategoryManager categoryManager;
	private StorageController storage;
	private Stack<ReversibleCommand> history;
	private UIController uiController;
	private static Logger logger = Logger.getLogger("Logic");

	private boolean isSaveOn = true;
	private boolean displayCommandline = true;

	public LogicController() {
		logger.log(Level.INFO, "Initialising logic class");

		parser = new Parser();
		taskManager = new TaskManager();
		categoryManager = new CategoryManager();
		history = new Stack<ReversibleCommand>();

		try {
			storage = new StorageController();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "ERROR: Fail to create storage");
			uiController.displayMessage("ERROR: CREATING STORAGE");
		}

		// try {
		// categoryManager.categories = storage.loadCategory();
		// } catch (IOException e) {
		// System.out.println("ERROR: LOAD CATEGORY");
		// uiController.displayMessage("ERROR: LOAD CATEGORY");
		// }

		try {
			ArrayList<Task> tasks = storage.loadTasks();
			taskManager.loadTask(tasks);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "ERROR: Fail to load task from storage");
			uiController.displayMessage("ERROR: LOAD TASK");
		}

		logger.log(Level.INFO, "End of initialising logic class");
	}

	/**
	 * process and execute command input from user
	 * 
	 * @param input
	 */
	public void processCommand(String input) {
		Command command = parser.getCommand(input);
		
		assert (command != null);
		executeCommand(command);
		addCommandToHistory(command);
		refreshUIController();
		save();
		displayInCommandline();
	}

	/**
	 * @param command
	 */
	private void executeCommand(Command command) {
		try {
			command.execute(this);
		} catch (IncorrectInputException e) {
			uiController.displayMessage(e.getMessage());
		}
	}

	/**
	 * @param command
	 */
	private void addCommandToHistory(Command command) {
		if (command instanceof ReversibleCommand) {
			history.push((ReversibleCommand) command);
		}
	}
	
	public void undoLatestCommand(){
		ReversibleCommand reversibleCommand;
		if(!history.isEmpty()){
			reversibleCommand = history.pop();
			reversibleCommand.undo(this);
		}
	}
	
	private void save() {
		if (!isSaveOn) {
			return;
		}
		
		try {
			storage.saveTasks(taskManager.getAllTasks());
		} catch (IOException e) {
			logger.log(Level.SEVERE, "ERROR: Fail to save");
			uiController.displayMessage("ERROR: SAVING");
		}
	}

	/**
	 * 
	 */
	private void displayInCommandline() {
		if(displayCommandline){
			taskManager.display();
		}
	}
	
	public void enableSave() {
		isSaveOn = true;
	}

	public void disableSave() {
		isSaveOn = false;
	}

	public void clearTask() {
		taskManager.clear();
		save();
	}

	private void refreshUIController() {
		// uiController.refreshMainView(taskManager.getTaskGroupsToday());
		// if (uiController == null)
		// return;

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

		case FLOAT:
			uiController.refreshMainView(taskManager.getTaskGroupsFloating());
			break;

		case CATEGORY:

			break;
		}

		uiController.refreshCategoryMenuView(categoryManager.getCategoryList());
	}

	public Task addFloatingTask(String taskName){
		Task addedTask = taskManager.addFloatingTask(taskName);
		return addedTask;
	}
	
	
	public Task addDeadlineTask(String taskName, DateTime dateTimeDeadline){
		Task addedTask = taskManager.addDeadlineTask(taskName, dateTimeDeadline);
		return addedTask;
	}
	
	public Task addEventTask(String taskName, DateTime dateTimeStart, DateTime dateTimeEnd){
		Task addedTask = taskManager.addEventTask(taskName, dateTimeStart, dateTimeEnd);
		return addedTask;
	}
	
	public boolean containsTask(int taskId){
		return taskManager.contains(taskId);
	}
	
	public Task removeTask(int taskId){
		Task removedTask = taskManager.remove(taskId);
		return removedTask;
	}
	
	/**
	 * pass the uicontroller references to this class
	 * 
	 * @param ui
	 */
	public void setUIController(UIController ui) {
		this.uiController = ui;
		refreshUIController();
	}

	/**
	 * Get the TaskManager object
	 * 
	 * @return TaskManager
	 */
	public TaskManager getTaskManager() {
		return taskManager;
	}

	/**
	 * Get UIController object
	 * 
	 * @return UIController
	 */
	public UIController getUIController() {
		return uiController;
	}

	/**
	 * Get Storage object
	 * 
	 * @return Storage
	 */
	public StorageController getStorage() {
		return storage;
	}

	/**
	 * Get CategoryManager object
	 * 
	 * @return CategoryManager
	 */
	public CategoryManager getCategoryManager() {
		return categoryManager;
	}

	public Stack<ReversibleCommand> getHistory() {
		return history;
	}
}
