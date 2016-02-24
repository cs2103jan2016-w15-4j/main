package dooyit.parser;

import dooyit.logic.Colour;
import dooyit.logic.Logic;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class AddCatParser {
	
	private static final String MARKER = ",";
	
	private static String userInput;
	private static String catName;
	private static String colour;
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
	
	public AddCatParser(String input) {
		userInput = input.toLowerCase();
		colour = DEFAULT_COLOUR;
		System.out.println("userInput is " + userInput);
	}
	
	public Command getCommand() {
		parse();
		setCmd();
		return cmd;
	}

	private void setCmd() {
		switch(colour) {
		case BLACK :
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.BLACK);
			break;
		
		case BLUE :
			//cmd = CommandUtils.createAddCategoryCommand(catName, Colour.BLUE);
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
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.CYAN);
			break;
			
		default :
			cmd = CommandUtils.createInvalidCommand("Invalid Colour!");
			break;
			
		}
		//cmd = CommandUtils.createAddCatCommand(catName, colour);
	}

	private void parse() {
		int lastOccurrenceOfMarker = userInput.lastIndexOf(MARKER);
		if(lastOccurrenceOfMarker != -1) {
			catName = userInput.substring(0, lastOccurrenceOfMarker).trim();
			colour = userInput.substring(lastOccurrenceOfMarker).replace(MARKER, "").trim();
		} else {
			catName = userInput.trim();
		}
	}
}
