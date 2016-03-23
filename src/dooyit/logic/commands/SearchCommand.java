package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.LogicController;

public class SearchCommand extends Command {
	private String searchString;
	
	public SearchCommand(String searchString){
		this.searchString = searchString;
	}
	
	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		logic.setActiveViewSearch(searchString);
	}

}
