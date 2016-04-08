//@@author A0126356E
package dooyit.logic.commands;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DateTime.DAY;
import dooyit.common.datatype.DateTime.MONTH;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

/**
 * The search command can search by keyword, day, month and datetime.
 * @author limtaeu
 *
 */
public class SearchCommand implements Command {
	
	enum SearchType {
		NAME, DATE, DAY, MONTH
	}
	
	private String searchString;
	private SearchType searchType;
	private DateTime searchDateTime;
	private DAY searchDay;
	private MONTH searchMonth;
	private boolean hasError = false;

	public SearchCommand(String searchString) {
		searchType = SearchType.NAME;
		this.searchString = searchString;
	}
	
	public SearchCommand(String searchString, DAY day){
		searchType = SearchType.DAY;
		this.searchString = searchString;
		this.searchDay = day;
	}
	
	public SearchCommand(String searchString, MONTH month){
		searchType = SearchType.MONTH;
		this.searchString = searchString;
		this.searchMonth = month;
	}
	
	public SearchCommand(DateTime dateTime){
		searchType = SearchType.DATE;
		this.searchDateTime = dateTime;
	}

	public boolean hasError() {
		return hasError;
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert(logic != null);
		
		switch(searchType){
		case NAME:
			logic.setSearchKey(searchString);
			break;
			
		case DAY:
			logic.setSearchKey(searchString, searchDay);
			break;
			
		case MONTH:
			logic.setSearchKey(searchString, searchMonth);
			break;
			
		case DATE:
			logic.setSearchKey(searchDateTime);
			break;
		}
		
		LogicAction logicAction = new LogicAction(Action.SEARCH);
		return logicAction;
	}

}
