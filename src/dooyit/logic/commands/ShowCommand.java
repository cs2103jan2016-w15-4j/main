package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.exception.IncorrectInputException;
import dooyit.logic.Logic;
import dooyit.logic.TaskGroup;
import dooyit.logic.TaskManager;
import dooyit.ui.UIController;
import dooyit.ui.UIMainViewType;

public class ShowCommand extends Command {
	
	String categoryName;
	UIMainViewType uiMainViewtype;
	
	public ShowCommand(UIMainViewType uiMainViewtype){
		this.uiMainViewtype = uiMainViewtype;
	}
	
	public ShowCommand(UIMainViewType uiMainViewtype, String categoryName){
		this.uiMainViewtype = uiMainViewtype;
		this.categoryName = categoryName;
	}
	
	@Override
	public void execute(Logic logic) throws IncorrectInputException{
		UIController uiController = logic.getUIController();
		
		uiController.refreshMainView(new ArrayList<TaskGroup>(), uiMainViewtype);
	}

}
