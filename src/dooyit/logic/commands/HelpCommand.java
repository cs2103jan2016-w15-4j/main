package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;
import dooyit.ui.UIController;

public class HelpCommand implements Command {

	public HelpCommand(){
		
	}
	
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		UIController uiController = logic.getUIController();
		LogicAction logicAction = new LogicAction(Action.HELP);
		
		uiController.showHelp();
		
		return logicAction;
	}

}
