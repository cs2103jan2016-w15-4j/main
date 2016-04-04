package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class SearchCommand implements Command {
	private String searchString;
	private boolean hasError = false;
	
	public SearchCommand(String searchString){
		this.searchString = searchString;
	}
	
	public boolean hasError(){
		return hasError;
	}
	
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		//logic.setActiveViewSearch(searchString);
		logic.setSearchKey(searchString);
		LogicAction logicAction = new LogicAction(Action.SEARCH);
		return logicAction;
	}

}
