package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.LogicController;
import dooyit.ui.UIController;

public class HelpCommand extends Command {

	public HelpCommand(){
		
	}
	
	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		UIController uiController = logic.getUIController();
		
		uiController.showHelp();
		
	}

}
