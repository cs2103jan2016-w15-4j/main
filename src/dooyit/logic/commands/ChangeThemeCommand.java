package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.Logic;
import dooyit.ui.UIController;
import dooyit.ui.UITheme;

public class ChangeThemeCommand extends Command {

	String themeString;
	UITheme theme;
	
	public ChangeThemeCommand(String themeString){
		this.themeString = themeString;
	}
	
	@Override
	public void execute(Logic logic) throws IncorrectInputException {
		UIController uicontroller = logic.getUIController();
		
		themeString = themeString.toLowerCase();
		
		switch(themeString){
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
		}
		
		uicontroller.changeTheme(theme);
	}

}
