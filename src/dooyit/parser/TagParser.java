//@@author A0133338J
package dooyit.parser;

import java.util.ArrayList;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class TagParser implements ParserCommons {
	protected static final String MARKER_FOR_INTERVAL_TAG_TYPE = "-";
	public static final String ERROR_MESSAGE_INVALID_TASK_ID = "Invalid Task ID: ";
	private static final int INDEX_SINGLE = 0;
	protected String userInput;
	protected String[] splitInput;
	protected ArrayList<Integer> taskIdsForTagging;
	protected int taskIdForTagging;
	
	enum TAG_TYPE {
		SINGLE, MULTIPLE, INVALID
	}

	public TagParser() {

	}
	
	public void parseTaskIds() throws IncorrectInputException {
		for(int i = 0; i < splitInput.length; i++) {
			String currWord = splitInput[i];
			if(ParserCommons.isNumber(currWord)) {
				int taggedId = Integer.parseInt(currWord);
				taskIdsForTagging.add(taggedId);
			} else if(isIntervalType(currWord)) {
				setInterval(currWord);
			} else {
				throw new IncorrectInputException(ERROR_MESSAGE_INVALID_TASK_ID + currWord);
			}
		}
	}
	
	public static boolean isIntervalType(String currWord) {
		boolean isInterval = false;
		if(currWord.contains(MARKER_FOR_INTERVAL_TAG_TYPE)) {
			String[] splitByDash = currWord.split(MARKER_FOR_INTERVAL_TAG_TYPE);
			isInterval = checkIfStartAndEndAreNumbers(splitByDash);

		}
		return isInterval;
	}

	private static boolean checkIfStartAndEndAreNumbers(String[] splitByDash) {
		boolean ans = false;
		if(splitByDash.length == 2) {
			String start = splitByDash[0];
			String end = splitByDash[1];
			ans = ParserCommons.isNumber(start) && ParserCommons.isNumber(end);
		}
		return ans;
	}

	public void setVariables(String input) {
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

	public Command getInvalidCommand(String message) {
		return CommandUtils.createInvalidCommand(message);
	}

	public TAG_TYPE getTagType() {
		TAG_TYPE type;
		if (taskIdsForTagging.size() == 1) {
			taskIdForTagging = taskIdsForTagging.get(INDEX_SINGLE);
			type = TAG_TYPE.SINGLE;
		} else if (taskIdsForTagging.size() > 1) {
			type = TAG_TYPE.MULTIPLE;
		} else {
			type = TAG_TYPE.INVALID;
		}
		return type;
	}
}
