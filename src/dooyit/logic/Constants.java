//@@author A0126356E
package dooyit.logic;

public class Constants {
	public static final String FEEDBACK_TASK_ADDED = "Task created.";
	public static final String FEEDBACK_TASK_CLEARED = "All tasks have been cleared!";
	public static final String FEEDBACK_TASK_DELETED = "Task%1$s has been deleted.";
	public static final String FEEDBACK_TASKS_DELETED = "Tasks%1$s have been deleted.";
	public static final String FEEDBACK_TASK_MARKED = "Task%1$s has been marked.";
	public static final String FEEDBACK_TASKS_MARKED = "Tasks%1$s have been marked.";
	public static final String FEEDBACK_TASK_UNMARKED = "Task%1$s has been unmarked.";
	public static final String FEEDBACK_TASKS_UNMARKED = "Tasks%1$s have been unmarked.";
	public static final String FEEDBACK_TASK_NOT_COMPLETED = "Task is still not done.";
	public static final String FEEDBACK_TASK_MOVED = "Task%1$s has been moved to %2$s.";
	public static final String FEEDBACK_TASKS_MOVED = "Tasks%1$s have been moved to %2$s.";
	public static final String FEEDBACK_TASK_UNMOVED = "Category successfully removed from Task %1$d.";
	public static final String FEEDBACK_CONFLICTING_EVENT = "This task conclicts with another event. ";
	public static final String FEEDBACK_INCORRECT_INPUT = "Incorrect Input.";

	public static final String FEEDBACK_DELETE_CATEGORY = "CATEGORY: %1$s deleted.";
	public static final String FEEDBACK_CATEGORY_ADDED = "CATEGORY: %1$s created. %2$s";
	public static final String FEEDBACK_FAIL_CATEGORY_EXISTS = "CATEGORY: %1$s already exists.";
	public static final String FEEDBACK_CATEGORY_NOT_FOUND = "CATEGORY: %1$s not found.";
	public static final String FEEDBACK_CATEGORY_EDITED = "Category has been edited.";
	public static final String FEEDBACK_CATEGORY_DOESNT_EXIST = "Task %1$d doesn't have any category.";
	public static final String DEFAULT_CATEGORY_ENTERTAINMENT = "Entertainment";
	public static final String DEFAULT_CATEGORY_SCHOOL = "School";
	
	public static final String FEEDBACK_FAIL_UNDO = "Oops, nothing to UNDO.";
	public static final String FEEDBACK_SUCCESS_UNDO = "UNDO successful!";
	public static final String FEEDBACK_FAIL_REDO = "Oops, nothing to REDO";
	public static final String FEEDBACK_SUCCESS_REDO = "REDO successful!";

	public static final String FEEDBACK_SET_NEW_PATH_WITH_LOAD = "New data has been loaded from the new path.";
	public static final String FEEDBACK_SET_NEW_PATH = "New path has been set.";
	public static final String FEEDBACK_INVALID_PATH = "Invalid path: %1$s";

	public static final String FEEDBACK_INVALID_ID = "TASK ID: %1$d doesn't exists.";
	public static final String FEEDBACK_INVALID_IDS = "TASK ID: %1$s doesn't exists.";
	public static final String FEEDBACK_INVALID_COLOUR_WITH_SUGGESTION = "COLOUR: %1$s is not available but a random colour has been picked for you!";
	public static final String FEEDBACK_INVALID_COLOUR = "Invalid Colour: %1$s";
	
	public static final String THEME_CUSTOM = "custom";
	public static final String THEME_AQUA = "aqua";
	public static final String THEME_DARK = "dark";
	public static final String THEME_LIGHT = "light";
	public static final String THEME_DEFAULT = "default";
	public static final String FEEDBACK_SUCCESS_CHANGE_THEME = "Your theme has been change to %1$s skin!";
	public static final String FEEDBACK_INVALID_THEME = "%1$s is not available, try DEFAULT, DARK, AQUA or CUSTOM";

	public static final String SPACE = " ";
	public static final String EMPTY_STRING = "";

	public static final String ERROR_FAIL_TO_CREATE_STORAGE = "ERROR: Fail to create storage";
	public static final String ERROR_FAIL_TO_LOAD_TASK_FROM_STORAGE = "ERROR: Fail to load task from storage";
	public static final String ERROR_INCORRECT_INPUT = "ERROR: Incorrect Input.";
	public static final String ERROR_FAIL_TO_SAVE = "ERROR: Fail to save";
	public static final String ERROR_FAIL_TO_GENERATE_CSS = "Error: fail to generate CSS";
	public static final String ERROR_SET_FILE_DESTINATION_PATH = "ERROR: setFileDestinationPath";

	public static final String LOG_LOGIC = "Logic";
	public static final String LOG_MSG_INITIALISING_LOGIC_CLASS = "Initialising logic class";
	public static final String LOG_MSG_END_OF_INITIALISING_LOGIC_CLASS = "End of initialising logic class";
	public static final String LOG_MSG_INITIALISING_PARSER = "Initialising Parser";
	public static final String LOG_MSG_INITIALISING_TASK_MANAGER = "Initialising Task Manager";
	public static final String LOG_MSG_INITIALISING_CATEGORY_MANAGER = "Initialising Category Manager";
	public static final String LOG_MSG_INITIALISING_HISTORY_MANAGER = "Initialising History Manager";
	public static final String LOG_MSG_INITIALISING_STORAGE = "Initialising Storage";
	public static final String LOG_MSG_INITIALISING_DATA_MANAGER = "Initialising DataManager";
	public static final String LOG_MSG_LOADING_DATA_FROM_STORAGE = "Loading data from storage";
}
