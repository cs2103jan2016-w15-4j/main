package dooyit.logic.commands;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CustomColor;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.CategoryManager;
import dooyit.logic.api.LogicController;

public class AddCategoryCommand extends Command {

	private String categoryName;
	private String colorString;
	Category addedCategory;

	public AddCategoryCommand(String categoryName) {
		this.categoryName = categoryName;
	}

	public AddCategoryCommand(String categoryName, String colorString) {
		this.categoryName = categoryName;
		this.colorString = colorString;
	}
	
	private boolean hasColorString(){
		return colorString != null;
	}

	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		if(!logic.containsCategory(categoryName)){
			if(hasColorString()){
				addedCategory = logic.addCategory(categoryName, colorString);
			}else{
				addedCategory = logic.addCategory(categoryName);
			}
		}
		else{
			throw new IncorrectInputException("Category: " + categoryName + " already exists.");
		}
	}

}
