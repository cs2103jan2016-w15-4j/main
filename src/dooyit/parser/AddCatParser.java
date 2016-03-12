package dooyit.parser;

import java.util.ArrayList;

import dooyit.common.datatype.Colour;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;
import dooyit.logic.core.Logic;

public class AddCatParser {
	
	private static final String MARKER_COLOUR = ",";
	private static final String MARKER_ID = "-";
	
	private static String userInput;
	private static String catName;
	private static String colour;
	private static ArrayList<Integer> taskIds;
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
	
	enum ADD_CATEGORY_TYPE {
		CREATE_NEW_CATEGORY_WITH_COLOUR, ADD_TO_EXISTING_CATEGORY, CREATE_AND_ADD_TO_EXISTING_CATEGORY, CREATE_NEW_CATEGORY_WITHOUT_COLOUR
	};
	
	public AddCatParser(String input) {
		userInput = input.toLowerCase();
		colour = DEFAULT_COLOUR;
		taskIds = new ArrayList<Integer>();
	}
	
	public Command getCommand() {
		parse();
		switch(getCommandType()) {
		case CREATE_NEW_CATEGORY_WITH_COLOUR :
			setCreateNewCategoryCommand();
			break;
			
		case ADD_TO_EXISTING_CATEGORY :
			parseTaskIds();
			setAddToExistingCategoryCommand();
			break;
			
		case CREATE_NEW_CATEGORY_WITHOUT_COLOUR :
			setCreateNewCategoryCommand();
			break;
			
		/*case CREATE_AND_ADD_TO_EXISTING_CATEGORY :
			parseTaskIds();
			setCreateAndAddToExistingCategoryCommand();*/
			
		default :
			cmd = CommandUtils.createInvalidCommand(userInput + " is an invalid addcat command!");
			break;
		}
		
		return cmd;
	}

	private void parseTaskIds() {
		int indexMarkerIds = userInput.lastIndexOf(MARKER_ID);
		String ids = userInput.substring(indexMarkerIds).replace(MARKER_ID, "").trim();
		String[] splitIds = ids.split("\\s+");
		
		for(int i = 0; i < splitIds.length; i++) {
			taskIds.add(Integer.parseInt(splitIds[i]));
		}
	}

	private void setCreateAndAddToExistingCategoryCommand() {
		//cmd = CommandUtils.createAddCategoryCommand(catName, colour, taskIds);
	}

	private void setAddToExistingCategoryCommand() {
		//cmd = CommandUtils.createAddCategoryCommand(catName, taskIds);
	}

	private ADD_CATEGORY_TYPE getCommandType() {
		int indexMarkerColour = userInput.lastIndexOf(MARKER_COLOUR);
		int indexMarkerIndices = userInput.lastIndexOf(MARKER_ID);
		
		if(indexMarkerColour != -1 && indexMarkerIndices != -1) {
			return ADD_CATEGORY_TYPE.CREATE_AND_ADD_TO_EXISTING_CATEGORY;
		} else if(indexMarkerColour == -1 && indexMarkerIndices != -1) {
			return ADD_CATEGORY_TYPE.ADD_TO_EXISTING_CATEGORY;
		} else if(indexMarkerColour != -1 && indexMarkerIndices == -1) {
			return ADD_CATEGORY_TYPE.CREATE_NEW_CATEGORY_WITH_COLOUR;
		} else if(indexMarkerColour == -1 && indexMarkerIndices == -1) {
			return ADD_CATEGORY_TYPE.CREATE_NEW_CATEGORY_WITHOUT_COLOUR;
		} else {
			return null;
		}
	}

	private void setCreateNewCategoryCommand() {
		switch(colour) {
		case BLACK :
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.BLACK);
			break;
		
		case BLUE :
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.BLUE);
			break;
			
		case CYAN :
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.CYAN);
			break;
			
		case GREY :
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.GREY);
			break;
			
		case GRAY :
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.GREY);
			break;
		
		case GREEN :
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.GREEN);
			break;
			
		case MAGENTA :
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.MAGENTA);
			break;
			
		case PINK :
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.PINK);
			break;
			
		case RED :
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.RED);
			break;
			
		case WHITE :
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.WHITE);
			break;
			
		case YELLOW :
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.YELLOW);
			break;
			
		case DEFAULT_COLOUR :
			cmd = CommandUtils.createAddCategoryCommand(catName);
			break;
			
		default :
			cmd = CommandUtils.createInvalidCommand(colour + "is an invalid colour!");
			break;
			
		}
	}

	private void parse() {
		int lastOccurrenceOfMarker = userInput.lastIndexOf(MARKER_COLOUR);
		if(lastOccurrenceOfMarker != -1) {
			catName = userInput.substring(0, lastOccurrenceOfMarker).trim();
			colour = userInput.substring(lastOccurrenceOfMarker).replace(MARKER_COLOUR, "").trim();
		} else {
			catName = userInput.trim();
		}
	}
}
