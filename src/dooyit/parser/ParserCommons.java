//@@author A0133338J
package dooyit.parser;

public interface ParserCommons {
	public static final int UNINITIALIZED_INT = -1;
	public static final String UNINITIALIZED_STRING = "-1";
	public static final String EMPTY_STRING = "";
	public static final String MARKER_START_EVENT = " from ";
	public static final String MARKER_END_EVENT = " to ";
	public static final String MARKER_WORK = " by ";
	public static final String ERROR_MESSAGE_END_BEFORE_START = "Error: End timing cannot be before Start timing";

	public static final String COMMAND_ADD = "add";
	public static final String COMMAND_ADD_CAT = "addcat";
	public static final String COMMAND_CLEAR = "clear";
	public static final String COMMAND_DELETE = "delete";
	public static final String COMMAND_DELETE_CAT = "deletecat";
	public static final String COMMAND_EDIT = "edit";
	public static final String COMMAND_EDIT_CAT = "editcat";
	public static final String COMMAND_EXIT = "exit";
	public static final String COMMAND_FLOAT = "float";
	public static final String COMMAND_HELP = "help";
	public static final String COMMAND_MARK = "mark";
	public static final String COMMAND_MOVE_TO_CAT = "move";
	public static final String COMMAND_REDO = "redo";
	public static final String COMMAND_SEARCH = "search";
	public static final String COMMAND_SHOW = "show";
	public static final String COMMAND_SKIN = "skin";
	public static final String COMMAND_STORAGE = "storage"; 
	public static final String COMMAND_UNDO = "undo";
	public static final String COMMAND_UNMARK = "unmark";
	
	public static final String[] exitCommandAlias = new String[]{"close", COMMAND_EXIT};
	public static final String[] deleteCommandAlias = new String[]{"rm", "remove", COMMAND_DELETE};
	public static final String[] floatCommandAlias = new String[]{"edittofloat", COMMAND_FLOAT};

	public static String getCommandType(String commandString) {
		String type;
		
		if(isAliasOf(commandString, exitCommandAlias)) {
			type = COMMAND_EXIT;
		} else if(isAliasOf(commandString, deleteCommandAlias)) {
			type = COMMAND_DELETE;
		} else if(isAliasOf(commandString, floatCommandAlias)) {
			type = COMMAND_FLOAT;
		} else {
			type = commandString;
		}
		return type;
	}

	public static boolean isAliasOf(String commandString, String[] arr) {
		boolean ans = false;
		for(int i = 0; i < arr.length; i++) {
			if(commandString.equals(arr[i])) {
				ans = true;
			}
		}
		return ans;
	}
	
	static boolean isUninitialized(int[] ans, int index) {
		return ans[index] == UNINITIALIZED_INT;
	}
	
	static boolean isUninitialized(int number) {
		return number == UNINITIALIZED_INT;
	}
	
	static boolean isNumber(String currWord) {
		return currWord.matches("[0-9]+");
	}
	
	static String getEndTimeString(String userInput, int indexFrom, int indexTo) {
		String endTimeString;
		if(indexFrom > indexTo) {
			endTimeString = userInput.substring(indexTo, indexFrom).replace(MARKER_END_EVENT, "").trim();
		} else {
			endTimeString = userInput.substring(indexTo).replace(MARKER_END_EVENT, "").trim();
		}
		return endTimeString;
	}

	static String getStartTimeString(String userInput, int indexFrom, int indexTo) {
		String startTimeString;
		if(indexFrom > indexTo) {
			startTimeString = userInput.substring(indexFrom).replace(MARKER_START_EVENT, "").trim();
		} else {
			startTimeString = userInput.substring(indexFrom, indexTo).replace(MARKER_START_EVENT, "").trim();
		}
		return startTimeString;
	}
	

	static String getTaskName(String userInput, int indexFrom, int indexTo) {
		String name;
		if(indexFrom < indexTo) {
			name = userInput.substring(0, indexFrom);
		} else {
			name = userInput.substring(0, indexTo);
		}
		return name;
	}
}
