//@@author A0126356E
package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.Constants;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class ChangeThemeCommand implements Command {

	
	private String themeString;
	private boolean hasError = false;

	public ChangeThemeCommand(String themeString) {
		this.themeString = themeString;
	}

	public boolean hasError() {
		return hasError;
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		LogicAction logicAction = null;

		String lowerThemeString = themeString.toLowerCase();

		switch (lowerThemeString) {
		case Constants.THEME_DEFAULT:
			logicAction = changeToDefaultTheme();
			break;

		case Constants.THEME_LIGHT:
			logicAction = changeToDefaultTheme();
			break;

		case Constants.THEME_DARK:
			logicAction = changeToDarkTheme();
			break;

		case Constants.THEME_AQUA:
			logicAction = changeToAquaTheme();
			break;

		case Constants.THEME_CUSTOM:
			logicAction = changeToCustomTheme();
			break;

		default:
			logicAction = showError();
		}

		return logicAction;
	}

	public LogicAction showError() {
		return new LogicAction(Action.ERROR, String.format(Constants.FEEDBACK_INVALID_THEME, themeString));
	}

	public LogicAction changeToCustomTheme() {
		return new LogicAction(Action.CHANGE_THEME_CUSTOM, String.format(Constants.FEEDBACK_SUCCESS_CHANGE_THEME, themeString));
	}

	public LogicAction changeToAquaTheme() {
		return new LogicAction(Action.CHANGE_THEME_AQUA, String.format(Constants.FEEDBACK_SUCCESS_CHANGE_THEME, themeString));
	}

	public LogicAction changeToDarkTheme() {
		return new LogicAction(Action.CHANGE_THEME_DARK, String.format(Constants.FEEDBACK_SUCCESS_CHANGE_THEME, themeString));
	}

	public LogicAction changeToDefaultTheme() {
		return new LogicAction(Action.CHANGE_THEME_DEFAULT, String.format(Constants.FEEDBACK_SUCCESS_CHANGE_THEME, themeString));
	}

}
