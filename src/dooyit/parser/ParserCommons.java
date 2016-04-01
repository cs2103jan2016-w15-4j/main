package dooyit.parser;

public interface ParserCommons {
	public static final int UNINITIALIZED_INT = -1;
	public static final String UNINITIALIZED_STRING = "-1";
	public static final String EMPTY_STRING = "";
	public static final String MARKER_START_EVENT = " from ";
	public static final String MARKER_END_EVENT = " to ";
	public static final String MARKER_WORK = " by ";
	
	default boolean isUninitialized(int[] ans, int index) {
		return ans[index] == UNINITIALIZED_INT;
	}
	
	default boolean isUninitialized(int number) {
		return number == UNINITIALIZED_INT;
	}
	
	default boolean isNumber(String currWord) {
		return currWord.matches("[0-9]+");
	}
	
	default String getEndTimeString(String userInput, int indexFrom, int indexTo) {
		String endTimeString;
		if(indexFrom > indexTo) {
			endTimeString = userInput.substring(indexTo, indexFrom).replace(MARKER_END_EVENT, "").trim();
		} else {
			endTimeString = userInput.substring(indexTo).replace(MARKER_END_EVENT, "").trim();
		}
		return endTimeString;
	}

	default String getStartTimeString(String userInput, int indexFrom, int indexTo) {
		String startTimeString;
		if(indexFrom > indexTo) {
			startTimeString = userInput.substring(indexFrom).replace(MARKER_START_EVENT, "").trim();
		} else {
			startTimeString = userInput.substring(indexFrom, indexTo).replace(MARKER_START_EVENT, "").trim();
		}
		return startTimeString;
	}
	

	default String getTaskName(String userInput, int indexFrom, int indexTo) {
		String name;
		if(indexFrom < indexTo) {
			name = userInput.substring(0, indexFrom);
		} else {
			name = userInput.substring(0, indexTo);
		}
		return name;
	}
}
