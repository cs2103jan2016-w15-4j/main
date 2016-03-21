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
	
	private boolean hasColorString(){
		return colorString != null;
	}

	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		CategoryManager categoryManager = logic.getCategoryManager();
		
		@SuppressWarnings("unused")
		Category category = null;

		if(!categoryManager.contains(categoryName)){
			if(hasColorString()){
				if(colorManager.contains(colorString)){
					CustomColor customColor = colorManager.find(colorString);
					category = categoryManager.addCategory(categoryName, customColor);
				}else{
					throw new IncorrectInputException("Color: " + colorString + " is not available.");
				}
			}else{
				category = categoryManager.addCategory(categoryName);
			}
		}
		else{
			throw new IncorrectInputException("Category: " + categoryName + " already exists.");
		}
	}

}
