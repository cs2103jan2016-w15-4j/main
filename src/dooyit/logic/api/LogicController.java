package dooyit.logic.api;

import dooyit.storage.StorageController;
import dooyit.ui.UIController;
import dooyit.ui.UIMainViewType;
import dooyit.parser.Parser;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.CategoryManager;
import dooyit.logic.TaskManager;
import dooyit.logic.commands.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CategoryData;
import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTaskData;
import dooyit.common.datatype.EventTaskData;
import dooyit.common.datatype.FloatingTaskData;
import dooyit.common.datatype.Task;
import dooyit.common.datatype.TaskData;

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
		setDefaultCategories();

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
		try {
			loadCategoryData();
			loadTaskData();
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
		LogicAction logicAction = executeCommand(command);
		addCommandToHistory(command);
		refreshUIController();
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
			uiController.displayMessage(e.getMessage());
			logicAction = new LogicAction(Action.ERROR);
		}
		return logicAction;
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
			storage.saveTasks(getTaskDatas());
			storage.saveCategory(getCategoryDatas());
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

	public ArrayList<TaskData> getTaskDatas() {
		ArrayList<Task> tasks = taskManager.getAllTasks();
		return convertTaskstoTaskDatas(tasks);
	}

	public ArrayList<TaskData> convertTaskstoTaskDatas(ArrayList<Task> tasks) {
		ArrayList<TaskData> taskDatas = new ArrayList<TaskData>();

		for (Task task : tasks) {
			TaskData taskData = task.convertToData();
			taskDatas.add(taskData);
		}

		return taskDatas;
	}

	public ArrayList<CategoryData> getCategoryDatas() {
		ArrayList<Category> categories = categoryManager.getAllCategories();
		return convertCategorytoCategoryDatas(categories);
	}

	public ArrayList<CategoryData> convertCategorytoCategoryDatas(ArrayList<Category> categories) {
		ArrayList<CategoryData> categoryDatas = new ArrayList<CategoryData>();

		for (Category category : categories) {
			CategoryData categoryData = category.convertToData();
			categoryDatas.add(categoryData);
		}

		return categoryDatas;
	}

	public void loadCategoryData() throws IOException {
		ArrayList<CategoryData> categoryDatas = storage.loadCategory();

		for (CategoryData categoryData : categoryDatas) {
			try{
				categoryManager.addCategory(categoryData.getName(), categoryData.getColor());
			}catch(IncorrectInputException e){
				
			}
		}
	}

	public void loadTaskData() throws IOException {
		ArrayList<TaskData> taskDatas = storage.loadTasks();

		for (TaskData taskData : taskDatas) {
			Task task = taskData.convertToTask();

			if (taskData.hasCategory()) {
				Category category = categoryManager.find(taskData.getCategory());
				task.setCategory(category);
			}

			taskManager.add(task);
		}
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

	public ArrayList<Task> removeTasksWithCategory(Category category) {
		ArrayList<Task> tasksWithCategoty = taskManager.removeTasksWithCategory(category);
		return tasksWithCategoty;
	}

	public ArrayList<Task> clearTask() {
		ArrayList<Task> clearedTasks = taskManager.clear();
		// save();
		return clearedTasks;
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

	public boolean containsCategory(String categoryName) {
		return categoryManager.contains(categoryName);
	}

	public Category findCategory(String categoryName) {
		return categoryManager.find(categoryName);
	}

	public ArrayList<Category> clearCategory() {
		ArrayList<Category> clearedCategories = categoryManager.clear();
		// save();
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

	public void setFileDestinationPath(String path) throws IncorrectInputException {
		try {
			storage.setFileDestination(path);
		} catch (IOException e) {
			throw new IncorrectInputException("Invalid path: " + path);
		}
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
