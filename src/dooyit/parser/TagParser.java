//@@author A0133338J
package dooyit.parser;

import java.util.ArrayList;

import dooyit.common.exception.IncorrectInputException;

public class TagParser implements ParserCommons {
	protected static final String MARKER_FOR_INTERVAL_TAG_TYPE = "-";
	private static final String ERROR_MESSAGE_INVALID_TASK_ID = "Invalid Task ID: ";
	protected String userInput;
	protected String[] splitInput;
	protected ArrayList<Integer> taskIdsForTagging;
	
	enum TagType {
		VALID, INVALID
	}

	public TagParser() {

	}
	
	public void parseTaskIds() throws IncorrectInputException {
		for (int i = 0; i < splitInput.length; i++) {
			String currWord = splitInput[i];
			if (ParserCommons.isNumber(currWord)) {
				int taggedId = Integer.parseInt(currWord);
				taskIdsForTagging.add(taggedId);
			} else if (isIntervalType(currWord)) {
				setInterval(currWord);
			} else {
				throw new IncorrectInputException(ERROR_MESSAGE_INVALID_TASK_ID + currWord);
			}
		}
	}
	
	public static boolean isIntervalType(String currWord) {
		boolean isInterval = false;
		if (currWord.contains(MARKER_FOR_INTERVAL_TAG_TYPE)) {
			String[] splitByDash = currWord.split(MARKER_FOR_INTERVAL_TAG_TYPE);
			isInterval = checkIfStartAndEndAreNumbers(splitByDash);
		}
		return isInterval;
	}

	private static boolean checkIfStartAndEndAreNumbers(String[] splitByDash) {
		boolean ans = false;
		if (splitByDash.length == 2) {
			String start = splitByDash[0];
			String end = splitByDash[1];
			ans = ParserCommons.isNumber(start) && ParserCommons.isNumber(end);
		}
		return ans;
	}

	public void setAttributesForTagging(String input) {
		userInput = input;
		splitInput = userInput.split("\\s+");
		taskIdsForTagging = new ArrayList<Integer>();
	}

	public void setInterval(String currWord) {
		String[] splitByDash = currWord.split(MARKER_FOR_INTERVAL_TAG_TYPE);
		int start = Integer.parseInt(splitByDash[0]); 
		int end = Integer.parseInt(splitByDash[1]);
		for (int i = start; i <= end; i++) {
			taskIdsForTagging.add(i);
		}
	}

	public TagType getTagType() {
		TagType type;
		if (taskIdsForTagging.size() >= 1) {
			type = TagType.VALID;
		} else {
			type = TagType.INVALID;
		}
		return type;
	}
}
