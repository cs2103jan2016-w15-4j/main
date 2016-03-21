package dooyit.logic.core;

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

import dooyit.common.datatype.Task;

public class LogicController {

	private Parser parser;
	private TaskManager taskManager;
	private CategoryManager categoryManager;
	private StorageController storage;
	private Stack<ReversibleCommand> history;
	private UIController uiController;
	private static Logger logger = Logger.getLogger("Logic");

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

		try {
			command.execute(this);
		} catch (IncorrectInputException e) {
			uiController.displayMessage(e.getMessage());
		}

		if (command instanceof ReversibleCommand) {
			history.push((ReversibleCommand) command);
		}

		refreshUIController();

		try {

			save();

		} catch (IOException e) {
			logger.log(Level.SEVERE, "ERROR: Fail to save");
			uiController.displayMessage("ERROR: SAVING");
		}

		// update UI - UI.update();
		taskManager.display();
	}

	private void save() throws IOException {
		storage.saveTasks(taskManager.getAllTasks());
	}

	private void refreshUIController() {
		// uiController.refreshMainView(taskManager.getTaskGroupsToday());
		if (uiController == null)
			return;

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
