package dooyit.parser;

import java.util.ArrayList;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class TagParser {
	public static final int INDEX_SINGLE = 0;
	public String userInput;
	public String[] splitInput;
	public ArrayList<Integer> taskIdsForTagging;
	public int taskIdForTagging;
	public int tagType;
	
	enum TAG_TYPE {
		SINGLE, MULTIPLE, INTERVAL, INVALID
	}

	public TagParser() {

	}
	
	public void parseTaskIds() throws IncorrectInputException {
		switch (getTagType()) {
		case SINGLE:
			System.out.println("is single tag type");
			try {
				parseSingleType();
			} catch (IncorrectInputException e) {
				throw e;
			}
			break;

		case MULTIPLE:
			System.out.println("is multiple tag type");
			try {
				parseMultipleType();
			} catch (IncorrectInputException e) {
				throw e;
			}
			break;

		case INTERVAL:
			System.out.println("is interval tag type");
			try {
				parseIntervalType();
			} catch (IncorrectInputException e) {
				throw e;
			}
			break;

		default:
			throw new IncorrectInputException("Error: Invalid Task IDs");
		}
	}
	
	public void setVariables(String input) {
		userInput = input;
		splitInput = userInput.split("\\s+");
		taskIdsForTagging = new ArrayList<Integer>();
	}

	public void parseIntervalType() throws IncorrectInputException {
		for (int i = INDEX_SINGLE; i < splitInput.length; i++) {
			if (splitInput[i].contains("-")) {
				setInterval(splitInput[i]);
			}
		}
	}

	public void setInterval(String currWord) {
		String[] splitByDash = currWord.split("-");
		int start = Integer.parseInt(splitByDash[0]);
		int end = Integer.parseInt(splitByDash[1]);
		for (int i = start; i <= end; i++) {
			taskIdsForTagging.add(i);
		}
	}

	public void parseMultipleType() throws IncorrectInputException {
		for (int i = INDEX_SINGLE; i < splitInput.length; i++) {
			String currWord = splitInput[i];
			if (!isNumber(currWord)) {
				throw new IncorrectInputException("Invalid Number!");
			} else {
				taskIdsForTagging.add(Integer.parseInt(currWord));
			}
		}
	}

	public Command getInvalidCommand(String message) {
		return CommandUtils.createInvalidCommand(message);
	}

	public void parseSingleType() throws IncorrectInputException {
		if (isNumber(splitInput[INDEX_SINGLE])) {
			System.out.println("taskID is " + splitInput[INDEX_SINGLE]);
			taskIdForTagging = Integer.parseInt(splitInput[INDEX_SINGLE]);
		} else {
			System.out.println("taskID is " + splitInput[INDEX_SINGLE]);
			throw new IncorrectInputException("Invalid Task ID!");
		}
	}

	public TAG_TYPE getTagType() {
		if (userInput.contains("-")) {
			return TAG_TYPE.INTERVAL;
		} else if (splitInput.length == 1) {
			return TAG_TYPE.SINGLE;
		} else if (splitInput.length > 1) {
			return TAG_TYPE.MULTIPLE;
		} else {
			return TAG_TYPE.INVALID;
		}
	}

	public boolean isNumber(String currWord) {
		return currWord.matches("[0-9]+");
	}
}
