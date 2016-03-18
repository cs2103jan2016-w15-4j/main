package dooyit.logic.commands;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CustomColor;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.CategoryManager;
import dooyit.logic.core.Logic;

public class AddCategoryCommand extends Command {

	private String categoryName;
	private CustomColor customColor;

	public AddCategoryCommand(String categoryName) {
		this.categoryName = categoryName;
		this.customColor = null;
	}

	public AddCategoryCommand(String categoryName, CustomColor colour) {
		this.categoryName = categoryName;
		this.customColor = colour;
	}
	
	private boolean hasCustomColor(){
		return customColor != null;
	}

	@Override
	public void execute(Logic logic) throws IncorrectInputException {
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
