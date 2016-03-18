package dooyit.parser;

import dooyit.common.datatype.Colour;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class AddCategoryParser extends TagParser{
	
	//addcat <cat name> 
	//addcat <cat name> <cat colour>, <task ids>
	//addcat <cat name>, <task ids>
	private static final String MARKER_ID = ", ";

	private static String userInput;
	private static String catName;
	private String[] colourStringArray;
	private Colour[] colourObjectArray;
	private Colour colourObject;
	private static String colourString;
	
	private static Command cmd;

	private static final String BLACK = "black";
	private static final String BLUE = "blue";
	private static final String CYAN = "cyan";
	private static final String GREY = "grey";
	private static final String GRAY = "gray";
	private static final String GREEN = "green";
	private static final String MAGENTA = "magenta";
	private static final String PINK = "pink";
	private static final String RED = "red";
	private static final String WHITE = "white";
	private static final String YELLOW = "yellow";
	private static final String DEFAULT_COLOUR = "";
	private static final String VALID_COLOUR = "valid Colour";
	private static final String NO_COLOUR = "no Colour";

	private static final int INDEX_COLOUR = 0;

	private static final int INDEX_NAME = 0;

	private static boolean hasTasks;
	
	enum ADD_CATEGORY_TYPE {
		CREATE_NEW_CATEGORY_WITHOUT_TASKS, CREATE_NEW_CATEGORY_WITH_TASKS, INVALID
	};

	public AddCategoryParser() {
		super();
		colourStringArray = new String[] {BLACK, BLUE, CYAN, GREY, GRAY, GREEN, MAGENTA, PINK, RED, WHITE, YELLOW};
		colourObjectArray = new Colour[] {Colour.BLACK, Colour.BLUE, Colour.CYAN, Colour.GREY, Colour.GREY,
										  Colour.GREEN, Colour.MAGENTA, Colour.PINK, Colour.RED, Colour.WHITE, 
										  Colour.YELLOW};
	}

	public Command getCommand(String input) {
		userInput = input;
		colourString = DEFAULT_COLOUR;
		hasTasks = false;
		parse();
		
		switch (getCommandType()) {
		case CREATE_NEW_CATEGORY_WITHOUT_TASKS:
			cmd = getCreateNewCategoryCommand();
			break;

		case CREATE_NEW_CATEGORY_WITH_TASKS:
			parseTaskIds();
			cmd = getCommandToCreateCategoryWithTasks();
			break;

		default:
			cmd = CommandUtils.createInvalidCommand(userInput + " is an invalid addcat command!");
			break;
		}

		return cmd;
	}

	private Command getCommandToCreateCategoryWithTasks() {
		switch (getTagType()) {
		case SINGLE:
			try {
				parseSingleType();
			} catch (IncorrectInputException e) {
				cmd = getInvalidCommand(e.getMessage());
				break;
			}
			//cmd = CommandUtils.createAddCategoryCommand(catName, colourObject, taskIdForTagging);
			break;

		case MULTIPLE:
			try {
				parseMultipleType();
			} catch (IncorrectInputException e) {
				cmd = getInvalidCommand(e.getMessage());
				break;
			}
			//cmd = CommandUtils.createAddCategoryCommand(catName, colourObject, taskIdsForTagging);
			break;

		case INTERVAL:
			try {
				parseIntervalType();
			} catch (IncorrectInputException e) {
				cmd = getInvalidCommand(e.getMessage());
				break;
			}
			//cmd = CommandUtils.createAddCategoryCommand(catName, colourObject, taskIdsForTagging);
			break;

		case INVALID:
			cmd = getInvalidCmd();
			break;
		}
		return cmd;
	}
	
	private Command getInvalidCmd() {
		return CommandUtils.createInvalidCommand("Invalid Add Category Command!");
	}
	
	private void parseTaskIds() {
		int indexOfIDs = userInput.indexOf(MARKER_ID);
		String ids = userInput.substring(indexOfIDs + 1);
		setVariables(ids);
	}

	private ADD_CATEGORY_TYPE getCommandType() {
		if(userInput.equals("")) {
			return ADD_CATEGORY_TYPE.INVALID;
		} else if(hasTasks) {
			return ADD_CATEGORY_TYPE.CREATE_NEW_CATEGORY_WITH_TASKS;
		} else {
			return ADD_CATEGORY_TYPE.CREATE_NEW_CATEGORY_WITHOUT_TASKS;
		}
	}

	private Command getCreateNewCategoryCommand() {
		switch (checkColour()) {
		case VALID_COLOUR:
			cmd = CommandUtils.createAddCategoryCommand(catName, colourObject);
			break;
			
		case NO_COLOUR:
			cmd = CommandUtils.createAddCategoryCommand(catName);
			break;
			
		default:
			cmd = CommandUtils.createInvalidCommand(colourString + "is an invalid Colour!");
			break;

		}
		return cmd;
	}

	private String checkColour() {
		String type = null;
		if(colourString.equals("")) {
			type = NO_COLOUR;
		} else {
			for(int i = 0; i < colourStringArray.length; i++) {
				if(colourString.equals(colourStringArray[i])) {
					colourObject = colourObjectArray[i];
					type = VALID_COLOUR;
					break;
				}
			}
		}
		return type;
	}

	private void parse() {
		String[] inputArr = userInput.split("//s+");
		catName = inputArr[INDEX_NAME];
		hasTasks = userInput.contains(MARKER_ID);
		if(inputArr.length > 1) {
			colourString = inputArr[INDEX_COLOUR].replace(MARKER_ID, "").trim();
			if(!isValidColour(colourString)) {
				colourString = DEFAULT_COLOUR;
			}
		}
	}

	private boolean isValidColour(String str) {
		boolean isValid = false;
		for(int i = 0; i < colourStringArray.length; i++) {
			if(str.equals(colourStringArray[i])) {
				isValid = true;
				break;
			}
		}
		return isValid;
	}
}
