package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class ChangeThemeCommand implements Command {

	private String themeString;
	private boolean hasError = false;
	
	public ChangeThemeCommand(String themeString){
		this.themeString = themeString;
	}
	
	public boolean hasError(){
		return hasError;
	}
	
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		LogicAction logicAction = null;
		
		
		String lowerThemeString = themeString.toLowerCase();
		
		switch(lowerThemeString){
		case "default":
			logicAction = new LogicAction(Action.CHANGE_THEME_DEFAULT);
			break;
			
		case "light":
			logicAction = new LogicAction(Action.CHANGE_THEME_DEFAULT);
			break;
		
		case "dark":
			logicAction = new LogicAction(Action.CHANGE_THEME_DARK);
			break;
			
		case "aqua":
			logicAction = new LogicAction(Action.CHANGE_THEME_AQUA);
			break;	
		
		case "custom":
			logicAction = new LogicAction(Action.CHANGE_THEME_CUSTOM);
			break;	
			
		default:
			logicAction = new LogicAction(Action.ERROR);
			throw new IncorrectInputException(themeString + " is not available, try DEFAULT, DARK, AQUA or CUSTOM");
		}
		
		//uicontroller.changeTheme(theme);
		
		return logicAction;
	}

}
