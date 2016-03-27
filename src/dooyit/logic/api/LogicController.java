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

import dooyit.common.datatype.Category;
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

		initParser();
		initTaskManager();
		initCategoryManager();
		initHistory();
		initStorage();
		loadFromStorage();
		assignTaskToCategory();
		
		logger.log(Level.INFO, "End of initialising logic class");
	}

	/**
	 * 
	 */
	public void initParser() {
		parser = new Parser();
	}
	
	/**
	 * 
	 */
	public void initTaskManager() {
		taskManager = new TaskManager();
	}
	
	/**
	 * 
	 */
	public void initCategoryManager() {
		categoryManager = new CategoryManager();
	}

	/**
	 * 
	 */
	public void initHistory() {
		history = new Stack<ReversibleCommand>();
	}
	
	/**
	 * 
	 */
	private void initStorage() {
		try {
			storage = new StorageController();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "ERROR: Fail to create storage");
			uiController.displayMessage("ERROR: CREATING STORAGE");
		}
	}
	
	/**
	 * 
	 */
	public void loadFromStorage() {
//		try {
//			categoryManager.load(storage.loadCategory());
//		} catch (IOException e) {
//			System.out.println("ERROR: LOAD CATEGORY");
//			uiController.displayMessage("ERROR: LOAD CATEGORY");
//		}

		try {
			ArrayList<Task> tasks = storage.loadTasks();
			loadTasks(tasks);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "ERROR: Fail to load task from storage");
			uiController.displayMessage("ERROR: LOAD TASK");
		}
	}


	/**
	 * @param tasks
	 */
	public void loadTasks(ArrayList<Task> tasks) {
		taskManager.load(tasks);
	}

	public void assignTaskToCategory(){
		ArrayList<Task> tasks = taskManager.getAllTasks();
		
		for(Task task : tasks){
			if(task.hasUncheckCategory()){
				String uncheckCategory = task.getUncheckCategory();
				if(categoryManager.contains(uncheckCategory)){
					Category category = categoryManager.find(uncheckCategory);
					task.setCategory(category);
				}
			}
		}
		
		categoryManager.setDefaultCategories();
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

	public void undoLatestCommand() {
		ReversibleCommand reversibleCommand;
		if (!history.isEmpty()) {
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
			storage.saveCategory(categoryManager.getAllCategories());
		} catch (IOException e) {
			logger.log(Level.SEVERE, "ERROR: Fail to save");
			uiController.displayMessage("ERROR: SAVING");
		}
		
		
	}

	/**
	 * 
	 */
	private void displayInCommandline() {
		if (displayCommandline) {
			taskManager.display();
		}
	}

	public void enableSave() {
		isSaveOn = true;
	}

	public void disableSave() {
		isSaveOn = false;
	}

	public ArrayList<Task> clearTask() {
		ArrayList<Task> clearedTasks = taskManager.clear();
		//save();
		return clearedTasks;
	}

	public void setActiveView(UIMainViewType uiMainViewType) {
		if (uiController == null) {
			return;
		}

		uiController.setActiveViewType(uiMainViewType);

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

		default:
			assert (true);
		}
	}

	public void setActiveViewCategory(Category category) {
		if (uiController == null) {
			return;
		}

		uiController.setActiveViewType(UIMainViewType.CATEGORY);
		uiController.refreshMainView(taskManager.getTaskGroupsCompleted(), category);
	}

	public void setActiveViewSearch(String searchString) {
		if (uiController == null) {
			return;
		}

		uiController.setActiveViewType(UIMainViewType.SEARCH);
		uiController.refreshMainView(taskManager.getTaskGroupSearched(searchString));
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

		case SEARCH:

			break;
		}

		uiController.refreshCategoryMenuView(categoryManager.getAllCategories());
	}

	public Task addFloatingTask(String taskName) {
		Task addedTask = taskManager.addFloatingTask(taskName);
		return addedTask;
	}

	public Task addDeadlineTask(String taskName, DateTime dateTimeDeadline) {
		Task addedTask = taskManager.addDeadlineTask(taskName, dateTimeDeadline);
		return addedTask;
	}

	public Task addEventTask(String taskName, DateTime dateTimeStart, DateTime dateTimeEnd) {
		Task addedTask = taskManager.addEventTask(taskName, dateTimeStart, dateTimeEnd);
		return addedTask;
	}

	public boolean containsTask(int taskId) {
		return taskManager.contains(taskId);
	}

	public boolean containsTask(Task task) {
		return taskManager.contains(task);
	}

	public Task findTask(int taskId) {
		return taskManager.find(taskId);
	}

	public Task removeTask(int taskId) {
		Task removedTask = taskManager.remove(taskId);
		return removedTask;
	}

	public boolean containsCategory(String categoryName) {
		return categoryManager.contains(categoryName);
	}

	public Category addCategory(String categoryName) {
		Category addedCategory = categoryManager.addCategory(categoryName);
		return addedCategory;
	}

	public Category addCategory(String categoryName, String colourString) {
		Category addedCategory = categoryManager.addCategory(categoryName, colourString);
		return addedCategory;
	}

	public Category findCategory(String categoryName) {
		return categoryManager.find(categoryName);
	}

	public boolean isTodayTask(Task task) {
		return taskManager.isTodayTask(task);
	}

	public boolean isNext7daysTask(Task task) {
		return taskManager.isNext7DaysTask(task);
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
