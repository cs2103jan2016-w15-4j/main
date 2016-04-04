package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;
import dooyit.ui.UIController;
import dooyit.ui.UITheme;

public class ChangeThemeCommand implements Command {

	String themeString;
	UITheme theme;
	
	public ChangeThemeCommand(String themeString){
		this.themeString = themeString;
	}
	
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		UIController uicontroller = logic.getUIController();
		LogicAction logicAction = null;
		
		
		String lowerThemeString = themeString.toLowerCase();
		
		switch(lowerThemeString){
		case "light":
			theme = UITheme.LIGHT;
			logicAction = new LogicAction(Action.CHANGE_THEME_DEFAULT);
			break;
		
		case "dark":
			theme = UITheme.DARK;
			logicAction = new LogicAction(Action.CHANGE_THEME_DARK);
			break;
			
		case "aqua":
			theme = UITheme.AQUA;
			logicAction = new LogicAction(Action.CHANGE_THEME_AQUA);
			break;	
		
		case "custom":
			theme = UITheme.CUSTOM;
			logicAction = new LogicAction(Action.CHANGE_THEME_CUSTOM);
			break;	
			
		default:
			logicAction = new LogicAction(Action.ERROR);
			throw new IncorrectInputException(themeString + " is not available, try LIGHT, DARK, AQUA or CUSTOM");
		}
		
		//uicontroller.changeTheme(theme);
		
		return logicAction;
	}

}
