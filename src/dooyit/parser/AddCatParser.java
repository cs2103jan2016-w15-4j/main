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
			System.out.println("colour is " + colour + " and catName is " + catName);
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.BLACK);
			break;
			
		case CYAN :
			System.out.println("colour is " + colour + " and catName is " + catName);
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.CYAN);
			break;
			
		case GREY :
			System.out.println("colour is " + colour + " and catName is " + catName);
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.GREY);
			break;
			
		case GRAY :
			System.out.println("colour is " + colour + " and catName is " + catName);
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.GREY);
			break;
		
		case GREEN :
			System.out.println("colour is " + colour + " and catName is " + catName);
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.GREEN);
			break;
			
		case MAGENTA :
			System.out.println("colour is " + colour + " and catName is " + catName);
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.MAGENTA);
			break;
			
		case PINK :
			System.out.println("colour is " + colour + " and catName is " + catName);
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.PINK);
			break;
			
		case RED :
			System.out.println("colour is " + colour + " and catName is " + catName);
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.RED);
			break;
			
		case WHITE :
			System.out.println("colour is " + colour + " and catName is " + catName);
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.WHITE);
			break;
			
		case YELLOW :
			System.out.println("colour is " + colour + " and catName is " + catName);
			cmd = CommandUtils.createAddCategoryCommand(catName, Colour.YELLOW);
			break;
			
		case DEFAULT_COLOUR :
			System.out.println("colour is " + colour + " and catName is " + catName);
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
