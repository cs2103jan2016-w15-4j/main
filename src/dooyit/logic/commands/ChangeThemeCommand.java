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
			logicAction = new LogicAction(Action.CHANGE_THEME_DEFAULT, String.format(Constants.FEEDBACK_SUCCESS_CHANGE_THEME, themeString));
			break;

		case Constants.THEME_LIGHT:
			logicAction = new LogicAction(Action.CHANGE_THEME_DEFAULT, String.format(Constants.FEEDBACK_SUCCESS_CHANGE_THEME, themeString));
			break;

		case Constants.THEME_DARK:
			logicAction = new LogicAction(Action.CHANGE_THEME_DARK, String.format(Constants.FEEDBACK_SUCCESS_CHANGE_THEME, themeString));
			break;

		case Constants.THEME_AQUA:
			logicAction = new LogicAction(Action.CHANGE_THEME_AQUA, String.format(Constants.FEEDBACK_SUCCESS_CHANGE_THEME, themeString));
			break;

		case Constants.THEME_CUSTOM:
			logicAction = new LogicAction(Action.CHANGE_THEME_CUSTOM, String.format(Constants.FEEDBACK_SUCCESS_CHANGE_THEME, themeString));
			break;

		default:
			logicAction = new LogicAction(Action.ERROR, String.format(Constants.FEEDBACK_INVALID_THEME, themeString));
		}

		return logicAction;
	}

}
