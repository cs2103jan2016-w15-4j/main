package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.LogicController;
import dooyit.ui.UIController;
import dooyit.ui.UITheme;

public class ChangeThemeCommand extends Command {

	String themeString;
	UITheme theme;
	
	public ChangeThemeCommand(String themeString){
		this.themeString = themeString;
	}
	
	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		UIController uicontroller = logic.getUIController();
		
		String lowerThemeString = themeString.toLowerCase();
		
		switch(lowerThemeString){
		case "light":
			theme = UITheme.LIGHT;
			break;
		
		case "dark":
			theme = UITheme.DARK;
			break;
			
		case "aqua":
			theme = UITheme.AQUA;
			break;	
		
		case "custom":
			theme = UITheme.CUSTOM;
			break;	
			
		default:
			throw new IncorrectInputException(themeString + " is not available, try LIGHT, DARK, AQUA or CUSTOM");
		}
		
		uicontroller.changeTheme(theme);
	}

}
