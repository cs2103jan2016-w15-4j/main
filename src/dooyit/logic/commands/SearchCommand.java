package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class SearchCommand implements Command {
	private String searchString;
	
	public SearchCommand(String searchString){
		this.searchString = searchString;
	}
	
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		logic.setActiveViewSearch(searchString);
		LogicAction logicAction = new LogicAction(Action.SEARCH);
		return logicAction;
	}

}
