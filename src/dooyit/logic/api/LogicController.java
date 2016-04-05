//@@author A0126356E
package dooyit.logic.api;

import dooyit.storage.StorageController;
import dooyit.parser.Parser;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.CategoryManager;
import dooyit.logic.DataManager;
import dooyit.logic.HistoryManager;
import dooyit.logic.TaskManager;
import dooyit.logic.commands.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import dooyit.common.datatype.Category;
import dooyit.common.datatype.CategoryData;
import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.Task;
import dooyit.common.datatype.TaskData;
import dooyit.common.datatype.TaskGroup;

public class LogicController {

	private Parser parser;
	private TaskManager taskManager;
	private CategoryManager categoryManager;
	private StorageController storage;
	private HistoryManager historyManager;
	private DataManager dataManager;
	private static Logger logger = Logger.getLogger("Logic");
	private boolean isSaveOn = true;
	private boolean displayCommandline = true;
	private String currentSearch = "";

	public LogicController() {
		logger.log(Level.INFO, "Initialising logic class");

		initParser();
		initTaskManager();
		initCategoryManager();
		initHistoryManager();
		initStorage();
		initDataManager();
		loadFromStorage();
		setDefaultCategories();
		save();

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
	public void initHistoryManager() {
		historyManager = new HistoryManager();
	}

	/**
	 * 
	 */
	private void initStorage() {
		try {
			storage = new StorageController();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "ERROR: Fail to create storage");
		}
	}

	private void initDataManager() {
		dataManager = new DataManager();
	}

	/**
	 * 
	 */
	public void loadFromStorage() {
		try {
			clearTasks();
			loadCategoryDataFromStorage();
			loadTaskDataFromStorage();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "ERROR: Fail to load task from storage");
			// uiController.displayMessage("ERROR: LOAD TASK");
		}
	}

	/**
	 * @param tasks
	 */
	public void loadTasks(ArrayList<Task> tasks) {
		taskManager.load(tasks);
	}

	public void setDefaultCategories() {
		categoryManager.setDefaultCategories();
	}

	/**
	 * process and execute command input from user
	 * 
	 * @param input
	 */
	public LogicAction processInput(String input) {
		
		Command command = parser.getCommand(input);
		assert (command != null);
		LogicAction logicAction = processCommand(command);
		return logicAction;
	}

	/**
	 * @param command
	 */
	public LogicAction processCommand(Command command) {
		resetTaskManager();
		LogicAction logicAction = executeCommand(command);
		addCommandToHistory(command);
		save();
		displayInCommandline();
		return logicAction;
	}

	/**
	 * @param command
	 */
	private LogicAction executeCommand(Command command) {
		LogicAction logicAction = null;

		try {
			logicAction = command.execute(this);
		} catch (IncorrectInputException e) {
			e.printStackTrace();
			logicAction = new LogicAction(Action.ERROR, "Incorrect Input.");
		}
		return logicAction;
	}

	/**
	 * @param command
	 */
	private void addCommandToHistory(Command command) {
		historyManager.addCommand(command);
	}

	public boolean undo() {
		return historyManager.undoCommand(this);
	}

	public boolean redo() {
		return historyManager.redoCommand(this);
	}

	private void save() {
		if (!isSaveOn) {
			return;
		}

		try {
			storage.saveTasks(getTaskDatas());
			storage.saveCategory(getCategoryDatas());
		} catch (IOException e) {
			logger.log(Level.SEVERE, "ERROR: Fail to save");
			// uiController.displayMessage("ERROR: SAVING");
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

	public ArrayList<TaskData> getTaskDatas() {
		ArrayList<Task> tasks = taskManager.getAllTasks();
		return dataManager.convertTaskstoTaskDatas(tasks);
	}

	public ArrayList<CategoryData> getCategoryDatas() {
		ArrayList<Category> categories = categoryManager.getAllCategories();
		return dataManager.convertCategorytoCategoryDatas(categories);
	}

	public void loadCategoryDataFromStorage() throws IOException {
		ArrayList<CategoryData> categoryDatas = storage.loadCategory();
		dataManager.loadCategoryData(this, categoryDatas);
	}

	public void loadTaskDataFromStorage() throws IOException {
		ArrayList<TaskData> taskDatas = storage.loadTasks();
		dataManager.loadTaskData(this, taskDatas);
	}

	public void setSelectedCategory(Category category) {
		categoryManager.setSelectedCategory(category); 
	}

	public Category getSelectedCategory() {
		return categoryManager.getSelectedCategory();
	}

	public void setSearchKey(String searchString) {
		this.currentSearch = searchString;
	}

	public ArrayList<TaskGroup> getTaskGroupsToday() {
		return taskManager.getTaskGroupsToday();
	}

	public ArrayList<TaskGroup> getTaskGroupsNext7Days() {
		return taskManager.getTaskGroupsNext7Days();
	}

	public ArrayList<TaskGroup> getTaskGroupsAll() {
		return taskManager.getTaskGroupsAll();
	}

	public ArrayList<TaskGroup> getTaskGroupsCompleted() {
		return taskManager.getTaskGroupsCompleted();
	}

	public ArrayList<TaskGroup> getTaskGroupsFloating() {
		return taskManager.getTaskGroupsFloating();
	}

	public ArrayList<TaskGroup> getTaskGroupCategory() {
		return taskManager.getTaskGroupCategory(categoryManager.getSelectedCategory());
	}

	public ArrayList<Category> getAllCategories() {
		return categoryManager.getAllCategories();
	}

	public ArrayList<TaskGroup> getSearchTaskGroup() {
		return taskManager.getTaskGroupSearched(currentSearch);
	}

	public void resetTaskManager(){
		taskManager.resetNewTask();
	}
	
	public void addTask(Task task) {
		taskManager.add(task);
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
	
	public Task changeToFloatingTask(int taskId){
		return taskManager.changeToFloatingTask(taskId);
	}

	public Task changeTaskName(int taskId, String newName) {
		return taskManager.changeTaskName(taskId, newName);
	}

	public Task changeTaskToDeadline(int taskId, DateTime dateTimeDeadline) {
		return taskManager.changeTaskToDeadline(taskId, dateTimeDeadline);
	}

	public Task changeTaskToEvent(int taskId, DateTime dateTimeStart, DateTime dateTimeEnd) {
		return taskManager.changeTaskToEvent(taskId, dateTimeStart, dateTimeEnd);
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

	public boolean removeTask(Task task) {
		boolean isRemoved = taskManager.remove(task);
		return isRemoved;
	}

	public void markTask(Task task) {
		taskManager.markTask(task);
	}

	public void markTask(int taskId) {
		taskManager.markTask(taskId);
	}

	public void unmarkTask(Task task) {
		taskManager.unmarkTask(task);
	}

	public void unmarkTask(int taskId) {
		taskManager.unmarkTask(taskId);
	}

	public int noOfTask() {
		return taskManager.size();
	}

	public boolean hasOverlapWithOverEventTask(Task task){
		return taskManager.hasOverlapWithOverEventTask(task);
	}
	
	public ArrayList<Task> removeTasksWithCategory(Category category) {
		ArrayList<Task> tasksWithCategoty = taskManager.removeTasksWithCategory(category);
		return tasksWithCategoty;
	}

	public ArrayList<Task> clearTasks() {
		ArrayList<Task> clearedTasks = taskManager.clear();
		return clearedTasks;
	}

	public void addCategory(Category category) {
		categoryManager.addCategory(category);
	}

	public Category addCategory(String categoryName) {
		Category addedCategory = categoryManager.addCategory(categoryName);
		return addedCategory;
	}

	public Category addCategory(String categoryName, String colourString) {
		Category addedCategory = categoryManager.addCategory(categoryName, colourString);
		return addedCategory;
	}

	public Category removeCategory(String categoryName) {
		Category removedCategory = categoryManager.remove(categoryName);
		return removedCategory;
	}

	public boolean removeCategory(Category category) {
		return categoryManager.remove(category);
	}

	public boolean containsCategory(String categoryName) {
		return categoryManager.contains(categoryName);
	}

	public Category findCategory(String categoryName) {
		return categoryManager.find(categoryName);
	}

	public ArrayList<Category> clearCategory() {
		ArrayList<Category> clearedCategories = categoryManager.clear();
		return clearedCategories;
	}

	public boolean isFloatingTask(Task task) {
		return taskManager.isFloatingTask(task);
	}

	public boolean isTodayTask(Task task) {
		return taskManager.isTodayTask(task);
	}

	public boolean isNext7daysTask(Task task) {
		return taskManager.isNext7DaysTask(task);
	}

	public String getFilePath() {
		return storage.getFilePath();
	}

	public boolean setFileDestinationPath(String path) throws IncorrectInputException {
		try {
			return storage.setFileDestination(path);
		} catch (IOException e) {
			throw new IncorrectInputException("Invalid path: " + path);
		}
	}

	public void setDefaultCustomCss(String path) {
		try {
			storage.generateCss(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setCustomCssPath(String path) {
		
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

	public HistoryManager getHistoryManager() {
		return historyManager;
	}
}
