package dooyit.logic.commands;

import dooyit.exception.IncorrectInputException;
import dooyit.logic.Category;
import dooyit.logic.CategoryManager;
import dooyit.logic.Colour;
import dooyit.logic.Logic;

public class AddCategoryCommand extends Command {

	private String categoryName;
	private Colour colour;
	
	public AddCategoryCommand(String categoryName){
		this.categoryName = categoryName;
		this.colour = null;
	}
	
	public AddCategoryCommand(String categoryName, Colour colour){
		this.categoryName = categoryName;
		this.colour = colour;
	}

	@Override
	public void execute(Logic logic) throws IncorrectInputException {
		CategoryManager categoryManager = logic.getCategoryManager();
		Category category;
		
		if(colour == null){
			category = categoryManager.addCategory(categoryName);
		}else{
			category = categoryManager.addCategory(categoryName, colour);
		}
		
		if(category == null){
			throw new IncorrectInputException("Category: " + categoryName + " already exists.");
		}
	}

}
