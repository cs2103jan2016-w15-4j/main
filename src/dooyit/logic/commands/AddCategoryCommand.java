package dooyit.logic.commands;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CustomColor;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.CategoryManager;
import dooyit.logic.core.LogicController;

public class AddCategoryCommand extends Command {

	private String categoryName;
	private CustomColor customColor;
	private String colorString;

	public AddCategoryCommand(String categoryName) {
		this.categoryName = categoryName;
		this.customColor = null;
	}

	public AddCategoryCommand(String categoryName, String colorString) {
		this.categoryName = categoryName;
		this.colorString = colorString;
	}
	
	private boolean hasCustomColor(){
		return customColor != null;
	}
	
	private boolean hasColorString(){
		return colorString != null;
	}

	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		CategoryManager categoryManager = logic.getCategoryManager();
		Category category;

		if(hasCustomColor()){
			category = categoryManager.addCategory(categoryName, customColor);
		}else{
			category = categoryManager.addCategory(categoryName);
		}

		if (category == null) {
			throw new IncorrectInputException("Category: " + categoryName + " already exists.");
		}
	}

}
